#!/bin/bash
# Matheus Correia de Franca, Davi Leite Coelho
# Deploy completo TP03-CBTSWE1 com compilação e estrutura correta

set -e

PROJECT_DIR="/home/dev/sistema/sistema/TP03-CBTSWE1"
TOMCAT_ROOT="/var/lib/tomcat10/webapps/ROOT"
SRC_JAVA="$PROJECT_DIR/src/main/java"
SRC_WEBAPP="$PROJECT_DIR/src/main/webapp"
SUDO=""
if [ "$(id -u)" -ne 0 ]; then
    SUDO="sudo"
fi

echo "======================================"
echo "Deploy Completo TP03-CBTSWE1"
echo "======================================"

# [1/6] Compilar Java
echo "[1/6] Compilando arquivos Java..."
cd "$PROJECT_DIR"
mkdir -p /tmp/classes
SERVLET_JAR="/usr/share/tomcat10/lib/servlet-api.jar"
MYSQL_JAR="$SRC_WEBAPP/WEB-INF/lib/mysql-connector-java-8.0.21.jar"
javac -cp "$SERVLET_JAR:$MYSQL_JAR" -d /tmp/classes -encoding UTF-8 $SRC_JAVA/*.java 2>&1 || {
    echo "ERRO na compilação!"
    exit 1
}
echo "✓ Compilação concluída"

# [2/7] Parar Tomcat para remover a aplicação antiga
echo "[2/7] Parando Tomcat..."
$SUDO systemctl stop tomcat10
echo "✓ Tomcat parado"

# [3/7] Limpar ROOT anterior
echo "[3/7] Removendo aplicação anterior..."
$SUDO rm -rf "$TOMCAT_ROOT"/*
echo "✓ ROOT limpo"

# [4/7] Criar estrutura WEB-INF
echo "[4/7] Criando estrutura de diretórios..."
$SUDO mkdir -p "$TOMCAT_ROOT/WEB-INF/classes"
$SUDO mkdir -p "$TOMCAT_ROOT/WEB-INF/lib"
echo "✓ Estrutura criada"

# [5/7] Copiar arquivos compilados
echo "[5/7] Copiando classes compiladas..."
$SUDO cp -v /tmp/classes/*.class "$TOMCAT_ROOT/WEB-INF/classes/"
$SUDO cp -v "$MYSQL_JAR" "$TOMCAT_ROOT/WEB-INF/lib/"
echo "✓ Classes copiadas"

# [6/7] Copiar arquivos estáticos e web.xml
echo "[6/7] Copiando arquivos estáticos..."
$SUDO cp -v "$SRC_WEBAPP"/*.html "$TOMCAT_ROOT/"
$SUDO cp -v "$SRC_WEBAPP"/*.css "$TOMCAT_ROOT/"
$SUDO cp -v "$SRC_WEBAPP"/*.js "$TOMCAT_ROOT/"
$SUDO cp -v "$SRC_WEBAPP/WEB-INF/web.xml" "$TOMCAT_ROOT/WEB-INF/"
echo "✓ Arquivos estáticos copiados"

# [7/7] Ajustar permissões e iniciar
echo "[7/7] Ajustando permissões e iniciando Tomcat..."
$SUDO chown -R tomcat:tomcat "$TOMCAT_ROOT"
$SUDO systemctl start tomcat10
echo "✓ Tomcat reiniciado"

echo ""
echo "======================================"
echo "Aguardando inicialização (5 segundos)..."
echo "======================================"
sleep 5

echo ""
echo "======================================"
echo "Validando aplicação publicada..."
echo "======================================"
if curl -fsS "http://localhost:8002/style.css?v=tp03-premium-v2" >/tmp/tp03-style-check.css; then
    if grep -q "premium-ui-v2" /tmp/tp03-style-check.css; then
        echo "✓ CSS premium carregado corretamente"
    else
        echo "ERRO: /style.css respondeu, mas não é a versão premium esperada."
        sed -n '1,20p' /tmp/tp03-style-check.css
        exit 1
    fi
else
    echo "ERRO: /style.css não respondeu com sucesso."
    exit 1
fi

if curl -fsS "http://localhost:8002/app.js?v=tp03-premium-v2" >/tmp/tp03-js-check.js; then
    echo "✓ JavaScript carregado corretamente"
else
    echo "ERRO: /app.js não respondeu com sucesso."
    exit 1
fi

if curl -fsS "http://localhost:8002/list" >/tmp/tp03-list-check.html; then
    if grep -q "Books Management\\|Catalogo\\|List of Books" /tmp/tp03-list-check.html; then
        echo "✓ /list respondeu com o CRUD de livros"
    else
        echo "ERRO: /list respondeu, mas o HTML não parece ser do CRUD de livros."
        echo "Primeiras linhas retornadas:"
        sed -n '1,20p' /tmp/tp03-list-check.html
        exit 1
    fi
else
    echo "ERRO: /list não respondeu com sucesso."
    echo "Confira o log do Tomcat:"
    echo "  sudo journalctl -u tomcat10 -n 80 --no-pager"
    exit 1
fi

echo ""
echo "======================================"
echo "✓ Deploy Concluído com Sucesso!"
echo "======================================"
echo ""
echo "URLs disponíveis:"
echo "  http://192.168.1.72:8002/"
echo "  http://192.168.1.72:8002/index.html"
echo "  http://192.168.1.72:8002/list"
echo "  http://192.168.1.72:8002/new"
echo "  http://192.168.1.72:8002/edit?id=1"
echo "  http://192.168.1.72:8002/delete?id=1"
echo "  http://192.168.1.72:8002/creditos.html"
echo ""
