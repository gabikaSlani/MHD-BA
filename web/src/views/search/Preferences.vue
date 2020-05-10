<template>
  <div>
    <div class="box" :class="{'box-opened': opened}">
      <table>
        <tbody>
          <tr>
            <td class="pref-name">Maximálny počet prestupov</td>
            <td class="pref-value">
              <select @input="setMaxNumberOfTransfers" :value="maxNumberOfTransfers">
                <option v-for="option of preferencesOptions.maxNumberOfTransfers"
                        v-bind:value="option" v-bind:key="option">
                  {{option}}
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td class="pref-name">Maximálna dĺžka peších presunov</td>
            <td class="pref-value">
              <select @input="setMaxTimeOfWalking" :value="maxTimeOfWalking">
                <option v-for="option of preferencesOptions.maxTimeOfWalking"
                        v-bind:value="option" v-bind:key="option">
                  {{option}} min
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td class="pref-name">Minimálny čas na prestup</td>
            <td class="pref-value">
              <select @input="setMinTimeForTransfer" :value="minTimeForTransfer">
                <option v-for="option of preferencesOptions.minTimeForTransfer"
                        v-bind:value="option" v-bind:key="option">
                  {{option}} min
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td class="pref-name">Len nízkopodlažné spoje</td>
            <td class="pref-value">
              <input type="checkbox" @input="setOnlyLowFloor"
                     :value="onlyLowFloor" class="checkbox">
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div>
      <img src="/assets/scroll_down.png"
           v-if="!opened"
           class="arrow"
           @click="opened = true">
      <img src="/assets/scroll_up.png"
           v-if="opened"
           class="arrow"
           @click="opened = false">
    </div>
  </div>
</template>

<script>
export default {
  name: 'Preferences',
  data() {
    return {
      opened: false,
    };
  },
  computed: {
    maxNumberOfTransfers() {
      return this.$store.getters.getMaxNumberOfTransfers;
    },
    maxTimeOfWalking() {
      return this.$store.getters.getMaxTimeOfWalking;
    },
    minTimeForTransfer() {
      return this.$store.getters.getMinTimeForTransfer;
    },
    onlyLowFloor() {
      return this.$store.getters.getOnlyLowFloor;
    },
    preferencesOptions() {
      return this.$store.getters.getPreferencesOptions;
    },
  },
  methods: {
    setMaxNumberOfTransfers(e) {
      this.$store.commit('setMaxNumberOfTransfers', parseInt(e.target.value, 0));
    },
    setMaxTimeOfWalking(e) {
      this.$store.commit('setMaxTimeOfWalking', parseInt(e.target.value, 0));
    },
    setMinTimeForTransfer(e) {
      this.$store.commit('setMinTimeForTransfer', parseInt(e.target.value, 0));
    },
    setOnlyLowFloor(e) {
      this.$store.commit('setOnlyLowFloor', e.target.checked);
    },
  },
};
</script>

<style scoped>
  .arrow {
    height: 20px;
  }

  .box {
    max-height: 0;
    border-bottom: 0 solid #13293d;
    transition: max-height 0.20s ease-out, border-width 0.20s ease-out;
    overflow: hidden;
    background-color: white;
  }

  .box-opened {
    border-bottom: 2px solid #13293d;
    max-height: 500px;
    transition: max-height 0.25s ease-in;
  }

  table {
    padding: 5px 0;
    width: 100%;
  }

  .pref-name {
    text-align: left;
  }

  .pref-value {
    text-align: right;
  }

  td {
    padding: 5px 10px;
    font-size: 18px;
  }
  select {
    font-size: 18px;
    width: 100%;
    border: none;
  }

  .checkbox {
    width: 20px;
    height: 20px;
  }

</style>
