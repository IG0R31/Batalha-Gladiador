# Batalha de Gladiadores (Gladiator Fighting)

Sistema web focado no gerenciamento, recrutamento e combate tático automatizado de gladiadores (*autobattler*), onde a estratégia reside na gestão de recursos e composição dos combatentes[cite: 2].

---

##  Visão Geral do Projeto
O projeto consiste em uma aplicação web na qual cada usuário registrado administra sua própria carteira de combatentes[cite: 1, 2]. O jogo dispensa comandos de ação em tempo real (*sem inputs dinâmicos em combate*): o cerne da jogabilidade concentra-se na compra, customização, gerenciamento e alocação estratégica de gladiadores para arenas e torneios[cite: 2].

---

##  Principais Características

### 1. Sistema de Usuários e Economia
* **Autenticação e Perfis:** Cadastro de conta com nome de usuário, e-mail e senha criptografada[cite: 1].
* **Carteira de Créditos/Moedas:** Todo novo usuário inicia com um saldo base de créditos para recrutar combatentes[cite: 1].
* **Recompensa por Vitória:** O saldo do usuário aumenta automaticamente a cada combate vencido na arena[cite: 1].
* **Perfil do Jogador:** Exibição de histórico de combates, histórico de aquisições e estatísticas gerais[cite: 1, 2].

### 2. Entidade: Gladiadores
* **Recrutamento com Aleatoriedade:** Os gladiadores possuem atributos gerados aleatoriamente, garantindo variações e raridade a cada recrutamento[cite: 1, 2].
* **Sistema de Tiers:** Classes/Tiers de poder (ex.: Bronze, Prata, Ouro e Diamante) que definem o custo de aquisição e o teto/piso dos atributos gerados[cite: 2].
* **Customização:** Nome exclusivo e biografia/descrição livre criada pelo usuário[cite: 1].
* **Vínculo Exclusivo:** Cada gladiador ativo pertence à carteira individual de um usuário registrado[cite: 1].

### 3. Ciclo de Vida e Perma-Death
* **Status Vital:** Monitoramento do gladiador como `[Ativo / Vivo]` ou `[Morto]`[cite: 1].
* **Morte Permanente:** Em caso de derrota na arena, o guerreiro perde o status de ativo, sendo retirado do fluxo de combate do usuário (mantendo histórico ou memorial)[cite: 1, 2].
* **Edição & Descarte:** Permite atualizar a biografia/nome do combatente ou dispensá-lo manualmente da carteira ativa[cite: 1].

### 4. Dinâmica de Batalha (Autobattler)
* **Combate Automatizado:** Lutas sem necessidade de reflexos ou tempo de resposta do usuário; o resultado e ações derivam dos atributos e regras táticas[cite: 2].
* **Resultados e Consequências:**
    * **Vitória:** +1 ao histórico de vitórias do gladiador e premiação em créditos ao proprietário[cite: 1].
    * **Derrota:** Perda irreversível do combatente (*status Morto*)[cite: 1, 2].

### 5. Consultas, Rankings e Busca
* **Classificação Global:** Rankings de desempenho por vitórias, maiores atributos e tiers[cite: 1, 2].
* **Histórico de Partidas:** Relatórios competitivos e logs de combates passados por gladiador ou por usuário[cite: 1].
* **Busca e Inspeção:** Pesquisa de combatentes, histórico por ID e consulta a perfis e carteiras de outros jogadores[cite: 1, 2].

---

##  Etapas do Código