package com.practice.traversal.binarytree

data class BinaryTree(val root: TreeNode?)

data class TreeNode(
    val value: Int, val left: TreeNode? = null, val right: TreeNode? = null
)