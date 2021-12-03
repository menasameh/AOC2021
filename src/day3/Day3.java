package day3;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day3 {
    public void smallSol() {
        List<String> logs = getInput();

        // Array contains the ones count in each bit in the logs
        int[] onesCount = new int[logs.get(0).length()];

        for(String s: logs) {
            for(int i=0;i<s.length();i++) {
                if(s.charAt(i) == '1') {
                    onesCount[i]++;
                }
            }
        }

        int gamma = 0;
        int epsilon = 0;

        for(int i=0;i<onesCount.length;i++) {
            if (onesCount[i] > logs.size()/2) {
                gamma+= Math.pow(2, (onesCount.length-i-1));
            } else {
                epsilon+= Math.pow(2, (onesCount.length-i-1));
            }
        }

        System.out.println(epsilon*gamma);
    }

    public void largeSol() {
        List<String> logs = getInput();

        List<String> current = logs;
        List<String> filtered = new ArrayList<>();
        int oxygenRate = 0;
        int co2Rate = 0;

        for(int i=0;i<logs.get(0).length();i++) {
            int finalI = i;
            int onesCount = current.stream().map(item -> item.charAt(finalI) == '1' ? 1 : 0).reduce(0, Integer::sum);
            int zeroCount = current.size() - onesCount;
            filtered = current.stream().filter(item -> {
                        if (onesCount >= zeroCount) {
                            return item.charAt(finalI) == '1';
                        } else {
                            return item.charAt(finalI) == '0';
                        }
                    }
            ).toList();
            if(filtered.size() == 1) {
                oxygenRate = Integer.parseInt(filtered.get(0), 2);
                break;
            }
            current = filtered;
        }

        current = logs;

        for(int i=0;i<logs.get(0).length();i++) {
            int finalI = i;
            int onesCount = current.stream().map(item -> item.charAt(finalI) == '1' ? 1 : 0).reduce(0, Integer::sum);
            int zeroCount = current.size() - onesCount;
            filtered = current.stream().filter(item -> {
                        if (onesCount >= zeroCount) {
                            return item.charAt(finalI) == '0';
                        } else {
                            return item.charAt(finalI) == '1';
                        }
                    }
            ).toList();
            if(filtered.size() == 1) {
                co2Rate = Integer.parseInt(filtered.get(0), 2);
                break;
            }
            current = filtered;
        }

        System.out.println(oxygenRate*co2Rate);
    }

    List<String> getInput() {
        String input = FilesUtil.getContentOf("src/day3/input");
        return Arrays.stream(input.split("\n")).toList();
    }
}