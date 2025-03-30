using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;

namespace MyShapeClass
{
    public class Trip
    {
        private static string filePath = "Data/Trips.json";

        public int IdTrip { get; set; }
        public string Destination { get; set; }
        public string Description { get; set; }
        public string Date { get; set; }
        public int Price { get; set; }
        public string Image { get; set; }

        public static List<Trip> LoadTrips()
        {
            try
            {
                if (File.Exists(filePath))
                {
                    // Lit le fichier JSON
                    string json = File.ReadAllText(filePath);

                    // Désérialise le JSON en une liste d'objets Trip
                    List<Trip> trips = JsonSerializer.Deserialize<List<Trip>>(json);

                    return trips ?? new List<Trip>();
                }
                else
                {
                    // Si le fichier n'existe pas, on retourne une liste vide
                    return new List<Trip>();
                }
            }
            catch (Exception ex)
            {
                // Gère les exceptions et retourne une liste vide
                Console.WriteLine($"Erreur lors du chargement des voyages : {ex.Message}");
                return new List<Trip>();
            }
        }

        public static List<Trip> SearchTrips(string destination, string date, int price)
        {
            try
            {
                // Charge tous les voyages
                List<Trip> trips = LoadTrips();
                // Filtre les voyages en fonction des critères de recherche
                List<Trip> filteredTrips = trips.FindAll(trip =>
                    (string.IsNullOrEmpty(destination) || trip.Destination.ToLower().Contains(destination.ToLower())) &&
                    (string.IsNullOrEmpty(date) || trip.Date == date)
                );
                return filteredTrips;
            }
            catch (Exception ex)
            {
                // Gère les exceptions et retourne une liste vide
                Console.WriteLine($"Erreur lors de la recherche des voyages : {ex.Message}");
                return new List<Trip>();
            }
        }

        // fonction qui cherche le nom du voyage avec l'id

        public static string FindTripById(int id)
        {
            string destination = "";
            // Charge tous les voyages
            List<Trip> trips = LoadTrips();
            // Cherche le voyage avec l'ID spécifié
            Trip trip = trips.Find(t => t.IdTrip == id);
            return trip.Destination;
        }

        public static int SaveTrips(string destination, string description, string date, int price, string image)
        {
            // Charge tous les voyages
            List<Trip> trips = LoadTrips();

            // Crée un nouvel objet Trip
            Trip newTrip = new Trip
            {
                IdTrip = trips.Count + 1,
                Destination = destination,
                Description = description,
                Date = date,
                Price = price,
                Image = image
            };
            // compare les voyages s'il existe déjà
            foreach (Trip trip in trips)
            {
                if (trip.Destination == destination && trip.Date == date)
                {
                    return -1;
                }
            }
            // Ajoute le nouveau voyage à la liste
            trips.Add(newTrip);
            return trips.Count;
        }
    }
}