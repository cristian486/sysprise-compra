package br.com.sysprise.compra.model;

import br.com.sysprise.compra.infra.rabbit.model.Movimentacao;

public enum StatusCompra {
    AGUARDANDO_APROVACAO("Aguardando Aprovação") {
        @Override
        public void aprovar(Compra compra) {
            StatusCompraManager.publicarMensagemCompra(compra);
            compra.atualizarStatus(AGUARDANDO_RECEBIMENTO);
        }

        @Override
        public void cancelar(Compra compra) {
            compra.definirObservacao("A compra foi cancelada!");
            compra.atualizarStatus(CANCELADO);
        }
    }, AGUARDANDO_RECEBIMENTO("Aguardando Recebimento") {
        @Override
        public void aprovar(Compra compra) {
            compra.atualizarStatus(CONFERENCIA);
        }

        @Override
        public void cancelar(Compra compra) {
            compra.definirObservacao("A compra foi cancelada pelo fornecedor!");
            compra.atualizarStatus(CANCELADO);
        }
    }, CONFERENCIA("Conferência") {
        @Override
        public void aprovar(Compra compra) {
            StatusCompraManager.publicarMensagemCobranca(compra, Movimentacao.ENTRADA);
            compra.atualizarStatus(ARMAZENADO);
        }

        @Override
        public void cancelar(Compra compra) {
            compra.definirObservacao("Os produtos da compra contém divergências do que foi pedido");
            compra.atualizarStatus(DIVERGENCIAS);
        }
    }, DIVERGENCIAS("Divergência"), CANCELADO("Cancelado"), ARMAZENADO("Finalizado");

    private final String nome;

    StatusCompra(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    public void aprovar(Compra compra) {
        throw new RuntimeException("Não é possível mudar o status da compra");
    }

    public void cancelar(Compra compra) {
        throw new RuntimeException("Não é possível mudar o status da compra");
    }
}
