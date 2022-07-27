package de.die_bartmanns.daniel.mymeal.data;

import java.io.Serializable;

/**
 * Created by Daniel on 04.01.2017.
 */
public class Ingredient implements Serializable{

    private String name;
    private float amount;
    private Unit unit;

    public Ingredient(String name, float amount, Unit unit){
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public Ingredient(String name, float amount){
        this.name = name;
        this.amount = amount;
    }

    public Ingredient(){}

    public String getName(){return name;}

    public float getAmount(){return amount;}

    public Unit getUnit(){return unit;}

    public void setName(String name){this.name = name;}

    public void setAmount(float amount){this.amount = amount;}

    public void setUnit(Unit unit){this.unit = unit;}

    public String toString(){
        return amount + "\t" + (unit != null ? unit : "\t") + "\t" + name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Ingredient){
            Ingredient i = (Ingredient) obj;
            return name.equals(i.name) && amount == i.amount && unit == i.unit;
        }
        return false;
    }

}
