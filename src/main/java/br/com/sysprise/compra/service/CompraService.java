package br.com.sysprise.compra.service;


import br.com.sysprise.compra.model.*;
import br.com.sysprise.compra.model.itemcompra.DadosDetalhamentoItemCompra;
import br.com.sysprise.compra.model.itemcompra.ItemCompra;
import br.com.sysprise.compra.repository.CompraRepository;
import jakarta.persistence.EntityNotFoundException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pb.ProdutoId;
import pb.ProdutoServiceGrpc;

import java.util.List;

@Service
public class CompraService {

    @GrpcClient("produto")
    private ProdutoServiceGrpc.ProdutoServiceBlockingStub produtoStub;

    @Autowired
    private GerarCompra gerarCompra;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private AtualizarItemCompraService atualizarItemCompraService;

    public Compra findCompraById(Long id) {
        return compraRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A compra requisitada não foi encontrada"));
    }

    public Page<DadosListagemCompra> listar(Pageable pageable) {
        return compraRepository.findAllByHabilitadoTrue(pageable).map(DadosListagemCompra::new);
    }

    public DadosDetalhamentoCompra detalhar(Long id) {
        Compra compra = this.findCompraById(id);
        List<DadosDetalhamentoItemCompra> listaDosItensDaCompra = this.gerarListaItensDetalhamentoDaCompra(compra.getItensDaCompra());
        return new DadosDetalhamentoCompra(compra, listaDosItensDaCompra);
    }

    public DadosDetalhamentoCompra cadastrar(DadosCadastroCompra dadosCadastro) {
        Compra compra = gerarCompra.executar(dadosCadastro);
        compraRepository.save(compra);
        List<DadosDetalhamentoItemCompra> listaDosItensDaCompra = this.gerarListaItensDetalhamentoDaCompra(compra.getItensDaCompra());
        return new DadosDetalhamentoCompra(compra, null);
    }

    public DadosDetalhamentoCompra atualizar(DadosAtualizarCompra dadosAtualizar) {
        Compra compra = this.findCompraById(dadosAtualizar.id());
        compra.atualizarCadastro(dadosAtualizar);
        dadosAtualizar.itens().ifPresent(itensAtualizar -> atualizarItemCompraService.executar(itensAtualizar, compra));
        List<DadosDetalhamentoItemCompra> listaDosItensDaCompra = this.gerarListaItensDetalhamentoDaCompra(compra.getItensDaCompra());
        return new DadosDetalhamentoCompra(compra, listaDosItensDaCompra);
    }

    public void deletar(Long id) {
        Compra compra = this.findCompraById(id);
        boolean compraAindaAguardandoAprovacao = compra.getStatus().toString().equals(StatusCompra.AGUARDANDO_APROVACAO.toString());

        if(compraAindaAguardandoAprovacao) {
            compraRepository.delete(compra);
            return;
        }

        compra.desabilitarCadastro();
    }

    private List<DadosDetalhamentoItemCompra> gerarListaItensDetalhamentoDaCompra(List<ItemCompra> itensDaCompra) {
        return itensDaCompra.stream().map(itemDaCompra -> {
            ProdutoId produtoId = ProdutoId.newBuilder().setProdutoId(itemDaCompra.getProdutoId()).build();
            String nomeDoProduto = produtoStub.getProductname(produtoId).getNome();
            return new DadosDetalhamentoItemCompra(itemDaCompra.getId(), nomeDoProduto, itemDaCompra.getQuantidade());
        }).toList();
    }

    public void atualizarStatus(DadosAtualizarStatusCompra dadosAtualizar) {
        Compra compra = this.findCompraById(dadosAtualizar.id());

        if(dadosAtualizar.aprovar()) {
            compra.aprovar();
        } else {
            compra.cancelar();
        }
    }
}
