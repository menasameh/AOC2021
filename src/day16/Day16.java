package day16;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day16 {
    public void smallSol() {
        List<Unit> list = getInput();

        System.out.println(list.stream().map(item -> item.tag).reduce(0, Integer::sum));
    }

    public void largeSol() {
        List<Unit> list = getInput();

        System.out.println(list.get(0).getValue());
    }

    List<Unit> getInput() {
        String input = FilesUtil.getContentOf("src/day16/input");
        HashMap<Character, String> map = new HashMap<>();
        map.put('0', "0000");
        map.put('1', "0001");
        map.put('2', "0010");
        map.put('3', "0011");
        map.put('4', "0100");
        map.put('5', "0101");
        map.put('6', "0110");
        map.put('7', "0111");
        map.put('8', "1000");
        map.put('9', "1001");
        map.put('A', "1010");
        map.put('B', "1011");
        map.put('C', "1100");
        map.put('D', "1101");
        map.put('E', "1110");
        map.put('F', "1111");
        StringBuilder mod = new StringBuilder();
        for(char c : input.toCharArray()) {
            mod.append(map.get(c));
        }
        String bin = mod.toString();

        List<Unit> units = new ArrayList<>();

        int index = 0;
        while(index + 7 < bin.length()) {
            if(Integer.parseInt(bin.substring(index+3, index+6), 2) == 4) {
                Literal part = new Literal(bin.substring(index));
                units.add(part);
                index += part.lengthRead;
            } else {
                Operator op = new Operator(bin.substring(index));
                units.add(op);
                index += op.lengthRead;
            }
        }

        return units;
    }
}

class Unit {
    int tag, type, lengthRead;

    long getValue() { return 0; }
}

class Literal extends Unit {
    long value;

    Literal(String input) {
        tag = Integer.parseInt(input.substring(0, 3), 2);
        type = Integer.parseInt(input.substring(3, 6), 2);
        int index = 6;
        StringBuilder valueContainer = new StringBuilder();
        while(true){
            if(index >= input.length()) {
                break;
            }

            valueContainer.append(input, index + 1, index + 5);
            if(input.charAt(index) == '0') {
                break;
            }
            index += 5;
        }
        value = Long.parseLong(valueContainer.toString(), 2);
        lengthRead = index + 5;
    }

    @Override
    public long getValue() {
        return value;
    }
}

class Operator extends Unit {
    List<Unit> literalList;

    Operator(String input) {
        tag = Integer.parseInt(input.substring(0, 3), 2);
        type = Integer.parseInt(input.substring(3, 6), 2);
        literalList = new ArrayList<>();
        char lengthType = input.charAt(6);

        if(lengthType == '0') {
            int length = Integer.parseInt(input.substring(7, 22), 2);
            String nums = input.substring(22, 22 + length);
            int index = 0;
            while(index + 6 < nums.length()) {
                if(Integer.parseInt(nums.substring(index+3, index+6), 2) == 4) {
                    Literal part = new Literal(nums.substring(index));
                    literalList.add(part);
                    index += part.lengthRead;
                    lengthRead+= part.lengthRead;
                } else {
                    Operator op = new Operator(nums.substring(index));
                    literalList.add(op);
                    index += op.lengthRead;
                    lengthRead+= op.lengthRead;
                }
            }
            lengthRead += 22;
        } else {
            int count = Integer.parseInt(input.substring(7, 18), 2);
            String nums = input.substring(18);
            int index = 0;
            while(count-- > 0) {
                if(Integer.parseInt(nums.substring(index+3, index+6), 2) == 4) {
                    Literal part = new Literal(nums.substring(index));
                    literalList.add(part);
                    index += part.lengthRead;
                    lengthRead+= part.lengthRead;
                } else {
                    Operator op = new Operator(nums.substring(index));
                    literalList.add(op);
                    index += op.lengthRead;
                    lengthRead+= op.lengthRead;
                }
            }
            lengthRead += 18;
        }
    }

    @Override
    public long getValue() {
        if(type ==0) {
            return literalList.stream().map(Unit::getValue).reduce(0L, Long::sum);
        } else if(type == 1) {
            return literalList.stream().map(Unit::getValue).reduce(1L, (a, b) -> a * b);
        } else if(type == 2) {
            return literalList.stream().map(Unit::getValue).min(Long::compareTo).get();
        }else if(type == 3) {
            return literalList.stream().map(Unit::getValue).max(Long::compareTo).get();
        }else if(type == 5) {
            return literalList.get(0).getValue() > literalList.get(1).getValue() ? 1 : 0;
        }else if(type == 6) {
            return literalList.get(0).getValue() < literalList.get(1).getValue() ? 1 : 0;
        }else if(type == 7) {
            return literalList.get(0).getValue() == literalList.get(1).getValue() ? 1 : 0;
        }
        return 0;
    }
}

