<template>
  <div class="search">
    <PreFetcher :methods="['fetchStops']">
      <Header title="Vyhľadanie spojenia" routeNameToRedirect="menu"/>
      <ViewBody :tight="true" class="view-body">
        <SearchBox class="search-box"></SearchBox>
        <div class="spacer"></div>
        <SearchHistory class="search-history"></SearchHistory>
      </ViewBody>
    </PreFetcher>
  </div>
</template>

<script>
import Header from '../../components/containers/Header.vue';
import ViewBody from '../../components/containers/ViewBody.vue';
import SearchBox from './SearchBox.vue';
import PreFetcher from '../../components/PreFetcher.vue';
import SearchHistory from './SearchHistory.vue';

export default {
  name: 'Search',
  components: {
    SearchHistory, PreFetcher, SearchBox, ViewBody, Header,
  },
};
</script>

<style scoped>
  .search {
    flex: 1;
    display: flex;
    flex-direction: column;
  }

  .view-body {
    display: flex;
    flex-direction: column;
  }

  .search-box {
    margin-bottom: 30px;
  }

  .search-history {

  }

  .spacer {
    flex: 1;
  }

</style>
