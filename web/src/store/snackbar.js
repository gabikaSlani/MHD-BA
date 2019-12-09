/* eslint no-shadow: ["error", { "allow": ["state"] }] */
import Vue from 'vue';
import SnackBar from '../components/SnackBar.vue';

const state = {};

const getters = {};

const mutations = {};

const actions = {
  // eslint-disable-next-line no-unused-vars
  info(state, config) {
    const ComponentClass = Vue.extend(SnackBar);
    const instance = new ComponentClass();
    instance.$slots.default = [config.message ? config.message : ''];
    instance.$mount();
    const app = document.getElementById('app');
    app.appendChild(instance.$el);

    const duration = config.duration ? config.duration : 3000;

    setTimeout(() => { app.removeChild(instance.$el); }, duration);
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
