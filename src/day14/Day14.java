package day14;

import utils.FilesUtil;

import java.util.*;

public class Day14 {
    public void smallSol() {
        Game game = getInput();

        for(int i=0;i<40;i++) {
            game.play();
        }

        System.out.println(game.diff());
    }

    public void largeSol() {
        Game game = getInput();

        System.out.println(game.play2());
    }

    Game getInput() {
        String input = FilesUtil.getContentOf("src/day14/input");
        String[] parts = input.split("\n\n");
        String line  = parts[0];
        List<Transition> transitions = Arrays.stream(parts[1].split("\n")).map(Transition::new).toList();
        return new Game(line, transitions);
    }
}

class Game {
    String input;
    HashMap<String, String> map;

    Game(String input, List<Transition> transitions) {
        this.input = input;
        map = new HashMap<>();
        for(Transition t : transitions) {
            map.put(t.from, t.to);
        }
    }

    void play() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<input.length()-1;i++) {
            String old = input.substring(i, i+2);
            sb.append(map.get(old));
            if(i!=input.length()-2) {
                sb.deleteCharAt(sb.length()-1);
            }
        }
        input = sb.toString();
    }

    long play2() {
        HashMap<String, Long> mapPair = new HashMap<>();
        HashMap<String, Long> newMapPair = new HashMap<>();

        for(int i=0;i<input.length()-1;i++) {
            String old = input.substring(i, i+2);
            mapPair.put(old, mapPair.getOrDefault(old, 0L) + 1);
        }

        for(int i=0;i<40;i++) {
            for(Map.Entry<String, Long> e : mapPair.entrySet()) {
                String old = map.get(e.getKey());
                String ol = old.charAt(0) +""+ old.charAt(1);
                String ld = old.charAt(1) +""+ old.charAt(2);

                newMapPair.put(ol, newMapPair.getOrDefault(ol, 0L) + e.getValue());
                newMapPair.put(ld, newMapPair.getOrDefault(ld, 0L) + e.getValue());
            }
            mapPair = new HashMap<>(newMapPair);
            newMapPair = new HashMap<>();
        }

        long[] chars = new long[26];
        for(Map.Entry<String, Long> e : mapPair.entrySet()) {
            chars[e.getKey().charAt(0)-'A'] += e.getValue();
        }
        chars[input.charAt(input.length()-1)-'A']++;


        long max = Arrays.stream(chars).max().getAsLong();
        long min = Arrays.stream(chars).filter(item-> item!=0).min().getAsLong();
        return max - min;
    }

    long diff() {
        long[] chars = new long[26];
        for(char c : input.toCharArray()) {
            chars[c-'A']++;
        }
        long max = Arrays.stream(chars).max().getAsLong();
        long min = Arrays.stream(chars).filter(item-> item!=0).min().getAsLong();
        return max - min;
    }

}

class Transition {
    String from, to;

    Transition(String line) {
        String[] parts = line.split(" -> ");
        this.from = parts[0];
        this.to = from.charAt(0) + parts[1] + from.charAt(1);
    }
}