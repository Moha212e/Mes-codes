using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;
using System.Windows;
using static Voyago.DashBoardAdmin;

namespace Voyago
{
    public partial class DashBoardAdmin : Window
    {
        public object NameTextBox { get; private set; }

        public DashBoardAdmin()
        {
            InitializeComponent();
        }
        public class Trip
        {
            public string Name { get; set; } = string.Empty;
            public string Description { get; set; } = string.Empty;
            public decimal Price { get; set; }
            public string ImageUrl { get; set; } = string.Empty;
            public string Date { get; set; } = string.Empty;
        }
        // fait la fonction AddButton_Click
        private void AddButton_Click(object sender, RoutedEventArgs e)
        {
            
        }
        private void EditUser_Click(object sender, RoutedEventArgs e)
        {
            // fait la fonction EditUser_Click
            AllUserWindow allUserWindow = new AllUserWindow();
            allUserWindow.Show();
            this.Close();
        }
        private void AddUser_Click(object sender, RoutedEventArgs e)
        {
            // fait la fonction DeleteUser_Click
            AddUserWindow addUserWindow = new AddUserWindow();
            addUserWindow.Show();
            this.Close();
        }

        private void EditTrip_Click(object sender, RoutedEventArgs e)
        {

        }
        private void AddTrip_Click(object sender, RoutedEventArgs e)
        {
            // fait la fonction AddTrip_Click
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