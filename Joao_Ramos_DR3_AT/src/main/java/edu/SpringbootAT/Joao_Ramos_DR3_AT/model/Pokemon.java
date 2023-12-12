package edu.SpringbootAT.Joao_Ramos_DR3_AT.model;

import lombok.Getter;
import lombok.Setter;

public class Pokemon {
    @Getter @Setter private String name;
    @Getter @Setter private Stat[] stats;
    @Getter @Setter private Type[] types;
    @Getter @Setter private int NationalDex;

    public boolean isOfType(String type){
        for (int i = 0; i < types.length; i++) {
            if(types[i].getName().equals(type)){
                return true;
            }
        }
        return false;
    }

    public Stat getStat(String name){
        for (int i = 0; i < stats.length; i++) {
            if (stats[i].getName().equals(name)){
                return stats[i];
            }
        }
        return null;
    }

    public void setStat(String name, int value){
        for (int i = 0; i < stats.length; i++) {
            if (stats[i].getName().equals(name)){
                stats[i] = new Stat(stats[i].getName(), value);
            }
        }
    }
    public String toString(){
       return " { " + NationalDex + ", " + name + ", " + types.toString() + ", " + stats.toString() + " } ";
    } ;

}
