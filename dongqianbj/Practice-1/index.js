const testCase = require('./cases.js')

/**
 * model: XYZ * m + PPP * n + QQ
 */
const DEBUG = false

function findQ (arr) {
  const map = {}

  const Q = []

  for (let i = 0; i < arr.length; i++) {
    const value = arr[i]
    map[value] = map[value] ? map[value] + 1 : 1
  }

  Object.keys(map).forEach(value => {
    if (map[value] > 1) {
      Q.push(value)
    }
  })

  return Q
}

function removeQQ (Q, arr) {
  const res = []

  Q.forEach(q => {
    const copy = arr.slice()

    const removedQQ = copy.join(',')
      .replace(q, '')
      .replace(q, '')
      .split(',')
      .filter(item => item)

    res.push(removedQQ)
  })

  return res
}

function findP (arr) {
  const map = {}

  const P = []

  for (let i = 0; i < arr.length; i++) {
    const value = arr[i]

    map[value] = map[value] ? map[value] + 1 : 1
  }

  Object.keys(map).forEach(value => {
    if (map[value] > 2) {
      P.push(value)
    }
  })

  return P
}

function removePPP (P, arr) {
  const res = []

  if (P.length) {
    P.forEach(p => {
      const copy = arr.slice()

      const removedPPP = copy.join(',')
        .replace(p, '')
        .replace(p, '')
        .replace(p, '')
        .split(',')
        .filter(item => item)

      res.push(removedPPP)
    })
  } else {
    res.push(arr)
  }

  return res
}

function removeXYZ (arr, idx) {
  if (arr.length < 3) {
    return arr
  } else {
    const copy = arr.slice()

    const X = copy[idx]

    const Y = String(parseInt(X) + 1)

    const Z = String(parseInt(X) + 2)

    if (copy.includes(Y) && copy.includes(Z)) {
      const removedXYZ = copy.join(',')
        .replace(X, '')
        .replace(Y, '')
        .replace(Z, '')
        .split(',')
        .filter(item => item)

      return removeXYZ(removedXYZ, 0)
    } else {
      return removeXYZ(copy.splice(0, 1), idx + 1)
    }
  }
}

function main (arr13, another) {
  const arr14 = [...arr13, another].sort((x, y) => x - y)

  const illegal = arr14.filter(item => item === another).length > 4

  if (illegal) return

  const Q = findQ(arr14)

  DEBUG && console.log('[debug]', 'Q', Q)

  const removedQQ = removeQQ(Q, arr14)

  DEBUG && console.log('[debug]', 'removedQQ', removedQQ)

  let removedPPP = []

  removedQQ.forEach(item => {
    const P = findP(item)

    DEBUG && console.log('[debug]', 'P', P)

    removedPPP = [...removedPPP, ...removePPP(P, item)]
  })

  DEBUG && console.log('[debug]', 'removedPPP', removedPPP)

  const success = []

  removedPPP.forEach(item => {
    const removedXYZ = removeXYZ(item, 0)

    DEBUG && console.log('[debug]', 'removedXYZ', removedXYZ)

    if (removedXYZ.length === 0) {
      success.push(another)
    }
  })

  success.length && console.log('[info]', 'success', success)
}

const all = [
  1, 2, 3, 4, 5, 6, 7, 8, 9,
  11, 12, 13, 14, 15, 16, 17, 18, 19,
  21, 22, 23, 24, 25, 26, 27, 28, 29
]

const index = 3

console.log('case', testCase[index])

all.forEach(item => {
  main(testCase[index], item)
})
