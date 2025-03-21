using System;
using System.Collections.Generic;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Text.RegularExpressions;

namespace ClassLibrary1
{
    public class User
    {
        private static string pathName = "user.json"; // Fichier de stockage

        private string id_user;
        private string mail;
        private DateTime birthDate;
        private string passwordHash;
        private int age;

        // Propriétés
        public string IdUser => id_user;  // ID auto-généré, pas de set
        public string Mail
        {
            get => mail;
            set
            {
                if (IsValidEmail(value))
                    mail = value;
                else
                    throw new ArgumentException("Email invalide !");
            }
        }

        public DateTime BirthDate
        {
            get => birthDate;
            set
            {
                birthDate = value;
                age = CalculateAge(birthDate);
            }
        }

        public int Age => age;  // Âge auto-calculé

        public string PasswordHash
        {
            get => passwordHash;
            set => passwordHash = HashPassword(value);
        }

        // Constructeur
        public User(string mail, DateTime birthDate, string password)
        {
            List<User> users = LoadUsers();
            this.id_user = GenerateUserId(users);
            this.Mail = mail;
            this.BirthDate = birthDate;
            this.PasswordHash = password;
        }

        // Vérifie le format de l'email
        private static bool IsValidEmail(string email)
        {
            string pattern = @"^[^@\s]+@[^@\s]+\.[^@\s]+$";
            return Regex.IsMatch(email, pattern);
        }

        // Génère un ID unique basé sur le nombre d'utilisateurs
        private static string GenerateUserId(List<User> users)
        {
            return "U" + (users.Count + 1).ToString("D4"); // Format : U0001, U0002...
        }

        // Calcule l'âge à partir de la date de naissance
        public static int CalculateAge(DateTime birthDate)
        {
            int age = DateTime.Now.Year - birthDate.Year;
            if (DateTime.Now < birthDate.AddYears(age)) age--; // Ajustement si l'anniversaire n'est pas encore passé
            return age;
        }

        // Hachage du mot de passe en SHA256
        private static string HashPassword(string password)
        {
            using (SHA256 sha256 = SHA256.Create())
            {
                byte[] bytes = sha256.ComputeHash(Encoding.UTF8.GetBytes(password));
                StringBuilder builder = new StringBuilder();
                foreach (byte b in bytes)
                {
                    builder.Append(b.ToString("x2"));
                }
                return builder.ToString();
            }
        }

        // Charge les utilisateurs depuis JSON
        private static List<User> LoadUsers()
        {
            if (!File.Exists(pathName))
                return new List<User>();

            string json = File.ReadAllText(pathName);
            return JsonSerializer.Deserialize<List<User>>(json) ?? new List<User>();
        }

        // Sauvegarde les utilisateurs dans JSON
        private static void SaveUsers(List<User> users)
        {
            string json = JsonSerializer.Serialize(users, new JsonSerializerOptions { WriteIndented = true });
            File.WriteAllText(pathName, json);
        }

        // Inscription d'un nouvel utilisateur
        public static bool Register(User newUser)
        {
            List<User> users = LoadUsers();

            // Vérifie si l'email est déjà utilisé
            if (users.Exists(u => u.Mail == newUser.Mail))
            {
                Console.WriteLine("Cet email est déjà utilisé !");
                return false;
            }

            users.Add(newUser);
            SaveUsers(users);
            Console.WriteLine("Inscription réussie !");
            return true;
        }

        // Vérification de l'utilisateur (Connexion)
        public static bool Login(string email, string password)
        {
            List<User> users = LoadUsers();
            string hashedPassword = HashPassword(password);

            if (users.Exists(u => u.Mail == email && u.PasswordHash == hashedPassword))
            {
                Console.WriteLine("Connexion réussie !");
                return true;
            }

            Console.WriteLine("Email ou mot de passe incorrect !");
            return false;
        }
        public static bool verifyPassword(string password, string password2)
        {
            if (password != password2)
            {
                return false;
            }
            return true;
        }
    }
}
