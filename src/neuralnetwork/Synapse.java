/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;
import java.*;
/**
 *
 * @author Ben
 */
public class Synapse {
    Neuron input;
    Neuron output;
    double weight;
    //boolean fixed;
    Synapse(Neuron input, Neuron output, double weight){
        this.input = input;
        this.output = output;
        this.weight = weight;
    }
    public double getInput(){
        return input.value*weight;
    }
}
