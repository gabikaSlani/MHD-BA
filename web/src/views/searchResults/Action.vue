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
          <div v-if="action.trip.lowFloor === true" class="row-image-wrapper1">
            <img src="/assets/handicap.png" alt="lowFloor" class="lowstoried-image"/>
          </div>
        </div>
        <img src="/assets/walking.png" v-if="action.type === 'walking'" class="row1-image"/>
      </div>
      <div v-if="action.type === 'trip'" class="stops-info">
        <div class="stop-info">
          {{action.startStop.name}}
          <div class="spacer"></div>
          <strong class="left-padding"
                  :class="{red: action.trip.leftTheOriginStop === true && action.trip.delay > 0,
                           green: action.trip.leftTheOriginStop === true && action.trip.delay === 0
                  }">
            {{action.trip.boardingTime}}
          </strong>
        </div>
        <div class="stop-info">
          {{action.endStop.name}}
          <div class="spacer"></div>
          <strong class="left-padding"
                  :class="{red: action.trip.leftTheOriginStop === true && action.trip.delay > 0,
                           green: action.trip.leftTheOriginStop === true && action.trip.delay === 0
                  }">
            {{action.trip.getOffTime}}
          </strong>
        </div>
      </div>
      <div v-if="action.type === 'walking'" class="walking-section">
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
        <template v-if="!first || !last">
          <template v-if="first && action.startStop != null">{{action.startStop.name}}
            &rarr; </template>
          <template v-if="first && action.startStop == null">Aktuálna lokalita &rarr; </template>
          <template class="walking">{{action.walkingTime}} min</template>
          <template v-if="last"> &rarr; {{action.endStop.name}}</template>
          <span class="spacer" v-if="last || first"></span>
          <strong v-if="first" class="left-padding">{{card.departureTime}}</strong>
          <strong v-if="last" class="left-padding">{{card.arrivalTime}}</strong>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Action',
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
    font-size: 16px;
    padding-bottom: 7px;
  }

  .walking-section {
    font-size: 16px;
    display: flex;
    align-items: center;
    flex: 1;
    text-align: left;
  }

  .walking {
    margin: 0 3px;
  }

  .row1 {
    display: flex;
    flex-direction: row;
    padding: 7px 10px 0 0;
  }

  .row-image-wrapper {
       width: 55px;
       display: flex;
       align-items: center;
       flex-direction: column;
       justify-content: center;
     }

  .row-image-wrapper1 {
    width: 50px;
    display: inline;
    align-items: center;
    flex-direction: column;
    justify-content: center;
  }

  .row1-image {
    height: 20px;
  }

  .lowstoried-image {
    height: 12px;
  }

  .stops-info {
    flex: 1;
    text-align: left;
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

  .left-padding{
    padding-left: 10px;
  }
</style>
