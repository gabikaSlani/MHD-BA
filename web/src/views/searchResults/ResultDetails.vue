<template>
  <div class="search-results">
    <Header title="Detail spojenia"/>
    <ViewBody :tight="true">
      <div class="wrapper-card" v-if="card">
        <div class="wrapper-card-header">
          <div class="wrapper-card-header-row">
            <div>{{card.startStop.name}}</div>
            <div class="spacer"></div>
            <div>{{card.date}}</div>
          </div>
          <div class="wrapper-card-header-row">
            <div>{{card.endStop.name}}</div>
            <div class="spacer"></div>
            <div>{{card.duration}} min</div>
          </div>
        </div>
        <div class="wrapper-card-body">
          <ActionDetail v-for="(action, index) of card.actions"
                        v-bind:key="index"
                        :first="index === 0"
                        :last="index === card.actions.length - 1"
                        :action="action"
                        :card="card">
          </ActionDetail>
        </div>
      </div>
    </ViewBody>
  </div>
</template>

<script>
import Header from '../../components/containers/Header.vue';
import ViewBody from '../../components/containers/ViewBody.vue';
import ActionDetail from './ActionDetail.vue';

export default {
  name: 'ResultDetails',
  components: {
    ActionDetail, Header, ViewBody,
  },
  props: {
    card: {
      type: Object,
    },
  },
  mounted() {
    if (this.card === undefined) {
      this.$router.push('/search');
    }
    window.scrollTo(0, 0);
  },
};
</script>

<style scoped>

  .wrapper-card {
    margin: 5px 15px;
  }

  .wrapper-card-header {
    background-color: #335c81;
    border: 2px solid #13293d;
    color: white;
    padding: 3px;
  }

  .wrapper-card-header-row {
    display: flex;
    flex-direction: row;
    font-weight: bold;
    padding: 1px 5px;
    font-size: 17px;
  }

  .spacer {
    flex: 1;
  }

  .wrapper-card-body {
    background-color: #e6e8e6;
    border: #13293d 2px solid;
    border-top: none;
    padding: 8px 8px;
  }

</style>
