package org.example;

import org.example.controller.Controller;
import org.example.model.dao.DAOLocation;
import org.example.model.DataAccessLayer;
import org.example.view.ViewLocation;
import org.example.view.GUI.JFramesLocation;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Créer le modèle (DAOLocation implémente DataAccessLayer)
        DataAccessLayer model = new DAOLocation();
        
        // Créer la vue
        ViewLocation view = new JFramesLocation();
        
        // Créer le contrôleur et connecter le modèle et la vue
        Controller controller = new Controller(model, view);
        
        // Démarrer l'application
        controller.run();
    }
}