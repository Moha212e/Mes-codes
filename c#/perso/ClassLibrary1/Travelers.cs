using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.Json;

namespace MyShapeClass
{
    public class Travelers
    {
        #region Variables & Propriétés

        private static readonly string fileName = "Data/Travelers.json";  // Fichier de stockage

        // Propriétés du voyageur
        public int _IdTravel { get; set; }

        public int _IdTrip { get; set; }
        public string _FirstName { get; set; }
        public string _LastName { get; set; }
        public DateTime _DateOfBirth { get; set; }
        public string _Nationality { get; set; }
        public string _PassportNumber { get; set; }
        public int _Bagage { get; set; }

        #endregion Variables & Propriétés

        #region Constructeurs

        // Constructeur principal
        public Travelers(int idTrip, string firstName, string lastName,
                       DateTime dateOfBirth, string nationality, string passportNumber)
        {
            _IdTrip = idTrip;
            _FirstName = firstName;
            _LastName = lastName;
            _DateOfBirth = dateOfBirth;
            _Nationality = nationality;
            _PassportNumber = passportNumber;
            _Bagage = 0;  // Valeur par défaut
        }

        // Constructeur pour bagages
        public Travelers(int id, List<Travelers> travelers)
        {
            _IdTravel = id;
            // On copie les autres infos du voyageur correspondant
            var traveler = travelers.FirstOrDefault(t => t._IdTravel == id);
            if (traveler != null)
            {
                _IdTrip = traveler._IdTrip;
                _FirstName = traveler._FirstName;
                _LastName = traveler._LastName;
                _DateOfBirth = traveler._DateOfBirth;
                _Nationality = traveler._Nationality;
                _PassportNumber = traveler._PassportNumber;
            }
        }

        #endregion Constructeurs

        #region Méthodes de Validation

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
                t._IdTrip == tripId &&
                t._FirstName.ToLower() == firstName.ToLower() &&
                t._LastName.ToLower() == lastName.ToLower());
        }

        // Vérifie les informations avant de les sauvegarder
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

        #endregion Méthodes de Validation

        #region Méthodes de Chargement & Sauvegarde

        // Charge les voyageurs d'un voyage spécifique
        public static List<Travelers> LoadTravelers(int idTrip)
        {
            return LoadAllTravelers().Where(t => t._IdTrip == idTrip).ToList();
        }

        // Charge tous les voyageurs depuis le fichier
        public static List<Travelers> LoadAllTravelers()
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
                    return new List<Travelers>();
                }

                // 3. Lire le contenu du fichier
                string jsonContent = File.ReadAllText(fileName);

                // 4. Si le fichier est vide, retourner une liste vide
                if (string.IsNullOrWhiteSpace(jsonContent))
                {
                    return new List<Travelers>();
                }

                // 5. Convertir le JSON en liste de voyageurs
                List<Travelers> travelers = JsonSerializer.Deserialize<List<Travelers>>(jsonContent);

                // 6. Retourner la liste (ou une nouvelle liste si null)
                return travelers ?? new List<Travelers>();
            }
            catch (Exception error)
            {
                // En cas d'erreur, afficher le message et retourner une liste vide
                Console.WriteLine("Une erreur est survenue: " + error.Message);
                return new List<Travelers>();
            }
        }

        // Sauvegarde un nouveau voyageur avec ses bagages
        public static int SaveTravel(List<Travelers> travelers)
        {
            try
            {
                var allTravelers = LoadAllTravelers();
                int newId = allTravelers.Count > 0 ? allTravelers.Max(t => t._IdTravel) + 1 : 1;

                // On suppose qu'on veut ajouter le premier voyageur de la liste avec ses bagages
                if (travelers.Count > 0)
                {
                    var travelerWithBaggage = new Travelers(newId, travelers);
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

        #endregion Méthodes de Chargement & Sauvegarde
    }
}