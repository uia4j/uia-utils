package uia.utils.states;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * State.
 *
 * @author Kyle K. Lin
 *
 * @param <T>
 */
public class State<T> {

    private final String name;

    private final TreeMap<String, EventExecutor<T>> executors;

    private final ArrayList<StateListener> inListeners;

    private final ArrayList<StateListener> outListeners;

    /**
     * Constructor.
     *
     * @param name State name.
     */
    public State(String name) {
        this.name = name;
        this.executors = new TreeMap<String, EventExecutor<T>>();
        this.inListeners = new ArrayList<StateListener>();
        this.outListeners = new ArrayList<StateListener>();
    }

    public boolean containsEvent(String eventName) {
        return this.executors.containsKey(eventName);
    }

    public void addInListener(StateListener listener) {
        this.inListeners.add(listener);
    }

    public void addOutListener(StateListener listener) {
        this.outListeners.add(listener);
    }

    public String getName() {
        return this.name;
    }

    public State<T> addEvent(String eventName, EventExecutor<T> executor) {
        if (eventName == null || executor == null) {
            return this;
        }
        this.executors.put(eventName, executor);
        return this;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String toStringEx() {
        return this.name + ", events:{" + String.join(",", this.executors.keySet().toArray(new String[0])) + "}";
    }

    String execute(T controller, String eventName, Object args) {
        EventExecutor<T> executor = this.executors.get(eventName);
        return executor.run(controller, args);
    }

    void raiseIn(String eventName, String prevState) {
        for (StateListener l : this.inListeners) {
            try {
                l.run(new StateEventArgs(eventName));
            }
            catch (Exception ex) {

            }
        }
    }

    void raiseOut(String eventName, String prevState) {
        for (StateListener l : this.outListeners) {
            try {
                l.run(new StateEventArgs(eventName));
            }
            catch (Exception ex) {

            }
        }
    }
}
