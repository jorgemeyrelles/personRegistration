#!/bin/bash

# Script para build e deploy do frontend React
# Sistema de Registro de Pessoas

echo "🚀 Iniciando build do frontend React..."

# Build da imagem Docker
echo "📦 Construindo imagem Docker..."
docker build -t jorgemeyrelles/web-person-registration:latest .

if [ $? -eq 0 ]; then
    echo "✅ Build concluído com sucesso!"
    
    # Opção para fazer push da imagem
    read -p "🔄 Deseja fazer push da imagem para o Docker Hub? (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        echo "📤 Fazendo push da imagem..."
        docker push jorgemeyrelles/web-person-registration:latest
        echo "✅ Push concluído!"
    fi
    
    echo ""
    echo "🎉 Frontend pronto!"
    echo "📋 Comandos úteis:"
    echo "   • Executar localmente: docker run -p 3031:3031 jorgemeyrelles/web-person-registration:latest"
    echo "   • Acessar aplicação: http://localhost:3031"
    echo "   • Health check: http://localhost:3031/health"
    echo ""
else
    echo "❌ Erro durante o build!"
    exit 1
fi
