package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import org.junit.Test;
import bgu.spl.a2.*;

import java.util.Vector;

import static org.junit.Assert.*;

public class AddCourseToStudentGradeSheetActionTest {
    static Vector<Action<Boolean>> actions = new Vector<>();
    static Vector<PrivateState> actionsPrivateStates = new Vector<>();
    static Vector<String> actionsActorIds = new Vector<>();

    @Test(timeout=10000)
    public void testAction(){
        createAction("A","90",new String[] {});
        createAction("B","20",new String[] {"A"});
        createAction("C","120",new String[] {"A","B"});
        createAction("D","-",new String[] {"A","B","C"});

        createAction("F","120",new String[] {"E"});
        createAction("G","120",new String[] {"A","B","C","F"});

        A.initiateActionTest(actions,actionsPrivateStates,actionsActorIds);

        StudentPrivateState privateState = new StudentPrivateState();
        privateState.addGrade("A",90);
        privateState.addGrade("B",20);
        privateState.addGrade("C",120);
        privateState.addGrade("D",-1);

        assertEquals(privateState.getGrades(),((StudentPrivateState)actionsPrivateStates.get(0)).getGrades());

        for(int i = 0;i < 4;i++){
            assertEquals(true,actions.get(i).getResult().get());
        }

        for(int i = 4;i < 6;i++){
            assertEquals(false,actions.get(i).getResult().get());
        }
    }

    public static void createAction(String theCourseName,String theCourseGrade,
                                                                  String[] theCoursePreq){
        Vector<String> studentPreq = new Vector<>();

        for(int i = 0;i < theCoursePreq.length;i++){
            studentPreq.add(theCoursePreq[i]);
        }

        AddCourseToStudentGradeSheetAction studentOne = new AddCourseToStudentGradeSheetAction(
                "Adding",theCourseName,theCourseGrade,studentPreq);

        actions.add(studentOne);
        actionsPrivateStates.add(new StudentPrivateState());
        actionsActorIds.add("Student");
    }
}