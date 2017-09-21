package uia.utils.states;

import org.junit.Assert;
import org.junit.Test;

public class StateMachineTest {

    private static String IDLE = "IDLE";

    private static String PRE_PROCESS = "PRE_PROCESS";

    private static String PROCESSING = "PROCESSING";

    private static String POST_PROCESS = "POST_PROCESS";

    private static String NEXT = "NEXT";

    private static String RUN_HOLD = "RUN_HOLD";

    private int event;

    @Test
    public void testSimple() {
        StateMachine<Object> machine = new StateMachine<Object>("FOUP");
        // IDLE
        machine.register(IDLE)
                .addEvent("validateLot", StateMachineTest.this::validateLot)
                .addEvent("moveIn", StateMachineTest.this::moveIn);

        // PRE_PROCESS
        machine.register(PRE_PROCESS)
                .addEvent("trackIn", StateMachineTest.this::trackIn)
                .addEvent("down", StateMachineTest.this::runHold)
                .addEvent("trackOut", StateMachineTest.this::trackIn);

        // PROCESSING
        machine.register(PROCESSING)
                .addEvent("trackIn", StateMachineTest.this::trackIn)
                .addEvent("down", StateMachineTest.this::runHold)
                .addEvent("trackOut", StateMachineTest.this::trackOut);

        // POST_PROCESS
        machine.register(POST_PROCESS)
                .addEvent("down", StateMachineTest.this::runHold)
                .addEvent("moveOut", StateMachineTest.this::moveOut);

        // NEXT
        machine.register(NEXT)
                .addEvent("ready", StateMachineTest.this::ready);

        // RUN_HOLD
        machine.register(RUN_HOLD);

        machine.changeState(IDLE);
        Assert.assertEquals(IDLE, machine.getCurrState().getName());

        machine.run(null, "validateLot", null);
        Assert.assertEquals(IDLE, machine.getCurrState().getName());

        machine.run(null, "trackIn", null); // NO CHANGED
        Assert.assertEquals(IDLE, machine.getCurrState().getName());

        machine.run(null, "moveIn", null);
        Assert.assertEquals(PRE_PROCESS, machine.getCurrState().getName());

        machine.run(null, "trackIn", null);
        Assert.assertEquals(PROCESSING, machine.getCurrState().getName());

        machine.run(null, "trackOut", null);
        Assert.assertEquals(POST_PROCESS, machine.getCurrState().getName());

        machine.run(null, "moveOut", null);
        Assert.assertEquals(NEXT, machine.getCurrState().getName());

        machine.run(null, "ready", null);
        Assert.assertEquals(IDLE, machine.getCurrState().getName());
    }

    @Test
    public void testEventListener() {
        StateMachine<Object> machine = new StateMachine<Object>("FOUP");
        // IDLE
        machine.register(IDLE)
                .addEvent("validateLot", StateMachineTest.this::validateLot)
                .addEvent("moveIn", StateMachineTest.this::moveIn);

        // PRE_PROCESS
        machine.register(PRE_PROCESS)
                .addEvent("trackIn", StateMachineTest.this::trackIn)
                .addEvent("down", StateMachineTest.this::runHold)
                .addEvent("trackOut", StateMachineTest.this::trackIn);

        // PROCESSING
        machine.register(PROCESSING)
                .addEvent("trackIn", StateMachineTest.this::trackIn)
                .addEvent("down", StateMachineTest.this::runHold)
                .addEvent("trackOut", StateMachineTest.this::trackOut);

        // POST_PROCESS
        machine.register(POST_PROCESS)
                .addEvent("down", StateMachineTest.this::runHold)
                .addEvent("moveOut", StateMachineTest.this::moveOut);

        // NEXT
        machine.register(NEXT)
                .addEvent("ready", StateMachineTest.this::ready);

        // RUN_HOLD
        machine.register(RUN_HOLD);

        // EVENT LISTENERS
        machine.addChangeListener(IDLE, PRE_PROCESS, a -> Assert.assertEquals(2, this.event));
        machine.addEventListener("trackIn", a -> Assert.assertEquals(3, this.event));
        machine.addEventListener("moveOut", a -> Assert.assertEquals(5, this.event));

        machine.changeState(IDLE);

        this.event = 1;
        machine.run(null, "validateLot", null);

        this.event = 2;
        machine.run(null, "moveIn", null);

        this.event = 3;
        machine.run(null, "trackIn", null);

        this.event = 4;
        machine.run(null, "trackOut", null);

        this.event = 5;
        machine.run(null, "moveOut", null);

        this.event = 6;
        machine.run(null, "ready", null);
    }

    private String validateLot(Object controller, Object args) {
        return null;
    }

    private String moveIn(Object controller, Object args) {
        return PRE_PROCESS;
    }

    private String moveOut(Object controller, Object args) {
        return NEXT;
    }

    private String trackIn(Object controller, Object args) {
        return PROCESSING;
    }

    private String trackOut(Object controller, Object args) {
        return POST_PROCESS;
    }

    private String runHold(Object controller, Object args) {
        return RUN_HOLD;
    }

    private String ready(Object controller, Object args) {
        return IDLE;
    }
}