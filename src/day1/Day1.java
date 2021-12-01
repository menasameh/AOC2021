package day1;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day1 {
    public void smallSol() {
        List<Integer> list = getInput();

        int numberOfIncreases = 0;

        // First item doesn't count, so we are starting from index 1
        for(int i=1;i<list.size();i++) {
            if(list.get(i) > list.get(i-1)) {
                numberOfIncreases++;
            }
        }

        System.out.println(numberOfIncreases);
    }
    
    List<Integer> getInput() {
        String input = FilesUtil.getContentOf("src/day1/input");
        return Arrays.stream(input.split("\n")).map(Integer::valueOf).toList();
    }
}
