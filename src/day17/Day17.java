package day17;

import utils.FilesUtil;

public class Day17 {
    public void smallSol() {
        Game game = getInput();

        System.out.println(game.getMaxHeight());
    }

    public void largeSol() {
        Game game = getInput();

        System.out.println(game.countDiff());
    }

    Game getInput() {
        String input = FilesUtil.getContentOf("src/day17/input");
        String[] ranges = input.split(": ")[1].split(", ");

        Range x = new Range(ranges[0]);
        Range y = new Range(ranges[1]);

        return new Game(x, y);
    }
}

class Game {
    Range x,y;

    Game(Range x, Range y) {
        this.x = x;
        this.y = y;
    }

    int getMaxHeight() {
        int maxH = 0;

        for(int i=0;i<1000;i++) {
            for(int j=0;j<1000;j++) {
                int px = 0;
                int py = 0;
                int vx = i;
                int vy = j;
                int max =0;
                boolean reached = false;
                for(int t=0;t<1000;t++) {
                    px += vx;
                    py += vy;
                    if(x.contains(px) && y.contains(py) ) {
                        reached = true;
                    }
                    max = Math.max(max, py);
                    if(vx > 0) {
                        vx--;
                    } else if (vx < 0) {
                        vx++;
                    }
                    vy--;
                }
                if(reached) {
                    maxH = Math.max(maxH, max);
                }
            }
        }
        return maxH;
    }

    int countDiff() {
        int count = 0;

        for(int i=0;i<1000;i++) {
            for(int j=-2000;j<2000;j++) {
                int px = 0;
                int py = 0;
                int vx = i;
                int vy = j;
                boolean reached = false;
                for(int t=0;t<1000;t++) {
                    px += vx;
                    py += vy;
                    if(x.contains(px) && y.contains(py) ) {
                        reached = true;
                    }
                    if(vx > 0) {
                        vx--;
                    } else if (vx < 0) {
                        vx++;
                    }
                    vy--;
                }
                if(reached) {
                    count++;
                }
            }
        }
        return count;
    }

}

class Range {
    int min, max;

    Range(String line) {
        String[] parts = line.split("=")[1].split("\\.\\.");
        min = Integer.parseInt(parts[0]);
        max = Integer.parseInt(parts[1]);
    }

    boolean contains(int x) {
        return x >= min && x <= max;
    }
}