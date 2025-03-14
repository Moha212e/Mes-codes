using System;
using System.Collections.Generic;
using System.IO;
using System.Security.Cryptography;
using System.Text.Json;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using MyShapeLibrary;
using Voyago;

namespace Voyago
{
    public partial class AddTripWindow : Window
    {
        public AddTripWindow()
        {
            InitializeComponent();
            ImageDropArea.AllowDrop = true;
            ImageDropArea.Drop += ImageDropArea_Drop;
        }

        private void AddTripButton_Click(object sender, RoutedEventArgs e)
        {
            string name = NameBox.Text;
            string description = DescriptionBox.Text;
            string priceText = PriceBox.Text;
            DateTime? date = DatePicker.SelectedDate;

            if (string.IsNullOrEmpty(name) || string.IsNullOrEmpty(description) || string.IsNullOrEmpty(priceText) || !date.HasValue)
            {
                MessageBox.Show("Veuillez remplir tous les champs.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            if (!int.TryParse(priceText, out int price))
            {
                MessageBox.Show("Veuillez entrer un prix valide.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            // Générer l'URL de l'image à partir du nom
            string imageUrl = $"images/{name.Replace(" ", "_")}.png";

            var trip = new Trip
            {
                IdTrip = RandomNumberGenerator.GetInt32(1, int.MaxValue),
                Name = name,
                Description = description,
                Price = price,
                Date = date.Value,
                ImageUrl = imageUrl
            };

            trip.RegisterTrip();

            MessageBox.Show("Voyage ajouté avec succès !");
            this.Close();
        }

        private void ImageDropArea_Drop(object sender, DragEventArgs e)
        {
            if(e.Data.GetDataPresent(DataFormats.FileDrop))
            {
                string[] files = (string[])e.Data.GetData(DataFormats.FileDrop);

                if (files.Length > 0 && (files[0].EndsWith(".png") || files[0].EndsWith(".jpg") || files[0].EndsWith(".jpeg")))
                {
                    // Message d'information
                    MessageBox.Show("Image ajoutée : " + files[0], "Succès", MessageBoxButton.OK, MessageBoxImage.Information);

                    // Définir le dossier cible pour l'image (par exemple, "images/")
                    string targetDirectory = "images/";

                    // Créer le dossier s'il n'existe pas
                    if (!Directory.Exists(targetDirectory))
                    {
                        Directory.CreateDirectory(targetDirectory);
                    }

                    // Générer un nom de fichier unique pour éviter les conflits
                    string imageName = $"{Guid.NewGuid()}{Path.GetExtension(files[0])}";
                    string targetFilePath = Path.Combine(targetDirectory, imageName);

                    // Copier l'image dans le dossier cible
                    File.Copy(files[0], targetFilePath);

                    // Afficher l'URL de l'image
                    MessageBox.Show("L'image a été enregistrée à : " + targetFilePath, "Succès", MessageBoxButton.OK, MessageBoxImage.Information);

                    // Ici tu peux aussi ajouter l'URL dans ton objet Trip, ou le garder pour plus tard
                    string imageUrl = targetFilePath; // L'URL de l'image sauvegardée
                }
                else
                {
                    MessageBox.Show("Veuillez déposer une image au format PNG ou JPEG.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
        }

        private void HomeMenuItem_Click(object sender, RoutedEventArgs e)
        {
            DashBoardAdmin dashBoard = new DashBoardAdmin();
            dashBoard.Show();
            this.Close();
        }

        private void Deconnect(object sender, RoutedEventArgs e)
        {
            SessionManager.ClearSession();
            MainWindow mainWindow = new MainWindow();
            mainWindow.Show();
            this.Close();
        }
    }
}
