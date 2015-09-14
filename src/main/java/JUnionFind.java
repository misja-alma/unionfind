import java.util.Optional;

public class JUnionFind implements UnionFind {
    private final Node[] nodes;

    public JUnionFind(int size) {
        nodes = new Node[size];
        // Note that in Java we can't use Array.fill, because it would initialize all elements to the same Node instance.
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(Optional.empty(), 1);
        }
    }

    public JUnionFind union(int t1, int t2) {
        if (t1 == t2) return this;

        int root1 = root(t1);
        int root2 = root(t2);
        if (root1 == root2) return this;

        Node node1 = nodes[root1];
        Node node2 = nodes[root2];

        if (node1.treeSize < node2.treeSize) {
            node1.parent = Optional.of(t2);
            node2.treeSize += node1.treeSize;
        } else {
            node2.parent = Optional.of(t1);
            node1.treeSize += node2.treeSize;
        }
        return this;
    }

    public boolean connected(int t1, int t2) {
        return t1 == t2 || root(t1) == root(t2);
    }

    private int root(int t) {
        Optional<Integer> parent = nodes[t].parent;
        return parent.isPresent()? root(parent.get()): t;
    }
}

class Node {
    Optional<Integer> parent;
    int treeSize;

    public Node(Optional<Integer> parent, int size) {
        this.parent = parent;
        this.treeSize = size;
    }
}
