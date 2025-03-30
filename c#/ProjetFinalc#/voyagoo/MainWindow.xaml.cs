using System.Windows;
using MyShapeClass;

namespace voyagoo
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void connect(object sender, RoutedEventArgs e)
        {
            string email = EmailTextBox.Text.Trim(); // Suppression des espaces
            string password = Password.Password.Trim(); // Suppression des espaces

            if (User.isMailValid(email) != 0)
            {
                ErrorMessage.Text = "Adresse mail invalide.";
                return;
            }

            int userExistResult = User.isUserExist(email, password);
            switch (userExistResult)
            {
                case 0:
                    Index index = new Index();
                    index.Show();
                    this.Close();
                    break;

                case 1:
                    IndexAdmin indexAdmin = new IndexAdmin();
                    indexAdmin.Show();
                    this.Close();
                    break;

                case User.ERROR_USER_NOT_FOUND:
                    ErrorMessage.Text = "Utilisateur non trouvé.";
                    break;

                case User.ERROR_INCORRECT_PASSWORD:
                    ErrorMessage.Text = "Mot de passe incorrect.";
                    break;

                case User.ERROR_FILE_NOT_FOUND:
                    ErrorMessage.Text = "Fichier utilisateur non trouvé.";
                    break;

                default:
                    ErrorMessage.Text = "Une erreur inconnue s'est produite.";
                    break;
            }
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            Register register = new Register();
            register.Show();
            this.Close();
        }
    }
}