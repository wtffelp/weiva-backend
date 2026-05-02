# Weiva — Backend API

Marketplace de farmácias. API REST desenvolvida em Java com Javalin, JDBI3, PostgreSQL e JWT.

## Stack

- Java 17
- Javalin 6.1.3
- JDBI3 + PostgreSQL
- BCrypt (senhas)
- JWT (autenticação)
- Gson (serialização)
- Docker (deploy)
- Render (hospedagem)

---

## Rotas implementadas

### Auth — público
```
POST /auth/register   { email, nome, cpf, telefone, senha }
POST /auth/login      { email, senha } → retorna token JWT
```

### Usuario
```
GET    /usuario                 lista todos (super_admin)
GET    /usuario/{id}            próprio usuário ou super_admin
GET    /usuario?email=
GET    /usuario?nome=
GET    /usuario?cpf=
GET    /usuario?telefone=
POST   /usuario                 super_admin
PUT    /usuario/{id}            próprio usuário ou super_admin
PUT    /usuario/{id}/ativo      { ativo: 0|1 } super_admin
PUT    /usuario/{id}/role       { role: usuario|admin|super_admin } super_admin
DELETE /usuario/{id}            super_admin
```

### Farmacia — GETs públicos
```
GET    /farmacias
GET    /farmacias/{id}
GET    /farmacias?cnpj=
GET    /farmacias?nome=
POST   /farmacias               { cnpj, nome, descricao, avaliacao, imagem_perfil, fk_usuario_id } admin, super_admin
PUT    /farmacias/{id}          admin, super_admin
PUT    /farmacias/{id}/ativo    { ativo: 0|1 } super_admin
DELETE /farmacias/{id}          super_admin
```

### Produto — GETs públicos
```
GET    /produto
GET    /produto/{id}
GET    /produto?nome=
GET    /produto?fk_farmacia_id=
GET    /produto?fk_categoria_id=
POST   /produto                 { nome, descricao, preco_unitario, caminho_galeria, fk_farmacia_id, fk_categoria_id } admin, super_admin
PUT    /produto/{id}            admin, super_admin
PUT    /produto/{id}/ativo      { ativo: 0|1 } admin, super_admin
DELETE /produto/{id}            super_admin
```

### Categoria — GETs públicos
```
GET    /categorias
GET    /categorias/{id}
GET    /categorias?nome=
GET    /categorias?pai=         subcategorias de uma categoria pai
POST   /categorias              { nome, descricao, fk_categoria_pai_id } super_admin
DELETE /categorias/{id}         super_admin
```

### Endereco
```
GET    /enderecos/usuario/{id}  próprio usuário ou super_admin
GET    /enderecos/{id}
POST   /enderecos               { logradouro, numero, bairro, cidade, estado, cep, fk_usuario_id }
PUT    /enderecos/{id}
DELETE /enderecos/{id}
```

### Busca — público
```
GET /busca?q=termo              busca em nome, descrição e farmácia
```

**Header para rotas protegidas:**
```
Authorization: Bearer <token>
```

---

## Roles

| Role        | Permissoes                                              |
|-------------|----------------------------------------------------------|
| usuario     | ver produtos, gerenciar proprio perfil e enderecos       |
| admin       | gerenciar produtos e farmacia propria                    |
| super_admin | acesso total                                             |

---

## A implementar

### Pedido
```
GET    /pedidos
GET    /pedidos/{id}
POST   /pedidos        { endereco_id, metodo_pagamento, itens: [{produto_id, quantidade}] }
PUT    /pedidos/{id}/status  { status: pendente|confirmado|preparando|saiu_entrega|entregue|cancelado }
DELETE /pedidos/{id}
```

Campos: id, fk_usuario_id, fk_farmacia_id, fk_endereco_id, status, metodo_pagamento, subtotal, taxa_entrega, total, data_criacao

### ItemPedido
Campos: id, fk_pedido_id, fk_produto_id, quantidade, preco_unitario

### Avaliacao
```
GET    /avaliacoes/produto/{id}
GET    /avaliacoes/farmacia/{id}
POST   /avaliacoes     { fk_produto_id, fk_farmacia_id, nota, comentario }
PUT    /avaliacoes/{id}
DELETE /avaliacoes/{id}
```

Campos: id, fk_usuario_id, fk_produto_id, fk_farmacia_id, fk_pedido_id, nota, comentario, data_criacao

### Upload de imagens (Cloudinary)
```
POST /upload/imagem    multipart/form-data → retorna URL
```

Dependencia Maven:
```xml
<dependency>
    <groupId>com.cloudinary</groupId>
    <artifactId>cloudinary-http45</artifactId>
    <version>1.37.1</version>
</dependency>
```

Variaveis necessarias:
```
CLOUDINARY_CLOUD_NAME
CLOUDINARY_API_KEY
CLOUDINARY_API_SECRET
```

### Campos a adicionar nos modelos existentes

**Usuario:** foto_perfil_url, telefone_verificado

**Farmacia:** logo_url, horario_funcionamento, taxa_entrega, pedido_minimo, areas_entrega

**Produto:** estoque, data_validade, receita_obrigatoria, imagens_adicionais, preco_promocional, data_inicio_promocao, data_fim_promocao

### Busca — melhorias
```
GET /busca?q=&preco_min=&preco_max=&farmacia_id=&categoria_id=&page=&limit=
```

---

## Segurança

- Senhas hasheadas com BCrypt
- CPF e CNPJ nao expostos nas respostas (Gson @Exclude)
- JWT com secret via variavel de ambiente
- CORS configurado (trocar anyHost() pela URL do frontend em producao)
- Email duplicado retorna 409 Conflict
- Senha nula no update nao sobrescreve a senha atual
- A implementar: validar tipo e tamanho no upload de imagens, validar estoque ao criar pedido, validar se usuario comprou antes de avaliar
