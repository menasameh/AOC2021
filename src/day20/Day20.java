package day20;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day20 {
    public void smallSol() {
        Game game = getInput();

        game.expand();
        for(int i=0;i<50;i++)
            game.runAlgo();

        System.out.println(game.countLit());
    }

    Game getInput() {
        String input = FilesUtil.getContentOf("src/day20/input");
        String[] parts = input.split("\n\n");

        return new Game(parts[0], parts[1]);
    }
}

class Game {
    String algo;
    List<String> grid;
    int num = 1000;

    Game(String algo, String grid) {
        this.algo = algo;
        this.grid = Arrays.stream(grid.split("\n")).toList();
    }

    void print() {
        for (String s : grid) {
            System.out.println(s);
        }
    }


    void expand() {
        List<String> updated = new ArrayList<>();
        for(int i=0;i<num;i++) {
            updated.add(".".repeat(2*num+grid.size()));
        }
        for(String s: grid) {
            updated.add(".".repeat(num) + s + ".".repeat(num));
        }
        for(int i=0;i<num;i++) {
            updated.add(".".repeat(2*num+grid.size()));
        }
        grid = updated;
    }

    void runAlgo() {
        List<String> updated = new ArrayList<>();

        char filling = calcItem(1, 1);
        updated.add((filling+"").repeat(grid.size()));
        for(int i=1;i<grid.size()-1;i++) {
            StringBuilder line = new StringBuilder(filling + "");
            for(int j=1;j<grid.get(0).length()-1;j++) {
                line.append(calcItem(i, j));
            }
            line.append(filling);
            updated.add(line.toString());
        }
        updated.add((filling+"").repeat(grid.size()));

        grid = updated;
    }

    char calcItem(int i, int j) {
        String item =   grid.get(i-1).substring(j-1, j+2) +
                        grid.get(i).substring(j-1, j+2) +
                        grid.get(i+1).substring(j-1, j+2);
        item = item.replace('.', '0');
        item = item.replace('#', '1');

        int index = Integer.parseInt(item, 2);

        return algo.charAt(index);
    }

    int countLit() {
        int count = 0;
        for (String s : grid) {
            for (int j = 0; j < grid.get(0).length(); j++) {
                if (s.charAt(j) == '#') {
                    count++;
                }
            }
        }
        return count;
    }


}