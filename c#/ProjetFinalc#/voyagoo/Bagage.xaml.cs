using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using MyShapeClass;

namespace voyagoo
{
    public partial class Bagage

    {
        public List<Traveler> travelers;
        public int price;

        public Bagage(List<Traveler> t, int price)
        {
            InitializeComponent();
            this.travelers = t;
            this.price = price;
        }

        private void Reserver_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            int kilo;
            if (bagageCabine.IsChecked == true)
            {
                kilo = 10;
            }
            else if (bagageSoute.IsChecked == true)
            {
                kilo = 20;
            }
            else if (sansBagage.IsChecked == true)
            {
                kilo = 0;
            }
            else
            {
                MessageBox.Show("Veuillez choisir un type de bagage", "Erreur", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }
            // affiche le kilo
            MessageBox.Show("Vous avez choisi un bagage de " + kilo + " kg", "Information", MessageBoxButton.OK, MessageBoxImage.Information);
            travelers[0].Bagage = kilo;
            Ticket ticket = new Ticket(travelers, price);
            ticket.Show();
            this.Close();
        }
    }
}