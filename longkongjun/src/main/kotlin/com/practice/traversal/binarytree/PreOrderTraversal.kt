package com.practice.traversal.binarytree

/**
 * 先序遍历
 */
class PreOrderTraversal {

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
            val curNode = reminds.removeLast()
            result.add(curNode)
            curNode.right?.let {
                reminds.add(it)
            }
            curNode.left?.let {
                reminds.add(it)
            }
        }
        return result.map { it.value }.toIntArray()
    }

    /**
     * 递归
     */
    fun traversalInRecursion(tree: BinaryTree): IntArray {
        return recursion(tree.root).map { it.value }.toIntArray()
    }

    private fun recursion(node: TreeNode?): List<TreeNode> {
        return if (node == null) {
            emptyList()
        } else {
            listOf(node) + recursion(node.left) + recursion(node.right)
        }
    }

}