package com.digitedgy.transform;

public class WeighingMachineTransformation implements Transformable{

    @Override
    public String transform(String input) {
        return Double.parseDouble(input.replace("=",""))+"";
    }
}
