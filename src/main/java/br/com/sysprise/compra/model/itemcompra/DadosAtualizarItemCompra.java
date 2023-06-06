package br.com.sysprise.compra.model.itemcompra;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public record DadosAtualizarItemCompra(@Min(1)
                                       @NotNull(message = "Obrigatório o envio do ID do item da compra")
                                       Optional<Long> id,
                                       Double quantidade,
                                       Boolean remover) {

    public Boolean deveRemover() {
        return this.remover != null && this.remover;
    }
}
