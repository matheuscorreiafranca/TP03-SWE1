# TP03-CBTSWE1 - CRUD de Livros

**Dupla:** Matheus Correia de Franca, Davi Leite Coelho

**Curso:** Análise e Desenvolvimento de Sistemas (ADS 571)

**Disciplina:** CBTSWE1 - Desenvolvimento Web II

**Instituição:** IFSP Campus Cubatão

## Descricao da Entidade BOOK

- **book_id**: int (chave primaria)
- **title**: String
- **author**: String
- **price**: decimal

## Funcionalidades

- Menu de navegacao com acesso aos principais recursos
- CRUD completo de livros: Create, Read, Update, Delete
- Persistencia em MySQL usando JDBC
- Script versionado em `database/bookstore.sql`
- Pagina especifica de creditos da dupla
- Tratamento de erros e mensagens de feedback

## Tecnologias

- **Java 17** OpenJDK
- **Jakarta EE Servlet 5.0**
- **Apache Tomcat 10.1.16**
- **HTML5 + CSS3 Moderno**
- **MySQL 8 + JDBC**

## Banco de Dados

O script oficial da entrega esta em:

```bash
database/bookstore.sql
```

Ele cria o banco `Bookstore`, a tabela `book` e alguns registros iniciais. Para executar com um usuario que tenha permissao de criar banco:

```bash
mysql -u root -p < database/bookstore.sql
```

Nesta maquina, o usuario disponivel `laravel` nao tinha permissao para criar novos bancos, entao a tabela `book` foi criada no schema existente `java`. A aplicacao usa por padrao:

```text
jdbc:mysql://localhost:3306/java
usuario: laravel
senha: laravel
```

Para apontar para `Bookstore` em outro ambiente, defina:

```bash
export BOOKSTORE_JDBC_URL='jdbc:mysql://localhost:3306/Bookstore?useSSL=false&allowPublicKeyRetrieval=true&useTimezone=true&serverTimezone=UTC'
export BOOKSTORE_JDBC_USERNAME='root'
export BOOKSTORE_JDBC_PASSWORD='sua_senha'
```

## Estrutura de Arquivos

```
TP03-CBTSWE1/
├── src/
│   └── main/
│       ├── java/
│       │   ├── Book.java                       # Entidade livro
│       │   ├── BookDao.java                    # DAO JDBC/MySQL
│       │   ├── BookControllerServlet.java      # Controller CRUD de livros
│       │   ├── Produto.java                    # Entidade do trabalho anterior
│       │   ├── ProdutoDao.java                 # DAO em memoria do trabalho anterior
│       │   ├── ProdutoFormValidator.java       # Validação de formulários
│       │   ├── UiRenderer.java                 # Geração de HTML
│       │   ├── SaveServlet.java                # POST: criar produto
│       │   ├── ViewServlet.java                # GET: listar produtos
│       │   ├── EditServlet.java                # GET: formulário edição
│       │   ├── EditServlet2.java               # POST: atualizar produto
│       │   ├── DeleteServlet.java              # GET: deletar produto
│       │   ├── DetalhesServlet.java            # GET: ver detalhes
│       │   └── CreditosServlet.java            # GET: página de créditos
│       └── webapp/
│           ├── index.html                      # Home page de livros
│           ├── creditos.html                   # Página de créditos
│           ├── style.css                       # Estilos modernos
│           └── WEB-INF/
│               └── web.xml                     # Configuração servlet
├── deploy/
│   └── tomcat/
│       ├── README.md                           # Guia de deploy
│       └── ROOT.xml                            # Config do contexto
├── database/
│   └── bookstore.sql                           # Script MySQL versionado
├── DEPLOY_COMPLETO.sh                          # Script de deploy automatizado
├── README.md                                   # Este arquivo
└── LICENSE                                     # Licença do projeto
```

## Como Executar

### Pré-requisitos
- Java 17 ou superior
- Apache Tomcat 10+
- Git (opcional, para clonar repositório)

### 1. Clonar o repositório
```bash
git clone https://github.com/matheuscorreiafranca/TP03-CBTSWE1.git
cd TP03-CBTSWE1
```

### 2. Compilar (opcional)
Se usar IDE como Eclipse, IntelliJ ou VS Code, a compilação é automática.

Para compilar manualmente:
```bash
cd src/main/java
javac -cp /usr/share/tomcat10/lib/servlet-api.jar:../webapp/WEB-INF/lib/mysql-connector-java-8.0.21.jar *.java
```

### 3. Fazer deploy no Tomcat

#### Opção A: Script automático (Linux/MacOS)
```bash
sudo bash DEPLOY_COMPLETO.sh
```

#### Opção B: Manual
1. Copiar `src/main/webapp/*` para `/var/lib/tomcat10/webapps/ROOT/`
2. Copiar classes compiladas para `/var/lib/tomcat10/webapps/ROOT/WEB-INF/classes/`
3. Copiar `deploy/tomcat/ROOT.xml` para `/var/lib/tomcat10/conf/Catalina/localhost/ROOT.xml`
4. Reiniciar Tomcat: `sudo systemctl restart tomcat10`

## 📍 Rotas da Aplicação

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/` | Página inicial (index.html) |
| GET | `/index.html` | Página inicial |
| GET | `/list` | Listar todos os livros |
| GET | `/new` | Formulário para novo livro |
| POST | `/insert` | Criar novo livro |
| GET | `/edit?id=X` | Formulário para editar livro |
| POST | `/update` | Atualizar livro |
| GET | `/delete?id=X` | Deletar livro |
| GET | `/creditos.html` | Página de créditos |
| GET | `/style.css` | Arquivo de estilos |

## 🎨 Interface Gráfica

Desenvolvida com **Manrope Font** e **esquema de cores moderno**:
- **Fundo:** Cinza claro profissional
- **Marca:** Teal (#1f6f61)
- **Destaque:** Vermelho para ações de risco (#a23b30)
- **Design:** Glassmorphism com cards flutuantes
- **Responsividade:** Totalmente responsivo (mobile, tablet, desktop)

## ✅ Requisitos Cumpridos

- Nome da dupla em todos arquivos `.java`
- Projeto CRUD completo de livros
- Banco MySQL com script versionado
- Menu de navegacao
- Pagina especifica de creditos
- Deploy em Tomcat com driver JDBC

## 🔗 Links Úteis

- **GitHub:** https://github.com/matheuscorreiafranca/TP03-CBTSWE1
- **Tomcat:** http://192.168.1.72:8002/ (apos deploy)
- **Professor:** Wellington Tuler Moraes

## 📝 Notas

- A aplicação usa apenas Java puro e Servlet, sem frameworks adicionais
- Todas as validações são feitas no servidor
- Mensagens de erro são amigáveis e orientadas ao usuário
- O código segue convenções Java (camelCase para variáveis, PascalCase para classes)

---

**Desenvolvido para CBTSWE1 - IFSP Campus Cubatao**
