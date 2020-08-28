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
package name.mymiller.utils.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * First In First Out Mutex, used when order of threads is required.
 *
 * @author jmiller
 */
class Mutex implements LockInterface {
    /**
     * Indicates if the mutex is currently locked
     */
    private final AtomicBoolean locked = new AtomicBoolean(false);
    /**
     * Queue of pending threads
     */
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

    /*
     * (non-Javadoc)
     *
     * @see name.mymiller.utils.concurrent.LockInterface#lock()
     */
    @Override
    public void lock() {
        boolean wasInterrupted = false;
        final Thread current = Thread.currentThread();
        this.waiters.add(current);

        // Block while not first in queue or cannot acquire lock
        while ((this.waiters.peek() != current) || !this.locked.compareAndSet(false, true)) {
            LockSupport.park(this);
            if (Thread.interrupted()) {
                wasInterrupted = true;
            }
        }

        this.waiters.remove();
        if (wasInterrupted) {
            current.interrupt();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see name.mymiller.utils.concurrent.LockInterface#unlock()
     */
    @Override
    public void unlock() {
        this.locked.set(false);
        LockSupport.unpark(this.waiters.peek());
    }
}
