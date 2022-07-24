from typing import List
import mahjong.Mahjong as m

def getListenCard(handscards: List[m.Mahjong]):
    assert(len(handscards) == 13)
    handscards = sorted(handscards)
    normals = []
    flowersize = 0
    for item in handscards:
        if item.type == 5:
            flowersize = flowersize + 1
        else:
            normals.append(item)
    return flowerListenCheck(normals, flowersize)

def flowerListenCheck(normals: List[m.Mahjong], flowersize: int):
    baseMahjongs = m.baseMahjongs
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
            if checkInputWinWithFlower(nest, normals, item):
                wins.append(item)
        else:
            copyNormals = normals.copy()
            copyNormals.append(item)
            copyNormals.sort()
            if m.checkInputWin(copyNormals, [], 4, 1):
                wins.append(item)
    print(wins)
    return wins

# 循环改递归
def checkInputWinWithFlower(nest: List[int], normals: List[m.Mahjong], item: m.Mahjong):
    baseSize = len(m.baseMahjongs)
    if checkNestOver(nest, baseSize):
        return False
    copyNormals = normals.copy()
    copyNormals.append(item)
    for cursor in nest:
        copyNormals.append(m.baseMahjongs[cursor])
    copyNormals = sorted(copyNormals)
    if m.checkInputWin(copyNormals, [], 4, 1):
        return True
    nestAppend(nest, baseSize)
    return checkInputWinWithFlower(nest, normals, item)

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

def checkCase(inputCase: str):
    inputMajongLists = inputCase.split(",")
    listenCards = getListenCard(m.inputConvert(inputMajongLists[0]))
    resultCards = m.inputConvert(inputMajongLists[1])
    for item in listenCards:
        assert(resultCards[listenCards.index(item)] == item)

checkCase("223344-234-2234,--25")
checkCase("1112345678999,123456789")
checkCase("23456-22233-789,147")
checkCase("123456-23444-55,-14-5")
checkCase("1112378999-123--,1469")
checkCase("2344445688999,178")
checkCase("223344-334--222-1,-23456")
checkCase("1234489-147--123,null")
checkCase("-123456789---1234,all")