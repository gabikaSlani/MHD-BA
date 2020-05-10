<template>
  <div class="route-on-map">
    <gmap-map class="map" :center="mapCenter" :zoom="14" v-if="route && route.stops">
      <gmap-polyline v-bind:path="path"
                     v-bind:options="{ strokeColor:'red'}"/>
      <gmap-marker :key="stop.id"
                   v-for="stop in route.stops"
                   :position="coordsFromStringToNumber(stop.coords)"
                   :icon="markerOptions"/>
    </gmap-map>
    <div class="button-div">
      <IconButton src="/assets/back.png" class="back-button"
                  @click.native="back"></IconButton>
    </div>
  </div>
</template>

<script>
import IconButton from '../../components/inputs/IconButton.vue';

const mapMarker = require('../../assets/stopDot.png');

export default {
  name: 'RouteOnMap',
  components: {
    IconButton,
  },
  data() {
    return {
      markerOptions: {
        url: mapMarker,
        scaledSize: {
          width: 12, height: 12, f: 'px', b: 'px',
        },
      },
    };
  },
  props: {
    route: {
      type: Object,
    },
  },
  computed: {
    mapCenter() {
      if (!this.route || !this.route.stops) {
        return {};
      }
      const points = this.path;
      if (points.length % 2 === 0) {
        return this.twoPointsCenter(points[points.length / 2], points[points.length / 2 - 1]);
      }
      return points[Math.floor(points.length / 2)];
    },
    path() {
      if (!this.route || !this.route.stops) {
        return [];
      }
      return this.route.stops
        .map(stop => stop.coords)
        .map(coords => this.coordsFromStringToNumber(coords));
    },
  },
  mounted() {
    if (!this.route) {
      this.$router.push({ name: 'route-list' });
    }
  },
  methods: {
    back() {
      this.$router.back();
    },
    twoPointsCenter(point1, point2) {
      return { lat: (point1.lat + point2.lat) / 2, lng: (point1.lng + point2.lng) / 2 };
    },
    coordsFromStringToNumber(coords) {
      return {
        lat: Number(coords.lat),
        lng: Number(coords.lng),
      };
    },
  },
};
</script>

<style scoped>
  .route-on-map {
    position: fixed;
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
    display: flex;
    flex-direction: column;
  }

  .map {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .button-div {
    height: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: black;
  }

  .back-button {
    cursor: pointer;
  }
</style>
