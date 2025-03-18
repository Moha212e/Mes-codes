namespace MyShapeLibrary
{
    // Définition de l'espace de noms MyShapeLibrary, qui organise les classes liées aux formes.
    public class Carre : Forme, IestDans, IPolygone, IComparable<Carre>, IEquatable<Carre>
    {
        // Définition de la classe Carre (Carré) qui hérite de Forme et implémente plusieurs interfaces.
        // Forme : Une classe de base (non montrée ici) probablement pour les formes géométriques.
        // IestDans : Une interface (non montrée ici) qui définit une méthode pour vérifier si un point est à l'intérieur de la forme.
        // IPolygone : Une interface (non montrée ici) qui définit des propriétés ou méthodes liées aux polygones.
        // IComparable<Carre> : Permet de comparer des instances de Carre.
        // IEquatable<Carre> : Permet de vérifier l'égalité entre des instances de Carre.

        #region Variables and proprieties
        // Début d'une région de code pour les variables et propriétés.
        private int _length;

        // Déclaration d'une variable privée _length pour stocker la longueur du côté du carré.
        public int Length
        {
            // Définition de la propriété publique Length pour accéder à _length.
            get { return _length; }
            // Getter : Renvoie la valeur de _length.
            set { _length = value; }
            // Setter : Définit la valeur de _length.
        }
        public int NbSommets { get { return 4; } }
        // Définition de la propriété publique NbSommets (Nombre de sommets), qui renvoie toujours 4 (un carré a 4 sommets).
        #endregion
        // Fin de la région des variables et propriétés.

        #region Constructors
        // Début de la région des constructeurs.
        public Carre(int length, Coordonnee pointAccroche) : base()
        {
            // Constructeur de Carre qui prend la longueur et le point d'accroche en paramètres.
            // : base() appelle le constructeur de la classe de base (Forme).
            Length = length;
            // Initialise la propriété Length avec la valeur passée en paramètre.
            PointAccroche = pointAccroche;
            // Initialise la propriété PointAccroche (probablement héritée de Forme) avec le point d'accroche passé en paramètre.
        }

        public Carre() : this(1, new Coordonnee())
        { }
        // Constructeur sans paramètres qui appelle le constructeur principal avec une longueur de 1 et un point d'accroche par défaut (0,0).
        #endregion
        // Fin de la région des constructeurs.

        #region Override
        // Début de la région des méthodes surchargées (override).
        public override string ToString()
        {
            // Surcharge de la méthode ToString() pour renvoyer une représentation textuelle du carré.
            return "longueur du carre : " + Length + "cm ;" + "avec point d'accroche : " + PointAccroche.ToString();
            // Renvoie une chaîne formatée décrivant la longueur et le point d'accroche du carré.
        }
        #endregion
        // Fin de la région des méthodes surchargées.

        #region Methods
        // Début de la région des méthodes personnalisées.
        public bool CoordonneeEstDans(Coordonnee p)
        {
            // Méthode pour vérifier si un point (Coordonnee p) est à l'intérieur du carré.
            return p.X >= PointAccroche.X && p.X <= PointAccroche.X + Length &&
                   p.Y >= PointAccroche.Y && p.Y <= PointAccroche.Y + Length;
            // Vérifie si les coordonnées X et Y du point sont dans les limites du carré.
        }

        public int CompareTo(Carre? other)
        {
            // Implémentation de l'interface IComparable<Carre> pour comparer deux carrés.
            if (other == null) return 1;
            // Si l'autre carré est null, le carré courant est considéré comme plus grand.
            return Length.CompareTo(other.Length);
            // Compare les longueurs des carrés.
        }

        public bool Equals(Carre? other)
        {
            // Implémentation de l'interface IEquatable<Carre> pour vérifier l'égalité entre deux carrés.
            if (other == null) return false;
            // Si l'autre carré est null, ils ne sont pas égaux.
            if (Length == other.Length && PointAccroche.Equals(other.PointAccroche)) return true;
            // Vérifie si les longueurs et les points d'accroche sont égaux.
            else return false;
            // Sinon, les carrés ne sont pas égaux.
        }
        #endregion
        // Fin de la région des méthodes.
    }
}