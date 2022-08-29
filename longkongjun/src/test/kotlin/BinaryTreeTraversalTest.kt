import com.practice.traversal.binarytree.BinaryTree
import com.practice.traversal.binarytree.BreadthTraversal
import com.practice.traversal.binarytree.PreOrderTraversal
import com.practice.traversal.binarytree.TreeNode
import org.junit.Assert
import org.junit.Test

class BinaryTreeTraversalTest {

    private val tree: BinaryTree

    init {
        val node8 = TreeNode(value = 8)
        val node7 = TreeNode(value = 7)
        val node6 = TreeNode(value = 6, left = node8)
        val node5 = TreeNode(value = 5)
        val node4 = TreeNode(value = 4, left = node7)
        val node3 = TreeNode(value = 3, left = node5, right = node6)
        val node2 = TreeNode(value = 2, left = node4)
        val node1 = TreeNode(value = 1, left = node2, right = node3)

        tree = BinaryTree(root = node1)
    }

    @Test
    fun testPreOrderTraversal() {
        val traversal = PreOrderTraversal()
        val want = intArrayOf(1, 2, 4, 7, 3, 5, 6, 8)
        Assert.assertArrayEquals(traversal.traversalInLoop(tree), want)
        Assert.assertArrayEquals(traversal.traversalInRecursion(tree), want)
    }

    @Test
    fun testBreadthTraversal() {
        val want = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        val traversal = BreadthTraversal()
        Assert.assertArrayEquals(traversal.traversalInLoop(tree), want)
        Assert.assertArrayEquals(traversal.traversalInRecursion(tree), want)
    }

}