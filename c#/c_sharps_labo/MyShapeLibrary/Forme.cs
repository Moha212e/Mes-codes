using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyShapeLibrary
{
    // La classe Forme est abstraite, ce qui signifie qu'on ne peut pas créer d'objets Forme directement.
    // D'autres classes peuvent hériter de Forme.
    public abstract class Forme
    {
        // Variable privée pour stocker le point d'accroche de la forme
        private Coordonne point_accroche;

        // Propriété pour accéder et modifier le point d'accroche de la forme
        public Coordonne PA
        {
            get { return point_accroche; }
            set { point_accroche = value; }
        }

        // Constructeur protégé par défaut qui initialise le point d'accroche
        // Le mot clé 'protected' permet aux classes dérivées d'utiliser ce constructeur
        protected Forme()
        {
            // Initialise le point d'accroche avec une nouvelle instance de Coordonne
            point_accroche = new Coordonne();
        }
    }
}