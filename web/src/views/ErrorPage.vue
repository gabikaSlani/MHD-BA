<template>
  <div class="body">
    <Header title="Error" routeNameToRedirect="menu"/>
    <ViewBody>
      <Card class="card">
        <div class="error-text">
          Nastala chyba <br/>
          Skontrolujte svoje internetové pripojenie a skúste znovu.
        </div>
        <button class="button" @click="back">Skúsiť znovu</button>
      </Card>
    </ViewBody>
  </div>
</template>

<script>
import ViewBody from '../components/containers/ViewBody.vue';
import Card from '../components/containers/Card.vue';
import Header from '../components/containers/Header.vue';

export default {
  name: 'ErrorPage',
  components: {
    Header, Card, ViewBody,
  },
  methods: {
    back() {
      this.$store.dispatch('fetchActualDateTime').then(() => {
        this.$router.back();
      });
    },
  },
};
</script>

<style scoped>
  .body {
    flex: 1;
  }

  .card {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 50px;
  }

  .error-text {
    font-weight: bold;
    font-size: 18px;
    text-align: center;
  }

  .button {
    background-color:#13293d;
    border-radius: 3px;
    border: 1px solid #13293d;
    display: inline-block;
    cursor: pointer;
    color: #ffffff;;
    padding: 6px 24px;
    text-decoration: none;
    text-shadow: 0 1px 0 rgb(51, 92, 129);
    margin-top: 20px;
    font-size: 18px;
  }
  .button:hover {
    background-color: rgb(51, 92, 129);
  }
  .button:active {
    position:relative;
    top:1px;
  }

</style>
