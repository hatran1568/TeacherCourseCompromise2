/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package teacherclasses;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author saplab
 */
public class TeacherClasses {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
//        Data data = Data.readDataFromFile();
//        GeneticAlgorithmImplementer Ga = new GeneticAlgorithmImplementer(data);
//        ArrayList<Solution> result = Ga.implementGA();
//        
//        for (Solution solution : result) {
//            System.out.println(solution.checkAllCourseConstraint(data));
//            System.out.println(solution.checkInRangeSlotConstraint(data));
//            System.out.println(solution.checkSelfRatingConstaint(data));
//            System.out.println(solution.checkSingleSlotConstraint(data));
//            System.out.println(solution.checkSingleTeacherCourseConstraint(data));
//            System.out.println(solution.checkSlotRatingConstraint(data));
//            System.out.println(solution.checkStudentRatingConstraint(data));
//            System.out.println(solution.check_Solution(data));
//          
//            System.out.println(solution.cal_Fitness(data));
//        }
//        GeneticAlgorithmImplementer.writeSolutionAsTimetable(result.get(result.size()-1), data);
//        GeneticAlgorithmImplementer.writeErrCourseToExcel(result.get(result.size()-1), data);
//        GeneticAlgorithmImplementer.writeSolution(result, data);
        for (int i = 0; i < 39; i++){
            Data data = Data.readDataFromFile();
            GeneticAlgorithmImplementer Ga = new GeneticAlgorithmImplementer(data);
            ArrayList<Solution> result = Ga.implementGA();
            GeneticAlgorithmImplementer.addResult(result.get(result.size()-1), data);
        }
    }

}
