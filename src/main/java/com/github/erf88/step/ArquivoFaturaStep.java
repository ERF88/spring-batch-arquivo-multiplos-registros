package com.github.erf88.step;

import com.github.erf88.model.in.Cliente;
import com.github.erf88.model.out.Fatura;
import com.github.erf88.reader.ClienteCartaoReader;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("ALL")
@Configuration
public class ArquivoFaturaStep {

    @Bean
    public Step faturaStep(
            StepBuilderFactory stepBuilderFactory,
            ClienteCartaoReader clienteCartaoReader,
            ItemProcessor<Cliente, Fatura> faturaProcessor,
            FlatFileItemWriter<Fatura> faturaWriter) {

        return stepBuilderFactory
                .get("faturaStep")
                .<Cliente, Fatura>chunk(50)
                .reader(clienteCartaoReader)
                .processor(faturaProcessor)
                .writer(faturaWriter)
                .build();
    }

}
