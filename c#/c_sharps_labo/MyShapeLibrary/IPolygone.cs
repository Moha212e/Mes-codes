using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyShapeLibrary
{
    public interface IPolygone
    {
        // Propri�t� en lecture seule pour obtenir le nombre de sommets
        int NbSommets { get; }
    }
}
