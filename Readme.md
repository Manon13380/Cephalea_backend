🚀 Backend - Spring Boot (Java 21)

Ce projet est un backend Java 21 Spring Boot.
Il utilise PostgreSQL comme base de données (toujours lancé via Docker).
Le backend peut être lancé via Docker ou directement en local.


🛠️ Avant de lancer le projet, assure-toi d’avoir installé :

    Java 21 (si lancement sans docker)
    Maven
    Docker & Docker Compose


▶️ Lancer avec Docker

    docker-compose up --build
Backend + PostgreSQL lancés

API disponible : http://localhost:9090


▶️ Lancer le projet sans Docker (backend seulement)

Si tu veux lancer uniquement le backend en local, laisse PostgreSQL tourner via Docker et exécute :

    docker-compose up -d postgres


🔧 Commandes Docker utiles

Démarrer en arrière-plan : 
    
    docker-compose up -d

Arrêter : 

    docker-compose down

Relancer le docker :

    docker restart <nom_du_container_ou_id>
