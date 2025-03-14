using System;
using System.Collections.Generic;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Text.RegularExpressions;
using Microsoft.Win32;

namespace Voyago
{
    public class User
    {
        public const string UsersFilePath = @"Data\user.json";
        public required string id_user { get; set; }
        public required string Email { get; set; }
        public required string Password { get; set; }
        public string LastName { get; set; }
        public string FirstName { get; set; }
        public bool IsAdmin { get; set; }

        #region User Methods

        /// <summary>
        /// Charge les utilisateurs depuis le fichier JSON.
        /// </summary>
        public static List<User> LoadUsers()
        {
            if (!File.Exists(UsersFilePath))
            {
                File.WriteAllText(UsersFilePath, "[]");
                return new List<User>();
            }

            string json = File.ReadAllText(UsersFilePath);
            return JsonSerializer.Deserialize<List<User>>(json);
        }

        /// <summary>
        /// Charge les paramètres utilisateur depuis le registre.
        /// </summary>
        public static void LoadUserSettings(MyAppParamManager paramManager)
        {
            try
            {
                paramManager.LoadRegistryParameters();
            }
            catch (Exception ex)
            {
                throw new Exception("Erreur lors du chargement des paramètres du registre", ex);
            }
        }

        /// <summary>
        /// Enregistre les paramètres utilisateur dans le registre.
        /// </summary>
        public static void SaveUserSettings(MyAppParamManager paramManager, string destination, string priceMin, string priceMax, DateTime? travelDate)
        {
            try
            {
                paramManager.Destination = destination ?? "";
                paramManager.PriceMin = double.TryParse(priceMin, out double min) ? min : 0;
                paramManager.PriceMax = double.TryParse(priceMax, out double max) ? max : 0;
                paramManager.TravelDate = travelDate ?? DateTime.Now;

                paramManager.SaveRegistryParameters();
            }
            catch (Exception ex)
            {
                throw new Exception("Erreur lors de l'enregistrement des paramètres dans le registre", ex);
            }
        }
        /// <summary>
        /// Vérifie si un utilisateur existe avec l'email et le mot de passe donnés.
        /// </summary>
        public static bool UserExists(string email, string password)
        {
            List<User> users = LoadUsers();
            string hashedPassword = HashPassword(password);
            return users.Exists(u => u.Email == email && u.Password == hashedPassword);
        }

        /// <summary>
        /// Hache le mot de passe donné.
        /// </summary>
        private static string HashPassword(string password)
        {
            using SHA256 sha256 = SHA256.Create();
            byte[] bytes = Encoding.UTF8.GetBytes(password);
            byte[] hash = sha256.ComputeHash(bytes);
            return BitConverter.ToString(hash).Replace("-", "").ToLower();
        }
        /// <summary>
        /// Récupère un utilisateur par email et mot de passe.
        /// </summary>
        public static User GetUserByEmailAndPassword(string email, string password)
        {
            List<User> users = LoadUsers();
            string hashedPassword = HashPassword(password);
            return users.FirstOrDefault(u => u.Email == email && u.Password == hashedPassword);
        }
        public static void SaveUsers(List<User> users)
        {
            string json = JsonSerializer.Serialize(users, new JsonSerializerOptions { WriteIndented = true });
            File.WriteAllText(UsersFilePath, json);
        }
        public static void EnsureDataDirectoryExists()
        {
            string directory = Path.GetDirectoryName(UsersFilePath);
            if (!Directory.Exists(directory))
            {
                Directory.CreateDirectory(directory);
            }
        }
        public static void Register(string email , string password , string firstName, string lastName , bool isAdmin)
        {
            List<User> users = LoadUsers();
            if (users.Exists(u => u.Email == email))
            {
                throw new Exception("Un utilisateur avec cet email existe déjà.");

            }
            string hashedPassword = HashPassword(password);
            string id_user = Guid.NewGuid().ToString();
            users.Add(new User { id_user = id_user, Email = email, Password = hashedPassword, FirstName = firstName, LastName = lastName, IsAdmin = isAdmin });
            SaveUsers(users);
        }
        public static bool IsValidEmail(string email)
        {
            string pattern = @"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$";
            return Regex.IsMatch(email, pattern);
        }

        public static bool IsValidPassword(string password)
        {
            // Regex : Au moins 8 caractères, 1 majuscule et 1 chiffre
            string pattern = @"^(?=.*[A-Z])(?=.*\d).{8,}$";
            return Regex.IsMatch(password, pattern);
        }

        #endregion
    }
}
