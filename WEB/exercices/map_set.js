// Exercice Map

// Objets utilisateurs
const user1 = { id: 1, name: 'Alice' };
const user2 = { id: 2, name: 'Bob' };
const user3 = { id: 3, name: 'Charlie' };

// Création de la Map avec les utilisateurs prédéfinis
const userRoles = new Map([
    [user1, 'Admin'],
    [user2, 'User'],
    [user3, 'Guest']
]);

// Fonction pour ajouter un nouvel utilisateur
function addUser(user, role) {
    userRoles.set(user, role);
}

// Fonction pour modifier le rôle d'un utilisateur existant
function updateUserRole(user, newRole) {
    if (userRoles.has(user)) {
        userRoles.set(user, newRole);
    } else {
        console.log('Utilisateur non trouvé');
    }
}

// Fonction pour supprimer un utilisateur
function deleteUser(user) {
    userRoles.delete(user);
}

// Affichage de la liste complète des utilisateurs
function displayUsers() {
    userRoles.forEach((role, user) => {
        console.log(`Utilisateur: ${user.name}, Rôle: ${role}`);
    });
}

// Test des fonctions
addUser({ id: 4, name: 'David' }, 'User');
updateUserRole(user2, 'Admin');
deleteUser(user3);
displayUsers();

// Exercice Set

// Tableau avec des nombres contenant des doublons
const numbers = [1, 2, 2, 3, 4, 4, 5];

// Conversion en Set pour éliminer les doublons
const uniqueNumbers = new Set(numbers);

// Vérification de la présence d'une valeur spécifique
console.log(uniqueNumbers.has(3)); // true
console.log(uniqueNumbers.has(6)); // false

// Affichage des valeurs uniques
console.log([...uniqueNumbers]);

// Création d'un second Set
const otherNumbers = new Set([3, 4, 5, 6, 7]);

// Intersection des deux ensembles
const intersection = new Set([...uniqueNumbers].filter(x => otherNumbers.has(x)));
console.log([...intersection]);