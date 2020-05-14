<template>
  <div class="datetime-picker-root">
    <div class="overlay" id="overlay" :class="{'overlay-hidden': !displayOverlay}"></div>
    <div class="datetime-picker-root">
      <div class="datetime-picker-input datetime-picker-root"
           @click="showDatepicker">{{printedValue}}
      </div>
      <Datepicker :language="slovak" :disabled-dates="disabledDates"
                  calendar-class="datepicker-calendar-dialog"
                  input-class="datepicker-input"
                  monday-first
                  v-model="time"
                  @input="hideDatepicker(); showTimeselector(); timeManuallyPicked = true"/>
      <Timeselector v-if="timeSelectorVisible"
                    :value="time" @input="timeSelected($event)" @close="closeTimeSelector"
                    :disable="{h:getDisabledHours, m:getDisabledMinutes, s:null}"
      />
    </div>
  </div>
</template>

<script>
import Datepicker from 'vuejs-datepicker';
import sk from 'vuejs-datepicker/dist/locale';
import Timeselector from './TimeSelector.vue';

export default {
  name: 'DateTimepicker',
  components: {
    Datepicker,
    Timeselector,
  },
  data() {
    return {
      slovak: sk,
      timeSelectorVisible: false,
      displayOverlay: false,
      timeManuallyPicked: false,
      time: null,
      printedValue: '',
    };
  },
  computed: {
    getDisabledHours() {
      if (this.$store.getters.getActualDateTime === undefined) return undefined;
      if (this.sameDay(this.time, this.getActualDateTime)) {
        const actualHour = this.$store.getters.getActualDateTime.hour;
        return [...Array(actualHour)
          .keys()];
      }
      return [];
    },
    getDisabledMinutes() {
      if (this.$store.getters.getActualDateTime === undefined) return undefined;
      if (this.sameDay(this.time, this.getActualDateTime)
        && this.sameHour(this.time, this.getActualDateTime)) {
        const actualMinute = this.$store.getters.getActualDateTime.minute;
        return [...Array(actualMinute).keys()];
      }
      return [];
    },
    getActualDateTime() {
      const now = this.$store.getters.getActualDateTime;
      if (now === undefined) return undefined;
      return new Date(now.year, now.month - 1, now.day, now.hour, now.minute);
    },
    disabledDates() {
      return {
        ranges: [
          {
            from: new Date(0, 0, 0),
            to: this.getActualDateTime,
          },
          {
            from: new Date(2018, 11, 31),
            to: new Date(2100, 0, 1),
          },
        ],
      };
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
    this.$emit('input', this.getActualDateTime);
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
      this.timeSelectorVisible = true;
    },
    closeTimeSelector() {
      this.timeSelectorVisible = false;
    },
    addZero(s) {
      if (s <= 9) {
        return `0${s}`;
      }
      return s;
    },
    timeSelected(e) {
      this.time.setMinutes(e.getMinutes());
      this.time.setHours(e.getHours());
      this.timeManuallyPicked = true;
      this.updatePrintedValue(this.time);
      this.$emit('input', this.time);
    },
    updatePrintedValue(val) {
      const now = new Date();
      if (val === undefined) this.printedValue = '';
      if (val.getFullYear() === now.getFullYear()
        && val.getMonth() === now.getMonth()
        && val.getDate() === now.getDate()
        && val.getHours() === now.getHours()
        && val.getMinutes() === now.getMinutes()) {
        this.printedValue = 'Teraz';
      }
      const dateToPrint = `${this.addZero(val.getDate())}.${this.addZero(val.getMonth() + 1)}.${val.getFullYear()}`;
      const timeToPrint = `${this.addZero(new Date(val).getHours())}:${this.addZero(new Date(val).getMinutes())}`;
      this.printedValue = `${dateToPrint} ${timeToPrint}`;
    },
    sameDay(day1, day2) {
      if (day1 === undefined || day2 === undefined) return false;
      return day1.getFullYear() === day2.getFullYear()
        && day1.getMonth() === day2.getMonth()
        && day1.getDate() === day2.getDate();
    },
    sameHour(day1, day2) {
      if (day1 === undefined || day2 === undefined) return false;
      return day1.getHours() === day2.getHours();
    },
  },
  watch: {
    getActualDateTime(val) {
      if (this.timeManuallyPicked === false) {
        this.time = val;
        this.$emit('input', this.time);
      }
    },
    time(val) {
      this.updatePrintedValue(val);
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

  .datetime-picker-root {
    width: 100%;
    height: 100%;
  }

  .datetime-picker-input {
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


  .overlay-hidden {
    display: none !important;
  }

</style>
