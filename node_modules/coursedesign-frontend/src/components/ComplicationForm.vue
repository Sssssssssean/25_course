<template>
  <div class="complication-form">
    <el-form :model="form" label-width="120px">
      <el-form-item label="并发症类型">
        <el-select v-model="form.type" placeholder="请选择并发症类型">
          <el-option label="糖尿病肾病" value="kidney" />
          <el-option label="糖尿病视网膜病变" value="retina" />
          <el-option label="糖尿病足" value="foot" />
          <el-option label="其他" value="other" />
        </el-select>
      </el-form-item>
      <el-form-item label="症状描述">
        <el-input
          v-model="form.description"
          type="textarea"
          :rows="4"
          placeholder="请详细描述症状"
        />
      </el-form-item>
      <el-form-item label="发生时间">
        <el-date-picker
          v-model="form.occurTime"
          type="datetime"
          placeholder="选择发生时间"
          format="YYYY-MM-DD HH:mm"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
    </el-form>
    <div class="form-actions">
      <el-button @click="$emit('close')">取消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading">
        提交
      </el-button>
      <el-button @click="testConnection" type="success">
        测试连接
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

const emit = defineEmits(['close', 'success'])

const loading = ref(false)
const form = ref({
  type: '',
  description: '',
  occurTime: ''
})

const handleSubmit = async () => {
  console.log('=== 开始提交并发症记录 ===')
  console.log('当前表单数据:', form.value)

  try {
    // 表单验证
    if (!form.value.type) {
      console.log('验证失败: 未选择并发症类型')
      ElMessage.warning('请选择并发症类型')
      return
    }
    if (!form.value.description) {
      console.log('验证失败: 未填写症状描述')
      ElMessage.warning('请填写症状描述')
      return
    }
    if (!form.value.occurTime) {
      console.log('验证失败: 未选择发生时间')
      ElMessage.warning('请选择发生时间')
      return
    }

    console.log('表单验证通过，开始发送请求...')
    loading.value = true

    // 先测试一个简单的请求
    console.log('发送测试请求到 /bfzNode')
    const response = await request({
      url: '/bfzNode',
      method: 'post',
      data: {
        type: form.value.type,
        description: form.value.description,
        occurTime: form.value.occurTime
      }
    })

    console.log('=== 收到响应 ===')
    console.log('响应状态:', response.status)
    console.log('响应数据:', response.data)

    if (response.status === 200 && response.data) {
      if (response.data.code === 200) {
        console.log('提交成功!')
        ElMessage.success('并发症记录成功，获得3分成长积分！')
        emit('success')
        emit('close')
      } else {
        console.log('业务逻辑失败:', response.data)
        ElMessage.error(response.data.message || '记录失败')
      }
    } else {
      console.log('HTTP请求失败')
      ElMessage.error('请求失败')
    }
  } catch (error: any) {
    console.log('=== 捕获到错误 ===')
    console.error('错误详情:', {
      message: error.message,
      response: error.response?.data,
      status: error.response?.status,
      config: error.config
    })

    if (error.response?.status === 400) {
      ElMessage.error('请求参数错误: ' + (error.response.data?.message || ''))
    } else if (error.response?.status === 500) {
      ElMessage.error('服务器内部错误: ' + (error.response.data?.message || ''))
    } else if (error.code === 'ERR_NETWORK') {
      ElMessage.error('网络连接失败，请检查后端服务')
    } else {
      ElMessage.error('记录并发症失败: ' + error.message)
    }
  } finally {
    loading.value = false
    console.log('=== 请求结束 ===')
  }
}

// 测试连接函数
const testConnection = async () => {
  console.log('=== 测试连接 ===')
  try {
    const response = await request({
      url: '/account/login',
      method: 'post',
      params: { 'X-userId': '123' }
    })
    console.log('测试连接成功:', response.data)
    ElMessage.success('后端连接正常')
  } catch (error: any) {
    console.error('测试连接失败:', error)
    ElMessage.error('后端连接失败: ' + error.message)
  }
}
</script>

<style scoped>
.complication-form {
  padding: 20px;
}

.form-actions {
  margin-top: 20px;
  text-align: right;
}
</style> 