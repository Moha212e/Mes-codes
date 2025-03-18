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
    public class FormeSurfaceComparer : IComparer<Forme>
    {
        // Définition de la classe FormeSurfaceComparer qui implémente l'interface IComparer<Forme>.
        // IComparer<Forme> : Permet de comparer des instances de Forme en fonction de leur surface.

        #region Methods
        // Début de la région des méthodes.
        public int Compare(Forme? one, Forme? two)
        {
            // Implémentation de la méthode Compare de l'interface IComparer<Forme>.
            // Cette méthode compare deux objets Forme et renvoie un entier indiquant leur ordre relatif en fonction de leur surface.
            if (one == null) return 1;
            // Si le premier objet est null, il est considéré comme plus grand que le second (non null).
            if (two == null) return -1;
            // Si le second objet est null, il est considéré comme plus petit que le premier (non null).

            double surfaceOne = CalculerSurface(one);
            // Calcule la surface du premier objet Forme en utilisant la méthode CalculerSurface.
            double surfaceTwo = CalculerSurface(two);
            // Calcule la surface du second objet Forme en utilisant la méthode CalculerSurface.

            return surfaceOne.CompareTo(surfaceTwo);
            // Compare les surfaces des deux formes et renvoie un entier indiquant leur ordre relatif.
            // Renvoie un entier négatif si surfaceOne < surfaceTwo, zéro si surfaceOne == surfaceTwo, et un entier positif si surfaceOne > surfaceTwo.
        }

        private double CalculerSurface(Forme forme)
        {
            // Méthode privée pour calculer la surface d'un objet Forme.
            switch (forme)
            {
                // Utilise un switch statement pour déterminer le type de la forme et appeler la méthode de calcul de surface appropriée.
                case Carre carre:
                    // Si la forme est un Carre (Carré).
                    return MathHelpers.CalculerSurface(carre);
                // Appelle la méthode CalculerSurface de la classe MathHelpers (non montrée ici) pour calculer la surface du carré.
                case Rectangle rectangle:
                    // Si la forme est un Rectangle.
                    return MathHelpers.CalculerSurface(rectangle);
                // Appelle la méthode CalculerSurface de la classe MathHelpers pour calculer la surface du rectangle.
                case Cercle cercle:
                    // Si la forme est un Cercle (Cercle).
                    return MathHelpers.CalculerSurface(cercle);
                // Appelle la méthode CalculerSurface de la classe MathHelpers pour calculer la surface du cercle.
                default:
                    // Si le type de la forme n'est pas reconnu.
                    throw new ArgumentException("Type de forme inconnu");
                    // Lance une exception ArgumentException pour indiquer que le type de forme n'est pas pris en charge.
            }
        }
        #endregion
        // Fin de la région des méthodes.
    }
}