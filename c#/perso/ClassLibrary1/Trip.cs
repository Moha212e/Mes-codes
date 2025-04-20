using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace MyShapeClass
{
    /// <summary>
    /// Classe représentant un voyage dans le système.
    /// Gère la création, la recherche et la gestion des voyages.
    /// </summary>
    public class Trip
    {
        #region Enums

        /// <summary>
        /// Enumération des erreurs possibles lors de l'ajout d'un voyage
        /// </summary>
        public enum addTripError
        {
            /// <summary>Ajout réussi</summary>
            Success = 0,
            /// <summary>Le voyage existe déjà</summary>
            TripAlreadyExists = 1,
            /// <summary>Erreur générale</summary>
            Error = 2
        }

        #endregion Enums

        #region Variables & Propriétés

        /// <summary>
        /// Chemin du fichier de stockage des voyages
        /// </summary>
        private static readonly string _filePath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "Data", "Trips.json");

        /// <summary>
        /// Identifiant unique du voyage
        /// </summary>
        [JsonPropertyName("_idTrip")]
        private int _idTrip { get; set; }

        /// <summary>
        /// Destination du voyage
        /// </summary>
        [JsonPropertyName("_destination")]
        private string _destination { get; set; }

        /// <summary>
        /// Description détaillée du voyage
        /// </summary>
        [JsonPropertyName("_description")]
        private string _description { get; set; }

        /// <summary>
        /// Date du voyage au format string
        /// </summary>
        [JsonPropertyName("_date")]
        private string _date { get; set; }

        /// <summary>
        /// Prix du voyage en unité monétaire
        /// </summary>
        [JsonPropertyName("_price")]
        private int _price { get; set; }

        /// <summary>
        /// Chemin de l'image représentant le voyage
        /// </summary>
        [JsonPropertyName("_image")]
        private string _image { get; set; }

        /// <summary>
        /// Durée du voyage en jours
        /// </summary>
        [JsonPropertyName("_duration")]
        private int _duration { get; set; }

        /// <summary>
        /// Indique si le voyage a été supprimé logiquement
        /// </summary>
        [JsonPropertyName("_isDeleted")]
        private bool _isDeleted { get; set; }

        /// <summary>
        /// Propriété publique pour accéder à l'identifiant du voyage
        /// </summary>
        public int IdTrip
        {
            get { return _idTrip; }
            set { _idTrip = value; }
        }

        /// <summary>
        /// Propriété publique pour accéder à la destination du voyage
        /// </summary>
        public string Destination
        {
            get { return _destination; }
            set { _destination = value; }
        }

        /// <summary>
        /// Propriété publique pour accéder à la description du voyage
        /// </summary>
        public string Description
        {
            get { return _description; }
            set { _description = value; }
        }

        /// <summary>
        /// Propriété publique pour accéder à la date du voyage
        /// </summary>
        public string Date
        {
            get { return _date; }
            set { _date = value; }
        }

        /// <summary>
        /// Propriété publique pour accéder au prix du voyage
        /// </summary>
        public int Price
        {
            get { return _price; }
            set { _price = value; }
        }

        /// <summary>
        /// Propriété publique pour accéder à l'image du voyage
        /// </summary>
        public string Image
        {
            get { return _image; }
            set { _image = value; }
        }

        /// <summary>
        /// Propriété publique pour accéder à la durée du voyage
        /// </summary>
        public int Duration
        {
            get { return _duration; }
            set { _duration = value; }
        }

        /// <summary>
        /// Propriété publique pour accéder au statut de suppression du voyage
        /// </summary>
        public bool IsDeleted
        {
            get { return _isDeleted; }
            set { _isDeleted = value; }
        }

        #endregion Variables & Propriétés

        #region Constructeurs

        /// <summary>
        /// Constructeur sans paramètres requis par la désérialisation JSON
        /// </summary>
        public Trip()
        { }

        /// <summary>
        /// Constructeur complet pour créer un nouveau voyage
        /// </summary>
        /// <param name="idTrip">Identifiant unique du voyage</param>
        /// <param name="destination">Destination du voyage</param>
        /// <param name="description">Description détaillée du voyage</param>
        /// <param name="date">Date du voyage</param>
        /// <param name="price">Prix du voyage</param>
        /// <param name="image">Chemin de l'image représentant le voyage</param>
        /// <param name="duration">Durée du voyage en jours</param>
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

        /// <summary>
        /// Charge la liste des voyages depuis le fichier JSON
        /// </summary>
        /// <param name="includeDeleted">Si true, inclut les voyages marqués comme supprimés</param>
        /// <returns>Liste des voyages disponibles</returns>
        public static List<Trip> LoadTrips(bool includeDeleted = false)
        {
            try
            {
                Debug.WriteLine($"Chemin du fichier JSON : {Path.GetFullPath(_filePath)}");

                if (!File.Exists(_filePath))
                {
                    Debug.WriteLine("⚠️ Le fichier JSON n'existe pas. Retour d'une liste vide.");
                    return new List<Trip>();
                }

                Debug.WriteLine("Le fichier JSON existe. Lecture en cours...");
                string json = File.ReadAllText(_filePath);
                Debug.WriteLine($"Contenu brut du fichier JSON : {json}");

                List<Trip> trips = JsonSerializer.Deserialize<List<Trip>>(json) ?? new List<Trip>();

                if (!includeDeleted)
                {
                    trips = trips.FindAll(t => !t._isDeleted);
                }

                Debug.WriteLine($"Nombre de voyages chargés : {trips.Count}");
                return trips;
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"❌ Erreur lors du chargement des voyages : {ex.Message}");
                return new List<Trip>();
            }
        }

        #endregion Méthodes de Chargement

        #region Méthodes de Recherche

        /// <summary>
        /// Recherche des voyages selon différents critères
        /// </summary>
        /// <param name="destination">Destination recherchée (peut être partielle)</param>
        /// <param name="date">Date spécifique du voyage</param>
        /// <param name="price">Prix maximum du voyage (-1 pour ignorer)</param>
        /// <returns>Liste des voyages correspondant aux critères</returns>
        public static List<Trip> SearchTrips(string destination, string date, int price)
        {
            try
            {
                List<Trip> trips = LoadTrips();
                return trips.FindAll(trip =>
                    (string.IsNullOrEmpty(destination) || trip._destination.ToLower().Contains(destination.ToLower())) &&
                    (string.IsNullOrEmpty(date) || trip._date == date) &&
                    (price == 0 || trip._price <= price || price == -1)  // Ajout de la condition 'price == -1' pour ignorer le prix si non précisé
                );
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erreur lors de la recherche des voyages : {ex.Message}");
                return new List<Trip>();
            }
        }

        /// <summary>
        /// Récupère la destination d'un voyage par son identifiant
        /// </summary>
        /// <param name="id">Identifiant du voyage</param>
        /// <returns>Nom de la destination ou chaîne vide si non trouvé</returns>
        public static string GetTripDestinationById(int id)
        {
            List<Trip> trips = LoadTrips();
            Trip trip = trips.Find(t => t._idTrip == id);
            return trip?._destination ?? "";
        }

        #endregion Méthodes de Recherche

        #region Méthodes de Sauvegarde

        /// <summary>
        /// Ajoute un nouveau voyage dans le système
        /// </summary>
        /// <param name="destination">Destination du voyage</param>
        /// <param name="description">Description détaillée du voyage</param>
        /// <param name="date">Date du voyage</param>
        /// <param name="price">Prix du voyage</param>
        /// <param name="image">Chemin de l'image représentant le voyage</param>
        /// <param name="duration">Durée du voyage en jours</param>
        /// <returns>Code d'erreur (0 = succès)</returns>
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

        /// <summary>
        /// Sauvegarde une image localement pour un voyage
        /// </summary>
        /// <param name="sourcePath">Chemin source de l'image</param>
        /// <param name="destination">Destination du voyage (utilisée pour nommer le fichier)</param>
        /// <returns>Nom du fichier image sauvegardé</returns>
        public static string SaveImageLocally(string sourcePath, string destination)
        {
            string imagesFolder = "voyaggo\\Images\\"; // Dossier où l'image sera sauvegardée

            // Crée le dossier si nécessaire
            Directory.CreateDirectory(imagesFolder);

            // Crée un nom de fichier basé sur la destination
            string fileName = $"{destination.Replace(" ", "_")}{Path.GetExtension(sourcePath)}";
            string destinationPath = Path.Combine(imagesFolder, fileName);

            // Copie l'image dans le dossier local
            File.Copy(sourcePath, destinationPath, true);

            return fileName; // Retourne le nom du fichier
        }

        /// <summary>
        /// Sauvegarde la liste des voyages dans le fichier JSON
        /// </summary>
        /// <param name="trips">Liste des voyages à sauvegarder</param>
        /// <returns>0 si succès, 1 si erreur</returns>
        public static int SaveVoyages(List<Trip> trips)
        {
            try
            {
                string json = JsonSerializer.Serialize(trips, new JsonSerializerOptions { WriteIndented = true });
                File.WriteAllText(_filePath, json);
                Debug.WriteLine("Voyages sauvegardés avec succès");
                return 0;
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"❌ Erreur lors de la sauvegarde des voyages : {ex.Message}");
                return 1;
            }
        }

        /// <summary>
        /// Recherche un voyage par son identifiant et retourne sa destination
        /// </summary>
        /// <param name="idTrip">Identifiant du voyage</param>
        /// <returns>Destination du voyage ou message d'erreur si non trouvé</returns>
        public static string FindTripById(int idTrip)
        {
            List<Trip> trips = LoadTrips();
            Trip trip = trips.Find(t => t._idTrip == idTrip);
            if (trip != null)
            {
                return trip._destination; // Retourne la destination du voyage
            }
            else
            {
                return "Aucune destination trouvée"; // Si aucun voyage n'est trouvé
            }
        }

        #endregion Méthodes de Sauvegarde
    }
}