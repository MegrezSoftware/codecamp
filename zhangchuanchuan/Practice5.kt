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
    Practice5().printNodeList(Practice5().preorderRecursionTraversal(testNode, emptyList()))
    println()

    Practice5().printNodeList(Practice5().levelTraversal(testNode))
    println()
    Practice5().printNodeList(Practice5().levelRecursionTraversal(listOf(testNode), emptyList()))
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
    fun preorderRecursionTraversal(node: Node, cacheList: List<Node>): List<Node> {
        var resultList: List<Node> = cacheList.toMutableList().apply {
            add(node)
        }
        if (node.left != null) {
            resultList = preorderRecursionTraversal(node.left, resultList)
        }
        if (node.right != null) {
            resultList = preorderRecursionTraversal(node.right, resultList)
        }
        return resultList
    }

    //广度循环
    fun levelTraversal(node: Node): List<Node> {
        val result = mutableListOf<Node>()
        val queue = mutableListOf<Node>()
        queue.add(node)
        while (queue.isNotEmpty()) {
            val lastNode = queue.removeLast()
            result.add(lastNode)
            if (lastNode.left != null) {
                queue.add(0, lastNode.left)
            }
            if (lastNode.right != null) {
                queue.add(0, lastNode.right)
            }
        }
        return result
    }

    //广度遍历递归
    fun levelRecursionTraversal(nodes: List<Node>, cacheList: List<Node>): List<Node> {
        if (nodes.isEmpty()) return cacheList
        val resultList = cacheList.toMutableList()
        resultList.addAll(nodes)
        val nextLevels = mutableListOf<Node>()
        nodes.forEach {
            it.left?.let { left->
                nextLevels.add(left)
            }
            it.right?.let { right->
                nextLevels.add(right)
            }
        }
        return levelRecursionTraversal(nextLevels, resultList)
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