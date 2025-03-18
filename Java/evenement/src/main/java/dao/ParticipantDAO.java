package dao;

import entity.Participant;
import java.util.List;

public class ParticipantDAO implements DataAccessObject<Participant> {

    @Override
    public void create(Participant participant) {
        // Code pour ajouter un participant à la base de données
    }

    @Override
    public Participant read(int id) {
        // Code pour lire un participant à partir de la base de données
        return null;
    }

    @Override
    public void update(Participant participant) {
        // Code pour mettre à jour un participant dans la base de données
    }

    @Override
    public void delete(int id) {
        // Code pour supprimer un participant de la base de données
    }

    @Override
    public List<Participant> findAll() {
        // Code pour trouver tous les participants dans la base de données
        return null;
    }
}