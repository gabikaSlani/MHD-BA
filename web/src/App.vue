<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style lang="scss">

  html, body {
    margin: 0;
    padding: 0;
    height: 100%;
    width: 100%;
  }

  #app {
    font-family: sans-serif, Helvetica;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    background-color: #c6c7c4;
    min-height: 100%;
    display: flex;
    flex-direction: column;
  }
</style>
