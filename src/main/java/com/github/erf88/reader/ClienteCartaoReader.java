package com.github.erf88.reader;

import com.github.erf88.model.in.Cartao;
import com.github.erf88.model.in.Cliente;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

public class ClienteCartaoReader implements ItemStreamReader<Cliente> {

    private Object objetoAtual;
    private final ItemStreamReader<Object> delegate;
    public ClienteCartaoReader(ItemStreamReader<Object> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }

    @Override
    public Cliente read() throws Exception {

        if(objetoAtual == null) {
            objetoAtual = delegate.read();
        }

        Cliente cliente = (Cliente) objetoAtual;
        objetoAtual = null;

        if(cliente != null) {
            while(peek() instanceof Cartao) {
                cliente.getCartoes().add((Cartao) objetoAtual);
            }
        }

        return cliente;
    }

    private Object peek() throws Exception {
        objetoAtual = delegate.read();
        return objetoAtual;
    }
    
}
