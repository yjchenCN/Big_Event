import './assets/main.scss'
// main.ts
import { createApp } from 'vue'  // Vue 3
import ElementPlus from 'element-plus'  // 导入Element Plus
import 'element-plus/dist/index.css'  // 导入element-plus样式
import App from './App.vue'  // 导入app.vue
//导入中文
import locale from 'element-plus/dist/locale/zh-cn.js'
//导入路由器
import router from '@/router'
//导入pinia
import { createPinia } from 'pinia'
//导入pinia-persistedstate-plugin
import {createPersistedState} from 'pinia-persistedstate-plugin'


const app = createApp(App) // 创建应用实例
const pinia = createPinia()
const persist = createPersistedState()
pinia.use(persist)

app.use(ElementPlus, {locale})  // 使用Element Plus
app.use(router)
app.use(pinia)
app.mount('#app')  //控制html元素