<template>
  <div class="stop">
    <div class="stop-name">
      {{stop.name.name}}
    </div>
    <div class="routes">
      <div v-for="route of routes" v-bind:key="route.name" class="route"
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
  data() {
    return {
      modeSortOrder: {
        tramway: 1,
        trolleybus: 2,
        bus: 3,
        nothing: 4,
      },
    };
  },
  props: {
    stop: {
      type: Object,
    },
  },
  computed: {
    routes() {
      if (this.stop === undefined || this.stop.routes === undefined) {
        return [];
      }
      const routes = [];
      for (let i = 0; i < this.stop.routes.length; i += 1) {
        routes.push(this.stop.routes[i]);
      }
      routes.sort((r1, r2) => {
        const r1ModeOrder = (!r1.mode)
          ? this.modeSortOrder.nothing
          : this.modeSortOrder[r1.mode];
        const r2ModeOrder = (!r2.mode)
          ? this.modeSortOrder.nothing
          : this.modeSortOrder[r2.mode];
        if (r1ModeOrder < r2ModeOrder) {
          return -1;
        }
        if (r1ModeOrder > r2ModeOrder) {
          return 1;
        }
        const r1Name = r1.name;
        const r2Name = r2.name;
        return r1Name > r2Name ? 1 : -1;
      });
      return routes;
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
