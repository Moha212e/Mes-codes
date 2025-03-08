using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyShapeLibrary
{
    public static class MathHelpers
    {
        // M�thode pour calculer la surface d'un rectangle
        public static double CalculerSurfaceRectangle(double longueur, double largeur)
        {
            return longueur * largeur;
        }

        // M�thode pour calculer la surface d'un cercle
        public static double CalculerSurfaceCercle(double rayon)
        {
            return Math.PI * rayon * rayon;
        }

        // M�thode pour v�rifier si une valeur est comprise entre une valeur min et une valeur max
        public static bool EstCompriseEntre(double valeur, double min, double max)
        {
            return valeur >= min && valeur <= max;
        }
    }
}
