<template>
  <div class="login-container">
    <div class="login-background">
      <div class="login-content">
        <div class="login-header">
          <h1>医疗积分系统</h1>
          <p class="subtitle">您的健康管理助手</p>
        </div>
        
        <el-card class="login-card" :body-style="{ padding: '40px' }">
          <template #header>
            <div class="card-header">
              <el-icon class="header-icon"><User /></el-icon>
              <span>用户登录</span>
            </div>
          </template>
          
          <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="rules"
            label-position="top"
            @submit.prevent="handleLogin"
            class="login-form"
          >
            <el-form-item prop="userId">
              <el-input
                v-model="loginForm.userId"
                placeholder="请输入用户ID"
                :prefix-icon="User"
                size="large"
                class="custom-input"
                clearable
              />
            </el-form-item>

            <el-form-item>
              <el-button
                type="primary"
                native-type="submit"
                :loading="loading"
                class="login-button"
                size="large"
              >
                {{ loading ? '登录中...' : '登录' }}
              </el-button>
            </el-form-item>
          </el-form>

          <div class="login-footer">
            <p>首次登录将获得1分成长积分</p>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import { login, getUserById } from '@/api'

const router = useRouter()
const loading = ref(false)

const loginForm = reactive({
  userId: ''
})

const rules = reactive<FormRules>({
  userId: [
    { required: true, message: '请输入用户ID', trigger: 'blur' },
    { pattern: /^\d+$/, message: '用户ID必须为数字', trigger: 'blur' }
  ]
})

const loginFormRef = ref<FormInstance>()

const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  try {
    await loginFormRef.value.validate()
    loading.value = true
    
    console.log('开始登录，用户ID:', loginForm.userId)
    
    // 直接进行登录
    const res = await login(loginForm.userId)
    console.log('登录响应:', res)
    
    if (res.data.code === 200) {
      // 存储用户信息
      localStorage.setItem('userId', loginForm.userId)
      ElMessage.success('登录成功')
      
      // 跳转到行为页面
      console.log('准备跳转到行为页面')
      try {
        await router.push('/behavior')
        console.log('跳转成功')
      } catch (error) {
        console.error('路由跳转失败:', error)
        ElMessage.error('页面跳转失败，请重试')
      }
    } else {
      console.error('登录失败，响应码:', res.data.code, '错误信息:', res.data.message)
      ElMessage.error(res.data.message || '登录失败')
    }
  } catch (error: any) {
    console.error('登录失败，详细错误:', {
      message: error.message,
      response: error.response,
      request: error.request,
      config: error.config,
      code: error.code
    })
    
    if (error.code === 'ECONNABORTED') {
      ElMessage.error('登录请求超时，请检查网络连接')
    } else if (error.code === 'ERR_NETWORK') {
      ElMessage.error('网络连接失败，请检查后端服务是否正常运行')
    } else if (error.response) {
      ElMessage.error(error.response.data?.message || '登录失败，请重试')
    } else {
      ElMessage.error('登录失败，请重试')
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  width: 100vw;
  overflow: hidden;
}

.login-background {
  height: 100%;
  width: 100%;
  background: linear-gradient(135deg, #4a8965 0%, #80dfa7 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

.login-background::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><circle cx="50" cy="50" r="40" fill="none" stroke="rgba(255,255,255,0.1)" stroke-width="2"/></svg>') center/60px 60px;
  opacity: 0.15;
  animation: rotate 20s linear infinite;
}

@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.login-content {
  width: 100%;
  max-width: 1200px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  z-index: 1;
}

.login-header {
  text-align: center;
  margin-bottom: 40px;
  color: white;
  animation: fadeInDown 1s ease-out;
}

.login-header h1 {
  font-size: 2.5em;
  margin: 0;
  font-weight: 600;
  text-shadow: 0 2px 4px rgba(0,0,0,0.2);
}

.subtitle {
  font-size: 1.2em;
  margin-top: 10px;
  opacity: 0.9;
}

.login-card {
  width: 100%;
  max-width: 400px;
  background: rgba(255, 255, 255, 0.98);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);
  animation: fadeInUp 1s ease-out;
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  font-size: 1.2em;
  color: #27ae60;
}

.header-icon {
  font-size: 1.4em;
}

.login-form {
  margin-top: 20px;
}

.custom-input :deep(.el-input__wrapper) {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.03);
  border-radius: 8px;
  transition: all 0.3s ease;
  background: rgba(255, 255, 255, 0.9);
}

.custom-input :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.08);
  background: rgba(255, 255, 255, 1);
}

.login-button {
  width: 100%;
  height: 48px;
  border-radius: 8px;
  font-size: 1.1em;
  font-weight: 500;
  transition: all 0.3s ease;
  background: linear-gradient(135deg, #1a5f38 0%, #27ae60 100%);
  border: none;
}

.login-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(26, 95, 56, 0.3);
}

.login-footer {
  margin-top: 20px;
  text-align: center;
  color: #666;
  font-size: 0.9em;
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .login-header h1 {
    font-size: 2em;
  }
  
  .subtitle {
    font-size: 1em;
  }
  
  .login-card {
    margin: 0 20px;
  }
}
</style> 