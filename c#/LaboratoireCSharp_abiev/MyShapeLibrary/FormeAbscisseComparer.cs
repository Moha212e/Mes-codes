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
    public class FormeAbscisseComparer : Forme, IComparer<Forme>
    {
        // Définition de la classe FormeAbscisseComparer qui hérite de Forme et implémente l'interface IComparer<Forme>.
        // Forme : Une classe de base (non montrée ici) probablement pour les formes géométriques.
        // IComparer<Forme> : Permet de comparer des instances de Forme en fonction de l'abscisse de leur point d'accroche.

        #region Methods
        // Début de la région des méthodes.
        public int Compare(Forme? one, Forme? two)
        {
            // Implémentation de la méthode Compare de l'interface IComparer<Forme>.
            // Cette méthode compare deux objets Forme et renvoie un entier indiquant leur ordre relatif.
            if (one == null) return 1;
            // Si le premier objet est null, il est considéré comme plus grand que le second (non null).
            if (two == null) return -1;
            // Si le second objet est null, il est considéré comme plus petit que le premier (non null).
            return one.PointAccroche.X.CompareTo(two.PointAccroche.X);
            // Compare les abscisses (X) des points d'accroche des deux formes.
            // Renvoie un entier négatif si one.X < two.X, zéro si one.X == two.X, et un entier positif si one.X > two.X.
        }
        #endregion
        // Fin de la région des méthodes.
    }
}