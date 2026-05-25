# Guia de Deploy - Tomcat 10

Este documento descreve como fazer o deploy da aplicação TP03-CBTSWE1 no Apache Tomcat 10+.

## Pré-requisitos

- Apache Tomcat 10.1.16 ou superior
- Java 17 JDK instalado
- Acesso ao diretório de instalação do Tomcat (geralmente requer sudo)
- A aplicação compilada

## Opção 1: Deploy Automático (Recomendado)

Execute o script automatizado no raiz do projeto:

```bash
cd /home/dev/sistema/sistema/TP03-CBTSWE1
sudo bash DEPLOY_COMPLETO.sh
```

Este script:
1. Compila todos os arquivos .java
2. Limpa o diretório ROOT anterior
3. Copia arquivos compilados para WEB-INF/classes/
4. Copia arquivos estáticos (HTML, CSS, web.xml)
5. Ajusta permissões
6. Reinicia o Tomcat

## Opção 2: Deploy Manual

### 1. Compilar os arquivos Java

```bash
cd TP03-CBTSWE1/src/main/java
javac -cp /usr/share/tomcat10/lib/servlet-api.jar:../../webapp/WEB-INF/lib/mysql-connector-java-8.0.21.jar -d /tmp/classes -encoding UTF-8 *.java
```

### 2. Copiar para Tomcat ROOT

```bash
sudo mkdir -p /var/lib/tomcat10/webapps/ROOT/WEB-INF/classes
sudo mkdir -p /var/lib/tomcat10/webapps/ROOT/WEB-INF/lib
sudo cp /tmp/classes/*.class /var/lib/tomcat10/webapps/ROOT/WEB-INF/classes/
sudo cp src/main/webapp/WEB-INF/lib/mysql-connector-java-8.0.21.jar /var/lib/tomcat10/webapps/ROOT/WEB-INF/lib/
```

### 3. Copiar arquivos estáticos

```bash
sudo cp src/main/webapp/*.html /var/lib/tomcat10/webapps/ROOT/
sudo cp src/main/webapp/*.css /var/lib/tomcat10/webapps/ROOT/
sudo cp src/main/webapp/WEB-INF/web.xml /var/lib/tomcat10/webapps/ROOT/WEB-INF/
```

### 4. Ajustar permissões

```bash
sudo chown -R tomcat:tomcat /var/lib/tomcat10/webapps/ROOT
```

### 5. Reiniciar Tomcat

```bash
sudo systemctl restart tomcat10
```

## Opção 3: Deploy como Contexto

Se preferir não sobrescrever ROOT, pode fazer deploy como contexto separado:

### 1. Copiar ROOT.xml

```bash
sudo cp deploy/tomcat/ROOT.xml /var/lib/tomcat10/conf/Catalina/localhost/TP03-CBTSWE1.xml
```

### 2. Copiar aplicação para webapps

```bash
sudo mkdir -p /var/lib/tomcat10/webapps/TP03-CBTSWE1/WEB-INF/classes
sudo cp -r src/main/webapp/* /var/lib/tomcat10/webapps/TP03-CBTSWE1/
sudo cp /tmp/classes/*.class /var/lib/tomcat10/webapps/TP03-CBTSWE1/WEB-INF/classes/
```

### 3. Ajustar permissões

```bash
sudo chown -R tomcat:tomcat /var/lib/tomcat10/webapps/TP03-CBTSWE1
```

### 4. Reiniciar Tomcat

```bash
sudo systemctl restart tomcat10
```

A aplicação estará acessível em: `http://localhost:8080/TP03-CBTSWE1/`

## Verificação

Após o deploy, verifique se a aplicação está acessível:

```bash
curl -I http://localhost:8080/
curl -I http://localhost:8080/index.html
curl -I http://localhost:8080/ViewServlet
```

Ou acesse no navegador:
- **Root:** http://192.168.1.72:8002/
- **Home:** http://192.168.1.72:8002/index.html
- **Livros:** http://192.168.1.72:8002/list
- **Novo livro:** http://192.168.1.72:8002/new
- **Créditos:** http://192.168.1.72:8002/creditos.html

## Solução de Problemas

### 404 - Página não encontrada
- Verifique se os arquivos foram copiados para ROOT
- Confirme que web.xml está em WEB-INF/
- Reinicie o Tomcat

### Classes não encontradas
- Verifique se os .class estão em WEB-INF/classes/
- Confirme que a compilação foi bem-sucedida

### Permissão negada
- Use `sudo` para operações em /var/lib/tomcat10/
- Verifique se o usuário tem acesso adequado

### Tomcat não reinicia
- Verifique o log: `/var/lib/tomcat10/logs/catalina.out`
- Confirme que a sintaxe de XML está correta

## Estrutura Esperada

Após um deploy bem-sucedido, o diretório deve ter essa estrutura:

```
/var/lib/tomcat10/webapps/ROOT/
├── index.html
├── creditos.html
├── style.css
└── WEB-INF/
    ├── web.xml
    └── classes/
        ├── Produto.class
        ├── ProdutoDao.class
        ├── ProdutoFormValidator.class
        ├── UiRenderer.class
        ├── SaveServlet.class
        ├── ViewServlet.class
        ├── EditServlet.class
        ├── EditServlet2.class
        ├── DeleteServlet.class
        ├── DetalhesServlet.class
        └── CreditosServlet.class
    └── lib/
        └── mysql-connector-java-8.0.21.jar
```

## Suporte

Para dúvidas ou problemas, consulte:
- Documentação do Tomcat: https://tomcat.apache.org/
- GitHub do projeto: https://github.com/matheuscorreiafranca/TP03-CBTSWE1
