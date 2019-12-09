<template>
  <Card v-bind:class="{ cardSquare: !rect, cardRect: rect }"
        :color="color" :bgColor="bgColor">
    <div class="content">
      <slot></slot>
    </div>
  </Card>
</template>

<script>
import Card from './Card.vue';

export default {
  name: 'SquareCard',
  components: { Card },
  props: {
    rect: {
      type: Boolean,
      default: false,
    },
    bgColor: {
      type: String,
    },
    color: {
      type: String,
    },
  },
};
</script>

<style scoped>
  .cardSquare {
       position: relative;
       flex: 1;
       padding: 0;
       cursor: pointer;
     }

  .cardSquare:after {
    content: "";
    display: block;
    padding-bottom: 100%;
  }

  .cardSquare:hover {
    transform: scale(1.02);
  }

  .cardRect {
    position: relative;
    flex: 1;
    padding: 0;
    cursor: pointer;
  }

  .cardRect:after {
    content: "";
    display: block;
    padding-bottom: 50%;
  }

  .cardRect:hover {
    transform: scale(1.02);
  }

  .content {
    position: absolute;
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: center;
    width: 100%;
    height: 100%;
  }
</style>
