package com.ufpa.simupay.repository;

import com.ufpa.simupay.model.Carteira;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class CarteiraRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public CarteiraRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //buscar todas as carteiras para listar
    public List<Carteira>buscarTodas(){
        String sql = "SELECT * FROM tb_carteira";
        return jdbcTemplate.query(sql, new CarteiraRowMapper());
    }

    //busca Por Id
    public Optional<Carteira>buscarPorId(Long id){
        String sql = "SELECT * FROM tb_carteira WHERE id = ?";
        try{
            Carteira carteira = jdbcTemplate.queryForObject(sql, new CarteiraRowMapper(), id);
            return Optional.ofNullable(carteira);
        }catch(Exception e){
            return Optional.empty();//retorna uma celula vazia se não achar
        }
    }


    //Tradutor dos comandos sql(RowMapper)
    private static class CarteiraRowMapper implements RowMapper<Carteira>{
        @Override
        public Carteira mapRow(ResultSet rs, int rowNum) throws SQLException{
            Carteira c = new Carteira();
            c.setId(rs.getLong("id"));
            c.setNomeCompleto(rs.getString("nome_completo"));
            c.setCpg(rs.getString("cpf"));
            c.setEmail(rs.getString("email"));
            c.setSaldo(rs.getBigDecimal("saldo")); // O Segredo do dinheiro!
            return c;
        }
    }

    //atualizar saldo após operação
    public void atualizarSaldo(Carteira carteira){
        String sql = "UPDATE tb_carteira SET saldo = ? WHERE id  = ?";

        jdbcTemplate.update(sql, carteira.getSaldo(), carteira.getId());
    }
}












