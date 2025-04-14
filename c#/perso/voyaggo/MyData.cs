using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using MyShapeClass;

namespace voyagoo
{
    public class MyData : INotifyPropertyChanged
    {
        private ObservableCollection<Trip> _trips;

        public ObservableCollection<Trip> Trips
        {
            get { return _trips; }
            set
            {
                if (_trips != value)
                {
                    _trips = value;
                    NotifyPropertyChanged();
                }
            }
        }

        private ObservableCollection<User> _users;

        public ObservableCollection<User> Users
        {
            get { return _users; }
            set
            {
                if (_users != value)
                {
                    _users = value;
                    NotifyPropertyChanged();
                }
            }
        }

        private ObservableCollection<Travelers> _travelers;

        public ObservableCollection<Travelers> Travelers
        {
            get { return _travelers; }
            set
            {
                if (_travelers != value)
                {
                    _travelers = value;
                    NotifyPropertyChanged();
                }
            }
        }

        public MyData()
        {
            Trips = new ObservableCollection<Trip>(Trip.LoadTrips());
            Users = new ObservableCollection<User>(User.LoadUsers());
            //Travelers = new ObservableCollection<Travelers>(Travelers.LoadAllTravelers());
        }

        #region INotifyPropertyChanged implementation

        public event PropertyChangedEventHandler? PropertyChanged;

        // This method is called by the Set accessor of each property.
        // The CallerMemberName attribute that is applied to the optional propertyName
        // parameter causes the property name of the caller to be substituted as an argument.
        private void NotifyPropertyChanged([CallerMemberName] String propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        #endregion INotifyPropertyChanged implementation
    }
}