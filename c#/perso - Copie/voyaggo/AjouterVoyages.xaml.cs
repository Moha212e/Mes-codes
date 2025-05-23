﻿using System;
using System.IO;
using System.Windows;
using Microsoft.Win32;
using System.Windows.Media.Imaging;
using MyShapeClass; // Assure-toi que l’espace de noms correspond bien à celui de la classe Trip

namespace voyagoo
{
    public partial class AjouterVoyages : Window
    {
        private string imagePath = ""; // Stocke le chemin de l'image sélectionnée

        public AjouterVoyages()
        {
            InitializeComponent();
        }

        private void ChoisirImage_Click(object sender, RoutedEventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog
            {
                Title = "Sélectionner une image",
                Filter = "Images (*.jpg;*.jpeg;*.png;*.bmp)|*.jpg;*.jpeg;*.png;*.bmp"
            };

            if (openFileDialog.ShowDialog() == true)
            {
                string destination = txtDestination.Text.Trim();
                if (string.IsNullOrEmpty(destination))
                {
                    MessageBox.Show("Veuillez entrer une destination avant de choisir une image.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return;
                }

                string imagesFolder = @"C:\Users\pasch\Documents\Mes-codes\c#\perso\voyaggo\Images\";

                // Vérifier et créer le dossier si nécessaire
                if (!Directory.Exists(imagesFolder))
                {
                    Directory.CreateDirectory(imagesFolder);
                }

                string fileExtension = System.IO.Path.GetExtension(openFileDialog.FileName);
                string newFileName = $"{destination}{fileExtension}";
                string destinationPath = System.IO.Path.Combine(imagesFolder, newFileName);

                try
                {
                    File.Copy(openFileDialog.FileName, destinationPath, true);
                    imagePath = destinationPath;
                    MessageBox.Show("Image enregistrée avec succès !", "Succès", MessageBoxButton.OK, MessageBoxImage.Information);
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Erreur lors de la sauvegarde de l'image : {ex.Message}", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            // Récupération des valeurs saisies
            string destination = txtDestination.Text.Trim();
            bool isPrixValid = int.TryParse(txtPrix.Text.Trim(), out int prix);
            DateTime? selectedDate = dpDate.SelectedDate;
            string description = txtDescription.Text.Trim();
            int duree = int.Parse(txtDuree.Text.Trim());

            // Vérification des entrées
            if (string.IsNullOrEmpty(destination) || !isPrixValid || !selectedDate.HasValue || string.IsNullOrEmpty(description))
            {
                MessageBox.Show("Veuillez remplir tous les champs correctement.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }
            MessageBox.Show("destination : " + destination + "\ndescription : " + description + "\ndate : " + selectedDate.Value.ToShortDateString() + "\nprix : " + prix + "\nimagePath : " + imagePath + "\nduree : " + duree, "Informations");

            string date = selectedDate.Value.ToShortDateString();

            // Sauvegarde du voyage en utilisant la classe Trip
            int result = MyShapeClass.Trip.AddTrip(destination, description, date, prix, imagePath, duree);

            switch (result)
            {
                case (int)MyShapeClass.Trip.addTripError.Success:
                    MessageBox.Show("Voyage ajouté avec succès !", "Succès", MessageBoxButton.OK, MessageBoxImage.Information);
                    break;

                case (int)MyShapeClass.Trip.addTripError.TripAlreadyExists:
                    MessageBox.Show("Un voyage avec la même destination et la même date existe déjà.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Warning);
                    break;

                case (int)MyShapeClass.Trip.addTripError.Error:
                    MessageBox.Show("Une erreur est survenue lors de l'ajout du voyage.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
                    break;
            }
        }

        private void Retour_Click(object sender, RoutedEventArgs e)
        {
            IndexAdmin indexAdmin = new IndexAdmin(); // Crée une instance de la fenêtre IndexAdmin
            indexAdmin.Show(); // Affiche la fenêtre IndexAdmin
            this.Close(); // Ferme la fenêtre actuelle (AjouterVoyages)
        }
    }
}