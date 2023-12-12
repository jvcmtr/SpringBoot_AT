package edu.SpringbootAT.Joao_Ramos_DR3_AT.utils;

import edu.SpringbootAT.Joao_Ramos_DR3_AT.APIConnection.APIHandler;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.Exeptions.AlredyExistsException;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.Exeptions.NotFoundException;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.model.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PokemonRepos implements IPokemonRepos {

    private final int maxDexNumber;
    private List<Pokemon> repos;

    public PokemonRepos(int maxDexNumber) {
        this.maxDexNumber = maxDexNumber;
        repos = init();
    }
    public PokemonRepos() {
        this.maxDexNumber = NationalDexUtils.GEN1_LAST_ID;
        repos = init();
    }

    @Override
    public void delete(int id) throws NotFoundException {
        int toRemove = -1;
        for( Pokemon p : repos ){
            if(p.getNationalDex() == id)
                toRemove = repos.indexOf(p);
        }

        if(toRemove>0){
            repos.remove(toRemove);
            log.info("Removed pokemon with National dex number: " + id);
            return;
        }

        NotFoundException e = new NotFoundException("Failed to remove pokemon with national dex number " + id + ". Pokemon not found");
        log.error(e.getMessage());
        throw e;
    }
    @Override
    public void create(Pokemon pokemon) throws AlredyExistsException {
        for(Pokemon p : repos){
            if(p.getNationalDex() == pokemon.getNationalDex()){
                AlredyExistsException e = new AlredyExistsException( "Failed to create pokemon " + pokemon.getName() +
                                                        " National dex " + pokemon.getNationalDex() +
                                                        " is already occupied by "+ p.getName());
                log.error(e.getMessage());
                throw e;
            }

            if(p.getName() == pokemon.getName()){
                AlredyExistsException e = new AlredyExistsException("Failed to create pokemon " + pokemon.getName() +
                            ". This name already exists in National dex number" + p.getNationalDex());
                log.error(e.getMessage());
                throw e;
            }
        }

        repos.add(pokemon);
        log.info("Added " + pokemon.getName() + " with National dex " + pokemon.getNationalDex() + "to the local repository" );
    }

    @Override
    public void update(int id, Pokemon pokemon) throws NotFoundException, AlredyExistsException {
        if(pokemon.getNationalDex() != id){
            for(Pokemon p : repos){
                if(p.getNationalDex() == pokemon.getNationalDex()){
                    AlredyExistsException e = new AlredyExistsException(
                                    "Failed to update pokemon. new dex number [" +
                                    pokemon.getNationalDex() + "] is occupied by " + p.getName());
                    log.error(e.getMessage());
                    throw e;
                }
            }
        }


        for(Pokemon p : repos){
            if (p.getNationalDex() == id){
                log.info("Updated pokemon\n\t OLD: " + p.toString() + "\n\t NEW: " + pokemon.toString());
                p = pokemon;
            }
        }
        
        NotFoundException e = new NotFoundException("Failed to update pokemon. National dex number " + id + "not found");
        log.error(e.getMessage());
        throw e;
    }

    @Override
    public List<Pokemon> getAll() {
        return repos;
    }

    @Override
    public Pokemon getByName(String name) throws NotFoundException {
        for (Pokemon p:repos){
            if (p.getName().equals(name)){
                return p;
            }
        }
        
        NotFoundException e = new NotFoundException("Could not find pokemon with name " + name);
        log.error(e.getMessage());
        throw e;
    }

    @Override
    public Pokemon getById(int id) throws NotFoundException {
        for (Pokemon p : repos){
            if(p.getNationalDex() == id){
                return p;
            }
        }
        NotFoundException e = new NotFoundException("could not find pokemon with NationalDex number " + id);
        log.error(e.getMessage());
        throw e;
    }

    @Override
    public List<Pokemon> getByType(String[] names) throws NotFoundException {

        List<Pokemon> found = new ArrayList<>();
        for(Pokemon p: repos) {
            boolean match = true;

            for (int i = 0; i < names.length; i++) {
                if( !p.isOfType(names[i]) )
                    match = false;
            }

            if(match) found.add(p);
        }

        if(found.size() == 0){
            NotFoundException e = new NotFoundException("could not find any pokemon with type " + names.toString());
            log.info(e.getMessage());
            throw e;
        }
        return found;
    }

    private List<Pokemon> init(){
        List<Pokemon> list = new ArrayList<>();
        APIHandler api = new APIHandler(maxDexNumber);
        Pokemon[] all = api.getAll();
        for (int i = 0; i < all.length; i++) {
            list.add(all[i]);
        }
        return list;
    }

}
