import request from '@/utils/request'
import type { UserPointsDetail } from '@/types/points'
import type { ApiResponse } from '@/types/api'

// 登录接口
export function login(userId: string) {
  return request<ApiResponse>({
    url: '/account/login',
    method: 'post',
    params: { 'X-userId': userId }
  })
}

// 填写资料
export function fillProfile(data: any) {
  return request<ApiResponse>({
    url: '/information/fill',
    method: 'post',
    data
  })
}

// 生成评估报告
export function generateReport() {
  return request<ApiResponse>({
    url: '/evaluateReport',
    method: 'get'
  })
}

// 跟进操作
export function followUp() {
  return request<ApiResponse>({
    url: '/followUp',
    method: 'get'
  })
}

// 胰岛功能监测
export function ydgnNote() {
  return request<ApiResponse>({
    url: '/YdgnNote',
    method: 'post'
  })
}

// 扩展活动
export function extendedActivity() {
  return request<ApiResponse>({
    url: '/extendedActivity',
    method: 'get'
  })
}

// 科研招募
export function researchRecruitment() {
  return request<ApiResponse>({
    url: '/researchRecruitment',
    method: 'get'
  })
}

// 测试设计
export function testDesign(id: number) {
  return request<ApiResponse>({
    url: `/testDesign/${id}`,
    method: 'put'
  })
}

// 查询所有用户
export function getAllUsers() {
  return request<ApiResponse>({
    url: '/user',
    method: 'get'
  })
}

// 查询指定用户
export function getUserById(id: string) {
  return request<ApiResponse<UserPointsDetail>>({
    url: `/user/query/${parseInt(id)}`,
    method: 'get'
  })
}

// 添加积分用户
export function addUser(data: any) {
  return request<ApiResponse>({
    url: '/user',
    method: 'post',
    data
  })
}

// 删除积分记录
export function deleteUser(id: number) {
  return request<ApiResponse>({
    url: `/user/${id}`,
    method: 'delete'
  })
}

// 更新积分记录
export function updateUser(id: number, data: any) {
  return request<ApiResponse>({
    url: `/user/${id}`,
    method: 'post',
    data
  })
}

// 获取用户积分详情
export function getUserPointsDetail() {
  return request<ApiResponse<UserPointsDetail>>({
    url: '/user/query/current',
    method: 'get'
  })
} 