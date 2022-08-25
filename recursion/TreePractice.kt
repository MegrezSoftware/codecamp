package recursion

import java.util.*


/**
 * Created by muchuanxin on 2022-08-25
 */
fun main() {
    val node5 = Node(5)
    val node4 = Node(4)
    val node3 = Node(3)
    val node2 = Node(2, node4, node5)
    val node1 = Node(1, node2, node3)
    preOrderRecur(node1)
    println()
    preOrderNotRecur(node1)
    println()
    breathOrderRecur(node1)
    println()
    breathOrderNotRecur(node1)
    println()
}

data class Node(
    val value: Int,
    var leftChild: Node? = null,
    var rightChild: Node? = null,
)

/**
 * 前序递归
 */
fun preOrderRecur(root: Node?) {
    if (root != null) {
        print("${root.value}  ")
        preOrderRecur(root.leftChild)
        preOrderRecur(root.rightChild)
    }
}

/**
 * 前序非递归
 */
fun preOrderNotRecur(root: Node) {
    val stack = Stack<Node>()
    stack.push(root)
    while (!stack.isEmpty()) {
        val current = stack.pop()
        print("${current.value}  ")
        current.rightChild?.let { stack.push(it) }
        current.leftChild?.let { stack.push(it) }
    }
}

/**
 * 广度递归
 */
fun breathOrderRecur(root: Node) {

    fun innerBreathOrderRecur(nodes: List<Node>) {
        if (nodes.isEmpty()) return
        val nextLevel = mutableListOf<Node>()
        nodes.forEach {
            print("${it.value}  ")
            it.leftChild?.let { nextLevel.add(it) }
            it.rightChild?.let { nextLevel.add(it) }
        }
        innerBreathOrderRecur(nextLevel)
    }

    innerBreathOrderRecur(listOf(root))
}

/**
 * 广度非递归
 */
fun breathOrderNotRecur(root: Node) {
    val queue = LinkedList<Node>()
    queue.offer(root)
    while (!queue.isEmpty()) {
        val current = queue.poll()
        print("${current.value}  ")
        current.leftChild?.let { queue.offer(it) }
        current.rightChild?.let { queue.offer(it) }
    }
}
