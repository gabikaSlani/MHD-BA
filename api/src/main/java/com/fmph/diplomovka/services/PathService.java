package com.fmph.diplomovka.services;

import api.com.fmph.diplomovka.model.CardDom;
import api.com.fmph.diplomovka.model.FindPathParametersDom;
import com.fmph.diplomovka.TimeSimulator;
import com.fmph.diplomovka.raptor.Algorithm;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import com.fmph.diplomovka.raptor.results.PathBuilder;
import com.fmph.diplomovka.raptor.results.RaptorResults;
import com.fmph.diplomovka.raptor.results.SortPaths;
import com.fmph.diplomovka.raptor.results.filters.PagePathsFilter;
import com.fmph.diplomovka.raptor.results.filters.PathFilter;
import com.fmph.diplomovka.services.mappers.PathMapper;
import com.fmph.diplomovka.services.mappers.PathParamMapper;
import com.fmph.diplomovka.services.models.Path;
import com.fmph.diplomovka.services.models.SearchParams;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PathService {

  private final static int MIN_PATHS_ON_PAGE = 5;
  private final static String NO_PATHS_MESSAGE =
      "Pre zadané parametre neexistuje žiadna cesta. Skúste upraviť parametre.";
  private final static String NO_STOPS_AROUND_LOCATION_MESSAGE =
      "V okolí 1,5 km nie sú žiadne zastávky";

  private final PathParamMapper pathParamMapper;
  private final PathMapper pathMapper;
  private final PathBuilder pathBuilder;
  private final PathFilter pathFilter;
  private final Algorithm algorithm;
  private final PagePathsFilter pagePathsFilter;
  private final TimeSimulator timeSimulator;

  public PathService(PathParamMapper pathParamMapper, PathMapper pathMapper,
      PathBuilder pathBuilder, PathFilter pathFilter, Algorithm algorithm,
      PagePathsFilter pagePathsFilter, TimeSimulator timeSimulator) {
    this.pathParamMapper = pathParamMapper;
    this.pathMapper = pathMapper;
    this.pathBuilder = pathBuilder;
    this.pathFilter = pathFilter;
    this.algorithm = algorithm;
    this.pagePathsFilter = pagePathsFilter;
    this.timeSimulator = timeSimulator;
  }

  public ResponseEntity getResponseEntity(FindPathParametersDom findPathParametersDom) {
    SearchParams searchParams;
    List<CardDom> cardDoms;
    try {
      searchParams = pathParamMapper.createSearchParams(findPathParametersDom);
      if (searchParams.getStartStops().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NO_STOPS_AROUND_LOCATION_MESSAGE);
      }
      searchParams = setTimeFromToActualTimeIfIsInPast(searchParams);
      cardDoms = getPaths(searchParams);
      if (cardDoms.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NO_PATHS_MESSAGE);
      }
      return ResponseEntity.ok(cardDoms);
    } catch (
        Exception e) {
      e.printStackTrace();
    }
    return ResponseEntity.badRequest().body(null);
  }

  private List<CardDom> getPaths(SearchParams searchParams) {
    List<Path> pagePaths = new ArrayList<>();
    Time lastSearchedTime = searchParams.getTimeFrom();
    Time maxSearchedTime = lastSearchedTime;
    while (pagePaths.size() < MIN_PATHS_ON_PAGE) {
      List<Path> raptorResultPaths = returnPathsCreatedByRaptor(searchParams);
      List<Path> filteredPaths = pathFilter.filter(raptorResultPaths);
      if (filteredPaths.size() == 0) {
        if (wasFirstSearching(lastSearchedTime, searchParams)) {
          return new ArrayList<>();
        } else {
          lastSearchedTime = increaseTimeByMinute(searchParams, lastSearchedTime);
          if (maxSearchedTime.isBefore(lastSearchedTime)) {
            maxSearchedTime = lastSearchedTime;
          }
        }
      } else {
        pagePaths = addPathsToPagePathsAndFilter(filteredPaths, pagePaths);
        lastSearchedTime = calculateLastDepartureTime(pagePaths, searchParams, maxSearchedTime);
        if (maxSearchedTime.isBefore(lastSearchedTime)) {
          maxSearchedTime = lastSearchedTime;
        }
      }
      searchParams.setTimeFrom(lastSearchedTime);
    }
    return pathMapper.mapPathsToCardDoms(pagePaths);
  }

  private List<Path> returnPathsCreatedByRaptor(SearchParams searchParams) {
    RaptorResults raptorResults = algorithm.search(searchParams);
    return pathBuilder.buildPaths(searchParams, raptorResults);
  }

  private List<Path> addPathsToPagePathsAndFilter(List<Path> filteredPaths, List<Path> pagePaths) {
    filteredPaths.sort(new SortPaths());
    pagePaths.addAll(filteredPaths);
    pagePaths = pagePathsFilter.filter(pagePaths);
    return pagePaths;
  }

  private Time calculateLastDepartureTime(List<Path> pagePaths, SearchParams searchParams,
      Time maxDepartureTime) {
    Time increasedTime;
    if (pagePaths.size() == 0) {
      increasedTime = increaseTimeByMinute(searchParams, searchParams.getTimeFrom());
    } else {
      increasedTime = increaseTimeByMinute(searchParams, getDepartureTimeOfLastPath(pagePaths));
    }
    if (increasedTime.isBeforeOrEqual(maxDepartureTime)) {
      increasedTime = increaseTimeByMinute(searchParams, maxDepartureTime);
    }
    return increasedTime;
  }

  private Time increaseTimeByMinute(SearchParams searchParams, Time time) {
    Time updatedTimeFrom = new Time(time).addMinutes(1);
    if (updatedTimeFrom.isNextDay()) {
      updatedTimeFrom.setNextDay(false);
      searchParams.setDateFrom(addOneDayToDateFrom(searchParams));
    }
    return updatedTimeFrom;
  }

  private LocalDate addOneDayToDateFrom(SearchParams searchParams) {
    return searchParams.getDateFrom().plusDays(1);
  }

  private Time getDepartureTimeOfLastPath(List<Path> paths) {
    return paths.get(paths.size() - 1).getDepartureTime();
  }

  private boolean wasFirstSearching(Time lastSearchedTime, SearchParams searchParams) {
    return lastSearchedTime.equals(searchParams.getTimeFrom());
  }

  private SearchParams setTimeFromToActualTimeIfIsInPast(SearchParams searchParams) {
    if (searchParams.getTimeFrom().isBefore(timeSimulator.getActualTime())) {
      searchParams.setTimeFrom(timeSimulator.getActualTime());
    }
    return searchParams;
  }
}
