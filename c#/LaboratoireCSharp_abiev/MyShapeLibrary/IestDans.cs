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
    public interface IestDans
    {
        // Définition de l'interface IestDans (Est dans).
        // Une interface définit un contrat que les classes doivent implémenter.

        #region Methods
        // Début de la région des méthodes.
        bool CoordonneeEstDans(Coordonnee p);
        // Déclaration de la méthode CoordonneeEstDans.
        // Cette méthode prend un objet Coordonnee (Coordonnée) en paramètre et renvoie un booléen.
        // Elle doit vérifier si la coordonnée donnée est à l'intérieur de la forme qui implémente cette interface.
        #endregion
        // Fin de la région des méthodes.
    }
}