using MyShapeLibrary;
using System;
using System.Collections.Generic;

class Program
{
    static void Main(string[] args)
    {
        Console.WriteLine("--- Création et Affichage des Formes ---");

        Carre carreDefault = new Carre();
        Carre carre = new Carre(3, new Coordonnee(3, 6));
        Rectangle rectangle = new Rectangle(5, 4, new Coordonnee(4, 7));
        Rectangle rectangleDefault = new Rectangle();
        Cercle cercle = new Cercle(6, new Coordonnee(6, 8));
        Cercle cercleDefault = new Cercle();

        Console.WriteLine($"Cercle: {cercle}");
        Console.WriteLine($"Cercle par défaut: {cercleDefault}");
        Console.WriteLine($"Carré: {carre}");
        Console.WriteLine($"Carré par défaut: {carreDefault}");
        Console.WriteLine($"Rectangle: {rectangle}");
        Console.WriteLine($"Rectangle par défaut: {rectangleDefault}");

        Console.WriteLine("\n--- Liste de Toutes les Formes ---");

        List<Forme> list = new List<Forme> { cercle, cercleDefault, carre, carreDefault, rectangle, rectangleDefault };
        foreach (Forme forme in list)
        {
            Console.WriteLine(forme);
        }

        Console.WriteLine("\n--- Affichage des Polygones (Implémentant IPolygone) ---");

        foreach (Forme forme in list)
        {
            if (forme is IPolygone)
            {
                Console.WriteLine(forme);
            }
        }

        Console.WriteLine("\n--- Affichage des Non-Polygones (N'implémentant pas IPolygone) ---");

        foreach (Forme forme in list)
        {
            if (forme is not IPolygone)
            {
                Console.WriteLine(forme);
            }
        }

        Console.WriteLine("\n--- Liste de Carrés, Tri par Longueur de Côté ---");

        List<Carre> listCarre = new List<Carre>
        {
            new Carre(4, new Coordonnee(3, 4)),
            new Carre(2, new Coordonnee(7, 1)),
            new Carre(3, new Coordonnee(4, 3)),
            new Carre(3, new Coordonnee(5, 2)),
            new Carre(5, new Coordonnee(2, 5))
        };

        Console.WriteLine("Carrés non triés:");
        foreach (Carre C in listCarre)
        {
            Console.WriteLine(C);
        }

        listCarre.Sort();
        Console.WriteLine("\nCarrés triés par longueur:");
        foreach (Carre C in listCarre)
        {
            Console.WriteLine(C);
        }

        Console.WriteLine("\n--- Liste de Carrés, Tri par Abscisse du Point d'Accroche ---");

        listCarre.Sort(new FormeAbscisseComparer());
        Console.WriteLine("Carrés triés par abscisse:");
        foreach (Carre C in listCarre)
        {
            Console.WriteLine(C);
        }

        Console.WriteLine("\n--- Recherche d'Éléments dans la Liste de Carrés ---");

        int tailleRef = 3;
        Carre? element = listCarre.Find(c => c.Length == tailleRef);
        int indexElement = listCarre.FindIndex(c => c.Length == tailleRef);
        Console.WriteLine($"Taille de référence: {tailleRef}");
        Console.WriteLine($"Élément trouvé: {element}");
        Console.WriteLine($"Index de l'élément: {indexElement}");

        tailleRef = 2;
        Carre? elementLast = listCarre.FindLast(c => c.Length == tailleRef);
        int indexElementLast = listCarre.FindLastIndex(c => c.Length == tailleRef);
        Console.WriteLine($"\nTaille de référence: {tailleRef}");
        Console.WriteLine($"Dernier élément trouvé: {elementLast}");
        Console.WriteLine($"Dernier index de l'élément: {indexElementLast}");

        tailleRef = 3;
        List<Carre> elementAll = listCarre.FindAll(c => c.Length == tailleRef);
        Console.WriteLine($"\nTaille de référence: {tailleRef}");
        Console.WriteLine("Éléments trouvés:");
        foreach (Carre c in elementAll)
        {
            Console.WriteLine(c);
        }

        tailleRef = 4;
        bool elementExist = listCarre.Exists(c => c.Length == tailleRef);
        Console.WriteLine($"\nTaille de référence: {tailleRef}");
        Console.WriteLine($"Élément trouvé: {elementExist}");

        Carre carreRef = new Carre(2, new Coordonnee(7, 1));
        bool elementEgal = listCarre.Contains(carreRef);
        Console.WriteLine($"\nCarré de référence: {carreRef}");
        Console.WriteLine($"Élément égal au carré de référence: {elementEgal}");

        Console.WriteLine("\n--- Recherche de Formes Contenant un Point Spécifique ---");

        List<Forme> listFormes = new List<Forme>
        {
            new Carre(4, new Coordonnee(2, 2)),
            new Rectangle(3, 5, new Coordonnee(5, 1)),
            new Cercle(3, new Coordonnee(7, 7)),
            new Carre(2, new Coordonnee(10, 10))
        };

        Coordonnee pointRecherche = new Coordonnee(7, 7);
        Console.WriteLine("Formes:");
        foreach (Forme forme in listFormes)
        {
            Console.WriteLine(forme);
        }
        Console.WriteLine($"\nPoint de recherche: {pointRecherche}");
        List<Forme> formeContenantPoints = listFormes.FindAll(f => f is IestDans i && i.CoordonneeEstDans(pointRecherche));
        Console.WriteLine("Formes contenant le point:");
        foreach (Forme forme in formeContenantPoints)
        {
            Console.WriteLine(forme);
        }

        Console.WriteLine("\n--- Calcul de Surfaces et Tri par Surface ---");

        Console.WriteLine("Calcul de la surface des formes du premier test:");
        Console.WriteLine($"Surface du carré: {MathHelpers.CalculerSurface(carre)}");
        Console.WriteLine($"Surface du rectangle: {MathHelpers.CalculerSurface(rectangle)}");
        Console.WriteLine($"Surface du cercle: {MathHelpers.CalculerSurface(cercle)}");

        List<Forme> listFormes1 = new List<Forme>
        {
            new Carre(4, new Coordonnee(2, 2)),
            new Rectangle(3, 5, new Coordonnee(5, 1)),
            new Cercle(3, new Coordonnee(7, 7)),
            new Carre(2, new Coordonnee(10, 10))
        };
        Console.WriteLine("\nFormes non triées:");
        foreach (Forme forme in listFormes1)
        {
            Console.WriteLine(forme);
        }

        listFormes1.Sort(new FormeSurfaceComparer());
        Console.WriteLine("\nFormes triées par surface:");
        foreach (Forme forme in listFormes1)
        {
            Console.WriteLine(forme);
        }
    }
}