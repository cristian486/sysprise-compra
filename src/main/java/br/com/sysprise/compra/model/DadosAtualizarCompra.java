package br.com.sysprise.compra.model;

import br.com.sysprise.compra.model.itemcompra.DadosAtualizarItemCompra;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record DadosAtualizarCompra(@Min(1)
                                   @NotNull(message = "Obrigatório o envio do ID da compra")
                                   Long id,
                                   @Future
                                   LocalDate dataDeRecebimento,
                                   @Valid
                                   Optional<List<DadosAtualizarItemCompra>> itens) {
}
