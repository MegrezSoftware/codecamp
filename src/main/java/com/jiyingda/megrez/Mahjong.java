/**
 * @(#)Mahjong.java, 7月 29, 2022.
 * <p>
 * Copyright 2022 .com. All rights reserved.
 * .COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jiyingda.megrez;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiyingdabj
 */
public enum Mahjong {

    /**
     * 麻将的定义
     */
    NONE(-1, -1, "废牌"),
    WAN1(1, 1, "万1"),
    WAN2(2, 1, "万2"),
    WAN3(3, 1, "万3"),
    WAN4(4, 1, "万4"),
    WAN5(5, 1, "万5"),
    WAN6(6, 1, "万6"),
    WAN7(7, 1, "万7"),
    WAN8(8, 1, "万8"),
    WAN9(9, 1, "万9"),
    TIAO1(11, 2, "条1"),
    TIAO2(12, 2, "条2"),
    TIAO3(13, 2, "条3"),
    TIAO4(14, 2, "条4"),
    TIAO5(15, 2, "条5"),
    TIAO6(16, 2, "条6"),
    TIAO7(17, 2, "条7"),
    TIAO8(18, 2, "条8"),
    TIAO9(19, 2, "条9"),
    TONG1(21, 3, "筒1"),
    TONG2(22, 3, "筒2"),
    TONG3(23, 3, "筒3"),
    TONG4(24, 3, "筒4"),
    TONG5(25, 3, "筒5"),
    TONG6(26, 3, "筒6"),
    TONG7(27, 3, "筒7"),
    TONG8(28, 3, "筒8"),
    TONG9(29, 3, "筒9"),
    HUA1(0, 4, "花1"),
    HUA2(0, 4, "花2"),
    HUA3(0, 4, "花3"),
    HUA4(0, 4, "花4"),
    HUA5(0, 4, "花5"),
    HUA6(0, 4, "花6"),
    HUA7(0, 4, "花7"),
    HUA8(0, 4, "花8");


    private int code;

    private int type;

    private String name;

    Mahjong(int code, int type, String name) {
        this.code = code;
        this.type = type;
        this.name = name;
    }

    public static int[] toCodes(List<Mahjong> mahjongs) {
        if (mahjongs == null) {
            return new int[0];
        }
        int[] codes = new int[mahjongs.size()];
        int i = 0;
        for (Mahjong mj : mahjongs) {
            codes[i] = mj.code;
            i++;
        }
        return codes;
    }

    public static Map<Integer, Mahjong> map = new HashMap<>();

    static {
        for (Mahjong mj : Mahjong.values()) {
            map.put(mj.code, mj);
        }
    }

    public static List<Mahjong> toMjs(List<Integer> codes) {
        List<Mahjong> mjs = new ArrayList<>();
        if (codes == null) {
            return mjs;
        }
        for (Integer code : codes) {
            mjs.add(map.getOrDefault(code, NONE));
        }
        return mjs;
    }

    public static void print(List<Mahjong> mahjongs) {
        for (Mahjong mj : mahjongs) {
            System.out.print(mj.name + "\t");
        }
        System.out.print("\n");
    }
}
