<template>
  <div class="root">
    <div class="overlay" id="overlay" :class="{'overlay-hidden': !displayOverlay}"></div>
    <div class="root">
      <div class="input root" @click="showDatepicker">{{printedValue}}</div>
      <Datepicker :language="slovak"
                  calendar-class="datepicker-calendar-dialog"
                  input-class="datepicker-input"
                  monday-first
                  v-model="date"
                  @input="hideDatepicker(); showTimeselector();"/>
      <div class="timeselector" :class="{'timeselector-hidden': !timeSelectorVisible}">
        <Timeselector ref="timeselector"
                      :interval="{h:1, m:1}"
                      v-model="time"/>
        <div class="button" @click="confirm">OK</div>
      </div>
    </div>
  </div>
</template>

<script>
import Datepicker from 'vuejs-datepicker';
import Timeselector from 'vue-timeselector';
import sk from 'vuejs-datepicker/dist/locale';

export default {
  name: 'DateTimepicker',
  components: {
    Datepicker, Timeselector,
  },
  data() {
    return {
      slovak: sk,
      date: new Date(),
      time: new Date(),
      timeSelectorVisible: false,
      displayOverlay: false,
    };
  },
  computed: {
    printedValue() {
      const now = new Date();
      if (this.date.getFullYear() === now.getFullYear() && this.date.getMonth() === now.getMonth()
        && this.date.getDate() === now.getDate() && this.time.getHours() === now.getHours()
        && this.time.getMinutes() === now.getMinutes()) {
        return 'Teraz';
      }
      const dateToPrint = `${this.addZero(this.date.getDate())}.${this.addZero(this.date.getMonth() + 1)}.${this.date.getFullYear()}`;
      const timeToPrint = `${this.addZero(new Date(this.time).getHours())}:${this.addZero(new Date(this.time).getMinutes())}`;
      return `${dateToPrint} ${timeToPrint}`;
    },
  },
  mounted() {
    window.onclick = (event) => {
      if (event.srcElement.id === 'overlay') {
        this.hideTimeselector();
        this.hideDatepicker();
        this.displayOverlay = false;
      }
    };
    this.$emit('input', this.mergeDateAndTime());
  },
  methods: {
    showDatepicker() {
      document.getElementsByClassName('vdp-datepicker__calendar')[0].style.display = 'block';
      this.displayOverlay = true;
    },
    hideDatepicker() {
      document.getElementsByClassName('vdp-datepicker__calendar')[0].style.display = 'none';
      this.displayOverlay = false;
    },
    showTimeselector() {
      this.displayOverlay = true;
      this.timeSelectorVisible = true;
      this.$refs.timeselector.$el.children[2].className = 'vtimeselector__box';
      const selectedElements = document.getElementsByClassName('timeselector__box__item--is-selected');
      setTimeout(() => {
        selectedElements[0].scrollIntoView({ behavior: 'smooth', block: 'center' });
      }, 100);
      setTimeout(() => {
        selectedElements[1].scrollIntoView({ behavior: 'smooth', block: 'center' });
      }, 700);
    },
    hideTimeselector() {
      this.displayOverlay = false;
      this.timeSelectorVisible = false;
      this.$refs.timeselector.$el.children[2].className = 'vtimeselector__box vtimeselector__box--is-closed';
    },
    confirm() {
      this.hideTimeselector();
      this.$emit('input', this.mergeDateAndTime());
    },
    mergeDateAndTime() {
      const res = this.date;
      res.setHours(this.time.getHours());
      res.setMinutes(this.time.getMinutes());
      return res;
    },
    addZero(s) {
      if (s <= 9) {
        return `0${s}`;
      }
      return s;
    },
  },
};
</script>

<style>
  .overlay {
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    z-index: 3;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background: transparent;
  }

  .root {
    width: 100%;
    height: 100%;
  }

  .input {
    font-family: sans-serif, Helvetica;
    border: 0;
    font-size: 23px;
    text-decoration: none;
    width: 100%;
    cursor: pointer;
    background: transparent;
    display: flex;
    align-items: center;
  }

  .datepicker-calendar-dialog {
    position: fixed !important;
    top: 50%;
    left: 50%;
    width: 280px !important;
    height: 320px;
    margin-top: -150px;
    margin-left: -140px;
    transform: scale(1.1);
  }

  .datepicker-calendar-dialog .cell.selected,
  .timeselector__box__item--is-selected {
    background: #bf3134 !important;
    color: #ffffff;
  }

  .datepicker-input, .datepicker-input:focus {
    display: none;
  }

  .vtimeselector__input, .vtimeselector__clear {
    display: none;
  }

  .timeselector {
    position: fixed;
    top: 50%;
    left: 50%;
    width: 280px !important;
    height: 320px;
    margin-top: -100px;
    margin-left: -140px;
    z-index: 999;
  }

  .timeselector-hidden, .overlay-hidden {
    display: none !important;
  }

  .vtimeselector__box {
    border: black 1px solid;
  }

  .button {
    position: relative;
    bottom: -160px;
    padding: 10px 0;
    background-color: white;
    font-weight: bold;
    font-size: 20px;
    cursor: pointer;
    z-index: 999;
    border: black 1px solid;
    border-top: none;
  }

  .button:focus {
    background-color: #eef0f2;
  }
</style>
