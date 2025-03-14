using System;
using System.IO;
using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Input;

namespace Voyago
{
    public partial class RegisterWindow : Window
    {
        public RegisterWindow()
        {
            InitializeComponent();
            User.EnsureDataDirectoryExists();
        }

        private void RegisterButton_Click(object sender, RoutedEventArgs e)
        {
            string email = EmailBox.Text;
            string password = PasswordBox.Password;
            string confirmPassword = ConfirmPasswordBox.Password;
            string firstName = FirstNameBox.Text;
            string lastName = LastNameBox.Text;
            bool isAdmin = false;

            if (password != confirmPassword )
            {
                MessageBox.Show("Les mots de passe ne correspondent pas.");
                return;
            }

            if (!User.IsValidEmail(email))
            {
                MessageBox.Show("Email invalide.");
                return;
            }

            if (!User.IsValidPassword(password))
            {
                MessageBox.Show("❌ Mot de passe invalide.\n\n🔒 Règles :\n- Minimum 8 caractères\n- Au moins une majuscule\n- Au moins un chiffre", "Erreur de validation");
                return;
            }
            

            try
            {
                User.Register(email, password, firstName, lastName,isAdmin);
                MessageBox.Show("Inscription réussie !");
                DashBoard dashboard = new DashBoard(lastName);
                dashboard.Show();
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

        private void NavigateToLogin(object sender, MouseButtonEventArgs e)
        {
            MainWindow mainWindow = new MainWindow();
            mainWindow.Show();
            this.Close();
        }

    }
}