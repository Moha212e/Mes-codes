using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace MyShapeClass
{
    public class Trip
    {
        #region Num

        public enum addTripError
        {
            Success = 0,
            TripAlreadyExists = 1,
            Error = 2
        }

        #endregion Num

        #region Variables & Propriétés

        private static readonly string _filePath = "Data/Trips.json";

        [JsonPropertyName("_idTrip")]
        public int _idTrip { get; set; }

        [JsonPropertyName("_destination")]
        public string _destination { get; set; }

        [JsonPropertyName("_description")]
        public string _description { get; set; }

        [JsonPropertyName("_date")]
        public string _date { get; set; }

        [JsonPropertyName("_price")]
        public int _price { get; set; }

        [JsonPropertyName("_image")]
        public string _image { get; set; }

        [JsonPropertyName("_duration")]
        public int _duration { get; set; }

        [JsonPropertyName("_isDeleted")]
        public bool _isDeleted { get; set; }

        #endregion Variables & Propriétés

        #region Constructeurs

        // Constructeur sans paramètres requis par la désérialisation JSON
        public Trip()
        { }

        #endregion Constructeurs

        #region Constructeurs

        public Trip(int idTrip, string destination, string description, string date, int price, string image, int duration)
        {
            _idTrip = idTrip;
            _destination = destination;
            _description = description;
            _date = date;
            _price = price;
            _image = image;
            _duration = duration;
            _isDeleted = false;
        }

        #endregion Constructeurs

        #region Méthodes de Chargement

        public static List<Trip> LoadTrips()
        {
            try
            {
                Debug.WriteLine($"Chemin du fichier JSON : {Path.GetFullPath(_filePath)}");

                if (File.Exists(_filePath))
                {
                    Debug.WriteLine("Le fichier JSON existe. Lecture en cours...");

                    // Lit le fichier JSON
                    string json = File.ReadAllText(_filePath);

                    Debug.WriteLine($"Contenu brut du fichier JSON : {json}");

                    // Désérialise le JSON en une liste d'objets Trip
                    List<Trip> trips = JsonSerializer.Deserialize<List<Trip>>(json);
                    // trie les trips qui sont pas supprimer
                    trips = trips.FindAll(t => !t._isDeleted);

                    Debug.WriteLine($"Nombre de voyages chargés : {trips?.Count ?? 0}");

                    return trips ?? new List<Trip>();
                }
                else
                {
                    Debug.WriteLine("⚠️ Le fichier JSON n'existe pas. Retour d'une liste vide.");
                    return new List<Trip>();
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"❌ Erreur lors du chargement des voyages : {ex.Message}");
                return new List<Trip>();
            }
        }

        public static List<Trip> LoadAllTrips()
        {
            try
            {
                Debug.WriteLine($"Chemin du fichier JSON : {Path.GetFullPath(_filePath)}");

                if (File.Exists(_filePath))
                {
                    Debug.WriteLine("Le fichier JSON existe. Lecture en cours...");

                    // Lit le fichier JSON
                    string json = File.ReadAllText(_filePath);

                    Debug.WriteLine($"Contenu brut du fichier JSON : {json}");

                    // Désérialise le JSON en une liste d'objets Trip
                    List<Trip> trips = JsonSerializer.Deserialize<List<Trip>>(json);
                    // trie les trips qui sont pas supprimer
                    trips = trips.FindAll(t => !t._isDeleted);

                    Debug.WriteLine($"Nombre de voyages chargés : {trips?.Count ?? 0}");

                    return trips ?? new List<Trip>();
                }
                else
                {
                    Debug.WriteLine("⚠️ Le fichier JSON n'existe pas. Retour d'une liste vide.");
                    return new List<Trip>();
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"❌ Erreur lors du chargement des voyages : {ex.Message}");
                return new List<Trip>();
            }
        }

        #endregion Méthodes de Chargement

        #region Méthodes de Recherche

        public static List<Trip> SearchTrips(string destination, string date, int price)
        {
            try
            {
                List<Trip> trips = LoadTrips();
                return trips.FindAll(trip =>
                    (string.IsNullOrEmpty(destination) || trip._destination.ToLower().Contains(destination.ToLower())) &&
                    (string.IsNullOrEmpty(date) || trip._date == date) &&
                    (price == 0 || trip._price <= price)
                );
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erreur lors de la recherche des voyages : {ex.Message}");
                return new List<Trip>();
            }
        }

        public static string GetTripDestinationById(int id)
        {
            List<Trip> trips = LoadTrips();
            Trip trip = trips.Find(t => t._idTrip == id);
            return trip?._destination ?? "";
        }

        #endregion Méthodes de Recherche

        #region Méthodes de Sauvegarde

        public static int AddTrip(string destination, string description, string date, int price, string image, int duration)
        {
            try
            {
                // Charger les voyages existants
                var trips = LoadTrips();

                if (trips.Exists(t => t._destination == destination))
                    return (int)addTripError.TripAlreadyExists;
                var trip = new Trip(trips.Count + 1, destination, description, date, price, image, duration);

                // Ajouter le voyage à la liste et sauvegarder
                trips.Add(trip);
                File.WriteAllText(_filePath, JsonSerializer.Serialize(trips));

                return (int)addTripError.Success;
            }
            catch
            {
                return (int)addTripError.Error;  // Si une erreur survient, retourner -1
            }
        }

        public static string SaveImageLocally(string sourcePath, string destination)
        {
            string imagesFolder = "Data/Images"; // Dossier où l'image sera sauvegardée

            // Crée le dossier si nécessaire
            Directory.CreateDirectory(imagesFolder);

            // Crée un nom de fichier basé sur la destination
            string fileName = $"{destination.Replace(" ", "_")}{Path.GetExtension(sourcePath)}";
            string destinationPath = Path.Combine(imagesFolder, fileName);

            // Copie l'image dans le dossier local
            File.Copy(sourcePath, destinationPath, true);

            return fileName; // Retourne le nom du fichier
        }

        #endregion Méthodes de Sauvegarde
    }
}