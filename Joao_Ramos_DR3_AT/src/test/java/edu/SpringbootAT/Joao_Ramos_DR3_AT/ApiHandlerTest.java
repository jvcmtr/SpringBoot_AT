package edu.SpringbootAT.Joao_Ramos_DR3_AT;

import edu.SpringbootAT.Joao_Ramos_DR3_AT.APIConnection.APIHandler;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.Exeptions.AlredyExistsException;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.model.Pokemon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApiHandlerTest {

    APIHandler api = new APIHandler(9);
    @Test
    @DisplayName("API sucessfully retrieves the correct information")
    void atualizarPokemonIncorretamente() {
        Pokemon[] starters = api.getAll();

        assertEquals(starters[0].getName(), "bulbasaur");
        assertEquals(starters[3].getName(), "charmander");
        assertEquals(starters[6].getName(), "squirtle");
    }
}
