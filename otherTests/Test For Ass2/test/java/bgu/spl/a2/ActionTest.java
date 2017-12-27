package bgu.spl.a2;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

public class ActionTest {
    boolean actionStartExecuted = false;
    boolean actionContinuationExecuted = false;
    Vector<Action<Boolean>> actionsToWaitFor = new Vector<>();

    @Test(timeout=10000)
    public void testImportantOperation() throws NoSuchFieldException, IllegalAccessException {
        // Creating test data
        Action<Boolean> actionToWaitFor1 = new Action<Boolean>() {
            @Override
            protected void start() {
                complete(true);
            }
        };

        // Creating test data
        Action<Boolean> actionToWaitFor2 = new Action<Boolean>() {
            @Override
            protected void start() {
                complete(true);
            }
        };

        actionsToWaitFor.add(actionToWaitFor1);
        actionsToWaitFor.add(actionToWaitFor2);

        Action<Boolean> action = new Action<Boolean>() {
            @Override
            protected void start() {
                actionStartExecuted = true;

                sendMessage(actionToWaitFor1, "An Actor 2", new PrivateState() {});

                then(actionsToWaitFor,() -> {
                    actionContinuationExecuted = true;
                });
            }
        };

        ActorThreadPool pool = new ActorThreadPool(4);

        // Initiating test

        assertEquals(false,actionStartExecuted);
        assertEquals(false,actionContinuationExecuted);

        action.handle(pool, "An Actor 1", new PrivateState() {});

        assertEquals(true,actionStartExecuted);
        assertEquals(false,actionContinuationExecuted);

        action.handle(pool, "An Actor 1", new PrivateState() {});

        assertEquals(true,actionStartExecuted);
        assertEquals(true,actionContinuationExecuted);

        Field field = ActorThreadPool.class.getDeclaredField("actorsQueues");
        field.setAccessible(true);
        HashMap<String,LinkedList<Action>> actors = (HashMap<String,LinkedList<Action>>)field.get(pool);

        assertEquals(null,actors.get("An Actor 1"));

        actionToWaitFor1.handle(new ActorThreadPool(2),"", new PrivateState() {});
        actionToWaitFor2.handle(new ActorThreadPool(2),"", new PrivateState() {});

        assertEquals(true,actors.get("An Actor 1").contains(action));

        actors.get("An Actor 1").poll();

        assertEquals(false,actors.get("An Actor 1").contains(action));

        assertEquals(true,actors.containsKey("An Actor 1"));
        assertEquals(true,actors.containsKey("An Actor 2"));
        assertEquals(true,actors.get("An Actor 2").contains(actionToWaitFor1));
        assertEquals(false,actors.get("An Actor 1").contains(actionToWaitFor1));
    }

    @Test(timeout=10000)
    public void testGeneralMethods() throws NoSuchFieldException, IllegalAccessException {
        // Creating test data
        Action<Boolean> action = new Action<Boolean>() {
            @Override
            protected void start() {
                complete(true);
                }
        };

        action.setActionName("Hello");

        ActorThreadPool pool = new ActorThreadPool(4);

        action.handle(pool, "An Actor 1", new PrivateState() {});

        // Initiating test
        assertEquals("Hello",action.getActionName());
        assertEquals(true,action.getResult().get());
    }
}