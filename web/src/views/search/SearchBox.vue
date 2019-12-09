<template>
  <div class="search-box">
    <div class="form">
      <div class="form-images">
        <img src="/assets/from_to.png" alt="inputs image" class="form-image">
        <img src="/assets/clock.png" alt="inputs image" class="form-image">
      </div>
      <div class="inputs">
        <div class="input-row">
          <StopSelection v-model="from" :currentLocationOption="true"/>
        </div>
        <div  class="input-row">
          <StopSelection v-model="to" :currentLocationOption="false"/>
        </div>
        <div class="input-row">
          <DateTimePicker v-model="when"></DateTimePicker>
        </div>
      </div>
    </div>
    <div class="buttons">
      <div class="change-direction-btn">
        <IconButton src="/assets/change_direction_btn.png"
                    @click.native="changeDirection"></IconButton>
      </div>
      <IconButton src="/assets/search_btn.png" class="search-btn"
                  @click.native="findPaths"></IconButton>
    </div>
  </div>
</template>

<script>
import IconButton from '../../components/inputs/IconButton.vue';
import DateTimePicker from '../../components/inputs/DateTimePicker.vue';
import StopSelection from './StopSelection.vue';

export default {
  name: 'SearchBox',
  components: {
    StopSelection, DateTimePicker, IconButton,
  },
  data() {
    return {
      from: undefined,
      to: undefined,
      when: undefined,
    };
  },
  watch: {
    from(value) {
      if (value !== undefined && this.to !== undefined && value.id === this.to.id) {
        this.to = undefined;
      }
    },
    to(value) {
      if (value !== undefined && this.from !== undefined && value.id === this.from.id) {
        this.from = undefined;
      }
    },
    selectedSearchFromHistory(value) {
      if (value !== undefined) {
        this.from = value.from;
        this.to = value.to;
        this.$store.commit('setSelectedSearch', undefined);
      }
    },
  },
  computed: {
    selectedSearchFromHistory() {
      return this.$store.getters.getSelectedSearch;
    },
  },
  methods: {
    changeDirection() {
      const tmp = this.to;
      this.to = this.from;
      this.from = tmp;
    },
    findPaths() {
      if (this.from === undefined || this.to === undefined) {
        return;
      }
      this.addSearchToHistory(this.from, this.to);
      this.$store.dispatch('findPaths', { from: this.from, to: this.to, when: this.when }).then((results) => {
        this.$router.push({ name: 'search-results', params: { results } });
      });
    },
    addSearchToHistory(from, to) {
      this.$store.dispatch('addSearchToHistory', { from, to });
    },
  },
};
</script>

<style scoped>
  .search-box {
    background-color: #eef0f2;
    border: 2px solid #13293d;
    margin: 0 -2px;
    display: flex;
    flex-direction: row;
  }

  .form {
    flex: 1;
    display: flex;
    flex-direction: row;
  }

  .inputs {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding-right: 15px;
  }

  .input-row {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 100%;
  }

  .input-row:not(:last-child) {
    border-bottom: 2px solid #13293d;
  }

  .form-images {
    flex: 0;
  }

  .form-image {
    width: 25px;
    padding: 15px 15px;
  }

  .buttons {
    padding: 10px;
    display: flex;
    flex-direction: column;
    border-left: 2px solid #13293d;
  }

  .change-direction-btn {
    flex: 2;
    display: flex;
    align-items: center;
  }

  .search-btn {
    flex: 1;
  }
</style>
