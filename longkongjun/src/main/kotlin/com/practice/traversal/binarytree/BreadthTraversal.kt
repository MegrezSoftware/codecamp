package com.practice.traversal.binarytree

/**
 * 广度遍历
 */
class BreadthTraversal {

    /**
     * 循环
     */
    fun traversalInLoop(tree: BinaryTree): IntArray {
        val result = mutableListOf<TreeNode>()
        val reminds = mutableListOf<TreeNode>()
        tree.root?.let {
            reminds.add(it)
        }
        while (reminds.isNotEmpty()) {
            val curNode = reminds.removeFirst()
            result.add(curNode)
            curNode.left?.let {
                reminds.add(it)
            }
            curNode.right?.let {
                reminds.add(it)
            }
        }
        return result.map { it.value }.toIntArray()
    }

    /**
     * 递归
     */
    fun traversalInRecursion(tree: BinaryTree): IntArray {
        return recursion(
            nodes = tree.root?.let { listOf(it) } ?: emptyList()
        ).map { it.value }.toIntArray()
    }

    private fun recursion(nodes: List<TreeNode>): List<TreeNode> {
        return if (nodes.isEmpty()) {
            emptyList()
        } else nodes + recursion(nodes.map { listOf(it.left, it.right) }.flatten().filterNotNull())
    }

}