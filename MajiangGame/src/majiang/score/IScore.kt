package majiang.score

/**
 * 分数
 */
interface IScore {
    /**
     * 分数的值
     */
    fun scoreValue(): Int

    /**
     * 分数展示文案
     */
    fun displayName(): String
}