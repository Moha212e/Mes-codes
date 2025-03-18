namespace MyShapeLibrary
{
    // Définition de l'espace de noms MyShapeLibrary, qui organise les classes liées aux formes.
    public class Coordonnee : IEquatable<Coordonnee>
    {
        // Définition de la classe Coordonnee (Coordonnée) qui implémente l'interface IEquatable<Coordonnee>.
        // IEquatable<Coordonnee> : Permet de vérifier l'égalité entre des instances de Coordonnee.

        #region Variables and proprieties
        // Début d'une région de code pour les variables et propriétés.
        private int _x, _y;
        // Déclaration de variables privées _x et _y pour stocker les coordonnées X et Y.

        public int X
        {
            // Définition de la propriété publique X pour accéder à _x.
            get { return _x; }
            // Getter : Renvoie la valeur de _x.
            set { _x = value; }
            // Setter : Définit la valeur de _x.
        }

        public int Y
        {
            // Définition de la propriété publique Y pour accéder à _y.
            get { return _y; }
            // Getter : Renvoie la valeur de _y.
            set { _y = value; }
            // Setter : Définit la valeur de _y.
        }
        #endregion
        // Fin de la région des variables et propriétés.

        #region Constructors
        // Début de la région des constructeurs.
        public Coordonnee(int x, int y)
        {
            // Constructeur de Coordonnee qui prend les coordonnées X et Y en paramètres.
            X = x;
            // Initialise la propriété X avec la valeur passée en paramètre.
            Y = y;
            // Initialise la propriété Y avec la valeur passée en paramètre.
        }

        public Coordonnee() : this(0, 0)
        {
            // Constructeur sans paramètres qui appelle le constructeur principal avec des coordonnées par défaut (0, 0).
        }
        #endregion
        // Fin de la région des constructeurs.

        #region Override
        // Début de la région des méthodes surchargées (override).
        public override string ToString()
        {
            // Surcharge de la méthode ToString() pour renvoyer une représentation textuelle de la coordonnée.
            return "position x = " + X + ", position y = " + Y;
            // Renvoie une chaîne formatée décrivant les coordonnées X et Y.
        }
        #endregion
        // Fin de la région des méthodes surchargées.

        #region Methods
        // Début de la région des méthodes personnalisées.
        public bool Equals(Coordonnee? other)
        {
            // Implémentation de l'interface IEquatable<Coordonnee> pour vérifier l'égalité entre deux coordonnées.
            if (other == null) return false;
            // Si l'autre coordonnée est null, elles ne sont pas égales.
            return X == other.X && Y == other.Y;
            // Vérifie si les coordonnées X et Y sont égales.
        }
        #endregion
        // Fin de la région des méthodes.
    }
}