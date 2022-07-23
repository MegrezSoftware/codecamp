fun main(args: Array<String>) {
    logTimeCost {
        val solution = MajiangSolution()
        MajiangCaseReader("./TestCase-1.txt", solution).apply {
            read()
            test()
        }
        MajiangCaseReader("./TestCase-2.txt", solution).apply {
            read()
            test()
        }
        MajiangCaseReader("./TestCase-3.txt", solution).apply {
            read()
            test()
        }
    }
}


fun logTimeCost(action: () -> Unit) {
    val time = System.currentTimeMillis()
    try {
        action.invoke()
    } finally {
        println("total time cost:${System.currentTimeMillis() - time}ms")
    }
}