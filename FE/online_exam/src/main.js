import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'

// 导入全局样式表
import './assets/css/global.css'
// 导入axios，并挂在到Vue的原型对象上
import axios from 'axios'
axios.defaults.baseURL = 'http://localhost:8088/'; // 设置请求根路径

// axios 请求拦截器，给每一个API请求都挂载上了 token
axios.interceptors.request.use(config => {
  config.headers.Authorization = window.sessionStorage.getItem('token');
  return config;
})

Vue.prototype.$http = axios;
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
