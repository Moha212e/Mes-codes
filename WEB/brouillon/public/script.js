document.addEventListener('DOMContentLoaded', async () => {
    // Éléments du DOM
    const recipeGrid = document.querySelector('.recipe-grid');
    const loadingOverlay = document.querySelector('.loading-overlay');
    const errorMessage = document.querySelector('.error-message');
    const recipeModal = document.querySelector('.recipe-modal');
    const modalContent = document.querySelector('.modal-content');
    const searchForm = document.querySelector('.search-form');
    const retryButton = document.querySelector('.error-retry');
    const themeToggle = document.querySelector('.theme-toggle');
    const showFavoritesBtn = document.getElementById('show-favorites');
    const showAllBtn = document.getElementById('show-all');

    // Configuration API
    const API_KEY = 'e431fc9020b04ef895a8713003ba2a2a';
    const BASE_URL = 'https://api.spoonacular.com/recipes';
    const DEFAULT_RECIPES_COUNT = 100;

    // État de l'application
    let currentRecipes = [];
    let favorites = JSON.parse(localStorage.getItem('favorites')) || [];
    let currentView = 'all'; // 'all' ou 'favorites'

    // Chargement initial
    initApp();

    // Initialisation de l'application
    function initApp() {
        loadRecipes();
        setupEventListeners();
        applyThemePreference();
        updateFavoriteCount();
    }

    // Configuration des écouteurs d'événements
    function setupEventListeners() {
        // Modal
        recipeModal.addEventListener('click', (e) => {
            if (e.target.classList.contains('modal-close') || e.target === recipeModal) {
                recipeModal.close();
            }
        });

        // Recherche
        searchForm.addEventListener('submit', handleSearch);

        // Réessayer après erreur
        retryButton.addEventListener('click', () => {
            if (currentView === 'favorites') {
                displayFavorites();
            } else {
                loadRecipes();
            }
        });

        // Bouton favori (délégué au parent)
        recipeGrid.addEventListener('click', handleRecipeActions);

        // Thème
        themeToggle.addEventListener('click', toggleTheme);

        // Boutons de navigation
        showFavoritesBtn.addEventListener('click', (e) => {
            e.preventDefault();
            switchToFavoritesView();
        });

        showAllBtn.addEventListener('click', (e) => {
            e.preventDefault();
            switchToAllRecipesView();
        });
    }

    // Charger les recettes
    async function loadRecipes(query = '') {
        currentView = 'all';
        showLoading();
        hideError();
        updateActiveNav();

        try {
            const url = query
                ? `${BASE_URL}/complexSearch?query=${query}&number=${DEFAULT_RECIPES_COUNT}&apiKey=${API_KEY}`
                : `${BASE_URL}/random?number=${DEFAULT_RECIPES_COUNT}&apiKey=${API_KEY}`;

            const response = await fetch(url);

            if (!response.ok) {
                throw new Error(`Erreur HTTP: ${response.status}`);
            }

            const data = await response.json();
            currentRecipes = query ? data.results : data.recipes;

            if (currentRecipes && currentRecipes.length > 0) {
                renderRecipes(currentRecipes);
            } else {
                showEmptyState();
            }
        } catch (error) {
            console.error("Erreur lors du chargement des recettes:", error);
            showError("Erreur lors du chargement des recettes. Veuillez réessayer.");
        } finally {
            hideLoading();
        }
    }

    // Afficher les recettes favorites
    async function displayFavorites() {
        currentView = 'favorites';
        showLoading();
        hideError();
        updateActiveNav();

        if (favorites.length === 0) {
            showEmptyFavoritesState();
            hideLoading();
            return;
        }

        try {
            // Récupérer les détails de chaque recette favorite
            const favoriteRecipes = await Promise.all(
                favorites.map(id =>
                    fetch(`${BASE_URL}/${id}/information?apiKey=${API_KEY}`)
                        .then(res => {
                            if (!res.ok) throw new Error('Recette non trouvée');
                            return res.json();
                        })
                        .catch(e => {
                            console.error(`Erreur avec la recette ${id}:`, e);
                            return null;
                        })
                )
            );

            // Filtrer les recettes valides
            const validRecipes = favoriteRecipes.filter(recipe => recipe !== null);

            if (validRecipes.length > 0) {
                renderRecipes(validRecipes);
            } else {
                showEmptyFavoritesState();
            }
        } catch (error) {
            console.error("Erreur lors du chargement des favoris:", error);
            showError("Impossible de charger vos recettes favorites.");
        } finally {
            hideLoading();
        }
    }

    // Afficher les recettes
    function renderRecipes(recipes) {
        recipeGrid.innerHTML = recipes.map(recipe => `
            <article class="recipe-card" data-id="${recipe.id}">
                <div class="card-image-container">
                    <img src="${recipe.image || 'placeholder-food.jpg'}" 
                         alt="${recipe.title}" 
                         class="card-image"
                         loading="lazy">
                    <button class="favorite-btn ${isFavorite(recipe.id) ? 'active' : ''}" 
                            aria-label="${isFavorite(recipe.id) ? 'Retirer des favoris' : 'Ajouter aux favoris'}">
                        ♥
                    </button>
                </div>
                <div class="card-content">
                    <h3 class="card-title">${recipe.title}</h3>
                    <div class="card-meta">
                        <span class="meta-item">${Math.ceil(recipe.readyInMinutes || 30)} min</span>
                        <span class="meta-item">${recipe.servings || 2} pers.</span>
                    </div>
                    <button class="details-btn" data-id="${recipe.id}">
                        Voir la recette
                    </button>
                </div>
            </article>
        `).join('');
    }

    // Gérer les actions sur les recettes
    function handleRecipeActions(e) {
        const target = e.target;

        if (target.classList.contains('favorite-btn')) {
            const recipeCard = target.closest('.recipe-card');
            const recipeId = recipeCard.dataset.id;
            toggleFavorite(recipeId, target);
        }

        if (target.classList.contains('details-btn')) {
            const recipeId = target.dataset.id;
            showRecipeDetails(recipeId);
        }
    }

    // Basculer favori
    function toggleFavorite(recipeId, button) {
        const index = favorites.indexOf(recipeId);

        if (index === -1) {
            favorites.push(recipeId);
            button.classList.add('active');
            button.setAttribute('aria-label', 'Retirer des favoris');
            showToast('Recette ajoutée aux favoris');
        } else {
            favorites.splice(index, 1);
            button.classList.remove('active');
            button.setAttribute('aria-label', 'Ajouter aux favoris');
            showToast('Recette retirée des favoris');

            // Si on est en vue favoris, retirer la carte
            if (currentView === 'favorites') {
                const recipeCard = button.closest('.recipe-card');
                recipeCard.classList.add('fade-out');
                setTimeout(() => {
                    recipeCard.remove();
                    if (recipeGrid.children.length === 0) {
                        showEmptyFavoritesState();
                    }
                }, 300);
            }
        }

        localStorage.setItem('favorites', JSON.stringify(favorites));
        updateFavoriteCount();
    }

    // Vérifier si une recette est favorite
    function isFavorite(recipeId) {
        return favorites.includes(recipeId.toString());
    }

    // Afficher les détails d'une recette
    async function showRecipeDetails(recipeId) {
        showLoading();

        try {
            const response = await fetch(`${BASE_URL}/${recipeId}/information?apiKey=${API_KEY}`);

            if (!response.ok) {
                throw new Error(`Erreur HTTP: ${response.status}`);
            }

            const recipe = await response.json();

            modalContent.innerHTML = `
                <div class="modal-header">
                    <h2 class="modal-title">${recipe.title}</h2>
                    <button class="modal-close" aria-label="Fermer">×</button>
                </div>
                <div class="modal-body">
                    <img src="${recipe.image}" alt="${recipe.title}" class="modal-image">
                    <div class="recipe-details">
                        <div class="detail-item">
                            <span>⏱️</span>
                            <span>${recipe.readyInMinutes} minutes</span>
                        </div>
                        <div class="detail-item">
                            <span>👨‍👩‍👧‍👦</span>
                            <span>${recipe.servings} personnes</span>
                        </div>
                        <div class="detail-item">
                            <span>❤️</span>
                            <span>${isFavorite(recipe.id) ? 'Dans vos favoris' : 'Non favoris'}</span>
                        </div>
                    </div>
                    <div class="recipe-summary">
                        ${recipe.summary}
                    </div>
                    <h3>Instructions</h3>
                    <div class="recipe-instructions">
                        ${recipe.instructions || 'Aucune instruction disponible.'}
                    </div>
                    <div class="recipe-actions">
                        <button class="print-btn" onclick="window.print()">Imprimer</button>
                        <button class="favorite-btn ${isFavorite(recipe.id) ? 'active' : ''}" 
                                onclick="toggleFavoriteInModal(${recipe.id}, this)">
                            ${isFavorite(recipe.id) ? 'Retirer des favoris' : 'Ajouter aux favoris'}
                        </button>
                        <a href="${recipe.sourceUrl}" target="_blank" rel="noopener" class="source-btn">
                            Voir la recette originale
                        </a>
                    </div>
                </div>
            `;

            // Exposer la fonction pour le bouton favori dans le modal
            window.toggleFavoriteInModal = (id, btn) => {
                toggleFavorite(id, btn);
                btn.textContent = isFavorite(id) ? 'Retirer des favoris' : 'Ajouter aux favoris';
                modalContent.querySelector('.detail-item:nth-child(3) span:last-child').textContent =
                    isFavorite(id) ? 'Dans vos favoris' : 'Non favoris';
            };

            recipeModal.showModal();
        } catch (error) {
            console.error("Erreur lors du chargement des détails:", error);
            showError("Impossible d'afficher les détails de la recette.");
        } finally {
            hideLoading();
        }
    }

    // Gérer la recherche
    function handleSearch(e) {
        e.preventDefault();
        const searchInput = searchForm.querySelector('input[type="search"]');
        const query = searchInput.value.trim();

        if (query) {
            loadRecipes(query);
        } else {
            loadRecipes();
        }
    }

    // Gestion du thème
    function toggleTheme() {
        const currentTheme = document.documentElement.getAttribute('data-theme');
        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';

        document.documentElement.setAttribute('data-theme', newTheme);
        localStorage.setItem('theme', newTheme);

        const icon = themeToggle.querySelector('.toggle-icon');
        icon.textContent = newTheme === 'dark' ? '☀️' : '🌙';
    }

    function applyThemePreference() {
        const savedTheme = localStorage.getItem('theme') || 'light';
        document.documentElement.setAttribute('data-theme', savedTheme);

        const icon = themeToggle.querySelector('.toggle-icon');
        icon.textContent = savedTheme === 'dark' ? '☀️' : '🌙';
    }

    // Navigation entre vues
    function switchToFavoritesView() {
        displayFavorites();
    }

    function switchToAllRecipesView() {
        loadRecipes();
    }

    function updateActiveNav() {
        document.querySelectorAll('.nav-link').forEach(link => {
            link.classList.remove('active');
            link.removeAttribute('aria-current');
        });

        if (currentView === 'favorites') {
            showFavoritesBtn.classList.add('active');
            showFavoritesBtn.setAttribute('aria-current', 'page');
        } else {
            document.querySelector('.nav-link[href="/"]').classList.add('active');
            document.querySelector('.nav-link[href="/"]').setAttribute('aria-current', 'page');
        }
    }

    // Mettre à jour le compteur de favoris
    function updateFavoriteCount() {
        const countElements = document.querySelectorAll('.favorite-count');
        countElements.forEach(el => {
            el.textContent = favorites.length;
            el.style.display = favorites.length > 0 ? 'inline-block' : 'none';
        });
    }

    // Affichage des états
    function showLoading() {
        loadingOverlay.hidden = false;
        recipeGrid.style.opacity = '0.5';
    }

    function hideLoading() {
        loadingOverlay.hidden = true;
        recipeGrid.style.opacity = '1';
    }

    function showError(message) {
        errorMessage.hidden = false;
        errorMessage.querySelector('.error-text').textContent = message;
    }

    function hideError() {
        errorMessage.hidden = true;
    }

    function showEmptyState() {
        recipeGrid.innerHTML = `
            <div class="empty-state">
                <p>Aucune recette trouvée.</p>
                <button class="retry-btn">Réessayer</button>
            </div>
        `;
        document.querySelector('.retry-btn').addEventListener('click', loadRecipes);
    }

    function showEmptyFavoritesState() {
        recipeGrid.innerHTML = `
            <div class="empty-state">
                <p>Vous n'avez aucune recette en favoris pour le moment.</p>
                <button class="cta-button">Découvrir des recettes</button>
            </div>
        `;
        document.querySelector('.cta-button').addEventListener('click', switchToAllRecipesView);
    }

    function showToast(message) {
        const toast = document.createElement('div');
        toast.className = 'toast';
        toast.textContent = message;
        document.body.appendChild(toast);

        setTimeout(() => {
            toast.classList.add('show');
        }, 10);

        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => {
                document.body.removeChild(toast);
            }, 300);
        }, 3000);
    }
});