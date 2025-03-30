using System.Windows;
using System.Windows.Controls;
using MyShapeClass;

namespace voyagoo
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void Connect_Click(object sender, RoutedEventArgs e)
        {
            int reponse;
            MessageBox.Show("Le mail : " + EmailTextBox.Text.Trim().ToLower() + "\nLe mot de passe : " + Password.Password.Trim(), "Informations");
            try
            {
                reponse = User.Authenticate(EmailTextBox.Text.Trim().ToLower(), Password.Password.Trim());
                switch (reponse)
                {
                    case (int)AuthenticationError.UserNotFound:
                        MessageBox.Show("User not found", "Authentication Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;

                    case (int)AuthenticationError.InvalidPassword:
                        MessageBox.Show("Invalid password", "Authentication Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;

                    case (int)AuthenticationError.UserDeleted:
                        MessageBox.Show("User deleted", "Authentication Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;

                    case (int)AuthenticationError.UserNotAdmin:
                        //Session.CurrentUser = User.addSession(EmailTextBox.Text.Trim().ToLower());
                        Index index = new Index();
                        index.Show();
                        this.Close();
                        break;

                    case (int)AuthenticationError.InvalidPasswordCo:
                        MessageBox.Show("Invalid password", "Authentication Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;

                    case (int)AuthenticationError.Error:
                        MessageBox.Show("Error", "Authentication Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;

                    case (int)AuthenticationError.Success:
                        IndexAdmin indexAdmin = new IndexAdmin();
                        indexAdmin.Show();
                        this.Close();

                        break;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Authentication Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void Register_Click(object sender, RoutedEventArgs e)
        {
            Register register = new Register();
            register.Show();
            this.Close();
        }
    }
}