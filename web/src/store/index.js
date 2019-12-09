import Vue from 'vue';
import axios from 'axios';
import Vuex from 'vuex';
import snackbar from './snackbar';
import searchHistory from './searchHistory';
import api from '../api';

import trips from './trips';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    stops: undefined,
    routes: undefined,
  },
  getters: {
    getStops(state) {
      return state.stops;
    },
    getRoutes(state) {
      return state.routes;
    },
  },
  mutations: {
    setStops(state, stops) {
      state.stops = stops;
    },
    setRoutes(state, routes) {
      state.routes = routes;
    },
  },
  actions: {
    findPaths() {
      return Promise.resolve(trips);
    },
    fetchStops(state) {
      if (state.getters.getStops !== undefined) {
        return Promise.resolve();
      }
      return axios.get(`${api.url}/getAllStops`).then((response) => {
        state.commit('setStops', response.data);
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
  },
  modules: {
    snackbar, searchHistory,
  },
});
