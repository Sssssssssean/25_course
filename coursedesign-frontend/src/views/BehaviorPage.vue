<template>
  <div class="behavior-container">
    <!-- 添加导航栏 -->
    <div class="nav-bar">
      <el-button @click="goBack" icon="ArrowLeft">返回</el-button>
      <el-button type="primary" @click="goToPoints" icon="Star">查看积分</el-button>
    </div>

    <el-row :gutter="20">
      <!-- 血糖记录 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>血糖记录</span>
              <el-tag type="success" v-if="bloodSugarCount >= 3">已获得积分</el-tag>
            </div>
          </template>
          <div class="card-content">
            <p>当前记录数：{{ bloodSugarCount }}</p>
            <el-button type="primary" @click="handleBloodSugar">
              记录血糖
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 并发症记录 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>并发症记录</span>
              <el-tag type="success" v-if="canRecordComplication">可记录</el-tag>
            </div>
          </template>
          <div class="card-content">
            <p>上次记录：{{ formatDate(lastComplicationTime) }}</p>
            <el-button type="primary" @click="handleComplication" :disabled="!canRecordComplication">
              记录并发症
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 评估报告 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>评估报告</span>
              <el-tag type="success" v-if="canGenerateReport">可生成</el-tag>
            </div>
          </template>
          <div class="card-content">
            <p>血糖记录数：{{ bloodSugarCount }}/10</p>
            <el-button type="primary" @click="handleReport" :disabled="!canGenerateReport">
              生成报告
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 胰岛功能监测 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>胰岛功能监测</span>
              <el-tag type="success" v-if="canMonitorYdgn">可监测</el-tag>
              <el-tag type="info" v-else>需等待</el-tag>
            </div>
          </template>
          <div class="card-content">
            <p>上次监测：{{ formatDate(lastYdgnTime) }}</p>
            <p class="points-info">🏆 获得2分成长积分</p>
            <p class="limitation-info">⏰ 3个月只能监测1次</p>
            <el-button type="primary" @click="handleYdgn" :disabled="!canMonitorYdgn" size="large">
              开始监测
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 门诊随访 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>门诊随访</span>
              <el-tag type="warning">可兑换积分</el-tag>
            </div>
          </template>
          <div class="card-content">
            <p>完成随访可获得3分可兑换积分</p>
            <p class="points-info">💰 每次随访都可获得积分</p>
            <el-button type="primary" @click="handleFollowUp" size="large">
              开始随访
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 扩展活动 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>扩展活动</span>
              <el-tag type="warning">可兑换积分</el-tag>
            </div>
          </template>
          <div class="card-content">
            <p>参加活动可获得5分可兑换积分</p>
            <p class="points-info">💰 每次参加都可获得积分</p>
            <el-button type="primary" @click="handleActivity" size="large">
              参加活动
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 科研招募 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>科研招募</span>
              <el-tag type="warning">可兑换积分</el-tag>
            </div>
          </template>
          <div class="card-content">
            <p>参加研究可获得8分可兑换积分</p>
            <p class="points-info">💰 每次参加都可获得积分</p>
            <el-button type="primary" @click="handleResearch" size="large">
              参加招募
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 各种弹窗组件 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="50%">
      <component
        :is="currentDialog"
        v-if="dialogVisible"
        :currentCount="bloodSugarCount"
        @close="dialogVisible = false"
        @success="handleDialogSuccess"
      />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'
import type { UserPointsDetail } from '@/types/points'
import BloodSugarForm from '@/components/BloodSugarForm.vue'
import ComplicationForm from '@/components/ComplicationForm.vue'
import { getUserById, generateReport, followUp, ydgnNote, extendedActivity, researchRecruitment } from '@/api'

const router = useRouter()

// 用户数据
const userData = ref<UserPointsDetail>({
  id: 0,
  growScore: 0,
  exchangeScore: 0,
  scoreTotal: 0,
  lastLoginTime: '',
  profileInputed: false,
  bloodSugarCount: 0,
  lastComplicationTime: '',
  lastYdqnTime: ''
})

// 计算属性
const bloodSugarCount = computed(() => userData.value.bloodSugarCount)
const lastComplicationTime = computed(() => userData.value.lastComplicationTime)
const lastYdgnTime = computed(() => userData.value.lastYdqnTime)

const canRecordComplication = computed(() => {
  if (!lastComplicationTime.value) return true
  const lastDate = new Date(lastComplicationTime.value)
  const now = new Date()
  return now.getFullYear() > lastDate.getFullYear()
})

const canGenerateReport = computed(() => {
  return userData.value.profileInputed && userData.value.bloodSugarCount >= 10
})

