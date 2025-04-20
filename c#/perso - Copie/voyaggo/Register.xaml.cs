using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MyShapeClass;

namespace voyagoo
{
    public partial class Register
    {
        public Register()
        {
            InitializeComponent();
        }

        private void register_User(object sender, System.Windows.RoutedEventArgs e)
        {
            string firstName = Prenom.Text.Trim();
            string lastName = Nom.Text.Trim();
            string email = Email.Text.Trim().ToLower();
            string password = Password.Password.Trim();
            string confirmPassword = PasswordCo.Password.Trim();
            DateTime dateBirth = DateNaissance.SelectedDate.Value;
            if (password != confirmPassword)
            {
                ErrorMessage.Text = "Passwords do not match";
                return;
            }
            int reponse = User.register(firstName, lastName, email, password, dateBirth);
            switch (reponse)
            {
                case (int)RegistrationError.InvalidEmail:
                    ErrorMessage.Text = "Invalid email";
                    break;

                case (int)RegistrationError.InvalidPassword:
                    ErrorMessage.Text = "Invalid password";
                    break;

                case (int)RegistrationError.InvalidFirstName:
                    ErrorMessage.Text = "Invalid first name";
                    break;

                case (int)RegistrationError.InvalidLastName:
                    ErrorMessage.Text = "Invalid last name";
                    break;

                case (int)RegistrationError.InvalidDateOfBirth:
                    ErrorMessage.Text = "Invalid date of birth";
                    break;

                case (int)RegistrationError.EmailAlreadyExists:
                    ErrorMessage.Text = "Email already exists";
                    break;

                case (int)RegistrationError.Success:
                    ErrorMessage.Text = "User registered successfully";
                    Index index = new Index();
                    index.Show();
                    this.Close();
                    break;
            }
        }

        private void have_Account(object sender, System.Windows.RoutedEventArgs e)
        {
            MainWindow mainWindow = new MainWindow();
            mainWindow.Show();
            this.Close();
        }
    }
}