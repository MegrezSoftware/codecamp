package com.yuanfudao.megrez.app.codecamp.first

import java.util.ArrayList

class TakeIndexCombination {
    fun permute(nums: List<Int>): List<List<Int>> {
        val len = nums.size
        val result = ArrayList<List<Int>>()
        if (len == 0) return result

        val temp = ArrayList<Int>()
        Logg.debugLn("CASE: $temp")
        dfs(nums, 0, temp, result)

        Logg.debugLn("Final Result: ")
        result.forEachIndexed { index, list ->
            Logg.debugLn("Answer ${index + 1}: $list")
        }
        return result
    }

    private fun dfs(
        nums: List<Int>,
        depth: Int,
        temp: MutableList<Int>,
        result: MutableList<List<Int>>,
    ) {
        val len = nums.size
        if (depth == len) {
            result.add(ArrayList(temp))
            return
        }
        val canTake = nums[depth]
        //
        if (canTake == 0) {
            temp.add(-1) // means not take anything
            dfs(nums, depth + 1, temp, result)
            temp.removeAt(temp.size - 1)
            return
        }
        for (i in 0 until canTake) {
            temp.add(i)
            Logg.debugLn("depth=$depth, i=$i, $temp")
            dfs(nums, depth + 1, temp, result)
            temp.removeAt(temp.size - 1)
        }
    }
}