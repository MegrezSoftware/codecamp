/**
 * @(#)MahjongMain.java, 7月 29, 2022.
 * <p>
 * Copyright 2022 yuanfudao.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
        List<Mahjong> lessonCards = getAllListenCards(mahjongs);
        Mahjong.print(lessonCards);

    }

    public static List<Mahjong> getAllListenCards(List<Mahjong> mahjongs) {
        int[] mjs = Mahjong.toCodes(mahjongs);
        List<Integer> lessonCards = Solution1.check13(mjs);
        return Mahjong.toMjs(lessonCards);
    }

    public static List<Mahjong> getAllListenCardsWithHua(List<Mahjong> mahjongs) {
        int[] mjs = Mahjong.toCodes(mahjongs);
        List<Integer> lessonCards = Solution2.check13(mjs);
        return Mahjong.toMjs(lessonCards);
    }


}
