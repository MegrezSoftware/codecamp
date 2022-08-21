fun main() {
    /**
     * testCase
     */
    val testNode = Node(
        1,
        Node(
            2,
            Node(4, null, null),
            Node(5, null, null)
        ),
        Node(
            3,
            Node(6, null, null),
            Node(7, null, null)
        )
    )

    Practice5().printNodeList(Practice5().preorderLoopTraversal(testNode))
    println()
    val resultList1 = mutableListOf<Node>()
    Practice5().preorderRecursionTraversal(testNode, resultList1)
    Practice5().printNodeList(resultList1)
    println()

    Practice5().printNodeList(Practice5().levelTraversal(testNode))
    println()
    val resultList = mutableListOf<Node>()
    Practice5().levelRecursionTraversal(listOf(testNode), resultList)
    Practice5().printNodeList(resultList)
}

class Practice5 {
    //前序循环
    fun preorderLoopTraversal(node: Node): List<Node> {
        val printNodes = mutableListOf<Node>()
        val nodes = mutableListOf(node)
        while (nodes.isNotEmpty()) {
            val currentNode = nodes.removeAt(0)
            printNodes.add(currentNode)
            currentNode.right?.let {
                nodes.add(0, it)
            }
            currentNode.left?.let {
                nodes.add(0, it)
            }
        }

        return printNodes
    }

    //前序递归
    fun preorderRecursionTraversal(node: Node, result: MutableList<Node>) {
        result.add(node)
        if (node.left != null) {
            preorderRecursionTraversal(node.left, result)
        }
        if (node.right != null) {
            preorderRecursionTraversal(node.right, result)
        }
    }

    //广度循环
    fun levelTraversal(node: Node): List<Node> {
        val result = mutableListOf<Node>()
        var list = mutableListOf<Node>()
        list.add(node)
        while (list.isNotEmpty()) {
            val nextLevel = mutableListOf<Node>()
            list.forEach { current ->
                result.add(current)
                current.left?.let {
                    nextLevel.add(it)
                }
                current.right?.let {
                    nextLevel.add(it)
                }
            }
            list = nextLevel
        }
        return result
    }

    //广度遍历递归
    fun levelRecursionTraversal(nodes: List<Node>, result: MutableList<Node>) {
        if (nodes.isEmpty()) return
        result.addAll(nodes)
        val nextLevels = mutableListOf<Node>()
        nodes.forEach {
            it.left?.let { left->
                nextLevels.add(left)
            }
            it.right?.let { right->
                nextLevels.add(right)
            }
        }
        levelRecursionTraversal(nextLevels, result)
    }

    fun printNodeList(nodes: List<Node>) {
        print(nodes.joinToString(", "))
    }
}

class Node(
    val value: Int,
    val left: Node?,
    val right: Node?
) {
    override fun toString(): String {
        return "$value"
    }
}