package edu.SpringbootAT.Joao_Ramos_DR3_AT.utils;

import edu.SpringbootAT.Joao_Ramos_DR3_AT.Exeptions.AlredyExistsException;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.Exeptions.NotFoundException;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.model.Pokemon;

import java.util.List;

public interface IPokemonRepos {
    void delete(int id) throws NotFoundException;
    void create(Pokemon pokemon) throws AlredyExistsException;
    void update(int id, Pokemon pokemon) throws NotFoundException, AlredyExistsException;
    List<Pokemon> getAll();
    Pokemon getByName(String name) throws NotFoundException;
    Pokemon getById(int id) throws NotFoundException;
    List<Pokemon> getByType(String[] names) throws NotFoundException;
}
