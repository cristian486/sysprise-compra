package br.com.sysprise.compra.service;


import br.com.sysprise.compra.model.Compra;
import br.com.sysprise.compra.model.DadosCadastroCompra;
import br.com.sysprise.compra.model.itemcompra.DadosCadastroItemCompra;
import br.com.sysprise.compra.model.itemcompra.ItemCompra;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pb.PessoaId;
import pb.PessoaServiceGrpc;
import pb.ProdutoId;
import pb.ProdutoServiceGrpc;

@Component
public class GerarCompra {

    @GrpcClient("pessoa")
    private PessoaServiceGrpc.PessoaServiceBlockingStub pessoaStub;

    @Autowired
    private GerarItemCompra gerarItemCompra;


    public Compra executar(DadosCadastroCompra dadosCadastro) {
        boolean pessoaNaoExiste = !verificarSePessoaExiste(dadosCadastro);

        if(pessoaNaoExiste)
            throw new IllegalArgumentException("O ID informado para o fornecedor é inválido!");

        Compra compra = new Compra(dadosCadastro, dadosCadastro.pessoa_id());
        dadosCadastro.itens().forEach(itemCompra -> gerarItemCompra.executar(compra, itemCompra));
        return compra;
    }


    private Boolean verificarSePessoaExiste(DadosCadastroCompra dadosCadastro) {
        return pessoaStub.verifyExistence(PessoaId.newBuilder().setId(dadosCadastro.pessoa_id()).build()).getExiste();
    }
}
