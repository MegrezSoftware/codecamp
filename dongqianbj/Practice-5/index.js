class Node {

  constructor (value, left, right) {

    this.value = value

    this.left = left

    this.right = right
  }
}

/**
 * 前序遍历递归
 */
function preorderRecursion (root) {

  const res = []

  function recursion (node) {

    if (!node) return

    res.push(node.value)

    recursion(node.left)

    recursion(node.right)
  }

  recursion(root)

  return res
}

/**
 * 前序遍历递归
 */
function preorderIterate (root) {

  if (!root) return []

  const stack = [root]

  const res = []

  while (stack.length) {

    const cur = stack.pop()

    res.push(cur.value)

    cur.right && stack.push(cur.right)

    cur.left && stack.push(cur.left)
  }

  return res
}

/**
 * 广度优先遍历递归
 */
function bfsRecursion (root) {

  const res = []

  function recursion (node, layer) {

    if (!node) return null

    res[layer] = res[layer] || []

    res[layer].push(node.value)

    recursion(node.left, layer + 1)

    recursion(node.right, layer + 1)
  }

  recursion(root, 0)

  return res
}

/**
 * 广度优先遍历迭代
 */
function bfsIterate (root) {

  if (!root) return []

  const res = []

  const temp = [root]

  let layer = 0

  while (temp.length) {

    res.push([])

    for (let i = 0; i < temp.length; i++) {

      const node = temp.shift()

      res[layer].push(node.value)

      node.left && temp.push(node.left)

      node.right && temp.push(node.right)
    }

    layer++
  }

  return res
}

const root = new Node(
  12,
  new Node(
    8,
    new Node(
      3,
      new Node(
        9,
        null,
        null
      ),
      new Node(
        11,
        new Node(
          5,
          new Node(
            8,
            new Node(
              2,
              null,
              null
            ),
            null
          ),
          new Node(
            19,
            new Node(
              17,
              null,
              null
            ),
            null
          )
        ),
        null
      )
    ),
    new Node(
      10,
      new Node(
        3,
        new Node(
          5,
          null,
          null
        ),
        null
      )
    )
  ),
  new Node(
    14,
    new Node(
      11,
      null,
      new Node(
        9,
        null,
        null
      )
    ),
    null
  )
)
