using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Voyago
{
    public class User
    {
        public required string Email { get; set; }
        public required string Password { get; set; }
        public string LastName { get; set; }
        public string FirstName { get; set; }
        public bool IsAdmin { get; set; }
    }
}
