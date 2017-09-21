package uia.utils.states;

import org.junit.Test;

public class StateMachineTest {

    private static String IDLE = "IDLE";

    private static String PRE_PROCESS = "PRE_PROCESS";

    private static String PROCESSING = "PROCESSING";

    private static String POST_PROCESS = "POST_PROCESS";

    private static String NEXT = "NEXT";

    private static String RUN_HOLD = "RUN_HOLD";

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

        machine.run(null, "validateLot", null);
        machine.println();
        machine.run(null, "moveIn", null);
        machine.println();
        machine.run(null, "trackIn", null);
        machine.println();
        machine.run(null, "trackOut", null);
        machine.println();
        machine.run(null, "moveOut", null);
        machine.println();
        machine.run(null, "ready", null);
        machine.println();
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

        machine.addEventListener("trackIn", a -> System.out.println(">>> trackIn"));

        machine.changeState(IDLE);

        machine.run(null, "validateLot", null);
        machine.println();
        machine.run(null, "moveIn", null);
        machine.println();
        machine.run(null, "trackIn", null);
        machine.println();
        machine.run(null, "trackOut", null);
        machine.println();
        machine.run(null, "moveOut", null);
        machine.println();
        machine.run(null, "ready", null);
        machine.println();
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