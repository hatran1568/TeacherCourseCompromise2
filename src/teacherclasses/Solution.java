/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teacherclasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 *
 * @author saplab
 */
public class Solution {

    public int chromosome[][];
    public Data data;

    public int[][] getChromosome() {
        return chromosome;
    }

    public void setChromosome(int[][] chromosome) {
        this.chromosome = chromosome;
    }

    public Solution(Data data) {
        this.data = data;
    }

    public double cal_Quality_P0(Data data) {
        double quality = 0;
        for (int i = 0; i < data.M; i++) {
            for (int j = 0; j < data.N; j++) {
                quality += chromosome[i][j] * data.Rating[i][j];
            }
        }
        return quality;
    }

    public double cal_Salary_P0(Data data) {
        double salary = 0;
        for (int i = 0; i < data.M; i++) {
            for (int j = 0; j < data.N; j++) {
                salary += chromosome[i][j] * data.teachers[j].getSalaryLevel();
            }
        }
        return salary;
    }

    public double cal_Payoff_P0(Data data) {
        double payoff;
        payoff = data.w1 * cal_Quality_P0(data) / (data.w2 * cal_Salary_P0(data));
        return payoff;
    }

    public double cal_Favourite_Subs_PJ(Data data, int teacher) {
        double favourite_Subs = 0;
        for (int i = 0; i < data.M; i++) {
            favourite_Subs += chromosome[i][teacher] * data.FSub[i][teacher];
        }
        return favourite_Subs;
    }
    
    public double cal_Favourite_Slots_PJ(Data data, int teacher) {
        double favourite_Slots = 0;
        for (int i = 0; i < data.M; i++) {
            favourite_Slots += chromosome[i][teacher] * data.FSlot[i][teacher];
        }
        return favourite_Slots;
    }
    public double cal_Err_Courses_PJ(Data data,int teacher){
        double Err_Courses = 0;
        for(int i=0;i<data.M;i++){
            Err_Courses += chromosome[i][teacher];
        }
        Err_Courses = data.teachers[teacher].getIdealClass()-Err_Courses;
        return  Math.abs(Err_Courses);
    }
    public double cal_Periods_PJ(Data data,int teacher){
        
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0;i<data.M;i++){
           if(chromosome[i][teacher] == 1) {
               list.add(data.courses[i].getSlot());
           }      
        }
        Collections.sort(list);
        int t = 1;
        for(int i = 1 ; i < list.size() ; i++) {
            if (list.get(i) - 1 != list.get(i-1)) 
                t++;
        }
        
        return t;
       
    }
    public double cal_Payoff_PJ(Data data,int teacher){
        double payoff =(data.w3*cal_Favourite_Subs_PJ(data, teacher)+data.w4*cal_Favourite_Slots_PJ(data, teacher))/(data.w5*cal_Err_Courses_PJ(data, teacher)+data.w6*cal_Periods_PJ(data, teacher));
        return payoff;
    }
    public double cal_Payoff_All_PJ(Data data){
        double payoff=0;
        for(int i=0;i<data.N;i++){
            payoff+=cal_Payoff_PJ(data, i);
        }
        return  payoff;
    }
    
    public double cal_Fitness(Data data){
        return data.c1*cal_Payoff_P0(data)+data.c2*cal_Payoff_All_PJ(data);
    }
    
    public boolean check_Solution(Data data){
        // all courses have a teacher assigned
        int sumSlot = 0;
        for (int i = 0; i < data.M; i++) {
            for (int j = 0; j < data.N; j++) {
                sumSlot += chromosome[i][j];
            }
        }
        
        if (sumSlot != data.M)
            return false;
        
        // no courses have 2 teachers
        for (int i = 0; i < data.M; i++) {
            int sum2 = 0;
            for (int j = 0; j < data.N; j++) {
                sum2 += chromosome[i][j];
            }
            if (sum2 != 1) return false;
            sum2 = 0;
        }
        
        // no teachers teach 2 different courses in the same slot
        for (int i = 0; i<data.N; i++){
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = 0; j < data.M; j++) {
                if(chromosome[j][i] == 1) {
                    list.add(data.courses[j].getSlot());
                }  
            }
            Collections.sort(list);
        
            for(int j = 1 ; j < list.size() ; j++) {
                if (list.get(j) == list.get(j-1)) 
                    return false;
            }
            list.clear();
        }
        
        //total number of slots of a teacher within min and max specified
        for (int i = 0; i< data.N; i++){
            int sum3 = 0;
            for (int j = 0; j < data.M; i++){
                sum3 += chromosome[j][i];
            }
            if (sum3 > data.teachers[i].getMaxClass() || sum3 < data.teachers[i].getMinClass()){
                return false;
            }
        }
        
        
        
        return true;
    }
    
}
