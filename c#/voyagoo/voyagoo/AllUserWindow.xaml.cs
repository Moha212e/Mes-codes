using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.Json;
using System.Windows;

namespace Voyago
{
    public partial class AllUserWindow : Window
    {
        private const string UsersFilePath = "C:\\Users\\pro\\Documents\\c#\\code\\C\\c#\\Voyago\\Voyago\\Data\\users.json";
        private List<User> users;

        public AllUserWindow()
        {
            InitializeComponent();
            LoadUsers();
        }

        private void LoadUsers()
        {
            if (!File.Exists(UsersFilePath))
            {
                MessageBox.Show("Le fichier des utilisateurs n'existe pas.");
                return;
            }

            string json = File.ReadAllText(UsersFilePath);
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
            File.WriteAllText(UsersFilePath, json);
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