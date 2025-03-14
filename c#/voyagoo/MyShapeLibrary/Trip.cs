using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;

namespace MyShapeLibrary
{
    public class Trip
    {
        public static string TripsFilePath = @"Data\trips.json";

        public int IdTrip { get; set; }
        public string Name { get; set; } = string.Empty;
        public string Description { get; set; } = string.Empty;
        public int Price { get; set; }
        public DateTime Date { get; set; }
        public string ImageUrl { get; set; } = string.Empty;

        public static List<Trip> LoadTrips()
        {
            if (!File.Exists(TripsFilePath))
            {
                File.WriteAllText(TripsFilePath, "[]");
                return new List<Trip>();
            }

            string json = File.ReadAllText(TripsFilePath);
            return JsonSerializer.Deserialize<List<Trip>>(json) ?? new List<Trip>();
        }

        public static void SaveTrips(List<Trip> trips)
        {
            string json = JsonSerializer.Serialize(trips, new JsonSerializerOptions { WriteIndented = true });
            Directory.CreateDirectory(Path.GetDirectoryName(TripsFilePath)!);
            File.WriteAllText(TripsFilePath, json);
        }

        public void RegisterTrip()
        {
            var trips = LoadTrips();
            trips.Add(this);
            SaveTrips(trips);
        }
    }
}