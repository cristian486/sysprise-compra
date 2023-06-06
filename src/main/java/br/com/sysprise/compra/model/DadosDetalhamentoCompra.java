package br.com.sysprise.compra.model;


import br.com.sysprise.compra.model.itemcompra.DadosDetalhamentoItemCompra;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoCompra(Long id, String documento, String observacao, LocalDateTime dataDeCriacao, LocalDate dataDeRecebimento,
                                      String status, List<DadosDetalhamentoItemCompra> itensDaCompra) {

    public DadosDetalhamentoCompra(Compra compra, List<DadosDetalhamentoItemCompra> itensDaCompra) {
        this(compra.getId(), compra.getDocumento(), compra.getObservacao(), compra.getDataDeCriacao(), compra.getDataDeRecebimento(),
                compra.getStatus().toString(), itensDaCompra);
    }
}
