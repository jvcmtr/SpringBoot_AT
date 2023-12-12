package edu.SpringbootAT.Joao_Ramos_DR3_AT.controller;


import edu.SpringbootAT.Joao_Ramos_DR3_AT.Exeptions.NotFoundException;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.model.Pokemon;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.utils.IPokemonRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/Pokemon")
public class PokemonController {
    @Autowired IPokemonRepos repos;

    @GetMapping
    public ResponseEntity getAll( @RequestParam(required = false ) Optional<String[]> types){
        if (types.isEmpty())
            return ResponseEntity.ok(repos.getAll());


        try{
            return ResponseEntity.ok(repos.getByType(types.get())) ;
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getById(@PathVariable int id){
        try {
            return ResponseEntity.ok( repos.getById(id) );
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity getByName(@PathVariable String name){
        try{
             return ResponseEntity.ok(repos.getByName(name));
        }
        catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable  int id, @RequestBody Pokemon pokemon){
        try{
            repos.update(id, pokemon);
        } catch ( Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(pokemon);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        try{
            repos.delete(id);
        } catch (Exception e){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Pokemon pokemon) {
        try {
            repos.create(pokemon);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
