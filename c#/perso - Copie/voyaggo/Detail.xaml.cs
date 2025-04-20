using System.Windows;
using System.Windows.Controls;
using MyShapeClass;

namespace voyagoo
{
    public partial class Detail : Window
    {
        public Detail(Trip selectedDetail)
        {
            InitializeComponent();
            this.DataContext = selectedDetail; // Lie les données du voyage sélectionné

            // Message plus propre et organisé pour afficher les détails
            string message = $"Détails du voyage à {selectedDetail._destination}\n\n"
                          + $"Description: {selectedDetail._description}\n"
                          + $"Durée: {selectedDetail._duration}\n"
                          + $"Date: {selectedDetail._date}\n"
                          + $"Prix: {selectedDetail._price}";

            MessageBox.Show(message,
                          "Détails du voyage",
                          MessageBoxButton.OK,
                          MessageBoxImage.Information);
        }

        private void BackButton_Click(object sender, RoutedEventArgs e)
        {
            this.Close(); // Ferme la fenêtre de détails
        }

        private void BookButton_Click(object sender, RoutedEventArgs e)
        {
            // Crée et ouvre la fenêtre de réservation
            Reserver reserver = new Reserver(this.DataContext as Trip);
            reserver.Show();
            this.Close(); // Ferme la fenêtre de détails
        }
    }
}