/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author Ben
 */
public class Testing {

    /**
     * @param args the command line arguments
     */
    public static Random r;
    //public static Brain a,b,c;
    public static ArrayList<Brain> Brains = new ArrayList<Brain>();
    public static JFrame frame;

        /*
            XOR
        {{0,0},{0}},
        {{1,0},{1}},
        {{0,1},{1}},
        {{1,1},{1}},
        
        */ 
    
            //Full bit adder
        /*{{0,0,0},{0,0}},
        {{1,0,0},{1,0}},
        {{0,1,0},{1,0}},
        {{1,1,0},{0,1}},
        {{0,0,1},{1,0}},
        {{1,0,1},{0,1}},
        {{0,1,1},{0,1}},
        {{1,1,1},{1,1}},*/
    
    public static int[][][] expected = new int[][][]{
        //Full bit adder
        {{0,0,0},{0,0,0}},
        {{1,0,0},{1,0,0}},
        {{0,1,0},{0,1,0}},
        {{1,1,0},{1,1,0}},
        {{0,0,1},{0,0,1}},
        {{1,0,1},{1,0,1}},
        {{0,1,1},{0,1,1}},
        {{1,1,1},{1,1,1}},
        
    };
    
    public static int[] Scores;
    public static int[] hidden = new int[]{1,6,6};
    public static int[] Output;
    public static int generations = 1000000;
    public static int creatures = 1000;
    public static int gen;
    
    public void main(String[] args){
        Scores = new int[expected.length+1];
        //new BitOperators();
        r = new Random();
        frame = new JFrame();
        frame.add(new Window(frame));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512, 512);
        //frame.setBackground(new Color(0,0,0,1));
        frame.setVisible(true);
        
        System.out.println("Starting neural network");
        //Create 1000 random brainseses
        for(int i = 0 ; i < creatures;i++){
            Brains.add(new Brain(expected,hidden,null,null));
        }
        for (gen = 0; gen < generations; gen++) {
            
            
            Scores = new int[expected.length+1];
            
        
        //Calculate their fitness
        for(Brain b : Brains){
            Fitness(b,expected);
            Scores[b.Fitness]++;
        }
        //Arrays.sort(myTypes, (a,b) -> a.name.compareTo(b.name));
        if(gen % (100) == 0){
            System.out.println("Generation: "+gen);
            for (int i = 0; i < Scores.length; i++) {
                System.out.println(Scores[i]+" got a score of "+i);
            }
        }

        Comparator<Brain> FitnessNumber = (b1, b2) -> Integer.compare(
            b2.Fitness, b1.Fitness
        );
        Brains.sort(FitnessNumber);

        //delete brains with the worse fitness untill there are only 500 left
        
        while(true){
            if(Brains.size() == creatures/2||Brains.get(0).Fitness == 0){
                 break;
             }
             Brains.remove(Brains.get(0));
        }
        double chances=Brains.size();
        while(Brains.size()<creatures){
            //chances+=0.1;
            int A = r.nextInt(Brains.size());
            int B = r.nextInt(Brains.size());
            
            if(r.nextInt((Brains.get(A).Fitness)+1)<1&&r.nextInt((Brains.get(B).Fitness)+1)<1){
                Brains.add(
                    new Brain(expected,hidden,
                        Brains.get(A).Genes,
                        Brains.get(B).Genes
                    )
                );            
            }else{
            }
            
            
        }
        boolean Break = false;
        for (int i = 0; i < Scores.length; i++) {
            if(Scores[i] == creatures) Break=true;
        }
        if(Break)break;
        
        }
        System.out.println("Finished");
        System.out.println("Generation: "+gen);
        for (int i = 0; i < Scores.length; i++) {
            System.out.println(Scores[i]+" got a score of "+i);
        }
        for(Brain b : Brains){
            for(Synapse s : b.Genes ){
                System.out.println("Gene: "+b.Genes.indexOf(s)+" = "+s.weight);
            }
            break;
        }
        
    }
    public static int Fitness(Brain b,int[][][] ex){
        //System.out.println("Brain");
        int error=0;
        //System.out.println("");
        for (int i = 0; i < ex.length; i++){
            for (int inputs = 0; inputs < ex[i][0].length; inputs++) {
                //System.out.println("Setting Neuron("+inputs+") to "+ex[i][0][inputs]);
                b.Input.get(inputs).value = ex[i][0][inputs];
            }
            
            Output = b.update();
            if(Arrays.equals(Output,ex[i][1])){
                //System.out.println(Arrays.toString(ex[i][0])+" = "+Arrays.toString(ex[i][1]));
            }else{
                error++;
                //System.out.println(Arrays.toString(ex[i][0])+" = "+Arrays.toString(Output));
            }
            //System.out.println(Arrays.toString(ex[i][0])+" = "+Arrays.toString(Output));
           
        }
        b.Fitness = error;
        return error;
    }
    
    
}
