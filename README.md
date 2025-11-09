# ✅ **Login-BE — Autenticação e Autorização em Java (Spring Boot)**

O **Login-BE** é uma API backend desenvolvida em **Java + Spring Boot** com o objetivo de fornecer um serviço simples e funcional de **autenticação e autorização baseada em JWT**.
Ele pode ser usado como serviço independente ou integrado a outras aplicações — como o projeto **AppProdutos** — para controlar login, emissão de tokens e gerenciamento de usuários.

---

## ✅ **Objetivo do Projeto**

Este backend foi criado para servir como **exemplo didático** de:

* Criação de API REST em Spring Boot
* Autenticação com JWT (token de acesso e token de refresh)
* Implementação de filtro de segurança personalizada
* Criação e validação de roles (perfis de acesso)
* Boas práticas de segurança e arquitetura

---

## ✅ **Principais Funcionalidades**

### 🔐 **1. Login com JWT**

* Usuário envia credenciais (e-mail/login + senha)
* API valida o usuário
* Se válido, retorna:

  * **access token** (curta duração)
  * **refresh token** (para renovar o access token)

### ✅ Endpoints:

```
POST /auth/login
POST /auth/refresh
GET  /auth/me
```

---

### 👤 **2. Cadastro e Gestão de Usuários**

* Usuário possui:

  * ID
  * Nome
  * E-mail/Login
  * Senha criptografada (BCrypt)
  * Roles (ex.: ADMIN, USER)

* CRUD básico de usuário pode ser incorporado ou ampliado conforme necessidade.

---

### 🛡️ **3. Controle de Acesso por Roles**

Após autenticar, o token contém as permissões do usuário.
Outros serviços (como o AppProdutos) podem validar essas roles usando:

```java
@PreAuthorize("hasRole('ADMIN')")
```

---

### 🔑 **4. Proteção das Rotas com Spring Security**

* Filtro JWT intercepta todas as requisições protegidas
* Valida assinatura, expiração e permissões
* Injeta o usuário autenticado no contexto de segurança

---

## ✅ **Arquitetura Básica**

```
controller/        → Endpoints públicos (login, refresh, user info)
service/           → Regras de autenticação e criptografia de senha
repository/        → Acesso ao banco de dados
security/          → Filtros, providers, configuração JWT
dto/               → Estruturas de entrada/saída
entity/            → Usuário, roles, tokens
```

---

## ✅ **Tecnologias Utilizadas**

* **Java 17+**
* **Spring Boot**
* **Spring Security**
* **JWT (JSON Web Token)**
* **JPA/Hibernate**
* **Banco relacional** (H2, PostgreSQL ou outro)
* **BCrypt** para hash de senha

---

## ✅ **Como esse projeto deve ser usado**

Este BE pode ser integrado a qualquer outro backend para servir como provedor de autenticação.
O fluxo clássico de uso:

1. Cliente realiza login no **Login-BE**
2. Login-BE devolve **access token + refresh token**
3. Aplicação cliente adiciona o header:

   ```
   Authorization: Bearer <token>
   ```
4. O backend de destino valida o token e autoriza a ação solicitada
5. Quando o token expira → cliente chama `/auth/refresh`

Ideal para projetos didáticos ou sistemas modulares.

---

## ✅ **Objetivo Educacional**

O Login-BE é ideal para ensino de:

* Autenticação moderna com JWT
* Segurança em APIs
* Arquitetura limpa
* Boas práticas com Spring Security
* Separação de serviços (auth → service independente)
