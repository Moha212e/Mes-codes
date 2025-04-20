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
            trips = MyShapeClass.Trip.LoadTrips(true);
            filteredTrips = trips;
            VoyagesDataGrid.ItemsSource = filteredTrips;
        }

        private void SaveVoyages_Click(object sender, RoutedEventArgs e)
        {
            if (MyShapeClass.Trip.SaveVoyages(trips) == 0)
            {
                MessageBox.Show("Les voyages ont été sauvegardés avec succès.", "Sauvegarde", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            else
            {
                MessageBox.Show("Erreur lors de la sauvegarde des voyages.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void Retour_Click(object sender, RoutedEventArgs e)
        {
            IndexAdmin indexAdmin = new IndexAdmin();
            indexAdmin.Show();
            this.Close();
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
                    string fileName = MyShapeClass.Trip.SaveImageLocally(openFileDialog.FileName, trip.Destination);

                    if (!string.IsNullOrEmpty(fileName))
                    {
                        trip.Image = $"Data/Images/{fileName}";
                        if (MyShapeClass.Trip.SaveVoyages(trips) == 0)
                        {
                            MessageBox.Show("Image importée avec succès.", "Succès", MessageBoxButton.OK, MessageBoxImage.Information);
                        }
                        else
                        {
                            MessageBox.Show("Erreur lors de l'importation de l'image.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
                            LoadTrips();
                        }
                    }
                }
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
            filteredTrips = trips.Where(trip => trip.Destination.ToLower().Contains(recherche)).ToList();
            VoyagesDataGrid.ItemsSource = filteredTrips;
        }

        private void AjouterVoyage_Click(object sender, RoutedEventArgs e)
        {
            AjouterVoyages ajouterVoyages = new AjouterVoyages();
            ajouterVoyages.Show();
            this.Close();
        }
    }
}