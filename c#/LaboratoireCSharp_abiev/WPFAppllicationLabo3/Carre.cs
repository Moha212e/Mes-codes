using System.ComponentModel;


    // Définition de l'espace de noms MyShapeLibrary, qui organise les classes liées aux formes.
    public class Carre : INotifyPropertyChanged
    {
        private double _cote;

        public double Cote
        {
            get => _cote;
            set
            {
                _cote = value;
                OnPropertyChanged(nameof(Cote));
            }
        }

        public event PropertyChangedEventHandler PropertyChanged;

        protected void OnPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    
    }
