<template>
  <div class="route-list">
    <PreFetcher :methods="['fetchRoutes']">
      <Header title="Linky" routeNameToRedirect="menu"/>
      <ViewBody :tight="true" class="body">
        <Route v-for="(route, index) of routes"
               v-bind:key="index"
               :route="route"
               @click.native="showRouteDetails(route)"></Route>
      </ViewBody>
    </PreFetcher>
  </div>
</template>

<script>
import Header from '../../components/containers/Header.vue';
import ViewBody from '../../components/containers/ViewBody.vue';
import PreFetcher from '../../components/PreFetcher.vue';
import Route from './Route.vue';

export default {
  name: 'RouteList',
  components: {
    Route, Header, ViewBody, PreFetcher,
  },
  computed: {
    routes() {
      return this.$store.getters.getRoutes;
    },
  },
  methods: {
    showRouteDetails(route) {
      this.$router.push({ name: 'route-details', params: { route } });
    },
  },
};
</script>

<style scoped>
  .body {
    background-color: white;
  }
</style>
