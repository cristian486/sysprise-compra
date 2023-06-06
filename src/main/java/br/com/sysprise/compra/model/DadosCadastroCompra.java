package br.com.sysprise.compra.model;


import br.com.sysprise.compra.model.itemcompra.DadosCadastroItemCompra;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record DadosCadastroCompra(@NotBlank(message = "Obrigatório o preenchimento do documento!")
                                  String documento,
                                  @Future(message = "A data de recebimento deve ser futura")
                                  @NotNull(message = "Obrigatório o envio data de recebimento")
                                  LocalDate dataDeRecebimento,
                                  @Min(1)
                                  @NotNull(message = "Obrigatório o envio do ID do fornecedor")
                                  Long pessoa_id,
                                  @Valid
                                  @NotNull(message = "Obrigatório o envio dos produtos da compra")
                                  @NotEmpty(message = "Obrigatório ao menos um produto para a compra")
                                  List<DadosCadastroItemCompra> itens) {
}
