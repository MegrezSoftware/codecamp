fun main(args: Array<String>) {
    val solution = Solution1()
    TestCase("223344-234-2234,--25").test(solution)
    TestCase("1112345678999--,123456789--").test(solution)
    TestCase("23456-22233-789,147--").test(solution)
    TestCase("123456-23444-55,-14-5").test(solution)
    TestCase("1112378999-123-,1469--").test(solution)
    TestCase("2344445688999--,1478--").test(solution)
}

class Solution1 : ISolution {
    override fun execute(existCard: ExistCard): DependentCard {
        return existCard.findSolution()
    }
}

