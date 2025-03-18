using System.Collections.ObjectModel;
using System.ComponentModel;


    public class MyData : INotifyPropertyChanged
    {
        private Carre _currentCarre;
        public ObservableCollection<Carre> ListeCarres { get; set; }

        public Carre CurrentCarre
        {
            get => _currentCarre;
            set
            {
                _currentCarre = value;
                OnPropertyChanged(nameof(CurrentCarre));
            }
        }

        public MyData()
        {
            ListeCarres = new ObservableCollection<Carre>();
        }

        public event PropertyChangedEventHandler PropertyChanged;

        protected void OnPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
