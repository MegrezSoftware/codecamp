package com.tangtang.practice5

import java.util.*

fun main() {
    val root =
            Node(0,
                    Node(1,
                            Node(3, null, null), Node(4, null, null)),
                    Node(2,
                            Node(5, null, null), Node(6, null, null)))

//    recursePreorder(root)// 3 1 4 0 5 2 6
//    loopPreorder(root)// 3 1 4 0 5 2 6

//    loopBfs(root)// 0 1 2 3 4 5 6
    recurseBfs(root)// 0 1 2 3 4 5 6
}

data class Node(
        val value: Int,
        val left: Node?,
        val right: Node?,
) {
    // loopPreorder
    var isAccess = false
}


fun recursePreorder(root: Node?) {
    if (root == null) return

    recursePreorder(root.left)
    println(root.value)
    recursePreorder(root.right)
}

fun loopPreorder(root: Node?) {
    if (root == null) return

    val stack = Stack<Node>()
    stack.push(root)
    while (stack.isNotEmpty()) {
        val peek = stack.peek()
        if (peek.left?.isAccess == false) {
            stack.push(peek.left)
        } else {
            println(peek.value)
            peek.isAccess = true
            stack.pop().right?.let {
                stack.push(it)
            }
        }
    }

}

// better?
fun recurseBfs(root: Node?) {
    if (root == null) return

    fun innerRecurse(queue: Queue<Node>) {
        if (queue.isEmpty()) return

        queue.poll().run {
            println(value)
            left?.let {
                queue.offer(it)
            }
            right?.let {
                queue.offer(it)
            }
        }
        return innerRecurse(queue)
    }

    innerRecurse(LinkedList<Node>().apply { offer(root) })
}

fun loopBfs(root: Node?) {
    if (root == null) return

    val queue = LinkedList<Node>()
    queue.offer(root)
    while (queue.isNotEmpty()) {
        queue.poll().run {
            println(value)
            left?.let {
                queue.offer(it)
            }
            right?.let {
                queue.offer(it)
            }
        }
    }
}