# 🛒 EcomProject - Architecture Microservices avec Spring Cloud

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.x-blue.svg)](https://spring.io/projects/spring-cloud)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📋 Table des matières

- [À propos](#à-propos)
- [Architecture](#architecture)
- [Microservices](#microservices)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Utilisation](#utilisation)
- [Endpoints API](#endpoints-api)
- [Technologies utilisées](#technologies-utilisées)
- [Auteur](#auteur)

## 🎯 À propos

EcomProject est une application e-commerce basée sur une architecture microservices utilisant Spring Cloud. Le projet démontre l'implémentation de patterns modernes de microservices incluant la découverte de services, la configuration centralisée, l'API Gateway, et la communication inter-services.

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        Client/Browser                        │
└───────────────────────────┬─────────────────────────────────┘
                            │
                    ┌───────▼────────┐
                    │  Gateway       │
                    │  Service       │
                    │  (Port 9999)   │
                    └───────┬────────┘
                            │
            ┌───────────────┼───────────────┐
            │               │               │
    ┌───────▼─────┐ ┌──────▼──────┐ ┌─────▼──────┐
    │  Customer   │ │  Inventory  │ │  Billing   │
    │  Service    │ │  Service    │ │  Service   │
    │ (Port 8081) │ │ (Port 8082) │ │(Port 8083) │
    └─────────────┘ └─────────────┘ └─────┬──────┘
            │               │               │
            └───────────────┼───────────────┘
                            │
                    ┌───────▼────────┐
                    │   Discovery    │
                    │   Service      │
                    │ (Port 8761)    │
                    └───────┬────────┘
                            │
                    ┌───────▼────────┐
                    │    Config      │
                    │    Service     │
                    │  (Port 8888)   │
                    └────────────────┘
```

### Flux de communication

1. **Client → Gateway** : Toutes les requêtes passent par la Gateway
2. **Gateway → Discovery** : Résolution des instances de services
3. **Gateway → Microservices** : Routage dynamique vers les services
4. **Billing → Customer/Inventory** : Communication via OpenFeign
5. **Tous → Config Service** : Récupération de la configuration centralisée

## 🔧 Microservices

### 1. **Customer Service** (Port: 8081)
Gestion des clients de l'application.

**Fonctionnalités :**
- Création, lecture, mise à jour et suppression (CRUD) des clients
- Validation des données clients
- API RESTful pour la gestion des clients

**Endpoints principaux :**
- `GET /customers` - Liste tous les clients
- `GET /customers/{id}` - Récupère un client par ID
- `POST /customers` - Crée un nouveau client
- `PUT /customers/{id}` - Met à jour un client
- `DELETE /customers/{id}` - Supprime un client

### 2. **Inventory Service** (Port: 8082)
Gestion de l'inventaire des produits.

**Fonctionnalités :**
- CRUD des produits
- Gestion des stocks
- Suivi des quantités disponibles

**Endpoints principaux :**
- `GET /products` - Liste tous les produits
- `GET /products/{id}` - Récupère un produit par ID
- `POST /products` - Crée un nouveau produit
- `PUT /products/{id}` - Met à jour un produit
- `DELETE /products/{id}` - Supprime un produit

### 3. **Billing Service** (Port: 8083)
Service de facturation utilisant OpenFeign pour communiquer avec Customer et Inventory.

**Fonctionnalités :**
- Génération de factures
- Calcul des totaux
- Récupération des informations clients et produits via OpenFeign

**Endpoints principaux :**
- `GET /bills` - Liste toutes les factures
- `GET /bills/{id}` - Récupère une facture complète (client + produits)
- `POST /bills` - Crée une nouvelle facture

### 4. **Gateway Service** (Port: 9999)
Point d'entrée unique pour tous les microservices.

**Fonctionnalités :**
- Routage des requêtes vers les services appropriés
- Configuration statique et dynamique des routes
- Load balancing
- Filtres personnalisés

**Configuration des routes :**
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: customer-service
          uri: lb://CUSTOMER-SERVICE
          predicates:
            - Path=/customers/**
        - id: inventory-service
          uri: lb://INVENTORY-SERVICE
          predicates:
            - Path=/products/**
        - id: billing-service
          uri: lb://BILLING-SERVICE
          predicates:
            - Path=/bills/**
```

### 5. **Discovery Service** (Port: 8761)
Serveur Eureka pour l'enregistrement et la découverte de services.

**Fonctionnalités :**
- Enregistrement automatique des microservices
- Découverte dynamique des services
- Health checking
- Dashboard Eureka

**Accès :** `http://localhost:8761`

### 6. **Config Service** (Port: 8888)
Service de configuration centralisée pour tous les microservices.

**Fonctionnalités :**
- Configuration externalisée
- Gestion des profils (dev, prod, test)
- Rafraîchissement dynamique de la configuration
- Support Git comme backend de configuration

## 📦 Prérequis

- **Java Development Kit (JDK)** : Version 17 ou supérieure
- **Maven** : Version 3.8 ou supérieure
- **IDE** : IntelliJ IDEA, Eclipse, ou VS Code
- **Postman** : Pour tester les APIs (optionnel)
- **Git** : Pour le versioning

## 🚀 Installation

### 1. Cloner le repository

```bash
git clone https://github.com/ZakariaElguazzar/EcomProject.git
cd EcomProject
```

### 2. Compiler le projet parent

```bash
mvn clean install
```

### 3. Démarrer les services dans l'ordre

**Ordre de démarrage recommandé :**

#### Étape 1 : Config Service
```bash
cd Config-Service
mvn spring-boot:run
```
Attendre que le service démarre sur le port 9999.

#### Étape 2 : Discovery Service
```bash
cd Discovery-Service
mvn spring-boot:run
```
Attendre que le service démarre sur le port 8761.
Vérifier le dashboard : `http://localhost:8761`

#### Étape 3 : Microservices métier
Démarrer dans n'importe quel ordre :

```bash
# Terminal 1
cd Customer-Service
mvn spring-boot:run

# Terminal 2
cd Inventory-Service
mvn spring-boot:run

# Terminal 3
cd Billing-Service
mvn spring-boot:run
```

#### Étape 4 : Gateway Service
```bash
cd Gateway-Service
mvn spring-boot:run
```

### 4. Vérification

Accéder au dashboard Eureka : `http://localhost:8761`

Vous devriez voir tous les services enregistrés :
- CUSTOMER-SERVICE
- INVENTORY-SERVICE
- BILLING-SERVICE
- GATEWAY-SERVICE

## ⚙️ Configuration

### Configuration Eureka (Discovery Service)

```yaml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
  server:
    enable-self-preservation: false
```

### Configuration des microservices

Chaque microservice contient une configuration similaire :

```yaml
server:
  port: 8081  # Port spécifique au service

spring:
  application:
    name: CUSTOMER-SERVICE
  cloud:
    config:
      enabled: true
      uri: http://localhost:9999

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
  instance:
    prefer-ip-address: true
```

### Configuration Gateway

```yaml
server:
  port: 9999

spring:
  application:
    name: GATEWAY-SERVICE
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
```

## 📡 Utilisation

### Accès via Gateway

Toutes les requêtes doivent passer par la Gateway :

**Base URL :** `http://localhost:8888`

### Exemples de requêtes

#### Clients

```bash
# Créer un client
curl -X POST http://localhost:8888/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com"
  }'

# Récupérer tous les clients
curl http://localhost:8888/customers

# Récupérer un client par ID
curl http://localhost:8888/customers/1
```

#### Produits

```bash
# Créer un produit
curl -X POST http://localhost:8888/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "price": 999.99,
    "quantity": 50
  }'

# Récupérer tous les produits
curl http://localhost:8888/products

# Récupérer un produit par ID
curl http://localhost:8888/products/1
```

#### Factures

```bash
# Créer une facture
curl -X POST http://localhost:8888/bills \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "productIds": [1, 2, 3]
  }'

# Récupérer toutes les factures
curl http://localhost:8888/bills

# Récupérer une facture avec détails
curl http://localhost:8888/bills/1
```

## 🔌 Endpoints API

### Customer Service

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/customers` | Liste tous les clients |
| GET | `/customers/{id}` | Récupère un client par ID |
| POST | `/customers` | Crée un nouveau client |
| PUT | `/customers/{id}` | Met à jour un client |
| DELETE | `/customers/{id}` | Supprime un client |

### Inventory Service

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/products` | Liste tous les produits |
| GET | `/products/{id}` | Récupère un produit par ID |
| POST | `/products` | Crée un nouveau produit |
| PUT | `/products/{id}` | Met à jour un produit |
| DELETE | `/products/{id}` | Supprime un produit |

### Billing Service

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/bills` | Liste toutes les factures |
| GET | `/bills/{id}` | Récupère une facture complète |
| POST | `/bills` | Crée une nouvelle facture |

## 🛠️ Technologies utilisées

### Framework & Librairies

- **Spring Boot** - Framework d'application
- **Spring Cloud** - Microservices patterns
  - Spring Cloud Gateway - API Gateway
  - Spring Cloud Netflix Eureka - Service Discovery
  - Spring Cloud Config - Configuration centralisée
  - Spring Cloud OpenFeign - Client REST déclaratif
- **Spring Data JPA** - Persistence
- **H2 Database** - Base de données en mémoire
- **Lombok** - Réduction du code boilerplate
- **Maven** - Gestion des dépendances

### Patterns implémentés

- ✅ **API Gateway Pattern** - Point d'entrée unique
- ✅ **Service Discovery Pattern** - Découverte dynamique des services
- ✅ **Externalized Configuration** - Configuration centralisée
- ✅ **Circuit Breaker** - Gestion de la résilience (via OpenFeign)
- ✅ **Load Balancing** - Répartition de charge
- ✅ **Service Registry** - Enregistrement des services

## 📊 Architecture technique

### Ports des services

| Service | Port | Description |
|---------|------|-------------|
| Config Service | 9999 | Configuration centralisée |
| Discovery Service | 8761 | Eureka Server |
| Gateway Service | 8888 | API Gateway |
| Customer Service | 8081 | Gestion des clients |
| Inventory Service | 8082 | Gestion des produits |
| Billing Service | 8083 | Gestion des factures |

### Dépendances entre services

```
Billing Service
├── → Customer Service (via OpenFeign)
└── → Inventory Service (via OpenFeign)

Gateway Service
├── → Discovery Service (Service discovery)
└── → Tous les microservices (Routing)

Tous les services
├── → Config Service (Configuration)
└── → Discovery Service (Enregistrement)
```

## 🔍 Monitoring et Debugging

### Dashboard Eureka
Accéder à `http://localhost:8761` pour voir :
- Les services enregistrés
- Le statut de santé des services
- Les instances disponibles

### Logs
Chaque service produit des logs dans la console. Pour activer des logs détaillés :

```yaml
logging:
  level:
    org.springframework.cloud: DEBUG
    com.netflix.eureka: DEBUG
```

## 🚦 Troubleshooting

### Problème : Service non enregistré dans Eureka

**Solution :**
1. Vérifier que Discovery Service est démarré
2. Vérifier la configuration eureka.client.service-url.defaultZone
3. Attendre 30 secondes pour l'enregistrement

### Problème : Gateway ne route pas correctement

**Solution :**
1. Vérifier que les services sont enregistrés dans Eureka
2. Vérifier la configuration des routes dans application.yml
3. Activer les logs DEBUG pour Spring Cloud Gateway

### Problème : OpenFeign - Connection refused

**Solution :**
1. Vérifier que les services cibles sont démarrés
2. Vérifier l'enregistrement dans Eureka
3. Vérifier les noms de services dans les interfaces Feign

## 📝 Améliorations futures

- [ ] Ajouter Spring Security pour l'authentification et l'autorisation
- [ ] Implémenter Circuit Breaker avec Resilience4j
- [ ] Ajouter des tests unitaires et d'intégration
- [ ] Implémenter la traçabilité distribuée avec Sleuth et Zipkin
- [ ] Ajouter une base de données PostgreSQL
- [ ] Containerisation avec Docker et Docker Compose
- [ ] Déploiement sur Kubernetes
- [ ] Ajouter Swagger/OpenAPI pour la documentation des APIs

## 👤 Auteur

**Zakaria Elguazzar**

- GitHub: [@ZakariaElguazzar](https://github.com/ZakariaElguazzar)
- Repository: [EcomProject](https://github.com/ZakariaElguazzar/EcomProject)

## 📄 License

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

---

⭐ **N'oubliez pas de mettre une étoile au projet si vous l'avez trouvé utile !** ⭐
