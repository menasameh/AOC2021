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

    public void largeSol() {
        List<Integer> list = getInput();

        List<Integer> sumList = new ArrayList<>();

        for(int i=2;i<list.size();i++) {
            sumList.add(list.get(i-2)+list.get(i-1)+list.get(i));
        }

        int numberOfIncreases = 0;

        // First item doesn't count, so we are starting from index 1
        for(int i=1;i<sumList.size();i++) {
            if(sumList.get(i) > sumList.get(i-1)) {
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
