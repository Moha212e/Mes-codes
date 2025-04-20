using System;
using System.Collections.Generic;
using System.Windows;
using MyShapeClass;

namespace voyagoo
{
    public partial class Reserver : Window
    {
        private int _idTrip;
        private int _price;

        public Reserver(Trip trip)
        {
            InitializeComponent();

            if (trip == null)
            {
                throw new ArgumentNullException(nameof(trip), "Le voyage ne peut pas être nul.");
            }

            this.DataContext = trip;
            _idTrip = trip._idTrip; // Utiliser une propriété publique
            _price = trip._price;

            MessageBox.Show(
                $"Réservation pour le voyage à {trip._destination}.\n" +
                $"Prix : {trip._price} €\n" +
                $"Description : {trip._description}\n" +
                $"Durée : {trip._duration} jours",
                "Information",
                MessageBoxButton.OK,
                MessageBoxImage.Information
            );
        }

        private void Suivant_Click(object sender, RoutedEventArgs e)
        {
            // Validation des champs
            if (!ValidateInputs())
            {
                MessageBox.Show(
                    "Veuillez remplir tous les champs correctement !",
                    "Champs manquants",
                    MessageBoxButton.OK,
                    MessageBoxImage.Warning
                );
                return;
            }

            try
            {
                var currentTrip = this.DataContext as Trip;

                if (currentTrip == null)
                {
                    throw new InvalidOperationException("Le voyage actuel est introuvable.");
                }

                // Vérification des informations
                int verificationResult = Travelers.VerifyInfos(
                    currentTrip._idTrip,
                    txtPrenom.Text.Trim(),
                    txtNom.Text.Trim(),
                    dpDateNaissance.SelectedDate.Value,
                    txtNationalite.Text.Trim(),
                    txtPasseport.Text.Trim()
                );

                if (verificationResult == 0)
                {
                    // Création du voyageur
                    var traveler = new Travelers(
                        _idTrip,
                        txtNom.Text.Trim(),
                        txtPrenom.Text.Trim(),
                        dpDateNaissance.SelectedDate.Value,
                        txtNationalite.Text.Trim(),
                        txtPasseport.Text.Trim(),
                        currentTrip._destination
                    );

                    // Passage à la fenêtre Bagage
                    var bagageWindow = new Bagage(traveler, currentTrip);
                    bagageWindow.Show();
                    this.Close();
                }
                else
                {
                    MessageBox.Show("Les informations saisies ne sont pas valides.",
                        "Erreur de validation",
                        MessageBoxButton.OK,
                        MessageBoxImage.Warning);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Erreur lors de la réservation : {ex.Message}",
                    "Erreur",
                    MessageBoxButton.OK,
                    MessageBoxImage.Error);
            }
        }

        private bool ValidateInputs()
        {
            return !string.IsNullOrWhiteSpace(txtNom.Text) &&
                   !string.IsNullOrWhiteSpace(txtPrenom.Text) &&
                   dpDateNaissance.SelectedDate.HasValue &&
                   !string.IsNullOrWhiteSpace(txtNationalite.Text) &&
                   !string.IsNullOrWhiteSpace(txtPasseport.Text);
        }
    }
}