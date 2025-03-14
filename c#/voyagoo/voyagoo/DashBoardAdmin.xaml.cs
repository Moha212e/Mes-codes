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

        #region Constructeur
        public DashBoardAdmin()
        {
            InitializeComponent();
        }
        #endregion

        #region Gestionnaires d'événements

        /// <summary>
        /// Gère l'événement de clic sur le bouton Ajouter.
        /// </summary>
        private void AddButton_Click(object sender, RoutedEventArgs e)
        {
            // Code pour AddButton_Click
            
        }

        /// <summary>
        /// Gère l'événement de clic sur le bouton Modifier Utilisateur.
        /// Ouvre la fenêtre AllUserWindow et ferme la fenêtre actuelle.
        /// </summary>
        private void EditUser_Click(object sender, RoutedEventArgs e)
        {
            AllUserWindow allUserWindow = new AllUserWindow();
            allUserWindow.Show();
            this.Close();
        }

        /// <summary>
        /// Gère l'événement de clic sur le bouton Ajouter Utilisateur.
        /// Ouvre la fenêtre AddUserWindow et ferme la fenêtre actuelle.
        /// </summary>
        private void AddUser_Click(object sender, RoutedEventArgs e)
        {
            AddUserWindow addUserWindow = new AddUserWindow();
            addUserWindow.Show();
            this.Close();
        }

        /// <summary>
        /// Gère l'événement de clic sur le bouton Modifier Voyage.
        /// </summary>
        private void EditTrip_Click(object sender, RoutedEventArgs e)
        {
            // Code pour EditTrip_Click
        }

        /// <summary>
        /// Gère l'événement de clic sur le bouton Ajouter Voyage.
        /// </summary>
        private void AddTrip_Click(object sender, RoutedEventArgs e)
        {
            // Code pour AddTrip_Click
            AddTripWindow addTripWindow = new AddTripWindow();
            addTripWindow.Show();
            this.Close();
        }

        /// <summary>
        /// Gère l'événement de clic sur l'élément de menu Accueil.
        /// Ouvre une nouvelle instance de DashBoardAdmin et ferme la fenêtre actuelle.
        /// </summary>
        private void HomeMenuItem_Click(object sender, RoutedEventArgs e)
        {
            DashBoardAdmin dashBoardAdmin = new DashBoardAdmin();
            dashBoardAdmin.Show();
            this.Close();
        }

        /// <summary>
        /// Gère l'événement de clic sur le bouton Déconnexion.
        /// Réinitialise la session et navigue vers la fenêtre MainWindow.
        /// </summary>
        private void Deconnect(object sender, RoutedEventArgs e)
        {
            // Réinitialiser les informations de session
            SessionManager.ClearSession();

            // Naviguer vers la fenêtre de connexion
            MainWindow mainWindow = new MainWindow();
            mainWindow.Show();
            this.Close();
        }

        #endregion
    }
}
