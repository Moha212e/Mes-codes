using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows;
using Microsoft.Win32;

namespace Voyago
{
    public partial class DashBoard : Window
    {
        private List<Trip> trips;
        private string lastName;
        private MyAppParamManager paramManager;

        public DashBoard(string lastName)
        {
            InitializeComponent();
            this.lastName = lastName;
            paramManager = new MyAppParamManager();

            LoadUserSettings();
            LoadUsers();
            DisplayUserName();
            trips = LoadTrips();
        }

        private void LoadUserSettings()
        {
            try
            {
                paramManager.LoadRegistryParameters();
                DestinationBox.Text = paramManager.Destination ?? "";
                PriceMinBox.Text = paramManager.PriceMin.ToString();
                PriceMaxBox.Text = paramManager.PriceMax.ToString();
                DatePicker.SelectedDate = paramManager.TravelDate;
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Erreur lors du chargement des paramètres : {ex.Message}", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void SaveUserSettings()
        {
            try
            {
                // Ne pas enregistrer le lastName ici
                paramManager.Destination = DestinationBox.Text ?? "";
                paramManager.PriceMin = double.TryParse(PriceMinBox.Text, out double priceMin) ? priceMin : 0;
                paramManager.PriceMax = double.TryParse(PriceMaxBox.Text, out double priceMax) ? priceMax : 0;
                paramManager.TravelDate = DatePicker.SelectedDate ?? DateTime.Now;

                paramManager.SaveRegistryParameters();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Erreur lors de l'enregistrement des paramètres : {ex.Message}", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private List<Trip>? LoadTrips()
        {
            const string TripsFilePath = "Data/trips.json";
            if (!System.IO.File.Exists(TripsFilePath))
            {
                return new List<Trip>();
            }

            string json = System.IO.File.ReadAllText(TripsFilePath);
            return System.Text.Json.JsonSerializer.Deserialize<List<Trip>>(json);
        }


        private void Deconnect(object sender, RoutedEventArgs e)
        {
            SessionManager.ClearSession();
            MainWindow mainWindow = new MainWindow();
            mainWindow.Show();
            this.Close();
        }

        private void DisplayUserName()
        {
            UserNameTextBlock.Text = $"Bienvenue, {lastName}";  // Juste l'afficher, ne pas le sauvegarder
        }

        private List<User> LoadUsers()
        {
            const string UsersFilePath = "Data/users.json";
            if (!System.IO.File.Exists(UsersFilePath))
            {
                return new List<User>();
            }

            string json = System.IO.File.ReadAllText(UsersFilePath);
            return System.Text.Json.JsonSerializer.Deserialize<List<User>>(json);
        }

        private void SearchButton_Click(object sender, RoutedEventArgs e)
        {
            SaveUserSettings();

            string destination = DestinationBox.Text.ToLower();
            int.TryParse(PriceMinBox.Text, out int priceMin);
            int.TryParse(PriceMaxBox.Text, out int priceMax);
            DateTime? selectedDate = DatePicker.SelectedDate;

            var filteredTrips = trips.Where(t =>
                (string.IsNullOrEmpty(destination) || t.Name.ToLower().Contains(destination) || t.Description.ToLower().Contains(destination)) &&
                (priceMin == 0 || t.Price >= priceMin) &&
                (priceMax == 0 || t.Price <= priceMax) &&
                (!selectedDate.HasValue || t.Date.Date == selectedDate.Value.Date)
            ).ToList();

            ResultsListBox.ItemsSource = filteredTrips;
        }

        private void ResultsListBox_SelectionChanged(object sender, System.Windows.Controls.SelectionChangedEventArgs e)
        {
            if (ResultsListBox.SelectedItem is Trip selectedTrip)
            {
                MessageBox.Show($"Détails du voyage :\n{selectedTrip.Name}\n{selectedTrip.Description}\nPrix : {selectedTrip.Price} €\nDate : {selectedTrip.Date:dd/MM/yyyy}", "Détails du voyage", MessageBoxButton.OK, MessageBoxImage.Information);
            }
        }

        protected override void OnClosing(System.ComponentModel.CancelEventArgs e)
        {
            SaveUserSettings();
            base.OnClosing(e);
        }
    }

    public class Trip
    {
        public string Name { get; set; }
        public string Description { get; set; }
        public int Price { get; set; }
        public DateTime Date { get; set; }
        public string ImageUrl { get; set; }
    }
}
