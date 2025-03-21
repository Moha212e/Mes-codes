const express = require('express');
const morgan = require('morgan'); // Middleware tiers pour logger
const loggerMiddleware = require('./loggerMiddleware');
const app = express();

// Utilisation du middleware morgan
app.use(morgan('dev'));

// Notre middleware perso, si on souhaite le conserver
app.use(loggerMiddleware);

app.use(express.json());

// Routes
app.get('/', (req, res) => {
  res.send('Bienvenue sur la page d\'accueil !');
});

app.post('/donnees', (req, res) => {
  const data = req.body;
  console.log('Données reçues :', data);
  res.json({
    message: 'Données bien reçues !',
    contenu: data
  });
});

const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Serveur démarré sur http://localhost:${PORT}`);
});