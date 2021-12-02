package day2;

import utils.FilesUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day2 {
    public void smallSol() {
        List<Action> actions = getInput();

        int horizontal = 0;
        int vertical = 0;

        for(Action action: actions) {
            switch (action.direction) {
                case up -> vertical -= action.value;
                case down -> vertical += action.value;
                case forward -> horizontal += action.value;
            }
        }

        System.out.println(horizontal*vertical);
    }

    public void largeSol() {
        List<Action> actions = getInput();

        int horizontal = 0;
        int vertical = 0;
        int aim = 0;

        for(Action action: actions) {
            switch (action.direction) {
                case up -> aim -= action.value;
                case down -> aim += action.value;
                case forward -> {
                    horizontal += action.value;
                    vertical += aim * action.value;
                }
            }
        }

        System.out.println(horizontal*vertical);
    }

    List<Action> getInput() {
        String input = FilesUtil.getContentOf("src/day2/input");
        return Arrays.stream(input.split("\n"))
                .map(item -> {
                            String[] row = item.split(" ");
                            Direction direction = switch (row[0]) {
                                case "up" -> Direction.up;
                                case "down" -> Direction.down;
                                case "forward" -> Direction.forward;
                                default -> null;
                            };
                            if (direction != null) {
                                return new Action(direction, Integer.parseInt(row[1]));
                            }
                            return null;
                        })
                .filter(Objects::nonNull)
                .toList();
    }

    static class Action {
        Direction direction;
        int value;

        Action(Direction direction, int value) {
            this.direction = direction;
            this.value = value;
        }
    }

    enum Direction {
        up, down, forward
    }
}
