package com.ufpa.simupay.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Pedido {
    private Long id;
    private String clienteEmail;
    private String descricaoProduto;
    private BigDecimal valorTotal;

    //status da operação Aguardando pagamento, pago, cacelado
    private String status;
}
