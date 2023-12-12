package edu.SpringbootAT.Joao_Ramos_DR3_AT.APIConnection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;


@AllArgsConstructor
@NoArgsConstructor
public class ApiType {
    @Getter private int slot;
    @Getter private HashMap<String, String> type;
}
