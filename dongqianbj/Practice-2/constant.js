/**
 * FAKE ENUM
 */

/**
 * @TIAO 条字牌
 */
const TIAO = {
  chs: '条',
  values: [1, 2, 3, 4, 5, 6, 7, 8, 9]
}

/**
 * @TONG 筒字牌
 */
const TONG = {
  chs: '筒',
  values: [11, 12, 13, 14, 15, 16, 17, 18, 19]
}

/**
 * @WAN 万字牌
 */
const WAN = {
  chs: '万',
  values: [21, 22, 23, 24, 25, 26, 27, 28, 29]
}

const FLOWER = {
  chs: ['春', '夏', '秋', '冬', '梅', '兰', '竹', '菊'],
  values: [100, 200, 300, 400, 500, 600, 700, 800]
}

Object.freeze(TIAO)

Object.freeze(TONG)

Object.freeze(WAN)

Object.freeze(FLOWER)

module.exports = {
  TIAO,
  TONG,
  WAN,
  FLOWER
}