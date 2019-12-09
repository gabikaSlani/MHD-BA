<template>
  <div class="snackbar" :class="{shown: shown, hidden: !shown}">
    <div class="message"><slot/></div>
    <div class="close-btn" @click="close">X</div>
  </div>
</template>

<script>
export default {
  name: 'SnackBar',
  data() {
    return {
      shown: true,
    };
  },
  methods: {
    close() {
      this.shown = false;
      setTimeout(() => {
        this.$destroy();
        this.$el.parentNode.removeChild(this.$el);
      }, 500);
    },
  },
};
</script>

<style scoped>
  .snackbar {
    display: inline-flex;
    min-width: 250px;
    margin-left: -125px;
    background-color: #000000;
    color: #fff;
    text-align: left;
    border-radius: 3px;
    padding: 12px;
    position: fixed;
    z-index: 1000;
    left: 50%;
    bottom: 30px;
  }

  .shown {
    -webkit-animation: fadein 0.5s;
    animation: fadein 0.5s;
  }

  .hidden {
    -webkit-animation: fadeout 0.5s;
    animation: fadeout 0.5s;
  }

  .message {
    flex: 1;
  }

  .close-btn {
    flex: 0;
    cursor: pointer;
  }

  @-webkit-keyframes fadein {
    from {bottom: 0; opacity: 0;}
    to {bottom: 30px; opacity: 1;}
  }

  @keyframes fadein {
    from {bottom: 0; opacity: 0;}
    to {bottom: 30px; opacity: 1;}
  }

  @-webkit-keyframes fadeout {
    from {bottom: 30px; opacity: 1;}
    to {bottom: 0; opacity: 0;}
  }

  @keyframes fadeout {
    from {bottom: 30px; opacity: 1;}
    to {bottom: 0; opacity: 0;}
  }
</style>
