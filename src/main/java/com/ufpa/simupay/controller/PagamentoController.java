package com.ufpa.simupay.controller;

import com.ufpa.simupay.model.CheckoutRequestDTO;
import com.ufpa.simupay.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController //mostra que essa classe responde requisições da internet
@RequestMapping("/pagamentos")//oendereço base da API
@CrossOrigin(origins = "http://localhost:4200")
public class PagamentoController {
    
    private final PagamentoService pagamentoService;

    // Injetamos o cérebro (Service) aqui dentro
    public PagamentoController(PagamentoService pagamentoService){
        this.pagamentoService = pagamentoService;
    }

    //metodo POST para proteger dados sensiveis 
    @PostMapping("/checkout")
    public ResponseEntity<String> realizaCheckout(@RequestBody CheckoutRequestDTO request){
        try{
            //recebe o JSON para o sistema processar
            String resultado = pagamentoService.processarCheckout(request);

            //caso estejá tudo correto retorna Status 200 (OK) como mensagem
            return ResponseEntity.ok(resultado);
        
        }catch (RuntimeException e) {
            // Se o Service lançar um erro (ex: Pedido já pago), cai aqui. Retorna Status 400.
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
}
