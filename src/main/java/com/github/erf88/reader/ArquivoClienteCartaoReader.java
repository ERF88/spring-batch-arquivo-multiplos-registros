package com.github.erf88.reader;

import com.github.erf88.model.in.Cartao;
import com.github.erf88.model.in.Cliente;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
@Configuration
public class ArquivoClienteCartaoReader {

    @Value("${spring.batch.file.clientes-cartoes.in}")
    private Resource resource;

    @Bean
    public ClienteCartaoReader clienteCartaoReader(LineMapper lineMapper) {
        FlatFileItemReader reader = new FlatFileItemReaderBuilder()
                .name("clienteCartaoReader")
                .resource(resource)
                .lineMapper(lineMapper)
                .build();
        return new ClienteCartaoReader(reader);
    }

    @Bean
    public PatternMatchingCompositeLineMapper lineMapper() {
        PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper();
        lineMapper.setTokenizers(tokenizer());
        lineMapper.setFieldSetMappers(fieldSetMappers());
        return lineMapper;
    }

    private Map<String, LineTokenizer> tokenizer() {
        Map<String, LineTokenizer> tokenizers = new HashMap<>();
        tokenizers.put("cliente*", clienteLineTokenizer());
        tokenizers.put("cartao*", cartaoLineTokenizer());
        return tokenizers;
    }

    private LineTokenizer clienteLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setIncludedFields(1, 2, 3, 4, 5);
        lineTokenizer.setNames("id", "nome", "sobrenome", "email", "status");
        return lineTokenizer;
    }

    private LineTokenizer cartaoLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setIncludedFields(1, 2, 3, 4, 5);
        lineTokenizer.setNames("id", "idCliente", "numeroCartao", "tipoCartao", "valor");
        return lineTokenizer;
    }

    private Map<String, FieldSetMapper> fieldSetMappers() {
        Map<String, FieldSetMapper> fieldSetMappers = new HashMap<>();
        fieldSetMappers.put("cliente*", fieldSetMapper(Cliente.class));
        fieldSetMappers.put("cartao*", fieldSetMapper(Cartao.class));
        return fieldSetMappers;
    }

    private FieldSetMapper fieldSetMapper(Class clazz) {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(clazz);
        return fieldSetMapper;
    }

}
