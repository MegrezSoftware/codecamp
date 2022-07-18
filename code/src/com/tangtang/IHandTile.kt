package com.tangtang

import com.tangtang.practice1.Mahjong

interface IHandTile {
    fun checkReady(): Set<Mahjong>
}