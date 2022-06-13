package com.github.erf88.writer;

import com.github.erf88.model.out.Fatura;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;

@SuppressWarnings("ALL")
@Configuration
public class ArquivoFaturaWriter {

    public static final String FORMATADOR = "%-40s %-20s %-40s %-40s %-20s %-20s %-20s";
    private int quantidadeLinhas;
    private BigDecimal valorTotal = BigDecimal.ZERO;
    @Value("${spring.batch.file.faturas.out}")
    private Resource faturasResource;

    @Bean
    public FlatFileItemWriter<Fatura> faturaWriter() {
        return new FlatFileItemWriterBuilder<Fatura>()
                .name("cartaoWriter")
                .resource(faturasResource)
                .headerCallback(cabecalho -> headerCallback(cabecalho))
                .lineAggregator(linha -> lineAggregator(linha))
                .footerCallback(rodape -> footerCallback(rodape))
                .build();
    }
    private void headerCallback(Writer writer) throws IOException {
        String header = String.format(
                FORMATADOR,
                "ID",
                "ID_CLIENTE",
                "NOME_CLIENTE",
                "EMAIL_CLIENTE",
                "TOTAL",
                "DATA_PROCESSAMENTO",
                "DATA_VENCIMENTO");
        writer.write(header);
    }

    private String lineAggregator(Fatura fatura) {
        quantidadeLinhas++;
        valorTotal = valorTotal.add(fatura.getTotal());
        String line = String.format(
                FORMATADOR,
                fatura.getId(),
                fatura.getIdCliente().toString(),
                fatura.getNomeCliente(),
                fatura.getEmailCliente(),
                fatura.getTotal().toString(),
                fatura.getDataProcessamento(),
                fatura.getDataVencimento()
        );

        return line;
    }

    private void footerCallback(Writer writer) throws IOException {
        writer.write("QUANTIDADE_LINHAS: " + quantidadeLinhas);
        writer.write(System.lineSeparator());
        writer.write("VALOR_TOTAL: R$ " + valorTotal);
    }

}
