
import java.io.File

//Data Define 

enum class Color(val v: Int) {
    WAN(0), BING(10), TIAO(20)
}

enum class Point(val v: Int) {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9)
}

data class MajongItem(val color:Color, val point: Point) {
    fun toInt() = color.v + point.v
    companion object {
        val allCases: List<MajongItem> =
            enumValues<Color>().flatMap { c -> 
                enumValues<Point>().map { MajongItem(c, it)}
            }
        
        fun fromInt(v: Int): MajongItem? =
            allCases.first { it.toInt() == v }
        
    }
}

//Help functions

fun toCntMap(items: List<Int>): List<Int> {
    var cntMap = MutableList(31) { 0 }
    items.forEach {
        cntMap[it]++
    }
    return cntMap.toList()
}

fun decrease(cntMap:List<Int>, pattern: List<Int>): List<Int> =
    cntMap.zip(pattern) {cnt, pat -> cnt - pat} + cntMap.drop(pattern.count())

//Logics

public fun isWin(patterns:List<MajongItem>) = isWin2(toCntMap(patterns.map(MajongItem::toInt)))

fun isWin2(cntMap:List<Int>) = if(cntMap.sum() != 14 || cntMap.count { it > 4 } >  0) false else isWin(cntMap, 4, 1)

//tPattern 预期生成triple的数量
//pPattern 预期生成pair的数量
fun isWin(cntMapPara:List<Int>, tPattern: Int, pPattern: Int): Boolean {
    var cntMap = cntMapPara.dropWhile { it == 0 }

    if(cntMap.count { it < 0 } > 0) return false
    if(tPattern == 0 && pPattern == 0) return true
    if(tPattern < 0 || pPattern < 0) return false

    return isWin(decrease(cntMap, listOf(3)), tPattern - 1, pPattern) ||
        isWin(decrease(cntMap, listOf(1,1,1)), tPattern - 1, pPattern) ||
        isWin(decrease(cntMap, listOf(2)), tPattern, pPattern - 1)
}

//Test

fun main() {
    test()
}

fun test() {
    val fileName = "TestCase-1.txt"
    File(fileName).readLines().forEach(::runCase)
}

fun runCase(s:String): Boolean {
    val parts = s.split(",")
    val input = extract(parts[0])
    val expected = extract(parts[1])
    val output = MajongItem.allCases.filter {
        isWin(input + it)
    }
    return (output == expected).also {
        println(if(it) "PASSED" else "FAILED")
        println("case:  " + input.map(MajongItem::toInt))
        println("got:   " + output.map(MajongItem::toInt))
        println("expect:" + expected.map(MajongItem::toInt))
    }
}

fun extract(s:String): List<MajongItem> =
    s.fold(Pair(listOf(), -('0'.toInt()))) { acc: Pair<List<Int>, Int>, cur -> 
        val (list, p) = acc
        if(cur == '-') Pair(list, p + 10)
        else Pair(list + (cur.toInt() + p), p)
    }.first.mapNotNull { MajongItem.fromInt(it) }

