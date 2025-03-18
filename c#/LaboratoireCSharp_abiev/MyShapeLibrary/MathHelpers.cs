namespace MyShapeLibrary
{
    // Définition de l'espace de noms MyShapeLibrary, qui organise les classes liées aux formes.
    public class MathHelpers
    {
        // Définition de la classe MathHelpers, qui contient des méthodes statiques pour les calculs mathématiques liés aux formes.

        #region Methods
        // Début de la région des méthodes.
        public static int CalculerSurface(Carre carre)
        {
            // Méthode statique pour calculer la surface d'un Carre (Carré).
            return carre.Length * carre.Length;
            // Renvoie la surface du carré, qui est la longueur du côté multipliée par elle-même.
        }

        public static double CalculerSurface(Cercle cercle)
        {
            // Méthode statique pour calculer la surface d'un Cercle (Cercle).
            return Math.PI * cercle.Radius * cercle.Radius;
            // Renvoie la surface du cercle, qui est π (pi) multiplié par le rayon au carré.
        }

        public static int CalculerSurface(Rectangle rectangle)
        {
            // Méthode statique pour calculer la surface d'un Rectangle.
            return rectangle.Length * rectangle.Width;
            // Renvoie la surface du rectangle, qui est la longueur multipliée par la largeur.
        }

        public static bool EstDansIntervalle(int valeur, int min, int max)
        {
            // Méthode statique pour vérifier si une valeur est dans un intervalle donné.
            return valeur >= min && valeur <= max;
            // Renvoie true si la valeur est supérieure ou égale à min et inférieure ou égale à max, sinon renvoie false.
        }
        #endregion
        // Fin de la région des méthodes.
    }
}