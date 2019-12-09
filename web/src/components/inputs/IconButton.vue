<template>
  <img :src="src" :alt="src" class="img"/>
</template>

<script>
export default {
  name: 'IconButton',
  props: {
    src: {
      type: String,
    },
  },
};
</script>

<style scoped>
  .img {
    width: 50px;
    height: 50px;
    cursor: pointer;
  }

  .img:hover {
    transform: scale(1.05);
  }

  .img:focus {
    outline: none;
  }
</style>
