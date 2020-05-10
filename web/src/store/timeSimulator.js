/* eslint no-shadow: ["error", { "allow": ["state"] }] */
import axios from 'axios';
import api from '../api';

const state = {
  actualDateTime: undefined,
};

const getters = {
  getActualDateTime(state) {
    return state.actualDateTime;
  },
};

const mutations = {
  setActualDateTime(state, actualDateTime) {
    state.actualDateTime = actualDateTime;
  },
};

const addSecond = (dateTime) => {
  const date = new Date(dateTime.year, dateTime.month - 1,
    dateTime.day, dateTime.hour, dateTime.minute, dateTime.second);
  const datePlus1Second = new Date(date.getTime() + 1000);
  return {
    day: datePlus1Second.getDate(),
    month: datePlus1Second.getMonth() + 1,
    year: datePlus1Second.getFullYear(),
    hour: datePlus1Second.getHours(),
    minute: datePlus1Second.getMinutes(),
    second: datePlus1Second.getSeconds(),
  };
};

const actions = {
  fetchActualDateTime(state) {
    return axios.get(`${api.url}/getActualDateTime`).then((result) => {
      state.commit('setActualDateTime', result.data);
      setInterval(() => {
        const dateTime = state.getters.getActualDateTime;
        state.commit('setActualDateTime', addSecond(dateTime));
      }, 1000);
    });
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
