using System;
using System.Collections.Generic;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Windows;
using System.Windows.Input;

namespace Voyago
{
    public partial class RegisterWindow : Window
    {
        private const string UsersFilePath = "C:\\Users\\pro\\Documents\\c#\\code\\C\\c#\\Voyago\\Voyago\\Data\\users.json";
        public RegisterWindow()
        {
            InitializeComponent();
            EnsureDataDirectoryExists();
        }

        private void RegisterButton_Click(object sender, RoutedEventArgs e)
        {
            string email = EmailBox.Text;
            string password = PasswordBox.Password;
            string confirmPassword = ConfirmPasswordBox.Password;
            string firstName = FirstNameBox.Text; // Ajout du prénom
            string lastName = LastNameBox.Text;   // 

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

            users.Add(new User { Email = email, Password = hashedPassword, FirstName = firstName, LastName = lastName ,IsAdmin = false});
            SaveUsers(users);

            DashBoard dashboard = new DashBoard(email);
            dashboard.Show();
            this.Close();
        }

        private void NavigateToLogin(object sender, MouseButtonEventArgs e)
        {
            MainWindow mainWindow = new MainWindow();
            mainWindow.Show();
            this.Close();
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

        private void SaveUsers(List<User> users)
        {
            string json = JsonSerializer.Serialize(users, new JsonSerializerOptions { WriteIndented = true });
            File.WriteAllText(UsersFilePath, json);
        }

        private void EnsureDataDirectoryExists()
        {
            string directory = Path.GetDirectoryName(UsersFilePath);
            if (!Directory.Exists(directory))
            {
                Directory.CreateDirectory(directory);
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
    }


}