const canMonitorYdgn = computed(() => {
  if (!lastYdgnTime.value) return true
  const lastDate = new Date(lastYdgnTime.value)
  const now = new Date()
  const diffMonths = (now.getFullYear() - lastDate.getFullYear()) * 12 + 
    now.getMonth() - lastDate.getMonth()
  return diffMonths >= 3
})

// 弹窗控制
const dialogVisible = ref(false)
const dialogTitle = ref('')
const currentDialog = ref<any>(null)

// 工具函数
const formatDate = (dateStr: string) => {
  if (!dateStr) return '暂无记录'
  return new Date(dateStr).toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })
}

// 导航函数
const goBack = () => {
  router.back()
}

const goToPoints = () => {
  router.push('/points')
}

// 处理函数
const handleBloodSugar = () => {
  dialogTitle.value = '记录血糖'
  currentDialog.value = BloodSugarForm
  dialogVisible.value = true
}

const handleComplication = () => {
  dialogTitle.value = '记录并发症'
  currentDialog.value = ComplicationForm
  dialogVisible.value = true
}

const handleReport = async () => {
  try {
    const res = await generateReport()
    if (res.data.code === 200) {
      ElMessage.success('评估报告生成成功')
      refreshUserData()
    }
  } catch (error) {
    console.error('生成报告失败:', error)
    ElMessage.error('生成报告失败，请重试')
  }
}

const handleYdgn = async () => {
  try {
    console.log('开始胰岛功能监测...')

    // 检查是否可以监测（3个月间隔）
    if (!canMonitorYdgn.value) {
      ElMessage.warning('距离上次监测不足3个月，请稍后再试')
      return
    }

    // 显示加载状态
    const loading = ElMessage({
      message: '正在进行胰岛功能监测...',
      type: 'info',
      duration: 0
    })

    const res = await ydgnNote()
    console.log('胰岛功能监测响应:', res)

    // 关闭加载状态
    loading.close()

    if (res.data && res.data.code === 200) {
      // 更新用户数据
      userData.value.growScore += 2
      userData.value.scoreTotal += 2
      userData.value.lastYdqnTime = new Date().toLocaleString('zh-CN')

      // 显示成功弹窗
      await ElMessageBox.alert(
        '胰岛功能监测完成！已获得2分成长积分！',
        '监测完成',
        {
          confirmButtonText: '确定',
          type: 'success',
          center: true,
          customClass: 'ydgn-success-dialog'
        }
      )

      // 异步刷新完整数据
      refreshUserData().catch(error => {
        console.error('刷新用户数据失败:', error)
      })
    } else {
      ElMessage.error(res.data?.message || '胰岛功能监测失败')
    }
  } catch (error: any) {
    console.error('胰岛功能监测失败:', error)

    if (error.response?.status === 500) {
      ElMessage.error('服务器内部错误，请稍后重试')
    } else if (error.code === 'ERR_NETWORK') {
      ElMessage.error('网络连接失败，请检查网络')
    } else {
      ElMessage.error('胰岛功能监测失败: ' + (error.message || '未知错误'))
    }
  }
}

const handleFollowUp = async () => {
  try {
    console.log('开始门诊随访...')

    // 显示加载状态
    const loading = ElMessage({
      message: '正在处理随访...',
      type: 'info',
      duration: 0
    })

    const res = await followUp()
    console.log('门诊随访响应:', res)

    // 关闭加载状态
    loading.close()

    if (res.data && res.data.code === 200) {
      // 更新用户数据
      userData.value.exchangeScore += 3
      userData.value.scoreTotal += 3

      // 显示成功弹窗
      await ElMessageBox.alert(
        '恭喜您完成门诊随访！已获得3分可兑换积分！',
        '随访完成',
        {
          confirmButtonText: '确定',
          type: 'success',
          center: true,
          customClass: 'follow-up-success-dialog'
        }
      )

      // 异步刷新完整数据
      refreshUserData().catch(error => {
        console.error('刷新用户数据失败:', error)
      })
    } else {
      ElMessage.error(res.data?.message || '随访记录失败')
    }
  } catch (error: any) {
    console.error('随访记录失败:', error)

    if (error.response?.status === 500) {
      ElMessage.error('服务器内部错误，请稍后重试')
    } else if (error.code === 'ERR_NETWORK') {
      ElMessage.error('网络连接失败，请检查网络')
    } else {
      ElMessage.error('随访记录失败: ' + (error.message || '未知错误'))
    }
  }
}

