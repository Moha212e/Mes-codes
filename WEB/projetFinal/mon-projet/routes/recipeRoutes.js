const express = require('express');
const router = express.Router();

// Importer les fonctions de ton contrôleur (par exemple, recipeController)
const recipeController = require('../controllers/recipeController');

// Définir une route POST pour ajouter une recette
router.post('/add', recipeController.addRecipe);

// Définir d'autres routes, comme GET, PUT, DELETE, etc.
router.get('/', recipeController.getAllRecipes);

module.exports = router;
