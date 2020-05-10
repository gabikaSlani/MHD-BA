<template>
  <div class="stop-selection-map-dialog" v-if="shown">
    <PreFetcher :methods="['fetchStops']">
      <div v-if="stops" class="condition-wrapper">
        <div v-if="!currentLocation" class="stop-selection-map">Musíte povoliť polohu.</div>
        <gmap-map v-if="currentLocation" :center="currentLocation" :zoom="zoom"
                  class="stop-selection-map"
                  @bounds_changed="saveBounds($event)">
          <gmap-marker :key="-stop.id" :animation="undefined"
                       v-for="stop in stopsInView"
                       @click="select(stop)"
                       :position="coordsFromStringToNumber(stop.coords)"
                       :icon="markerOptions">
          </gmap-marker>
        </gmap-map>
        <div class="stop-selection-map-back-button-div">
          <IconButton src="/assets/back.png" class="stop-selection-map-back-button"
                      @click.native="back"></IconButton>
        </div>
      </div>
    </PreFetcher>
  </div>
</template>

<script>
import IconButton from '../../components/inputs/IconButton.vue';
import PreFetcher from '../../components/PreFetcher.vue';

const mapMarker = require('../../assets/busstop.png');

export default {
  name: 'StopSelectionFromMap',
  components: { PreFetcher, IconButton },
  data() {
    return {
      shown: false,
      currentLocation: undefined,
      zoom: 11,
      markerOptions: {
        url: mapMarker,
        scaledSize: {
          width: 30,
          height: 32,
          f: 'px',
          b: 'px',
        },
      },
      infoOptions: {
        pixelOffset: {
          width: 0,
          height: -30,
        },
      },
      mapBounds: undefined,
    };
  },
  computed: {
    stops() {
      return this.$store.getters.getStops;
    },
    stopsInView() {
      if (this.mapBounds === undefined || this.stops === undefined) {
        return [];
      }
      const result = [];
      const rectangle = this.reduceRectangle(this.mapBounds);
      for (const stop of this.stops) {
        if (this.coordsInRectangle(stop.coords, rectangle)) {
          result.push(stop);
        }
      }
      return result;
    },
  },
  mounted() {
    navigator.geolocation.getCurrentPosition((position) => {
      this.currentLocation = {
        lat: position.coords.latitude,
        lng: position.coords.longitude,
      };
    });
  },
  methods: {
    saveBounds(bounds) {
      if (bounds === undefined) return;
      this.mapBounds = {
        top: bounds.Ua.j,
        right: bounds.Ya.j,
        bottom: bounds.Ua.i,
        left: bounds.Ya.i,
      };
    },
    coordsFromStringToNumber(coords) {
      return {
        lat: Number(coords.lat),
        lng: Number(coords.lng),
      };
    },
    select(stop) {
      this.$emit('change', stop);
    },
    back() {
      this.shown = false;
    },
    coordsInRectangle(coords, rect) {
      return coords.lat > rect.left && coords.lat < rect.right
        && coords.lng > rect.bottom && coords.lng < rect.top;
    },
    reduceRectangle(rectangle) {
      const MAX_RECT_AREA_SIZE = 0.01;
      const width = rectangle.right - rectangle.left;
      const height = rectangle.top - rectangle.bottom;
      const area = width * height;
      const result = Object.assign({}, rectangle);
      if (area > MAX_RECT_AREA_SIZE) {
        const newWidth = width * MAX_RECT_AREA_SIZE / area;
        const newHeight = height * MAX_RECT_AREA_SIZE / area;
        result.left += (width - newWidth) / 2;
        result.right -= (width - newWidth) / 2;
        result.top -= (height - newHeight) / 2;
        result.bottom += (height - newHeight) / 2;
      }
      return result;
    },
  },
};
</script>

<style>
  .stop-selection-map-dialog {
    position: fixed;
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
    background-color: white;
    display: flex;
    flex-direction: column;
  }

  .stop-selection-map{
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .condition-wrapper {
    height: 100%;
    display: flex;
    flex-direction: column;
  }

  .stop-selection-map-back-button-div {
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: black;
  }

  .stop-selection-map-back-button {
    cursor: pointer;
    height: 25px !important;
    width: 35px !important;
  }

  .gm-ui-hover-effect {
    display: none !important;
  }
</style>
