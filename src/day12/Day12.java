package day12;

import utils.FilesUtil;

import java.util.*;

public class Day12 {

    Set<String> paths;
    HashMap<String, Integer> visitedWithRepeat;
    HashSet<String> visited;
    List<String> path;

    void dfsWithRepeat(HashMap<String, List<String>> grid, String current) {
        if(current.equals("end")) {
            paths.add(String.join(",", path));
            return ;
        }

        if(current.equals("start")) {
            if(visitedWithRepeat.getOrDefault(current, 0) == 1) {
                return;
            }
        }

        if(!isLargeCave(current)) {
            int currentVisits = visitedWithRepeat.getOrDefault(current, 0);
            if (currentVisits == 1) {
                if (visitedWithRepeat.values().stream().filter(item -> item > 1).toList().size() >= 1) {
                    return;
                }
            }
            else if (currentVisits >= 2) {
                return;
            }
            visitedWithRepeat.put(current, visitedWithRepeat.getOrDefault(current, 0) + 1);
        }

        for(String dest : grid.getOrDefault(current, new ArrayList<>())) {
            path.add(dest);
            dfsWithRepeat(grid, dest);
            path.remove(path.size() - 1);
        }

        if(!isLargeCave(current)) {
            visitedWithRepeat.put(current, Math.max(visitedWithRepeat.getOrDefault(current, 0) - 1, 0));
        }
    }

    void dfs(HashMap<String, List<String>> grid, String current) {
        if(current.equals("end")) {
            paths.add(String.join(",", path));
            return ;
        }

        if(current.equals("start") && visited.contains(current)) {
            return;
        }

        if(!isLargeCave(current)) {
            if(visited.contains(current)) {
                return;
            }
            visited.add(current);
        }

        for(String dest : grid.getOrDefault(current, new ArrayList<>())) {
            path.add(dest);
            dfs(grid, dest);
            path.remove(path.size() - 1);
        }

        visited.remove(current);
    }

    boolean isLargeCave(String cave) {
        int diff = cave.charAt(0) - 'A';
        return diff >= 0 && diff < 26;
    }


    public void smallSol() {
        HashMap<String, List<String>> grid = getInput();
        paths = new HashSet<>();
        visited = new HashSet<>();
        path = new ArrayList<>();
        path.add("start");
        dfs(grid, "start");

        System.out.println(paths.size());
    }

    public void largeSol() {
        HashMap<String, List<String>> grid = getInput();
        paths = new HashSet<>();
        visitedWithRepeat = new HashMap<>();
        path = new ArrayList<>();
        path.add("start");
        dfsWithRepeat(grid, "start");

        System.out.println(paths.size());
    }

    HashMap<String, List<String>> getInput() {
        String input = FilesUtil.getContentOf("src/day12/input");
        List<Path> list = Arrays.stream(input.split("\n")).map(line -> new Path(line)).toList();

        HashMap<String, List<String>> grid = new HashMap<>();

        for(Path p: list) {
            List<String> outbound = grid.getOrDefault(p.start, new ArrayList<>());
            outbound.add(p.end);
            grid.put(p.start, outbound);

            List<String> inbound = grid.getOrDefault(p.end, new ArrayList<>());
            inbound.add(p.start);
            grid.put(p.end, inbound);
        }

        return grid;
    }
}

class Path {
    String start, end;

    Path(String path) {
        String[] parts = path.split("-");
        start = parts[0];
        end = parts[1];
    }
}