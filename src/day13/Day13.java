package day13;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 {
    public void smallSol() {
        Game game = getInput();

        game.doFold();


        System.out.println(game.points.size());
    }

    public void largeSol() {
        Game game = getInput();

        for(int i=0;i<game.folds.size();i++) {
            game.doFold();
        }

        int maxX = game.points.stream().map(item -> item.x).max(Integer::compareTo).get();
        int maxY = game.points.stream().map(item -> item.y).max(Integer::compareTo).get();

        for(int i=0;i<=maxY;i++) {
            for(int j=0;j<=maxX;j++) {
                if(game.contains(game.points, new Point(j, i))) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    Game getInput() {
        String input = FilesUtil.getContentOf("src/day13/input");
        String[] parts = input.split("\n\n");
        List<Point> points = Arrays.stream(parts[0].split("\n")).map(Point::new).toList();
        List<Fold> folds = Arrays.stream(parts[1].split("\n")).map(Fold::new).toList();
        return new Game(points, folds);
    }
}

class Game {
    List<Point> points;
    List<Fold> folds;
    int foldsIndex = 0;

    Game(List<Point> points, List<Fold> folds) {
        this.folds = folds;
        this.points = points;
    }

    void doFold() {
        Fold f = folds.get(foldsIndex);
        List<Point> newPoints = new ArrayList<>();
        // Could be optimized to only one for loop, too lazy to do it :'(
        if(f.isVertical) {
            for(int i=0;i<points.size();i++) {
                if(points.get(i).y > f.num) {
                    Point newPoint = points.get(i).fold(f);
                    if(!contains(newPoints, newPoint)) {
                        newPoints.add(newPoint);
                    }
                } else {
                    if(!contains(newPoints, points.get(i))) {
                        newPoints.add(points.get(i));
                    }
                }
            }
        } else {
            for(int i=0;i<points.size();i++) {
                if(points.get(i).x > f.num) {
                    Point newPoint = points.get(i).fold(f);
                    if(!contains(newPoints, newPoint)) {
                        newPoints.add(newPoint);
                    }
                } else {
                    if(!contains(newPoints, points.get(i))) {
                        newPoints.add(points.get(i));
                    }
                }
            }
        }
        points = newPoints;
        foldsIndex++;
    }

    boolean contains(List<Point> list, Point p) {
        return !list.stream().filter(item -> item.x == p.x && item.y == p.y).toList().isEmpty();
    }
}

class Point {
    int x, y;

    Point(String line) {
        String[] parts = line.split(",");
        this.x = Integer.parseInt(parts[0]);
        this.y = Integer.parseInt(parts[1]);
    }

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Point fold(Fold f) {
        if(f.isVertical) {
            int distance = y - f.num;
            return new Point(x, y - 2* distance);
        } else {
            int distance = x - f.num;
            return new Point(x - 2* distance, y);
        }
    }
}

class Fold  {
    int num;
    boolean isVertical;

    Fold(String line) {
        String[] parts = line.split("=");
        this.num = Integer.parseInt(parts[1]);
        this.isVertical = parts[0].contains("y");
    }
}