# **Controlador**

## **Descrição**

Solução para controle de sondas em múltiplos planetas.
Todos os planetas têm tamanho 5x5.
As sondas estarão posicionadas em um dado planeta como num plano cartesiano, ou seja, através de (posicaoX, posicaoY) e podem estar apondatas paras as direções N, S, E e W que representam os pontos cardeias norte, sul, leste e oeste, respectivamente.
Para mover uma sonda, utilizaremos os comandos R, L e M, **R**ight: rotação de noventa graus no sentido horário, **L**eft: idem para o sentido anti-horário e **M**over: mover de um na direção em que a sonda aponta.

Exemplo:
> **Controlador** é uma API RESTful desenvolvida em Java + Spring Boot, documentada por Swagger que inclui no controlador planetas, aterrissa e move sondas, com o cuidado de não sobrepô-las, ou seja, duas sondas não podem colidir, uma sonda não pode aterrissar num planeta que não esteja já registrado no controlador e a sonda não pode ser movida para o espaço, i.e., extravasar a dimensão de 5x5.
> Applicar o comando MMMRR numa sonda (1,2) apontando para oeste resulta (1,-1) apontando para o leste.

## **Recursos**
- Java 17
- Spring Boot 3.3.2
- Banco de Dados H2
- Docker

## **Instalação**

### **Pré-requisitos**
- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [Docker](https://docs.docker.com/get-docker/)

### **Passos para Rodar a Aplicação Localmente**

1. Clone o repositório:

    ```bash
    git clone https://github.com/rianemelo/controlador.git
    cd controlador
    ```

2. Compile o projeto:

    ```bash
    mvn clean install
    ```

3. Execute a aplicação:

    ```bash
    mvn spring-boot:run
    ```

    A API estará disponível em `http://localhost:8080`.

### **Rodando com Docker**

1. Construa a imagem Docker:

    ```bash
    docker build -t <<nome-da-sua-imagem>> .
    ```

2. Execute o contêiner Docker:

    ```bash
    docker run -p 8080:8080 <<nome-da-sua-imagem>>
    ```

    A API estará disponível em `http://localhost:8080`.

## **Endpoints da API**

- **GET /api/planetas**: Retorna a lista de todos os planetas.
- **GET /api/planetas/{planetaId}**: Retorna um determinado planeta a partir de seu ID.
- **GET /api/planetas/{planetaId}/sondas**: Retorna todas as sondas que estão no planeta a partir do ID do planeta.
- **GET /api/planetas/{planetaId}/sondas/{sondaId}**: Retorna uma determinada sonda através dos id's do planeta e da sonda.
- **POST /api/planetas**: Inclui um planeta no controlador.
- **POST /api/planetas/{planetaID}/sondas**: Inclui uma sonda em um determinado planeta.
- **PUT /api/planetas/{planetaId}**: Renomeia um planeta.
- **PUT /api/planetas/sondas**: Movimenta a sonda, dada sua posição e o planeta em que está, através de uma sequencia de comandos _R_, _L_ e _M_, 
- **DELETE /api/planetas/{planetaId}**: Detona um planeta (a partir do ID), e consequentemente todas as eventuais sondas que estiverem nele.
- **DELETE /api/planetas/{planetaId}/sondas**: Detona a sonda dada a sua posição e planeta em que está.



### **Banco de Dados**
A aplicação utiliza o banco de dados H2 em memória. As configurações do banco de dados podem ser encontradas no arquivo `application.properties`:

