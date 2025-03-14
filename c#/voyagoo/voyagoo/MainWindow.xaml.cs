using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;

namespace Voyago
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void NavigateToInscription(object sender, MouseButtonEventArgs e)
        {
            RegisterWindow registerWindow = new RegisterWindow();
            registerWindow.Show();
            this.Close();
        }

        private void TextBox_TextChanged(object sender, TextChangedEventArgs e)
        {
        }

        private void ConnectButton_Click(object sender, RoutedEventArgs e)
        {
            string email = EmailBox.Text;
            string password = PasswordBox.Password;

            User user = User.GetUserByEmailAndPassword(email, password);

            if (user != null)
            {
                if (user.IsAdmin)
                {
                    MessageBox.Show($"Connexion réussie ! Bonjour, {user.LastName}");
                    DashBoardAdmin dashBoardAdmin = new DashBoardAdmin();
                    dashBoardAdmin.Show();
                }
                else
                {
                    MessageBox.Show($"Connexion réussie ! Bonjour, {user.LastName}");
                    DashBoard dashBoard = new DashBoard(user.LastName);
                    dashBoard.Show();
                }
                this.Close();
            }
            else
            {
                MessageBox.Show("Email ou mot de passe incorrect.");
            }
        }
    }
}