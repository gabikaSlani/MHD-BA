<template>
  <div class="pre-fetcher">
    <Loader v-if="fetching"></Loader>
    <slot></slot>
  </div>
</template>

<script>
import Loader from './Loader.vue';

export default {
  name: 'PreFetcher',
  components: {
    Loader,
  },
  data() {
    return {
      fetching: false,
    };
  },
  props: {
    methods: {
      type: Array,
    },
  },
  mounted() {
    if (this.methods !== undefined && this.methods.length > 0) {
      this.prefetch();
    }
  },
  methods: {
    prefetch() {
      this.fetching = true;
      const dispatchMethods = this.methods.map(m => this.$store.dispatch(m));
      Promise.all(dispatchMethods).then(() => {
        this.fetching = false;
      }).catch(() => {
        this.fetching = false;
        this.$router.push('/error');
      });
    },
  },
};
</script>

<style scoped>

  .pre-fetcher {
    flex: 1;
    display: flex;
    flex-direction: column;
  }

</style>
