using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace MyShapeClass
{
    public class Travelers
    {
        #region ENUM

        public enum TravelerError
        {
            Success = 0,
            TravelerAlreadyExists = 1,
            InvalidPassport = 2,
            TravelerNotFound = 3,
            FileError = 4,
            UnknownError = 5,
            InvalidId = 10,
            InvalidName = 11,
            InvalidFirstName = 12,
            InvalidBirthDate = 13,
            InvalidNationality = 14,
            InvalidTravelerList = 15,
            AccessDenied = 16,
            SerializationError = 17,
            DatabaseError = 18,
            InvalidTripData = 19,
            CapacityExceeded = 20,
            PaymentError = 21,
            ConnectionError = 22,
            TimeoutError = 23,
            InvalidBaggage = 24,
            ReservationExpired = 25
        }

        #endregion ENUM

        #region Variables & Propriétés (privées)

        private static readonly string fileName = "Data/travelers.json";

        private int _IdTravel;
        private int _IdTrip;
        private string _FirstName;
        private string _LastName;
        private DateTime _DateOfBirth;
        private string _Nationality;
        private string _PassportNumber;
        private string _destinination;
        private int _Bagage;

        #endregion Variables & Propriétés (privées)

        #region Propriétés publiques

        public int idTraveler
        {
            get { return _IdTravel; }
            set
            {
                if (value < 0)
                    throw new ArgumentOutOfRangeException(nameof(idTraveler), "L'identifiant du voyageur ne peut pas être négatif.");
                _IdTravel = value;
            }
        }

        public int idTrip
        {
            get { return _IdTrip; }
            set
            {
                if (value < 0)
                    throw new ArgumentOutOfRangeException(nameof(idTrip), "L'identifiant du voyage ne peut pas être négatif.");
                _IdTrip = value;
            }
        }

        public string firstName
        {
            get { return _FirstName; }
            set
            {
                if (string.IsNullOrWhiteSpace(value))
                    throw new ArgumentException("Le prénom ne peut pas être vide.", nameof(firstName));
                _FirstName = value;
            }
        }

        public string lastName
        {
            get { return _LastName; }
            set
            {
                if (string.IsNullOrWhiteSpace(value))
                    throw new ArgumentException("Le nom ne peut pas être vide.", nameof(lastName));
                _LastName = value;
            }
        }

        [JsonPropertyName("dateOfBirth")]
        public DateTime DateNaissance
        {
            get { return _DateOfBirth; }
            set
            {
                if (value > DateTime.Now)
                    throw new ArgumentOutOfRangeException(nameof(DateNaissance), "La date de naissance ne peut pas être dans le futur.");
                _DateOfBirth = value;
            }
        }

        public string Nationality
        {
            get { return _Nationality; }
            set
            {
                if (string.IsNullOrWhiteSpace(value))
                    throw new ArgumentException("La nationalité ne peut pas être vide.", nameof(Nationality));
                _Nationality = value;
            }
        }

        public string PassportNumber
        {
            get { return _PassportNumber; }
            set
            {
                if (string.IsNullOrWhiteSpace(value))
                    throw new ArgumentException("Le numéro de passeport ne peut pas être vide.", nameof(PassportNumber));
                _PassportNumber = value;
            }
        }

        public string destinination
        {
            get { return _destinination; }
            set
            {
                if (string.IsNullOrWhiteSpace(value))
                    throw new ArgumentException("La destination ne peut pas être vide.", nameof(destinination));
                _destinination = value;
            }
        }

        public int bagage
        {
            get { return _Bagage; }
            set
            {
                if (value < 0)
                    throw new ArgumentOutOfRangeException(nameof(bagage), "Le nombre de bagages ne peut pas être négatif.");
                _Bagage = value;
            }
        }

        #endregion Propriétés publiques

        #region Constructeurs

        public Travelers(int idTrip, string firstName, string lastName, DateTime dateOfBirth,
                        string nationality, string passportNumber, string destinination)
        {
            if (TripExistsForTraveler(idTrip, passportNumber))
                throw new InvalidOperationException("Ce voyageur est déjà enregistré pour ce voyage.");

            idTraveler = GenerateNewId();
            this.idTrip = idTrip;
            this.firstName = firstName;
            this.lastName = lastName;
            DateNaissance = dateOfBirth;
            Nationality = nationality;
            PassportNumber = passportNumber;
            this.destinination = destinination;
            bagage = 0;
        }

        public Travelers(int id, List<Travelers> travelers)
        {
            var existingTraveler = travelers.FirstOrDefault(t => t.idTraveler == id);
            if (existingTraveler == null)
                throw new ArgumentException("Voyageur non trouvé", nameof(id));

            idTraveler = existingTraveler.idTraveler;
            idTrip = existingTraveler.idTrip;
            firstName = existingTraveler.firstName;
            lastName = existingTraveler.lastName;
            DateNaissance = existingTraveler.DateNaissance;
            Nationality = existingTraveler.Nationality;
            PassportNumber = existingTraveler.PassportNumber;
            destinination = existingTraveler.destinination;
            bagage = existingTraveler.bagage;
        }

        #endregion Constructeurs

        #region Méthodes de Validation

        public static bool IsPassportValid(string passportNumber)
        {
            return !string.IsNullOrWhiteSpace(passportNumber) &&
                   passportNumber.Length >= 6 &&
                   passportNumber.Length <= 12 &&
                   passportNumber.All(c => char.IsLetterOrDigit(c));
        }

        public static bool TravelerExists(int tripId, string firstName, string lastName)
        {
            var travelers = LoadAllTravelers();
            return travelers.Any(t =>
                t.idTrip == tripId &&
                string.Equals(t.firstName, firstName, StringComparison.OrdinalIgnoreCase) &&
                string.Equals(t.lastName, lastName, StringComparison.OrdinalIgnoreCase));
        }

        public static bool TripExistsForTraveler(int tripId, string passportNumber)
        {
            var travelers = LoadAllTravelers();
            return travelers.Any(t =>
                t.idTrip == tripId &&
                t.PassportNumber.Equals(passportNumber, StringComparison.OrdinalIgnoreCase));
        }

        public static int VerifyInfos(int id, string nom, string prenom, DateTime dateNaissance, string nationalite, string passport)
        {
            if (id < 0)
                return (int)TravelerError.InvalidId;

            if (string.IsNullOrWhiteSpace(nom))
                return (int)TravelerError.InvalidName;

            if (string.IsNullOrWhiteSpace(prenom))
                return (int)TravelerError.InvalidFirstName;

            if (dateNaissance > DateTime.Now)
                return (int)TravelerError.InvalidBirthDate;

            if (string.IsNullOrWhiteSpace(nationalite))
                return (int)TravelerError.InvalidNationality;

            if (!IsPassportValid(passport))
                return (int)TravelerError.InvalidPassport;

            return (int)TravelerError.Success;
        }

        #endregion Méthodes de Validation

        #region Méthodes de Chargement & Sauvegarde

        public static List<Travelers> LoadAllTravelers()
        {
            try
            {
                if (!File.Exists(fileName))
                    return new List<Travelers>();

                var json = File.ReadAllText(fileName);
                var options = new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                };
                return JsonSerializer.Deserialize<List<Travelers>>(json, options) ?? new List<Travelers>();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erreur lors du chargement des voyageurs: {ex.Message}");
                return new List<Travelers>();
            }
        }

        public static int SaveTravel(Travelers traveler)
        {
            try
            {
                var travelers = LoadAllTravelers();

                if (travelers.Any(t => t.idTraveler == traveler.idTraveler))
                    return (int)TravelerError.TravelerAlreadyExists;

                if (travelers.Any(t => t.idTrip == traveler.idTrip &&
                                     t.PassportNumber == traveler.PassportNumber))
                    return (int)TravelerError.TravelerAlreadyExists;

                travelers.Add(traveler);

                var options = new JsonSerializerOptions
                {
                    WriteIndented = true,
                    PropertyNamingPolicy = JsonNamingPolicy.CamelCase
                };

                var json = JsonSerializer.Serialize(travelers, options);
                File.WriteAllText(fileName, json);

                return (int)TravelerError.Success;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erreur lors de la sauvegarde du voyageur: {ex.Message}");
                return (int)TravelerError.FileError;
            }
        }

        private static int GenerateNewId()
        {
            List<Travelers> travelers = LoadAllTravelers();
            return travelers.Count > 0 ? travelers.Max(t => t.idTraveler) + 1 : 1;
        }

        #endregion Méthodes de Chargement & Sauvegarde

        #region Méthodes supplémentaires

        public override string ToString()
        {
            return $"{firstName} {lastName} (ID: {idTraveler}), Passeport: {PassportNumber}, Destination: {destinination}";
        }

        public static Travelers GetTravelerById(int id)
        {
            var travelers = LoadAllTravelers();
            return travelers.FirstOrDefault(t => t.idTraveler == id);
        }

        public static int UpdateTraveler(Travelers updatedTraveler)
        {
            try
            {
                var travelers = LoadAllTravelers();
                var index = travelers.FindIndex(t => t.idTraveler == updatedTraveler.idTraveler);

                if (index == -1)
                    return (int)TravelerError.TravelerNotFound;

                travelers[index] = updatedTraveler;

                var options = new JsonSerializerOptions
                {
                    WriteIndented = true,
                    PropertyNamingPolicy = JsonNamingPolicy.CamelCase
                };

                var json = JsonSerializer.Serialize(travelers, options);
                File.WriteAllText(fileName, json);

                return (int)TravelerError.Success;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erreur lors de la mise à jour du voyageur: {ex.Message}");
                return (int)TravelerError.FileError;
            }
        }

        public static int DeleteTraveler(int id)
        {
            try
            {
                var travelers = LoadAllTravelers();
                var travelerToRemove = travelers.FirstOrDefault(t => t.idTraveler == id);

                if (travelerToRemove == null)
                    return (int)TravelerError.TravelerNotFound;

                travelers.Remove(travelerToRemove);

                var options = new JsonSerializerOptions
                {
                    WriteIndented = true,
                    PropertyNamingPolicy = JsonNamingPolicy.CamelCase
                };

                var json = JsonSerializer.Serialize(travelers, options);
                File.WriteAllText(fileName, json);

                return (int)TravelerError.Success;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erreur lors de la suppression du voyageur: {ex.Message}");
                return (int)TravelerError.FileError;
            }
        }

        #endregion Méthodes supplémentaires
    }
}