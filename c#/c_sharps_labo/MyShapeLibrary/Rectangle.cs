using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyShapeLibrary
{
    public class Rectangle : Forme, IEstDans, IPolygone
    {
        // Propri�t�s pour la longueur et la largeur
        public int Longueur { get; set; }
        public int Largeur { get; set; }

        // Constructeur par d�faut
        public Rectangle() : base()
        {
            Longueur = 0;
            Largeur = 0;
        }

        // Constructeur d'initialisation
        public Rectangle(int longueur, int largeur, Coordonne pointAccroche) : base()
        {
            Longueur = longueur;
            Largeur = largeur;
            PA = pointAccroche;
        }

        // Surcharge de la m�thode ToString
        public override string ToString()
        {
            return $"Rectangle [Longueur={Longueur}, Largeur={Largeur}, Point d'accroche={PA}]";
        }

        // Impl�mentation de la m�thode CoordonneEstDans
        public bool CoordonneEstDans(Coordonne p)
        {
            return p.X >= PA.X && p.X <= PA.X + Longueur && p.Y >= PA.Y && p.Y <= PA.Y + Largeur;
        }

        public bool CoordonneeEstDans(Coordonne p)
        {
            throw new NotImplementedException();
        }

        // Impl�mentation de la propri�t� NbSommets
        public int NbSommets
        {
            get { return 4; }
        }
    }
}