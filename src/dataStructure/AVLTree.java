package dataStructure;


public class AVLTree {
    class Node {
        String key;
        int height;
        Node left, right;

        Node(String d) {
            key = d;
            height = 1;
        }
    }


    public Node root;

    int height(Node N) {
        if (N == null)
            return 0;

        return N.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    String max_string(String a, String b) {

        if(a.compareTo(b) >= 0){
            return a;
        }
        return b;
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }


    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Get Balance factor of node N
    int getBalance(Node N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }

    public Node insert(Node node, String key) {


        if (node == null)
            return (new Node(key));

        if(key.compareTo(node.key) < 0)
            node.left = insert(node.left, key);
        else if (key.compareTo(node.key) > 0)
            node.right = insert(node.right, key);
        else // Duplicate keys not allowed
            return node;

        node.height = 1 + max(height(node.left),
                height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && key.compareTo(node.left.key) < 0)
            return rightRotate(node);

        if (balance < -1 && key.compareTo(node.right.key) > 0 )
            return leftRotate(node);

        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }


        return node;
    }

    public Boolean find(Node node, String key){
        if(node == null){
            return false;
        }
        if(key.equalsIgnoreCase(node.key)){
            return true;
        }

        find(node.right,key);
        return find(node.left,key) || find(node.right,key);
    }
}