<template>
  <input :type="type" :value="value" ref="input"
         @input="updateValue($event.target.value)"
         :placeholder="placeholder"/>
</template>

<script>
export default {
  name: 'Input',
  props: ['type', 'value', 'placeholder'],
  methods: {
    updateValue(value) {
      this.$emit('input', value);
    },
    changeValue(value) {
      console.log(value);
    },
  },
};
</script>

<style scoped>
  input {
    font-family: sans-serif, Helvetica;
    border: 0;
    font-size: 23px;
    text-decoration: none;
    border-bottom: none;
    background: transparent;
    width: 100%;
    cursor: pointer;
    padding: 5px 0;
  }

  input:focus {
    outline: none;
  }
</style>
