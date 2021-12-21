package day19;

import utils.FilesUtil;

import java.util.*;

public class Day19 {

    List<List<Integer>> perms;

    void getPerm() {
        List<List<Integer>> perm = new ArrayList<>();

        for(int i=0;i<6;i++) {
            perm.add(new ArrayList<>());
        }
        perm.get(0).add(1);perm.get(0).add(2);perm.get(0).add(3);
        perm.get(1).add(2);perm.get(1).add(1);perm.get(1).add(3);
        perm.get(2).add(3);perm.get(2).add(1);perm.get(2).add(2);

        perm.get(3).add(1);perm.get(3).add(3);perm.get(3).add(2);
        perm.get(4).add(2);perm.get(4).add(3);perm.get(4).add(1);
        perm.get(5).add(3);perm.get(5).add(2);perm.get(5).add(1);

        HashMap<String, List<Integer>> map = new HashMap<>();


        List<List<Integer>> masks = new ArrayList<>();
        for(int i=0;i<8;i++) {
            masks.add(new ArrayList<>());
        }
        masks.get(0).add(1);masks.get(0).add(1);masks.get(0).add(1);
        masks.get(1).add(1);masks.get(1).add(1);masks.get(1).add(-1);

        masks.get(2).add(1);masks.get(2).add(-1);masks.get(2).add(1);
        masks.get(3).add(1);masks.get(3).add(-1);masks.get(3).add(-1);

        masks.get(4).add(-1);masks.get(4).add(1);masks.get(4).add(1);
        masks.get(5).add(-1);masks.get(5).add(1);masks.get(5).add(-1);

        masks.get(6).add(-1);masks.get(6).add(-1);masks.get(6).add(1);
        masks.get(7).add(-1);masks.get(7).add(-1);masks.get(7).add(-1);


        for(List<Integer> p : perm) {
            for(List<Integer> mask : masks) {
                int x = p.get(0) * mask.get(0);
                int y = p.get(1) * mask.get(1);
                int z = p.get(2) * mask.get(2);
                String s = x+","+y+","+z;
                List<Integer> vals = new ArrayList<>();
                vals.add(x);vals.add(y);vals.add(z);
                map.put(s, vals);
            }
        }

        perms = new ArrayList<>(map.values());
    }

    int getDefaultOr() {
        for(int i=0;i< perms.size();i++) {
            if(perms.get(i).get(0) == 1 && perms.get(i).get(1) == 2 && perms.get(i).get(2) == 3) {
                return i;
            }
        }
        System.out.println("5ara");
        return 0;
    }

