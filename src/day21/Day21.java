package day21;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day21 {
    public void smallSol() {
        Game game = getInput();

        while(!game.hasWin()) {
            game.run();
        }

        System.out.println(game.winScore());
    }

    long o1, o2;
    int[] dist = {3, 4, 5, 4, 5, 6, 5, 6, 7, 4, 5, 6, 5, 6, 7, 6, 7, 8, 5, 6, 7, 6, 7, 8, 7, 8, 9};
    Pair[][][][][] mem;

    Pair dp(int p1, int p2, int s1, int s2, int turn) {
        if(s1 >= 21) {
//            o1++;
//            mem[p1][p2][s1][s2][turn] = true;
            return new Pair(1, 0);
        }
        if(s2 >= 21) {
//            o2++;
//            mem[p1][p2][s1][s2][turn] = false;
            return new Pair(0, 1);
        }

        if(mem[p1][p2][s1][s2][turn] != null) {
            return mem[p1][p2][s1][s2][turn];
        }

        Pair ans = new Pair(0,0);
        if(turn == 0) {
            for(int i : dist) {
                int pos = move(p1, i);
                Pair sub = dp(pos, p2, s1+pos, s2, 1);
                ans.a += sub.a;
                ans.b += sub.b;
            }
        } else {
            for(int i : dist) {
                int pos = move(p2, i);
                Pair sub = dp(p1, pos, s1, s2+pos, 0);
                ans.a += sub.a;
                ans.b += sub.b;
            }
        }

        mem[p1][p2][s1][s2][turn] = ans;
        return ans;
    }

    int move(int pos, int steps) {
        if(pos + steps <= 10) {
            pos = pos + steps;
        } else {
            if((pos + steps) % 10 == 0) {
                pos = 10;
            } else {
                pos = (pos + steps) % 10;
            }
        }
        return pos;
    }

    public void largeSol() {
        Game game = getInput();
        mem = new Pair[11][11][35][35][2];
        Pair ans = dp(game.p1.pos, game.p2.pos, 0, 0 ,0);

        System.out.println(ans.a + " " + ans.b);
        System.out.println(Math.max(ans.a, ans.b));
    }

    Game getInput() {
        String input = FilesUtil.getContentOf("src/day21/input");
        String[] parts = input.split("\n");
        int p1 = Integer.parseInt(parts[0].split(": ")[1]);
        int p2 = Integer.parseInt(parts[1].split(": ")[1]);
        return new Game(p1, p2);
    }
}

class Game {
    Player p1, p2;
    Dice dice;
    int turn = 0;

    Game(int p1, int p2) {
        this.p1 = new Player(p1);
        this.p2 = new Player(p2);
        dice = new Dice();
    }

    void run() {
        if(turn == 0) {
            playPlayer(p1);
        } else {
            playPlayer(p2);
        }
        turn = (turn+1)%2;
    }

    void playPlayer(Player p) {
        int step1 = dice.getNext();
        int step2 = dice.getNext();
        int step3 = dice.getNext();
        int steps = step1 + step2 + step3;
        p.move(steps);
    }

    boolean hasWin() {
        return p1.score >= 1000 || p2.score >= 1000;
    }

    int winScore() {
        if(p1.score >= 1000) {
            return p2.score * dice.count;
        } else {
            return p1.score * dice.count;
        }
    }
}

class Player {
    int score;
    int pos;

    Player(int pos) {
        this.pos = pos;
        score = 0;
    }

    void move(int steps) {
        if(pos + steps <= 10) {
            pos = pos + steps;
        } else {
            if((pos + steps) % 10 == 0) {
                pos = 10;
            } else {
                pos = (pos + steps) % 10;
            }
        }
        score += pos;
    }
}

class Dice {
    int num = 1;
    int count;

    int getNext() {
        count++;
        int ret = num;
        num = num+1 == 101 ? 1 : num+1;
        return ret;
    }
}

class Pair {
    long a, b;

    Pair(long a, long b) {
        this.a = a;
        this.b = b;
    }
}