# Usando uma imagem base do OpenJDK 21
FROM openjdk:21-jdk-slim

# Instalar dependências essenciais e Maven manualmente
RUN apt-get update && \
    apt-get install -y --no-install-recommends wget ca-certificates && \
    wget https://archive.apache.org/dist/maven/maven-3/3.9.7/binaries/apache-maven-3.9.7-bin.tar.gz && \
    tar -xzf apache-maven-3.9.7-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.9.7/bin/mvn /usr/bin/mvn && \
    rm apache-maven-3.9.7-bin.tar.gz && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar os arquivos do projeto para o container
COPY . .

# Expor a porta padrão do Spring Boot
EXPOSE 8080

# Comando para executar a aplicação
CMD ["mvn", "spring-boot:run"]