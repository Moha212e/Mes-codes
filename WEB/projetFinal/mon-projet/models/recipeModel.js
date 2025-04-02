const mongoose = require('mongoose');

// Schéma pour les informations nutritionnelles d'une recette
const nutritionSchema = new mongoose.Schema({
  calories: { type: Number, required: true },
  fat: { type: Number, required: true },
  carbs: { type: Number, required: true },
  protein: { type: Number, required: true },
});

// Schéma pour une recette
const recipeSchema = new mongoose.Schema({
  title: { type: String, required: true },
  image: { type: String, required: true },
  ingredients: [{ type: String }],
  readyInMinutes: { type: Number, required: true },
  nutrition: { type: nutritionSchema },
  instructions: { type: String },
  sourceUrl: { type: String },
});

// Création du modèle
const Recipe = mongoose.model('Recipe', recipeSchema);

module.exports = Recipe;
