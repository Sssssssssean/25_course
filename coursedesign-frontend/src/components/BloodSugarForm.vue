<template>
  <div class="blood-sugar-form">
    <el-form :model="form" label-width="120px">
      <el-form-item label="血糖值">
        <el-input-number v-model="form.value" :min="0" :max="30" :precision="1" />
        <span class="unit">mmol/L</span>
      </el-form-item>
      <el-form-item label="测量时间">
        <el-date-picker
          v-model="form.measureTime"
          type="datetime"
          placeholder="选择测量时间"
          format="YYYY-MM-DD HH:mm"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
      <el-form-item label="备注">
        <el-input
          v-model="form.remark"
          type="textarea"
          :rows="3"
          placeholder="请输入备注信息"
        />
      </el-form-item>
    </el-form>
    <div class="form-actions">
      <el-button @click="$emit('close')">取消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading">
        提交
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

const emit = defineEmits(['close', 'success'])

const props = defineProps<{
  currentCount: number
}>()

const loading = ref(false)
const form = ref({
  value: 0,
  measureTime: '',
  remark: ''
})

const handleSubmit = async () => {
  try {
    loading.value = true
    console.log('提交的数据:', form.value)
    
    // 先触发成功事件，让父组件更新记录数
    if (props.currentCount < 3) {
      ElMessage.success('血糖记录成功，继续加油！')
    } else {
      ElMessage.success('血糖记录成功，获得1分成长积分！')
    }
    emit('success')
    
    // 发送血糖记录请求
    const response = await request({
      url: '/BloodSugar',
      method: 'post',
      data: form.value
    })
    
    console.log('血糖记录响应:', response)
    
    if (response.data) {
      // 发送积分更新请求，让后端判断是否加分
      const pointsResponse = await request({
        url: '/api/points/update',
        method: 'post',
        data: {
          type: 'bloodSugar',
          points: 1
        }
      })
      
      console.log('积分更新响应:', pointsResponse)
    }
  } catch (error: any) {
    console.error('记录血糖失败，详细错误:', {
      message: error.message,
      response: error.response,
      request: error.request,
      config: error.config
    })
    // ElMessage.error(error.response?.data?.message || '记录血糖失败，请重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.blood-sugar-form {
  padding: 20px;
}

.unit {
  margin-left: 10px;
  color: #606266;
}

.form-actions {
  margin-top: 20px;
  text-align: right;
}
</style> 