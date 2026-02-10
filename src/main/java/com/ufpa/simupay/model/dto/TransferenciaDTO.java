package com.ufpa.simupay.model.dto;

import lombok.Data;
import java.math.BigDecimal; //fundamental para lidar com dinheiro

@Data //get e set
public class TransferenciaDTO {
    private Long pagadorId;
    private Long recebedorId;
    private BigDecimal valor;
}
