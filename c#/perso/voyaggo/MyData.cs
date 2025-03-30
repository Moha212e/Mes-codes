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
        #region Fields and properties

        private ObservableCollection<User> _listPersons;

        public ObservableCollection<User> ListPersons
        {
            get { return _listPersons; }
            set
            {
                if (_listPersons != value)
                {
                    _listPersons = value;
                    NotifyPropertyChanged();
                }
            }
        }

        private ObservableCollection<Trip> _listTrips;

        public ObservableCollection<Trip> ListTrips
        {
            get { return _listTrips; }
            set
            {
                if (_listTrips != value)
                {
                    _listTrips = value;
                    NotifyPropertyChanged();
                }
            }
        }

        public MyData()
        {
            ListPersons = new ObservableCollection<User>(User.LoadUsers());
            ListTrips = new ObservableCollection<Trip>((IEnumerable<Trip>)MyShapeClass.Trip.LoadTrips());
        }

        #endregion Fields and properties

        #region INotifyPropertyChanged implementation

        public event PropertyChangedEventHandler PropertyChanged;

        private void NotifyPropertyChanged([CallerMemberName] String propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        #endregion INotifyPropertyChanged implementation
    }
}