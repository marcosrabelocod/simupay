package com.ufpa.simupay.model;

import lombok.Data;

@Data
public class CheckoutRequestDTO {
    private Long pedidoId;
    private String metodo;

    
    private String numeroCartao;
    private String cvv;
}
