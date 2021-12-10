package day9;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day9 {

    int[] dx = {0, 1, -1, 0};
    int[] dy = {-1, 0, 0, 1};

    boolean[][] visited;

    public void smallSol() {
        List<String> list = getInput();

        int sum = 0;

        for(int i=0;i<list.size();i++) {
            for(int j=0;j<list.get(i).length();j++) {
                int item = list.get(i).charAt(j);
                boolean isLow = true;
                for(int k=0;k<4;k++) {
                    if(i+dx[k] >= 0 && i+dx[k] < list.size() && j+dy[k] >= 0 && j+dy[k] < list.get(i).length()) {
                        if(item >= list.get(i+dx[k]).charAt(j+dy[k])) {
                            isLow = false;
                            break;
                        }
                    }
                }
                if(isLow) {
                    sum += item - '0' + 1;
                }
            }
        }

        System.out.println(sum);
    }

    int dfs(List<String> list, int item, int x, int y) {

        if(x < 0 || x >= list.size() || y < 0 || y >= list.get(x).length()) {
            return 0;
        }

        if(item >= list.get(x).charAt(y) || list.get(x).charAt(y) == '9') {
            return 0;
        }

        if(visited[x][y]) {
            return 0;
        }

        visited[x][y] = true;



        int count = 1;

        for(int k=0;k<4;k++) {
            count += dfs(list, list.get(x).charAt(y), x+dx[k], y+dy[k]);
        }

        return count;
    }

    public void largeSol() {
        List<String> list = getInput();

        List<Point> points = new ArrayList<>();

        for(int i=0;i<list.size();i++) {
            for(int j=0;j<list.get(i).length();j++) {
                int item = list.get(i).charAt(j);
                boolean isLow = true;
                for(int k=0;k<4;k++) {
                    if(i+dx[k] >= 0 && i+dx[k] < list.size() && j+dy[k] >= 0 && j+dy[k] < list.get(i).length()) {
                        if(item >= list.get(i+dx[k]).charAt(j+dy[k])) {
                            isLow = false;
                            break;
                        }
                    }
                }
                if(isLow) {
                    points.add(new Point(i, j));
                }
            }
        }

        visited = new boolean[list.size()][list.get(0).length()];

        List<Integer> sizes = new ArrayList<>();

        for (Point p: points) {
            sizes.add(dfs(list, list.get(p.x).charAt(p.y) - 1, p.x, p.y));
        }

        List<Integer> listSorted = sizes.stream().sorted((o1, o2) -> o2-o1).toList();

        System.out.println(listSorted.get(0)*listSorted.get(1)*listSorted.get(2));
    }

    List<String> getInput() {
        String input = FilesUtil.getContentOf("src/day9/input");
        return Arrays.stream(input.split("\n")).toList();
    }
}

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

