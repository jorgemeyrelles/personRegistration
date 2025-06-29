# Usando uma imagem base do OpenJDK 21
FROM openjdk:21-jdk-slim

# Instalar o Maven manualmente
RUN apt-get update && apt-get install -y wget && \
    wget https://downloads.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz && \
    tar -xzf apache-maven-3.8.8-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.8.8/bin/mvn /usr/bin/mvn && \
    rm apache-maven-3.8.8-bin.tar.gz

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar os arquivos do projeto para o container
COPY . .

# Expor a porta padrão do Spring Boot
EXPOSE 8080

# Comando para executar a aplicação
CMD ["mvn", "spring-boot:run"]