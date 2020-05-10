/* eslint no-shadow: ["error", { "allow": ["state"] }] */

const state = {
  maxNumberOfTransfers: 2,
  maxTimeOfWalking: 8,
  minTimeForTransfer: 1,
  onlyLowFloor: false,
  preferencesOptions: {
    maxNumberOfTransfers: [0, 1, 2, 3],
    maxTimeOfWalking: [1, 2, 3, 4, 5, 6, 8, 10],
    minTimeForTransfer: [1, 2, 3, 4, 5],
  },
};

const getters = {
  getMaxNumberOfTransfers(state) {
    return state.maxNumberOfTransfers;
  },
  getMaxTimeOfWalking(state) {
    return state.maxTimeOfWalking;
  },
  getMinTimeForTransfer(state) {
    return state.minTimeForTransfer;
  },
  getOnlyLowFloor(state) {
    return state.onlyLowFloor;
  },
  getPreferencesOptions(state) {
    return state.preferencesOptions;
  },
};

const mutations = {
  setMaxNumberOfTransfers(state, maxNumberOfTransfers) {
    state.maxNumberOfTransfers = maxNumberOfTransfers;
  },
  setMaxTimeOfWalking(state, maxTimeOfWalking) {
    state.maxTimeOfWalking = maxTimeOfWalking;
  },
  setMinTimeForTransfer(state, minTimeForTransfer) {
    state.minTimeForTransfer = minTimeForTransfer;
  },
  setOnlyLowFloor(state, onlyLowFloor) {
    state.onlyLowFloor = onlyLowFloor;
  },
};

const actions = {
  loadPreferences(state) {
    const preferencesString = window.localStorage.getItem('preferences');
    if (preferencesString === null) {
      return Promise.resolve(true);
    }
    const preferences = JSON.parse(preferencesString);
    state.commit('setMaxNumberOfTransfers', preferences.maxNumberOfTransfers);
    state.commit('setMaxTimeOfWalking', preferences.maxTimeOfWalking);
    state.commit('setMinTimeForTransfer', preferences.minTimeForTransfer);
    state.commit('setOnlyLowFloor', preferences.onlyLowFloor);
    return Promise.resolve(true);
  },
  savePreferences(state) {
    const preferences = {
      maxNumberOfTransfers: state.getters.getMaxNumberOfTransfers,
      maxTimeOfWalking: state.getters.getMaxTimeOfWalking,
      minTimeForTransfer: state.getters.getMinTimeForTransfer,
      onlyLowFloor: state.getters.getOnlyLowFloor,
    };
    window.localStorage.setItem('preferences', JSON.stringify(preferences));
    return Promise.resolve(true);
  },
};


export default {
  state,
  getters,
  mutations,
  actions,
};
