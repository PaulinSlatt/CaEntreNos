# CaEntreNos API

## Descrição

O **CaEntreNos** é uma API desenvolvida para gerenciar as operações de uma escola, incluindo cadastro e gerenciamento de responsáveis, alunos, admins e relatos. Esta API foi construída utilizando **Spring Boot** e segue as melhores práticas de desenvolvimento.

## Funcionalidades

- Cadastro, atualização, listagem e exclusão de responsáveis.
- Cadastro, atualização, listagem e exclusão de alunos.
- Cadastro, atualização, listagem e exclusão de admins.
- Cadastro, resposta, listagem e exclusão de relatos.
- Autenticação via JWT para admins e alunos.
- Documentação completa da API utilizando Swagger.
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Documentação JSON (OpenAPI): http://localhost:8080/v3/api-docs

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.4**
- **Spring Data JPA**
- **Spring Security**
- **Hibernate**
- **JWT (JSON Web Token)**
- **Maven**
- **MySQL**
- **Swagger (springdoc-openapi)**

## Configuração do Ambiente

### Pré-requisitos

- JDK 17 ou superior
- Maven 3.8 ou superior
- MySQL Server
- IDE de sua preferência (IntelliJ, Eclipse, VS Code, etc.)

### Configuração do Banco de Dados

Certifique-se de que você tem um banco de dados MySQL configurado. Ajuste as configurações de conexão no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/caentrenos
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
spring.jpa.hibernate.ddl-auto=update

