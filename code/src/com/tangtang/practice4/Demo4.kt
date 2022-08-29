package com.tangtang.practice4

/**
 *
整数反转
给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。

如果反转后整数超过 32 位的有符号整数的范围[Int.MIN_VALUE, Int.MAX_VALUE] ，就返回 0。

假设环境不允许存储 64 位整数（有符号或无符号）。

作者：力扣 (LeetCode)
链接：https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnx13t/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */

fun main(args: Array<String>) {
    println(reverse(-2147483412))
    println(reverse2(-2147483412, 0))
}

/**
 * 循环
 */
fun reverse(x: Int): Int {
    var sum = 0
    var i = x
    while (i != 0) {
        if (sum > Int.MAX_VALUE / 10 || sum < Int.MIN_VALUE / 10) return 0

        sum = sum * 10 + i % 10
        i /= 10
    }
    return sum
}

/**
 * 递归
 */
fun reverse2(x: Int, y: Int): Int {
    if (x == 0) return y
    if (y > Int.MAX_VALUE / 10 || y < Int.MIN_VALUE / 10) return 0

    return reverse2(x / 10, y * 10 + x % 10)
}