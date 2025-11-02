import request from '@/utils/request'
import type { UserPointsDetail } from '@/types/points'

// 获取用户积分信息
export function getUserPoints() {
  return request({
    url: '/api/points/user',
    method: 'get'
  })
}

// 获取积分历史记录
export function getPointsHistory(params: any) {
  return request({
    url: '/api/points/history',
    method: 'get',
    params
  })
}

// 获取积分规则
export function getPointsRules() {
  return request({
    url: '/api/points/rules',
    method: 'get'
  })
}

// 获取用户等级信息
export function getUserLevel() {
  return request({
    url: '/api/points/level',
    method: 'get'
  })
}

// 获取可兑换积分列表
export function getExchangeablePoints() {
  return request({
    url: '/api/points/exchangeable',
    method: 'get'
  })
}

// 获取用户积分详情
export function getUserPointsDetail() {
  return request<UserPointsDetail>({
    url: '/api/points/detail',
    method: 'get'
  })
} 