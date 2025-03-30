using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;
using System.Windows;
using System.Windows.Controls;
using MyShapeClass;

namespace voyagoo
{
    public partial class EditerUtilisateurs : Window
    {
        private static string filePath = "Data/users.json";
        private List<User> users; // Déclaration de la variable users

        public EditerUtilisateurs()
        {
            InitializeComponent();
            users = User.LoadUsers(); // Correction de l'affectation
            UsersDataGrid.ItemsSource = users; // Correction de l'affectation
        }

        // Enregistre les modifications
        private void Enregistrer_Click(object sender, RoutedEventArgs e)
        {
            User.saveUser(users);
            MessageBox.Show("Modifications enregistrées !", "Succès", MessageBoxButton.OK, MessageBoxImage.Information);
        }

        // Méthode pour revenir à la fenêtre IndexAdmin
        private void Retour_Click(object sender, RoutedEventArgs e)
        {
            IndexAdmin indexAdmin = new IndexAdmin();
            indexAdmin.Show();
            this.Close();
        }
    }
}