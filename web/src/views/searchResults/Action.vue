<template>
  <div class="action">
    <div class="row1">
      <div class="row-image-wrapper">
        <img :src="tripImageUrl" v-if="action.type === 'trip'" class="row1-image"/>
        <div v-if="action.type === 'trip'" class="route-name"
             :class="{red: action.trip.routeInfo.mode === 'tramway',
                      blue: action.trip.routeInfo.mode === 'bus',
                      green: action.trip.routeInfo.mode === 'trolleybus'}">
          {{action.trip.routeInfo.name}}
        </div>
        <img src="/assets/walking.png" v-if="action.type === 'walking'" class="row1-image"/>
      </div>
      <div v-if="action.type === 'trip'" class="stops-info">
        <div class="stop-info">
          {{action.trip.tripStops[0].name.name}}
          <div class="spacer"></div>
          {{action.trip.tripStops[0].departureTime}}
        </div>
        <div class="stop-info">
          {{action.trip.tripStops[action.trip.tripStops.length-1].name.name}}
          <div class="spacer"></div>
          {{action.trip.tripStops[action.trip.tripStops.length-1].departureTime}}
        </div>
      </div>
      <div v-if="action.type === 'walking'">
        Presun {{action.walkingTime}} min.
      </div>
    </div>
    <div class="row2" v-if="action.type === 'trip'">
      <div class="row-image-wrapper">
        <img src="/assets/handicap.png" alt="lowStoried" v-if="action.trip.lowStoried"
             class="lowstoried-image"/>
      </div>
      <div class="delay" v-if="action.type === 'trip' && action.trip">
        <span v-if="!action.trip.delay || action.trip.delay === 0" class="green bold">včas</span>
        <span v-if="action.trip.delay > 0" class="red bold">
          meškanie {{action.trip.delay}} min.
        </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Action',
  props: {
    action: {
      type: Object,
    },
  },
  computed: {
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
    width: 100%;
    font-size: 17px;
    padding-bottom: 7px;
  }

  .row1 {
    display: flex;
    flex-direction: row;
    padding: 7px 12px 0 0;
  }

  .row2 {
    display: flex;
    flex-direction: row;
  }

  .row-image-wrapper {
    width: 70px;
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: center;
  }

  .row1-image {
    height: 20px;
  }

  .lowstoried-image {
    height: 15px;
  }

  .stops-info {
    flex: 1;
  }

  .stop-info {
    display: flex;
  }

  .route-name {
    font-weight: bold;
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
</style>
