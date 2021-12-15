package day15;

import utils.FilesUtil;

import java.util.*;

public class Day15 {
    int[] dx = {0, 1, -1, 0};
    int[] dy = {-1, 0, 0, 1};

    boolean[][] visited;

    public void smallSol() {
        List<List<Integer>> list = getInput();

        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.pathSum));
        visited = new boolean[list.size()][list.get(0).size()];

        Point p = new Point(0, 0, 0);
        queue.add(p);
        while (!queue.isEmpty()) {
            p = queue.poll();

            if(p.x == list.size() -1 && p.y == list.get(0).size()-1) {
                break;
            }

            visited[p.x][p.y] = true;

            for(int k=0;k<4;k++) {
                int i = p.x + dx[k];
                int j = p.y + dy[k];

                if(i < 0 || i >= list.size() || j < 0 || j>= list.get(0).size()) {
                    continue;
                }

                if (visited[i][j]) {
                    continue;
                }
                Point np = new Point(i, j, p.pathSum + list.get(i).get(j));
                queue.add(np);
            }
        }
        System.out.println(p.pathSum);
    }

    public void largeSol() {
        List<List<Integer>> grid = getInput();
        List<List<Integer>> newGrid = new ArrayList<>();

        // Expanding the grid 5X
        for(int i=0;i<grid.size()*5;i++) {
            newGrid.add(new ArrayList<>());
            for(int j=0;j<grid.get(0).size()*5;j++) {
                int iDiff = i/grid.size();
                int jDiff = j/grid.get(0).size();
                int diff = iDiff+jDiff;
                int item = grid.get(i%grid.size()).get(j%grid.get(0).size()) + diff;
                newGrid.get(i).add(item > 9 ? (item%10) + 1 : item);
            }
        }
        grid = newGrid;

        System.out.println(grid.size()*grid.get(0).size());

        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.pathSum));

        visited = new boolean[grid.size()][grid.get(0).size()];

        Point p = new Point(0, 0, 0);
        visited[0][0] = true;
        queue.add(p);
        while (!queue.isEmpty()) {
            p = queue.poll();

            if(p.x == grid.size() -1 && p.y == grid.get(0).size()-1) {

                break;
            }

            for(int k=0;k<4;k++) {
                int i = p.x + dx[k];
                int j = p.y + dy[k];

                if(i < 0 || i >= grid.size() || j < 0 || j>= grid.get(0).size()) {
                    continue;
                }

                if (visited[i][j]) {
                    continue;
                }

                visited[i][j] = true;
                Point np = new Point(i, j, p.pathSum + grid.get(i).get(j));
                queue.add(np);
            }
        }
        System.out.println(p.pathSum);
    }

    List<List<Integer>> getInput() {
        String input = FilesUtil.getContentOf("src/day15/input");
        List<String> items =  Arrays.stream(input.split("\n")).toList();

        List<List<Integer>> ret = new ArrayList<>();

        for(int i=0;i<items.size();i++) {
            ret.add(new ArrayList<>());
            for(char c:items.get(i).toCharArray()) {
                ret.get(i).add(c-'0');
            }
        }
        return ret;
    }
}

class Point {
    int x, y;
    int pathSum;

    Point(int x, int y, int pathSum) {
        this.x = x;
        this.y = y;
        this.pathSum = pathSum;
    }
}