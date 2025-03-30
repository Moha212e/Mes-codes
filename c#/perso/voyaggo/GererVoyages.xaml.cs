using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.Json;
using System.Windows;
using System.Windows.Controls;
using Microsoft.Win32;
using MyShapeClass;

namespace voyagoo
{
    public partial class GererVoyages : Window
    {
        private static string filePath = "Data/Trips.json";
        private List<MyShapeClass.Trip> trips;
        private List<MyShapeClass.Trip> filteredTrips;  // Liste filtrée pour la recherche

        public GererVoyages()
        {
            InitializeComponent();
            LoadTrips();  // Charger les voyages au démarrage
        }

        private void LoadTrips()
        {
            trips = MyShapeClass.Trip.LoadAllTrips();
            filteredTrips = trips;
            VoyagesDataGrid.ItemsSource = filteredTrips;
        }

        private void SaveVoyages()
        {
            try
            {
                string json = JsonSerializer.Serialize(trips, new JsonSerializerOptions { WriteIndented = true });
                File.WriteAllText(filePath, json);
                MessageBox.Show("Modifications enregistrées !", "Succès", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Erreur lors de l'enregistrement : {ex.Message}", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void SaveVoyages_Click(object sender, RoutedEventArgs e)
        {
            SaveVoyages();
        }

        private void Retour_Click(object sender, RoutedEventArgs e)
        {
            IndexAdmin indexAdmin = new IndexAdmin();
            indexAdmin.Show();
            this.Close();
        }

        private void DeleteVoyage_Click(object sender, RoutedEventArgs e)
        {
            if (sender is Button button && button.DataContext is MyShapeClass.Trip trip)
            {
                var result = MessageBox.Show($"Confirmer la suppression de {trip._destination} ?", "Suppression",
                                             MessageBoxButton.YesNo, MessageBoxImage.Warning);
                if (result == MessageBoxResult.Yes)
                {
                    trips.Remove(trip);
                    SaveVoyages();
                    LoadTrips();
                }
            }
        }

        private void ImportImage_Click(object sender, RoutedEventArgs e)
        {
            if (sender is Button button && button.DataContext is MyShapeClass.Trip trip)
            {
                OpenFileDialog openFileDialog = new OpenFileDialog
                {
                    Title = "Sélectionner une image",
                    Filter = "Images (*.jpg;*.jpeg;*.png;*.bmp)|*.jpg;*.jpeg;*.png;*.bmp"
                };

                if (openFileDialog.ShowDialog() == true)
                {
                    string fileName = SaveImageLocally(openFileDialog.FileName, trip._destination);

                    if (!string.IsNullOrEmpty(fileName))
                    {
                        trip._image = $"Data/Images/{fileName}";
                        SaveVoyages();
                        LoadTrips();
                    }
                }
            }
        }

        private string SaveImageLocally(string sourcePath, string destination)
        {
            try
            {
                string imagesFolder = "Data/Images";

                if (!Directory.Exists(imagesFolder))
                {
                    Directory.CreateDirectory(imagesFolder);
                }

                string extension = Path.GetExtension(sourcePath);
                string fileName = $"{destination.Replace(" ", "_").Replace("/", "_").Replace("\\", "_")}{extension}";
                string destinationPath = Path.Combine(imagesFolder, fileName);

                File.Copy(sourcePath, destinationPath, true);
                return fileName;
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Erreur lors de l'enregistrement de l'image : {ex.Message}", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
                return null;
            }
        }

        private void Recherche_TextChanged(object sender, TextChangedEventArgs e)
        {
            FiltrerVoyages();
        }

        private void Recherche_Click(object sender, RoutedEventArgs e)
        {
            FiltrerVoyages();
        }

        private void FiltrerVoyages()
        {
            string recherche = txtRecherche.Text.Trim().ToLower();
            filteredTrips = trips.Where(trip => trip._destination.ToLower().Contains(recherche)).ToList();
            VoyagesDataGrid.ItemsSource = filteredTrips;
        }
    }

    public class Trip
    {
        internal object _image;

        public string Destination { get; set; }
        public double Price { get; set; }
        public string Description { get; set; }
        public string Date { get; set; }
        public string ImagePath { get; set; }
    }
}