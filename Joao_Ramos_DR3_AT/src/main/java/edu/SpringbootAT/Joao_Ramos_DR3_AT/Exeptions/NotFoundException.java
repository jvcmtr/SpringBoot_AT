package edu.SpringbootAT.Joao_Ramos_DR3_AT.Exeptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super (message);
    }
}
