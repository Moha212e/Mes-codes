package org.example.model.persistence;

import com.google.gson.*;
import org.example.model.entity.*;

import java.lang.reflect.Type;

/**
 * Adaptateur pour gérer la sérialisation et désérialisation des différents types de réservations
 * Permet de conserver le type concret lors de la sérialisation/désérialisation
 */
public class ReservationTypeAdapter implements JsonSerializer<Reservation>, JsonDeserializer<Reservation> {

    private static final String TYPE_FIELD = "type";
    private static final String STANDARD_TYPE = "standard";
    private static final String PREMIUM_TYPE = "premium";
    private static final String CONTRAT_TYPE = "contrat";

    /**
     * Sérialise une réservation en JSON en ajoutant un champ pour le type
     */
    @Override
    public JsonElement serialize(Reservation src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        
        // Déterminer le type de réservation
        if (src instanceof Contrat) {
            result.addProperty(TYPE_FIELD, CONTRAT_TYPE);
        } else if (src instanceof ReservationPremium) {
            result.addProperty(TYPE_FIELD, PREMIUM_TYPE);
        } else if (src instanceof ReservationStandard) {
            result.addProperty(TYPE_FIELD, STANDARD_TYPE);
        } else {
            result.addProperty(TYPE_FIELD, STANDARD_TYPE); // Type par défaut
        }
        
        // Sérialiser les propriétés de l'objet
        JsonObject srcJson = (JsonObject) new Gson().toJsonTree(src);
        for (String key : srcJson.keySet()) {
            result.add(key, srcJson.get(key));
        }
        
        return result;
    }

    /**
     * Désérialise un JSON en réservation en fonction du type spécifié
     */
    @Override
    public Reservation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        
        // Récupérer le type de réservation
        String type = STANDARD_TYPE; // Type par défaut
        if (jsonObject.has(TYPE_FIELD)) {
            type = jsonObject.get(TYPE_FIELD).getAsString();
        }
        
        // Créer l'objet approprié en fonction du type
        switch (type) {
            case CONTRAT_TYPE:
                return context.deserialize(jsonObject, Contrat.class);
            case PREMIUM_TYPE:
                return context.deserialize(jsonObject, ReservationPremium.class);
            case STANDARD_TYPE:
            default:
                return context.deserialize(jsonObject, ReservationStandard.class);
        }
    }
}
