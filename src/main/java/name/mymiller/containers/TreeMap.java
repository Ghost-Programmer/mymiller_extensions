package name.mymiller.containers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @param <T> Type of Object to map
 * @author jmiller Class used to map values out on a tree. Allows for infinite
 * depth. Key names should be in form of "/text/text/text/...."
 */
public class TreeMap<T> {

    /**
     * Roote Node of the tree also known as "/"
     */
    private TreeNode root = null;

    /**
     * Adds the Value to the node at specified Key
     *
     * @param key   Specified branch to add values to
     * @param value Values to add at node
     */
    public void add(String key, T value) {
        final TreeNode node = this.findNode(key);

        ArrayList<T> values = node.getValues();
        if (values == null) {
            values = new ArrayList<>();
            node.setValues(values);
        }

        values.add(value);
    }

    /**
     * Add Collection of values to the specified node
     *
     * @param key        Specifies the node to add values to.
     * @param collection Collection of values to add to the node.
     * @return
     */
    public boolean addAll(String key, Collection<? extends T> collection) {
        final TreeNode node = this.findNode(key);

        ArrayList<T> values = node.getValues();
        if (values == null) {
            values = new ArrayList<>();
            node.setValues(values);
        }

        return values.addAll(collection);
    }

    /**
     * Clear all values from specified node
     *
     * @param key Node to clear all values from.
     */
    public void clearNode(String key) {
        final TreeNode node = this.findNode(key);
        final ArrayList<T> values = node.getValues();

        if (values != null) {
            values.clear();
        }
    }

    /**
     * Internal method used to find/create the tree structure to the specified node
     *
     * @param key Node to create/find
     * @return Node specified
     */
    private TreeNode findNode(String key) {
        final String[] keys = key.split("/");

        TreeNode currentNode = null;
        for (final String nodeKey : keys) {
            if (nodeKey.isEmpty()) {
                if (this.root == null) {
                    this.root = new TreeNode();
                }
                currentNode = this.root;
            } else if (currentNode == null) {
                if (this.root == null) {
                    this.root = new TreeNode();
                }
                currentNode = this.root;
            }

            if (!nodeKey.isEmpty()) {
                HashMap<String, TreeNode> children = currentNode.getChildren();
                if (children == null) {
                    currentNode.setChildren(new HashMap<>());
                    children = currentNode.getChildren();
                } else {
                    currentNode = children.get(nodeKey);
                }

                if (currentNode == null) {
                    currentNode = new TreeNode();
                    children.put(nodeKey, currentNode);
                }
            }
        }
        return currentNode;
    }

    /**
     * Returns the values associated with this key, or null if none are set.
     *
     * @param key Specified Node to return values on.
     * @return List of values.
     */
    public List<T> get(String key) {
        final TreeNode node = this.findNode(key);
        return node.getValues();
    }

    /**
     * Remove a value from specified node
     *
     * @param key   Specified node to remove value from
     * @param value Valueto remove from specified node.
     * @return boolean indicating success.
     */
    public boolean remove(String key, T value) {
        final TreeNode node = this.findNode(key);
        final ArrayList<T> values = node.getValues();

        if (values == null) {
            return false;
        }

        return values.remove(value);
    }

    /**
     * Remove the colleciton of values from the specified node
     *
     * @param key        Specifies the node to remove values from.
     * @param collection Collection of values to remove.
     * @return Boolean indicating if values remoted.
     */
    public boolean removeAll(String key, Collection<?> collection) {
        final TreeNode node = this.findNode(key);

        final ArrayList<T> values = node.getValues();
        if (values == null) {
            return false;
        }

        return values.removeAll(collection);
    }

    /**
     * Internal class used to map out tree structure and hold values.
     *
     * @author jmiller
     */
    private class TreeNode {
        /**
         * Values of this branch holds
         */
        private ArrayList<T> values = null;

        /**
         * Map directing to the children of this branch.
         */
        private HashMap<String, TreeNode> children = null;

        /**
         * @return Get the HashMap that has the children of this node.
         */
        public HashMap<String, TreeNode> getChildren() {
            return this.children;
        }

        /**
         * Sets the HashMap for this node, with the children
         *
         * @param children Hashmap of the children of this node.
         */
        public void setChildren(HashMap<String, TreeNode> children) {
            this.children = children;
        }

        /**
         * Get the values of the current Node
         *
         * @return ArrayList of the values on this node
         */
        public ArrayList<T> getValues() {
            return this.values;
        }

        /**
         * Sets the values of this node.
         *
         * @param values ArrayList containing the values of this node.
         */
        public void setValues(ArrayList<T> values) {
            this.values = values;
        }
    }

}
