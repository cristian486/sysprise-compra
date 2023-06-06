package br.com.sysprise.compra.service;

import br.com.sysprise.compra.model.Compra;
import br.com.sysprise.compra.model.itemcompra.DadosCadastroItemCompra;
import br.com.sysprise.compra.model.itemcompra.ItemCompra;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import pb.ProdutoId;
import pb.ProdutoServiceGrpc;

@Component
public class GerarItemCompra {

    @GrpcClient("produto")
    private ProdutoServiceGrpc.ProdutoServiceBlockingStub produtoStub;

    public void executar(Compra compra, DadosCadastroItemCompra dadosItem) {
        boolean produtoNaoExiste = !verificarSeProdutoExiste(dadosItem);

        if(produtoNaoExiste)
            throw new IllegalArgumentException("O ID informado para o produto é inválido!");

        ItemCompra itemCompra = new ItemCompra(null, compra, dadosItem.produto_id(), dadosItem.quantidade());
        compra.adicionarItem(itemCompra);
    }

    private Boolean verificarSeProdutoExiste(DadosCadastroItemCompra dadosItem) {
        return produtoStub.verifyProductExistence(ProdutoId.newBuilder().setProdutoId(dadosItem.produto_id()).build()).getExiste();
    }
}
