package br.com.sysprise.compra.service;


import br.com.sysprise.compra.model.Compra;
import br.com.sysprise.compra.model.itemcompra.DadosAtualizarItemCompra;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AtualizarItemCompraService {

    public void executar(List<DadosAtualizarItemCompra> itens, Compra compra) {

        itens.forEach(itemParaAtualizar -> {
            itemParaAtualizar.id().ifPresent(id -> {
                compra.buscarItemCompraPorId(id).ifPresent(itemCadastrado -> {
                    if (itemParaAtualizar.deveRemover())
                        compra.removerItem(itemCadastrado);
                    else
                        itemCadastrado.atualizarCadastro(itemParaAtualizar);
                });
            });
        });
    }
}
