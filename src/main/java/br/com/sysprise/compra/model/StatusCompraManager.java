package br.com.sysprise.compra.model;

import br.com.sysprise.compra.infra.rabbit.model.DadosCompraMaterial;
import br.com.sysprise.compra.infra.rabbit.model.DadosMovimentacao;
import br.com.sysprise.compra.infra.rabbit.model.Movimentacao;
import br.com.sysprise.compra.model.itemcompra.ItemCompra;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StatusCompraManager {

    private static RabbitTemplate rabbitTemplate;
    private static String routingKeyCobranca;
    private static String routinkeyCompraMaterial;
    private static String exchangeName;


    @Autowired
    public StatusCompraManager(RabbitTemplate rabbitTemplate,
                               @Value("${spring.rabbitmq.exchange_name}") String exchangeName,
                               @Value("${spring.rabbitmq.routing_key_compra_material}") String routinkeyCompraMaterial,
                               @Value("${spring.rabbitmq.routing_key}")String routingKeyCobranca) {
        StatusCompraManager.rabbitTemplate = rabbitTemplate;
        StatusCompraManager.routingKeyCobranca = routingKeyCobranca;
        StatusCompraManager.exchangeName = exchangeName;
        StatusCompraManager.routinkeyCompraMaterial = routinkeyCompraMaterial;
    }

    public static void publicarMensagemCobranca(Compra compra, Movimentacao movimentacao) {
        compra.getItensDaCompra().forEach(itemCompra -> {
            MessageProperties props = new MessageProperties();
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            DadosMovimentacao dadosMovimentacao = new DadosMovimentacao(itemCompra.getProdutoId(), itemCompra.getQuantidade(), movimentacao);
            Message mensagem = rabbitTemplate.getMessageConverter().toMessage(dadosMovimentacao, props);
            rabbitTemplate.convertAndSend(exchangeName, routingKeyCobranca, mensagem);
        });
    }

    public static void publicarMensagemCompra(Compra compra) {
        MessageProperties props = new MessageProperties();
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        Map<Long, Double> itensDaCompraComQuantidade = compra.getItensDaCompra().stream().collect(Collectors.toMap(ItemCompra::getId, ItemCompra::getQuantidade));
        DadosCompraMaterial dadosCompra = new DadosCompraMaterial(compra.getFornecedorId(), itensDaCompraComQuantidade, compra.getDataDeRecebimento());
        Message mensagem = rabbitTemplate.getMessageConverter().toMessage(dadosCompra, props);
        rabbitTemplate.convertAndSend(exchangeName, routinkeyCompraMaterial, mensagem);
    }
}
