using System;
using System.Collections.Generic;
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

    // Enumération des erreurs d'authentification
    public enum AuthenticationError
    {
        Success = 0,
        UserNotFound = 1,
        InvalidPassword = 2,
        UserDeleted = 3,
        UserNotAdmin = 4,
        InvalidPasswordCo = 5,
        InvalidEmail = 6,
        Error = 7
    }

    // Enumération des erreurs d'inscription
    public enum RegistrationError
    {
        Success = 0,
        InvalidEmail = 1,
        InvalidPassword = 2,
        InvalidFirstName = 3,
        InvalidLastName = 4,
        InvalidDateOfBirth = 5,
        EmailAlreadyExists = 6
    }

    #endregion Enums

    public class User
    {
        #region Champs et propriétés

        // Chemin du fichier de stockage des utilisateurs
        private static readonly string _fileName = "C:\\Users\\pasch\\Documents\\Mes-codes\\c#\\perso\\voyaggo\\bin\\Debug\\net8.0-windows\\Data\\users.json";

        // Propriétés de l'utilisateur
        public int _IdUser { get; set; }

        public string _firstName { get; set; }
        public string _lastName { get; set; }
        public string _email { get; set; }
        public string _password { get; set; }
        public DateTime _dateBirth { get; set; }
        public bool _isAdmin { get; set; }
        public bool _isDeleted { get; set; }

        #endregion Champs et propriétés

        #region Constructeurs

        // Constructeur par défaut
        public User()
        { }

        // Constructeur complet
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

        // Constructeur simplifié pour l'authentification
        public User(string email, string password)
        {
            _email = email;
            _password = password;
        }

        #endregion Constructeurs

        #region Méthodes publiques

        // Authentifie un utilisateur
        public static int Authenticate(string email, string password)
        {
            var users = LoadUsers();

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

        // Enregistre un nouvel utilisateur
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

        public static User addSession(string email)
        {
            var users = LoadUsers();
            var user = users.Find(u => u._email == email);
            return user;
        }

        #endregion Méthodes publiques

        #region Méthodes privées

        // Charge la liste des utilisateurs depuis le fichier JSON
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
            catch (JsonException ex)
            {
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

        public static void saveUser(List<User> users)
        {
            string json = JsonSerializer.Serialize(users);
            File.WriteAllText(_fileName, json);
        }

        #endregion Méthodes privées

        #region Validation

        // Valide le format de l'email
        private static bool valideMail(string email)
        {
            if (string.IsNullOrWhiteSpace(email))
                return false;
            return Regex.IsMatch(email, @"^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$");
        }

        // Valide le format du mot de passe
        private static bool validePassword(string password)
        {
            if (string.IsNullOrWhiteSpace(password))
                return false;
            return Regex.IsMatch(password, @"^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,15}$");
        }

        // Valide le prénom
        private static bool valideFirstName(string firstName)
        {
            if (string.IsNullOrWhiteSpace(firstName))
                return false;
            return Regex.IsMatch(firstName, @"^[a-zA-Z]+$");
        }

        // Valide le nom de famille
        private static bool valideLastName(string lastName)
        {
            if (string.IsNullOrWhiteSpace(lastName))
                return false;
            return Regex.IsMatch(lastName, @"^[a-zA-Z]+$");
        }

        // Valide la date de naissance
        private static bool valideDateOfBirth(DateTime dateOfBirth)
        {
            if (dateOfBirth == null)
                return false;
            return dateOfBirth < DateTime.Now;
        }

        #endregion Validation

        #region Utilitaires

        // Hash le mot de passe avec SHA256
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