using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Runtime.CompilerServices;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Text.RegularExpressions;

namespace MyShapeClass
{
    #region Enums

    /// <summary>
    /// Enumération des erreurs possibles lors de l'authentification d'un utilisateur.
    /// </summary>
    public enum AuthenticationError
    {
        /// <summary>Authentification réussie</summary>
        Success = 0,

        /// <summary>Utilisateur non trouvé</summary>
        UserNotFound = 1,

        /// <summary>Mot de passe invalide</summary>
        InvalidPassword = 2,

        /// <summary>Utilisateur supprimé</summary>
        UserDeleted = 3,

        /// <summary>Utilisateur non administrateur</summary>
        UserNotAdmin = 4,

        /// <summary>Mot de passe de connexion invalide</summary>
        InvalidPasswordCo = 5,

        /// <summary>Email invalide</summary>
        InvalidEmail = 6,

        /// <summary>Erreur générale</summary>
        Error = 7
    }

    /// <summary>
    /// Enumération des erreurs possibles lors de l'inscription d'un utilisateur.
    /// </summary>
    public enum RegistrationError
    {
        /// <summary>Inscription réussie</summary>
        Success = 0,

        /// <summary>Email invalide</summary>
        InvalidEmail = 1,

        /// <summary>Mot de passe invalide</summary>
        InvalidPassword = 2,

        /// <summary>Prénom invalide</summary>
        InvalidFirstName = 3,

        /// <summary>Nom de famille invalide</summary>
        InvalidLastName = 4,

        /// <summary>Date de naissance invalide</summary>
        InvalidDateOfBirth = 5,

        /// <summary>Email déjà existant</summary>
        EmailAlreadyExists = 6
    }

    #endregion Enums

    /// <summary>
    /// Classe représentant un utilisateur du système.
    /// Gère l'authentification, l'inscription et la gestion des utilisateurs.
    /// </summary>
    public class User
    {
        #region Champs et propriétés

        /// <summary>
        /// Chemin du fichier de stockage des utilisateurs
        /// </summary>
        private static readonly string _fileName = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "Data", "users.json");

        /// <summary>
        /// Identifiant unique de l'utilisateur
        /// </summary>
        public int _IdUser { get; set; }

        /// <summary>
        /// Prénom de l'utilisateur
        /// </summary>
        public string _firstName { get; set; }

        /// <summary>
        /// Nom de famille de l'utilisateur
        /// </summary>
        public string _lastName { get; set; }

        /// <summary>
        /// Adresse email de l'utilisateur (identifiant de connexion)
        /// </summary>
        public string _email { get; set; }

        /// <summary>
        /// Mot de passe hashé de l'utilisateur
        /// </summary>
        public string _password { get; set; }

        /// <summary>
        /// Date de naissance de l'utilisateur
        /// </summary>
        public DateTime _dateBirth { get; set; }

        /// <summary>
        /// Indique si l'utilisateur a des droits d'administrateur
        /// </summary>
        public bool _isAdmin { get; set; }

        /// <summary>
        /// Indique si l'utilisateur a été supprimé logiquement
        /// </summary>
        public bool _isDeleted { get; set; }

        #endregion Champs et propriétés

        #region Constructeurs

        /// <summary>
        /// Constructeur par défaut, requis pour la désérialisation JSON
        /// </summary>
        public User()
        { }

        /// <summary>
        /// Constructeur complet pour créer un nouvel utilisateur avec tous ses attributs
        /// </summary>
        /// <param name="IdUser">Identifiant unique de l'utilisateur</param>
        /// <param name="firstName">Prénom de l'utilisateur</param>
        /// <param name="lastName">Nom de famille de l'utilisateur</param>
        /// <param name="email">Adresse email de l'utilisateur</param>
        /// <param name="password">Mot de passe hashé de l'utilisateur</param>
        /// <param name="isAdmin">Indique si l'utilisateur est administrateur</param>
        /// <param name="isDeleted">Indique si l'utilisateur est supprimé</param>
        /// <param name="date">Date de naissance de l'utilisateur</param>
        public User(int IdUser, string firstName, string lastName, string email,
                   string password, bool isAdmin, bool isDeleted, DateTime date)
        {
            _IdUser = IdUser;
            _firstName = firstName;
            _lastName = lastName;
            _email = email;
            _password = password;
            _isAdmin = isAdmin;
            _isDeleted = isDeleted;
            _dateBirth = date;
        }

