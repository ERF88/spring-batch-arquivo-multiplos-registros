package com.github.erf88.model.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Fatura implements Serializable {

    private String id;
    private Integer idCliente;
    private String nomeCliente;
    private String emailCliente;
    private BigDecimal total;
    private String dataProcessamento;
    private String dataVencimento;

}
