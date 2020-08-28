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

/**
 * Locking mechanism used for Syncrhonization Calls. Calling Lock/Unlock will
 * result in an being thrown.
 *
 * @author jmiller
 */
public class SyncLock implements LockInterface {
    @Override
    public void lock() {
        throw new SyncLockSynchronizationOnlyException();
    }

    @Override
    public void unlock() {
        throw new SyncLockSynchronizationOnlyException();
    }

}
