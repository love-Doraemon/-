// 引入axios库
import axios from 'axios'
import router from '@/router'
// 创建一个axios实例，设置基础URL和超时时间
const request = axios.create({
    baseURL:'http://10.17.57.87:9090', // 基础URL
    timeout:30000 // 超时时间
})

// 添加请求拦截器
request.interceptors.request.use(config=>{
    // 在发送请求之前，设置请求头的Content-Type为application/json;charset=utf-8
    config.headers['Content-Type']='application/json;charset=utf-8';
    let user= JSON.parse(localStorage.getItem('user-inform')||'{}')
    config.headers['token']=user.token;

    return config // 返回配置
},error=>{
    // 如果请求出错，打印错误信息并返回Promise.reject
    console.error('request error:'+error)
    return Promise.reject(error)
});

// 添加响应拦截器
request.interceptors.response.use(
    response=>{
        // 获取响应数据
        let res = response.data;
        // 如果响应数据是字符串，尝试将其解析为JSON
        if(typeof res==='string'){
            res=res? JSON.parse(res): res
        }
        if(res.code ==='401'){
            router.push('/login')
        }
        return res; // 返回解析后的数据
    },
    error=>{ 
        // 如果响应出错，打印错误信息并返回Promise.reject
        console.error('response error:'+error)
        return Promise.reject(error)
    }
)

export default request