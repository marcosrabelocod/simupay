package com.ufpa.simupay.model;

import lombok.Data;
import java.math.BigDecimal;//para lidar com dinhero de maneira precisa

@Data
public class Carteira {
    private Long id;
    private String nomeCompleto;
    private String cpg;
    private String email;
    private BigDecimal saldo;
}
