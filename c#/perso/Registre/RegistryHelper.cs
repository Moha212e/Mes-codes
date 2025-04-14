using Microsoft.Win32;
using System;
using System.Diagnostics;

namespace voyagoo
{
    public static class RegistryHelper
    {
        private const string RegistryKeyPath = @"Software\Voyagoo";
        private const string LastSearchPlace = "DernierLieu";
        private const string LastSearchDate = "DerniereDate";
        private const string LastSearchPrice = "DernierPrix";

        public static void SaveLastSearch(string lieu, string date, string prix)
        {
            try
            {
                using (RegistryKey key = Registry.CurrentUser.CreateSubKey(RegistryKeyPath))
                {
                    key.SetValue(LastSearchPlace, lieu);
                    key.SetValue(LastSearchDate, date);
                    key.SetValue(LastSearchPrice, prix);
                    Debug.WriteLine("Recherche enregistrée dans le registre");
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"Erreur registre (sauvegarde): {ex.Message}");
            }
        }

        public static (string lieu, string date, string prix) LoadLastSearch()
        {
            try
            {
                using (RegistryKey key = Registry.CurrentUser.OpenSubKey(RegistryKeyPath))
                {
                    if (key != null)
                    {
                        return (
                            key.GetValue(LastSearchPlace)?.ToString() ?? "",
                            key.GetValue(LastSearchDate)?.ToString() ?? "",
                            key.GetValue(LastSearchPrice)?.ToString() ?? ""
                        );
                    }
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"Erreur registre (chargement): {ex.Message}");
            }

            return ("", "", "");
        }
    }
}