/**
 *
 */
package name.mymiller.pipelines.switches;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Predicate;

/**
 * @author jmiller
 *
 */
public abstract class AbstractPropertyChangeListenerSwitch<T> implements Predicate<T>, PropertyChangeListener {

    private String propertyName;

    private Object newValue;
    private Object source;
    private Object oldValue;

    /**
     * @param propertyName
     * @param newValue
     * @param source
     * @param oldValue
     */
    public AbstractPropertyChangeListenerSwitch(String propertyName, Object newValue, Object source, Object oldValue) {
        super();
        this.propertyName = propertyName;
        this.newValue = newValue;
        this.source = source;
        this.oldValue = oldValue;
    }

    /**
     * @return the newValue
     */
    protected synchronized Object getNewValue() {
        return this.newValue;
    }

    /**
     * @return the oldValue
     */
    protected synchronized Object getOldValue() {
        return this.oldValue;
    }

    /**
     * @return the propertyName
     */
    protected synchronized String getPropertyName() {
        return this.propertyName;
    }

    /**
     * @return the source
     */
    protected synchronized Object getSource() {
        return this.source;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
     * PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.propertyName = evt.getPropertyName();
        this.newValue = evt.getNewValue();
        this.source = evt.getSource();
        this.oldValue = evt.getOldValue();
    }

}
