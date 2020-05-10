<template>
  <div class="search-results">
    <Header title="Spojenia"/>
    <ViewBody :tight="true">
      <Loader v-if="fetching"></Loader>
      <div class="cards">
        <ResultCard class="card"
                    v-for="(card, index) of cards"
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
import Loader from '../../components/Loader.vue';

export default {
  name: 'SearchResults',
  components: {
    Loader,
    ResultCard,
    ViewBody,
    Header,
  },
  data() {
    return {
      cards: undefined,
      fetching: false,
    };
  },
  computed: {
    results() {
      return this.$store.getters.getResults;
    },
  },
  watch: {
    results(val) {
      this.cards = val;
    },
  },
  mounted() {
    if (this.results === undefined) {
      this.$router.back();
    }
    this.cards = this.results;
    window.scrollTo(0, 0);
  },
  methods: {
    showDetails(card) {
      this.$router.push({ name: 'result-details', params: { card } });
    },
    findAnotherTrips() {
      this.fetching = true;
      const request = this.$store.getters.getLastFindPathsRequest;
      const lastCard = this.cards[this.cards.length - 1];
      const dateFromCard = lastCard.date;
      const oldTimeFrom = this.stringDateToDate(dateFromCard);
      oldTimeFrom.setHours(lastCard.departureTime.substring(0, 2));
      oldTimeFrom.setMinutes(lastCard.departureTime.substring(3, 5));
      const newTimeFrom = new Date(oldTimeFrom.getTime() + 60000);
      request.timeFrom = this.dateToDateTime(newTimeFrom);
      this.$store.dispatch('findPaths', request).then((response) => {
        if (response.status !== 200) {
          alert(response.data);
          return;
        }
        this.cards = response.data;
        this.fetching = false;
      }).catch(() => {
        alert('Nastala chyba pri vyhľadávaní cesty.');
        this.fetching = false;
      });
    },
    dateToDateTime(date) {
      return {
        day: date.getDate(),
        month: date.getMonth() + 1,
        year: date.getFullYear(),
        hour: date.getHours(),
        minute: date.getMinutes(),
      };
    },
    stringDateToDate(stringDate) {
      const dd = stringDate.substring(0, 2);
      const MM = stringDate.substring(3, 5);
      const yyyy = stringDate.substring(6, 10);
      return new Date(yyyy, MM - 1, dd);
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
