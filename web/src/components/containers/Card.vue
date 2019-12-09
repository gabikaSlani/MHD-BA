<template>
  <div class="card" :style="style">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'Card',
  props: {
    bgColor: {
      type: String,
      default: '#e7e4e4',
    },
    color: {
      type: String,
      default: 'black',
    },
  },
  computed: {
    style() {
      return `background-color: ${this.bgColor}; color: ${this.color};`;
    },
  },
};
</script>

<style scoped>
  .card {
    padding: 20px;
    border-radius: 12px;
    box-shadow: 6px 6px 6px rgba(0, 0, 0, .3);
    min-height: 100px;
    min-width: 100px;
  }
</style>
