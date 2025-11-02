import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../views/ProfileForm.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/behavior',
    name: 'Behavior',
    component: () => import('../views/BehaviorPage.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/points',
    name: 'Points',
    component: () => import('../views/points/PointsView.vue'),
    meta: {
      requiresAuth: true
    }
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

// 路由守卫
router.beforeEach((to, _from, next) => {
  const isAuthenticated = localStorage.getItem('userId')
  
  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login')
  } else {
    next()
  }
})

export default router
