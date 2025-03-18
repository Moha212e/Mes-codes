namespace MyShapeLibrary
{
    // Définition de l'espace de noms MyShapeLibrary, qui organise les classes liées aux formes.
    public abstract class Forme
    {
        // Définition de la classe abstraite Forme (Forme).
        // Une classe abstraite ne peut pas être instanciée directement et sert de base pour d'autres classes.

        #region Variables and proprieties
        // Début d'une région de code pour les variables et propriétés.
        protected Coordonnee _pointAccroche;
        // Déclaration d'une variable protégée _pointAccroche de type Coordonnee.
        // "protected" signifie que cette variable est accessible dans la classe Forme et ses classes dérivées.

        public Coordonnee PointAccroche
        {
            // Définition de la propriété publique PointAccroche de type Coordonnee.
            get => _pointAccroche;
            // Getter : Utilise une expression lambda pour renvoyer la valeur de _pointAccroche.
            set => _pointAccroche = value;
            // Setter : Utilise une expression lambda pour définir la valeur de _pointAccroche.
        }
        #endregion
        // Fin de la région des variables et propriétés.

        #region Constructors
        // Début de la région des constructeurs.
        public Forme()
        {
            // Constructeur sans paramètres de la classe Forme.
            _pointAccroche = new Coordonnee();
            // Initialise la variable _pointAccroche avec une nouvelle instance de Coordonnee (par défaut 0,0).
        }
        #endregion
        // Fin de la région des constructeurs.
    }
}