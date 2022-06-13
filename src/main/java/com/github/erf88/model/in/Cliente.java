package com.github.erf88.model.in;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Cliente implements Serializable {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String email;
    private String status;
    private String dataProcessamento;
    @Setter(AccessLevel.NONE)
    private List<Cartao> cartoes = new ArrayList<>();

}
