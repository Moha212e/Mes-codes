namespace MyShapeLibrary
{
    public class Coordonne(int x, int y)
    {
        // Variables membres
        private int x = x;
        private int y = y;

        // Propriétés
        public int X
        {
            get { return x; }
            set { x = value; }
        }

        public int Y
        {
            get { return y; }
            set { y = value; }
        }
       
        // Constructeur par défaut
        public Coordonne() : this(0, 0)
        {
        }

        // Surcharge de la méthode ToString()
        public override string ToString()
        {
            return $"({X},{Y})";
        }
    }
}