        /// <summary>
        /// Constructeur simplifié pour l'authentification
        /// </summary>
        /// <param name="email">Adresse email de l'utilisateur</param>
        /// <param name="password">Mot de passe de l'utilisateur</param>
        public User(string email, string password)
        {
            _email = email;
            _password = password;
        }

        #endregion Constructeurs

        #region Méthodes publiques

        /// <summary>
        /// Authentifie un utilisateur avec son email et son mot de passe
        /// </summary>
        /// <param name="email">Email de l'utilisateur</param>
        /// <param name="password">Mot de passe de l'utilisateur</param>
        /// <returns>Code d'erreur d'authentification (0 = succès)</returns>
        public static int Authenticate(string email, string password)
        {
            var users = LoadUsers();//comparer les utilisateurs

            // Recherche l'utilisateur par email
            var user = users.FirstOrDefault(u => u._email.Equals(email, StringComparison.OrdinalIgnoreCase));

            // Vérifie si l'utilisateur existe
            if (user == null)
                return (int)AuthenticationError.UserNotFound;

            // Vérifie si l'utilisateur est supprimé
            if (user._isDeleted)
                return (int)AuthenticationError.UserDeleted;

            // Vérifie si le mot de passe est valide
            if (user._password != hashPassword(password))
                return (int)AuthenticationError.InvalidPassword;

            // Vérifie si l'utilisateur est admin
            if (!user._isAdmin)
                return (int)AuthenticationError.UserNotAdmin;

            // Authentification réussie
            return (int)AuthenticationError.Success;
        }

        /// <summary>
        /// Enregistre un nouvel utilisateur dans le système
        /// </summary>
        /// <param name="firstName">Prénom de l'utilisateur</param>
        /// <param name="lastName">Nom de famille de l'utilisateur</param>
        /// <param name="email">Adresse email de l'utilisateur</param>
        /// <param name="password">Mot de passe de l'utilisateur</param>
        /// <param name="dateOfBirth">Date de naissance de l'utilisateur</param>
        /// <returns>Code d'erreur d'inscription (0 = succès)</returns>
        public static int register(string firstName, string lastName, string email,
                          string password, DateTime dateOfBirth)
        {
            var users = LoadUsers();

            // Valide les champs
            if (!valideMail(email))
                return (int)RegistrationError.InvalidEmail;

            if (!valideFirstName(firstName))
                return (int)RegistrationError.InvalidFirstName;

            if (!valideLastName(lastName))
                return (int)RegistrationError.InvalidLastName;

            if (!valideDateOfBirth(dateOfBirth))
                return (int)RegistrationError.InvalidDateOfBirth;

            // Vérifie si l'email existe déjà
            if (users.Exists(u => u._email == email))
                return (int)RegistrationError.EmailAlreadyExists;

            // Crée et ajoute le nouvel utilisateur
            var user = new User(users.Count + 1, firstName, lastName, email,
                              hashPassword(password), false, false, dateOfBirth);
            users.Add(user);

            // Sauvegarde les utilisateurs dans le fichier
            File.WriteAllText(_fileName, JsonSerializer.Serialize(users));
            return (int)RegistrationError.Success;
        }

        /// <summary>
        /// Récupère un utilisateur pour la session courante
        /// </summary>
        /// <param name="email">Email de l'utilisateur à récupérer</param>
        /// <returns>L'objet User correspondant à l'email</returns>
        /*public static User addSession(string email)
        {
            var users = LoadUsers();
            var user = users.Find(u => u._email == email);
            return user;
        }*/

        #endregion Méthodes publiques

        #region Méthodes de gestion des données