    public void smallSol() {
        List<Scanner> list = getInput();
        list.get(0).diff = new Beacon(0, 0, 0);
        // 44 means default rotations x, y, z
        list.get(0).orientation = getDefaultOr();

        int whil = 0;
        int count = 1;
        while(count != list.size()) {
            whil++;
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if(i == j) {
                        continue;
                    }
                    if(list.get(i).orientation == null) {
                        continue;
                    }
                    if(list.get(j).orientation != null) {
                        continue;
                    }
                    for (int k=0;k< perms.size();k++) {
                        if(hasOverlapping(list.get(i), list.get(j), list.get(i).orientation, k)) {
                            list.get(j).orientation = k;
                            count++;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println(whil);
//        for (int i = 0; i < list.size(); i++) {
//            for (int j = 0; j < list.size(); j++) {
//                if(i == j) {
//                    continue;
//                }
//                if(list.get(i).orientation == null) {
//                    continue;
//                }
//                for (int k=0;k< perms.size();k++) {
//                    if(hasOverlapping(list.get(i), list.get(j), list.get(i).orientation, k)) {
//                        list.get(j).orientation = k;
//                        break;
//                    }
//                }
//            }
//        }

//        for (int i = 0; i < list.size(); i++) {
//            for (int j = i + 1; j < list.size(); j++) {
//                for (int k=0;k< perms.size();k++) {
//                    if(list.get(j).orientation == null) {
//                        continue;
//                    }
//                    if(hasOverlapping(list.get(j), list.get(i), list.get(j).orientation, k)) {
//                        list.get(i).orientation = k;
//                        break;
//                    }
//                }
//            }
//        }

//        for(int i=0;i<list.size();i++) {
//            if(list.get(i).orientation == null) {
//                System.out.println(i);
//            }
//        }

        Set<String> beacons = new HashSet<>();
        for(Scanner s : list) {
            beacons.addAll(s.getModBeacons(perms).stream().map(i -> i.x + ","+i.y + ","+i.z).toList());
        }

        int max = 0;
        for(int i=0;i<list.size();i++) {
            for(int j=0;j<list.size();j++) {
                max = Math.max(max, Beacon.manDistance(list.get(i).diff, list.get(j).diff));
            }
        }

        System.out.println(max);
    }

    boolean hasOverlapping(Scanner a, Scanner b, int orientationA, int orientationB) {
        Scanner flippedA = a.flip(perms.get(orientationA));
        Scanner flippedB = b.flip(perms.get(orientationB));

        return hasOverlapping(flippedA, flippedB, b);
    }

    boolean hasOverlapping(Scanner a, Scanner b, Scanner c) {
        List<Pair> match = new ArrayList<>();
        for(int i=0;i<a.beacons.size();i++) {
            if(b.low != null) {
                break;
            }
            for(int j=0;j<b.beacons.size();j++) {
                Set<Integer> visitedI = new HashSet<>();
                Set<Integer> visitedJ = new HashSet<>();
                match = new ArrayList<>();
                Beacon xLow = Beacon.low(a.beacons.get(i), b.beacons.get(j));
                Beacon xHigh = Beacon.high(a.beacons.get(i), b.beacons.get(j));

                int matches = 0;

                for(int ii=0;ii<a.beacons.size();ii++) {
                    for(int jj=0;jj<b.beacons.size();jj++) {
                        if (visitedI.contains(ii) || visitedJ.contains(jj)) {
                            continue;
                        }
                        Beacon low = Beacon.low(a.beacons.get(ii), b.beacons.get(jj));

                        if(low.x == xLow.x && low.y == xLow.y && low.z == xLow.z) {
                            matches++;
                            visitedI.add(ii);
                            visitedJ.add(jj);
                            match.add(new Pair(ii, jj));
                        }
                    }
                }

                if(matches >= 12) {
                    b.low = Beacon.low(a.beacons.get(i), b.beacons.get(j));
                    System.out.println("set low "+i+","+j);
//                    b.high = Beacon.high(a.beacons.get(i), b.beacons.get(j));
                    break;
                }
                else {
                    matches = 0;
                    match = new ArrayList<>();
                    visitedI = new HashSet<>();
                    visitedJ = new HashSet<>();
                    for(int ii=0;ii<a.beacons.size();ii++) {
                        for(int jj=0;jj<b.beacons.size();jj++) {
                            if (visitedI.contains(ii) || visitedJ.contains(jj)) {
                                continue;
                            }
                            Beacon high = Beacon.high(a.beacons.get(ii), b.beacons.get(jj));

                            if(high.x == xHigh.x && high.y == xHigh.y && high.z == xHigh.z) {
                                matches++;
                                visitedI.add(ii);
                                visitedJ.add(jj);
                                match.add(new Pair(ii, jj));
                            }
                        }
                    }

                    if(matches >= 12) {
                        System.out.println("set high "+i+","+j);
                        b.low = Beacon.high(a.beacons.get(i), b.beacons.get(j));
                        break;
                    }
                }
            }
        }

        if(match.size() < 12) {
            return false;
        }

        if(b.low == null) {
            System.out.println("null");
        }

        c.diff = new Beacon(a.diff.x+b.low.x, a.diff.y+b.low.y, a.diff.z+b.low.z);

//        Beacon i = a.beacons.get(match.get(0).a);
//        Beacon ii = a.beacons.get(match.get(1).a);
//        Beacon j = b.beacons.get(match.get(0).b);
//        Beacon jj = b.beacons.get(match.get(1).b);
//
//        int xpos = getPos(i.x, ii.x, j.x, jj.x, b.low.x, b.high.x);
//        int ypos = getPos(i.y, ii.y, j.y, jj.y, b.low.y, b.high.y);
//        int zpos = getPos(i.z, ii.z, j.z, jj.z, b.low.z, b.high.z);
//
//        c.diff = new Beacon(xpos+a.diff.x, ypos+a.diff.y, zpos+a.diff.z);
//        c.inversion = new Beacon(i.x == -1 * j.x + c.diff.x ? -1 : 1,
//                i.y == -1 * j.y + c.diff.y ? -1 : 1,
//                i.z == -1 * j.z + c.diff.z ? -1 : 1);

        return true;
    }

    int getPos(int i, int ii, int j, int jj, int low, int high) {
//        if(Math.abs(ii) < Math.abs(i) && Math.abs(jj) < Math.abs(j) || Math.abs(ii) > Math.abs(i) && Math.abs(jj) > Math.abs(j)) {
        if(ii < i && jj < j || ii > i && jj > j) {
                // low
                if(Math.abs(j) > Math.abs(i)) {
                    return i > 0 ? -low : low;
                } else {
                    return i > 0 ? low : -low;
                }
            } else {
                // high
                if(i > 0) {
                    return high;
                } else {
                    return -high;
                }
            }
//        }
    }

    public void largeSol() {
        List<Scanner> list = getInput();

        System.out.println(list);
    }

    List<Scanner> getInput() {
        String input = FilesUtil.getContentOf("src/day19/input");
        String[] parts = input.split("\n\n");
        getPerm();
        return Arrays.stream(input.split("\n\n")).map(Scanner::new).toList();
    }
}

class Scanner {
    List<Beacon> beacons;
    Beacon low, high;
    Beacon diff;
    Beacon inversion;
    Integer orientation;

    List<Beacon> getModBeacons() {
        return beacons
                .stream()
                .map(item -> Beacon.invert(item, inversion))
                .map(item -> Beacon.add(item, diff))
                .toList();
    }

    List<Beacon> getModBeacons(List<List<Integer>> perms) {
        return beacons
                .stream()
                .map(item -> item.flip(perms.get(orientation)))
                .map(item -> Beacon.add(item, diff))
                .toList();
    }

    Scanner swapXY() {
        return new Scanner(new ArrayList<>(beacons.stream().map(Beacon::swapXY).toList()), low, high, diff, inversion);
    }

    Scanner swapXZ() {
        return new Scanner(new ArrayList<>(beacons.stream().map(Beacon::swapXZ).toList()), low, high, diff, inversion);
    }

    Scanner flip(List<Integer> orientation) {
        return new Scanner(new ArrayList<>(beacons.stream().map(item -> item.flip(orientation)).toList()), low, high, diff, inversion);
    }

    Scanner(List<Beacon> beacons, Beacon low, Beacon high, Beacon diff, Beacon inversion) {
        this.beacons = beacons;
        this.low = low;
        this.high = high;
        this.diff = diff;
        this.inversion = inversion;
    }

    Scanner(String line) {
        String[] parts = line.split("\n");

        beacons = new ArrayList<>(Arrays.stream(parts).skip(1).map(Beacon::new).toList());
    }
}

class Beacon {
    int x, y, z;

    static Beacon low(Beacon a, Beacon b) {
        return new Beacon(a.x-b.x, a.y-b.y, a.z-b.z);
    }

    static Beacon lowAbs(Beacon a, Beacon b) {
        return new Beacon(Math.abs(Math.abs(a.x)-Math.abs(b.x)), Math.abs(Math.abs(a.y)-Math.abs(b.y)), Math.abs(Math.abs(a.z)-Math.abs(b.z)));
    }

    static Beacon high(Beacon a, Beacon b) {
        return new Beacon(Math.abs(Math.abs(a.x)+Math.abs(b.x)), Math.abs(Math.abs(a.y)+Math.abs(b.y)), Math.abs(Math.abs(a.z)+Math.abs(b.z)));
    }

    static Beacon add(Beacon a, Beacon b) {
        return new Beacon(a.x+b.x, a.y+b.y, a.z+b.z);
    }

    static int manDistance(Beacon a, Beacon b) {
        return  Math.abs(a.x-b.x)+
                Math.abs(a.y-b.y)+
                Math.abs(a.z-b.z);
    }

    static Beacon invert(Beacon a, Beacon mask) {
        return new Beacon(mask.x * a.x, mask.y * a.y, mask.z * a.z);
    }

    Beacon swapXY() {
        return new Beacon(y, x, z);
    }

    Beacon swapXZ() {
        return new Beacon(z, y, x);
    }

    Beacon flip(List<Integer> orientation) {
        if(orientation.get(0) == 1 && orientation.get(1) == 2 && orientation.get(2) == 3) {
            return new Beacon(x, y, z);
        }
        int x = 0;
        int y = 0;
        int z = 0;

        if(Math.abs(orientation.get(0)) == 1) {
            x = orientation.get(0) > 0 ? this.x : -this.x;
        } else if(Math.abs(orientation.get(0)) == 2) {
            x = orientation.get(0) > 0 ? this.y : -this.y;
        } else {
            x = orientation.get(0) > 0 ? this.z : -this.z;
        }

        if(Math.abs(orientation.get(1)) == 1) {
            y = orientation.get(1) > 0 ? this.x : -this.x;
        } else if(Math.abs(orientation.get(1)) == 2) {
            y = orientation.get(1) > 0 ? this.y : -this.y;
        } else {
            y = orientation.get(1) > 0 ? this.z : -this.z;
        }

        if(Math.abs(orientation.get(2)) == 1) {
            z = orientation.get(2) > 0 ? this.x : -this.x;
        } else if(Math.abs(orientation.get(2)) == 2) {
            z = orientation.get(2) > 0 ? this.y : -this.y;
        } else {
            z = orientation.get(2) > 0 ? this.z : -this.z;
        }
        return new Beacon(x, y ,z);
    }

    Beacon(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Beacon(String line) {
        String[] parts = line.split(",");
        x = Integer.parseInt(parts[0]);
        y = Integer.parseInt(parts[1]);
        z = Integer.parseInt(parts[2]);
    }
}

class Pair {
    int a,b;

    Pair(int a, int b) {
        this.a = a;
        this.b = b;
    }
}