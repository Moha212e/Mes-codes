using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.Json;
using System.Windows;
using MyShapeLibrary;

namespace Voyago
{
    public partial class AllUserWindow : Window
    {
        private List<User> users;

        public AllUserWindow()
        {
            InitializeComponent();
            LoadUsers();
        }

        private void LoadUsers()
        {
            if (!File.Exists(User.UsersFilePath))
            {
                MessageBox.Show("Le fichier des utilisateurs n'existe pas.");
                return;
            }

            string json = File.ReadAllText(User.UsersFilePath);
            if (string.IsNullOrWhiteSpace(json))
            {
                MessageBox.Show("Le fichier des utilisateurs est vide.");
                return;
            }

            users = JsonSerializer.Deserialize<List<User>>(json);
            RemoveEmptyUsers();
            UsersDataGrid.ItemsSource = users;
        }

        private void RemoveEmptyUsers()
        {
            users = users.Where(u => !string.IsNullOrWhiteSpace(u.Email) && !string.IsNullOrWhiteSpace(u.Password)).ToList();
        }

        private void SaveChanges_Click(object sender, RoutedEventArgs e)
        {
            string json = JsonSerializer.Serialize(users, new JsonSerializerOptions { WriteIndented = true });
            File.WriteAllText(User.UsersFilePath, json);
            MessageBox.Show("Modifications enregistrées avec succès.");
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