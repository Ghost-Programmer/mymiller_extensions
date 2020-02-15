/**
 * Copyright 2018 MyMiller Consulting LLC.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/**
 *
 */
package name.mymiller.utils.concurrent;

import java.lang.reflect.InvocationTargetException;
import java.util.WeakHashMap;

/**
 * Manager of locks, stores them by name assists in creating them for
 * syncrhonization purposes. Locks can be disposed if no reference is maintained
 * outside of the Lock Manager.
 *
 * @author jmiller
 *
 */

public class LockManager {
    /**
     * WeakHashMap to track the named Locks
     */
    private static final WeakHashMap<String, LockInterface> lockTable = new WeakHashMap<>();

    /**
     * Retrieve a named Lock. The Lock will be of type SyncLock unless created
     * elsewhere as different type.
     *
     * @param lockName String containing the name of the lock
     * @return LockInterface for the object with the given name.
     * @throws InstantiationException Failed to create the lock.
     * @throws IllegalAccessException Illedgal Access to Create the Lock.
     */
    public LockInterface getLock(String lockName)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return this.getLock(lockName, SyncLock.class);
    }

    /**
     * Retrieve a named Lock. The Lock will be of type SyncLock unless created
     * elsewhere as different type.
     *
     * @param lockName String containing the name of the lock
     * @param lockType Class for the type of lock that should be created if lock
     *                 does not exist.
     * @return LockInterface for the object with the given name.
     * @throws InstantiationException Failed to create the lock.
     * @throws IllegalAccessException Illedgal Access to Create the Lock.
     */
    public LockInterface getLock(String lockName, Class<?> lockType)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        LockInterface lockObject = null;

        synchronized (LockManager.lockTable) {
            lockObject = LockManager.lockTable.get(lockName);
            if (lockObject == null) {
                lockObject = (LockInterface) lockType.getDeclaredConstructor().newInstance();
                LockManager.lockTable.put(lockName, lockObject);
            }
        }
        return lockObject;
    }
}
