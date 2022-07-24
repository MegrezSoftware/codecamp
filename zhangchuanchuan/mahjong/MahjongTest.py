from typing import List
import Mahjong as mahjong

def assertInputStr(inputstr: str, mahjongs: List[mahjong.Mahjong]):
    convertMahjongs = mahjong.inputConvert(inputstr)
    assert(len(convertMahjongs) == len(mahjongs))
    print(convertMahjongs)
    print(mahjongs)
    for i in range(0, len(convertMahjongs)):
        assert(convertMahjongs[i] == mahjongs[i])

#输出转换TestCase
assert(mahjong.outputConvert([mahjong.Mahjong(1, 1), mahjong.Mahjong(1, 2), mahjong.Mahjong(1, 3), mahjong.Mahjong(2, 1), mahjong.Mahjong(2, 2),mahjong.Mahjong(2, 3)]) == "123-123")
# 输入转换TestCase
assertInputStr("1", [mahjong.Mahjong(1, 1)])
assertInputStr("123", [mahjong.Mahjong(1, 1), mahjong.Mahjong(1, 2), mahjong.Mahjong(1, 3)])
assertInputStr("123-123", [mahjong.Mahjong(1, 1), mahjong.Mahjong(1, 2), mahjong.Mahjong(1, 3), mahjong.Mahjong(2, 1), mahjong.Mahjong(2, 2),mahjong.Mahjong(2, 3)])