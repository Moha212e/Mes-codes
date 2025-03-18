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
    public interface IPolygone
    {
        // Définition de l'interface IPolygone.
        // Une interface définit un contrat que les classes doivent implémenter.

        #region Methods
        // Début de la région des méthodes (en réalité, une propriété ici).
        int NbSommets
        {
            // Déclaration de la propriété NbSommets (Nombre de sommets).
            get;
            // Déclaration du getter pour la propriété NbSommets.
            // Cela signifie que les classes qui implémentent cette interface doivent fournir une manière de lire la valeur de NbSommets.
        }
        #endregion
        // Fin de la région des méthodes.
    }
}