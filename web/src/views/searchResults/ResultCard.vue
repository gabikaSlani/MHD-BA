<template>
  <div class="result-card">
    <div class="header">
      <div>{{cardDate}}</div>
      <div class="spacer"></div>
      <div>{{card.duration}} min</div>
    </div>
    <div class="body">
      <Action v-for="(action, index) of card.actions"
              v-bind:key="index"
              :first="index === 0"
              :last="index === card.actions.length - 1"
              :action="action" :card="card">
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
  computed: {
    cardDate() {
      if (this.card === undefined || this.card.date === undefined) {
        return '';
      }
      const pattern = /(\d{2})\.(\d{2})\.(\d{4})/;
      const parsedDate = new Date(this.card.date.replace(pattern, '$3-$2-$1'));
      const today = new Date();
      if (parsedDate.getFullYear() === today.getFullYear()
        && parsedDate.getMonth() === today.getMonth()
        && parsedDate.getDate() === today.getDate()) {
        return 'Dnes';
      }
      if (parsedDate.getFullYear() === today.getFullYear()
        && parsedDate.getMonth() === today.getMonth()
        && parsedDate.getDate() === today.getDate() + 1) {
        return 'Zajtra';
      }
      return this.card.date;
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
    padding: 0 12px;
    font-size: 16px;
  }

  .body {
    background-color: white;
    border: 1px black solid;
    border-top: none;
    padding: 5px 0;
  }

  .spacer {
    flex: 1;
  }

</style>
