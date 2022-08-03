

### 麻将四人玩家绘制流程图

```flow
start=>start: 分得13张手牌
takeMyTurn=>operation: Player摸牌A
cond=>condition: Player是否没自摸胡
abandonMyTurn=>operation: Player弃牌B
checkAnyoneNeed=>condition: 是否Player Any吃或碰牌B
someoneNeed=>operation: Player Any吃或碰牌B，Player = Any
playerIsWinAfterNeed=>condition: Player Any吃牌后是否胡牌
nextPlayerTurn=>operation: 轮到下一家 Player = Player+1 % 4
end=>end: 胡牌

start->takeMyTurn->cond
cond(yes)->abandonMyTurn
cond(no)->end
abandonMyTurn->checkAnyoneNeed
checkAnyoneNeed(yes)->someoneNeed
checkAnyoneNeed(no)->nextPlayerTurn
someoneNeed->playerIsWinAfterNeed
playerIsWinAfterNeed(yes)->end
playerIsWinAfterNeed(no)->nextPlayerTurn
nextPlayerTurn->takeMyTurn
```

自动加一的轮数 = 总剩余牌数 = 136张牌 - 13张 * 4Player

洗牌初始化shuffle
While (总剩余牌数>0) {
计算Player的听牌列表
摸牌：总剩余牌数 -= 1
判断是否胡：if 在听牌状态 && 摸牌在听牌列表 -> 胡了 return true；
	    	       else -> 弃牌
弃牌：丢弃配对可能（对刻顺）最少的牌
判断任意一家要吃/碰牌：if (该名玩家的牌能组成新的对刻顺 && threeCount > 0 && pairCount > 0)
					    	吃牌/碰牌成功：threeCount-1，总剩余牌数不减少（因为只是弃牌和手牌的互换）
					    	跳到该名吃牌/碰牌玩家的下一家，进行下一轮游戏
					    else 不跳过任何玩家，正常进行下一轮游戏
}
牌用尽，没有人胡 return false

