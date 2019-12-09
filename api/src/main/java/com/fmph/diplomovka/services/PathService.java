package com.fmph.diplomovka.services;

import api.com.fmph.diplomovka.model.CardDom;
import api.com.fmph.diplomovka.model.FindPathParametersDom;
import com.fmph.diplomovka.raptor.Algorithm;
import com.fmph.diplomovka.services.mappers.PathMapper;
import com.fmph.diplomovka.services.mappers.PathParamMapper;
import com.fmph.diplomovka.services.models.Path;
import com.fmph.diplomovka.services.models.SearchParams;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathService {

    private final PathParamMapper pathParamMapper;
    private final Algorithm algorithm;
    private final PathMapper pathMapper;

    public PathService(PathParamMapper pathParamMapper, Algorithm algorithm, PathMapper pathMapper) {
        this.pathParamMapper = pathParamMapper;
        this.algorithm = algorithm;
        this.pathMapper = pathMapper;
    }

    public List<CardDom> getPaths(FindPathParametersDom findPathParametersDom) {
        SearchParams searchParams = pathParamMapper.createSearchParams(findPathParametersDom);
        Path resultPath = algorithm.returnShortestPath(searchParams);
        CardDom cardDom = pathMapper.createCardDomFromPath(resultPath);
        return List.of(cardDom);
    }
}
