/**
 * 胡牌公式：XYZ * m + PPP * n + QQ
 * @XYZ 顺子 @X @Y @Z
 * @PPP 刻子 @P
 * @QQ 对子 @Q
 * @F 花字牌
 */

const { TONG, TIAO, WAN, FLOWER } = require('./constant.js')

const testCase = require('./cases.js')

const result = {
  _count: 0
}

const ALL_CARDS = [
  ...TONG.values,
  ...TIAO.values,
  ...WAN.values,
  ...FLOWER.values
]

const NO_FLOWER_CARDS = [
  ...TONG.values,
  ...TIAO.values,
  ...WAN.values,
]

/**
 * 去除 @n 张 @card 手牌
 * @param arr {Array} 手牌
 * @param n {Number} 去除个数
 * @param card {Number} 去除手牌
 * @return {Array}
 */
function removeNCard(arr, n, card) {

  let count = 0

  const removed = arr.map(item => {
    if (item % 100 === card && count < n) {
      count ++
      return 0
    } else {
      return item
    }
  })

  return removed.filter(item => item)
}

/**
 * 手牌计数表
 * @param arr {Array} 手牌
 * @return {Object}
 */
function getCountMap(arr) {

  const countMap = {}

  for (let i = 0; i < arr.length; i++) {

    const value = arr[i] % 100

    countMap[value] = countMap[value] ? countMap[value] + 1 : 1
  }

  return countMap
}

/**
 * 寻找 @F 花字牌
 * @param arr {Array} 手牌
 * @return {Array}
 */
function findF(arr) {
  return arr.filter(item => item % 100 === 0)
}

/**
 * 寻找 @QQ 对子
 * @param countMap {Object} 手牌计数表
 * @return {Array}
 */
function findQ(countMap) {

  const Qs = []

  Object.keys(countMap).forEach(value => {

    if (countMap[value] > 1) {

      Qs.push(parseInt(value))
    }
  })

  return Qs
}

/**
 * 移除 @QQ 对子
 * @param Qs {Array} 对子
 * @param arr {Array} 手牌
 * @return {Array} 移除 @QQ 后的手牌
 */
function removeQQ(Qs, arr) {

  const res = []

  Qs.forEach(Q => {

    const removed = removeNCard(arr, 2, Q)

    res.push(removed)
  })

  return res
}

/**
 * 寻找 @PPP 刻子
 * @param arr {Array} 手牌
 * @return {Array}
 */
function findP(countMap) {

  const Ps = []

  Object.keys(countMap).forEach(value => {

    if (countMap[value] > 2) {

      Ps.push(parseInt(value))
    }
  })

  return Ps
}

/**
 * 移除 @PPP 刻子
 * @param Ps {Array} 刻子
 * @param arr {Array} 手牌
 * @return {Array} 移除 @PPP 后的手牌
 */
function removePPP(Ps, arr) {

  const res = []

  if (Ps.length) {

    let removed = arr.slice()

    Ps.forEach(P => {

      removed = removeNCard(removed, 3, P)
    })

    res.push(removed)
  } else {

    res.push(arr)
  }

  return res
}

/**
 * 移除 @XYZ 顺子
 * @param arr {Array} 手牌
 * @param idx {Number} 起始点
 * @return {Array} 移除 @XYZ 后的手牌
 */
function removeXYZ(arr, idx) {

  if (arr.length < 3) {

    return arr

  } else {

    const X = arr[idx]

    const Y = X + 1

    const Z = X + 2

    if (arr.includes(Y) && arr.includes(Z)) {

      const removedX = removeNCard(arr, 1, X)
      const removedXY = removeNCard(removedX, 1, Y)
      const removedXYZ = removeNCard(removedXY, 1, Z)

      return removeXYZ(removedXYZ, 0)
    } else {

      return removeXYZ(arr.splice(0, 1), idx + 1)
    }
  }
}

/**
 * @param arr13 {Array} 待听手牌
 * @param others {Array} 备选上牌
 * @param flag {Number} 上牌标记
 * @return {Array}
 */
function checkWin(arr13, others, flag) {

  arr13 = arr13.map(item => parseInt(item))

  if (flag === 0) {
    others.filter(item => !(item % 100 === 0 && arr13.includes(item)))
  }

  others.forEach(card => {

    const arr14 = [...arr13, card + flag].sort((x, y) => (x % 100 - y % 100))

    const Fs = findF(arr14)

    if (Fs.length) {

      Fs.forEach(F => {

        const removedF = arr14.join(',')
          .replace(F, '')
          .split(',')
          .filter(item => item)
          .map(item => parseInt(item))

        checkWin(removedF, NO_FLOWER_CARDS, F)
      })
    } else {

      const illegal = arr14.filter(item => item % 100 === card).length > 4

      if (illegal) return

      const Qs = findQ(getCountMap(arr14))

      const removedQQ = removeQQ(Qs, arr14)

      let removedPPP = []

      removedQQ.forEach(item => {

        const Ps = findP(getCountMap(item))

        removedPPP = [...removedPPP, ...removePPP(Ps, item)]
      })

      removedPPP.forEach(item => {

        const removedXYZ = removeXYZ(item, 0)

        if (removedXYZ.length === 0) {

          result[card] = result[card] ? [...result[card], arr14] : [arr14]

          result._count++
        }
      })
    }
  })
}


function execTestCase(tests) {

  tests.forEach(test => {

    const startTime = +new Date()

    console.log('\n' + `---------- case #${test.id} ----------` + '\n')

    console.log(String(test.data) + '\n')

    checkWin(test.data, ALL_CARDS, 0)

    console.log(`${'listen'.padEnd(10, ' ')} ${'count'.padEnd(10, ' ')} ${'percent'}`)

    Object.keys(result).forEach(item => {

      const count = String(result[item].length)

      const percent = (result[item].length * 100 / result._count).toFixed(2) + '%'

      !item.startsWith('_') &&
        console.log(`${String(item).padEnd(10, ' ')} ${count.padEnd(10, ' ')} ${percent}`)
    })

    console.log('\n' + `use: ${+new Date() - startTime} ms`)
  })
}

execTestCase(testCase)
