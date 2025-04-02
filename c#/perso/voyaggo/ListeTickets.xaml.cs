using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace voyagoo
{
    /// <summary>
    /// Logique d'interaction pour ListeTickets.xaml
    /// </summary>
    public partial class ListeTickets : Window

    {
        private List<MyShapeClass.Travelers> travelers;

        public ListeTickets()
        {
            InitializeComponent();
            LoadTravelers();
        }

        private void LoadTravelers()
        {
            travelers = MyShapeClass.Travelers.LoadAllTravelers();
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