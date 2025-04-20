using System.Windows;
using System.Windows.Controls;
using MyShapeClass;

namespace voyagoo
{
    /// <summary>
    /// Fenêtre principale de l'application.
    /// Gère l'interface de connexion et l'authentification des utilisateurs.
    /// </summary>
    public partial class MainWindow : Window
    {
        #region Constructeurs

        /// <summary>
        /// Initialise une nouvelle instance de la classe MainWindow.
        /// Configure l'interface utilisateur initiale.
        /// </summary>
        public MainWindow()
        {
            InitializeComponent();
        }

        #endregion Constructeurs

        #region Gestionnaires d'événements

        /// <summary>
        /// Gère l'événement de clic sur le bouton de connexion.
        /// Authentifie l'utilisateur et redirige vers l'interface appropriée selon son rôle.
        /// </summary>
        /// <param name="sender">Source de l'événement</param>
        /// <param name="e">Arguments de l'événement</param>
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

        /// <summary>
        /// Gère l'événement de clic sur le bouton d'inscription.
        /// Ouvre la fenêtre d'inscription et ferme la fenêtre actuelle.
        /// </summary>
        /// <param name="sender">Source de l'événement</param>
        /// <param name="e">Arguments de l'événement</param>
        private void Register_Click(object sender, RoutedEventArgs e)
        {
            Register register = new Register();
            register.Show();
            this.Close();
        }

        #endregion Gestionnaires d'événements
    }
}