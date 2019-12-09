<template>
  <div class="header">
    <div class="img-wrapper" @click="navigate()">
      <div class="img-circle">
        <img src="/assets/back.png" alt="arrow-back" class="back-img">
      </div>
    </div>
    <div class="title">{{title}}</div>
  </div>
</template>

<script>
export default {
  name: 'Header',
  props: {
    title: {
      type: String,
    },
    routeNameToRedirect: {
      type: String,
    },
  },
  methods: {
    navigate() {
      if (!this.routeNameToRedirect) {
        this.$router.back();
      } else {
        this.$router.push({ name: this.routeNameToRedirect });
      }
    },
  },
};
</script>

<style scoped>
  .header {
    background-color: #13293d;
    height: 70px;
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-start;
  }

  .img-wrapper {
    height: 100%;
    border-radius: 50%;
    display: flex;
    flex-direction: row;
    align-items: center;
  }

  .img-circle {
    height: 65%;
    margin: 0 5px;
    padding: 0 12px;
    border-radius: 50%;
    display: flex;
    flex-direction: row;
    align-items: center;
  }

  .img-circle:hover {
    background-color: rgba(198, 199, 196, 0.3) ;
  }

  .back-img {
    height: 40%;
  }

  .title {
    color: white;
    font-size: 24px;
  }
</style>
