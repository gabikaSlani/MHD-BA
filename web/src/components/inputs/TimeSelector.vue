<template>
  <div class="overlay" :class="{'overlay-hidden': !displayOverlay}">
    <div class="root">
      <div class="dialog">
        <div class="pickers">
          <ul class="list">
            <li class="head item">HOD</li>
            <li v-for="index in 24" v-bind:key="index" class="item"
                :class="{'timepicker-selected': index - 1 === hours,
                         'disabled': isDisabled(index - 1, 'hours')}"
                @click="selectHours(index - 1)">
              {{index - 1}}
            </li>
          </ul>
          <ul class="list">
            <li class="head item">MIN</li>
            <li v-for="index in 60" v-bind:key="60 + index" class="item"
                :class="{'timepicker-selected': index - 1 === minutes,
                         'disabled': isDisabled(index - 1, 'minutes')}"
                @click="selectMinutes(index - 1)">
              {{index - 1}}
            </li>
          </ul>
        </div>
        <div class="button" @click="confirm">OK</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TimeSelector',
  data() {
    return {
      time: new Date(),
      hours: 0,
      minutes: 0,
      seconds: 0,
      displayOverlay: true,
    };
  },
  props: ['value', 'disable'],
  mounted() {
    this.time.setHours(this.value.getHours());
    this.time.setMinutes(this.value.getMinutes());
    this.time.setSeconds(this.value.getSeconds());
    this.hours = this.value.getHours();
    this.minutes = this.value.getMinutes();
    this.seconds = this.value.getSeconds();
    this.scrollToSelected();
  },
  methods: {
    confirm() {
      this.$emit('input', this.time);
      this.$emit('close', this.time);
    },
    scrollToSelected() {
      const selectedElements = document.getElementsByClassName('timepicker-selected');
      setTimeout(() => {
        if (selectedElements[0] !== undefined) {
          selectedElements[0].scrollIntoView({
            behavior: 'smooth',
            block: 'center',
          });
        }
      }, 100);
      setTimeout(() => {
        if (selectedElements[1] !== undefined) {
          selectedElements[1].scrollIntoView({
            behavior: 'smooth',
            block: 'center',
          });
        }
      }, 700);
    },
    selectHours(hours) {
      if (this.isDisabled(hours, 'hours')) return;
      this.hours = hours;
      this.time.setHours(hours);
      this.$emit('input', this.time);
      this.scrollToSelected();
    },
    selectMinutes(minutes) {
      if (this.isDisabled(minutes, 'minutes')) return;
      this.minutes = minutes;
      this.time.setMinutes(minutes);
      this.$emit('input', this.time);
      this.scrollToSelected();
    },
    isDisabled(value, type) {
      if (this.disable === undefined) return false;
      if (type === 'hours') {
        if (this.disable.h === undefined) return false;
        return this.disable.h.includes(value);
      }
      if (type === 'minutes') {
        if (this.disable.m === undefined) return false;
        return this.disable.m.includes(value);
      }
      return false;
    },
  },
  watch: {
    value(val) {
      this.time = val;
      this.hours = val.getHours();
      this.minutes = val.getMinutes();
      this.seconds = val.getSeconds();
      this.scrollToSelected();
    },
  },
};
</script>

<style scoped>
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

  .dialog {
    position: fixed !important;
    top: 50%;
    left: 50%;
    width: 260px !important;
    height: 200px;
    margin-top: -100px;
    margin-left: -130px;
    background-color: white;
    border: 1px black solid;
  }

  .pickers {
    height: 165px;
    display: flex;
  }

  .overlay-hidden {
    display: none !important;
  }

  .list {
    list-style: none;
    padding: 0;
    margin: 0;
    flex: 1;
    text-align: center;
    overflow-x: hidden;
    overflow-y: auto;
  }

  .head {
    color: white;
    background: #13293d;
    font-size: .8em;
    padding: .8em 0 .4em;
    cursor: default !important;
  }

  .item {
    padding: 5px 0;
    font-size: 18px;
    cursor: pointer;
  }

  .disabled {
    cursor: auto;
    background: #f5f5f5;
    color: #a5a5a5;
  }

  .timepicker-selected {
    background: #bf3134 !important;
    color: white;
  }

  .button {
    color: white;
    background: #335c81;
    font-weight: bold;
    font-size: 20px;
    cursor: pointer;
    z-index: 999;
    border-top: black 1px solid;
    border-bottom: black 1px solid;
    height: 35px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .button:focus {
    background-color: #eef0f2;
  }

</style>
