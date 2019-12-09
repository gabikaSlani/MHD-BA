import Vue from 'vue';
import VueRouter from 'vue-router';
import Menu from '../views/Menu.vue';
import Search from '../views/search/Search.vue';
import RouteList from '../views/routeList/RouteList.vue';
import RouteDetails from '../views/routeList/RouteDetails.vue';
import RouteOnMap from '../views/routeList/RouteOnMap.vue';
import SearchResults from '../views/searchResults/SearchResults.vue';
import ResultDetails from '../views/searchResults/ResultDetails.vue';

Vue.use(VueRouter);

const routes = [
  {
    name: 'menu',
    path: '/',
    component: Menu,
  },
  {
    name: 'search',
    path: '/search',
    component: Search,
  },
  {
    name: 'route-list',
    path: '/route-list',
    component: RouteList,
  },
  {
    name: 'route-details',
    path: '/route-list/details',
    component: RouteDetails,
    props: true,
  },
  {
    name: 'route-on-map',
    path: '/route-list/details/map',
    component: RouteOnMap,
    props: true,
  },
  {
    name: 'search-results',
    path: '/search-results',
    component: SearchResults,
    props: true,
  },
  {
    name: 'result-details',
    path: '/search-results/details',
    component: ResultDetails,
    props: true,
  },
  {
    path: '*',
    redirect: '/',
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  next();
});

export default router;
