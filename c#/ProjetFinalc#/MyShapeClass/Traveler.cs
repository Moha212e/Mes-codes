using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.Json;

namespace MyShapeClass
{
    public class Traveler
    {
        private static readonly string fileName = "Data/Travelers.json";  // Fichier de stockage

        // Propriétés du voyageur
        public int IdTravel { get; set; }

        public int IdTrip { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime DateOfBirth { get; set; }
        public string Nationality { get; set; }
        public string PassportNumber { get; set; }
        public int Bagage { get; set; }

        // Constructeur principal
        public Traveler(int idTrip, string firstName, string lastName,
                       DateTime dateOfBirth, string nationality, string passportNumber)
        {
            IdTrip = idTrip;
            FirstName = firstName;
            LastName = lastName;
            DateOfBirth = dateOfBirth;
            Nationality = nationality;
            PassportNumber = passportNumber;
            Bagage = 0;  // Valeur par défaut
        }

        // Constructeur pour bagages
        public Traveler(int id, List<Traveler> travelers)
        {
            IdTravel = id;
            // On copie les autres infos du voyageur correspondant
            var traveler = travelers.FirstOrDefault(t => t.IdTravel == id);
            if (traveler != null)
            {
                IdTrip = traveler.IdTrip;
                FirstName = traveler.FirstName;
                LastName = traveler.LastName;
                DateOfBirth = traveler.DateOfBirth;
                Nationality = traveler.Nationality;
                PassportNumber = traveler.PassportNumber;
            }
        }

        // Vérifie si le passeport est valide (8 caractères)
        public static bool IsPassportValid(string passportNumber)
        {
            return passportNumber != null && passportNumber.Length == 8;
        }

        // Vérifie si un voyageur existe déjà
        public static bool TravelerExists(int tripId, string firstName, string lastName)
        {
            var travelers = LoadAllTravelers();
            return travelers.Any(t =>
                t.IdTrip == tripId &&
                t.FirstName.ToLower() == firstName.ToLower() &&
                t.LastName.ToLower() == lastName.ToLower());
        }

        // Charge les voyageurs d'un voyage spécifique
        public static List<Traveler> LoadTravelers(int idTrip)
        {
            return LoadAllTravelers().Where(t => t.IdTrip == idTrip).ToList();
        }

        // Charge tous les voyageurs depuis le fichier
        public static List<Traveler> LoadAllTravelers()
        {
            try
            {
                // 1. Vérifier si le dossier existe, sinon le créer
                string folderPath = Path.GetDirectoryName(fileName);
                if (!Directory.Exists(folderPath))
                {
                    Directory.CreateDirectory(folderPath);
                }

                // 2. Vérifier si le fichier existe
                if (!File.Exists(fileName))
                {
                    // Créer un nouveau fichier avec une liste vide
                    File.WriteAllText(fileName, "[]");
                    return new List<Traveler>();
                }

                // 3. Lire le contenu du fichier
                string jsonContent = File.ReadAllText(fileName);

                // 4. Si le fichier est vide, retourner une liste vide
                if (string.IsNullOrWhiteSpace(jsonContent))
                {
                    return new List<Traveler>();
                }

                // 5. Convertir le JSON en liste de voyageurs
                List<Traveler> travelers = JsonSerializer.Deserialize<List<Traveler>>(jsonContent);

                // 6. Retourner la liste (ou une nouvelle liste si null)
                return travelers ?? new List<Traveler>();
            }
            catch (Exception error)
            {
                // En cas d'erreur, afficher le message et retourner une liste vide
                Console.WriteLine("Une erreur est survenue: " + error.Message);
                return new List<Traveler>();
            }
        }

        // Sauvegarde un nouveau voyageur avec ses bagages
        public static int SaveTravel(List<Traveler> travelers)
        {
            try
            {
                var allTravelers = LoadAllTravelers();
                int newId = allTravelers.Count > 0 ? allTravelers.Max(t => t.IdTravel) + 1 : 1;

                // On suppose qu'on veut ajouter le premier voyageur de la liste avec ses bagages
                if (travelers.Count > 0)
                {
                    var travelerWithBaggage = new Traveler(newId, travelers);
                    allTravelers.Add(travelerWithBaggage);
                }

                File.WriteAllText(fileName, JsonSerializer.Serialize(allTravelers));
                return newId;
            }
            catch (Exception)
            {
                return -1;
            }
        }

        public static int verifiryInfos(int id, string nom, string prenom, DateTime dateNaissance, string nationalite, string passport)
        {
            if (!IsPassportValid(passport))
            {
                return 1;
            }
            if (TravelerExists(id, nom, prenom))
            {
                return 2;
            }
            return 0;
        }
    }
}