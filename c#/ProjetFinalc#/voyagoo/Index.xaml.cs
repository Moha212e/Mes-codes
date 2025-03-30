using System.Collections.Generic;
using System.Windows;
using MyShapeClass;

namespace voyagoo
{
    public partial class Index : Window
    {
        public Index()
        {
            InitializeComponent();
            LoadTrips();
        }

        private void LoadTrips()
        {
            // Charge les voyages
            List<Trip> trips = Trip.LoadTrips();
            tripsListBox.ItemsSource = trips;
        }

        private void OnSearchClick(object sender, RoutedEventArgs e)
        {
            // Logique de recherche
            string lieu = txtLieu.Text;
            string date = datePicker.Text;
            int prix = int.Parse(txtPrix.Text);

            List<Trip> trips = Trip.SearchTrips(lieu, date, prix);
            tripsListBox.ItemsSource = trips;
        }

        public void OnTripClick(object sender, RoutedEventArgs e)
        {
            // Récupère le voyage sélectionné
            Trip trip = (Trip)tripsListBox.SelectedItem;
            if (trip != null)
            {
                // Affiche les détails du voyage
                TripDetails tripDetails = new TripDetails(trip);
                tripDetails.ShowDialog();
            }
        }
    }
}