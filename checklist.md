# Weiva — Backend Checklist

## CRUDs

- [ ] **Usuário**
  - [x] CREATE
  - [x] READ (todos, por id, por email, por cpf, por telefone)
  - [x] UPDATE dados (nome, email, telefone, senha)
  - [x] UPDATE ativo → `PUT /usuarios/{id}/ativo`
  - [x] UPDATE role → `PUT /usuarios/{id}/role`
  - [x] DELETE

- [ ] **Farmácia**
  - [ ] Model
  - [ ] Repository
  - [ ] Service
  - [ ] Controller
  - [ ] CREATE
  - [ ] READ (todas, por id)
  - [ ] UPDATE
  - [ ] DELETE

- [ ] **Categoria**
  - [ ] Model
  - [ ] Repository
  - [ ] Service
  - [ ] Controller
  - [ ] CREATE
  - [ ] READ (todas, por id)
  - [ ] UPDATE
  - [ ] DELETE

- [ ] **Produto**
  - [ ] Model
  - [ ] Repository
  - [ ] Service
  - [ ] Controller
  - [ ] CREATE
  - [ ] READ (todos, por id, por farmácia, por categoria)
  - [ ] READ busca por termo (nome, descrição, farmácia)
  - [ ] UPDATE
  - [ ] UPDATE ativo → `PUT /produtos/{id}/ativo`
  - [ ] DELETE

- [ ] **Endereço**
  - [ ] Model
  - [ ] Repository
  - [ ] Service
  - [ ] Controller
  - [ ] CREATE
  - [ ] READ (por usuário)
  - [ ] UPDATE
  - [ ] DELETE

---

## Autenticação e Autorização

- [ ] `AuthMiddleware.java` (gerarToken, validarToken, exigirRole)
- [ ] `AuthService.java` (login com bcrypt, registro)
- [ ] `AuthController.java`
  - [ ] `POST /auth/login` → retorna token JWT
  - [ ] `POST /auth/registrar` → cria usuário comum
- [ ] Proteger rotas por role
  - [ ] Rotas públicas (sem token): busca de produtos, listagem de farmácias
  - [ ] Rotas de usuário logado: perfil, endereço
  - [ ] Rotas de admin: criar/editar/ativar produto da própria farmácia
  - [ ] Rotas de super_admin: gerenciar usuários, farmácias, roles

---

## Banco de dados

- [ ] `schema.sql` finalizado
  - [ ] Tabela `usuario`
  - [ ] Tabela `farmacia`
  - [ ] Tabela `categoria`
  - [ ] Tabela `produto`
  - [ ] Tabela `endereco`
  - [ ] Tabela `pedido` *(a adicionar)*
  - [ ] Tabela `item_pedido` *(a adicionar)*
  - [ ] Tabela `cupom` *(a adicionar)*
  - [ ] Tabela `avaliacao_produto` *(a adicionar)*

---

## Configuração do projeto

- [ ] `pom.xml` com todas as dependências (Javalin, JDBI3, MySQL, BCrypt, Gson, JWT)
- [ ] `db.properties` apontando pro MySQL
- [ ] CORS configurado no `Main.java` para o frontend React
- [ ] Tratamento global de exceções no `Main.java`

---

## Talvez (futuro)

- [ ] Notificações
- [ ] Imagem de perfil do usuário (Gravatar)
- [ ] Controle de quantidade no carrinho `| + | 6 | - |`
- [ ] Upload de imagem de produto (Multipart)
- [ ] Paginação nas listagens