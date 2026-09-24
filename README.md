# Batalha de Gladiadores

Sistema web focado no gerenciamento, recrutamento e combate automatizado de gladiadores (*autobattler*), onde a estratégia reside na gestão de recursos e composição dos combatentes. Sem inputs dinâmicos em combate: o resultado da luta deriva dos atributos sorteados e da sorte.

---

## Visão Geral do Projeto

Cada usuário registrado administra sua própria carteira de combatentes. O jogador **compra** gladiadores com créditos, **customiza** nome/descrição/aparência/tier e os envia para a arena. A batalha compara os atributos dos dois gladiadores + um fator de sorte (`java.util.Random`): o vencedor acumula vitórias (subindo no ranking), o perdedor morre.

---

## Stack

| Camada | Tecnologia |
|---|---|
| Build | Maven (wrapper `mvnw`) |
| Linguagem | Java 25 |
| Framework | Spring Boot **4.1.1** |
| Web | Spring Web (MVC) + **Thymeleaf** |
| Persistência | Spring Data JPA + Hibernate |
| Banco | MySQL (schema `batalha_gladiador`) |
| Dev | Spring Boot DevTools (restart automático) |

Empacotamento `war`; roda com Tomcat embarcado via `./mvnw spring-boot:run`.

---

## Arquitetura

```
src/main/java/
├── controller/batalha_gladiadorController.java   # Rotas MVC + sessão de login
├── service/
│   ├── UsuarioService.java                       # Cadastro, login e créditos
│   └── GladiadorService.java                     # Regras do jogo (criar, batalhar, ranking)
├── model/                                        # Entidades JPA (mapeiam as tabelas)
│   ├── Usuario.java            (@Entity → tabela usuario)
│   ├── Gladiador.java          (@Entity → tabela gladiador, com enum interno Tier)
│   ├── AtributosBatalha.java   (@Embeddable → colunas forca/agilidade/stamina)
│   └── batalha_gladiadorModel.java
└── repository/
    ├── UsuarioRepository.java
    └── GladiadorRepository.java
src/main/resources/
├── application.properties                        # Conexão MySQL
├── templates/                                    # Telas (Thymeleaf)
└── static/img/                                   # Imagens (aparencias + carrossel)
    ├── Gladiadores_Rogue/                        # 1.png a 34.png (aparências)
    └── carrosel/                                 # Imagens do carrossel da tela principal
```

A controller não conhece SQL — toda regra passa pelo service, que acessa o banco pelos repositories.

---

## Banco de Dados

```sql
CREATE TABLE usuario(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    creditos INT NOT NULL DEFAULT 1000,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(150) NOT NULL
);

CREATE TABLE gladiador(
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    nome VARCHAR(150) NOT NULL,
    descricao VARCHAR(150),
    batalhas_vencidas INT NOT NULL DEFAULT 0,
    status ENUM('VIVO','MORTO') NOT NULL DEFAULT 'VIVO',
    aparencia INT NOT NULL,
    tier ENUM('BRONZE','PRATA','OURO','PLATINA') NOT NULL DEFAULT 'BRONZE',
    forca DOUBLE NOT NULL,
    agilidade DOUBLE NOT NULL,
    stamina DOUBLE NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
```

Detalhes de projeto:
- Os atributos de batalha são **sorteados uma única vez**, na criação (o tier define o fator aleatório). Depois, o banco é a fonte da verdade — carregar o gladiador não re-sorteia nada.
- A coluna `aparencia` (1–34) mapeia para uma imagem em `static/img/Gladiadores_Rogue/` (conversão no helper `getImagem()` da entidade `Gladiador`; valores fora da faixa são limitados ao intervalo).
- `status` é `String` (`"VIVO"`/`"MORTO"`) mapeando o ENUM do banco.

---

## Funcionalidades Implementadas

