using System;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Controls;
using MyShapeClass;

namespace voyagoo
{
    public partial class ListeTickets : Window
    {
        private List<Travelers> travelers;

        public ListeTickets()
        {
            InitializeComponent();
            LoadTravelers();
        }

        private void LoadTravelers()
        {
            travelers = Travelers.LoadAllTravelers();
            TicketsDataGrid.ItemsSource = travelers;
        }

        private void Retour_Click(object sender, RoutedEventArgs e)
        {
            IndexAdmin indexAdmin = new IndexAdmin();
            indexAdmin.Show();
            this.Close();
        }
    }
}