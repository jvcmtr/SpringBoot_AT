package edu.SpringbootAT.Joao_Ramos_DR3_AT;

import edu.SpringbootAT.Joao_Ramos_DR3_AT.Exeptions.AlredyExistsException;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.Exeptions.NotFoundException;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.model.Pokemon;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.model.Stat;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.model.Type;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.utils.IPokemonRepos;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.utils.PokemonRepos;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class PokemonReposTest {
    IPokemonRepos testRepos = new PokemonRepos(9) ;

    @Test @DisplayName("Successfully gets all pokemons from API")
    void pegarIniciais(){
        assertEquals(testRepos.getAll().size(), 9);
    }
    @Test @DisplayName("Succesfully finds a pokemon from API")
    void encontrarPokemonsPorId(){
        Pokemon bulba = testRepos.getById(1);
        Pokemon charm = testRepos.getById(4);
        Pokemon squirt = testRepos.getById(7);

        assertEquals(bulba.getName(), "bulbasaur");
        assertEquals(charm.getName(), "charmander");
        assertEquals(squirt.getName(), "squirtle");
    }
    @Test @DisplayName("Succesfully loads pokemon Information from API")
    void informacoesCorretas(){
        Pokemon bulba = testRepos.getById(1);

        assertEquals(bulba.getName(), "bulbasaur");
        assertEquals(bulba.getNationalDex(), 1);
        assertEquals(bulba.getTypes()[0].getName(), "grass");
        assertEquals(bulba.getTypes()[0].getSlot(), 1);
        assertEquals(bulba.getStat("hp").getBaseStat(), 45);
    }
    @Test @DisplayName("Succesfully finds single type pokemon")
    void encontrarPokemonsPorTipo(){
        List<Pokemon> fireTypes = testRepos.getByType(new String[]{"flying"});
        assertEquals(fireTypes.get(0).getName(), "charizard");
    }
    @Test @DisplayName("Successfully finds dual type pokemon")
    void encontrarPokemonsPorTipos(){
        List<Pokemon> fireTypes = testRepos.getByType(new String[]{"fire","flying"});
        assertEquals(fireTypes.get(0).getName(), "charizard");
        assertEquals(fireTypes.get(0).getNationalDex(), 6);
    }
    @Test @DisplayName("Successfully finds a pokemon by name")
    void encontrarPokemonPorNome(){
        Pokemon blastoise = testRepos.getByName("blastoise");

        assertEquals(blastoise.getName(), "blastoise");
        assertEquals(blastoise.getNationalDex(), 9);
        assertEquals(blastoise.getTypes()[0].getName(), "water");
    }
    @Test @DisplayName("throws when trying to find non existent pokemon")
    void encontrarPokemonsInexistentes(){
        assertThrows(NotFoundException.class, ()-> testRepos.getById(999));
        assertThrows(NotFoundException.class, ()-> testRepos.getByType(new String[]{"sound"}));
        assertThrows(NotFoundException.class, ()-> testRepos.getByName("snivy"));
    }
    @Test @DisplayName("Successfully creates a pokemon")
    void criarPokemon(){
        Pokemon totodile = new Pokemon();
        totodile.setName("totodile");
        totodile.setNationalDex(158);
        totodile.setTypes(new Type[]{ new Type("water", 1) });
        totodile.setStats(new Stat[]{
                new Stat("hp", 50),
                new Stat("attack", 65),
                new Stat("defense", 64),
                new Stat("special-attack", 44),
                new Stat("special-defense", 48),
                new Stat("speed", 43),
        });
        testRepos.create(totodile);
        assertEquals(testRepos.getByName("totodile"), totodile);

        assertThrows(AlredyExistsException.class, ()-> testRepos.create(totodile));
    }
    @Test @DisplayName("throws when trying to create a existing pokemon")
    void criarPokemonJaExistente(){
        assertThrows(Exception.class, ()-> testRepos.create(testRepos.getById(1)));
    }
    @Test @DisplayName("Successfully updates a pokemon")
    void atualizarPokemon(){
        Pokemon charizard = testRepos.getByName("charizard");
        charizard.setTypes(new Type[]{
                new Type("fire", 1),
                new Type("dragon", 2)
        });

        assertEquals(testRepos.getByName("charizard"), charizard);
}
    @Test
    @DisplayName("throws when trying to update a pokemon id to an invalid id ")
    void atualizarPokemonIncorretamente() {
        Pokemon charizard = testRepos.getById(6);
        charizard.setNationalDex(4);
        assertThrows(AlredyExistsException.class, () -> testRepos.update(6, charizard));
    }
    @Test
    @DisplayName("throws when trying to update a non existent pokemon")
    void atualizarPokemonNaoExistente() {
        assertThrows(NotFoundException.class, () -> testRepos.update(999, new Pokemon()));
    }
    @Test @DisplayName("Successfully deletes a pokemon")
    void deletarPokemon(){
        Pokemon charmander = testRepos.getByName("charmander");
        testRepos.delete(charmander.getNationalDex());

        assertThrows( NotFoundException.class , ()-> testRepos.getByName("charmander"));
    }
    @Test
    @DisplayName("throws when trying to delete a non existing pokemon")
    void deletarPokemonIncorretamente() {
        assertThrows(NotFoundException.class, () -> {
            testRepos.delete(999);
        });
    }
}



