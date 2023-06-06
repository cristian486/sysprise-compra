package br.com.sysprise.compra.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
public record DadosAtualizarStatusCompra (@Min(1)
                                        @NotNull(message = "Obrigatório o envio do ID da venda")
                                        Long id,
                                        @NotNull(message = "Obrigatório o envio do campo de aprovação da venda")
                                        Boolean aprovar) {
}