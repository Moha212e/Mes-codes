using System;
using System.Windows;
using MyShapeClass;

namespace voyagoo
{
    public partial class Ticket : Window
    {
        private readonly Travelers traveler;
        private readonly Trip currentTrip;
        private readonly int baggageWeight;

        public Ticket(Travelers traveler, Trip voyage, int kilo)
        {
            InitializeComponent();

            // Validation des paramètres
            if (traveler == null)
                throw new ArgumentNullException(nameof(traveler), "Le voyageur ne peut pas être null");

            if (voyage == null)
                throw new ArgumentNullException(nameof(voyage), "Le voyage ne peut pas être null");

            this.traveler = traveler;
            this.currentTrip = voyage;
            this.baggageWeight = kilo;

            this.DataContext = currentTrip;

            InitializeTicket();
            DisplayConfirmation();
        }

        private void InitializeTicket()
        {
            // Chargement des données du voyage
            txtDestination.Text = currentTrip._destination;
            txtDate.Text = currentTrip._date.ToString();
            txtDuration.Text = $"{currentTrip._duration} jours";
            txtDescription.Text = currentTrip._description;

            // Calcul du prix total
            int baggagePrice = CalculateBaggagePrice();
            txtLuggage.Text = $"{baggageWeight} kg ({(baggagePrice > 0 ? $"{baggagePrice} €" : "inclus")})";
            txtTotal.Text = $"{currentTrip._price + baggagePrice} €";

            // Affichage des infos du voyageur
            txtMainPassenger.Text = $"{traveler.firstName} {traveler.lastName}\n" +
                                     $"Passeport : {traveler.PassportNumber}\n" +
                                     $"Né(e) le : {traveler.DateNaissance:dd/MM/yyyy}\n" +
                                     $"Nationalité : {traveler.Nationality}";
        }

        private int CalculateBaggagePrice()
        {
            // Exemple : 5€/kg au-delà de 10kg
            return baggageWeight <= 10 ? 0 : (baggageWeight - 10) * 5;
        }

        private void DisplayConfirmation()
        {
            MessageBox.Show(
                "Votre billet a été généré avec succès !\n\n" +
                $"🗺️ Destination : {currentTrip._destination}\n" +
                $"📅 Date : {currentTrip._date:dd/MM/yyyy}\n" +
                $"👤 Voyageur : {traveler.firstName} {traveler.lastName}",
                "Confirmation",
                MessageBoxButton.OK,
                MessageBoxImage.Information);
        }

        private void Confirmer_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                int result = Travelers.SaveTravel(traveler);

                switch (result)
                {
                    case (int)Travelers.TravelerError.Success:
                        MessageBox.Show("Réservation réussie !", "Succès", MessageBoxButton.OK, MessageBoxImage.Information);
                        break;

                    case (int)Travelers.TravelerError.FileError:
                        MessageBox.Show("Erreur de fichier lors de la réservation.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;

                    case (int)Travelers.TravelerError.AccessDenied:
                        MessageBox.Show("Accès refusé lors de la réservation.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;

                    case (int)Travelers.TravelerError.SerializationError:
                        MessageBox.Show("Erreur de sérialisation lors de la réservation.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;

                    case (int)Travelers.TravelerError.DatabaseError:
                        MessageBox.Show("Erreur de base de données lors de la réservation.", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Une erreur critique est survenue : {ex.Message}",
                                "Erreur",
                                MessageBoxButton.OK,
                                MessageBoxImage.Error);
            }
        }
    }
}