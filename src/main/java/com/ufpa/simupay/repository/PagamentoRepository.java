package com.ufpa.simupay.repository;

import com.ufpa.simupay.model.Pagamento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PagamentoRepository {

    private final JdbcTemplate jdbcTemplate;

    public PagamentoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Gravar o histórico da transação
    public void salvar(Pagamento pagamento) {
        // Note que não inserimos o ID (é automático) nem a data (o banco põe o default)
        String sql = "INSERT INTO tb_pagamento (pedido_id, metodo, numero_cartao, status_pagamento) VALUES (?, ?, ?, ?)";
        
        jdbcTemplate.update(
            sql, 
            pagamento.getPedidoId(), 
            pagamento.getMetodo(), 
            pagamento.getNumeroCartao(), 
            pagamento.getStatusPagamento()
        );
    }
}