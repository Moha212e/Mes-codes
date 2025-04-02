const express = require('express');
const app = express();
const cors = require('cors');
const recipeRoutes = require('./routes/recipeRoutes');

// Middleware pour CORS
app.use(cors());

// Middleware pour parser les requêtes JSON
app.use(express.json());

// Utiliser les routes
app.use('/recipes', recipeRoutes);

// Démarrer le serveur
app.listen(3000, () => {
  console.log('Server is running on port 3000');
});
