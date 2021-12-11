package day10;

import utils.FilesUtil;

import java.util.*;

public class Day10 {
    public void smallSol() {
        List<String> list = getInput();

        HashMap<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        map.put('>', '<');

        HashMap<Character, Integer> error = new HashMap<>();
        error.put(')', 3);
        error.put('}', 1197);
        error.put(']', 57);
        error.put('>', 25137);

        int errors = 0;

        for(String s: list) {
            Stack<Character> validator = new Stack<>();
            System.out.println(s);
            for(char c: s.toCharArray()) {
                if(c == '(' || c == '{' ||c == '[' ||c == '<') {
                    validator.push(c);
                } else {
                    if(validator.peek().equals(map.get(c))){
                        validator.pop();
                    } else {
                        errors += error.get(c);
                        break;
                    }
                }
            }

        }


        System.out.println(errors);
    }

    public void largeSol() {
        List<String> list = getInput();

        HashMap<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('(', ')');
        map.put('}', '{');
        map.put('{', '}');
        map.put(']', '[');
        map.put('[', ']');
        map.put('>', '<');
        map.put('<', '>');

        HashMap<Character, Integer> error = new HashMap<>();
        error.put(')', 1);
        error.put('}', 3);
        error.put(']', 2);
        error.put('>', 4);

        ArrayList<Long> scores = new ArrayList<>();

        for(String s: list) {
            Stack<Character> validator = new Stack<>();
            boolean valid = true;
            for(char c: s.toCharArray()) {
                if(c == '(' || c == '{' ||c == '[' ||c == '<') {
                    validator.push(c);
                } else {
                    if(validator.peek().equals(map.get(c))){
                        validator.pop();
                    } else {
                        valid = false;
                        break;
                    }
                }
            }


            if(valid) {
                long count =0;
                List<Character> chars = validator.stream().toList();
                for(int i=chars.size()-1;i>=0;i--) {
                    count *= 5;
                    count += error.get(map.get(chars.get(i)));
                }
                scores.add(count);
            }


        }

        scores.sort(Long::compare);
        System.out.println(scores.get(scores.size()/2));
    }

    List<String> getInput() {
        String input = FilesUtil.getContentOf("src/day10/input");
        return Arrays.stream(input.split("\n")).toList();
    }
}
