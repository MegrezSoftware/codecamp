from operator import le
from re import I
from termios import TIOCPKT_DOSTOP
from typing import List
import mahjong.Mahjong as m

def getListenCard(cardsstr: str):
    handscards = m.inputConvert(cardsstr) 
    assert(len(handscards) == 13)
    handscards = sorted(handscards)
    normals = []
    flowersize = 0
    for item in handscards:
        if item.type == 4:
            flowersize = flowersize + 1
        else:
            normals.append(item)
    return flowerListenCheck(normals, flowersize)

def flowerListenCheck(normals: List[m.Mahjong], flowersize: int):
    baseMahjongs = m.getBaseMahjongs()
    countMap = {}
    for item in baseMahjongs:
        countMap[item] = 0
    for item in normals:
        countMap[item] = countMap[item] + 1
    checkWins = []
    wins = []
    for item in baseMahjongs:
        if countMap[item] < 4:
            checkWins.append(item)
    # 检查最后一张牌是否可以胡牌
    for item in checkWins:
        nest = []
        for i in range(0, flowersize):
            nest.append(0)
        if (len(nest) > 0):
            # 组合万能牌的所有情形，可以胡牌则退出
            while(checkNestOver(nest, len(baseMahjongs)) == False):
                copyNormals = normals.copy()
                copyNormals.append(item)
                for cursor in nest:
                    copyNormals.append(baseMahjongs[cursor])
                copyNormals = sorted(copyNormals)
                if m.checkInputWin(copyNormals, [], 4, 1):
                    wins.append(item)
                    break
                nestAppend(nest, len(baseMahjongs))
            else:
                copyNormals = normals.copy()
                if m.checkInputWin(copyNormals, [], 4, 1):
                    wins.append(item)
    print(wins)
    result = ""
    if (len(wins) == 0):
        result = "null"
    elif len(wins) == len(baseMahjongs):
        result = "all"
    else:
        result = m.outputConvert(wins)
    print(result)
    return result

def checkNestOver(nest: List[int], baseSize: int):
    return nest[0] >= baseSize
#模仿进制计算自增
def nestAppend(nest: List[int], baseSize: int):
    nestLen = len(nest)
    nest[nestLen - 1] = nest[nestLen - 1] + 1
    #非最高位检查进位
    for index in range(1, nestLen):
        if (nest[nestLen - index] >= baseSize):
            nest[nestLen - index] = 0
            nest[nestLen - index - 1] = nest[nestLen - index - 1] + 1

#case1: 223344-234-223-1,--12345
assert(getListenCard("223344-234-223-1")=="--12345")
#case2: 1234489-147-1234,null
assert(getListenCard("1234489-147-123")=="null")
#case3: 123456789---1234,all
assert(getListenCard("123456789---1234")=="all")