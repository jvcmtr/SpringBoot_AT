package edu.SpringbootAT.Joao_Ramos_DR3_AT.APIConnection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;


@AllArgsConstructor
@NoArgsConstructor
public class ApiStat {

    @Getter
    private int base_stat;
    @Getter private int effort;
    @Getter private HashMap<String, String> stat;
}
