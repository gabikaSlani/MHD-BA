<template>
  <div class="stop">
    <div class="stop-name">
      {{stop.name.name}}
    </div>
    <div class="routes">
      <div v-for="route of stop.routes" v-bind:key="route.name" class="route"
          :class="{red: route.mode === 'tramway',
                   blue: route.mode === 'bus',
                   green: route.mode === 'trolleybus'}">
        {{route.name}}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'StopListCell',
  props: {
    stop: {
      type: Object,
    },
  },
};
</script>

<style scoped>
  .stop {
    padding: 8px 15px;
    text-align: left;
    cursor: pointer;
  }

  .stop:focus, .stop:hover {
    background-color: #eef0f2;
  }

  .stop-name {
    font-size: 20px;
    padding: 3px 0;
  }

  .routes {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
  }

  .route {
    padding: 1px 3px;
    font-weight: bold;
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
