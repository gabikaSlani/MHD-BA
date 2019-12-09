<template>
  <div>
    <Input @click.native="showDialog" :value="text"/>
    <div class="dialog" :class="{'dialog-hidden': !dialogShown}">
      <ViewBody :tight="true" :paddingTop="false">
        <div class="header">
          <Input placeholder="Odkiaľ" v-model="searchValue"/>
          <div class="header-icons">
            <img src="/assets/current-location.png" alt="current-location" class="header-icon"
                 v-if="currentLocationOption" @click="pickCurrentLocation"/>
            <img src="/assets/map.png" alt="map" class="header-icon"
                 @click="pickLocationFromMap"/>
          </div>
        </div>
        <div class="stop-list">
          <template v-for="stop of filteredStops">
            <StopListCell :stop="stop"
                          @click.native="stopSelected(stop)"
                          v-bind:key="stop.id">
            </StopListCell>
          </template>
        </div>
      </ViewBody>
    </div>
    <StopSelectionFromMap :stops="stops" ref="stopSelectionFromMap"
                          @change="locationPickedFromMap($event)"/>
  </div>
</template>

<script>
import Input from '../../components/inputs/Input.vue';
import StopListCell from './StopListCell.vue';
import StopSelectionFromMap from './StopSelectionFromMap.vue';
import ViewBody from '../../components/containers/ViewBody.vue';

export default {
  name: 'StopSelection',
  components: {
    ViewBody, StopSelectionFromMap, StopListCell, Input,
  },
  data() {
    return {
      text: '',
      dialogShown: false,
      mapShown: false,
      searchValue: '',
    };
  },
  props: {
    value: {
      type: Object,
    },
    currentLocationOption: {
      type: Boolean,
      default: true,
    },
  },
  watch: {
    value(val) {
      if (val === undefined) {
        this.text = '';
      } else if (val.name === undefined && val.coords !== undefined) {
        this.text = 'Aktuálna poloha';
      } else {
        this.text = val.name.name;
      }
    },
  },
  computed: {
    stops() {
      return this.$store.getters.getStops;
    },
    filteredStops() {
      if (this.stops === undefined) {
        return [];
      }
      return this.stops.filter(s => s.name.name.toLowerCase().includes(this.searchValue.trim()));
    },
  },
  methods: {
    showDialog() {
      this.dialogShown = true;
    },
    hideDialog() {
      this.dialogShown = false;
    },
    stopSelected(stop) {
      this.hideDialog();
      this.$emit('input', stop);
    },
    pickCurrentLocation() {
      navigator.geolocation.getCurrentPosition((position) => {
        const currentLocation = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };
        this.$emit('input', { coords: currentLocation });
        this.hideDialog();
      }, () => {
        this.$store.dispatch('info', { message: 'Musíte povoliť polohu.' });
      });
    },
    pickLocationFromMap() {
      this.$refs.stopSelectionFromMap.shown = true;
    },
    locationPickedFromMap(stop) {
      this.$refs.stopSelectionFromMap.shown = false;
      this.$emit('input', stop);
      this.hideDialog();
    },
  },
};
</script>

<style scoped>
  .dialog {
    position: fixed;
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
    background-color: white;
  }

  .dialog-hidden {
    display: none;
  }

  .header {
    border-bottom: 2px black solid;
    padding: 5px 15px;
    display: flex;
    flex-direction: row;
    height: 40px;
  }

  .header-icons {
    height: 100%;
    flex: 0;
    display: flex;
    flex-direction: row;
    align-items: center;
  }

  .header-icon {
    height: 80%;
    margin-right: 8px;
    cursor: pointer;
  }

  .stop-list {
    height: 100%;
    overflow-y: auto;
  }
</style>
