package com.github.erf88.processor;

import com.github.erf88.model.in.Cliente;
import com.github.erf88.model.out.Fatura;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("ALL")
@Configuration
public class ArquivoFaturaProcessor {

    @Bean
    public ItemProcessor<Cliente, Fatura> faturaProcessor() {
        return new FaturaProcessor();
    }

}
