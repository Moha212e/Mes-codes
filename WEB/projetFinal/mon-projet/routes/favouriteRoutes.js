const express = require('express');
const router = express.Router();
const favoriteController = require('../controllers/favoriteController');

// Route pour obtenir les favoris d'un utilisateur
router.get('/', favoriteController.getUserFavorites);

// Route pour ajouter une recette aux favoris
router.post('/:recipeId', favoriteController.addFavorite);

// Route pour supprimer une recette des favoris
router.delete('/:recipeId', favoriteController.removeFavorite);

module.exports = router;
