package br.com.sysprise.compra.model;


import br.com.sysprise.compra.model.itemcompra.ItemCompra;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "documento")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documento;
    private String observacao;
    private LocalDateTime dataDeCriacao;
    private LocalDate dataDeRecebimento;
    private Long fornecedorId;
    private Boolean habilitado;
    @Enumerated(EnumType.STRING)
    private StatusCompra status;
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<ItemCompra> itensDaCompra = new ArrayList<>();

    public Compra(DadosCadastroCompra dadosCadastro, Long fornecedorId) {
        this.documento = dadosCadastro.documento();
        this.dataDeCriacao = LocalDateTime.now();
        this.dataDeRecebimento = dadosCadastro.dataDeRecebimento();
        this.fornecedorId = fornecedorId;
        this.habilitado = Boolean.TRUE;
        this.observacao = "";
        this.status = StatusCompra.AGUARDANDO_APROVACAO;
    }

    public void atualizarStatus(StatusCompra status) {
        this.status = status;
    }

    public Optional<ItemCompra> buscarItemCompraPorId(Long id) {
        return this.itensDaCompra.stream().filter(itemCadastrado -> itemCadastrado.getId().equals(id)).findFirst();
    }

    public void adicionarItem(ItemCompra itemCompra) {
        this.itensDaCompra.add(itemCompra);
    }

    public void removerItem(ItemCompra itemCompra) {
        this.itensDaCompra.remove(itemCompra);
    }

    public void atualizarCadastro(DadosAtualizarCompra dadosAtualizar) {
        LocalDate novaDataDeRecebimento = dadosAtualizar.dataDeRecebimento();
        if(novaDataDeRecebimento != null && novaDataDeRecebimento.isAfter(this.dataDeRecebimento))
            this.dataDeRecebimento = novaDataDeRecebimento;
    }

    public void desabilitarCadastro() {
        this.habilitado = Boolean.FALSE;
    }

    public void definirObservacao(String observacao) {
        this.observacao += observacao;
    }

    public void aprovar() {
        this.status.aprovar(this);
    }

    public void cancelar() {
        this.status.cancelar(this);
    }
}
