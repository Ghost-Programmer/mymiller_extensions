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
