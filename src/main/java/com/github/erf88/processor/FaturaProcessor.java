package com.github.erf88.processor;

import com.github.erf88.model.in.Cartao;
import com.github.erf88.model.in.Cliente;
import com.github.erf88.model.out.Fatura;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FaturaProcessor implements ItemProcessor<Cliente, Fatura> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final LocalDate dataProcessamento = LocalDate.now();

    @Override
    public Fatura process(Cliente cliente) {
        Fatura fatura = new Fatura();
        fatura.setId(UUID.nameUUIDFromBytes(cliente.getEmail().getBytes()).toString());
        fatura.setIdCliente(cliente.getId());
        String nomeCliente = removeAcentos(cliente.getNome()) + removeAcentos(cliente.getSobrenome());
        fatura.setNomeCliente(nomeCliente);
        fatura.setEmailCliente(cliente.getEmail());
        double total = cliente.getCartoes().stream().mapToDouble(Cartao::getValor).reduce(0, Double::sum);
        fatura.setTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN));
        fatura.setDataProcessamento(dataProcessamento.format(formatter));
        LocalDate dataVencimento = dataProcessamento.plusMonths(1);
        fatura.setDataVencimento(dataVencimento.format(formatter));
        return fatura;
    }

    public static String removeAcentos(final String str) {
        String strSemAcentos = Normalizer.normalize(str, Normalizer.Form.NFD);
        return strSemAcentos.replaceAll("[^\\p{ASCII}]", "");
    }

}

