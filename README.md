# TP02-CBTSWE1 - CRUD de Produtos

**Dupla:** Matheus Correia de Franca, Davi Leite Coelho

**Curso:** Análise e Desenvolvimento de Sistemas (ADS 571)

**Disciplina:** CBTSWE1 - Desenvolvimento Web II

**Instituição:** IFSP Campus Cubatão

## 📋 Descrição da Entidade PRODUTOS

- **id**: int (chave primária)
- **nome**: String
- **unidadeCompra**: int
- **descricao**: String
- **qtdPrevistoMes**: double
- **precoMaxComprado**: double

## ✨ Funcionalidades

✅ **Menu de navegação** - Fácil acesso aos principais recursos
✅ **CRUD Completo** - Create, Read, Update, Delete de produtos
✅ **Banco de dados em memória** - Sem dependências externas
✅ **Validação de formulários** - Mensagens de erro amigáveis
✅ **Interface moderna** - Design responsivo com Manrope Font
✅ **Página de créditos** - Informações da dupla
✅ **Página de detalhes** - Visualizar produto completo
✅ **Tratamento de erros** - Feedback visual ao usuário

## 🛠️ Tecnologias

- **Java 17** OpenJDK
- **Jakarta EE Servlet 5.0**
- **Apache Tomcat 10.1.16**
- **HTML5 + CSS3 Moderno**
- **Sem frameworks externos** (puro Servlet)

## 📁 Estrutura de Arquivos

```
TP02-CBTSWE1/
├── src/
│   └── main/
│       ├── java/
│       │   ├── Produto.java                    # Entidade
│       │   ├── ProdutoDao.java                 # DAO com banco em memória
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
│           ├── index.html                      # Home page
│           ├── creditos.html                   # Página de créditos
│           ├── style.css                       # Estilos modernos
│           └── WEB-INF/
│               └── web.xml                     # Configuração servlet
├── deploy/
│   └── tomcat/
│       ├── README.md                           # Guia de deploy
│       └── ROOT.xml                            # Config do contexto
├── DEPLOY_COMPLETO.sh                          # Script de deploy automatizado
├── README.md                                   # Este arquivo
└── LICENSE                                     # Licença do projeto
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Apache Tomcat 10+
- Git (opcional, para clonar repositório)

### 1. Clonar o repositório
```bash
git clone https://github.com/matheuscorreiafranca/TP02-CBTSWE1.git
cd TP02-CBTSWE1
```

### 2. Compilar (opcional)
Se usar IDE como Eclipse, IntelliJ ou VS Code, a compilação é automática.

Para compilar manualmente:
```bash
cd src/main/java
javac -cp /usr/share/tomcat10/lib/servlet-api.jar *.java
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
| GET | `/ViewServlet` | Listar todos os produtos |
| GET | `/DetalhesServlet?id=X` | Ver detalhes do produto |
| GET | `/EditServlet?id=X` | Formulário para editar |
| POST | `/EditServlet2` | Atualizar produto |
| GET | `/DeleteServlet?id=X` | Deletar produto |
| POST | `/SaveServlet` | Criar novo produto |
| GET | `/creditos` | Página de créditos |
| GET | `/style.css` | Arquivo de estilos |

## 💾 Banco de Dados em Memória

O banco de dados é implementado em `ProdutoDao.java` usando `LinkedHashMap`:

```java
private static final Map<Integer, Produto> BANCO = new LinkedHashMap<>();
```

### Dados pré-carregados:
1. **Arroz** - 50kg, R$ 120.00
2. **Café** - 1kg, R$ 25.50
3. **Detergente** - 1L, R$ 5.00

### Thread Safety
Todos os métodos do DAO são sincronizados para garantir segurança em ambiente multi-thread.

## 🎨 Interface Gráfica

Desenvolvida com **Manrope Font** e **esquema de cores moderno**:
- **Fundo:** Cinza claro profissional
- **Marca:** Teal (#1f6f61)
- **Destaque:** Vermelho para ações de risco (#a23b30)
- **Design:** Glassmorphism com cards flutuantes
- **Responsividade:** Totalmente responsivo (mobile, tablet, desktop)

## ✅ Requisitos Cumpridos

- ✅ Nome da dupla em todos arquivos .java
- ✅ Projeto CRUD completo (Create, Read, Update, Delete)
- ✅ Banco de dados em memória
- ✅ Menu de navegação (index.html)
- ✅ Página específica de créditos (/creditos)
- ✅ UI moderna e profissional
- ✅ Validação de formulários
- ✅ Deploy funcional em Tomcat
- ✅ Repositório GitHub

## 🔗 Links Úteis

- **GitHub:** https://github.com/matheuscorreiafranca/TP02-CBTSWE1
- **Tomcat:** http://192.168.1.72:8002/ (após deploy)
- **Professor:** Wellington Tuler Moraes

## 📝 Notas

- A aplicação usa apenas Java puro e Servlet, sem frameworks adicionais
- Todas as validações são feitas no servidor
- Mensagens de erro são amigáveis e orientadas ao usuário
- O código segue convenções Java (camelCase para variáveis, PascalCase para classes)

---

**Desenvolvido em 04/05/2026 para CBTSWE1 - IFSP Campus Cubatão**
