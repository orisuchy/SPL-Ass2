package bgu.spl.a2.sim.actions;

import bgu.spl.a2.A;
import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class AddStudentActionTest {
    static Vector<Action<Boolean>> actions = new Vector<>();
    static Vector<PrivateState> actionsPrivateStates = new Vector<>();
    static Vector<String> actionsActorIds = new Vector<>();

    @Test(timeout=10000)
    public void testAction() {
        createAction("A","1");
        createAction("A","2");
        createAction("B","3");
        createAction("B","4");
        createAction("B","5");

        ActorThreadPool pool = A.initiateActionTest(actions,actionsPrivateStates,actionsActorIds);

        assertEquals(true,((DepartmentPrivateState)pool.getActors().get("A")).getStudentList().contains("1"));
        assertEquals(true,((DepartmentPrivateState)pool.getActors().get("A")).getStudentList().contains("2"));
        assertEquals(false,((DepartmentPrivateState)pool.getActors().get("A")).getStudentList().contains("3"));
        assertEquals(false,((DepartmentPrivateState)pool.getActors().get("A")).getStudentList().contains("4"));
        assertEquals(false,((DepartmentPrivateState)pool.getActors().get("A")).getStudentList().contains("5"));

        assertEquals(false,((DepartmentPrivateState)pool.getActors().get("B")).getStudentList().contains("1"));
        assertEquals(false,((DepartmentPrivateState)pool.getActors().get("B")).getStudentList().contains("2"));
        assertEquals(true,((DepartmentPrivateState)pool.getActors().get("B")).getStudentList().contains("3"));
        assertEquals(true,((DepartmentPrivateState)pool.getActors().get("B")).getStudentList().contains("4"));
        assertEquals(true,((DepartmentPrivateState)pool.getActors().get("B")).getStudentList().contains("5"));

        assertEquals(true,pool.getActors().containsKey("1"));
        assertEquals(true,pool.getActors().containsKey("2"));
        assertEquals(true,pool.getActors().containsKey("3"));
        assertEquals(true,pool.getActors().containsKey("4"));
        assertEquals(true,pool.getActors().containsKey("5"));
    }

    public static void createAction(String department,String studentId){

        AddStudentAction student = new AddStudentAction(
                "Adding",studentId);

        actions.add(student);
        actionsPrivateStates.add(new DepartmentPrivateState());
        actionsActorIds.add(department);
    }
}