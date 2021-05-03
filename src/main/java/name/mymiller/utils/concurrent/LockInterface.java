package name.mymiller.utils.concurrent;

/**
 * Interface for Concurrent Locking mechanisms.
 *
 * @author jmiller
 */
public interface LockInterface {

    /**
     * Acquire the lock
     */
    void lock();

    /**
     * Release the lock
     */
    void unlock();

}
