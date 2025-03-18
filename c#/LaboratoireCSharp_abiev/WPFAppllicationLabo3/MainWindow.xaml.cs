using System.Windows;

namespace WPFApplicationLabo3
{
    public partial class MainWindow : Window
    {
        private MyData _data;

        public MainWindow()
        {
            InitializeComponent();
            _data = new MyData();
            DataContext = _data;
        }

        private void AjouterCarre_Click(object sender, RoutedEventArgs e)
        {
            Carre nouveauCarre = new Carre();
            _data.ListeCarres.Add(nouveauCarre);
            _data.CurrentCarre = nouveauCarre;
        }
    }
}