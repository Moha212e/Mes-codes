using System;
using System.Windows;

namespace voyagoo
{
    public partial class IndexAdmin : Window
    {
        public IndexAdmin()
        {
            InitializeComponent();
        }

        private void EditerUtilisateur_Click(object sender, RoutedEventArgs e)
        {
            EditerUtilisateurs editerUtilisateurs = new EditerUtilisateurs();
            editerUtilisateurs.Show();
            this.Close();
        }

        /*
        private void ListerReservations_Click(object sender, RoutedEventArgs e)
        {
            ListeTickets listeTickets = new ListeTickets();
            listeTickets.Show();
            this.Close();
        }
        */

        private void AjouterVoyages_Click(object sender, RoutedEventArgs e)
        {
            AjouterVoyages ajouterVoyages = new AjouterVoyages();
            ajouterVoyages.Show();
            this.Close();
        }

        private void GererVoyages_Click(object sender, RoutedEventArgs e)
        {
            GererVoyages gererVoyages = new GererVoyages();
            gererVoyages.Show();
            this.Close();
        }
    }
}