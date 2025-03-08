using System;
using System.Collections.Generic;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Windows;

namespace Voyago
{
    public partial class AddUserWindow : Window
    {
        private const string UsersFilePath = "C:\\Users\\pro\\Documents\\c#\\code\\C\\c#\\Voyago\\Voyago\\Data\\users.json";

        public AddUserWindow()
        {
            InitializeComponent();
        }

        private void AddUserButton_Click(object sender, RoutedEventArgs e)
        {
            string email = EmailBox.Text;
            string password = PasswordBox.Password;
            string confirmPassword = ConfirmPasswordBox.Password;
            string firstName = FirstNameBox.Text;
            string lastName = LastNameBox.Text;
            bool isAdmin = IsAdminCheckBox.IsChecked ?? false;

            if (password != confirmPassword)
            {
                MessageBox.Show("Les mots de passe ne correspondent pas.");
                return;
            }

            List<User> users = LoadUsers();

            if (users.Exists(u => u.Email == email))
            {
                MessageBox.Show("Un utilisateur avec cet email existe déjà.");
                return;
            }

            // Hacher le mot de passe
            string hashedPassword = HashPassword(password);

            users.Add(new User { Email = email, Password = hashedPassword, FirstName = firstName, LastName = lastName, IsAdmin = isAdmin });
            if (SaveUsers(users))
            {
                MessageBox.Show("Utilisateur ajouté avec succès.");
                DashBoardAdmin dashBoardAdmin = new DashBoardAdmin();
                dashBoardAdmin.Show();
                this.Close();
            }
        }

        private List<User> LoadUsers()
        {
            if (!File.Exists(UsersFilePath))
            {
                return new List<User>();
            }

            string json = File.ReadAllText(UsersFilePath);
            return JsonSerializer.Deserialize<List<User>>(json);
        }

        private bool SaveUsers(List<User> users)
        {
            try
            {
                string json = JsonSerializer.Serialize(users, new JsonSerializerOptions { WriteIndented = true });
                File.WriteAllText(UsersFilePath, json);
                return true;
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Erreur lors de l'enregistrement des utilisateurs : {ex.Message}");
                return false;
            }
        }

        private string HashPassword(string password)
        {
            using (SHA256 sha256 = SHA256.Create())
            {
                byte[] bytes = Encoding.UTF8.GetBytes(password);
                byte[] hash = sha256.ComputeHash(bytes);
                return BitConverter.ToString(hash).Replace("-", "").ToLower();
            }
        }

        private void HomeMenuItem_Click(object sender, RoutedEventArgs e)
        {
            DashBoardAdmin dashBoardAdmin = new DashBoardAdmin();
            dashBoardAdmin.Show();
            this.Close();
        }

        private void Deconnect(object sender, RoutedEventArgs e)
        {
            // Réinitialiser les informations de session
            SessionManager.ClearSession();

            // Naviguer vers la fenêtre de connexion
            MainWindow mainWindow = new MainWindow();
            mainWindow.Show();
            this.Close();
        }
    }
}

