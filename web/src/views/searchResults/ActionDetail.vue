<template>
  <div class="action">
    <div v-if="action.type === 'walking'" class="walking-action">
      <img src="/assets/walking.png" class="walking-img"/>
      <div v-if="first && last" class="stops-info">
        <div class="stop-info">
          {{action.startStop.name}}
          <div class="spacer"></div>
          <strong class="left-padding">{{card.departureTime}}</strong>
        </div>
        <div class="stop-info">
          {{action.endStop.name}}
          <div class="spacer"></div>
          <strong class="left-padding">{{card.arrivalTime}}</strong>
        </div>
      </div>
      <template v-if="!(first && last)">
        <template v-if="first && action.startStop !== undefined">{{action.startStop.name}} &rarr;
        </template>
        <template v-if="first && action.startStop === undefined">Aktuálna lokalita &rarr;&nbsp;
        </template>
        <template class="walking">presun {{action.walkingTime}} min</template>
        <template v-if="last"> &rarr; {{action.endStop.name}}</template>
      </template>
    </div>
    <div v-if="action.type === 'trip'" class="trip-action">
      <div class="trip-action-header">
        <div class="trip-name"
             :class="{red: action.trip.routeInfo.mode === 'tramway',
                      blue: action.trip.routeInfo.mode === 'bus',
                      green: action.trip.routeInfo.mode === 'trolleybus'}">
          {{action.trip.routeInfo.name}}
        </div>
        <img :src="tripImageUrl()" class="trip-image"/>
        <img src="/assets/handicap.png" v-if="action.trip.lowFloor" class="handicap-image"/>
        <div class="spacer"></div>
        <div class="delay" v-if="action.type === 'trip' && action.trip">
          <span v-if="action.trip.delay === 0 && action.trip.leftTheOriginStop === true"
                class="green bold">včas</span>
          <span v-if="action.trip.delay > 0 && action.trip.leftTheOriginStop === true"
                class="red bold">
            meškanie {{action.trip.delay}} min
          </span>
        </div>
      </div>
      <div class="trip-action-body">
        <div v-for="(stop, index) of action.trip.tripStops"
             v-bind:key="index"
             class="stop"
             :class="{'bold': index === 0 || index === action.trip.tripStops.length - 1}">
          <div>{{stop.name.name}}</div>
          <div class="spacer"></div>
          <img src="/assets/hand.png" v-if="stop.onRequest" class="on-sign-img"/>
          <div class="left-margin zone">{{stop.zone}}</div>
          <div class="left-margin">{{stop.departureTime}}</div>
        </div>
        <div class="final-stop">
          &rarr; {{action.trip.finalStop.name}}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ActionDetail',
  props: {
    card: {
      type: Object,
    },
    action: {
      type: Object,
    },
    first: {
      type: Boolean,
      default: false,
    },
    last: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    tripImageUrl() {
      if (this.action.type !== 'trip' || this.action.trip === undefined) {
        return undefined;
      }
      if (this.action.trip.routeInfo.mode === 'bus') {
        return '/assets/bus.png';
      }
      if (this.action.trip.routeInfo.mode === 'tramway') {
        return '/assets/tramway.png';
      }
      if (this.action.trip.routeInfo.mode === 'trolleybus') {
        return '/assets/trolleybus.png';
      }
      return undefined;
    },
  },
};
</script>

<style scoped>

  .action {
    margin: 12px 0;
  }

  .walking-action {
    font-weight: bold;
    font-size: 16px;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
  }

  .walking-img {
    height: 28px;
    margin-right: 10px;
  }

  .walking {
    margin: 0 3px;
  }

  .trip-action {
    border: #13293d 2px solid;
    background-color: white;
  }

  .trip-action-header {
    display: flex;
    flex-direction: row;
    align-items: center;
    padding: 2px 10px;
    border-bottom: 2px #13293d solid;
  }

  .trip-name {
    font-size: 32px;
    font-weight: bold;
    margin-right: 10px;
  }

  .trip-image {
    height: 20px;
    margin-right: 8px;
  }

  .stops-info {
    flex: 1;
    text-align: left;
  }

  .stop-info {
    display: flex;
  }

  .handicap-image {
    height: 15px;
  }

  .trip-action-body {
    text-align: left;
    padding: 6px 10px;
  }

  .stop {
    display: flex;
    flex-direction: row;
    align-items: center;
    font-size: 16px;
    padding: 3px 0;
  }

  .final-stop {
    padding: 3px 0 3px 12px;
    font-size: 16px;
    font-style: italic;
  }

  .on-sign-img {
    height: 16px;
  }

  .left-margin {
    margin-left: 10px;
  }

  .spacer {
    flex: 1;
  }

  .red {
    color: #b82c2e;
  }

  .blue {
    color: #5386e4;
  }

  .green {
    color: #17bf12;
  }

  .bold {
    font-weight: bold;
  }

  .zone {
    font-weight: normal;
  }
</style>
