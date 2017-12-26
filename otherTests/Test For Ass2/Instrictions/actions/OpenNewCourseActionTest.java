package bgu.spl.a2.sim.actions;

import bgu.spl.a2.A;
import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import org.junit.Test;

import java.util.HashMap;
import java.util.Vector;

import static org.junit.Assert.*;

public class OpenNewCourseActionTest {
    static Vector<Action<Boolean>> actions = new Vector<>();
    static Vector<PrivateState> actionsPrivateStates = new Vector<>();
    static Vector<String> actionsActorIds = new Vector<>();

    @Test(timeout=10000)
    public void testAction() {
        createAction("Dep1","Cou1",20,new String[] {});
        createAction("Dep1","Cou2",100,new String[] {"A","B"});
        createAction("Dep1","Cou3",0,new String[] {"C"});
        createAction("Dep1","Cou4",10,new String[] {"D","G","J"});

        createAction("Dep2","Cou5",15,new String[] {"1"});
        createAction("Dep3","Cou6",18,new String[] {"2","3"});
        createAction("Dep4","Cou7",15,new String[] {"1"});

        ActorThreadPool pool = A.initiateActionTest(actions,actionsPrivateStates,actionsActorIds);

        assertEquals(true,pool.getActors().containsKey("Dep1"));
        assertEquals(true,pool.getActors().containsKey("Dep2"));
        assertEquals(true,pool.getActors().containsKey("Dep3"));
        assertEquals(true,pool.getActors().containsKey("Dep4"));

        assertEquals(true,pool.getActors().containsKey("Cou1"));
        assertEquals(true,pool.getActors().containsKey("Cou2"));
        assertEquals(true,pool.getActors().containsKey("Cou3"));
        assertEquals(true,pool.getActors().containsKey("Cou4"));
        assertEquals(true,pool.getActors().containsKey("Cou5"));
        assertEquals(true,pool.getActors().containsKey("Cou6"));

        assertEquals(A.createVectorFromArray(new String[] {}),((DepartmentPrivateState)pool.getActors().get("Dep1")).getStudentList());
        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {"Cou1","Cou2","Cou3","Cou4"}),
                ((DepartmentPrivateState)pool.getActors().get(
                "Dep1")).getCourseList()));

        assertEquals(A.createVectorFromArray(new String[] {}),((DepartmentPrivateState)pool.getActors().get("Dep2")).getStudentList());
        assertEquals(A.createVectorFromArray(new String[] {"Cou5"}),((DepartmentPrivateState)pool.getActors().get("Dep2")).getCourseList());

        assertEquals(A.createVectorFromArray(new String[] {}),((DepartmentPrivateState)pool.getActors().get("Dep3")).getStudentList());
        assertEquals(A.createVectorFromArray(new String[] {"Cou6"}),((DepartmentPrivateState)pool.getActors().get("Dep3")).getCourseList());

        assertEquals(A.createVectorFromArray(new String[] {}),((DepartmentPrivateState)pool.getActors().get("Dep4")).getStudentList());
        assertEquals(A.createVectorFromArray(new String[] {"Cou7"}),((DepartmentPrivateState)pool.getActors().get("Dep4")).getCourseList());

        assertEquals(A.createVectorFromArray(new String[] {}),((CoursePrivateState)pool.getActors().get("Cou1")).getPrequisites());
        assertEquals(A.createVectorFromArray(new String[] {}),((CoursePrivateState)pool.getActors().get("Cou1")).getRegStudents());
        assertEquals(20,(long)((CoursePrivateState)pool.getActors().get("Cou1")).getAvailableSpots());

        assertEquals(A.createVectorFromArray(new String[] {"A","B"}),((CoursePrivateState)pool.getActors().get("Cou2")).getPrequisites());
        assertEquals(A.createVectorFromArray(new String[] {}),((CoursePrivateState)pool.getActors().get("Cou2")).getRegStudents());
        assertEquals(100,(long)((CoursePrivateState)pool.getActors().get("Cou2")).getAvailableSpots());

        assertEquals(A.createVectorFromArray(new String[] {"C"}),((CoursePrivateState)pool.getActors().get("Cou3")).getPrequisites());
        assertEquals(A.createVectorFromArray(new String[] {}),((CoursePrivateState)pool.getActors().get("Cou3")).getRegStudents());
        assertEquals(0,(long)((CoursePrivateState)pool.getActors().get("Cou3")).getAvailableSpots());

        assertEquals(A.createVectorFromArray(new String[] {"D","G","J"}),((CoursePrivateState)pool.getActors().get("Cou4")).getPrequisites());
        assertEquals(A.createVectorFromArray(new String[] {}),((CoursePrivateState)pool.getActors().get("Cou4")).getRegStudents());
        assertEquals(10,(long)((CoursePrivateState)pool.getActors().get("Cou4")).getAvailableSpots());

        assertEquals(A.createVectorFromArray(new String[] {"1"}),((CoursePrivateState)pool.getActors().get("Cou5")).getPrequisites());
        assertEquals(A.createVectorFromArray(new String[] {}),((CoursePrivateState)pool.getActors().get("Cou5")).getRegStudents());
        assertEquals(15,(long)((CoursePrivateState)pool.getActors().get("Cou5")).getAvailableSpots());

        assertEquals(A.createVectorFromArray(new String[] {"2","3"}),((CoursePrivateState)pool.getActors().get("Cou6")).getPrequisites());
        assertEquals(A.createVectorFromArray(new String[] {}),((CoursePrivateState)pool.getActors().get("Cou6")).getRegStudents());
        assertEquals(18,(long)((CoursePrivateState)pool.getActors().get("Cou6")).getAvailableSpots());
    }

    public static void createAction(String department, String courseName, int space,String[] prerequisites){
        OpenNewCourseAction course = new OpenNewCourseAction(
                "Adding",courseName,space,prerequisites);

        actions.add(course);
        actionsPrivateStates.add(new DepartmentPrivateState());
        actionsActorIds.add(department);
    }
}