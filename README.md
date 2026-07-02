# Pay My Buddy

## Présentation

**Pay My Buddy** est une application web développée dans le cadre du **Projet 6 de la formation Développeur d'Application Java d'OpenClassrooms**.

L'objectif est de réaliser un prototype d'application permettant à un utilisateur d'effectuer des transferts d'argent entre ses contacts de manière simple et sécurisée.

Le projet est développé avec **Spring Boot** selon une architecture **MVC** en utilisant **Spring Data JPA**, **Spring Security** et **Thymeleaf**.

---

# Fonctionnalités

L'application permet de :

* créer un compte utilisateur ;
* se connecter de manière sécurisée ;
* consulter son profil ;
* ajouter une nouvelle relation ;
* effectuer un transfert d'argent vers une relation ;
* consulter l'historique de ses transactions ;
* gérer les principales erreurs métier.

---

# Technologies utilisées

* Java 21
* Spring Boot
* Spring Data JPA (Hibernate)
* Spring Security
* Thymeleaf
* MySQL
* Maven
* Lombok
* JUnit 5
* Mockito
* Spring Boot Test

---

# Architecture du projet

Le projet suit une architecture **MVC (Model – View – Controller)**.

```
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
Base de données MySQL
```

Les différentes couches ont les responsabilités suivantes :

* **Controller** : reçoit les requêtes HTTP et prépare les vues.
* **Service** : contient la logique métier de l'application.
* **Repository** : assure les échanges avec la base de données via Spring Data JPA.
* **Model** : représente les entités métier en base.

---

# Base de données

La base de données comporte les entités suivantes :

* User
* Account
* UserConnection
* Transaction

Le **Modèle Physique de Données (MPD)** est disponible dans le dossier :

```
docs/
```

<img width="890" height="691" alt="Diagramme_EER" src="https://github.com/user-attachments/assets/07a09619-9d7e-4750-bf42-3736f4df3df8" />


Les scripts SQL sont disponibles dans :

```
sql/
```

* **schema.sql** : création de la base de données et des tables.
* **data.sql** : données de démonstration permettant de tester rapidement l'application.

---

# Installation

## Prérequis

Les éléments suivants doivent être installés :

* Java 21
* Maven
* MySQL Workbench
* IntelliJ IDEA (ou tout autre IDE compatible)

## Configuration

1. Créer une base de données MySQL.
2. Exécuter le script `schema.sql`.
3. (Facultatif) Exécuter le script `data.sql` afin d'importer des données de démonstration.
4. Configurer la connexion à la base de données dans le fichier :

```
src/main/resources/application.properties
```

en renseignant :

```
spring.datasource.url
spring.datasource.username
spring.datasource.password
```

## Lancement de l'application

Ouvrir le projet dans IntelliJ IDEA puis exécuter la classe :

```
PaymybuddyApplication
```

L'application sera disponible à l'adresse :

```
http://localhost:8080
```

---

# Sécurité

L'application utilise **Spring Security** pour gérer l'authentification des utilisateurs.

Les mots de passe sont chiffrés avec **BCrypt** avant leur enregistrement en base de données.

Les pages nécessitant une authentification sont protégées et les exceptions sont gérées de manière centralisée grâce à un `ControllerAdvice`.

---

# Gestion des transactions

Les transferts d'argent sont réalisés dans une méthode annotée `@Transactional`.

Cette approche garantit l'intégrité des données :

* le débit et le crédit sont réalisés ensemble ;
* en cas d'erreur, aucune modification n'est enregistrée en base de données.

---

# Tests

Le projet comporte des **tests unitaires** et des **tests d'intégration**.

Les tests unitaires couvrent notamment :

* TransactionService
* UserService
* UserConnectionService
* CustomUserDetailsService

Les tests d'intégration vérifient :

* le démarrage du contexte Spring ;
* la sécurité des différentes pages ;
* les redirections des utilisateurs authentifiés et non authentifiés.

Tous les tests peuvent être exécutés avec :

```
mvn test
```

---

# Améliorations possibles

Le projet constitue un prototype fonctionnel.

Plusieurs évolutions pourraient être envisagées :

* édition complète du profil utilisateur ;
* pagination de l'historique des transactions ;
* validation avancée des formulaires ;
* notifications utilisateurs ;
* interface responsive ;
* gestion des frais de transaction.

---

# Auteur
Denis San Vicente
Projet réalisé dans le cadre de la formation **Développeur d'Application Java** d'OpenClassrooms.
