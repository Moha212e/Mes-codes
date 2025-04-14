using System;
using System.Collections.Generic;
using System.Windows;
using MyShapeClass;

namespace voyagoo
{
    public partial class Bagage : Window
    {
        private readonly Travelers _traveler;
        private readonly Trip _currentTrip;

        public Bagage(Travelers traveler, Trip trip)
        {
            InitializeComponent();

            if (traveler == null)
                throw new ArgumentNullException(nameof(traveler), "Le voyageur ne peut pas être nul.");

            if (trip == null)
                throw new ArgumentNullException(nameof(trip), "Le voyage ne peut pas être nul.");

            _traveler = traveler;
            _currentTrip = trip;
            this.DataContext = _currentTrip;

            MessageBox.Show(
                $"🧳 DÉTAILS DE LA RÉSERVATION\n\n" +
                $"📍 Destination : {_currentTrip._destination}\n" +
                $"⏳ Durée : {_currentTrip._duration} jours\n" +
                $"📅 Date : {_currentTrip._date:dd/MM/yyyy}\n" +
                $"📝 Description : {_currentTrip._description}\n\n" +
                $"💰 PRIX : {_currentTrip._price} €\n\n" +
                $"👤 CLIENT : {_traveler.firstName} {_traveler.lastName}\n" +
                $"🛂 Passeport : {_traveler.PassportNumber}",
                "Confirmation de réservation",
                MessageBoxButton.OK,
                MessageBoxImage.Information
            );
        }

        private void Reserver_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                int baggageWeight = GetSelectedBaggageWeight();

                _traveler.bagage = baggageWeight;

                ShowBaggageConfirmation(baggageWeight);

                var ticketWindow = new Ticket(_traveler, _currentTrip, baggageWeight);
                ticketWindow.Show();
                this.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Une erreur est survenue : {ex.Message}", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private int GetSelectedBaggageWeight()
        {
            if (bagageCabine.IsChecked == true) return 10;
            if (bagageSoute.IsChecked == true) return 20;
            if (sansBagage.IsChecked == true) return 0;

            throw new InvalidOperationException("Veuillez sélectionner un type de bagage.");
        }

        private void ShowBaggageConfirmation(int weight)
        {
            string message = weight > 0
                ? $"✅ Vous avez choisi un bagage de {weight} kg."
                : "🚫 Vous voyagez sans bagage.";

            MessageBox.Show(message, "Bagage", MessageBoxButton.OK, MessageBoxImage.Information);
        }
    }
}