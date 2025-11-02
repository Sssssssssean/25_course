# Vue 3 + TypeScript + Vite

This template should help get you started developing with Vue 3 and TypeScript in Vite. The template uses Vue 3 `<script setup>` SFCs, check out the [script setup docs](https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup) to learn more.

Learn more about the recommended Project Setup and IDE Support in the [Vue Docs TypeScript Guide](https://vuejs.org/guide/typescript/overview.html#project-setup).



### Src/

```
src/
├── api/                          # 所有前端请求接口封装
│   ├── user.ts                   # 用户相关接口（/user）
│   └── bfzNote.ts                # 胰岛功能接口（/bfzNode）
│
├── views/                        # 页面组件（每个 Vue 页面一个功能）
│   ├── DashboardView.vue             # 首页 / 仪表盘
│   ├── UserProfileView.vue           # 个人资料页面
│   ├── BloodSugarView.vue            # 血糖记录页面
│   ├── ComplicationView.vue          # 并发症记录页面
│   ├── AssessmentReportView.vue      # 评估报告页面
│   ├── YdgnNoteView.vue              # 胰岛功能监测页面
│   ├── FollowUpView.vue              # 门诊随访页面
│   ├── ExtendedActivityView.vue      # 扩展活动页面
│   └── ResearchRecruitmentView.vue   # 科研招募页面
│
├── components/                  # 可复用 UI 组件
│   ├── UserForm.vue                 # 用户表单（新增 / 编辑）
│   ├── BloodSugarForm.vue           # 血糖记录表单
│   └── ScoreCard.vue                # 当前积分卡片
│
└── router/                     # 路由配置
    └── index.ts                   # Vue Router 路由表配置
```