        /// <summary>
        /// Charge la liste des utilisateurs depuis le fichier JSON
        /// </summary>
        /// <returns>Liste des utilisateurs enregistrés</returns>
        public static List<User> LoadUsers()
        {
            try
            {
                // Vérifie si le fichier existe, sinon le crée avec "[]"
                if (!File.Exists(_fileName))
                {
                    List<User> users = new List<User>();
                    string json = JsonSerializer.Serialize(users);
                    File.WriteAllText(_fileName, json);
                    return new List<User>();
                }

                // Lit le contenu du fichier
                var user = File.ReadAllText(_fileName);

                // Désérialise le JSON en liste d'utilisateurs
                return JsonSerializer.Deserialize<List<User>>(user) ?? new List<User>();
            }
            // Gère les exceptions de désérialisation JSON
            catch (JsonException ex)
            {
                // Affiche l'erreur dans la console
                Console.WriteLine($"Erreur JSON: {ex.Message}");
                File.WriteAllText(_fileName, "[]"); // Corrige en cas de JSON corrompu
                return new List<User>();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erreur inattendue: {ex.Message}");
                return new List<User>();
            }
        }

        /// <summary>
        /// Sauvegarde la liste des utilisateurs dans le fichier JSON
        /// Met a jour le fichier avec la liste actuelle
        /// </summary>
        /// <param name="users">Liste des utilisateurs à sauvegarder</param>
        /// <returns>0 si succès, 1 si erreur</returns>
        public static int saveUser(List<User> users)
        {
            try
            {
                string json = JsonSerializer.Serialize(users, new JsonSerializerOptions { WriteIndented = true });
                File.WriteAllText(_fileName, json);
                Debug.WriteLine("Utilisateurs sauvegardés avec succès");
                return 0;
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"❌ Erreur lors de la sauvegarde des utilisateurs : {ex.Message}");
                return 1;
            }
        }

        #endregion Méthodes de gestion des données

        #region Validation

        /// <summary>
        /// Valide le format de l'email
        /// </summary>
        /// <param name="email">Email à valider</param>
        /// <returns>True si l'email est valide, sinon False</returns>
        private static bool valideMail(string email)
        {
            if (string.IsNullOrWhiteSpace(email))
                return false;
            return Regex.IsMatch(email, @"^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$");
        }

        /// <summary>
        /// Valide le format du mot de passe
        /// </summary>
        /// <param name="password">Mot de passe à valider</param>
        /// <returns>True si le mot de passe est valide, sinon False</returns>
        private static bool validePassword(string password)
        {
            if (string.IsNullOrWhiteSpace(password))
                return false;
            return Regex.IsMatch(password, @"^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,15}$");
        }

        /// <summary>
        /// Valide le prénom
        /// </summary>
        /// <param name="firstName">Prénom à valider</param>
        /// <returns>True si le prénom est valide, sinon False</returns>
        private static bool valideFirstName(string firstName)
        {
            if (string.IsNullOrWhiteSpace(firstName))
                return false;
            return Regex.IsMatch(firstName, @"^[a-zA-Z]+$");
        }

        /// <summary>
        /// Valide le nom de famille
        /// </summary>
        /// <param name="lastName">Nom de famille à valider</param>
        /// <returns>True si le nom est valide, sinon False</returns>
        private static bool valideLastName(string lastName)
        {
            if (string.IsNullOrWhiteSpace(lastName))
                return false;
            return Regex.IsMatch(lastName, @"^[a-zA-Z]+$");
        }

        /// <summary>
        /// Valide la date de naissance
        /// </summary>
        /// <param name="dateOfBirth">Date de naissance à valider</param>
        /// <returns>True si la date est valide, sinon False</returns>
        private static bool valideDateOfBirth(DateTime dateOfBirth)
        {
            if (dateOfBirth == null)
                return false;
            return dateOfBirth < DateTime.Now;
        }

        #endregion Validation

        #region Utilitaires

        /// <summary>
        /// Hash le mot de passe avec l'algorithme SHA256
        /// </summary>
        /// <param name="password">Mot de passe en clair</param>
        /// <returns>Hash SHA256 du mot de passe</returns>
        private static string hashPassword(string password)
        {
            using (SHA256 sha256Hash = SHA256.Create())
            {
                // Calcule le hash
                byte[] bytes = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(password));
                StringBuilder builder = new StringBuilder();

                // Convertit le hash en string hexadécimal
                for (int i = 0; i < bytes.Length; i++)
                    builder.Append(bytes[i].ToString("x2"));

                return builder.ToString();
            }
        }

        #endregion Utilitaires
    }
}