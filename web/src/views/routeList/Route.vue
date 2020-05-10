<template>
  <div class="route">
    <div class="row1" :class="{red: route.routeInfo.mode === 'tramway',
                               blue: route.routeInfo.mode === 'bus',
                               green: route.routeInfo.mode === 'trolleybus'}">
      <img :src="modeImageUrl()" alt="mode-image-url"
             v-if="route.routeInfo" class="mode-img"/>
      {{modeName()}}
      {{route.routeInfo.name}}
    </div>
    <div class="row2">
      {{route.startStopName.name}}
      <img src="/assets/double_arrow.png" alt="double-arrow" class="double-arrow"/>
      {{route.endStopName.name}}
    </div>
  </div>
</template>

<script>
export default {
  name: 'Route',
  props: {
    route: {
      type: Object,
    },
  },
  methods: {
    modeImageUrl() {
      if (this.route.routeInfo.mode === 'bus') {
        return '/assets/bus.png';
      }
      if (this.route.routeInfo.mode === 'tramway') {
        return '/assets/tramway.png';
      }
      if (this.route.routeInfo.mode === 'trolleybus') {
        return '/assets/trolleybus.png';
      }
      return undefined;
    },
    modeName() {
      if (this.route.routeInfo.mode === 'bus') {
        return 'Bus';
      }
      if (this.route.routeInfo.mode === 'tramway') {
        return 'Električka';
      }
      if (this.route.routeInfo.mode === 'trolleybus') {
        return 'Trolejbus';
      }
      return undefined;
    },
  },
};
</script>

<style scoped>

  .route {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 6px 15px;
  }

  .row1 {
    margin-bottom: 2px;
    font-size: 21px;
  }

  .row2 {
    font-size: 19px;
    display: flex;
    justify-content: left;
    align-items: center;
    flex-wrap: wrap;
  }

  .mode-img {
    height: 19px;
  }

  .double-arrow {
    height: 6px;
    margin: 0 5px;
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

</style>
