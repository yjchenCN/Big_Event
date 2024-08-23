//定制请求的实例

//导入axios  npm install axios
import axios from "axios";
import { ElMessage } from "element-plus";
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = "/api";
const instance = axios.create({ baseURL });

//添加请求拦截器
//用于在请求发送前进行一些操作，例如添加认证令牌、设置请求头等。可以帮助统一处理请求的公共部分，简化业务代码。
import { useTokenStore } from "@/stores/token";
instance.interceptors.request.use(
  (config) => {
    //在请求头中添加token
    const tokenStore = useTokenStore();
    if (tokenStore.token) {
      config.headers.Authorization = tokenStore.token; //在拦截器中统一添加token
    }
    return config;
  },
  (err) => {
    //请求错误的回调
    return Promise.reject(err);
  }
);

//添加响应拦截器
//用于在响应到达后进行一些操作，例如检查响应状态码、处理错误信息等。可以帮助统一处理响应的公共部分，例如错误处理、数据转换等。
import router from "@/router";
instance.interceptors.response.use(
  (result) => {
    if (result.data.code === 0) {
      return result.data;
    }
    ElMessage.error(result.data.message ? result.data.message : "服务异常");
    return Promise.reject(result.data);
  },
  (err) => {
    //判断响应状态码，如果为401，则证明未登陆，并跳转到登录页面
    if (err.response.status === 401) {
      ElMessage.error("未登录，请先登陆");
      router.push("/login");
    } else {
      ElMessage.error(result.data.message ? result.data.message : "服务异常");
    }
    return Promise.reject(err); //异步的状态转化成失败的状态
  }
);

export default instance;
