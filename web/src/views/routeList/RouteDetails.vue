<template>
  <div>
    <Header title="Detail linky"/>
    <ViewBody :tight="true">
      <div class="card">
        <div class="card-header">
          <div>{{buildModeName(route.routeInfo.mode)}} {{route.routeInfo.name}}</div>
          <div class="spacer"></div>
          <img src="/assets/routes-white.png"
               class="routes-img"
               @click="showRouteOnMap()"/>
        </div>
        <div class="body">
          <div class="list-header">
            <div class="list-header-item">Zóna</div>
            <div class="list-header-item">Na znamenie</div>
          </div>
          <div class="stop-list" v-if="route.stops">
            <div v-for="(stop, index) of route.stops"
                 v-bind:key="index"
                 class="stop">
              <div :class="{'bold': index === 0 || index === route.stops.length - 1}">
                {{stop.name.name}}
              </div>
              <div class="spacer"></div>
              <img src="/assets/hand-red.png" class="on-request-img" v-if="stop.onRequest"/>
              <div class="zone">{{stop.zone}}</div>
            </div>
          </div>
          <div v-if="!route.stops || route.stops.length === 0" class="no-stops-message">
            Spoj nemá žiadne zastávky.
          </div>
        </div>
      </div>
    </ViewBody>
  </div>
</template>

<script>
import Header from '../../components/containers/Header.vue';
import ViewBody from '../../components/containers/ViewBody.vue';

export default {
  name: 'RouteDetails',
  components: {
    Header, ViewBody,
  },
  props: {
    route: {
      type: Object,
    },
  },
  mounted() {
    if (this.route === undefined) {
      this.$router.push('/route-list');
    }
  },
  methods: {
    buildModeName(modeCode) {
      if (modeCode === 'bus') {
        return 'Bus';
      }
      if (modeCode === 'tramway') {
        return 'Električka';
      }
      if (modeCode === 'trolleybus') {
        return 'Trolejbus';
      }
      return undefined;
    },
    showRouteOnMap() {
      this.$router.push({ name: 'route-on-map', params: { route: this.route } });
    },
  },
};
</script>

<style scoped>
  .card {
    padding: 0 10px;
  }

  .card-header {
    background-color: #335c81;
    border: 2px #13293d solid;
    color: white;
    padding: 8px 15px;
    font-size: 20px;
    display: flex;
    flex-direction: row;
    align-items: center;
  }

  .spacer {
    flex: 1;
  }

  .routes-img {
    height: 25px;
    cursor: pointer;
  }

  .body {
    background-color: white;
    border: solid 2px #13293d;
    border-top: none;
    padding-top: 5px;
    padding-bottom: 5px;
  }

  .list-header {
    display: flex;
    flex-direction: row-reverse;
    align-items: center;
  }

  .list-header-item {
    color: #b82c2e;
    font-size: 18px;
    font-weight: bold;
    margin: 5px 15px 5px 0;
  }

  .stop {
    display: flex;
    flex-direction: row;
    padding: 3px 15px;
  }

  .zone {
    width: 57px;
    text-align: right;
  }

  .on-request-img {
    height: 20px;
  }

  .no-stops-message {
    text-align: left;
    padding: 8px 15px;
  }

  .bold {
    font-weight: bold;
  }
</style>
