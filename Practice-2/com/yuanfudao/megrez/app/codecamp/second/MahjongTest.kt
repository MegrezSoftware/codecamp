package com.yuanfudao.megrez.app.codecamp.second

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MahjongTest {
    @Test
    fun launchMultiPresetCaseTest() {
        val caseFileName =
            "/Users/eva/Mahnong/src/test/java/com/yuanfudao/megrez/app/codecamp/first/testCase"
        MahjongScanner.testCaseToAnswer(caseFileName)
    }
}