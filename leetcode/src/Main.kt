fun main(args: Array<String>) {
}


class Solution {
    fun isMatch(s: String, p: String): Boolean {
        return regexMatch(
            targetChars = s.toCharArray().toList(),
            regexChars = p.toCharArray().toList()
        )
    }

    /**
     * 算法地址：https://leetcode.cn/problems/regular-expression-matching/
     * @param targetChars 待匹配字符串
     * @param regexChars 剩余的匹配字符
     */
    private fun regexMatch(targetChars: List<Char>, regexChars: List<Char>): Boolean {
        if (regexChars.isEmpty() && targetChars.isEmpty()) return true

        val targetChar = targetChars.firstOrNull()
        val regexChar = regexChars.firstOrNull()

        val subTargetChars = if (targetChars.isEmpty()) targetChars else targetChars.subList(1, targetChars.size)
        val subRegexChars = if (regexChars.isEmpty()) regexChars else regexChars.subList(1, regexChars.size)

        return if (regexChars.getOrNull(1) == '*') {
            if (targetChar == regexChar || (targetChar != null && regexChar == '.')) {
                regexMatch(subTargetChars, regexChars) ||
                        regexMatch(targetChars, regexChars.subList(2, regexChars.size))
            } else {
                regexMatch(targetChars, regexChars.subList(2, regexChars.size))
            }
        } else {
            if (targetChar == regexChar || (targetChar != null && regexChar == '.')) {
                regexMatch(subTargetChars, subRegexChars)
            } else {
                false
            }
        }
    }
}