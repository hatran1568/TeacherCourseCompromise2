/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teacherclasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author saplab
 */
public class Data {

    public static double MAX_QUALITY_P0;
    public static double MIN_QUALITY_P0 = 0;

    public static double MAX_SALARY_P0;
    public static double MIN_SALARY_P0 = 0;
    
    public static double MAX_FAVORITE_SUBS_PJ = 43191.16;
    public static double MIN_FAVORITE_SUBS_PJ = 0;
    
    public static double MAX_FAVORITE_SLOTS_PJ = 43191.16;
    public static double MIN_FAVORITE_SLOTS_PJ = 0;
    
    public static double MAX_ERR_COURSES_PJ = 100000;
    public static double MIN_ERR_COURSES_PJ = 0;
    
    public static double MAX_PERIODS_PJ = 100000;
    public static double MIN_PERIODS_PJ = 0;
    
   
    
    int L; //number of subjects
    int M; //number of courses
    int N; //number of teachers
    int T; // number of timeslots
    double[][] FSub = new double[500][500]; //rating of teachers towards subjects
    double[][] FSlot = new double[500][500]; //rating of teachers towards timeslots
    double[][] Rating = new double[500][500]; //rating of students towards teachers with regards to subjects
    Teacher[] teachers = new Teacher[500]; //list of teachers
    Course[] courses = new Course[500];
    
    double w1; // weight of total rating 
    double w2; // weight of total salary
    double w3; // weight of teacher favour slot
    double w4; // weight of teacher favour subject
    double w5; // weight of teacher error slot
    double w6; // weight of teacher periods
    double c1; // Weight of P_0
    double c2; // weight of P_j
    public Data() {

    }

    public static Data readDataFromFile() throws FileNotFoundException, IOException {
        Data data = new Data();
        data.L = 13;
        data.M = 153;
        data.N = 25;
        data.T = 10;
        data.w1 = 1;
        data.w2 = 1;
        data.w3 = 1;
        data.w4 = 1;
        data.w5 = 1;
        data.w6 = 1;
        data.c1=1;
        data.c2 =1;

        String workingDirectory = System.getProperty("user.dir");
        String excelFilePath = workingDirectory + "//data//data_T.xlsx";

        InputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = (Sheet) workbook.getSheetAt(0);

        for (int i = 1; i <= data.N; i++) {
            Teacher t = new Teacher();
            t.setId(i - 1);
            t.setName(sheet.getRow(i).getCell(1).toString());
            t.setSalaryLevel((double) sheet.getRow(i).getCell(2).getNumericCellValue());
            t.setMinClass((int) sheet.getRow(i).getCell(3).getNumericCellValue());
            t.setMaxClass((int) sheet.getRow(i).getCell(4).getNumericCellValue());
            t.setIdealClass((int) sheet.getRow(i).getCell(5).getNumericCellValue());
            data.teachers[i - 1] = t;
        }

        excelFilePath = workingDirectory + "//data//data_FSlot_S.xlsx";
        inputStream = new FileInputStream(new File(excelFilePath));
        workbook = new XSSFWorkbook(inputStream);
        sheet = (Sheet) workbook.getSheetAt(1);

        for (int i = 0; i < data.N; i++) {
            for (int j = 0; j < data.L; j++) {
                data.FSub[i][j] = sheet.getRow(i + 1).getCell(j + 1).getNumericCellValue();
            }
        }

        excelFilePath = workingDirectory + "//data//data_FSlot_T.xlsx";
        inputStream = new FileInputStream(new File(excelFilePath));
        workbook = new XSSFWorkbook(inputStream);
        sheet = (Sheet) workbook.getSheetAt(0);

        for (int i = 0; i < data.N; i++) {
            for (int j = 0; j < data.T; j++) {
                data.FSlot[i][j] = sheet.getRow(i + 1).getCell(j + 1).getNumericCellValue();
            }
        }

        excelFilePath = workingDirectory + "//data//data_Rating.xlsx";
        inputStream = new FileInputStream(new File(excelFilePath));
        workbook = new XSSFWorkbook(inputStream);
        sheet = (Sheet) workbook.getSheetAt(0);

        for (int i = 0; i < data.N; i++) {
            for (int j = 0; j < data.L; j++) {
                data.Rating[i][j] = sheet.getRow(i + 1).getCell(j + 1).getNumericCellValue();
            }
        }

        excelFilePath = workingDirectory + "//data//sp22_to_import.xlsx";
        inputStream = new FileInputStream(new File(excelFilePath));
        workbook = new XSSFWorkbook(inputStream);
        sheet = (Sheet) workbook.getSheetAt(0);
        for (int i = 1; i <= data.M; i++) {
            Course c = new Course();
            c.setId(i - 1);
            c.setClasses(sheet.getRow(i).getCell(1).toString());
            c.setSubjectName(sheet.getRow(i).getCell(2).toString());
            c.setSubject((int) sheet.getRow(i).getCell(3).getNumericCellValue());
            c.setSlot((int) sheet.getRow(i).getCell(5).getNumericCellValue());
            c.setRoom(sheet.getRow(i).getCell(7).toString());
            data.courses[i - 1] = c;
        }

        MAX_QUALITY_P0 = data.calc_Max_Quality_P0();
        MAX_SALARY_P0 = data.calc_Max_Salary_P0();
        MIN_SALARY_P0 = data.calc_Min_Salary_P0();
        MAX_FAVORITE_SUBS_PJ = data.calc_Max_Favourite_Subs();
        MAX_FAVORITE_SLOTS_PJ = data.calc_Max_Favorite_Slots();
        MAX_ERR_COURSES_PJ = data.calc_Max_Err_Courses();
        MAX_PERIODS_PJ = data.calc_Max_Periods();
        MIN_FAVORITE_SUBS_PJ = data.calc_Min_Favourite_Subs();
        MIN_FAVORITE_SLOTS_PJ = data.calc_Min_Favorite_Slots();
        MIN_PERIODS_PJ = data.calc_Min_Periods();
        
        return data;
    }
    
    public double calc_Max_Quality_P0(){
        return 10 * this.M;
    }
    
    public double calc_Max_Salary_P0(){
        double maxSalaryLevel = 0;
        for (int i = 0; i < this.N; i++){
            if (maxSalaryLevel <= this.teachers[i].getSalaryLevel()){
                maxSalaryLevel = this.teachers[i].getSalaryLevel();
            }
        }
        return this.M * maxSalaryLevel;
    }
    
    public double calc_Min_Salary_P0(){
        double minSalaryLevel = 30;
        for (int i = 0; i < this.N; i++){
            if (minSalaryLevel >= this.teachers[i].getSalaryLevel()){
                minSalaryLevel = this.teachers[i].getSalaryLevel();
            }
        }
        return this.M * minSalaryLevel;
    }

    public double calc_Max_Favourite_Subs(){
        return this.M * 10;
    }
    
    public double calc_Min_Favourite_Subs(){
        return 0;
    }
    
    public double calc_Max_Favorite_Slots(){
        return 10 * this.M;
    }
    
    public double calc_Min_Favorite_Slots(){
        return 0;
    }
    
    public double calc_Max_Err_Courses(){
        double sum = 0;
        for (int i = 0; i < this.N; i++){
            sum += this.teachers[i].getMaxClass() - this.teachers[i].getMinClass();
        }
        return sum;
    }
    
    public double calc_Max_Periods(){
        return Double.min(this.M - 1, this.N * 15);
    }
    
    public double calc_Min_Periods(){
        return 0;
    }
}
