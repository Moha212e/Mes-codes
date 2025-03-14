namespace Voyago;

public static class SessionManager
{
    public static int Id_Current_User { get; set; }
    public static string CurrentUserEmail { get; set; }
    public static bool IsUserAdmin { get; set; }

    public static void ClearSession()
    {
        CurrentUserEmail = null;
        IsUserAdmin = false;
    }
}