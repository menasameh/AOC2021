package day8;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day8 {
    public void smallSol() {
        List<Output> list = getInput();

        int count = (int) list
                .stream()
                .flatMap(output -> output.digits.stream())
                .filter(output -> output.length() == 2 || output.length() == 3 || output.length() == 4 || output.length() == 7)
                .count();

        System.out.println(count);
    }

    public void largeSol() {
        List<Output> list = getInput();

        int ret = 0;
        for(Output o : list) {
            String s1 = o.numbers.stream().filter(item -> item.length() == 2).toList().get(0);
            String s4 = o.numbers.stream().filter(item -> item.length() == 4).toList().get(0);
            String s7 = o.numbers.stream().filter(item -> item.length() == 3).toList().get(0);
            String s8 = o.numbers.stream().filter(item -> item.length() == 7).toList().get(0);

            List<String> s235 = new ArrayList<>(o.numbers.stream().filter(item -> item.length() == 5).toList());
            List<String> s069 = new ArrayList<>(o.numbers.stream().filter(item -> item.length() == 6).toList());

            String s3 = s235.stream().filter(item -> contains(item, s1)).toList().get(0);
            s235.remove(s3);

            String s6 = s069.stream().filter(item -> !contains(item, s1)).toList().get(0);
            s069.remove(s6);

            String s9 = s069.stream().filter(item -> contains(item, s4)).toList().get(0);
            s069.remove(s9);
            String s0 = s069.get(0);

            String s5 = s235.stream().filter(item -> getCommon(item, s6) == 5).toList().get(0);
            s235.remove(s5);
            String s2 = s235.get(0);


            char tr = getCommonChars(s1, s2).get(0);
            char br = getCommonChars(s1, s5).get(0);
            char t = getDiffChars(s7, s1).get(0);
            char b = getDiffChars(s9, s4).stream().filter(item -> item!=t).toList().get(0);
            char m = getDiffChars(s8, s0).get(0);
            char tl = getDiffChars(s4, s1).stream().filter(item -> item!=m).toList().get(0);
            char bl = getDiffChars(s8, s9).get(0);

            // map each character to its true value, to unify the values of numbers
            HashMap<Character, Character> map = new HashMap<>();
            map.put(t, 'a');
            map.put(tl, 'b');
            map.put(tr, 'c');
            map.put(m, 'd');
            map.put(bl, 'e');
            map.put(br, 'f');
            map.put(b, 'g');

            // map each digit to its numerical value
            HashMap<String, Integer> digitMap = new HashMap<>();
            digitMap.put("abcefg", 0);
            digitMap.put("cf", 1);
            digitMap.put("acdeg", 2);
            digitMap.put("acdfg", 3);
            digitMap.put("bcdf", 4);
            digitMap.put("abdfg", 5);
            digitMap.put("abdefg", 6);
            digitMap.put("acf", 7);
            digitMap.put("abcdefg", 8);
            digitMap.put("abcdfg", 9);

            List<Integer> nums = o.digits.stream().map(item -> {
                StringBuilder n = new StringBuilder();
                char[] chars = item.toCharArray();
                for(int i=0;i<chars.length;i++) {
                    chars[i] = map.get(chars[i]);
                }
                Arrays.sort(chars);
                for (char aChar : chars) {
                    n.append(aChar);
                }
                return n.toString();
            }).map(digitMap::get)
                    .toList();
            int num = nums.get(0)*1000 + nums.get(1)*100 + nums.get(2)*10 +nums.get(3);
            ret+= num;
        }

        System.out.println(ret);
    }

    boolean contains(String word, String segment) {
        for(char c: segment.toCharArray()) {
            if(!word.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    int getCommon(String s1, String s2) {
        return getCommonChars(s1, s2).size();
    }

    List<Character> getCommonChars(String s1, String s2) {
        int[] chars = new int[26];

        for(char c : s1.toCharArray()) {
            chars[c-'a'] += 1;
        }

        for(char c : s2.toCharArray()) {
            chars[c-'a'] += 1;
        }
        List<Character> ret = new ArrayList<>();
        for(int i=0;i<chars.length;i++) {
            if(chars[i] == 2) {
                ret.add((char) (i+'a'));
            }
        }
        return ret;
    }

    List<Character> getDiffChars(String s1, String s2) {
        int[] chars = new int[26];

        for(char c : s1.toCharArray()) {
            chars[c-'a'] += 1;
        }

        for(char c : s2.toCharArray()) {
            if(chars[c-'a'] != 0) {
                chars[c-'a'] = 0;
            }
        }
        List<Character> ret = new ArrayList<>();
        for(int i=0;i<chars.length;i++) {
            if(chars[i] == 1) {
                ret.add((char) (i+'a'));
            }
        }
        return ret;
    }

    List<Output> getInput() {
        String input = FilesUtil.getContentOf("src/day8/input");
        return Arrays.stream(input.split("\n")).map(Output::new).toList();
    }
}

class Output {
    List<String> numbers;
    List<String> digits;

    Output(String s) {
        String[] parts = s.split(" \\| ");
        numbers = Arrays.stream(parts[0].split(" ")).toList();
        digits = Arrays.stream(parts[1].split(" ")).toList();
    }
}
