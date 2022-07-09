package com.xpu.onlineexaminationsys.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RANDOM {
    public static List<Integer> getRandomList(List<Integer> qids, int nums) {
        int length = qids.size();
        List<Integer> target = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < nums;) {
            Integer idx = random.nextInt(length);
            // 判重
            boolean flag = true;
            for(int j = 0; j < target.size(); j ++) {
                if(target.get(j) == qids.get(idx)) {
                    flag = false;
                    break;
                }
            }
            // 不重复则加入当前数字
            if(flag == true) {
                target.add(qids.get(idx));
                i ++;
            }
        }
        return target;
    }
}
