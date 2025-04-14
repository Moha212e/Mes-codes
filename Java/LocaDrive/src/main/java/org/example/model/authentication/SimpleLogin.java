package org.example.model.authentication;

import java.util.Map;

public class SimpleLogin extends LoginTemplate {
    private Map<String, String> userStore;

    public SimpleLogin(Map<String, String> userStore) {
        this.userStore = userStore;
    }

    @Override
    protected String encryptPassword(String password) {
        // Pas de chiffrement ici, on retourne simplement le mot de passe
        return password;
    }

    @Override
    protected boolean authenticate(String username, String encryptedPassword) {
        if (!userStore.containsKey(username)) return false;
        return userStore.get(username).equals(encryptedPassword);
    }
}
/**
 * # Synthèse de la classe `SimpleLogin`
 *
 * ## Description générale
 * La classe `SimpleLogin` est une implémentation concrète d'un système d'authentification simple qui hérite d'un template de login (`LoginTemplate`). Elle utilise un stockage clé-valeur pour gérer les utilisateurs et leurs mots de passe sans chiffrement.
 *
 * ## Structure et implémentation
 *
 * ### Héritage
 * - Hérite de `LoginTemplate` (implémente le pattern Template Method)
 * - Doit implémenter les méthodes abstraites `encryptPassword` et `authenticate`
 *
 * ### Attributs
 * - `userStore`: Une Map<String, String> qui sert de base de données utilisateur
 *   - Clé: nom d'utilisateur (String)
 *   - Valeur: mot de passe (String)
 *
 * ### Constructeur
 * - Prend en paramètre la Map contenant les utilisateurs et mots de passe
 * - Initialise l'attribut `userStore` avec cette Map
 *
 * ### Méthodes implémentées
 *
 * 1. **encryptPassword(String password)**
 *    - Implémentation minimale qui retourne le mot de passe en clair
 *    - Ne réalise aucun chiffrement (contrairement à ce que le nom suggère)
 *
 * 2. **authenticate(String username, String encryptedPassword)**
 *    - Vérifie si l'utilisateur existe dans le `userStore`
 *    - Compare le mot de passe fourni avec celui stocké
 *    - Retourne `true` seulement si:
 *      - L'utilisateur existe
 *      - Le mot de passe correspond exactement
 *
 * ## Caractéristiques clés
 * - **Simplicité**: Implémentation minimale sans complexité de chiffrement
 * - **Flexibilité**: Le stockage des utilisateurs est injecté via le constructeur
 * - **Insécurité**: Les mots de passe sont stockés et comparés en clair
 *
 * ## Cas d'utilisation typique
 * - Systèmes où la sécurité n'est pas critique
 * - Prototypage rapide
 * - Environnements de test
 *
 * ## Limitations
 * - **Sécurité faible**: Absence de chiffrement des mots de passe
 * - **Stockage volatile**: Dépend de la Map fournie, pas de persistance intégrée
 * - **Pas de gestion des salages**: Vulnérable aux attaques par dictionnaire
 *
 * Cette implémentation représente un système d'authentification basique qui pourrait être étendu pour des besoins plus sécurisés (ajout de chiffrement, salage, etc.).
 */