package com.codecamp.practice5

import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by lei.jialin on 2022/8/26
 */
class BinaryTree {
    class TreeNode<T>(val left: TreeNode<T>?, val right: TreeNode<T>?, val data: T)

    /*
    * 前序递归
    * */
    fun <T> preOrderRecursion(root: TreeNode<T>?, onTraversal: (TreeNode<T>) -> Unit) {
        if (root == null) return
        onTraversal.invoke(root)
        preOrderRecursion(root.left, onTraversal)
        preOrderRecursion(root.right, onTraversal)
    }

    /*
    * 前序非递归
    * */
    fun <T> preOrderIteration(root: TreeNode<T>?, onTraversal: (TreeNode<T>) -> Unit) {
        root ?: return
        val stack = LinkedList<TreeNode<T>>()
        stack.push(root)
        while (stack.isNotEmpty()) {
            val cur = stack.pop()
            onTraversal.invoke(cur)
            cur.right?.apply { stack.push(this) }
            cur.left.apply { stack.push(this) }
        }
    }

    /*
    * 层次递归
    * */
    fun <T> sequenceRecursion(
        root: TreeNode<T>?,
        onTraversalDepth: (depth: Int, level: List<TreeNode<T>>) -> Unit
    ) {
        root ?: return
        val levels = ArrayList<LinkedList<TreeNode<T>>>()
        sequenceRecQueue(treeNode = root, depth = 0, levelStore = levels)
        levels.forEachIndexed { index, linkedList ->
            onTraversalDepth.invoke(index, linkedList)
        }
    }

    private fun <T> sequenceRecQueue(
        treeNode: TreeNode<T>?,
        depth: Int,
        levelStore: ArrayList<LinkedList<TreeNode<T>>>,
    ) {
        if (treeNode == null) return
        if(depth == levelStore.size)  {
            levelStore.add(LinkedList<TreeNode<T>>())
        }
        levelStore[depth].offer(treeNode)
        sequenceRecQueue(treeNode.left, depth = depth + 1, levelStore)
        sequenceRecQueue(treeNode.right, depth = depth + 1, levelStore)
    }


    /*
    * 层次非递归
    * */
    fun <T> sequenceIteration(
        root: TreeNode<T>?,
        onTraversalDepth: (depth: Int, level: List<TreeNode<T>>) -> Unit
    ) {
        root ?: return
        val queue = LinkedList<TreeNode<T>>()
        queue.offer(root)
        var depth = 1
        while (queue.isNotEmpty()) {
            val childCount = queue.size
            for (i in 0 until childCount) {
                val cur = queue.poll() ?: break
                cur.right?.apply { queue.offer(this) }
                cur.left.apply { queue.offer(this) }
            }
            onTraversalDepth.invoke(depth, queue)
            depth++
        }
    }
}