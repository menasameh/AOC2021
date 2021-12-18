package day18;

import utils.FilesUtil;
import java.util.Arrays;
import java.util.List;

public class Day18 {
    public void smallSol() {
        List<Node> list = getInput();
        Node add = list.get(0);
        for(int i=1;i<list.size();i++) {
            add = Node.add(add, list.get(i));
            add.simplify();
        }
        System.out.println(add.magnitude());
    }

    public void largeSol() {
        List<Node> list = getInput();
        int max = 0;
        for(int i=0;i<list.size();i++) {
            for(int j=i+1;j<list.size();j++) {
                Node add = Node.add(list.get(i).copy(null), list.get(j).copy(null));
                add.simplify();
                max = Math.max(max, add.magnitude());
                add = Node.add(list.get(j).copy(null), list.get(i).copy(null));
                add.simplify();
                max = Math.max(max, add.magnitude());
            }
        }

        System.out.println(max);
    }

    List<Node> getInput() {
        String input = FilesUtil.getContentOf("src/day18/input");
        return Arrays.stream(input.split("\n")).map(Node::new).toList();
    }
}

class Node {
    int value;
    Node left, right;
    Node parent;

    Node() {}

    Node(String line) {
        Node temp = this;
        int val = 0;
        for(int i=0;i<line.length();i++) {
            if(line.charAt(i) == '[') {
                temp.left = new Node();
                temp.left.parent = temp;
                temp = temp.left;
            } else if (line.charAt(i) == ']') {
                temp.value = val;
                val = 0;
                temp = temp.parent;
            } else if (line.charAt(i) == ',') {
                temp.value = val;
                val = 0;
                temp.parent.right = new Node();
                temp.parent.right.parent = temp.parent;
                temp = temp.parent.right;
            } else {
                val = val*10 + (line.charAt(i) - '0');
            }
        }
    }

    static Node add(Node a, Node b) {
        Node temp = new Node();
        temp.left = a;
        temp.left.parent = temp;
        temp.right = b;
        temp.right.parent = temp;

        return temp;
    }

    Node copy(Node parent) {
        Node left = null;
        Node right = null;
        Node temp = new Node();
        if (this.left != null) {
            left = this.left.copy(temp);
        }
        if (this.right != null) {
            right = this.right.copy(temp);
        }
        temp.parent = parent;
        temp.left = left;
        temp.right = right;
        temp.value = value;
        return temp;
    }

    void simplify() {
        boolean done = false;
        while(!done) {
            if(tryExplode(0, this)) {
                continue;
            }

            if(!trySplit( this)) {
                done = true;
            }
        }

    }

    boolean tryExplode(int level, Node parent) {

        if(level == 4) {
            if(parent.left == null) {
                return false;
            }
            addleft(parent, parent.left, parent.left.value);
            addRight(parent, parent.right, parent.right.value);
            parent.value = 0;
            parent.left = null;
            parent.right = null;
            return true;
        }

        if(parent.left != null) {
            if(tryExplode(level+1, parent.left)) {
                return true;
            }
        }

        if(parent.right != null) {
            if(tryExplode(level+1, parent.right)) {
                return true;
            }
        }

        return false;
    }

    void addleft(Node parent, Node curr, int value) {
        while(parent.left == curr) {
            curr = parent;
            parent = parent.parent;
            if(parent == null) {
                return;
            }
        }

        Node near = parent.left;
        while(near.right != null) {
            near = near.right;
        }
        if(near == null) {
            return;
        }
        near.value = near.value + value;
    }

    void addRight(Node parent, Node curr, int value) {
        while(parent.right == curr) {
            curr = parent;
            parent = parent.parent;
            if(parent == null) {
                return;
            }
        }
        Node near = parent.right;
        while(near.left != null) {
            near = near.left;
        }
        if(near == null) {
            return;
        }
        near.value = near.value + value;
    }

    boolean trySplit(Node parent) {
        if(parent.left == null && parent.right == null) {
            if (parent.value > 9) {
                parent.left = new Node();
                parent.left.parent = parent;
                parent.left.value = parent.value/2;
                parent.right = new Node();
                parent.right.parent = parent;
                parent.right.value = parent.value - parent.left.value;
                parent.value = 0;
                return true;
            }
        }

        if(parent.left != null) {
            if(trySplit(parent.left)) {
                return true;
            }
        }

        if(parent.right != null) {
            if(trySplit(parent.right)) {
                return true;
            }
        }
        return false;
    }

    int magnitude() {
        return mag(this);
    }

    int mag(Node parent) {
        if(parent.left == null && parent.right == null) {
            return parent.value;
        }

        return 3*mag(parent.left)+2*mag(parent.right);
    }
}