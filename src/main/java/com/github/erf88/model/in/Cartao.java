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
public class Cartao implements Serializable {

    private String id;
    private Integer idCliente;
    private String numeroCartao;
    private String tipoCartao;
    private Double valor;

}