const handleActivity = async () => {
  try {
    console.log('开始参加扩展活动...')

    // 显示加载状态
    const loading = ElMessage({
      message: '正在参加扩展活动...',
      type: 'info',
      duration: 0
    })

    const res = await extendedActivity()
    console.log('扩展活动响应:', res)

    // 关闭加载状态
    loading.close()

    if (res.data && res.data.code === 200) {
      // 更新用户数据
      userData.value.exchangeScore += 5
      userData.value.scoreTotal += 5

      // 显示成功弹窗
      await ElMessageBox.alert(
        '扩展活动参加成功！已获得5分可兑换积分！',
        '活动完成',
        {
          confirmButtonText: '确定',
          type: 'success',
          center: true,
          customClass: 'activity-success-dialog'
        }
      )

      // 异步刷新完整数据
      refreshUserData().catch(error => {
        console.error('刷新用户数据失败:', error)
      })
    } else {
      ElMessage.error(res.data?.message || '参加扩展活动失败')
    }
  } catch (error: any) {
    console.error('参加扩展活动失败:', error)

    if (error.response?.status === 500) {
      ElMessage.error('服务器内部错误，请稍后重试')
    } else if (error.code === 'ERR_NETWORK') {
      ElMessage.error('网络连接失败，请检查网络')
    } else {
      ElMessage.error('参加扩展活动失败: ' + (error.message || '未知错误'))
    }
  }
}

const handleResearch = async () => {
  try {
    console.log('开始参加科研招募...')

    // 显示加载状态
    const loading = ElMessage({
      message: '正在参加科研招募...',
      type: 'info',
      duration: 0
    })

    const res = await researchRecruitment()
    console.log('科研招募响应:', res)

    // 关闭加载状态
    loading.close()

    if (res.data && res.data.code === 200) {
      // 更新用户数据
      userData.value.exchangeScore += 8
      userData.value.scoreTotal += 8

      // 显示成功弹窗
      await ElMessageBox.alert(
        '科研招募参加成功！已获得8分可兑换积分！',
        '招募完成',
        {
          confirmButtonText: '确定',
          type: 'success',
          center: true,
          customClass: 'research-success-dialog'
        }
      )

      // 异步刷新完整数据
      refreshUserData().catch(error => {
        console.error('刷新用户数据失败:', error)
      })
    } else {
      ElMessage.error(res.data?.message || '参加科研招募失败')
    }
  } catch (error: any) {
    console.error('参加科研招募失败:', error)

    if (error.response?.status === 500) {
      ElMessage.error('服务器内部错误，请稍后重试')
    } else if (error.code === 'ERR_NETWORK') {
      ElMessage.error('网络连接失败，请检查网络')
    } else {
      ElMessage.error('参加科研招募失败: ' + (error.message || '未知错误'))
    }
  }
}

const handleDialogSuccess = () => {
  // 根据当前对话框类型更新数据
  if (currentDialog.value === BloodSugarForm) {
    // 血糖记录成功，增加记录数
    userData.value.bloodSugarCount += 1
  } else if (currentDialog.value === ComplicationForm) {
    // 并发症记录成功，更新最后记录时间
    userData.value.lastComplicationTime = new Date().toLocaleString('zh-CN')
  }

  // 关闭对话框
  dialogVisible.value = false

  // 异步刷新完整数据
  refreshUserData().catch(error => {
    console.error('刷新用户数据失败:', error)
  })
}

// 获取用户数据
const refreshUserData = async () => {
  try {
    const userId = localStorage.getItem('userId')
    if (userId) {
      const res = await getUserById(userId)
      if (res.data.code === 200) {
        userData.value = res.data.data
      }
    }
  } catch (error) {
    console.error('获取用户数据失败:', error)
    ElMessage.error('获取用户数据失败，请重试')
  }
}

// 在组件挂载时获取用户数据
onMounted(() => {
  refreshUserData()
})
</script>

<style scoped>
.behavior-container {
  padding: 20px;
}

.nav-bar {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.behavior-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-content {
  text-align: center;
}

.card-content p {
  margin-bottom: 15px;
  color: #606266;
}

.points-info {
  font-size: 14px;
  color: #E6A23C;
  font-weight: 500;
  margin-bottom: 20px !important;
}

.limitation-info {
  font-size: 12px;
  color: #909399;
  margin-bottom: 15px !important;
}

/* 成功弹窗样式 */
:deep(.follow-up-success-dialog),
:deep(.ydgn-success-dialog),
:deep(.activity-success-dialog),
:deep(.research-success-dialog) {
  .el-message-box__title {
    font-size: 20px;
    font-weight: bold;
    color: #67C23A;
  }

  .el-message-box__content {
    font-size: 16px;
    padding: 20px 0;
  }

  .el-message-box__btns {
    padding-top: 20px;
  }

  .el-button--primary {
    background-color: #67C23A;
    border-color: #67C23A;
    padding: 12px 30px;
    font-size: 16px;
  }
}
</style> 