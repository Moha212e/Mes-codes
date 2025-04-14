const express = require('express');
const path = require('path');
const app = express();

// Définissez le port
const PORT = 3000;

// Dossier pour servir les fichiers statiques
app.use(express.static(path.join(__dirname, 'public')));


// Lancer le serveur
app.listen(PORT, () => {
    console.log(`Serveur en marche sur http://localhost:${PORT}`);
});