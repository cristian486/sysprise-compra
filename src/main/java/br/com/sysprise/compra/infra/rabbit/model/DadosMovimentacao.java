package br.com.sysprise.compra.infra.rabbit.model;

import java.io.Serializable;

public record DadosMovimentacao(Long produto_id, Double quantidade, Movimentacao movimentacao) implements Serializable {


}