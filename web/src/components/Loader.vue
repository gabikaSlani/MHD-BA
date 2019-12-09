<template>
  <div class="wrapper">
    <div class="loader"></div>
  </div>
</template>

<script>
export default {
  name: 'Loader',
};
</script>

<style scoped>
  .wrapper {
    z-index: 10;
    position: fixed;
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #323232c1;
  }

  .loader {
    z-index: 11;
    border: 16px solid #f3f3f3;
    border-top: 16px solid #13293d;
    border-radius: 50%;
    width: 120px;
    height: 120px;
    animation: spin 2s linear infinite;
  }

  @keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }
</style>
