// Fonction pour afficher les recettes
async function fetchRecipes(query = '') {
    const response = await fetch(`/api/recipes?query=${query}`);
    const recipes = await response.json();
    const recipeContainer = document.getElementById('recipe-container');
    recipeContainer.innerHTML = '';  // Vider le conteneur avant d'ajouter de nouvelles recettes
  
    recipes.forEach(recipe => {
      const recipeCard = document.createElement('div');
      recipeCard.classList.add('recipe-card');
  
      const recipeImage = document.createElement('img');
      recipeImage.src = recipe.image;
      recipeImage.classList.add('recipe-img');
  
      const recipeTitle = document.createElement('h3');
      recipeTitle.classList.add('recipe-title');
      recipeTitle.textContent = recipe.title;
  
      const recipeInfo = document.createElement('div');
      recipeInfo.classList.add('recipe-info');
      recipeInfo.textContent = `Temps de préparation: ${recipe.readyInMinutes} minutes`;
  
      recipeCard.appendChild(recipeImage);
      recipeCard.appendChild(recipeTitle);
      recipeCard.appendChild(recipeInfo);
  
      recipeContainer.appendChild(recipeCard);
    });
  }
  
  // Charger les recettes lorsque la page se charge
  document.addEventListener('DOMContentLoaded', () => {
    fetchRecipes();  // Par défaut, charger toutes les recettes
  });
  
  // Fonction de recherche
  const searchForm = document.getElementById('search-form');
  searchForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const query = document.getElementById('search-input').value;
    fetchRecipes(query);
  });
  