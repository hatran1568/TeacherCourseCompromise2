/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teacherclasses;

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
    
    public Solution(Data data){
        this.data = data;
    }
    
}
