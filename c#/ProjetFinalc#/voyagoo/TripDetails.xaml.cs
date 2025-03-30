using System.Windows;
using System.Windows.Controls;
using MyShapeClass;

namespace voyagoo
{
    public partial class TripDetails : Window
    {
        public TripDetails(Trip selectedTrip)
        {
            InitializeComponent();
            this.DataContext = selectedTrip; // Lie les données du voyage sélectionné
        }

        private void BackButton_Click(object sender, RoutedEventArgs e)
        {
            this.Close(); // Ferme la fenêtre de détails
        }

        private void BookButton_Click(object sender, RoutedEventArgs e)
        {
            // Récupère les informations du voyage sélectionné
            Trip trip = (Trip)this.DataContext;

            // Essaie de convertir le prix du voyage en entier

            // Ouvre la fenêtre de réservation avec l'ID du voyage et le prix converti
            Reserver reserver = new Reserver(trip.IdTrip, trip.Price);
            reserver.Show();
            this.Close(); // Ferme la fenêtre de détails
        }
    }
}