package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cell {
    public Integer number;
    public List<Integer> possibleNumbers;

    public Cell() {
        this.number = null;
        this.possibleNumbers = fillUp_possibleNumbers();
    }

    public List<Integer> fillUp_possibleNumbers(){
        List<Integer> possibleNumbers = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            possibleNumbers.add(i);
        }
        return possibleNumbers;
    }
    public void remove_listElement(Integer number_toRemove){
        this.possibleNumbers.remove((Integer) number_toRemove);
    }

    public Integer pick_randomNumber(){
        Random rnd = new Random();
        return this.possibleNumbers.get(rnd.nextInt(0, possibleNumbers.size()));
    }


}
