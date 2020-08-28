/******************************************************************************
 Copyright 2018 MyMiller Consulting LLC.

 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License.  You may obtain a copy
 of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 License for the specific language governing permissions and limitations under
 the License.
 */
package name.mymiller.containers;

import java.io.Serializable;

/**
 * @param <E> Object type for this list.
 * @author jmiller Abstract list class for List Container
 */
public abstract class AbstractList<E> implements Container<E>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2389678681216763471L;
    /**
     * Pointer to Head of linked list
     */
    private Node head = null;
    /**
     * Pointer to Tail of Linked List
     */
    private Node tail = null;
    /**
     * Length of Linked List
     */
    private int length = 0;

    /**
     * Add the value to the Container.
     *
     * @param value Element to add
     * @return boolean indicating if successfully added
     */
    protected boolean add(final E value) {
        if ((this.head == null) && (this.tail == null)) {
            final Node node = new Node(value);

            this.setHead(node);
            this.setTail(node);
        } else {
            final Node node = new Node(value, this.getTail(), null);
            this.getTail().setNext(node);
            this.setTail(node);
        }

        this.setLength(this.getLength() + 1);
        return true;
    }

    /**
     * Add the contents of the Array to the Container.
     *
     * @param elements Array of Elements to add.
     * @return boolean indicating if successfully added.
     */
    protected boolean add(final E[] elements) {
        for (final E element : elements) {
            this.add(element);
        }
        return true;
    }

    @Override
    public void clear() {
        Node current = this.getHead();

        while (current != null) {
            final Node next = current.getNext();
            current.clear();
            current = next;
        }

        this.setHead(null);
        this.setTail(null);
        this.setLength(0);
    }

    @Override
    public boolean contains(final E element) {
        Node current = this.getHead();

        while (current != null) {
            if (current.getValue() == element) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * @return the head
     */
    protected Node getHead() {
        return this.head;
    }

    /**
     * @param head the head to set
     */
    protected void setHead(final Node head) {
        this.head = head;
    }

    /**
     * @return the length
     */
    protected int getLength() {
        return this.length;
    }

    /**
     * @param length the length to set
     */
    protected void setLength(final int length) {
        this.length = length;
    }

    /**
     * @return the tail
     */
    protected Node getTail() {
        return this.tail;
    }

    /**
     * @param tail the tail to set
     */
    protected void setTail(final Node tail) {
        this.tail = tail;
    }

    @Override
    public boolean isEmpty() {
        return (this.getLength() > 0);
    }

    /**
     * Remove the value from the container.
     *
     * @param element Element to remove
     * @return boolean True if the list contains the value
     */
    protected boolean remove(final E element) {
        boolean removed = false;
        Node current = null;
        if (this.getLength() == 0) {
            return false;
        } else if ((this.getHead().getValue() == element) && (this.getTail().getValue() == element)) {
            this.setHead(null);
            this.setTail(null);
            removed = true;
        } else if (this.getHead().getValue() == element) {
            current = this.getHead();
            this.setHead(current.getNext());
            current.setNext(null);
            if (this.getHead() != null) {
                this.getHead().setPrevious(null);
            }
            removed = true;
        } else if (this.getTail().getValue() == element) {
            current = this.getTail();
            this.setTail(current.getPrevious());
            current.setPrevious(null);
            if (this.getTail() != null) {
                this.getTail().setNext(null);
            }
            removed = true;
        } else {
            current = this.getHead();
            while (current != null) {
                if (current.getValue() == element) {
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    removed = true;
                    break;
                }
                final Node node = current.getNext();
                current.clear();
                current = node;
            }
        }
        if (removed) {
            this.setLength(this.getLength() - 1);
        }
        return removed;
    }

    @Override
    public int size() {
        return this.getLength();
    }

    @Override
    public E[] toArray(final E[] array) {
        if (array == null) {
            throw new NullPointerException();
        } else if (array.length != this.getLength()) {
            throw new ArrayStoreException("Length of array should be: " + this.getLength());
        } else {
            int i = 0;
            Node current = this.getHead();

            while (current != null) {
                array[i] = current.getValue();
                current = current.getNext();
                i++;
            }
        }
        return array;
    }

    /**
     * @author jmiller Node class to make linked list.
     */
    protected class Node implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = -6957226886938944903L;
        /**
         * Value of this node
         */
        E value = null;
        /**
         * Pointer to next item in Linked List
         */
        Node next = null;
        /**
         * Pointer to previous item in the Linked List
         */
        Node previous = null;

        /**
         * @param element Value for this node
         */
        public Node(final E element) {
            super();
            this.value = element;
        }

        /**
         * @param value    Value to associate to this node
         * @param next     The next node in the list
         * @param previous The Previous Node in the list.
         */
        public Node(final E value, final Node next, final Node previous) {
            super();
            this.value = value;
            this.next = next;
            this.previous = previous;
        }

        /**
         * Set this node to null
         *
         * @return Value of this node
         */
        public E clear() {
            final E value = this.value;
            this.setNext(null);
            this.setPrevious(null);
            this.setValue(null);

            return value;
        }

        /**
         * @return the next
         */
        public Node getNext() {
            return this.next;
        }

        /**
         * @param next the next to set
         */
        public void setNext(final Node next) {
            this.next = next;
        }

        /**
         * @return the previous
         */
        public Node getPrevious() {
            return this.previous;
        }

        /**
         * @param previous the previous to set
         */
        public void setPrevious(final Node previous) {
            this.previous = previous;
        }

        /**
         * @return the value
         */
        public E getValue() {
            return this.value;
        }

        /**
         * @param value the value to set
         */
        public void setValue(final E value) {
            this.value = value;
        }
    }

}
