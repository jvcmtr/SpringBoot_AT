package edu.SpringbootAT.Joao_Ramos_DR3_AT.Exeptions;

public class AlredyExistsException extends RuntimeException{
    public AlredyExistsException(String message){
        super (message);
    }
}
