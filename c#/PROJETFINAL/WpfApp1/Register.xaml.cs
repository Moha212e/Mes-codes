using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using ClassLibrary1;
namespace WpfApp1
{
    public partial class Register
    {
        public Register() { 
            InitializeComponent();

        }

        private void have_Account(object sender, System.Windows.RoutedEventArgs e)
        {
            MainWindow mainWindow = new MainWindow();
            mainWindow.Show();
            this.Close();
        }

        private void register_User(object sender, RoutedEventArgs e)
        {
            /// fais la verification des champs
            string mail = Email.Text;
            string password = Password.Password;
            string password2 = PasswordCo.Password;
            DateTime birthDate = DateNaissance.SelectedDate.Value;
            int age = User.CalculateAge(birthDate);

        }
    }
}
