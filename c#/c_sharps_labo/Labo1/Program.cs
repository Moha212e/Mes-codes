
using MyShapeLibrary;
namespace Labo1
{
    internal class Program
    {
        static void Main(string[] args)
        {
            // Crée un carré de 5 de côté
            Carre carre1 = new Carre(5);
            System.Console.WriteLine(carre1.ToString());

            // Crée un carré de 10 de côté
            Carre carre2 = new Carre(10);
            System.Console.WriteLine(carre2.ToString());

            Console.ReadLine();
        }
    }
}
