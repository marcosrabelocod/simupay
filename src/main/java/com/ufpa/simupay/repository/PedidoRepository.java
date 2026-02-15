package com.ufpa.simupay.repository;

import com.ufpa.simupay.model.Pedido;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class PedidoRepository {

    private final JdbcTemplate jdbcTemplate;

    public PedidoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. Buscar o pedido para conferir antes de pagar
    public Optional<Pedido> buscarPorId(Long id) {
        String sql = "SELECT * FROM tb_pedido WHERE id = ?";
        try {
            Pedido pedido = jdbcTemplate.queryForObject(sql, new PedidoRowMapper(), id);
            return Optional.ofNullable(pedido);
        } catch (Exception e) {
            return Optional.empty(); // Se o cara tentar pagar o pedido 999 que não existe, não quebra a API
        }
    }

    // 2. Atualizar o status após o pagamento dar certo (ou errado)
    public void atualizarStatus(Long id, String novoStatus) {
        String sql = "UPDATE tb_pedido SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, novoStatus, id);
    }

    // --- O Tradutor ---
    private static class PedidoRowMapper implements RowMapper<Pedido> {
        @Override
        public Pedido mapRow(ResultSet rs, int rowNum) throws SQLException {
            Pedido p = new Pedido();
            p.setId(rs.getLong("id"));
            p.setClienteEmail(rs.getString("cliente_email"));
            p.setDescricaoProduto(rs.getString("descricao_produto"));
            p.setValorTotal(rs.getBigDecimal("valor_total"));
            p.setStatus(rs.getString("status"));
            return p;
        }
    }
}