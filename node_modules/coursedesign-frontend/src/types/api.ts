// API响应基础类型
export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
}

// 分页响应类型
export interface PageResponse<T> extends ApiResponse {
  data: {
    list: T[]
    total: number
    page: number
    size: number
  }
} 