package day6;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    public void smallSol() {
        List<Integer> list = getInput();

        for(int i=0;i<80;i++) {
            int initialSize = list.size();
            for(int j=0;j<initialSize;j++) {
                if(list.get(j) == 0) {
                    list.set(j, 6);
                    list.add(8);
                } else {
                    list.set(j, list.get(j) - 1);
                }
            }
        }

        System.out.println(list.size());
    }

    public void largeSol() {
        List<Integer> list = getInput();
        int daysToRun = 256;
        // instead of having an array of fish, we would have an array of days
        // fishOnDay[i] in the array would be the count of fish that has i days to produce
        long[] fishOnDay = new long[9];
        // to separate new fish from old ones, we will have the changes stored in another array
        // and once we finish calculating the production we will apply it.
        long[] fishProductionOnDay = new long[9];

        for(Integer i: list) {
            fishOnDay[i]++;
        }

        for(int i=0;i<daysToRun;i++) {
            for(int j=0;j<fishOnDay.length;j++) {
                // reducing fish daysToProduce by 1, by moving all the fish in day j to day j-1
                // taking into consideration if j-1 is below zero

                fishProductionOnDay[j] -= fishOnDay[j];
                if(j > 7) {
                    fishProductionOnDay[j-1] += fishOnDay[j];
                } else {
                    fishProductionOnDay[(j-1+7)%7] += fishOnDay[j];
                }

                // after moving fish at day zero to day 6, they produce new fish equal to their count on day 8
                if(j == 0) {
                    fishProductionOnDay[8] += fishOnDay[j];
                }
            }

            // apply production
            for(int j=0;j<fishOnDay.length;j++) {
                fishOnDay[j] += fishProductionOnDay[j];
                fishProductionOnDay[j] = 0;
            }
        }

        long count = 0;
        for (long l : fishOnDay) {
            count += l;
        }
        System.out.println(count);
    }

    List<Integer> getInput() {
        String input = FilesUtil.getContentOf("src/day6/input");
        // using new ArrayList to create modifiable List, toList produces immutable list :'(
        return new ArrayList<>(Arrays.stream(input.split(",")).map(Integer::valueOf).toList());
    }
}