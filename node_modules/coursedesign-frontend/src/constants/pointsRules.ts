import type { PointsRule } from '@/types/points'

export const POINTS_RULES: PointsRule[] = [
  // 成长积分规则
  {
    category: '成长积分',
    behavior: '登陆平台',
    points: 1,
    limitation: '每日首次登陆获得'
  },
  {
    category: '成长积分',
    behavior: '填写个人资料',
    points: 2,
    limitation: '首次填写获得'
  },
  {
    category: '成长积分',
    behavior: '记录血糖',
    points: 1,
    limitation: '填写血糖记录大于3，积1分'
  },
  {
    category: '成长积分',
    behavior: '填写并发症记录',
    points: 3,
    limitation: '并发症记录一般每年填写1次，每年只计分1次'
  },
  {
    category: '成长积分',
    behavior: '生成评估报告',
    points: 2,
    limitation: '需要先填写好个人资料，血糖记录数填写大于等于10'
  },
  {
    category: '成长积分',
    behavior: '监测胰岛功能',
    points: 2,
    limitation: '胰岛功能3个月只积分1次'
  },

  // 可兑换积分规则
  {
    category: '可兑换积分',
    behavior: '完成门诊随访',
    points: 3,
    limitation: '无限制'
  },
  {
    category: '可兑换积分',
    behavior: '参加扩展活动',
    points: 5,
    limitation: '无限制'
  },
  {
    category: '可兑换积分',
    behavior: '参加科研招募',
    points: 8,
    limitation: '无限制'
  }
]

// 等级规则
export const LEVEL_RULES = {
  A: {
    min: 25,
    color: '#67C23A'
  },
  B: {
    min: 11,
    max: 24,
    color: '#E6A23C'
  },
  C: {
    max: 10,
    color: '#909399'
  }
} 