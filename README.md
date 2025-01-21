# Fórum API

Esta é uma API para um sistema de fórum, construído com o Spring Boot, Hibernate, MySQL, JWT e Flyway. A API permite a criação, listagem, detalhamento, atualização e exclusão de tópicos de um fórum, com autenticação e segurança utilizando tokens JWT.

### Tecnologias Utilizadas

- Spring Boot: Framework principal para construção da API.
- JWT (JSON Web Token): Mecanismo de autenticação e segurança.
- Flyway: Gerenciamento de migrações de banco de dados.
- MySQL: Banco de dados utilizado para armazenar os dados do fórum.
- Spring Doc OpenAPI: Geração automática da documentação da API no formato OpenAPI (Swagger).

### Funcionalidades

A API oferece os seguintes recursos:
- **Autenticação e Autorização**: Os usuários podem fazer login e acessar funcionalidades protegidas.
- **Criação de Tópicos**: Usuários autenticados podem criar novos tópicos de discussão.
- **Postagem de Respostas**: Permite que os usuários respondam aos tópicos criados.
- **Listagem de Tópicos**: Exibe todos os tópicos criados no fórum.
- **Exibição de Tópicos e Respostas**: Permite visualizar um tópico específico e todas as suas respostas associadas.

### Acessando a Documentação da API

A documentação interativa da API está disponível no seguinte link:

  ```http://localhost:8080/swagger-ui.html```

Essa URL assume que a aplicação está rodando localmente na porta 8080. Caso esteja em outro ambiente ou porta, ajuste o link conforme necessário.
### Validação de Token JWT

- O token JWT gerado na autenticação será utilizado para validar o acesso aos endpoints da API.

### Configuração do Banco de Dados

- O projeto utiliza o Flyway para gerenciar as migrações de banco de dados.
- A configuração do banco de dados pode ser definida no arquivo `application.properties`:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/forum_db
  spring.datasource.username=root
  spring.datasource.password=senha
  flyway.locations=classpath:/db/migration
  ```

### Regras de Negócio

- Campos obrigatórios: Todos os campos do tópico (título, mensagem, autor, e curso) são obrigatórios e devem ser validados antes da persistência no banco.
- Tópicos duplicados: A API não permite o cadastro de tópicos com o mesmo título e mensagem.
- Autenticação: Somente usuários autenticados com um token JWT válido podem interagir com a API.

### Como Rodar o Projeto

Clone o repositório:

```bash
git clone git@github.com:EvertonDev2002/forum-hub.git
```

Instale as dependências:

Se você estiver usando Maven, pode rodar:

```bash
mvn install
```

### Configuração do Banco de Dados

Certifique-se de ter o MySQL instalado e uma base de dados configurada.
Edite o arquivo `application.properties` com as informações corretas de conexão.
