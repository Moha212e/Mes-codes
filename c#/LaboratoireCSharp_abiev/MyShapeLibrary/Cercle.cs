using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace MyShapeLibrary
{
    public class Cercle : Forme, IestDans
    {
        #region Variables and proprietes
        private int _radius;

        public int Radius
        {
            get { return _radius; }
            set { _radius = value; }
        }

        public float Diameter
        {
            get { return (float)(_radius * 2 / 3.14); }

        }
        #endregion

        #region Constructors
        public Cercle(int radius, Coordonnee pointAccroche) : base()
        {
            Radius = radius;
            PointAccroche = pointAccroche;
        }
        public Cercle() : this(1, new Coordonnee())
        {

        }
        #endregion

        #region Override
        public override string ToString()
        {
            return $"le rayon vaut {Radius} cm et le diametre vaut {Diameter}, son point d'accroche : {PointAccroche.ToString()}";
        }
        #endregion

        #region Methods
        public bool CoordonneeEstDans(Coordonnee p)
        {
            int dx = p.X - PointAccroche.X;
            int dy = p.Y - PointAccroche.Y;
            return dx * dx + dy * dy <= Radius * Radius;
        }
        #endregion
    }
}