/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class Neuron {
    
    ArrayList<Synapse> input = new ArrayList<Synapse>();
    public double threshold = 0;
    public double value = 1;
    public int type;
   
    Neuron(int type){
        this.type = type;
    }
    
    public void update(){
        if(type==0){
            for(Synapse in : input)
                value+= in.getInput();
            value = Math.tanh(value);
        }
    }
    
}
