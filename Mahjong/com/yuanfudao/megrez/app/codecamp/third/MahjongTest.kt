package com.yuanfudao.megrez.app.codecamp.third

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
            "/Users/eva/megrezProjects/codecamp/src/test/java/com/yuanfudao/megrez/app/codecamp/second/TestCase-3.txt"
        MahjongScanner.testCaseToAnswer(caseFileName)
    }
}