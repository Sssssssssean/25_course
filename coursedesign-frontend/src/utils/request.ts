import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建 axios 实例
const request = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 10000, // 增加超时时间到10秒
    headers: {
        'Content-Type': 'application/json'
    }
})

// 请求拦截器
request.interceptors.request.use(
    config => {
        console.log('发送请求:', {
            url: config.url,
            method: config.method,
            data: config.data,
            headers: config.headers,
            baseURL: config.baseURL
        })
        // 从localStorage获取用户ID
        const userId = localStorage.getItem('userId')
        if (userId) {
            config.headers['X-userId'] = userId
        }
        return config
    },
    error => {
        console.error('请求配置错误:', error)
        return Promise.reject(error)
    }
)

// 响应拦截器
request.interceptors.response.use(
    response => {
        console.log('收到响应:', {
            url: response.config.url,
            status: response.status,
            data: response.data,
            headers: response.headers
        })
        return response
    },
    error => {
        console.error('响应错误:', {
            url: error.config?.url,
            status: error.response?.status,
            data: error.response?.data,
            message: error.message,
            config: error.config,
            code: error.code
        })
        
        if (error.code === 'ECONNABORTED') {
            ElMessage.error('请求超时，请检查网络连接')
        } else if (error.code === 'ERR_NETWORK') {
            ElMessage.error('网络连接失败，请检查后端服务是否正常运行')
        } else if (error.response) {
            // 服务器返回了错误状态码
            const message = error.response.data?.message || `请求失败 (${error.response.status})`
            ElMessage.error(message)
        } else if (error.request) {
            // 请求已发送但没有收到响应
            ElMessage.error('服务器无响应，请检查后端服务是否正常运行')
        } else {
            // 请求配置出错
            ElMessage.error('请求配置错误：' + error.message)
        }
        return Promise.reject(error)
    }
)

export default request