from re import T
from typing import List

class Mahjong:
    def __init__(self, type: int, value: int):
        self.type = type
        self.value = value

    def __repr__(self):
        if self.type == 1:
            assert(self.value >= 1 and self.value <= 9)
            showStr = "%s%s" % (self.value, '万')
        elif self.type == 2:
            assert(self.value >= 1 and self.value <= 9)
            showStr = "%s%s" % (self.value, '筒')
        elif self.type == 3:
            assert(self.value >= 1 and self.value <= 9)
            showStr = "%s%s" % (self.value, '条')
        elif self.type == 4:
            assert(self.value >= 1 and self.value <= 8)
            showStr = self.__getType4Name(self.value)
            
        else:
            raise Exception("错误的牌型%s" % self.type)
        return showStr
    
    def __hash__(self) -> int:
        return self.type * 10 + self.value

    def __eq__(self, __o: object):
        return self.type == __o.type and self.value == __o.value

    def __ne__(self, __o: object):
        if self.type > __o.type:
            return True
        elif self.type == __o.type:
            return self.value > __o.value
        else:
            return False

    def __lt__(self, __o: object):
        if self.type < __o.type:
            return True
        elif self.type == __o.type:
            return self.value < __o.value
        else:
            return False

    def __getType4Name(value: int):
        showName=""
        if value == 1:
            showName = "春"
        elif value == 2:
            showName = "夏"
        elif value == 3:
            showName = "秋"
        elif value == 4:
            showName = "冬"
        elif value == 5:
            showName = "梅"
        elif value == 6:
            showName = "兰"
        elif value == 7:
            showName = "竹"
        elif value == 8:
            showName = "菊"
        else:
            raise Exception("错误的牌")
        return showName

def getBaseMahjongs()->List[Mahjong]:
    mahjongs = []
    for type in range(1, 4):
        for value in range(1, 10):
            mahjongs.append(Mahjong(type, value))
    return mahjongs

def inputConvert(inputstr: str)->List[Mahjong]:
    mahjongs = []
    mahjongType = 1
    for index in range(0, len(inputstr)):
        if inputstr[index] == '-':
            mahjongType = mahjongType + 1
        else:
            mahjongs.append(Mahjong(mahjongType, int(inputstr[index])))
    return mahjongs

def outputConvert(outputMahjongs: List[Mahjong]):
    mahjongType = 1
    outputstr = ""
    for item in outputMahjongs:
        while (mahjongType != item.type):
            outputstr = outputstr + ("-")
            mahjongType = mahjongType + 1
        outputstr = outputstr + ("%s" % item.value)
            
    return outputstr

def checkInputWin(input: List[Mahjong], output: List[List[Mahjong]], triplets: int, couples: int):
    if triplets == 0 and couples == 0:
        return True
    inputLen = len(input)
    firstCard = input[0]
    tripletsList = []
    couplesList=[]
    #找到所有第一张牌的3张组合牌和对牌
    for second in range(1, inputLen):
        secondCard = input[second]
        if secondCard.type != firstCard.type or secondCard.value - firstCard.value > 1:
            break
        if couples > 0 and secondCard == firstCard:
            couplesList.append([firstCard, secondCard])
        for third in range(second + 1, inputLen):
            thirdCard = input[third]
            if thirdCard.type != secondCard.type or thirdCard.value - secondCard.value > 1:
                break
            # 刻
            if firstCard == secondCard and secondCard == thirdCard:
                tripletsList.append([firstCard, secondCard, thirdCard])
            # 顺
            if thirdCard.value - secondCard.value == 1 and secondCard.value - firstCard.value == 1:
                tripletsList.append([firstCard, secondCard, thirdCard])
    
    checkRight = False
    for triplet in tripletsList:
        copyInput = input.copy()
        copyOutput = output.copy()
        for item in triplet:
            copyInput.remove(item)
        copyOutput.append(triplet)
        if checkInputWin(copyInput, copyOutput, triplets - 1, couples):
            checkRight = True
            break
    if len(couplesList) > 0:
        copyInput = input.copy()
        copyOutput = output.copy()
        for item in couplesList[0]:
            copyInput.remove(item)
        copyOutput.append(couplesList[0])
        if checkInputWin(copyInput, copyOutput, triplets, couples - 1):
            checkRight = True
    return checkRight