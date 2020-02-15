package name.mymiller.utils.pipelines.switches;

import java.util.function.Predicate;

/**
 * @author jmiller Class used to remote enable/disable the predicate for use in
 * pipeline switches.
 */
public class RemoteSwitch implements Predicate<Object> {

    private boolean enabled = false;

    /**
     * Default Constructor specifying the switch is disabled.
     */
    public RemoteSwitch() {
        this(false);
    }

    /**
     * Constructor to specify initial setting for the switch
     *
     * @param enabled boolean indicating if the swithc is enabled or disabled.
     */
    public RemoteSwitch(boolean enabled) {
        super();
        this.enabled = enabled;
    }

    /**
     * @return the enabled
     */
    public synchronized boolean isEnabled() {
        return this.enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public synchronized void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean test(Object t) {
        return this.isEnabled();
    }

}
