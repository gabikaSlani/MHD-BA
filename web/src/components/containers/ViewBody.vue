<template>
  <div class="view-body" :class="{'view-body': !tight,
                                  'view-body-tight': tight,
                                  'without-padding-top': !paddingTop}">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'ViewBody',
  props: {
    tight: {
      type: Boolean,
      default: false,
    },
    paddingTop: {
      type: Boolean,
      default: true,
    },
  },
};
</script>

<style scoped>
  .view-body {
    height: 100%;
    overflow-y: hidden;
    padding-bottom: 0 !important;
  }

  @media (min-width: 400px) {
    .view-body {
      padding: 20px calc((100% - 500px)/2);
      flex: 1;
    }
    .view-body-tight {
      padding: 20px calc((100% - 500px)/2);
      flex: 1;
    }
  }

  @media (max-width: 400px) {
    .view-body {
      padding: 20px 23px;
      flex: 1;
    }
    .view-body-tight {
      flex: 1;
      padding: 20px 0;
      overflow-x: hidden;
    }
  }

  .without-padding-top {
    padding-top: 0;
  }
</style>
