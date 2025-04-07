# Utilisation d'une image légère de JDK 21
FROM eclipse-temurin:21-jdk-alpine

# Définir le répertoire de travail
WORKDIR /app

# Copier uniquement les fichiers nécessaires
COPY . /app

# Installer Maven pour exécuter Spring Boot directement
RUN apk add --no-cache maven

# Exposer le port Spring Boot
EXPOSE 9090

# Lancer l'application en mode développement
CMD ["mvn", "spring-boot:run"]
