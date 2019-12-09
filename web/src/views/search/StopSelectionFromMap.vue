<template>
  <div class="stop-selection-map-dialog" v-if="shown">
    <div v-if="!currentLocation" class="stop-selection-map">Musíte povoliť polohu.</div>
    <gmap-map v-if="currentLocation" :center="currentLocation" :zoom="12"
              class="stop-selection-map">
      <gmap-marker :key="stop.id"
                   v-for="stop in stops"
                   @click="select(stop)"
                   :position="coordsFromStringToNumber(stop.coords)"
                   :icon="markerOptions">
      </gmap-marker>
      <gmap-info-window :key="stop.name.name"
                        v-for="stop in stops"
                        :options="infoOptions"
                        :position="coordsFromStringToNumber(stop.coords)"
                        :opened="true">{{stop.name.name}}
      </gmap-info-window>
    </gmap-map>
    <div class="stop-selection-map-back-button-div">
      <IconButton src="/assets/back.png" class="stop-selection-map-back-button"
                  @click.native="back"></IconButton>
    </div>
  </div>
</template>

<script>
import IconButton from '../../components/inputs/IconButton.vue';

const mapMarker = require('../../assets/busstop.png');

export default {
  name: 'StopSelectionFromMap',
  components: { IconButton },
  data() {
    return {
      shown: false,
      currentLocation: undefined,
      markerOptions: {
        url: mapMarker,
        scaledSize: {
          width: 30, height: 32, f: 'px', b: 'px',
        },
      },
      infoOptions: {
        pixelOffset: {
          width: 0,
          height: -30,
        },
      },
    };
  },
  props: {
    stops: {
      type: Array,
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

  .stop-selection-map-back-button-div {
    height: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: black;
  }

  .stop-selection-map-back-button {
    cursor: pointer;
  }

  .gm-ui-hover-effect {
    display: none !important;
  }
</style>
