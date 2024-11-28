<h2>Microserviço customer wallet</h2>

<p align="center">
 <a href="#Objetivo">Objetivo</a> •
 <a href="#Tecnologias">Tecnologias</a> •
 <a href="#Como rodar">Como rodar</a> •
 <a href="#Rotas">Rotas</a> 
</p>

## Objetivo
Esse microserviço tem como objetivo principal gerir o saldo dos usuários, controlando quando pode ou não haver uma compra de determinado papel

## Tecnologias
Para o desenvolvimento desse projeto, foi utilizado as seguintes tecnologias:
 - Java 17, com springboot framework
 - Banco de dados MySQL

## Como rodar
Para rodar esse serviço, devemos primeiramente ter um serviço kafka rodando, para isso, podemos executar o seguinte docker-compose
```bash
services:
  kafka:
    container_name: kafka
    image: apache/kafka:3.9.0
    ports:
      - 9092:9092
```
Tendo o serviço docker funcional, podemos executar o docker-compose do projeto para subirmos o banco mySQL
```
docker-compose up -d
```
Após, basta executarmos as migrations para configurar o banco, e rodarmos a class Main com o profile development

## Rotas
O projeto dispoe das seguintes rotas, que podem ser testadas pelo ([swagger](http://localhost:8081/api/swagger-ui/index.html))

### GET /wallets
- Essa rota serve para listarmos todas as carteiras cadastradas

### POST /wallets
- Rota disponibilizada para criarmos uma wallet para determinado customer, passado no corpo da requisição

### GET /wallets/{customerId}
- Rota utilizada para buscar a carteira de determinado customer. Retorna o balanço disponivel do usuário
