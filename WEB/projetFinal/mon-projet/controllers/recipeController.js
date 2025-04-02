 
const axios = require('axios');
const apiConfig = require('../config/apiConfig');
const addRecipe = (req, res) => {
    // Logique pour ajouter une recette
    res.send('Recette ajoutée avec succès');
  };
  
  module.exports = {
    addRecipe,
  };
  
// Récupérer toutes les recettes
exports.getAllRecipes = async (req, res) => {
  try {
    const response = await axios.get(`${apiConfig.apiBaseUrl}/recipes/complexSearch`, {
      params: {
        apiKey: apiConfig.apiKey,
        query: req.query.query || '',  // Recherche par ingrédient ou nom de recette
        number: 10,  // Limiter à 10 recettes
      }
    });

    res.json(response.data.results);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Erreur lors de la récupération des recettes' });
  }
};

// Récupérer une recette par son ID
exports.getRecipeById = async (req, res) => {
  const recipeId = req.params.id;
  try {
    const response = await axios.get(`${apiConfig.apiBaseUrl}/recipes/${recipeId}/information`, {
      params: {
        apiKey: apiConfig.apiKey,
      }
    });

    res.json(response.data);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Erreur lors de la récupération de la recette' });
  }
};
