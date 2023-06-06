package br.com.sysprise.compra.infra.rabbit.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.Map;

public record DadosCompraMaterial(Long pessoaId,
                                  Map<Long, Double> produtoQuantidade,
                                  @JsonSerialize(using = LocalDateSerializer.class)
                                  LocalDate dataDeRecebimento) {
}
