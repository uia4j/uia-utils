package uia.utils.states;

/**
 * Event executor.
 *
 * @author Kyle K. Lin
 *
 * @param <C> Controller.
 */
public interface EventExecutor<C> {

    /**
     * Run this event.
     * @param controller The controller.
     * @param args Arguments.
     * @return State name changed to.
     */
    public String run(C controller, Object args);
}
