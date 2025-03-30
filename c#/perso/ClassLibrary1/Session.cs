using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MyShapeClass;

namespace MyShapeClass
{
    public class Session
    {
        // Déclaration de l'événement
        public static event Action<User> CurrentUserChanged;

        private static User _currentUser;
    }
}