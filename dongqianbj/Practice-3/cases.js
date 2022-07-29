/**
 * queue: 万，筒，条，字，花
 */

const { TIAO, TONG, WAN, ZI, FLOWER } = require('./constant.js')

function wrapper(raw) {

    const [hand, listen] = raw.split(',')

    const [
        wanHand = '',
        tongHand = '',
        tiaoHand = '',
        ziHand = '',
        flowerHand = ''
    ] = hand.split('-')

    const quz = [
        ...transform(wanHand, WAN),
        ...transform(tongHand, TONG),
        ...transform(tiaoHand, TIAO),
        ...transform(ziHand, ZI),
        ...transform(flowerHand, FLOWER)
    ]

    let ans = []

    if ('null' === listen) {

        ans = []

    } else if ('all' === listen) {

        ans = [
            ...WAN.values,
            ...TONG.values,
            ...TIAO.values,
            ...ZI.values
        ]

    } else {

        const [
            wanListen = '',
            tongListen = '',
            tiaoListen = '',
            ziListen = ''
        ] = listen.split('-')

        ans = [
            ...transform(wanListen, WAN),
            ...transform(tongListen, TONG),
            ...transform(tiaoListen, TIAO),
            ...transform(ziListen, ZI)
        ]
    }

    return {
        quz,
        ans,
    }
}

function transform(raw, MAP) {

    const arr = raw.split('').map(c => parseInt(c))

    return arr.map(num => MAP.values[num - 1])
}

const raws = [
    '223344-234-2234,--25',
    '1112345678999,123456789',
    '23456-22233-789,147',
    '123456-23444-55,-14-5',
    '1112378999-123--,1469',
    '2344445688999,178',
    '223344-334--222-1,-23456',
    '1234489-147--1234,null',
    '-123456789---1234,all'
]

const cases = raws.map((raw, idx) => {

    const {quz, ans} = wrapper(raw)

    return {
        id: idx,
        data: quz,
        expect: ans
    }
})

module.exports = cases
