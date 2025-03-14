// Importation de l'espace de noms pour accéder au registre Windows
using Microsoft.Win32;
// Importation de l'espace de noms principal de .NET
using System;

// Définition de la classe MyAppParamManager pour gérer les paramètres de l'application
public class MyAppParamManager
{
    // Déclaration d'une constante pour le chemin du registre
    private const string RegistryPath = "SOFTWARE\\MyApplication";

    // Propriété pour le nom d'utilisateur avec une valeur par défaut

    // Propriété pour la destination avec une valeur par défaut
    public string Destination { get; set; } = "Unknown";

    // Propriété pour le prix minimum avec une valeur par défaut
    public double PriceMin { get; set; } = 0.0;

    // Propriété pour le prix maximum avec une valeur par défaut
    public double PriceMax { get; set; } = 0.0;

    // Propriété pour la date de voyage avec la date actuelle comme valeur par défaut
    public DateTime TravelDate { get; set; } = DateTime.Now;

    // Méthode pour charger les paramètres depuis le registre
    public void LoadRegistryParameters()
    {
        try
        {
            using (RegistryKey key = Registry.CurrentUser.OpenSubKey(RegistryPath))
            {
                if (key == null)
                {
                    Console.WriteLine("Clé du registre non trouvée. Chargement des valeurs par défaut.");
                    return;
                }


                Destination = key.GetValue(nameof(Destination), Destination) as string ?? Destination;

                if (double.TryParse(key.GetValue(nameof(PriceMin), PriceMin)?.ToString(), out double min))
                {
                    PriceMin = min;
                }

                if (double.TryParse(key.GetValue(nameof(PriceMax), PriceMax)?.ToString(), out double max))
                {
                    PriceMax = max;
                }

                if (DateTime.TryParse(key.GetValue(nameof(TravelDate), TravelDate.ToString()) as string, out DateTime parsedDate))
                {
                    TravelDate = parsedDate;
                }

                Console.WriteLine("Paramètres chargés avec succès depuis le registre.");
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Erreur lors du chargement du registre : {ex.Message}");
        }
    }

    // Méthode pour enregistrer les paramètres dans le registre
    public void SaveRegistryParameters()
    {
        try
        {
            using (RegistryKey key = Registry.CurrentUser.CreateSubKey(RegistryPath))
            {
                if (key == null)
                {
                    Console.WriteLine("Erreur : Impossible de créer ou d'ouvrir la clé du registre.");
                    return;
                }


                key.DeleteValue(nameof(Destination), false);
                key.DeleteValue(nameof(PriceMin), false);
                key.DeleteValue(nameof(PriceMax), false);
                key.DeleteValue(nameof(TravelDate), false);

                key.SetValue(nameof(Destination), Destination ?? "Unknown");
                key.SetValue(nameof(PriceMin), PriceMin);
                key.SetValue(nameof(PriceMax), PriceMax);
                key.SetValue(nameof(TravelDate), TravelDate.ToString("o"));

                Console.WriteLine("Paramètres enregistrés avec succès dans le registre.");
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Erreur lors de l'enregistrement du registre : {ex.Message}");
        }
    }

    // Méthode pour afficher les paramètres (utile pour le débogage)

}
