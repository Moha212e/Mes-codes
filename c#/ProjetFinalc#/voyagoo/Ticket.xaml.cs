using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using MyShapeClass;

namespace voyagoo
{
    public partial class Ticket
    {
        public List<Traveler> travelers;
        private int price;

        public Ticket(List<Traveler> t, int price)
        {
            InitializeComponent();
            this.travelers = t;
            this.price = price;
            LoadTicketData();
        }

        private void LoadTicketData()
        {
            // Charge les informations du premier voyageur (index 0)
            txtMainPassenger.Text = travelers[0].FirstName + " " + travelers[0].LastName;

            // Formatage de la date de naissance pour l'affichage (par exemple, format "dd/MM/yyyy")
            txtDate.Text = travelers[0].DateOfBirth.ToString("dd/MM/yyyy");

            // Affichage de l'ID du voyage
            txtDestination.Text = travelers[0].IdTrip.ToString();

            // Affichage du poids des bagages et calcul du total
            txtLuggage.Text = travelers[0].Bagage.ToString();  // Utilisation de BagageWeight
            txtDepartureDate.Text = "Date de départ ici" + Trip.FindTripById(travelers[0].IdTrip); // Exemple d'ajout de la date de départ, tu peux remplacer par une vraie valeur
            txtTotal.Text = (travelers[0].Bagage + price).ToString(); // Exemple de calcul basé sur le poids
        }

        private void Confirmer_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            if (Traveler.SaveTravel(travelers) == 0)
            {
                MessageBox.Show("Réservation effectuée avec succès", "Information", MessageBoxButton.OK, MessageBoxImage.Information);
                this.Close();
            }
            else
            {
                MessageBox.Show("Erreur lors de la réservation", "Erreur", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}