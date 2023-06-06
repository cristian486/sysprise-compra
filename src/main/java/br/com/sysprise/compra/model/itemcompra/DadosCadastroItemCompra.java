package br.com.sysprise.compra.model.itemcompra;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroItemCompra(@Min(1)
                                      @NotNull(message = "Obrigatório o envio do ID do produto")
                                      Long produto_id,
                                      @NotNull(message = "Obrigatório o envio da quantidade do produto")
                                      Double quantidade) {
}