### Usuários e Economia
- **Cadastro** (`/cadastro`) com nome, email e senha — email único validado pelo service e pelo banco
- **Login/Logout** (`/login`, `/logout`) via `HttpSession` (`usuarioId`); rotas protegidas redirecionam para `/login` quando não há sessão
- **Créditos:** saldo inicial de **1000**; montar um gladiador custa **250** (descontado no service, com verificação de saldo)

### Gladiadores (CRUD)
- **Criar** — formulário com nome, descrição, tier (BRONZE/PRATA/OURO/PLATINA) e escolha de aparência entre as **34 imagens do pacote Gladiadores_Rogue** (radio com o índice 1–34; sem escolha, a aparência é sorteada de 1 a 34 na criação)
- **Pesquisar** — barra de pesquisa por parte do nome (`findByNomeContainingIgnoreCase`), busca global
- **Atualizar** — apenas a descrição (regra do projeto), pela tela de detalhe do gladiador
- **Deletar** — pela tela do usuário ou de detalhe

### Batalha (autobattler)
- Dois gladiadores **vivos** entram (`/batalha`); o poder de cada um é a soma dos atributos + sorte:

```java
poder = forca + agilidade + stamina + random.nextDouble(50);
```

- Maior poder vence: **+1 `batalhas_vencidas`** para o vencedor; perdedor vira `MORTO` (perma-death)
- Sem barra de vida/rounds: os valores sorteados na criação são a definição do personagem; a batalha apenas os compara

### Ranking e Consultas
- **Ranking geral** ordenado por batalhas vencidas, com pódio (top 3), tabela completa e destaque
- **Tela principal (arena)** com carrossel, botão de batalha e gladiadores em destaque

### Telas (Thymeleaf)
| Template | Rota | Função |
|---|---|---|
| `log_in.html` | `/login` | Login (com alerta de erro) |
| `sign_in.html` | `/cadastro` | Cadastro (nome, email, senha) |
| `tela_principal.html` | `/principal` | Arena: carrossel, batalha, destaques, busca |
| `tela_usuario.html` | `/usuario/{id}` | Perfil, créditos, lista de gladiadores |
| `gladiador/novo.html` | `/usuario/{id}/gladiador/novo` | Monte seu gladiador (form completo) |
| `gladiador/detalhe.html` | `/gladiador/{id}` | Ficha, atributos, editar descrição, deletar |
| `batalha.html` | `/batalha` | Seleção dos lutadores + resultado |
| `ranking.html` | `/ranking` | Pódio + classificação completa |
| `pesquisa.html` | `/pesquisar?q=` | Resultados da busca por nome |

---

## Como Rodar

1. **MySQL:** subir o servidor e executar o script SQL acima (database `batalha_gladiador`)
2. **Credenciais:** editar `src/main/resources/application.properties` (usuário/senha do seu banco)
3. **Rodar:** `./mvnw spring-boot:run` — abre em `http://localhost:8080/login`

---

## Status e Pendências

- [x] Estrutura Maven + Spring Boot 4.1.1 / Java 25
- [x] Entidades JPA mapeando o schema
- [x] Repositories (`UsuarioRepository`, `GladiadorRepository`)
- [x] Controller com sessão, todas as rotas e templates
- [x] Services completos (`UsuarioService` e `GladiadorService`), consultando o banco pelos repositories
- [x] Imagens servidas de `static/img/` — pacote **Gladiadores_Rogue** (34 aparências, `aparencia` 1–34) e carrossel da tela principal
- [x] Correções nos templates: `gladiador/novo.html` (variáveis fora de escopo em `th:src` da aparência e no `th:each` dos tiers)
- [ ] Hash de senha (hoje o login compara texto puro)
- [ ] Tabela de `batalha` para histórico de partidas (item "Histórico" do menu)

---

## Roadmap (ideias futuras)

- Batalha por HP (`getHealth` já existe em `AtributosBatalha`) com múltiplos rounds
- Derrota ≠ morte (com histórico de batalhas para revanche)
- Subida de tier por vitórias acumuladas
- Créditos de premiação por vitória