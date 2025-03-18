using System;
// Importe l'espace de noms System, qui contient des types fondamentaux et des classes de base.
using System.Collections.Generic;
// Importe l'espace de noms System.Collections.Generic, qui contient des types de collections génériques.
using System.Linq;
// Importe l'espace de noms System.Linq, qui fournit des méthodes pour les requêtes LINQ.
using System.Text;
// Importe l'espace de noms System.Text, qui contient des classes pour encoder et décoder du texte.
using System.Threading.Tasks;
// Importe l'espace de noms System.Threading.Tasks, qui fournit des types pour la programmation asynchrone (non utilisé ici).

namespace MyShapeLibrary
{
    // Définition de l'espace de noms MyShapeLibrary, qui organise les classes liées aux formes.
    public class Rectangle : Forme, IestDans, IPolygone
    {
        // Définition de la classe Rectangle qui hérite de Forme et implémente les interfaces IestDans et IPolygone.
        // Forme : Une classe de base (non montrée ici) probablement pour les formes géométriques.
        // IestDans : Une interface (non montrée ici) qui définit une méthode pour vérifier si un point est à l'intérieur de la forme.
        // IPolygone : Une interface (non montrée ici) qui définit des propriétés ou méthodes liées aux polygones.

        #region variables and properties
        // Début de la région des variables et propriétés.
        private int _length;
        // Déclaration d'une variable privée _length pour stocker la longueur du rectangle.
        private int _width;
        // Déclaration d'une variable privée _width pour stocker la largeur du rectangle.

        public int Length
        {
            // Définition de la propriété publique Length pour accéder à _length.
            get { return _length; }
            // Getter : Renvoie la valeur de _length.
            set { _length = value; }
            // Setter : Définit la valeur de _length.
        }
        public int Width
        {
            // Définition de la propriété publique Width pour accéder à _width.
            get { return _width; }
            // Getter : Renvoie la valeur de _width.
            set { _width = value; }
            // Setter : Définit la valeur de _width.
        }
        public int NbSommets { get { return 4; } }
        // Définition de la propriété publique NbSommets (Nombre de sommets), qui renvoie toujours 4 (un rectangle a 4 sommets).
        #endregion
        // Fin de la région des variables et propriétés.

        #region Constructors
        // Début de la région des constructeurs.
        public Rectangle(int length, int width, Coordonnee pointAccroche) : base()
        {
            // Constructeur de Rectangle qui prend la longueur, la largeur et le point d'accroche en paramètres.
            // : base() appelle le constructeur de la classe de base (Forme).
            Length = length;
            // Initialise la propriété Length avec la valeur passée en paramètre.
            Width = width;
            // Initialise la propriété Width avec la valeur passée en paramètre.
            PointAccroche = pointAccroche;
            // Initialise la propriété PointAccroche (probablement héritée de Forme) avec le point d'accroche passé en paramètre.
        }
        public Rectangle() : this(1, 1, new Coordonnee())
        {
            // Constructeur sans paramètres qui appelle le constructeur principal avec une longueur et une largeur de 1 et un point d'accroche par défaut (0,0).
        }
        #endregion
        // Fin de la région des constructeurs.

        #region Override
        // Début de la région des méthodes surchargées (override).
        public override string ToString()
        {
            // Surcharge de la méthode ToString() pour renvoyer une représentation textuelle du rectangle.
            return $"la longueur et la largeur du rectangle sont de {Length} et {Width} cm, sont point d'accroche : {PointAccroche.ToString()}";
            // Renvoie une chaîne formatée décrivant la longueur, la largeur et le point d'accroche du rectangle.
        }
        #endregion
        // Fin de la région des méthodes surchargées.

        #region Methods
        // Début de la région des méthodes personnalisées.
        public bool CoordonneeEstDans(Coordonnee p)
        {
            // Méthode pour vérifier si un point (Coordonnee p) est à l'intérieur du rectangle.
            return p.X >= PointAccroche.X && p.X <= PointAccroche.X + Length &&
                   p.Y >= PointAccroche.Y && p.Y <= PointAccroche.Y + Width;
            // Vérifie si les coordonnées X et Y du point sont dans les limites du rectangle.
        }
        #endregion
        // Fin de la région des méthodes.
    }
}