document.addEventListener('DOMContentLoaded', async () => {
    const recipeList = document.querySelector('.recipe-list');
    const loadingElement = document.querySelector('.loading');
    const errorElement = document.querySelector('.error');
    const modal = document.querySelector('.modal');
    const modalContent = document.querySelector('.modal-content');

    const API_KEY = 'e431fc9020b04ef895a8713003ba2a2a';
    const API_URL = `https://api.spoonacular.com/recipes/random?number=10&apiKey=${API_KEY}`;

    async function loadRecipes() {
        loadingElement.style.display = 'block';
        errorElement.style.display = 'none';

        try {
            const response = await fetch(API_URL);
            const data = await response.json();
            loadingElement.style.display = 'none';

            if (data.recipes && data.recipes.length > 0) {
                recipeList.innerHTML = data.recipes.map(recipe => `
                    <div class="recipe-card">
                        <img src="${recipe.image}" alt="${recipe.title}">
                        <div class="recipe-info">
                            <h3>${recipe.title}</h3>
                            <p>${recipe.summary.slice(0, 100)}...</p>
                            <div class="recipe-actions">
                                <button class="favorite-btn">❤</button>
                                <button class="details-btn" data-id="${recipe.id}">Détails</button>
                            </div>
                        </div>
                    </div>
                `).join('');
            } else {
                recipeList.innerHTML = '<p>Aucune recette trouvée.</p>';
            }
        } catch (error) {
            loadingElement.style.display = 'none';
            errorElement.style.display = 'block';
            errorElement.textContent = "Erreur lors du chargement des recettes. Veuillez réessayer plus tard.";
        }
    }

    // Afficher le modal des détails
    recipeList.addEventListener('click', async (event) => {
        if (event.target.classList.contains('details-btn')) {
            const recipeId = event.target.getAttribute('data-id');
            await showRecipeDetails(recipeId);
        }
    });

    async function showRecipeDetails(recipeId) {
        try {
            const response = await fetch(`https://api.spoonacular.com/recipes/${recipeId}/information?apiKey=${API_KEY}`);
            const recipe = await response.json();
            modalContent.innerHTML = `
                <h2>${recipe.title}</h2>
                <img src="${recipe.image}" alt="${recipe.title}">
                <p>${recipe.summary}</p>
                <button class="close-modal">Fermer</button>
            `;
            modal.style.display = 'flex';
        } catch (error) {
            alert("Impossible d'afficher les détails de la recette.");
        }
    }

    modal.addEventListener('click', (event) => {
        if (event.target.classList.contains('close-modal') || event.target === modal) {
            modal.style.display = 'none';
        }
    });

    loadRecipes();
});
