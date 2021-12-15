package day11;

import utils.FilesUtil;

import java.util.*;

public class Day11 {
    public void smallSol() {
        List<List<Integer>> list = getInput();

        int[] dx = {0, 0, 1, 1, 1, -1, -1, -1 };
        int[] dy = {1, -1, -1, 0, 1, -1, 0, 1};
        int turns = 100;
        long flashes = 0;

        for(int k=0;k<turns;k++) {
            for(int i=0;i<list.size();i++) {
                for(int j=0;j<list.get(0).size();j++) {
                    list.get(i).set(j, list.get(i).get(j) + 1);
                }
            }

            boolean done = false;
            boolean[][] visited = new boolean[list.size()][list.get(0).size()];

            while(!done) {
                done = true;
                for(int i=0;i<list.size();i++) {
                    for(int j=0;j<list.get(0).size();j++) {
                        if(list.get(i).get(j) > 9) {
                            flashes++;
                            list.get(i).set(j, 0);
                            visited[i][j] = true;
                            for(int t=0;t<8;t++) {
                                int x = i+dx[t];
                                int y = j+dy[t];

                                if(x < 0 || x >= list.size() || y < 0 || y >= list.get(0).size())
                                    continue;

                                if(visited[x][y])
                                    continue;

                                list.get(x).set(y, list.get(x).get(y) + 1);
                            }
                            done = false;
                        }
                    }
                }
            }
        }

        System.out.println(flashes);
    }

    public void largeSol() {
        List<List<Integer>> list = getInput();

        int[] dx = {0, 0, 1, 1, 1, -1, -1, -1 };
        int[] dy = {1, -1, -1, 0, 1, -1, 0, 1};
        // Simulate enough # of turns to get all flashes
        int turns = 1000;

        for(int k=0;k<turns;k++) {
            for(int i=0;i<list.size();i++) {
                for(int j=0;j<list.get(0).size();j++) {
                    list.get(i).set(j, list.get(i).get(j) + 1);
                }
            }

            boolean done = false;
            boolean[][] visited = new boolean[list.size()][list.get(0).size()];

            while(!done) {
                done = true;
                for(int i=0;i<list.size();i++) {
                    for(int j=0;j<list.get(0).size();j++) {
                        if(list.get(i).get(j) > 9) {
                            list.get(i).set(j, 0);
                            visited[i][j] = true;
                            for(int t=0;t<8;t++) {
                                int x = i+dx[t];
                                int y = j+dy[t];

                                if(x < 0 || x >= list.size() || y < 0 || y >= list.get(0).size())
                                    continue;

                                if(visited[x][y])
                                    continue;

                                list.get(x).set(y, list.get(x).get(y) + 1);
                            }
                            done = false;
                        }
                    }
                }
            }

            boolean allFlash = true;
            for(int i=0;i<visited.length;i++) {
                for(int j=0;j<visited[0].length;j++) {
                    if(!visited[i][j]) {
                        allFlash = false;
                        break;
                    }
                }
            }

            if(allFlash) {
                System.out.println(k+1);
            }

        }
    }

    List<List<Integer>> getInput() {
        String input = FilesUtil.getContentOf("src/day11/input");
        List<String> list = Arrays.stream(input.split("\n")).toList();
        List<List<Integer>> inputItems = new ArrayList<>();

        for(int i=0;i<list.size();i++) {
            inputItems.add(new ArrayList<>());
            for(int j=0;j<list.get(i).length();j++) {
                inputItems.get(i).add(list.get(i).charAt(j) - '0');
            }
        }
        return inputItems;
    }
}
