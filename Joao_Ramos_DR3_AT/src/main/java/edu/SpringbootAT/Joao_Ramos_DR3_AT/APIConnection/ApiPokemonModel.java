package edu.SpringbootAT.Joao_Ramos_DR3_AT.APIConnection;

import edu.SpringbootAT.Joao_Ramos_DR3_AT.model.Pokemon;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.model.Stat;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.model.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@AllArgsConstructor@NoArgsConstructor
public class ApiPokemonModel {

    @Getter private int id;
    @Getter private String name;
    @Getter private ApiStat[] stats;
    @Getter private ApiType[] types ;
    public Pokemon ToPokemon(){
        Pokemon p = new Pokemon();
        p.setNationalDex(id);
        p.setName(name);
        p.setStats(convertToPokemonStats());
        p.setTypes(convertToPokemonTypes());


        return p;
    }

    private Type[] convertToPokemonTypes() {
        ArrayList<Type> newTypes = new ArrayList<Type>();

        for (int i = 0; i < this.types.length; i++) {
            int slot = this.types[i].getSlot();
            String name = this.types[i].getType().get("name");

            Type newType = new Type(name, slot);
            newTypes.add(newType);
        }

        newTypes.sort((a, b) -> {
            return a.getSlot() - b.getSlot();
        });

        Type[] a = new Type[newTypes.size()];
        return newTypes.toArray(a);
    }

    private Stat[] convertToPokemonStats(){
        Stat[] newStats = new Stat[6];
        for (int i = 0; i < 6 ; i++) {
            int base = this.stats[i].getBase_stat();
            String name = this.stats[i].getStat().get("name");

            Stat newStat = new Stat(name, base);
            newStats[i] = newStat;
        }
        return newStats;
    }

}
