<template>
  <div class="route-list">
    <PreFetcher :methods="['fetchRoutes']">
      <Header title="Linky" routeNameToRedirect="menu"/>
      <div class="body">
        <Route v-for="(route, index) of routes"
               v-bind:key="index"
               :route="route"
               @click.native="showRouteDetails(route)"></Route>
      </div>
    </PreFetcher>
  </div>
</template>

<script>
import Header from '../../components/containers/Header.vue';
import PreFetcher from '../../components/PreFetcher.vue';
import Route from './Route.vue';

export default {
  name: 'RouteList',
  components: {
    Route, Header, PreFetcher,
  },
  data() {
    return {
      modeSortOrder: {
        tramway: 1,
        trolleybus: 2,
        bus: 3,
        nothing: 4,
      },
    };
  },
  computed: {
    routes() {
      const routes = this.$store.getters.getRoutes;
      if (routes) {
        routes.sort((r1, r2) => {
          const r1ModeOrder = (!r1.routeInfo || !r1.routeInfo.mode)
            ? this.modeSortOrder.nothing
            : this.modeSortOrder[r1.routeInfo.mode];
          const r2ModeOrder = (!r2.routeInfo || !r2.routeInfo.mode)
            ? this.modeSortOrder.nothing
            : this.modeSortOrder[r2.routeInfo.mode];
          if (r1ModeOrder < r2ModeOrder) {
            return -1;
          }
          if (r1ModeOrder > r2ModeOrder) {
            return 1;
          }
          const r1Name = r1.routeInfo.name;
          const r2Name = r2.routeInfo.name;
          return r1Name > r2Name ? 1 : -1;
        });
      }
      return routes;
    },
  },
  methods: {
    showRouteDetails(route) {
      this.$router.push(`/route-list/details/${route.id}`);
    },
  },
};
</script>

<style scoped>
  .route-list {
    flex: 1;
    display: flex;
  }

  .body {
    background-color: white;
  }

  .body {
    height: 100%;
    overflow-y: hidden;
    padding-bottom: 0 !important;
  }

  @media (min-width: 400px) {
    .body {
      padding-top: 20px;
      margin: 0 calc((100% - 500px)/2);
      flex: 1;
    }
  }

  @media (max-width: 400px) {
    .body {
      padding: 10px 0;
      flex: 1;
    }
  }
</style>
