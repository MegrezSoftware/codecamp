

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

## 总流程

自动加一的轮数 = 总剩余牌数 = 136张牌 - 13张 * 4Player

洗牌初始化shuffle
While (总剩余牌数>0) {
计算当前Player的输赢生成听牌列表
摸牌：总剩余牌数 -= 1
判断是否胡：if 在听牌状态 && 摸牌在听牌列表 -> 胡了 
				return true；
	    	       else -> 弃牌
弃牌：丢弃配对可能（对刻顺）最少的牌
判断任意一家要吃/碰牌：if (该名玩家的牌能组成新的对刻顺 && threeCount > 0)
					    	吃牌/碰牌成功：threeCount-1，总剩余牌数不减少（因为只是弃牌和手牌的互换）
					    	跳到该名吃牌/碰牌玩家的下一家，进行下一轮游戏
					    else 不跳过任何玩家，正常进行下一轮游戏
}
牌用尽，没有人胡 return false

## 决定弃牌

摸牌+弃牌阶段是最复杂的，因为要打出当前最不可能赢的牌。
一副牌由：
1、顺和刻的个数2、对儿3、差一张的牌（A*C，AB/BC）
如果假设N=手上还需要替换掉才能赢的牌数目，那么N最大的手牌需要被弃牌，表示需要替换掉的牌越多N = 2 * (4 - threesCount) + 差一张的牌 + abs(doubleCount - 1)摸牌后有14张手牌，对每个手牌：
```
Fun getCardToDiscard(inputArray, ) {
	for (eachCard in inputArray) {
		val card13List = inputArray - eachCard
		howManyCardNeededToWin(card13List, depth = 0, threeCount = 0, missingOneCount = 0, doubleCount = 0, resultNeededCount = 13)
	}
}

Fun howManyCardNeededToWin(remainArray,  depth, threeCount, missingOneCount, doubleCount, resultNeededCount) : Int {
	if (depth >= remainArray.size) {
		resultNeededCount = min(resultNeededCount, 2* (4 - threeCount) - missingOneCount + abs(doubleCount - 1))
		return resultNeededCount
	}
	var minResult = resultNeededCount	if (拿顺/刻) {
		val res = howManyCardNeededToWin(card13List, depth = depth, threeCount = threeCount + 1, missingOneCount = missingOneCount, doubleCount = doubleCount, resultNeededCount = resultNeededCount)
		minResult = min(res, minResult)
	}
	if (拿对儿) {
		val res = howManyCardNeededToWin(card13List, depth = depth, threeCount = threeCount, missingOneCount = missingOneCount, doubleCount = doubleCount + 1, resultNeededCount = resultNeededCount)
		minResult = min(res, minResult)
	}
	if (拿差一张的情况) {
		val res = howManyCardNeededToWin(card13List, depth = depth, threeCount = threeCount, missingOneCount = missingOneCount + 1, doubleCount = doubleCount, resultNeededCount = resultNeededCount)		minResult = min(res, minResult)
	}	val res = howManyCardNeededToWin(card13List, depth = depth + 1, threeCount = threeCount, missingOneCount = missingOneCount + 1, doubleCount = doubleCount, resultNeededCount = resultNeededCount)
	minResult = min(res, minResult)
	return minResult
}
```

