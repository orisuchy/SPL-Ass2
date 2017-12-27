package bgu.spl.a2.sim.actions;

import bgu.spl.a2.A;
import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Vector;

import static org.junit.Assert.*;

public class OpeningNewPlacesInCourseActionTest {
    static Vector<Action> actions = new Vector<>();
    static Vector<PrivateState> actionsPrivateStates = new Vector<>();
    static Vector<String> actionsActorIds = new Vector<>();
    static Vector<Action<Boolean>> phasesActions = new Vector<>();
    static Vector<PrivateState> phasesPrivateStates = new Vector<>();
    static Vector<String> phasesActorsIds = new Vector<>();

    @Test(timeout=10000)
    public void testAction() {
        createAction("Dep1","Cou1",20,new String[] {});
        createAction("Dep1","Cou2",100,new String[] {"A","B"});
        createAction("Dep1","Cou3",10,new String[] {"D","G","J"});

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createAction("Cou1",20);
        createAction("Cou1",10);
        createAction("Cou1",40);
        createAction("Cou2",15);
        createAction("Cou3",10);

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        ActorThreadPool pool = A.initiateActionTest(phasesActions,phasesPrivateStates,phasesActorsIds);

        assertEquals(90,(long)((CoursePrivateState)pool.getActors().get("Cou1")).getAvailableSpots());
        assertEquals(115,(long)((CoursePrivateState)pool.getActors().get("Cou2")).getAvailableSpots());
        assertEquals(20,(long)((CoursePrivateState)pool.getActors().get("Cou3")).getAvailableSpots());
    }

    public static void createAction(String courseName,int numOfSpaces){
        OpeningNewPlacesInCourseAction course = new OpeningNewPlacesInCourseAction(
                "Adding",numOfSpaces);

        actions.add(course);
        actionsPrivateStates.add(new CoursePrivateState());
        actionsActorIds.add(courseName);
    }

    public static void createAction(String department, String courseName, int space,String[] prerequisites){
        OpenNewCourseAction course = new OpenNewCourseAction(
                "Adding",courseName,space,prerequisites);

        actions.add(course);
        actionsPrivateStates.add(new DepartmentPrivateState());
        actionsActorIds.add(department);
    }
}