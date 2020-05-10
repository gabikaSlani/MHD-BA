<template>
  <div class="history">
    <div class="header">
      História
    </div>
    <div class="body">
      <div v-for="(search, index) of lastSearches" v-bind:key="index"
           class="search">
        <div class="stops" :class="{invisible: search.invisible}" @click="selected(search, false)">
          <div>{{search.from.name.name}}</div>
          <div>{{search.to.name.name}}</div>
        </div>
        <div class="icon" v-if="!search.invisible" @click="selected(search, false)">
          <img src="/assets/arrow.png" alt="arrow" class="icon-img">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SearchHistory',
  watch: {
    stops(newValue) {
      if (newValue !== undefined) {
        this.$store.dispatch('computeLastSearches', {});
      }
    },
  },
  computed: {
    lastSearches() {
      const lastSearches = this.$store.getters.getLastSearches;
      if (lastSearches !== undefined && lastSearches.length < 5) {
        const countToAdd = 5 - lastSearches.length;
        for (let i = 0; i < countToAdd; i += 1) {
          const from = { name: { name: 'Neviditeľná položka' } };
          const to = { name: { name: 'na vyplenie výšky' } };
          lastSearches.push({ from, to, invisible: true });
        }
      }
      return lastSearches;
    },
    stops() {
      return this.$store.getters.getStopAreas;
    },
  },
  methods: {
    selected(search, runSearch) {
      this.$store.dispatch('searchSelected', { search, runSearch });
    },
  },
};
</script>

<style scoped>

  .history {
    display: flex;
    flex-direction: column;
  }

  .header {
    color: white;
    background-color: #335c81;
    border: 2px #13293d solid;
    font-weight: bold;
    font-size: 25px;
  }

  .body {
    background-color: white;
    border: 2px #13293d solid;
    border-top: none;
  }

  .search {
    display: flex;
    flex-direction: row;
  }

  .stops {
    display: flex;
    flex-direction: column;
    align-items: baseline;
    padding: 8px 10px;
    font-size: 18px;
    flex: 1;
  }

  .icon {
    display: flex;
    align-items: center;
    padding: 8px 8px;
    margin-right: 5px;
    cursor: pointer;
  }

  .icon-img {
    height: 18px;
  }

  .invisible {
    color: white;
  }

</style>
