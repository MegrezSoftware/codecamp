/**
 * @(#)Solution1.java, 7月 15, 2022.
 * <p>
 * Copyright 2022 .com. All rights reserved.
 * .COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jiyingda.megrez;

import java.util.ArrayList;
import java.util.List;

/**
 * 练习第一期
 * 只保留万筒条的麻将牌，现在有手牌13张，判断是否听牌以及听的牌是什么
 * https://github.com/MegrezSoftware/codecamp
 * 牌有3种花色*9个数字=27种，每种牌有4张
 * 术语：
 * 对：2张同样的牌
 * 刻：3张同样的牌
 * 顺：同花色三张连续数字的牌
 * 胡牌：14张牌可以按照1对 + 4组（刻或顺）组成
 * 听牌：13张，差1张就可以胡牌
 *
 * @author jiyingdabj
 */
public class Solution1 {

    /** 01-09代表花色一的牌; 11-19代表花色二的牌; 21-29代表花色三的牌 */
    static int[] allCards = new int[]{1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,21,22,23,24,25,26,27,28,29};

    public static void main(String[] args) {
        int[] cards = new int[]{3,3,4,4,4,5,5,5,6,12,13,13,13};
        List<Integer> tp = check13(cards);
        for (int b : tp) {
            System.out.print(b + "\t");
        }
    }

    /**
     * 判断是否听牌
     */
    public static List<Integer> check13(int[] cards) {
        List<Integer> tinPais = new ArrayList<>();
        int[] pai = new int[30];
        for (int c : cards) {
            pai[c]++;
        }
        for (int other : allCards) {
            if (pai[other] >= 0 && pai[other] < 4) {
                // 这边尝试完回溯就行了，不用copyarray
                pai[other]++;
                boolean f = check14(pai);
                pai[other]--;
                if (f) {
                    // 保存所有可能听的牌
                    tinPais.add(other);
                }
            }
        }
        return tinPais;
    }

    /**
     * 判断是否胡牌
     */
    public static boolean check14(int[] pai) {
        // 利用回溯来处理多种情况的判断
        return dfs(pai, 1, 4, 1);
    }

    /**
     * 递归
     * c3 刻/顺的个数
     * c2 对的数量
     */
    public static boolean dfs(int[] pai, int i, int c3, int c2) {
        int len = pai.length;
        while (i < len && pai[i] == 0) {
            i++;
        }
        if (i >= len) {
            return c3 == 0 && c2 == 0;
        }
        if (pai[i] < 0 || c3 < 0 || c2 < 0) {
            return false;
        }
        // pai[i] == 1 必然要连顺
        if (pai[i] == 1) {
            // 后面的牌不够连顺
            if (i + 2 >= len || pai[i + 1] < 1 || pai[i + 2] < 1) {
                return false;
            }
            // 能连成顺则 i + 1 继续判断
            pai[i]--;
            pai[i + 1]--;
            pai[i + 2]--;
            boolean f1 = dfs(pai, i + 1, c3 - 1, c2);
            // 所有进行尝试的地方都应该进行回溯
            pai[i]++;
            pai[i + 1]++;
            pai[i + 2]++;
            return f1;
        }
        // pai[i] == 2 可以选择对 也可以选择2条顺
        if (pai[i] == 2) {
            // 先选择对，如果不能胡的话，则进行回溯
            pai[i] = pai[i] - 2;
            boolean f = dfs(pai, i + 1, c3, c2 - 1);
            // 回溯
            pai[i] = pai[i] + 2;
            if (f) {
                return true;
            }
            // 选择2条顺 这边也可以先选择一条顺 不过会再进入到 pai[i] = 1 的情况 实质是一样的
            if (i + 2 >= len || pai[i + 1] < 2 || pai[i + 2] < 2) {
                // 后面的牌不够2个顺
                return false;
            }
            pai[i] = pai[i] - 2;
            pai[i + 1] = pai[i + 1] - 2;
            pai[i + 2] = pai[i + 2] - 2;
            boolean f2 = dfs(pai, i + 1, c3 - 2, c2);
            pai[i] = pai[i] + 2;
            pai[i + 1] = pai[i + 1] + 2;
            pai[i + 2] = pai[i + 2] + 2;
            return f2;
        }
        // pai[i] >= 3 也有2种情况 1,2,3,3,3,3,4,5这种情况会在前面判断pai[i] == 1的时候处理掉了
        // 我们只需考虑 选择刻还是对
        // 这边i不加1 因为pi[i]可能还没用完 让程序接下来判断 pai[i] == 0/1/2 的情况
        pai[i] = pai[i] - 3;
        boolean f3 =  dfs(pai, i, c3 - 1, c2);
        // 进行回溯
        pai[i] = pai[i] + 3;
        if (f3) {
            return true;
        }
        // 选择对子 这边不可能再出现3条顺的情况 因为等价于3个刻
        pai[i] = pai[i] - 2;
        // i不用加1
        boolean f2 = dfs(pai, i, c3, c2 - 1);
        pai[i] = pai[i] + 2;
        return f2;
    }
}
