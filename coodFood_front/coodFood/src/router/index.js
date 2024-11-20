import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/home',
    }, {
      path: '/home',
      name: 'home',
      meta: {
        title: '首页',
        keepAlive: true
      },
      component: HomeView
    },
    {
      path: '/user',
      name: 'user',
      meta: {
        title: '用户',
        keepAlive: true
      },
      component: () => import('../views/UserView.vue')
    }
  ],
})

export default router
