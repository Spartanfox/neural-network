/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Ben
 */
public class Brain {
    Random r;// = new Random(1);
    int Fitness;
    ArrayList<ArrayList<Neuron>> neurons = new ArrayList<ArrayList<Neuron>>();
    ArrayList<Neuron>Input;
    ArrayList<Neuron>Output;
    ArrayList<Synapse>Genes = new ArrayList<Synapse>();
    int seed = new Random().nextInt();
    Brain(int[][][] training,int[] hidden, ArrayList<Synapse>GenePool1, ArrayList<Synapse>GenePool2){
       //if(seed==0){
            r = new Random(this.seed);
       //}
        //XOR 271389959
        //halfbitadder 1779229535  2,3,2
        //System.out.println("Seed:"+seed);
        neurons.add(new ArrayList<Neuron>()); //input layer

        //build the brain
        Input = neurons.get(0);
        for (int i = 0; i < hidden[0]; i++) {
           // System.out.print(" ");
        }
        for (int i = 0; i < training[0][0].length; i++) {
           Input.add(new Neuron(1));
           //System.out.print("   (I)");
        }
            //add bias
            Input.add(new Neuron(1));
        
        for (int layer = 0; layer < hidden.length; layer++) {
            
            neurons.add(new ArrayList<Neuron>());
            //System.out.println("\n");
            ArrayList<Neuron> Hidden = neurons.get(layer+1);
            for (int neuron = 0; neuron < hidden[layer]; neuron++) {
                //System.out.print("   (H)");
                Hidden.add(new Neuron(0));
                Neuron n = Hidden.get(neuron);
                
                for (int i2 = 0; i2 < neurons.get(layer).size(); i2++) {
                    Synapse connect = new Synapse(neurons.get(layer).get(i2),n,(r.nextInt(2)+r.nextDouble())-1);
                    
                    n.input.add(connect);
                    Genes.add(connect);
                }
            }
        }
        //System.out.println("\n");
        neurons.add(new ArrayList<Neuron>());
        Output = neurons.get(hidden.length+1);
        for (int i = 0; i < hidden[0]; i++) {
           // System.out.print(" ");
        }
        for (int i = 0; i < training[0][1].length; i++) {
           // System.out.print("   (O)");
            Output.add(new Neuron(0));
            Neuron n = Output.get(i);
            for (int i2 = 0; i2 < hidden[hidden.length-1]; i2++) {
                Synapse connect = new Synapse(neurons.get(hidden.length).get(i2),n,(r.nextInt(2)+r.nextDouble())-1);
                n.input.add(connect);
                Genes.add(connect);
            }
        }
        //update neurons

        //0,0:0
        //0,1:1
        //1,0
        //1,1:0
        //System.out.println("\nPrinting brains Genes");
        int i = 0;
        if(r.nextInt(1000)!=0){
            for(Synapse s : Genes){
                i++;
                //System.out.println(s.weight);
                double weight=0;
                if(GenePool1 != null && GenePool2 != null && r.nextInt(1000)>1){
                    if(r.nextBoolean()){
                       //System.out.print("       A: ");
                       if(r.nextBoolean())weight = GenePool1.get(Genes.indexOf(s)).weight+0.1;
                       else weight = GenePool1.get(Genes.indexOf(s)).weight-0.1;
                       
                       weight = GenePool1.get(Genes.indexOf(s)).weight;
                    }
                    else{
                      // System.out.print("       B: ");

                       if(r.nextBoolean())weight = GenePool2.get(Genes.indexOf(s)).weight+0.1;
                       else weight = GenePool2.get(Genes.indexOf(s)).weight-0.1;  
                       
                       
                       weight = GenePool2.get(Genes.indexOf(s)).weight;
                    }
                    s.weight = weight;
                }else{
                    //System.out.print("Mutation: ");
                }

                //System.out.println((s.weight));
            }
        }
    }
    public int[] update(){
        for (int layer = 0; layer < neurons.size(); layer++) {
            //System.out.println("Next layer update");
            for (int neuron = 0; neuron < neurons.get(layer).size(); neuron++) {
                //System.out.print("Next Neuron("+layer+","+Neuron+") update: ");
                neurons.get(layer).get(neuron).update();  
                //System.out.println(neurons.get(layer).get(Neuron).value);
                //System.out.println(
                //        "Layer:"+layer
                //        +" Neuron: "+Neuron
                //        +" is equal to "+neurons.get(layer).get(Neuron).value);
            }
        }
        //System.out.println(Arrays.toString(new int[] {Output(Output.get(0).value),Output(Output.get(1).value)}));
        int[] returner = new int[Output.size()];
        for(int i = 0; i < Output.size(); i++){
            returner[i] = Output(Output.get(i).value);
        }
        return returner;
    }
    public int Output(double input){
        //System.out.println(input);
        if(input <=0){return 0;}
        else return 1;
    }
}
