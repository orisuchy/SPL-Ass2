package bgu.spl.a2.sim.actions;

import bgu.spl.a2.A;
import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class RegisterWithPreferencesActionTest {
    static Vector<Action> actions = new Vector<>();
    static Vector<PrivateState> actionsPrivateStates = new Vector<>();
    static Vector<String> actionsActorIds = new Vector<>();
    static Vector<Action<Boolean>> phasesActions = new Vector<>();
    static Vector<PrivateState> phasesPrivateStates = new Vector<>();
    static Vector<String> phasesActorsIds = new Vector<>();

    @Test(timeout=10000)
    public void testAction() {
        createCourse("Dep1","A",10,new String[] {});
        createCourse("Dep1","B",5,new String[] {"A"});
        createCourse("Dep2","C",2,new String[] {"A","B"});
        createCourse("Dep2","D",2,new String[] {"A","B","C"});

        createStudent("Dep1","1");
        createStudent("Dep1","2");
        createStudent("Dep1","3");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createAction("1",new String[] {"A"},new String[]{"22"});
        createAction("2",new String[] {"A","B"},new String[]{"11","20"});

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createAction("1",new String[] {"D","C","B"},new String[]{"11","20","120"});
        createAction("3",new String[] {"D","C","B"},new String[]{"44","24","44"});
        createAction("2",new String[] {"D","C"},new String[]{"42","66","44"});

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        ActorThreadPool pool = A.initiateActionTest(phasesActions,phasesPrivateStates,phasesActorsIds);

        assertEquals(A.createHash(new String[] {"A","B"},new Integer[] {22,120}),
                ((StudentPrivateState)pool.getActors().get("1")).getGrades());

        assertEquals(A.createHash(new String[] {"A"},new Integer[] {11}),
                ((StudentPrivateState)pool.getActors().get("2")).getGrades());

        assertEquals(A.createHash(new String[] {},new Integer[] {}),
                ((StudentPrivateState)pool.getActors().get("3")).getGrades());

        assertEquals(8,(long)
                ((CoursePrivateState)pool.getActors().get("A")).getAvailableSpots());
        assertEquals(2,(long)
                ((CoursePrivateState)pool.getActors().get("A")).getRegistered());
        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {"1","2"}),
                ((CoursePrivateState)pool.getActors().get("A")).getRegStudents()));

        assertEquals(4,(long)
                ((CoursePrivateState)pool.getActors().get("B")).getAvailableSpots());
        assertEquals(1,(long)
                ((CoursePrivateState)pool.getActors().get("B")).getRegistered());
        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {"1"}),
                ((CoursePrivateState)pool.getActors().get("B")).getRegStudents()));

        assertEquals(2,(long)
                ((CoursePrivateState)pool.getActors().get("C")).getAvailableSpots());
        assertEquals(0,(long)
                ((CoursePrivateState)pool.getActors().get("C")).getRegistered());
        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {}),
                ((CoursePrivateState)pool.getActors().get("C")).getRegStudents()));

        assertEquals(2,(long)
                ((CoursePrivateState)pool.getActors().get("D")).getAvailableSpots());
        assertEquals(0,(long)
                ((CoursePrivateState)pool.getActors().get("D")).getRegistered());
        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {}),
                ((CoursePrivateState)pool.getActors().get("D")).getRegStudents()));
    }


    public static void createAction(String student,String[] pref,String[] grades){
        RegisterWithPreferencesAction regWithPref = new RegisterWithPreferencesAction(
                "Adding",student,pref,grades);

        actions.add(regWithPref);
        actionsPrivateStates.add(new StudentPrivateState());
        actionsActorIds.add(student);
    }

    public static void createCourse(String department, String courseName, int space,String[] prerequisites){
        OpenNewCourseAction course = new OpenNewCourseAction(
                "Adding",courseName,space,prerequisites);

        actions.add(course);
        actionsPrivateStates.add(new DepartmentPrivateState());
        actionsActorIds.add(department);
    }

    public static void createStudent(String department,String studentId){

        AddStudentAction student = new AddStudentAction(
                "Adding",studentId);

        actions.add(student);
        actionsPrivateStates.add(new DepartmentPrivateState());
        actionsActorIds.add(department);
    }
}