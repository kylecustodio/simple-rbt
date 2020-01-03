// Kyle Custodio | KYC180000
// CS 3345 | Data Structures and Intro to Algorithmic Analysis
// Section 001 | Anjum Chida
// Fall 2019
// Project 4: A simplified Red-Black Tree with the only operations being insert, contains, and toString

/**
 * a simple red black tree with only insert, contains, and toString operations
 * @param <E> a comparable object
 */
public class RedBlackTree<E extends Comparable<E>> {
    /**
     * a node of the simple red black tree
     * @param <E> a comporable object
     */
    static class Node<E extends Comparable<E>> {
        /** the element of the node */
        E element;

        /** the left child of the node */
        Node<E> leftChild;

        /** the right child of the node */
        Node<E> rightChild;

        /** the parent of the node */
        Node<E> parent;

        /** the color of the node */
        boolean color;

        /**
         * creates a new red node
         * @param element the element of the new node
         */
        public Node(E element) {
            this.element = element;
            this.leftChild = null;
            this.rightChild = null;
            this.parent = null;
            this.color = RED;
        }

        /**
         * returns the toString value of the element with a '*' preceding if the node is red
         * @return a string represenation of the node
         */
        @Override
        public String toString() {
            if (color == RED) {
                return "*" + element.toString();
            } else {
                return element.toString();
            }
        }
    }

    /** the boolean value for RED */
    private static final boolean RED = true;

    /** the boolean value for BLACK */
    private static final boolean BLACK = false;

    /** the root of the tree */
    private Node<E> root;

    /**
     * creates an empty redblack tree
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Inserts a given element into a redback tree
     * @param element the element to be inserted in the redblack tree
     * @return false if the tree already has element, true if insertion was successful
     * @throws NullPointerException if element is null
     */
    public boolean insert(E element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("Error in insert: NullPointerException raised");
        }

        if (contains(root, element)) {
            return false;
        }

        Node<E> node = new Node<>(element);
        root = insert(root, node);
        fixTree(node);
        return true;
    }

    /**
     * recursively inserts a node into the tree into its proper location
     * @param root the root of the tree
     * @param node the node to be inserted into the tree
     * @return the root of the tree or the node that was just inserted
     */
    private Node<E> insert(Node<E> root, Node<E> node) {
        if (root == null) {
            return node;
        }

        int compare = root.element.compareTo(node.element);

        if (compare > 0) {
            root.leftChild = insert(root.leftChild, node);
            root.leftChild.parent = root;
        } else if (compare < 0) {
            root.rightChild = insert(root.rightChild, node);
            root.rightChild.parent = root;
        }
        return root;

    }

    /**
     * fixes the tree starting from the node that was just inserted, working up towards the root
     * @param node the node that was just inserted
     */
    private void fixTree(Node<E> node) {
        Node<E> parent;
        Node<E> grandParent;
        Node<E> uncle;

        while (node != root && node.color == RED && node.parent.color == RED) {
            parent = node.parent;
            grandParent = parent.parent;

            if (parent == grandParent.leftChild) {
                uncle = grandParent.rightChild;

                if (uncle != null && uncle.color == RED) { // left left w/ red uncle: only recolor
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    if (node == parent.rightChild) { // left right w/ black uncle: rotate once to get left left
                        rotateLeft(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    // left left w/ black uncle: single rotate
                    rotateRight(grandParent);
                    swapColors(parent, grandParent);
                    node = parent;
                }
            } else {
                uncle = grandParent.leftChild;

                if (uncle != null && uncle.color == RED) { // right right w/ red uncle: only recolor
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    if (node == parent.leftChild) { // right left w/ black uncle: rotate once to get right right
                        rotateRight(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    // right right w/ black uncle: single rotate
                    rotateLeft(grandParent);
                    swapColors(parent, grandParent);
                    node = parent;
                }
            }
        }

        root.color = BLACK; // root can never be red
    }

    /**
     * rotates a given node to the left
     * @param node the node to be rotated
     */
    private void rotateLeft(Node<E> node) {
        Node<E> right = node.rightChild;
        node.rightChild = right == null ? null : right.leftChild;

        if (node.rightChild != null) {
            node.rightChild.parent = node;
        }

        right.parent = node.parent;

        if (node.parent == null) {
            root = right;
        } else if (node == node.parent.leftChild) {
            node.parent.leftChild = right;
        } else {
            node.parent.rightChild = right;
        }

        right.leftChild = node;
        node.parent = right;
    }

    /**
     * rotates a given node to the right
     * @param node the node to be rotated
     */
    private void rotateRight(Node<E> node) {
        Node<E> left = node.leftChild;
        node.leftChild = left == null ? null : left.rightChild;

        if (node.leftChild != null) {
            node.leftChild.parent = node;
        }

        left.parent = node.parent;

        if (node.parent == null) {
            root = left;
        } else if (node == node.parent.leftChild) {
            node.parent.leftChild = left;
        } else {
            node.parent.rightChild = left;
        }

        left.rightChild = node;
        node.parent = left;
    }

    /**
     * swaps the colors of two given nodes
     * @param node1 the first node
     * @param node2 the second node
     */
    private void swapColors(Node<E> node1, Node<E> node2) {
        boolean color1 = node1.color;

        node1.color = node2.color;
        node2.color = color1;
    }

    /**
     * checks if the tree contains a given element
     * @param element the element to look for in the tree
     * @return true if the element is in the tree, false if not
     */
    public boolean contains(E element) {
        return contains(root, element);
    }

    /**
     * traverses the tree recursively to check if a node with the given element is in the tree
     * @param root the root of the tree
     * @param element the element to look for in the tree
     * @return true if the element is in the tree, false if not
     */
    private boolean contains(Node<E> root, E element) {
        if (root == null) {
            return false;
        }

        int compare = root.element.compareTo(element);

        if (compare > 0) {
            return contains(root.leftChild, element);
        } else if(compare < 0) {
            return contains(root.rightChild, element);
        }

        return true;
    }

    /**
     * converts the elements of the tree into a string
     * @return a string representation of the tree
     */
    @Override
    public String toString() {
        return toString(root);
    }

    /**
     * converts the tree to a string with preorder traversal
     * @param root the root of the tree
     * @return a string representation of the tree
     */
    private String toString(Node<E> root) {
        String s = "";

        if (root == null) {
            return s;
        }

        s += root.toString() + " ";
        s += toString(root.leftChild);
        s += toString(root.rightChild);

        return s;
    }
}
