<template>
  <div class="profile-container">
    <el-card class="profile-card">
      <template #header>
        <div class="card-header">
          <h2>个人资料</h2>
        </div>
      </template>

      <el-form
        ref="profileForm"
        :model="profileForm"
        :rules="rules"
        label-width="120px"
        @submit.prevent="handleSubmit"
      >
        <el-form-item label="姓名" prop="name">
          <el-input v-model="profileForm.name" placeholder="请输入姓名" />
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="profileForm.gender">
            <el-radio label="male">男</el-radio>
            <el-radio label="female">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="年龄" prop="age">
          <el-input-number
            v-model="profileForm.age"
            :min="1"
            :max="120"
            placeholder="请输入年龄"
          />
        </el-form-item>

        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="profileForm.phone" placeholder="请输入联系电话" />
        </el-form-item>

        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="profileForm.email" placeholder="请输入电子邮箱" />
        </el-form-item>

        <el-form-item label="身高(cm)" prop="height">
          <el-input-number
            v-model="profileForm.height"
            :min="100"
            :max="250"
            placeholder="请输入身高"
          />
        </el-form-item>

        <el-form-item label="体重(kg)" prop="weight">
          <el-input-number
            v-model="profileForm.weight"
            :min="20"
            :max="200"
            placeholder="请输入体重"
          />
        </el-form-item>

        <el-form-item label="病史" prop="medicalHistory">
          <el-input
            v-model="profileForm.medicalHistory"
            type="textarea"
            :rows="4"
            placeholder="请输入病史"
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            native-type="submit"
            :loading="loading"
            class="submit-button"
          >
            提交
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()
const loading = ref(false)

const profileForm = reactive({
  name: '',
  gender: '',
  age: undefined,
  phone: '',
  email: '',
  height: undefined,
  weight: undefined,
  medicalHistory: ''
})

const rules = reactive<FormRules>({
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  age: [
    { required: true, message: '请输入年龄', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入电子邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  height: [
    { required: true, message: '请输入身高', trigger: 'blur' }
  ],
  weight: [
    { required: true, message: '请输入体重', trigger: 'blur' }
  ],
  medicalHistory: [
    { required: true, message: '请输入病史', trigger: 'blur' }
  ]
})

const profileFormRef = ref<FormInstance>()

const handleSubmit = async () => {
  if (!profileFormRef.value) return
  
  try {
    await profileFormRef.value.validate()
    loading.value = true
    
    // TODO: 调用提交API
    // const res = await submitProfile(profileForm)
    
    ElMessage.success('提交成功')
    router.push('/behavior')
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败，请重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.profile-container {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.profile-card {
  margin-top: 20px;
}

.card-header {
  text-align: center;
}

.card-header h2 {
  margin: 0;
  color: #303133;
}

.submit-button {
  width: 100%;
}
</style> 