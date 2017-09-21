package uia.utils.states;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * State machine.
 *
 * @author Kyle K. Lin
 *
 * @param <C> Controller.
 */
public class StateMachine<C> {

    public enum RunType {
        /**
         * No event.
         */
        NOEVENT,
        /**
         * No changed.
         */
        STATE_NOCHANGE,
        /**
         * Changed.
         */
        STATE_CHANGE
    }

    private final String name;

    private final TreeMap<String, State<C>> states;

    private final TreeMap<String, List<StateListener>> stateChangedListeners;

    private final TreeMap<String, List<StateListener>> eventListeners;

    private State<C> prevState;

    private State<C> currState;

    /**
     * Constructor.
     * @param name State machine name.
     */
    public StateMachine(String name) {
        this.name = name;
        this.states = new TreeMap<String, State<C>>();
        this.stateChangedListeners = new TreeMap<String, List<StateListener>>();
        this.eventListeners = new TreeMap<String, List<StateListener>>();
        this.prevState = new State<C>("NULL");
        this.currState = this.prevState;
    }

    /**
     * Get name.
     * @return Name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get state.
     * @param stateName State name.
     * @return State.
     */
    public State<C> getState(String stateName) {
        return this.states.get(stateName);
    }

    /**
     *
     * @param stateName
     * @return
     */
    public State<C> register(String stateName) {
        State<C> state = this.states.get(stateName);
        if (state == null) {
            state = new State<C>(stateName);
            this.states.put(stateName, state);
        }
        return state;
    }

    /**
     *
     * @return
     */
    public State<C> getPrevState() {
        return this.prevState;
    }

    /**
     *
     * @return
     */
    public State<C> getCurrState() {
        return this.currState;
    }

    /**
     * Add event listener.
     * @param eventName Event name.
     * @param listener The listener.
     */
    public void addEventListener(String eventName, StateListener listener) {
        List<StateListener> listeners = this.eventListeners.get(eventName);
        if (listeners == null) {
            listeners = new ArrayList<StateListener>();
            this.eventListeners.put(eventName, listeners);
        }
        listeners.add(listener);
    }

    /**
     * Add state changed listener.
     * @param fromStateName State changed from.
     * @param toStateName State changed to.
     * @param listener The listener.
     */
    public void addChangeListener(String fromStateName, String toStateName, StateListener listener) {
        String key = genKey(fromStateName, toStateName);
        List<StateListener> listeners = this.stateChangedListeners.get(key);
        if (listeners == null) {
            listeners = new ArrayList<StateListener>();
            this.stateChangedListeners.put(key, listeners);
        }
        listeners.add(listener);
    }

    /**
     * Change to a new state. Listeners will not be notified.
     * @param stateName New state name.
     * @return Changed or not.
     * @throws StateException Raise if state control failed.
     */
    public boolean changeState(String stateName) throws StateException {
        if (stateName == null) {
            return false;
        }

        State<C> temp = this.states.get(stateName);
        if (temp == null) {
            throw new StateException(String.format("%s not found in StateMachin:%s", stateName, this.name));
        }

        this.prevState = this.currState;
        this.currState = temp;

        return true;
    }

    /**
     * Run an event.
     * @param controller The controller name.
     * @param eventName The event name.
     * @param args Arguments.
     * @throws StateException Raise if state control failed.
     */
    public RunType run(C controller, String eventName, Object args) throws StateException {
        if (!this.currState.containsEvent(eventName)) {
            raiseEvent(eventName);
            return RunType.NOEVENT;
        }
        String nextState = this.currState.execute(controller, eventName, args);
        if (changeState(nextState)) {
            this.prevState.raiseOut(eventName, this.prevState.getName());
            this.currState.raiseIn(eventName, this.prevState.getName());
            raiseEvent(eventName);
            raiseStateChanged(eventName, this.prevState.getName(), this.currState.getName());
            return RunType.STATE_CHANGE;
        }
        else {
            raiseEvent(eventName);
            return RunType.STATE_NOCHANGE;
        }
    }

    /**
     * Print state information(for debug only.)
     */
    public void println() {
        System.out.println(String.format("CurrState:%-20s, PrevState:%s",
                this.currState,
                this.prevState));
    }

    void raiseStateChanged(String eventName, String fromStateName, String toStateName) {
        String key = genKey(fromStateName, toStateName);
        List<StateListener> listeners = this.stateChangedListeners.get(key);
        if (listeners == null) {
            return;
        }
        for (StateListener listener : listeners) {
            listener.run(new StateEventArgs(eventName));
        }
    }

    void raiseEvent(String eventName) {
        List<StateListener> listeners = this.eventListeners.get(eventName);
        if (listeners == null) {
            return;
        }
        for (StateListener listener : listeners) {
            listener.run(new StateEventArgs(eventName));
        }
    }

    private String genKey(String fromStateName, String toStateName) {
        return fromStateName + "--" + toStateName;
    }
}
