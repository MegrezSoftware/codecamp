import java.lang.IllegalArgumentException

/**
 * 两数之和
 * https://leetcode.cn/problems/two-sum/description/
 */
fun main() {
    require(Solution().twoSum(intArrayOf(2, 7, 11, 15), 9).contentEquals(intArrayOf(0, 1)))
    require(Solution().twoSum(intArrayOf(3, 2, 4), 6).contentEquals(intArrayOf(1, 2)))
    require(Solution().twoSum(intArrayOf(3, 3), 6).contentEquals(intArrayOf(0, 1)))

    require(Solution().twoSumRecursion(intArrayOf(2, 7, 11, 15), 9).contentEquals(intArrayOf(0, 1)))
    require(Solution().twoSumRecursion(intArrayOf(3, 2, 4), 6).contentEquals(intArrayOf(1, 2)))
    require(Solution().twoSumRecursion(intArrayOf(3, 3), 6).contentEquals(intArrayOf(0, 1)))
}

class Solution {
    /**
     * 循环版
     */
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val map = mutableMapOf<Int, Int>()
        nums.forEachIndexed { index, n ->
            if (map[target - n] != null) {
                return intArrayOf(map[target - n]!!, index)
            } else {
                map[n] = index
            }
        }
        throw IllegalArgumentException("no answer")
    }

    /**
     * 递归版入口
     */
    fun twoSumRecursion(nums: IntArray, target: Int): IntArray {
        val map = mutableMapOf<Int, Int>()
        return innerTwoSumRecursion(nums, target, 0, map)
    }

    /**
     * 尾递归
     */
    private fun innerTwoSumRecursion(nums: IntArray, target: Int, index: Int, map: MutableMap<Int, Int>): IntArray {
        if (map[target - nums[index]] != null) {
            return intArrayOf(map[target - nums[index]]!!, index)
        }
        if (index > nums.lastIndex) {
            throw IllegalArgumentException("no answer")
        }
        map[nums[index]] = index
        return innerTwoSumRecursion(nums, target, index + 1, map)
    }
}