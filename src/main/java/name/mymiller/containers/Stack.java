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
 * @param <E> Element type for this stack
 *
 */
public class Stack<E> extends LinkedList<E> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7906140439014535248L;

    /**
     * Return the top value in the stack without removing it
     *
     * @return Top Value in the stack.
     */
    public E peek() {
        if (this.getHead() == null) {
            return null;
        }
        return this.getHead().getValue();
    }

    /**
     *
     * @return Top Element in the stack
     */
    public E pop() {
        return this.removeHead();
    }

    /**
     * Push the value onto the stack
     *
     * @param value Value to push onto the stack
     */
    public void push(final E value) {
        this.insertHead(value);
    }
}
