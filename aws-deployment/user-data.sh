#!/bin/bash

# Log de execução
exec > >(tee /var/log/user-data.log)
exec 2>&1

echo "🚀 Iniciando configuração do servidor..."
echo "Data/Hora: $(date)"

# Atualizar sistema
apt-get update -y
apt-get upgrade -y

# Instalar dependências básicas
apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg \
    lsb-release \
    git \
    htop \
    wget \
    nano \
    unzip

# Instalar Docker
echo "📦 Instalando Docker..."
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null

apt-get update -y
apt-get install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin

# Instalar Docker Compose standalone
echo "📦 Instalando Docker Compose..."
curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose

# Configurar Docker
systemctl start docker
systemctl enable docker
usermod -aG docker ubuntu

# Verificar instalação
docker --version > /home/ubuntu/docker-version.txt
docker-compose --version > /home/ubuntu/docker-compose-version.txt

# Criar diretório do projeto
mkdir -p /home/ubuntu/app
chown -R ubuntu:ubuntu /home/ubuntu/app

# Configurar swap (importante para t3.micro)
echo "💾 Configurando swap..."
fallocate -l 2G /swapfile
chmod 600 /swapfile
mkswap /swapfile
swapon /swapfile
echo '/swapfile none swap sw 0 0' >> /etc/fstab

# Configurar timezone
timedatectl set-timezone America/Sao_Paulo

# Otimizações de memória
echo "vm.swappiness=10" >> /etc/sysctl.conf
sysctl -p

# Log final
echo "✅ Configuração concluída em $(date)" > /home/ubuntu/setup-complete.log
echo "✅ Setup completo!"
