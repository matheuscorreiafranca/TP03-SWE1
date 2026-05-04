#!/bin/bash
# Matheus Correia de Franca, Davi Leite Coelho
# Deploy completo TP02-CBTSWE1 com compilação e estrutura correta

set -e

PROJECT_DIR="/home/dev/sistema/sistema/TP02-CBTSWE1"
TOMCAT_ROOT="/var/lib/tomcat10/webapps/ROOT"
SRC_JAVA="$PROJECT_DIR/src/main/java"
SRC_WEBAPP="$PROJECT_DIR/src/main/webapp"

echo "======================================"
echo "Deploy Completo TP02-CBTSWE1"
echo "======================================"

# [1/6] Compilar Java
echo "[1/6] Compilando arquivos Java..."
cd "$PROJECT_DIR"
mkdir -p /tmp/classes
SERVLET_JAR="/usr/share/tomcat10/lib/servlet-api.jar"
javac -cp "$SERVLET_JAR" -d /tmp/classes -encoding UTF-8 $SRC_JAVA/*.java 2>&1 || {
    echo "ERRO na compilação!"
    exit 1
}
echo "✓ Compilação concluída"

# [2/6] Limpar ROOT anterior
echo "[2/6] Removendo aplicação anterior..."
sudo rm -rf $TOMCAT_ROOT/*
echo "✓ ROOT limpo"

# [3/6] Criar estrutura WEB-INF
echo "[3/6] Criando estrutura de diretórios..."
sudo mkdir -p $TOMCAT_ROOT/WEB-INF/classes
echo "✓ Estrutura criada"

# [4/6] Copiar arquivos compilados
echo "[4/6] Copiando classes compiladas..."
sudo cp -v /tmp/classes/*.class $TOMCAT_ROOT/WEB-INF/classes/
echo "✓ Classes copiadas"

# [5/6] Copiar arquivos estáticos e web.xml
echo "[5/6] Copiando arquivos estáticos..."
sudo cp -v $SRC_WEBAPP/*.html $TOMCAT_ROOT/
sudo cp -v $SRC_WEBAPP/*.css $TOMCAT_ROOT/
sudo cp -v $SRC_WEBAPP/WEB-INF/web.xml $TOMCAT_ROOT/WEB-INF/
echo "✓ Arquivos estáticos copiados"

# [6/6] Ajustar permissões e reiniciar
echo "[6/6] Ajustando permissões e reiniciando Tomcat..."
sudo chown -R tomcat:tomcat $TOMCAT_ROOT
sudo systemctl restart tomcat10
echo "✓ Tomcat reiniciado"

echo ""
echo "======================================"
echo "Aguardando inicialização (5 segundos)..."
echo "======================================"
sleep 5

echo ""
echo "======================================"
echo "✓ Deploy Concluído com Sucesso!"
echo "======================================"
echo ""
echo "URLs disponíveis:"
echo "  http://192.168.1.72:8002/"
echo "  http://192.168.1.72:8002/index.html"
echo "  http://192.168.1.72:8002/ViewServlet"
echo "  http://192.168.1.72:8002/SaveServlet (POST)"
echo "  http://192.168.1.72:8002/EditServlet?id=1"
echo "  http://192.168.1.72:8002/EditServlet2 (POST)"
echo "  http://192.168.1.72:8002/DetalhesServlet?id=1"
echo "  http://192.168.1.72:8002/DeleteServlet?id=1"
echo "  http://192.168.1.72:8002/creditos"
echo ""
