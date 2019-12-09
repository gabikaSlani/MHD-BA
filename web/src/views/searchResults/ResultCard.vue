<template>
  <div class="result-card">
    <div class="header">
      {{card.duration}} min, {{card.distance}} km
    </div>
    <div class="body">
      <Action v-for="(action, index) of card.actions"
              v-bind:key="index"
              :action="action">
      </Action>
    </div>
  </div>
</template>

<script>
import Action from './Action.vue';

export default {
  name: 'ResultCard',
  components: { Action },
  props: {
    card: {
      type: Object,
    },
  },
};
</script>

<style scoped>

  .result-card {
    margin-bottom: 15px;
  }

  .header {
    height: 23px;
    border: 1px black solid;
    background-color: #335c81;
    font-weight: bold;
    color: white;
    display: flex;
    align-items: center;
    flex-direction: row;
    justify-content: flex-end;
    padding-right: 12px;
    font-size: 16px;
  }

  .body {
    background-color: white;
    border: 1px black solid;
    border-top: none;
  }

</style>
