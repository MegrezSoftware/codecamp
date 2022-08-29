package com.tangtang.practice5

import java.util.*

fun main() {
    val root =
            Node(0,
                    Node(1,
                            Node(3, null, null), Node(4, null, null)),
                    Node(2,
                            Node(5, null, null), Node(6, null, null)))

//    recursePreorder(root)// 0 1 3 4 2 5 6
//    loopPreorder(root)// 0 1 3 4 2 5 6

//    recurseInorder(root)// 3 1 4 0 5 2 6
//    loopInorder(root)// 3 1 4 0 5 2 6

//    recursePostorder(root)// 3 4 1 5 6 2 0
    loopPostorder(root)// 3 4 1 5 6 2 0

    loopBT(root)// 0 1 2 3 4 5 6
    recurseBT(root)// 0 1 2 3 4 5 6
}

data class Node(
        val value: Int,
        val left: Node?,
        val right: Node?,
) {
    // loopPreorder loopPostorder
    var hasAccess = false
}

fun recursePreorder(root: Node?) {
    if (root == null) return

    println(root.value)
    recursePreorder(root.left)
    recursePreorder(root.right)
}

fun loopPreorder(root: Node?) {
    if (root == null) return

    val stack = Stack<Node>()
    stack.push(root)
    while (stack.isNotEmpty()) {
        stack.pop().run {
            print(value)
            right?.let {
                stack.push(it)
            }
            left?.let {
                stack.push(it)
            }
        }
    }
}

fun recurseInorder(root: Node?) {
    if (root == null) return

    recurseInorder(root.left)
    println(root.value)
    recurseInorder(root.right)
}

fun loopInorder(root: Node?) {
    if (root == null) return

    val stack = Stack<Node>()
    stack.push(root)
    while (stack.isNotEmpty()) {
        val peek = stack.peek()
        if (peek.left?.hasAccess == false) {
            stack.push(peek.left)
        } else {
            println(peek.value)
            peek.hasAccess = true
            stack.pop().right?.let {
                stack.push(it)
            }
        }
    }
}

fun recursePostorder(root: Node?) {
    if (root == null) return

    recursePostorder(root.left)
    recursePostorder(root.right)
    println(root.value)
}

fun loopPostorder(root: Node?) {
    if (root == null) return

    val stack = Stack<Node>()
    stack.push(root)
    while (stack.isNotEmpty()) {
        val peek = stack.peek()
        when {
            peek.left?.hasAccess == false -> stack.push(peek.left)
            peek.right?.hasAccess == false -> stack.push(peek.right)
            else -> {
                println(peek.value)
                peek.hasAccess = true
                stack.pop()
            }
        }
    }
}

// todo better?
fun recurseBT(root: Node?) {
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

fun loopBT(root: Node?) {
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