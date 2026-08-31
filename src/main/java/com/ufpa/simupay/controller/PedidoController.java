package com.ufpa.simupay.controller;

import com.ufpa.simupay.model.Pedido;
import com.ufpa.simupay.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //mostra que essa classe responde requisições da internet
@RequestMapping("/pedidos")//oendereço base da API
public class PedidoController {

    private final PagamentoService pagamentoService;

    // Injetamos o cérebro (Service) aqui dentro
    public PedidoController(PagamentoService pagamentoService){
        this.pagamentoService = pagamentoService;
    }

    @GetMapping
    public ResponseEntity<Object>mostrarLista(){
        try{
            return ResponseEntity.ok(pagamentoService.buscaGeral());
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    //usamos o protocolo get para enviar o id da compra por url e então receber um json com detalhes da compra
    @GetMapping("/{id}")
    public ResponseEntity<Object>buscarPedido(@PathVariable Long id) {
        try{
            Pedido pedido = pagamentoService.consutarPedido(id);
            return ResponseEntity.ok(pedido);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }  
}
