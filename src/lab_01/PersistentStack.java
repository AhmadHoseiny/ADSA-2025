import java.util.ArrayList;

public class PersistentStack {

    ArrayList<Node> pointers;
    int currentVersion;

    PersistentStack () {
        pointers = new ArrayList<>();
        Node dummyNode = new Node(Integer.MAX_VALUE, null);
        pointers.add(dummyNode);
    }

    void push(int value) {
        Node parent = pointers.get(currentVersion); 
        Node newNode = new Node(value, parent);
        pointers.add(newNode);
        currentVersion = pointers.size() - 1;
    }

    int pop () {
        Node removedNode = pointers.get(currentVersion);
        pointers.add(removedNode.parent == null ? removedNode : removedNode.parent);
        currentVersion = pointers.size() - 1;
        return removedNode.value;
    }

    void checkout (int version) {
        this.currentVersion = version;
    }

    static class Node {
        int value;
        Node parent;

        Node(int value, Node parent) {
            this.value = value;
            this.parent = parent;
        }
    }

}