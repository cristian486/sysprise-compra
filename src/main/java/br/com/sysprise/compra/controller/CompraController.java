package br.com.sysprise.compra.controller;


import br.com.sysprise.compra.model.*;
import br.com.sysprise.compra.service.CompraService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;



@CrossOrigin
@RestController
@RequestMapping("/compra")
@AllArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @GetMapping
    public ResponseEntity<Page<DadosListagemCompra>> listar(@PageableDefault(sort = "id", size = 5) Pageable pageable) {
        Page<DadosListagemCompra> dadosListagem = compraService.listar(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(dadosListagem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCompra> detalhar(@PathVariable("id") Long id) {
        DadosDetalhamentoCompra dadosDetalhamento = compraService.detalhar(id);
        return ResponseEntity.status(HttpStatus.OK).body(dadosDetalhamento);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCompra> cadastrar(@RequestBody @Valid DadosCadastroCompra dadosCadastro, UriComponentsBuilder componentsBuilder) {
        DadosDetalhamentoCompra dadosDetalhamento = compraService.cadastrar(dadosCadastro);
        URI uri = componentsBuilder.path("/compra/{id}").buildAndExpand(dadosDetalhamento.id()).toUri();
        return ResponseEntity.created(uri).body(dadosDetalhamento);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCompra> atualizar(@RequestBody @Valid DadosAtualizarCompra dadosAtualizar) {
        DadosDetalhamentoCompra dadosDetalhamento = compraService.atualizar(dadosAtualizar);
        return ResponseEntity.status(HttpStatus.OK).body(dadosDetalhamento);
    }

    @PatchMapping
    @Transactional
    public ResponseEntity<?> atualizarStatusVenda(@RequestBody @Valid DadosAtualizarStatusCompra dadosAtualizar) {
        compraService.atualizarStatus(dadosAtualizar);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        compraService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
