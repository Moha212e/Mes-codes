using System.Collections.Generic;
using System.Diagnostics;
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

            // Debug pour vérifier le chargement
            Debug.WriteLine("Initialisation terminée");
        }

        private void LoadTrips()
        {
            var trips = MyShapeClass.Trip.LoadTrips();
            tripsListBox.ItemsSource = trips;

            // Debug
            foreach (var trip in trips)
            {
                Debug.WriteLine($"Trip chargé: {trip._destination}, Image: {trip._image}");
            }
        }

        private void OnSearchClick(object sender, RoutedEventArgs e)
        {
            string lieu = txtLieu.Text;
            string date = datePicker.Text;

            if (!int.TryParse(txtPrix.Text, out int prix))
            {
                MessageBox.Show("Veuillez entrer un prix valide.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            var trips = MyShapeClass.Trip.SearchTrips(lieu, date, prix);
            tripsListBox.ItemsSource = trips;
        }

        // Séparer les handlers
        private void OnListBoxSelectionChanged(object sender, RoutedEventArgs e)
        {
            // Logique de sélection si nécessaire
        }

        private void OnReserveButtonClick(object sender, RoutedEventArgs e)
        {
            if (((FrameworkElement)sender).DataContext is Trip trip)
            {
                Detail tripDetails = new Detail(trip);
                tripDetails.ShowDialog();
            }
        }
    }
}