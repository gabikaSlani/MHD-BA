/* eslint no-shadow: ["error", { "allow": ["state"] }] */

const state = {
  lastSearches: [],
  selectedSearch: undefined,
};

const getters = {
  getLastSearches(state) {
    return state.lastSearches;
  },
  getSelectedSearch(state) {
    return state.selectedSearch;
  },
};

const mutations = {
  setLastSearches(state, lastSearches) {
    state.lastSearches = lastSearches;
  },
  setSelectedSearch(state, search) {
    state.selectedSearch = search;
  },
};

const helpers = {
  getStopById(id, stops) {
    for (let i = 0; i < stops.length; i += 1) {
      if (stops[i].id === id) {
        return stops[i];
      }
    }
    return undefined;
  },
};

const actions = {
  addSearchToHistory(state, request) {
    if (request.from.id === undefined || request.to.id === undefined) {
      return;
    }
    let lastSearches = window.localStorage.getItem('searchHistory');
    const objectToAdd = { from: request.from.id, to: request.to.id };
    if (!lastSearches) {
      window.localStorage.setItem('searchHistory', JSON.stringify([objectToAdd]));
      state.dispatch('computeLastSearches', {});
      return;
    }
    lastSearches = JSON.parse(lastSearches);
    lastSearches = lastSearches
      .filter(obj => !(obj.from === objectToAdd.from && obj.to === objectToAdd.to));
    lastSearches.push(objectToAdd);
    while (lastSearches.length > 5) {
      lastSearches.shift();
    }
    window.localStorage.setItem('searchHistory', JSON.stringify(lastSearches));
    state.dispatch('computeLastSearches', {});
  },
  computeLastSearches(state) {
    const lastSearches = window.localStorage.getItem('searchHistory');
    const stops = state.getters.getStopAreas;
    if (!lastSearches || !stops) {
      state.commit('setLastSearches', []);
      return;
    }
    const result = [];
    JSON.parse(lastSearches).forEach((search) => {
      const from = helpers.getStopById(search.from, stops);
      const to = helpers.getStopById(search.to, stops);
      if (from !== undefined && to !== undefined) {
        result.unshift({ from, to });
      }
    });
    state.commit('setLastSearches', result);
  },
  searchSelected(state, search) {
    state.commit('setSelectedSearch', search);
  },
};


export default {
  state,
  getters,
  mutations,
  actions,
};
