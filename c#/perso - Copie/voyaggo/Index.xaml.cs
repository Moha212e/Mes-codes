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
            LoadLastSearch();
            Debug.WriteLine("Initialisation terminée");
        }

        private void LoadLastSearch()
        {
            var (lieu, date, prix) = RegistryHelper.LoadLastSearch();
            txtLieu.Text = lieu;
            datePicker.Text = date;
            txtPrix.Text = prix;
        }

        private void LoadTrips()
        {
            var trips = MyShapeClass.Trip.LoadTrips();
            tripsListBox.ItemsSource = trips;

            foreach (var trip in trips)
            {
                Debug.WriteLine($"Trip chargé: {trip._destination}, Image: {trip._image}");
            }
        }

        private void OnSearchClick(object sender, RoutedEventArgs e)
        {
            string lieu = txtLieu.Text;
            string date = datePicker.Text;
            string prixText = txtPrix.Text;

            // Initialisation avec une valeur par défaut
            int prix = 0;

            // Vérification et conversion du prix
            if (!string.IsNullOrEmpty(prixText) && !int.TryParse(prixText, out prix))
            {
                MessageBox.Show("Veuillez entrer un prix valide.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            // Sauvegarde dans le registre
            RegistryHelper.SaveLastSearch(lieu, date, prixText);

            // Recherche avec le prix correctement initialisé
            var trips = MyShapeClass.Trip.SearchTrips(lieu, date, prix);
            tripsListBox.ItemsSource = trips;
        }

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