package br.com.sysprise.compra.model;
import java.time.LocalDate;

public record DadosListagemCompra(Long id, String documento, LocalDate dataDeRecebimento, String observacao, String status) {

    public DadosListagemCompra(Compra compra) {
        this(compra.getId(),
                compra.getDocumento(),
                compra.getDataDeRecebimento(),
                compra.getObservacao(), compra.getStatus().toString());
    }
}
