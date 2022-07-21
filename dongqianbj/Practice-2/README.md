### 解题思路

- 尝试所有可能的上牌，判断是否可胡牌
- 胡牌模版：XYZ * m + PPP * n + QQ
  - 先剔除对子，在剔除所有刻子，在剔除所有顺子
  - 如果可全部剔除，说明满足胡牌条件

### 文件结构

`cases.js` 测试用例
`constant.js` 常量
`index.js 逻辑`

### 执行

node index.js

### 输出

`listen` 听牌
`count` 可胡牌型总数
`percent` 可胡牌型占比