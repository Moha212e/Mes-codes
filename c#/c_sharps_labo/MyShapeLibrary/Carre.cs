using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyShapeLibrary
{
    public class Carre : Forme
    {
        // Variable membre pour la longueur du côté
        private int longueurCote;

        // Propriété pour accéder et modifier la longueur du côté
        public int LongueurCote
        {
            get { return longueurCote; }
            set { longueurCote = value; }
        }

        // Constructeur par défaut
        public Carre() : this(0)
        {
        }

        // Constructeur d'initialisation
        public Carre(int longueurCote) : base()
        {
            this.longueurCote = longueurCote;
        }

        public Carre(int longueurCote, Coordonne coordonne) : this(longueurCote)
        {
        }

        // Surcharge de la méthode ToString()
        public override string ToString()
        {
            return $"Carre: Longueur du côté = {LongueurCote}, Point d'accroche = {PA}";
        }
    }
}
