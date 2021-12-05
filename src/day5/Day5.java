package day5;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 {

    public void smallSol() {
        List<Line> lines = getInput();

        int[][] grid = new int[1000][1000];

        for(Line l: lines) {
            if(l.isHorizontalOrVertical()) {
                List<Point> points = l.getPoints();

                for(Point p: points) {
                    grid[p.x][p.y]++;
                }
            }
        }

        int count = 0;

        for(int i=0;i<grid.length;i++) {
            for(int j=0;j<grid[0].length;j++) {
                if(grid[i][j] > 1) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    public void largeSol() {
        List<Line> lines = getInput();

        int[][] grid = new int[1000][1000];

        for(Line l: lines) {
            List<Point> points = l.getPoints();

            for(Point p: points) {
                grid[p.x][p.y]++;
            }
        }

        int count = 0;

        for(int i=0;i<grid.length;i++) {
            for(int j=0;j<grid[0].length;j++) {
                if(grid[i][j] > 1) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    List<Line> getInput() {
        String input = FilesUtil.getContentOf("src/day5/input");
        return Arrays.stream(input.split("\n")).map(Line::new).toList();
    }
}


class Line {
    Point start, end;

    Line(String line) {
        List<String> segments = Arrays.stream(line.split(" -> ")).toList();
        this.start = new Point(segments.get(0));
        this.end = new Point(segments.get(1));
    }

    List<Point> getPoints() {
        // considering the equation y=mx+c
        double m = ((double) end.y - (double) start.y)/( (double)end.x - (double) start.x);
        double c = start.y - m * start.x;

        List<Point> points = new ArrayList<>();

        int startX = Math.min(start.x, end.x);
        int endX = Math.max(start.x, end.x);

        for(int i=startX;i<=endX;i++) {
            double y = m * i + c;
            if(isInteger(y)) {
                points.add(new Point(i, (int) y));
            }
        }

        // in case of vertical lines, m=-infinity, so we don't need to calculate the x value.
        // simply it's the same for all points
        if(startX == endX) {
            int startY = Math.min(start.y, end.y);
            int endY = Math.max(start.y, end.y);

            for(int i=startY;i<=endY;i++) {
                points.add(new Point(startX, i));
            }
        }

        return points;
    }

    boolean isInteger(double number) {
        return Math.ceil(number) == Math.floor(number);
    }

    boolean isHorizontalOrVertical() {
        return start.x == end.x || start.y == end.y;
    }
}

class Point {
    int x,y;

    Point(String point) {
        List<Integer> segments = Arrays.stream(point.split(",")).map(Integer::valueOf).toList();
        this.x = segments.get(0);
        this.y = segments.get(1);
    }

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}