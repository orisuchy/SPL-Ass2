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

public class CloseCourseActionTest {
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
        createStudent("Dep1","4");

        createStudent("Dep2","2");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createParticipate("1","A","90");
        createParticipate("2","A","45");
        createParticipate("3","A","60");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createParticipate("1","B","-");
        createParticipate("3","B","40");


        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createParticipate("1","C","20");
        createParticipate("2","C","70");
        createParticipate("3","C","30");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createParticipate("1","D","10");
        createParticipate("4","D","45");
        createParticipate("2","D","11");
        createParticipate("3","D","30");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createAction("Dep1","A");

        createAction("Dep1","B");

        createAction("Dep2","C");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        ActorThreadPool pool = A.initiateActionTest(phasesActions,phasesPrivateStates,phasesActorsIds);

        assertEquals(A.createHash(new String[] {"D"},new Integer[] {10}),
                ((StudentPrivateState)pool.getActors().get("1")).getGrades());

        assertEquals(A.createHash(new String[] {},new Integer[] {}),
                ((StudentPrivateState)pool.getActors().get("2")).getGrades());

        assertEquals(A.createHash(new String[] {"D"},new Integer[] {30}),
                ((StudentPrivateState)pool.getActors().get("3")).getGrades());

        assertEquals(A.createHash(new String[] {},new Integer[] {}),
                ((StudentPrivateState)pool.getActors().get("4")).getGrades());

        assertEquals(-1,(long)
                ((CoursePrivateState)pool.getActors().get("A")).getAvailableSpots());
        assertEquals(0,(long)
                ((CoursePrivateState)pool.getActors().get("A")).getRegistered());
        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {}),
                ((CoursePrivateState)pool.getActors().get("A")).getRegStudents()));

        assertEquals(-1,(long)
                ((CoursePrivateState)pool.getActors().get("B")).getAvailableSpots());
        assertEquals(0,(long)
                ((CoursePrivateState)pool.getActors().get("B")).getRegistered());
        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {}),
                ((CoursePrivateState)pool.getActors().get("B")).getRegStudents()));

        assertEquals(-1,(long)
                ((CoursePrivateState)pool.getActors().get("C")).getAvailableSpots());
        assertEquals(0,(long)
                ((CoursePrivateState)pool.getActors().get("C")).getRegistered());
        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {}),
                ((CoursePrivateState)pool.getActors().get("C")).getRegStudents()));

        assertEquals(0,(long)
                ((CoursePrivateState)pool.getActors().get("D")).getAvailableSpots());
        assertEquals(2,(long)
                ((CoursePrivateState)pool.getActors().get("D")).getRegistered());
        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {"1","3"}),
                ((CoursePrivateState)pool.getActors().get("D")).getRegStudents()));


        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {}),
                ((DepartmentPrivateState)pool.getActors().get("Dep1")).getCourseList()));
        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {"1","2","3","4"}),
                ((DepartmentPrivateState)pool.getActors().get("Dep1")).getStudentList()));

        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {"D"}),
                ((DepartmentPrivateState)pool.getActors().get("Dep2")).getCourseList()));
        assertTrue(A.containsSame(A.createVectorFromArray(new String[] {"2"}),
                ((DepartmentPrivateState)pool.getActors().get("Dep2")).getStudentList()));
    }

    public static void createAction(String department,String courseName){
        CloseCourseAction close = new CloseCourseAction(
                "Adding",courseName);

        actions.add(close);
        actionsPrivateStates.add(new DepartmentPrivateState());
        actionsActorIds.add(department);
    }

    public static void createParticipate(String student,String courseName,String grade){
        ParticipatingInCourseAction participate = new ParticipatingInCourseAction(
                "Adding",student,courseName,grade);

        actions.add(participate);
        actionsPrivateStates.add(new CoursePrivateState());
        actionsActorIds.add(courseName);
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