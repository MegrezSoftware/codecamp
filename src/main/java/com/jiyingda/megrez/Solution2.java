/**
 * @(#)Solution2.java, 7月 18, 2022.
 * <p>
 * Copyright 2022 yuanfudao.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jiyingda.megrez;

import java.util.ArrayList;
import java.util.List;

/**
 * 练习第二期
 * 在保留万筒条的麻将牌的基础上，定义八张特殊牌春夏秋冬梅兰竹菊，这八张牌在本次练习中充当万能牌 现在有手牌13张，判断是否听牌以及听的牌是什么
 * 牌有3种花色*9个数字=27种，每种牌有4张，以及春夏秋冬梅兰竹菊八张花牌 术语： 对：2张同样的牌 刻：3张同样的牌 顺：同花色三张连续数字的牌
 * 万能牌：在你需要的时候可以充当任意牌使用
 * 胡牌：14张牌可以按照1对 + 4组（刻或顺）组成 听牌：13张，差1张就可以胡牌
 *
 * @author jiyingdabj
 */
public class Solution2 {

    /** 01-09代表花色一的牌; 11-19代表花色二的牌; 21-29代表花色三的牌; 0代表万能牌 */
    static int[] allCards = new int[]{1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,21,22,23,24,25,26,27,28,29};

    public static void main(String[] args) {
        int[] cards = new int[]{3,3,5,5,5,6,6,6,12,13,14,15,0};
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
            // pai[0] 保存万能牌的数量
            if (pai[0] == 0) {
                return c3 == 0 && c2 == 0;
            }
            int a = pai[0];
            a -= c3 * 3;
            a -= c2 * 2;
            return a == 0;
        }
        if (pai[0] < 0 || pai[i] < 0 || c3 < 0 || c2 < 0) {
            return false;
        }
        // pai[i] == 1 必然要连顺
        if (pai[i] == 1) {
            return check1(pai, i, c3, c2, len);
        }
        // pai[i] == 2 可以选择对 也可以选择2条顺
        if (pai[i] == 2) {
            return check2(pai, i, c3, c2, len);
        }
        // pai[i] >= 3 也有2种情况 1,2,3,3,3,3,4,5这种情况会在前面判断pai[i] == 1的时候处理掉了
        return check3(pai, i, c3, c2, len);
    }

    public static boolean check1(int[] pai, int i, int c3, int c2, int len) {
        if (i + 2 < len && pai[i + 1] > 0 && pai[i + 2] > 0) {
            // 能连成顺则 i + 1 继续判断
            pai[i]--;
            pai[i + 1]--;
            pai[i + 2]--;
            boolean f1 = dfs(pai, i + 1, c3 - 1, c2);
            // 所有进行尝试的地方都应该进行回溯
            pai[i]++;
            pai[i + 1]++;
            pai[i + 2]++;
            if (f1) {
                return true;
            }
        }
        // 后面的牌不够连顺 1：当前的牌为一张单排 考虑用万能牌凑对或者连三
        if ((i + 1 >= len) || (i + 1 == len && pai[i + 1] < 1) || (i + 2 < len && pai[i + 1] < 1 && pai[i + 2] < 1)) {
            // 凑「刻」
            pai[0] -= 2;
            pai[i] -= 1;
            boolean f1 = dfs(pai, i + 1, c3 - 1, c2);
            pai[0] += 2;
            pai[i] += 1;
            if (f1) {
                return true;
            }
        } else if ((i + 2 < len && pai[i + 2] < 1) || (i + 1 == len && pai[i + 1] > 0)) {
            // 后面第二张牌不够连顺
            pai[0] -= 1;
            pai[i] -= 1;
            pai[i + 2] -= 1;
            boolean f1 = dfs(pai, i + 1, c3 - 1, c2);
            pai[0] += 1;
            pai[i] += 1;
            pai[i + 2] += 1;
            if (f1) {
                return true;
            }
        } else if (i + 2 < len && pai[i + 2] > 0 && pai[i + 1] < 1) {
            // 后面第一张牌不够连顺
            pai[0] -= 1;
            pai[i] -= 1;
            pai[i + 1] -= 1;
            boolean f1 = dfs(pai, i + 1, c3 - 1, c2);
            pai[0] += 1;
            pai[i] += 1;
            pai[i + 1] += 1;
            if (f1) {
                return true;
            }
        }
        // 再考虑凑对
        pai[0] -= 1;
        pai[i] -= 1;
        boolean f1 = dfs(pai, i + 1, c3, c2 - 1);
        pai[0] += 1;
        pai[i] += 1;
        return f1;
    }

    public static boolean check2(int[] pai, int i, int c3, int c2, int len) {
        // 先选择对，如果不能胡的话，则进行回溯
        pai[i] = pai[i] - 2;
        boolean f = dfs(pai, i + 1, c3, c2 - 1);
        // 回溯
        pai[i] = pai[i] + 2;
        if (f) {
            return true;
        }
        // 选择2条顺 这边也可以先选择一条顺 不过会再进入到 pai[i] = 1 的情况 实质是一样的
        if (i + 2 < len && pai[i + 1] >= 2 && pai[i + 2] >= 2) {
            pai[i] = pai[i] - 2;
            pai[i + 1] = pai[i + 1] - 2;
            pai[i + 2] = pai[i + 2] - 2;
            boolean f2 = dfs(pai, i + 1, c3 - 2, c2);
            pai[i] = pai[i] + 2;
            pai[i + 1] = pai[i + 1] + 2;
            pai[i + 2] = pai[i + 2] + 2;
            return f2;
        }

        /**
         * 后面的牌不够2个顺 尝试用万能牌
         * 考虑牌型 112233
         *  11 02 33 > 011 002 033
         *  11 00 33 = 011 033
         *  11 00 03 < 011 033
         * 我们只需选用万能牌用的少的组合 因此我们只需考虑连顺后面的牌缺一张的情况 其他情况通过凑「刻」即可
         */
        if (i + 2 < len && pai[i + 1] == 1 && pai[i + 2] > 1) {
            pai[0] -= 1;
            pai[i] -= 2;
            pai[i + 1] -= 1;
            pai[i + 2] -= 2;
            boolean f2 = dfs(pai, i + 1, c3 - 2, c2);
            pai[0] += 1;
            pai[i] += 2;
            pai[i + 1] += 1;
            pai[i + 2] += 2;
            if (f2) {
                return true;
            }
        } else if (i + 2 < len && pai[i + 1] > 1 && pai[i + 2] == 1) {
            pai[0] -= 1;
            pai[i] -= 2;
            pai[i + 1] -= 2;
            pai[i + 2] -= 1;
            boolean f2 = dfs(pai, i + 1, c3 - 2, c2);
            pai[0] += 1;
            pai[i] += 2;
            pai[i + 1] += 2;
            pai[i + 2] += 1;
            if (f2) {
                return true;
            }
        }
        // 尝试凑「刻」
        pai[0] -= 1;
        pai[i] -= 2;
        boolean f2 = dfs(pai, i + 1, c3 - 1, c2);
        pai[i] += 2;
        pai[0] += 1;
        return f2;
    }

    public static boolean check3(int[] pai, int i, int c3, int c2, int len) {
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
