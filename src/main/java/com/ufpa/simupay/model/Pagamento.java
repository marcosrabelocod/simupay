package com.ufpa.simupay.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Pagamento {
    private Long id;
    private Long pedidoId;
    private String metodo; //pix, debito credito
    private String numeroCartao;
    
    //Processando, Aprovado, Recusado
    private String statusPagamento;
    private LocalDateTime dataHora;
}
