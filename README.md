# Sistema de Apoio a Testes Exploratórios (API)

**Desenvolvimento de Software para a Web 1**

Diogo Conforti Vaz Bellini<br>
João Paulo Morais Rangel<br>
Luiz Fellipe de Lima Barbosa<br>
Pedro Vinicius Ferreira Santos<br>

---

## 📜 Descrição do Projeto

Este projeto consiste no desenvolvimento de um **backend REST API** utilizando Spring Boot. A API fornece a estrutura necessária para um sistema de apoio a testes exploratórios (TE) em video games, permitindo o gerenciamento de usuários, projetos, estratégias de teste e o ciclo de vida de sessões de teste.

Esta é uma evolução de uma aplicação web tradicional, refatorada para uma arquitetura de API desacoplada que pode ser consumida por qualquer cliente moderno.

---

## 🛠️ Tecnologias Utilizadas

- **Backend**: Spring Boot 3.5.0, Spring Security, Spring Data JPA
- **Linguagem**: Java 21
- **Banco de Dados**: MySQL
- **Build**: Maven
- **Controle de versão**: Git (GitHub)

---

## ✨ Principais Funcionalidades da API

A API está organizada em torno de três perfis de usuário: Administrador, Testador e Visitante.

-   **Administradores**:
    -   CRUD completo de Usuários (Administradores e Testadores).
    -   CRUD completo de Projetos (incluindo associação de membros).
    -   CRUD completo de Estratégias de Teste.
-   **Testadores**:
    -   Visualização dos projetos aos quais está associado.
    -   Gerenciamento completo do ciclo de vida de Sessões de Teste.
-   **Visitantes (Não autenticado)**:
    -   Listagem de todas as Estratégias de Teste disponíveis.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

-   Java Development Kit (JDK) 21 ou superior
-   Apache Maven 3.6+
-   Um servidor de banco de dados MySQL em execução

### 1. Configuração do Banco de Dados

O projeto está pré-configurado para se conectar a um banco de dados MySQL local. Verifique e, se necessário, altere o arquivo `src/main/resources/application.properties` com as suas credenciais.

```properties
# MYSQL
spring.datasource.url=jdbc:mysql://localhost:3306/projetoDSW?createDatabaseIfNotExist=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.username=root
spring.datasource.password=root

# JPA
spring.jpa.hibernate.ddl-auto=update
```

A propriedade `createDatabaseIfNotExist=true` tentará criar o banco `projetoDSW` automaticamente, mas é uma boa prática criá-lo manualmente antes de executar a aplicação.

### 2. Build e Execução

O projeto utiliza o Maven Wrapper, que facilita a execução sem a necessidade de uma instalação manual do Maven.

1.  Clone o repositório:
    ```bash
    git clone https://github.com/jpmrangel/game-testing-support-api.git
    cd game-testing-support-api
    ```
2.  Execute o projeto:

    -   No Linux ou macOS:
        ```bash
        ./mvnw spring-boot:run
        ```
    -   No Windows (CMD ou PowerShell):
        ```bash
        mvnw.cmd spring-boot:run
        ```
3.  A API estará em execução e pronta para receber requisições em `http://localhost:8080`.

---

## 🔌 Interagindo com a API

A API utiliza **HTTP Basic Authentication** para proteger seus endpoints. Para testar e interagir, recomendamos o uso de uma ferramenta como o **Postman**, **Insomnia** ou `curl`.

### Usuários Padrão para Testes

O sistema é iniciado com dois usuários para facilitar os testes:

-   **Administrador**:
    -   **Email**: `admin@teste.com`
    -   **Senha**: `admin`
-   **Testador**:
    -   **Email**: `testador@teste.com`
    -   **Senha**: `testador`

### Principais Rotas da API

-   `/api/admin/**`: Endpoints de gerenciamento (CRUD de usuários, projetos, estratégias). Requer autenticação de **Administrador**.
-   `/api/testador/**`: Endpoints para testadores (listar seus projetos). Requer autenticação de **Testador** ou **Administrador**.
-   `/api/sessoes/**`: Endpoints de gerenciamento de sessões de teste. Requer autenticação de **Testador** ou **Administrador**.
-   `/api/estrategias`: Endpoint público para listar estratégias. **Não requer autenticação**.

### Exemplo de Requisição com `curl`

Para listar todos os projetos (como administrador):

```bash
curl -u admin@teste.com:admin http://localhost:8080/api/admin/projetos
```

Para listar as estratégias disponíveis (público):

```bash
curl http://localhost:8080/api/estrategias
```
