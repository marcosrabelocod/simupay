package com.ufpa.simupay.service;

import com.ufpa.simupay.model.CheckoutRequestDTO;
import com.ufpa.simupay.model.Pagamento;
import com.ufpa.simupay.model.Pedido;
import com.ufpa.simupay.repository.PagamentoRepository;
import com.ufpa.simupay.repository.PedidoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {
    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;

    // O Spring injeta os dois repositórios que criamos
    public PagamentoService(PedidoRepository pedidoRepository, PagamentoRepository pagamentoRepository){
        this.pedidoRepository = pedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
    }
    
    //O @Transactional garante que se o banco cair no meio, nada será salvo pela metade
    @Transactional
    public String processarCheckout(CheckoutRequestDTO request){
        //1 buscar pedido no banco
        Pedido pedido = pedidoRepository.buscarPorId(request.getPedidoId()).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        //2 Verifica se o pedido já foi pago (anti cobrança dupla)
        if (pedido.getStatus().equals("Pago")){
            throw new RuntimeException("Este pedido já foi pago");
        }

        //3 Monta o historico de pagamento para salvar no banco
        Pagamento pagamento = new Pagamento();
        pagamento.setPedidoId(pedido.getId());
        pagamento.setMetodo(request.getMetodo());
        pagamento.setNumeroCartao(request.getNumeroCartao());

        // 4 A simulação do gateway de pagamento 

        boolean aprovado = false;

        if (request.getMetodo().equalsIgnoreCase("PIX")){
            //se for pix aprova na hora
            aprovado = true;
            pagamento.setStatusPagamento("APROVADO");

        }else if (request.getMetodo().equalsIgnoreCase("Cartão")){
            // A Regra é clara(inventada): Se o cartão começa com 5555 então passa
            if (request.getNumeroCartao() != null && request.getNumeroCartao().startsWith("5555")){
                aprovado = true;
                pagamento.setStatusPagamento("Aprovado");
            
            }else{
                pagamento.setStatusPagamento("Recusado: numero do cartão invalido");
            }
        }else{
            throw new RuntimeException("Método de pagamento não suportado");
        }

        //5 Salvar historico no banco de dados
        pagamentoRepository.salvar(pagamento);

        if (aprovado) {
            pedidoRepository.atualizarStatus(pedido.getId(), "Pago");
            return "Sucesso! Pagamento aprovado";

        }else{
            return "Falha! pagamento não aprovado";
        }
    }
}
