package bgu.spl.a2.sim.actions;

import bgu.spl.a2.A;
import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Vector;

import static org.junit.Assert.*;

public class CheckAdministrativeObligationsActionTest {
    static Vector<Action> actions = new Vector<>();
    static Vector<PrivateState> actionsPrivateStates = new Vector<>();
    static Vector<String> actionsActorIds = new Vector<>();
    static Vector<Action<Boolean>> phasesActions = new Vector<>();
    static Vector<PrivateState> phasesPrivateStates = new Vector<>();
    static Vector<String> phasesActorsIds = new Vector<>();

    @Test(timeout=10000)
    public void testAction() {
        Vector<Computer> computers = new Vector<>();

        for(int i = 1;i < 10;i++){
            Computer comp = new Computer("" + i);
            try {
                Field field = Computer.class.getDeclaredField("successSig");
                field.setAccessible(true);
                field.set(comp, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Field field = Computer.class.getDeclaredField("failSig");
                field.setAccessible(true);
                field.set(comp, -i);
            } catch (Exception e) {
                e.printStackTrace();
            }

            computers.add(comp);
        }

        Warehouse warehouse = new Warehouse(computers);

        createCourse("Dep1","A",10,new String[] {});
        createCourse("Dep1","B",10,new String[] {});
        createCourse("Dep2","C",10,new String[] {});
        createCourse("Dep2","D",10,new String[] {});

        createStudent("Dep1","1");
        createStudent("Dep1","2");
        createStudent("Dep1","3");
        createStudent("Dep1","4");
        createStudent("Dep1","5");
        createStudent("Dep1","6");
        createStudent("Dep1","7");
        createStudent("Dep1","8");
        createStudent("Dep1","9");
        createStudent("Dep1","10");


        createStudent("Dep2","1");
        createStudent("Dep2","2");
        createStudent("Dep2","3");
        createStudent("Dep2","4");
        createStudent("Dep2","5");
        createStudent("Dep2","6");
        createStudent("Dep2","7");
        createStudent("Dep2","8");
        createStudent("Dep2","9");
        createStudent("Dep2","10");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createParticipate("1","A","90");
        createParticipate("1","B","-");
        createParticipate("1","C","60");
        createParticipate("1","D","60");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createParticipate("2","A","90");
        createParticipate("2","C","60");
        createParticipate("2","D","60");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createParticipate("3","A","90");
        createParticipate("3","B","20");
        createParticipate("3","C","60");
        createParticipate("3","D","55");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createParticipate("4","A","90");
        createParticipate("4","B","70");
        createParticipate("4","C","60");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createParticipate("5","A","90");
        createParticipate("5","B","70");
        createParticipate("5","C","60");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createParticipate("6","A","90");
        createParticipate("6","B","23");
        createParticipate("6","C","60");

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        createAction("Dep1",new String[] {"1"},"1",new String[] {"A","B","C","D"},warehouse);

        createAction("Dep1",new String[] {"2"},"1",new String[] {"A","B"},warehouse);

        createAction("Dep2",new String[] {"3"},"1",new String[] {"A"},warehouse);

        createAction("Dep2",new String[] {"4"},"1",new String[] {"A","B","C","D"},warehouse);

        createAction("Dep1",new String[] {"6","5"},"5",new String[] {"A","B","C"},warehouse);

        A.createPhase(actions,actionsPrivateStates,actionsActorIds,phasesActions,phasesPrivateStates,phasesActorsIds);

        ActorThreadPool pool = A.initiateActionTest(phasesActions,phasesPrivateStates,phasesActorsIds);

        assertEquals(-1,((StudentPrivateState)pool.getActors().get("1")).getSignature());
        assertEquals(-1,((StudentPrivateState)pool.getActors().get("2")).getSignature());
        assertEquals(1,((StudentPrivateState)pool.getActors().get("3")).getSignature());
        assertEquals(-1,((StudentPrivateState)pool.getActors().get("4")).getSignature());
        assertEquals(5,((StudentPrivateState)pool.getActors().get("5")).getSignature());
        assertEquals(-5,((StudentPrivateState)pool.getActors().get("6")).getSignature());
    }

    public static void createAction(String department,String[] students,String computer,String[] conditions,
                                    Warehouse warehouse){
        CheckAdministrativeObligationsAction check = new CheckAdministrativeObligationsAction(
                "Adding",students,computer,conditions,warehouse);

        actions.add(check);
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