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

            try
            {
                User.Register(email, password, firstName, lastName , isAdmin);
                MessageBox.Show("Inscription réussie !");
                DashBoardAdmin dashboardAdmin = new DashBoardAdmin();
                dashboardAdmin.Show();
                this.Close();
            }
            catch (UnauthorizedAccessException ex)
            {
                MessageBox.Show($"Erreur d'accès : {ex.Message}", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
            }
            catch (IOException ex)
            {
                MessageBox.Show($"Erreur d'entrée/sortie : {ex.Message}", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Une erreur est survenue : {ex.Message}", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
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
