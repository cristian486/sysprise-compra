package br.com.sysprise.compra.model.itemcompra;

import br.com.sysprise.compra.model.Compra;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"compra", "produtoId"})
public class ItemCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Compra compra;
    private Long produtoId;
    private Double quantidade;

    public void atualizarCadastro(DadosAtualizarItemCompra item) {
        if(item.quantidade() != null && item.quantidade() > 0.000d)
            this.quantidade = item.quantidade();
    }
}
