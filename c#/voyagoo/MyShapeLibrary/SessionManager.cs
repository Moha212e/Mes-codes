namespace Voyago
{
    public static class SessionManager
    {
        public static string CurrentUserEmail { get; set; }
        public static bool IsUserAdmin { get; set; }

        public static void ClearSession()
        {
            CurrentUserEmail = null;
            IsUserAdmin = false;
        }
    }
}