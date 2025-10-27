# ms-locations

Microservice de gestion des localisations GPS.

## Description

Ce microservice permet de gérer les localisations GPS des utilisateurs et de détecter les alertes de proximité entre utilisateurs. Il utilise une architecture hexagonale avec Spring Boot.

## Technologies utilisées

- Java 17
- Spring Boot 3.5.6
- Spring Data JPA
- MySQL 8.0
- Apache Kafka
- Docker

## Architecture

Le projet suit une architecture hexagonale organisée en couches :

- **Domain** : Modèles métier et ports
- **Application** : Services applicatifs
- **Infrastructure** : Adaptateurs (repositories, messaging)
- **Presentation** : Contrôleurs REST et DTOs

## Prérequis

- Java 17
- Maven 3.6+
- Docker et Docker Compose

## Configuration

Créer un fichier `.env` à la racine du projet avec les variables suivantes :

```
DATABASE_NAME=mslocations_db
DATABASE_USER=location_user
DATABASE_PASSWORD=location_pass
MYSQL_ROOT_PASSWORD=root_password
```

## Installation et démarrage

### Avec Docker Compose

```bash
docker-compose up -d
```

L'application sera accessible sur http://localhost:8081

### En local

```bash
# Compilation
./mvnw clean install

# Démarrage avec le profil local
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

## API Endpoints

Le service expose des endpoints REST pour la gestion des localisations :

- `POST /api/locations` : Ajouter une localisation
- `GET /api/locations` : Récupérer les localisations

## Base de données

Le service utilise MySQL en production et H2 pour les tests. Le schéma de base de données est automatiquement créé et mis à jour grâce à Hibernate.

## Messaging

Le service publie des alertes de proximité sur Kafka lorsque deux utilisateurs se trouvent à proximité l'un de l'autre.

## Tests

```bash
./mvnw test
```

## Auteur

Développé dans le cadre du cours R5A08 - Qualité développement.