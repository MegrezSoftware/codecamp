/**
 * @(#)MahjongMain.java, 7月 29, 2022.
 * <p>
 * Copyright 2022 .com. All rights reserved.
 * .COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jiyingda.megrez;

import java.util.Arrays;
import java.util.List;

/**
 * @author jiyingdabj
 */
public class MahjongMain {

    public static void main(String[] args) {
        List<Mahjong> mahjongs = Mahjong.toMjs(Arrays.asList(3,3,4,4,4,5,5,5,6,12,13,13,13));
        Mahjong.print(mahjongs);
        List<Mahjong> lessonCards = getAllListenCards(mahjongs);
        List<Mahjong> lessonCards2 = getAllListenCardsWithHua(mahjongs);
        Mahjong.print(lessonCards);
        Mahjong.print(lessonCards2);
    }

    /**
     * 听牌的判断
     */
    public static List<Mahjong> getAllListenCards(List<Mahjong> mahjongs) {
        int[] mjs = Mahjong.toCodes(mahjongs);
        List<Integer> lessonCards = Solution1.check13(mjs);
        return Mahjong.toMjs(lessonCards);
    }

    /**
     * 带花的听牌判断
     * 功能包含 #getAllListenCards
     */
    public static List<Mahjong> getAllListenCardsWithHua(List<Mahjong> mahjongs) {
        int[] mjs = Mahjong.toCodes(mahjongs);
        List<Integer> lessonCards = Solution2.check13(mjs);
        return Mahjong.toMjs(lessonCards);
    }


}
