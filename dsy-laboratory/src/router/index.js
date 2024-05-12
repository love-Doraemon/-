import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manage',
    component:()=>import('../views/Manage.vue'),
    children:[
      {path:'home',name:'Home',component:()=> import('../views/Manage/Home.vue')},
      {path: '/Alarm', name: 'alarm',component:()=>import('../views/Manage/AlarmView')},
      {path: '/message', name: 'message',component:()=>import('../views/Manage/MessageView')},
      {path: '/admin', name: 'admin',component:()=>import('../views/Manage/Admin')},
      {path:'/getdata',name:'getdata',component:()=>import('../views/Manage/GetData')},
      {path:'/wsdata',name:'wsdata',component:()=>import('../views/Manage/WsData')},
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component:()=>import('../views/LoginView.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component:()=>import('../views/Register.vue')
  },

]

const router = new VueRouter({
  routes
})

export default router
