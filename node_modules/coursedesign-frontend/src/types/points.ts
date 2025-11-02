// 积分类型
export interface Points {
  growthPoints: number;      // 成长积分
  exchangeablePoints: number; // 可兑换积分
  totalPoints: number;       // 总积分
}

// 积分历史记录
export interface PointsHistory {
  id: number;
  type: 'growth' | 'exchangeable'; // 积分类型
  points: number;                   // 积分数量
  description: string;              // 积分描述
  createTime: string;              // 获得时间
}

// 积分规则
export interface PointsRule {
  category: string;    // 分类
  behavior: string;    // 行为
  points: number;      // 分值
  limitation: string;  // 限制
}

// 用户等级
export interface UserLevel {
  level: 'A' | 'B' | 'C';  // 等级
  points: number;          // 当前积分
  nextLevelPoints?: number; // 下一等级所需积分
}

// 可兑换积分
export interface ExchangeablePoint {
  id: number;
  points: number;          // 积分数量
  expireTime: string;      // 过期时间
  createTime: string;      // 获得时间
}

// 用户积分详情
export interface UserPointsDetail {
  id: number;                    // 用户ID
  growScore: number;             // 成长积分
  exchangeScore: number;         // 可兑换积分
  scoreTotal: number;            // 总积分
  lastLoginTime: string;         // 最后登录时间
  profileInputed: boolean;       // 是否已填写个人资料
  bloodSugarCount: number;       // 血糖记录数量
  lastComplicationTime: string;  // 最后并发症记录时间
  lastYdqnTime: string;         // 最后胰岛功能监测时间
} 