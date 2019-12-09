<template>
  <div class="search-results">
    <Header title="Spojenia"/>
    <ViewBody :tight="true">
      <div class="cards">
        <ResultCard class="card"
                    v-for="(card, index) of results"
                    v-bind:key="index"
                    :card="card"
                    @click.native="showDetails(card)"
        />
        <div class="another-results" @click="findAnotherTrips">Nasledujúce</div>
      </div>
    </ViewBody>
  </div>
</template>

<script>
import Header from '../../components/containers/Header.vue';
import ViewBody from '../../components/containers/ViewBody.vue';
import ResultCard from './ResultCard.vue';

export default {
  name: 'SearchResults',
  components: {
    ResultCard,
    ViewBody,
    Header,
  },
  props: {
    results: {
      type: Array,
    },
  },
  mounted() {
    // TODO po dokonceni vyvoja odstranit
    this.results = this.$store.dispatch('findPaths', {}).then((results) => {
      this.results = results;
    });

    if (this.results === undefined) {
      this.$router.push('/search');
    }
    window.scrollTo(0, 0);
  },
  methods: {
    showDetails(card) {
      this.$router.push({ name: 'result-details', params: { card } });
    },
    findAnotherTrips() {
      // TODO
    },
  },
};
</script>

<style scoped>
  .search-results {
    flex: 1;
    display: flex;
    flex-direction: column;
  }

  .cards {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-bottom: 15px;
  }

  .card {
    width: 90%;
    cursor: pointer;
  }

  .card:hover, .card:focus {
    transform: scale(1.01);
  }

  .another-results {
    border: 2px solid #13293d;
    background-color: #bf3134;
    width: 90%;
    height: 30px;
    font-weight: bold;
    color: white;
    font-size: 23px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }

  .another-results:hover, .another-results:focus {
    transform: scale(1.01);
  }
</style>
