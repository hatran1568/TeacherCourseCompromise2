/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacherclasses;

import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class NSGA {

    public Data data;

    public NSGA(Data data) {
        this.data = data;
    }

    public void fast_nondominated_sort(Population population) {
        population.fronts = new ArrayList<ArrayList<Solution>>();
        ArrayList<Solution> lst = new ArrayList<Solution>();
        for (Solution individual : population.population) {
            individual.domination_count = 0;
            individual.dominated_solution = new ArrayList<Solution>();

            for (Solution other_individual : population.population) {
                if (individual.dominates(other_individual)) {
                    individual.dominated_solution.add(other_individual);
                } else {
                    if (other_individual.dominates(individual)) {
                        individual.domination_count++;
                    }
                }
            }

            if (individual.domination_count == 0) {
                individual.rank = 0;
                lst.add(individual);

            }

        }
        population.fronts.add(0, lst);

        int i = 0;
        while (population.fronts.get(i).size() > 0) {
            lst = new ArrayList<Solution>();
            for (Solution individual : population.fronts.get(i)) {
                for (Solution other_individual : individual.dominated_solution) {

                    other_individual.domination_count--;

                    if (other_individual.domination_count == 0) {
                        other_individual.rank = i + 1;
                        lst.add(other_individual);
                    }
                }
                i++;
                population.fronts.add(i, lst);
            }
        }
    }


    public void calculate_crowding_distance(ArrayList<Solution> front) {
        if (front.size() > 0) {
            int solution_num = front.size();

            for (Solution individual : front) {
                individual.crowding_distance = 0;
            }

            front.sort((o1, o2) -> {
                int flag = 0;
                if (o1.cal_Quality_P0(data) < o2.cal_Quality_P0(data)) {
                    flag = 1;
                }
                if (o1.cal_Quality_P0(data) > o2.cal_Quality_P0(data)) {
                    flag = -1;
                }
                return flag;
            });

            front.get(0).crowding_distance = Math.pow(10, 9);
            front.get(solution_num - 1).crowding_distance = Math.pow(10, 9);

            double max = -1;
            double min = -1;
            for (int j = 0; j < front.size(); j++) {
                double curr = front.get(j).cal_Quality_P0(data);
                if ((max == -1) || (curr > max)) {
                    max = curr;
                }

                if ((min == -1) || (curr < min)) {
                    min = curr;
                }
            }

            double scale = max - min;
            if (scale == 0) {
                scale = 1;
            }
            for (int j = 1; j < solution_num - 1; j++) {
                front.get(j).crowding_distance += (front.get(j + 1).cal_Quality_P0(data) - front.get(j - 1).cal_Quality_P0(data)) / scale;
            }
            front.sort((o1, o2) -> {
                int flag = 0;
                if (o1.cal_Salary_P0(data) > o2.cal_Salary_P0(data)) {
                    flag = 1;
                }
                if (o1.cal_Salary_P0(data) < o2.cal_Salary_P0(data)) {
                    flag = -1;
                }
                return flag;
            });

            front.get(0).crowding_distance = Math.pow(10, 9);
            front.get(solution_num - 1).crowding_distance = Math.pow(10, 9);

            max = -1;
            min = -1;
            for (int j = 0; j < front.size(); j++) {
                double curr = front.get(j).cal_Quality_P0(data);
                if ((max == -1) || (curr > max)) {
                    max = curr;
                }

                if ((min == -1) || (curr < min)) {
                    min = curr;
                }
            }

            scale = max - min;
            if (scale == 0) {
                scale = 1;
            }
            for (int j = 1; j < solution_num - 1; j++) {
                front.get(j).crowding_distance += (front.get(j + 1).cal_Salary_P0(data) - front.get(j - 1).cal_Salary_P0(data)) / scale;
            }
            for (int i = 0; i < data.N; i++) {
                final int idx = i;
                front.sort((o1, o2) -> {
                    int flag = 0;
                    if (o1.cal_Favourite_Slots_PJ(data, idx) < o2.cal_Favourite_Slots_PJ(data, idx)) {
                        flag = 1;
                    }
                    if (o1.cal_Favourite_Slots_PJ(data, idx) > o2.cal_Favourite_Slots_PJ(data, idx)) {
                        flag = -1;
                    }
                    return flag;
                });

                front.get(0).crowding_distance = Math.pow(10, 9);
                front.get(solution_num - 1).crowding_distance = Math.pow(10, 9);

                max = -1;
                min = -1;
                for (int j = 0; j < front.size(); j++) {
                    double curr = front.get(j).cal_Favourite_Slots_PJ(data, idx);
                    if ((max == -1) || (curr > max)) {
                        max = curr;
                    }

                    if ((min == -1) || (curr < min)) {
                        min = curr;
                    }
                }

                scale = max - min;
                if (scale == 0) {
                    scale = 1;
                }
                for (int j = 1; j < solution_num - 1; j++) {
                    front.get(j).crowding_distance += (front.get(j + 1).cal_Favourite_Slots_PJ(data, idx) - front.get(j - 1).cal_Favourite_Slots_PJ(data, idx)) / scale;
                }

            }
            for (int i = 0; i < data.N; i++) {
                final int idx = i;
                front.sort((o1, o2) -> {
                    int flag = 0;
                    if (o1.cal_Favourite_Subs_PJ(data, idx) < o2.cal_Favourite_Subs_PJ(data, idx)) {
                        flag = 1;
                    }
                    if (o1.cal_Favourite_Subs_PJ(data, idx) > o2.cal_Favourite_Subs_PJ(data, idx)) {
                        flag = -1;
                    }
                    return flag;
                });

                front.get(0).crowding_distance = Math.pow(10, 9);
                front.get(solution_num - 1).crowding_distance = Math.pow(10, 9);

                max = -1;
                min = -1;
                for (int j = 0; j < front.size(); j++) {
                    double curr = front.get(j).cal_Favourite_Subs_PJ(data, idx);
                    if ((max == -1) || (curr > max)) {
                        max = curr;
                    }

                    if ((min == -1) || (curr < min)) {
                        min = curr;
                    }
                }

                scale = max - min;
                if (scale == 0) {
                    scale = 1;
                }
                for (int j = 1; j < solution_num - 1; j++) {
                    front.get(j).crowding_distance += (front.get(j + 1).cal_Favourite_Subs_PJ(data, idx) - front.get(j - 1).cal_Favourite_Subs_PJ(data, idx)) / scale;
                }

            }
            for (int i = 0; i < data.N; i++) {
                final int idx = i;
                front.sort((o1, o2) -> {
                    int flag = 0;
                    if (o1.cal_Err_Courses_PJ(data, idx) < o2.cal_Err_Courses_PJ(data, idx)) {
                        flag = -1;
                    }
                    if (o1.cal_Err_Courses_PJ(data, idx) > o2.cal_Err_Courses_PJ(data, idx)) {
                        flag = 1;
                    }
                    return flag;
                });

                front.get(0).crowding_distance = Math.pow(10, 9);
                front.get(solution_num - 1).crowding_distance = Math.pow(10, 9);

                max = -1;
                min = -1;
                for (int j = 0; j < front.size(); j++) {
                    double curr = front.get(j).cal_Err_Courses_PJ(data, idx) ;
                    if ((max == -1) || (curr > max)) {
                        max = curr;
                    }

                    if ((min == -1) || (curr < min)) {
                        min = curr;
                    }
                }

                scale = max - min;
                if (scale == 0) {
                    scale = 1;
                }
                for (int j = 1; j < solution_num - 1; j++) {
                    front.get(j).crowding_distance += (front.get(j + 1).cal_Err_Courses_PJ(data, idx) - front.get(j - 1).cal_Err_Courses_PJ(data, idx)) / scale;
                }

            }
             for (int i = 0; i < data.N; i++) {
                final int idx = i;
                front.sort((o1, o2) -> {
                    int flag = 0;
                    if (o1.cal_Periods_PJ(data, idx) < o2.cal_Periods_PJ(data, idx)) {
                        flag = -1;
                    }
                    if (o1.cal_Periods_PJ(data, idx) > o2.cal_Periods_PJ(data, idx)) {
                        flag = 1;
                    }
                    return flag;
                });

                front.get(0).crowding_distance = Math.pow(10, 9);
                front.get(solution_num - 1).crowding_distance = Math.pow(10, 9);

                max = -1;
                min = -1;
                for (int j = 0; j < front.size(); j++) {
                    double curr = front.get(j).cal_Periods_PJ(data, idx);
                    if ((max == -1) || (curr > max)) {
                        max = curr;
                    }

                    if ((min == -1) || (curr < min)) {
                        min = curr;
                    }
                }

                scale = max - min;
                if (scale == 0) {
                    scale = 1;
                }
                for (int j = 1; j < solution_num - 1; j++) {
                    front.get(j).crowding_distance += (front.get(j + 1).cal_Periods_PJ(data, idx) - front.get(j - 1).cal_Periods_PJ(data, idx)) / scale;
                }

            }
            

        }
    }
     public int crowding_operator(Solution individual, Solution other_individual){
        if ((individual.rank<other_individual.rank) || ((individual.rank== other_individual.rank) && (individual.crowding_distance>other_individual.crowding_distance))){
            return 1;
        }else{
            return 0;
        }
    }

}
