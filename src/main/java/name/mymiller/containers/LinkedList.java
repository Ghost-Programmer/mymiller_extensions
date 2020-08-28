/*
  Copyright 2018 MyMiller Consulting LLC.
  <p>
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License.  You may obtain a copy
  of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
  License for the specific language governing permissions and limitations under
  the License.
 */
/*

 */
package name.mymiller.containers;

import java.io.Serializable;

/**
 * @author jmiller
 * @param <E> Element Type for this list
 *
 */
public class LinkedList<E> extends AbstractList<E> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7112067932902642741L;

    /**
     * Insert an element at the head of the list.
     *
     * @param value Value to insert
     * @return boolean indicating success
     */
    public boolean insertHead(final E value) {
        if (this.size() == 0) {
            this.add(value);
        } else {
            final Node node = new Node(value, this.getHead(), null);
            this.getHead().setPrevious(node);
            this.setHead(node);
            this.setLength(this.getLength() + 1);
        }
        return true;
    }

    /**
     * Insert an element at the tail of the list
     *
     * @param value Value to insert
     * @return boolean indicating success
     */
    public boolean insertTail(final E value) {
        if (this.size() == 0) {
            this.add(value);
        } else {
            final Node node = new Node(value, null, this.getTail());
            this.getTail().setNext(node);
            this.setTail(node);
            this.setLength(this.getLength() + 1);
        }

        return true;
    }

    /**
     *
     * @return value at the head of the list
     */
    public E removeHead() {
        if (this.getHead() == null) {
            return null;
        }

        final Node node = this.getHead();
        this.remove(node.getValue());

        return node.getValue();
    }

    /**
     *
     * @return value at the tail of the list.
     */
    public E removeTail() {
        if (this.getTail() == null) {
            return null;
        }
        final Node node = this.getTail();
        this.remove(node.getValue());

        return node.getValue();
    }
}
