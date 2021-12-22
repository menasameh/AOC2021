package day22;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day22 {
    public void smallSol() {
        List<Instruction> list = getInput();
        // shift min/max to be able to simulate them in an array
        for(Instruction instruction : list) {
            instruction.x.min += 50;
            instruction.x.max += 50;

            instruction.y.min += 50;
            instruction.y.max += 50;

            instruction.z.min += 50;
            instruction.z.max += 50;
        }

        boolean[][][] grid = new boolean[101][101][101];

        for(Instruction instruction : list) {
            if(instruction.x.min < 0 || instruction.x.min > 100) {
                continue;
            }
            if(instruction.x.max < 0 || instruction.x.max > 100) {
                continue;
            }
            if(instruction.y.min < 0 || instruction.y.min > 100) {
                continue;
            }
            if(instruction.y.max < 0 || instruction.y.max > 100) {
                continue;
            }
            if(instruction.z.min < 0 || instruction.z.min > 100) {
                continue;
            }
            if(instruction.z.max < 0 || instruction.z.max > 100) {
                continue;
            }
            for(int i=instruction.x.min;i<instruction.x.max+1;i++) {
                for(int j=instruction.y.min;j<instruction.y.max+1;j++) {
                    for(int k=instruction.z.min;k<instruction.z.max+1;k++) {
                        grid[i][j][k] = instruction.command;
                    }
                }
            }
        }

        int count = 0;
        for(int i=0;i<101;i++) {
            for(int j=0;j<101;j++) {
                for(int k=0;k<101;k++) {
                    if(grid[i][j][k]) {
                        count++;
                    }
                }
            }
        }

        System.out.println(count);
    }

    public void largeSol() {
        List<Instruction> list = getInput();
        // to be ready for subtraction
        for(Instruction instruction : list) {
            instruction.x.max++;
            instruction.y.max++;
            instruction.z.max++;
        }

        // Apply space compression technique by storing only the min/max items of each instruction

        // Convert the 3d space into linear vectors
        List<Integer> x = new ArrayList<>(), y = new ArrayList<>(), z = new ArrayList<>();
        for(Instruction instruction : list) {
            x.add(instruction.x.min);x.add(instruction.x.max);
            y.add(instruction.y.min);y.add(instruction.y.max);
            z.add(instruction.z.min);z.add(instruction.z.max);
        }
        x.sort(Integer::compareTo);
        y.sort(Integer::compareTo);
        z.sort(Integer::compareTo);

        // create a grid for the indices in the linear vectors
        boolean[][][] grid = new boolean[x.size()][y.size()][z.size()];

        for(Instruction instruction : list) {
            int ixMin = x.indexOf(instruction.x.min);
            int ixMax = x.indexOf(instruction.x.max);
            int iyMin = y.indexOf(instruction.y.min);
            int iyMax = y.indexOf(instruction.y.max);
            int izMin = z.indexOf(instruction.z.min);
            int izMax = z.indexOf(instruction.z.max);

            for(int i=ixMin;i<ixMax;i++) {
                for(int j=iyMin;j<iyMax;j++) {
                    for(int k=izMin;k<izMax;k++) {
                        grid[i][j][k] = instruction.command;
                    }
                }
            }
        }

        long sum = 0;
        for(int i=0;i<x.size()-1;i++) {
            for(int j=0;j<y.size()-1;j++) {
                for(int k=0;k<z.size()-1;k++) {
                    if(grid[i][j][k]) {
                        sum += (long) (x.get(i + 1) - x.get(i)) * (y.get(j+1) - y.get(j)) * (z.get(k+1) - z.get(k));
                    }
                }
            }
        }
        System.out.println(sum);
    }

    List<Instruction> getInput() {
        String input = FilesUtil.getContentOf("src/day22/input");
        return Arrays.stream(input.split("\n")).map(Instruction::new).toList();
    }
}

class Instruction {
    boolean command;
    Range x, y, z;

    Instruction(String line) {
        String[] parts = line.split("[ ,]");
        command = parts[0].equals("on");
        x = new Range(parts[1]);
        y = new Range(parts[2]);
        z = new Range(parts[3]);
    }

}

class Range {
    int min, max;

    Range(String line) {
        String[] parts = line.split("=")[1].split("\\.\\.");
        min = Integer.parseInt(parts[0]);
        max = Integer.parseInt(parts[1]);
    }
}