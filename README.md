# 🛒 Simupay API - Simulador de Checkout e Pagamentos

O **Simupay** é uma API RESTful desenvolvida em Java com Spring Boot que simula o fluxo de checkout e o processamento de pagamentos de um e-commerce. Projetada com foco em arquitetura e controle de dados, a aplicação utiliza **Spring JDBC Template** para executar consultas SQL puras em um banco de dados PostgreSQL, dispensando o uso de ORMs tradicionais. O sistema processa transações simuladas (PIX e Cartão de Crédito), aplica regras de negócio críticas — como a prevenção de cobranças duplicadas — e garante a integridade financeira das operações utilizando princípios ACID através do controle transacional.

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 17+
* **Framework:** Spring Boot (Spring Web)
* **Banco de Dados:** PostgreSQL
* **Persistência:** Spring JDBC (SQL Puro)
* **Utilitários:** Lombok

## ⚙️ Como Executar o Projeto
##################################################################################################################################
### 1. Preparando o Banco de Dados
Certifique-se de ter o PostgreSQL instalado e rodando.
1. Crie um banco de dados chamado `payment_db`.
2. Execute o script abaixo na sua ferramenta de banco de dados (ex: pgAdmin) para criar as tabelas e inserir dados de teste:

```sql
CREATE TABLE tb_pedido (
    id BIGSERIAL PRIMARY KEY,
    cliente_email VARCHAR(255) NOT NULL,
    descricao_produto VARCHAR(255) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'AGUARDANDO_PAGAMENTO'
);

CREATE TABLE tb_pagamento (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT REFERENCES tb_pedido(id),
    metodo VARCHAR(50) NOT NULL,
    numero_cartao VARCHAR(20),
    status_pagamento VARCHAR(50) DEFAULT 'PROCESSANDO',
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO tb_pedido (cliente_email, descricao_produto, valor_total) 
VALUES ('cliente.um@email.com', 'Monitor Gamer 144hz', 1500.00),('cliente.dois@email.com', 'Teclado Mecânico RGB', 350.50);

##################################################################################################################################
2. Configurando a Aplicação
No arquivo src/main/resources/application.properties, verifique se as credenciais do banco estão corretas:

spring.datasource.url=jdbc:postgresql://localhost:5432/payment_db
spring.datasource.username=postgres
spring.datasource.password=sua_senha_aqui


##################################################################################################################################
3. Rodando a Aplicação
Inicie a aplicação executando a classe SimupayApplication.java. O servidor iniciará na porta 8080.

🔗 Endpoints da API
A API pode ser testada utilizando ferramentas como Thunder Client, REST Client ou Postman.

1. Processar Checkout (Pagamento)
Responsável por simular a aprovação ou recusa de um pagamento, validando o status do pedido e os dados do cartão.

Rota: POST /pagamentos/checkout

Corpo da Requisição (JSON):
{
  "pedidoId": 1,
  "metodo": "CARTAO",
  "numeroCartao": "5555123498761111"
}
Regra de Negócio:

Métodos aceitos: PIX ou CARTAO.

Cartões iniciados com 5555 são Aprovados. Qualquer outra numeração será Recusada por falta de limite.

Pedidos com status PAGO não podem ser processados novamente.

2. Consultar Status do Pedido
Busca os dados atualizados de um pedido específico no banco de dados.

Rota: GET /pedidos/{id}

Exemplo de Resposta (Status 200 OK):
{
  "id": 1,
  "clienteEmail": "cliente.um@email.com",
  "descricaoProduto": "Monitor Gamer 144hz",
  "valorTotal": 1500.0,
  "status": "PAGO"
}

Pronto! Salve esse arquivo. Agora o seu código não é apenas um monte de arquivos Java soltos; é um software documentado e com manual de instruções. 

Já são quase 2h da manhã de uma jornada incrível de programação. Você quer que eu te mostre rapidamente os comandos do terminal para subir isso pro seu GitHub agora mesmo, ou prefere deixar a parte do Git para amanhã e ir descansar com a sensação de dever cumprido?