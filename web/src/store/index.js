import Vue from 'vue';
import axios from 'axios';
import Vuex from 'vuex';
import snackbar from './snackbar';
import searchHistory from './searchHistory';
import preferences from './preferences';
import timeSimulator from './timeSimulator';
import api from '../api';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    stops: undefined,
    stopAreas: undefined,
    routes: undefined,
    lastFindPathsRequest: undefined,
    results: undefined,
  },
  getters: {
    getStops(state) {
      return state.stops;
    },
    getStopAreas(state) {
      return state.stopAreas;
    },
    getRoutes(state) {
      return state.routes;
    },
    getLastFindPathsRequest(state) {
      return state.lastFindPathsRequest;
    },
    getResults(state) {
      return state.results;
    },
  },
  mutations: {
    setStops(state, stops) {
      state.stops = stops;
    },
    setStopAreas(state, stopAreas) {
      state.stopAreas = stopAreas;
    },
    setRoutes(state, routes) {
      state.routes = routes;
    },
    setLastFindPathsRequest(state, lastFindPathsRequest) {
      state.lastFindPathsRequest = lastFindPathsRequest;
    },
    setResults(state, results) {
      state.results = results;
    },
  },
  actions: {
    findPaths(state, request) {
      state.commit('setLastFindPathsRequest', request);
      return axios
        .post(`${api.url}/findPaths`, request)
        .then(response => response)
        .catch(error => error.response);
    },
    fetchStops(state) {
      if (state.getters.getStops !== undefined) {
        return Promise.resolve();
      }
      return axios.get(`${api.url}/getAllStops`).then((response) => {
        state.commit('setStops', response.data);
      });
    },
    fetchStopAreas(state) {
      if (state.getters.getStopAreas !== undefined) {
        return Promise.resolve();
      }
      return axios.get(`${api.url}/getAllStopAreas`).then((response) => {
        state.commit('setStopAreas', response.data);
      });
    },
    fetchRoutes(state) {
      if (state.getters.getRoutes !== undefined) {
        return Promise.resolve();
      }
      return axios.get(`${api.url}/getAllRoutes`).then((response) => {
        state.commit('setRoutes', response.data);
      });
    },
    fetchRouteWithId(state, id) {
      return axios.get(`${api.url}/getRouteDetail?routeId=${id}`);
    },
  },
  modules: {
    snackbar, searchHistory, preferences, timeSimulator,
  },
});
