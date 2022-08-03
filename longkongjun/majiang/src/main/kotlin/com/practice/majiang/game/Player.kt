package com.practice.majiang.game

import com.practice.majiang.IPlayer
import com.practice.majiang.IRuleStrategy
import com.practice.majiang.data.MaJiang
import com.practice.majiang.rule.ChuanRuleStrategy

class Player(private val ruleStrategy: IRuleStrategy) : IPlayer {

    private val mMaJiang = mutableListOf<MaJiang>()

    override fun input(majiangs: List<MaJiang>) {
        mMaJiang.addAll(majiangs)
    }

    override fun pick(majiang: MaJiang) {
        mMaJiang.add(majiang)
    }

    override fun discard(): MaJiang {
        TODO("Not yet implemented")
    }

    override fun checkHu(target: MaJiang): Boolean {
        return ruleStrategy.checkHu(mMaJiang, target = target)
    }

    override fun checkPeng(target: MaJiang): Boolean {
        return ruleStrategy.checkPeng(mMaJiang, target = target)
    }

    override fun checkChi(target: MaJiang): Boolean {
        return ruleStrategy.checkChi(mMaJiang, target = target)
    }
}