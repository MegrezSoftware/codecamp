package com.practice.majiang.rule

import com.practice.majiang.IRuleStrategy
import com.practice.majiang.data.MaJiang

class ChuanRuleStrategy:IRuleStrategy {
    override fun checkHu(set: List<MaJiang>, target: MaJiang): Boolean {
        TODO("Not yet implemented")
    }

    override fun checkPeng(set: List<MaJiang>, target: MaJiang): Boolean {
        TODO("Not yet implemented")
    }

    override fun checkChi(set: List<MaJiang>, target: MaJiang): Boolean {
        return false
    }
}