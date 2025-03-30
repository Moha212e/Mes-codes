using System;
using System.Collections.Generic;
using System.Windows;
using MyShapeClass;

namespace voyagoo
{
    public partial class Reserver : Window
    {
        private int idTrip;
        private int price;

        public Reserver(int idTrip, int Price)
        {
            InitializeComponent();
            this.idTrip = idTrip;
            this.price = Price;
        }

        private void Suivant_Click(object sender, RoutedEventArgs e)
        {
            // Récupération des valeurs des champs
            string nom = txtNom.Text?.Trim();
            string prenom = txtPrenom.Text?.Trim();
            DateTime? dateNaissance = dpDateNaissance.SelectedDate;
            string nationalite = txtNationalite.Text?.Trim();
            string passport = txtPasseport.Text?.Trim();
            if (nom != "" && prenom != "" && dateNaissance != null && nationalite != "" && passport != "")
            {
                // Enregistrement du voyageur

                if (Traveler.verifiryInfos(idTrip, nom, prenom, dateNaissance.Value, nationalite, passport) == 0)
                {
                    // Création de l'objet Traveler
                    Traveler traveler = new Traveler(idTrip, nom, prenom, dateNaissance.Value, nationalite, passport);
                    List<Traveler> travelersList = new List<Traveler> { traveler };

                    // Ouvrir la fenêtre Bagage et passer la liste des voyageurs
                    Bagage bagageWindow = new Bagage(travelersList, price);
                    bagageWindow.Show();

                    this.Close(); // Ferme la fenêtre actuelle
                }
            }
            else
            {
                MessageBox.Show("Veuillez remplir tous les champs !");
            }
        }
    }
}