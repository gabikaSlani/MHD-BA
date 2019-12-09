<template>
  <ViewBody class="menu">
    <div class="title">MHD BA</div>
    <div class="nav">
      <div class="nav-links">
        <SquareCard color="white" bgColor="#bf3134" @click.native="redirectTo('/search')">
          <img src="/assets/search.png" alt="search icon" class="icon"/>
          <div class="nav-name">Vyhľadanie<br/>spojenia</div>
        </SquareCard>
        <div class="space"></div>
        <SquareCard color="white" bgColor="#335c81"  @click.native="redirectTo('/route-list')">
          <img src="/assets/routes.png" alt="routes icon" class="icon"/>
          <div class="nav-name">Zoznam<br/>liniek</div>
        </SquareCard>
      </div>
    </div>
    <div class="fmfi">FMFI UK</div>
  </ViewBody>
</template>

<script>
import SquareCard from '../components/containers/SquareCard.vue';
import ViewBody from '../components/containers/ViewBody.vue';

export default {
  name: 'Menu',
  components: { ViewBody, SquareCard },
  methods: {
    redirectTo(route) {
      this.$router.push(route);
    },
  },
};
</script>

<style scoped>
  .menu {
    background-color: #13293d;
    display: flex;
    flex-direction: column;
    flex: 1;
  }

  .title {
    color: white;
    width: 100%;
    font-size: 70px;
    font-weight: bold;
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .nav {
    width: 100%;
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  .nav-links {
    display: inline-flex;
    width: 100%;
  }

  .space {
    width: 5vw;
  }

  .icon {
    height: 40%;
  }

  .nav-name {
    margin-top: 10%;
    font-weight: bold;
    font-size: 20px;
  }

  .fmfi {
    width: 100%;
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: flex-end;
    color: white;
    font-weight: bold;
    font-size: 18px;
    padding-bottom: 30px;
  }
</style>
