using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Text.RegularExpressions;

namespace MyShapeClass
{
    public class User
    {
        // Déclaration des codes d'erreur
        public const int ERROR_PASSWORD_MISMATCH = 1001;

        public const int ERROR_INVALID_EMAIL = 1002;
        public const int ERROR_USER_NOT_FOUND = 1003;
        public const int ERROR_INCORRECT_PASSWORD = 1004;
        public const int ERROR_FILE_NOT_FOUND = 1005;

        private static string filePath = "Data/users.json";
        public string id_user { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public string PasswordConfirm { get; set; }
        private DateTime DateOfBirth { get; set; }
        public bool isAdmin { get; set; }

        // Méthode pour hacher le mot de passe
        public static string HashPassword(string password)
        {
            using (SHA256 sha256Hash = SHA256.Create())
            {
                // Convertir le mot de passe en tableau d'octets
                byte[] bytes = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(password));

                // Convertir les octets en une chaîne hexadécimale
                StringBuilder builder = new StringBuilder();
                foreach (byte b in bytes)
                {
                    builder.Append(b.ToString("x2"));
                }
                return builder.ToString(); // Retourne le mot de passe haché
            }
        }

        public static int isEqualsPassword(string password, string passwordConfirm)
        {
            if (password == passwordConfirm)
            {
                return 0; // Pas d'erreur
            }
            return ERROR_PASSWORD_MISMATCH;
        }

        public static int isMailValid(string email)
        {
            string pattern = @"^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$";
            if (Regex.IsMatch(email, pattern))
            {
                return 0; // Pas d'erreur
            }
            return ERROR_INVALID_EMAIL;
        }

        // Méthode pour vérifier si un utilisateur existe en fonction de l'email et du mot de passe
        public static int isUserExist(string email, string password)
        {
            // Vérifie si le fichier existe
            if (File.Exists(filePath))
            {
                // Lire le contenu du fichier JSON
                string jsonContent = File.ReadAllText(filePath);

                // Désérialiser le contenu JSON en une liste d'utilisateurs
                var users = JsonSerializer.Deserialize<List<User>>(jsonContent);

                // Si la liste d'utilisateurs n'est pas vide
                if (users != null)
                {
                    // Vérifie si un utilisateur avec cet email existe
                    foreach (var user in users)
                    {
                        if (user.Email == email)
                        {
                            // Compare le mot de passe haché
                            if (user.Password == HashPassword(password))  // Hachage du mot de passe
                            {
                                return user.isAdmin ? 1 : 0;  // Retourne 1 si Admin, 0 sinon
                            }
                            else
                            {
                                return ERROR_INCORRECT_PASSWORD; // Le mot de passe est incorrect
                            }
                        }
                    }
                }
                return ERROR_USER_NOT_FOUND; // L'utilisateur n'existe pas
            }
            return ERROR_FILE_NOT_FOUND; // Le fichier n'existe pas
        }

        public static int SaveUser(string mail, string password, DateTime dateNaissance)
        {
            try
            {
                DateTime date = DateTime.Now;
                User user = new User();
                user.id_user = Guid.NewGuid().ToString();
                user.Email = mail;
                user.Password = HashPassword(password);  // Hachage du mot de passe
                user.DateOfBirth = dateNaissance;
                user.isAdmin = false;

                if (File.Exists(filePath))
                {
                    string jsonContent = File.ReadAllText(filePath);
                    var users = JsonSerializer.Deserialize<List<User>>(jsonContent);

                    // Vérifie si la désérialisation a échoué
                    if (users == null)
                    {
                        return ERROR_FILE_NOT_FOUND; // Le fichier est vide ou mal formé
                    }

                    users.Add(user);
                    string json = JsonSerializer.Serialize(users);

                    // Tentative d'écriture dans le fichier
                    try
                    {
                        File.WriteAllText(filePath, json);
                    }
                    catch (Exception)
                    {
                        return ERROR_FILE_NOT_FOUND; // Impossible d'écrire dans le fichier
                    }
                }
                else
                {
                    // Création d'un nouveau fichier
                    List<User> users = new List<User>();
                    users.Add(user);
                    string json = JsonSerializer.Serialize(users);

                    // Tentative d'écriture dans le fichier
                    try
                    {
                        File.WriteAllText(filePath, json);
                    }
                    catch (Exception)
                    {
                        return ERROR_FILE_NOT_FOUND; // Impossible d'écrire dans le fichier
                    }
                }

                return 0; // Succès
            }
            catch (IOException)
            {
                return ERROR_FILE_NOT_FOUND; // Erreur d'accès au fichier
            }
            catch (Exception)
            {
                return -1; // Erreur générique
            }
        }
    }
}