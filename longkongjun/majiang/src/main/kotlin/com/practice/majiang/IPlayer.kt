package com.practice.majiang

import com.practice.majiang.data.MaJiang

interface IPlayer {

    /**
     *发牌
     */
    fun input(majiangs:List<MaJiang>)

    /**
     * 弃牌
     */
    fun discard():MaJiang

    /**
     * 是否胡牌
     */
    fun checkHu(target:MaJiang):Boolean


    /**
     * 是否吃牌
     */
    fun checkChi(target:MaJiang):Boolean

}