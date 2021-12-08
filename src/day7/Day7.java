package day7;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day7 {
    public void smallSol() {
        List<Integer> list = getInput();

        int min = list.stream().min(Integer::compareTo).get();
        int max = list.stream().max(Integer::compareTo).get();

        int minCost = Integer.MAX_VALUE;
        for(int i=min;i<max+1;i++){
            int cost = 0;
            for(Integer item: list) {
                cost += Math.abs(item-i);
            }
            minCost = Math.min(cost, minCost);
        }

        System.out.println(minCost);
    }

    public void largeSol() {
        List<Integer> list = getInput();

        int min = list.stream().min(Integer::compareTo).get();
        int max = list.stream().max(Integer::compareTo).get();

        int minCost = Integer.MAX_VALUE;
        for(int i=min;i<max+1;i++){
            int cost = 0;
            for(Integer item: list) {
                int raw = Math.abs(item-i);
                cost += (raw+1)*raw/2;
            }
            minCost = Math.min(cost, minCost);
        }

        System.out.println(minCost);
    }

    List<Integer> getInput() {
        String input = FilesUtil.getContentOf("src/day7/input");
        // using new ArrayList to create modifiable List, toList produces immutable list :'(
        return new ArrayList<>(Arrays.stream(input.split(",")).map(Integer::valueOf).toList());
    }
}
