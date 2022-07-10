from mimetypes import init
from operator import truediv
from re import T
import threading
from traceback import print_list
from typing import List

# 麻将牌定义。type：牌类别：万，筒，条。value：牌值[1, 9]
class Mahjong:
    def __init__(self, type: int, value: int):
        self.type = type
        self.value = value

    def __repr__(self):
        if self.type == 1:
            typeStr = '万'
        elif self.type == 2:
            typeStr = '筒'
        elif self.type == 3:
            typeStr = "条"
        else:
            raise Exception("错误的牌型%s" % self.type)
        return "%s%s" % (self.value, typeStr)

    def __eq__(self, __o: object):
        return self.type == __o.type and self.value == __o.value

    def __ne__(self, __o: object):
        if self.type > __o.type:
            return True
        if self.value > __o.value:
            return True
        return False

    def __lt__(self, __o: object):
        if self.type < __o.type:
            return True
        if self.value < __o.value:
            return True
        return False

# input:手牌, output:听的牌数组
def listenToCard(handcards: List[Mahjong], hasTrump: bool):
    listenList: List[Mahjong] = []
    # sort
    handcards.sort()
    handcardsLen = len(handcards)
    #大于2张
    if handcardsLen > 2:
        for first in range(0, handcardsLen):
            #寻找第i张牌所有可能的刻，顺组合。
            tripletsList = []
            #寻找对组合,如果没有对，则寻找对组合。
            couplesList = []
            firstCard = handcards[first]
            for second in range(first + 1, handcardsLen):
                secondCard = handcards[second]
                if hasTrump == False and secondCard.__eq__(firstCard):
                    couplesList.append([firstCard, secondCard])
                for thrid in range(second + 1, handcardsLen):
                    thirdCard = handcards[thrid]
                    if firstCard.__eq__( secondCard) and firstCard.__eq__(thirdCard):
                        tripletsList.append([firstCard, secondCard, thirdCard])
                    elif firstCard.type == secondCard.type and firstCard.type == thirdCard.type and firstCard.value - secondCard.value == -1 and secondCard.value - thirdCard.value == -1:
                        tripletsList.append([firstCard, secondCard, thirdCard])
            # 刻或顺组合
            for triplets in tripletsList:
                nextHandcards = handcards.copy()
                for item in triplets:
                    nextHandcards.remove(item)
                for item in listenToCard(nextHandcards, hasTrump):
                    if listenList.__contains__(item) == False:
                        listenList.append(item)
            # 对组合
            for couples in couplesList:
                nextHandcards = handcards.copy()
                for item in couples:
                    nextHandcards.remove(item)
                for item in listenToCard(nextHandcards, True):
                    if listenList.__contains__(item) == False:
                        listenList.append(item)
    #剩两张，需要组刻或者顺
    elif handcardsLen == 2:
        card1 = handcards[0]
        card2 = handcards[1]
        if card1.type == card2.type:
            if card1.value == card2.value:
                listenList.append(card1)
            elif card1.value - card2.value == 1:
                if card2.value > 1:
                    listenList.append(Mahjong(card2.type, card2.value - 1))
                if card1.value < 9:
                    listenList.append(Mahjong(card1.type, card1.value + 1))
            elif card1.value - card2.value == -1:
                if card1.value > 1:
                    listenList.append(Mahjong(card1.type, card1.value - 1))
                if card2.value < 9:
                    listenList.append(Mahjong(card2.type, card2.value + 1))
    #剩下一张，需要组成对
    elif handcardsLen == 1:
        card1 = handcards[0]
        listenList.append(Mahjong(card1.type, card1.value))
    else:
        print(handcardsLen)
        raise Exception("不符合规则")
    # 去重
    #校验听牌的合理性。同一张牌最多四张。去重
    resultList = []
    for item in listenList:
        repeat = 0
        contains = False
        for hands in handcards:
            if hands.__eq__(item):
                repeat = repeat+1
        for result in resultList:
            if item.__eq__(result):
                contains = True
        if (repeat < 4) and contains == False:
            resultList.append(item)
    resultList.sort()
    return resultList


#九宝灯莲
testCase1 = [Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 2), Mahjong(1, 3),Mahjong(1, 4),Mahjong(1, 5),Mahjong(1, 6),Mahjong(1, 7),Mahjong(1, 8),Mahjong(1, 9),Mahjong(1, 9),Mahjong(1, 9)]
#不停牌
testCase2 = [Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 3),Mahjong(1, 4),Mahjong(1, 5),Mahjong(1, 6),Mahjong(1, 7),Mahjong(1, 8),Mahjong(1, 9),Mahjong(1, 9),Mahjong(1, 9)]
#听对
testCase3 = [Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 2), Mahjong(1, 2),Mahjong(1, 2),Mahjong(1, 3),Mahjong(1, 3),Mahjong(1, 3),Mahjong(1, 4),Mahjong(1, 4),Mahjong(1, 4),Mahjong(2, 2)]
#两面听
testCase4 = [Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 2), Mahjong(1, 2),Mahjong(1, 2),Mahjong(1, 3),Mahjong(1, 3),Mahjong(1, 3),Mahjong(1, 6),Mahjong(1, 7),Mahjong(2, 2),Mahjong(2, 2)]
#三面听
testCase4 = [Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 2), Mahjong(1, 2),Mahjong(1, 2),Mahjong(3, 3),Mahjong(3, 3),Mahjong(2, 6),Mahjong(2, 2),Mahjong(2, 3),Mahjong(2, 4),Mahjong(2, 5)]
print(listenToCard(testCase1, False))
print(listenToCard(testCase2, False))
print(listenToCard(testCase3, False))
print(listenToCard(testCase4, False))