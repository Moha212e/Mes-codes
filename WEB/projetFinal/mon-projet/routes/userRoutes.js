const express = require('express');
const router = express.Router();
const userController = require('../controllers/userController');

// Route pour inscrire un nouvel utilisateur
router.post('/register', userController.registerUser);

// Route pour connecter un utilisateur
router.post('/login', userController.loginUser);

// Route pour obtenir les informations de l'utilisateur connecté
router.get('/profile', userController.getUserProfile);

module.exports = router;
