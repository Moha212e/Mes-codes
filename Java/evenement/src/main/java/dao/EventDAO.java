package dao;

import entity.Event;
import java.util.List;

public class EventDAO implements DataAccessObject<Event> {

    @Override
    public void create(Event event) {
        // Code pour créer un événement dans la base de données
    }

    @Override
    public Event read(int id) {
        // Code pour lire un événement à partir de la base de données
        return null; // Remplacer par l'événement récupéré
    }

    @Override
    public void update(Event event) {
        // Code pour mettre à jour un événement dans la base de données
    }

    @Override
    public void delete(int id) {
        // Code pour supprimer un événement de la base de données
    }

    @Override
    public List<Event> findAll() {
        // Code pour récupérer tous les événements de la base de données
        return null; // Remplacer par la liste des événements récupérés
    }
}