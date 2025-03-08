using System;
using System.Collections.Generic;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;

namespace Voyago
{
    public partial class MainWindow : Window
    {
        private const string UsersFilePath = @"DaTa\user.json";

        public MainWindow()
        {
            InitializeComponent();
        }

        private void NavigateToInscription(object sender, MouseButtonEventArgs e)
        {
            RegisterWindow registerWindow = new RegisterWindow();
            registerWindow.Show();
            this.Close();
        }

        private void TextBox_TextChanged(object sender, TextChangedEventArgs e)
        {
        }

        private bool UserExists(string email, string password)
        {
            List<User> users = LoadUsers();
            string hashedPassword = HashPassword(password);
            return users.Exists(u => u.Email == email && u.Password == hashedPassword);
        }

        private void ConnectButton_Click(object sender, RoutedEventArgs e)
        {
            string email = EmailBox.Text;
            string password = PasswordBox.Password;

            List<User> users = LoadUsers();
            User user = users.FirstOrDefault(u => u.Email == email && u.Password == HashPassword(password));

            if (user != null)
            {
                if (user.IsAdmin)
                {
                    MessageBox.Show($"Connexion réussie ! Bonjour, {user.LastName}");
                    DashBoardAdmin dashBoardAdmin = new DashBoardAdmin();
                    dashBoardAdmin.Show();
                }
                else
                {
                    MessageBox.Show($"Connexion réussie ! Bonjour, {user.LastName}");
                    DashBoard dashBoard = new DashBoard(user.LastName);
                    dashBoard.Show();
                }
                this.Close();
            }
            else
            {
                MessageBox.Show("Email ou mot de passe incorrect.");
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