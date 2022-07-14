from re import T
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

# 检查输入的牌听的牌
def listenToCard(handcards: List[Mahjong], hasTrump: bool):
    listenList: List[Mahjong] = []
    # sort
    handcards.sort()
    handcardsLen = len(handcards)
    #大于2张
    if handcardsLen > 2:
        firstCard = handcards[0]
        tripletsList = []
        couples=[]
        #可以和第一张牌组合的有：1对，刻，顺
        for second in range(1, handcardsLen):
            secondCard = handcards[second]
            if secondCard.type != firstCard.type or secondCard.value - firstCard.value > 1:
                break
            if hasTrump == False and secondCard == firstCard:
                couples.append([firstCard, secondCard])
            for third in range(second + 1, handcardsLen):
                thirdCard = handcards[third]
                if thirdCard.type != secondCard.type or thirdCard.value - secondCard.value > 1:
                    break
                # 刻
                if firstCard == secondCard and secondCard == thirdCard:
                    tripletsList.append([firstCard, secondCard, thirdCard])
                # 顺
                if thirdCard.value - secondCard.value == 1 and secondCard.value - firstCard.value == 1:
                    tripletsList.append([firstCard, secondCard, thirdCard])
        for triplet in tripletsList:
            copyLast = handcards.copy()
            for item in triplet:
                copyLast.remove(item)
            listenList.extend(listenToCard(copyLast, hasTrump))
        if len(couples) > 0:
            copyLast = handcards.copy()
            for item in couples[0]:
                copyLast.remove(item)
            listenList.extend(listenToCard(copyLast, True))
        #缺一对
        copyLast = handcards.copy()
        copyLast.remove(firstCard)
        if checkLastRight(copyLast, True):
            listenList.append(firstCard)
        #缺一刻
        secondCard = handcards[1]
        if (firstCard == secondCard):
            copyLast = handcards.copy()
            copyLast.remove(firstCard)
            copyLast.remove(secondCard)
            if checkLastRight(copyLast, hasTrump):
                listenList.append(firstCard)
        #缺一顺，
        for second in range(1, handcardsLen):
            secondCard = handcards[second]
            if (secondCard != firstCard):
                if (secondCard.type != firstCard.type):
                    break
                checkListens = []
                if (secondCard.value - firstCard.value == 1):
                    if (firstCard.value == 1):
                        checkListens.append(Mahjong(firstCard.type, secondCard.value + 1))
                    elif secondCard.value == 9:
                        checkListens.append(Mahjong(firstCard.type, firstCard.value - 1))
                    else:
                        checkListens.append(Mahjong(firstCard.type, secondCard.value + 1))
                        checkListens.append(Mahjong(firstCard.type, firstCard.value - 1))
                elif (secondCard.value - firstCard.value == 2):
                    checkListens.append(Mahjong(firstCard.type, firstCard.value + 1))
                else:
                    break
                copyLast = handcards.copy()
                copyLast.remove(firstCard)
                copyLast.remove(secondCard)
                if checkLastRight(copyLast, hasTrump):
                    listenList.extend(checkListens)
    #剩两张，需要组刻或者顺
    elif handcardsLen == 2:
        card1 = handcards[0]
        card2 = handcards[1]
        if card1.type == card2.type:
            if card1.value == card2.value:
                listenList.append(card1)
            elif card2.value - card1.value == 1:
                if card1.value > 1:
                    listenList.append(Mahjong(card2.type, card1.value - 1))
                if card2.value < 9:
                    listenList.append(Mahjong(card1.type, card2.value + 1))
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
            if hands == item:
                repeat = repeat+1
        for result in resultList:
            if item == result:
                contains = True
        if (repeat < 4) and contains == False:
            resultList.append(item)
    resultList.sort()
    return resultList

# 检查剩余牌是否满足规则（0或1对 + N个顺或刻）：
def checkLastRight(lastCards: List[Mahjong], hasTrump: bool):
    lastLen = len(lastCards)
    if lastLen == 0:
        return True
    if hasTrump:
        if lastLen % 3 != 0:
            return False
    else:
        if ((lastLen - 2) % 3 != 0):
            return False
    firstCard = lastCards[0]
    tripletsList = []
    couples=[]
    for second in range(1, lastLen):
        secondCard = lastCards[second]
        if secondCard.type != firstCard.type or secondCard.value - firstCard.value > 1:
            break
        if hasTrump == False and secondCard == firstCard:
            couples.append([firstCard, secondCard])
        for third in range(second + 1, lastLen):
            thirdCard = lastCards[third]
            if thirdCard.type != secondCard.type or thirdCard.value - secondCard.value > 1:
                break
            # 刻
            if firstCard == secondCard and secondCard == thirdCard:
                tripletsList.append([firstCard, secondCard, thirdCard])
            # 顺
            if thirdCard.value - secondCard.value == 1 and secondCard.value - firstCard.value == 1:
                tripletsList.append([firstCard, secondCard, thirdCard])
    
    if len(tripletsList) == 0 and len(couples) == 0:
        return False
    checkRight = False
    for triplet in tripletsList:
        copyLast = lastCards.copy()
        for item in triplet:
            copyLast.remove(item)
        if checkLastRight(copyLast, hasTrump):
            checkRight = True
            break
    if len(couples) > 0:
        copyLast = lastCards.copy()
        for item in couples[0]:
            copyLast.remove(item)
        if checkLastRight(copyLast, True):
            checkRight = True
    return checkRight

#九宝灯莲
testCase1 = [Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 2), Mahjong(1, 3),Mahjong(1, 4),Mahjong(1, 5),Mahjong(1, 6),Mahjong(1, 7),Mahjong(1, 8),Mahjong(1, 9),Mahjong(1, 9),Mahjong(1, 9)]
#听一张
testCase2 = [Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 3),Mahjong(1, 4),Mahjong(1, 5),Mahjong(1, 6),Mahjong(1, 7),Mahjong(1, 8),Mahjong(1, 9),Mahjong(1, 9),Mahjong(1, 9)]
#听对
testCase3 = [Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 2), Mahjong(1, 2),Mahjong(1, 2),Mahjong(1, 3),Mahjong(1, 3),Mahjong(1, 3),Mahjong(1, 4),Mahjong(1, 4),Mahjong(1, 4),Mahjong(2, 2)]
#两面听
testCase4 = [Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 1), Mahjong(1, 2), Mahjong(1, 2),Mahjong(1, 2),Mahjong(1, 3),Mahjong(1, 3),Mahjong(1, 3),Mahjong(1, 6),Mahjong(1, 7),Mahjong(2, 2),Mahjong(2, 2)]
print(listenToCard(testCase1, False))
print(listenToCard(testCase2, False))
print(listenToCard(testCase3, False))
print(listenToCard(testCase4, False))
