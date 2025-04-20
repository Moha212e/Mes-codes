package org.example.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la classe Contrat")
class ContratTest {
    private Contrat contrat;
    private Client client;
    private Car voiture;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private static final String ID_CONTRAT = "C-1";
    private static final float PRIX_VOITURE_PAR_JOUR = 50.0f;

    @BeforeEach
    void setUp() {
        // Initialisation du client
        client = new Client();
        client.setIdClient(1);
        client.setName("Dupont");
        client.setEmail("dupont@email.com");

        // Initialisation de la voiture
        voiture = new Car();
        voiture.setIdCar(1);
        voiture.setBrand("Toyota");
        voiture.setModel("Corolla");
        voiture.setPriceday(PRIX_VOITURE_PAR_JOUR);

        // Dates de location
        dateDebut = LocalDate.now();
        dateFin = dateDebut.plusDays(3);

        // Création du contrat
        contrat = new Contrat(ID_CONTRAT, client, voiture, dateDebut, dateFin);
    }

    @Test
    @DisplayName("Le constructeur doit initialiser correctement tous les champs")
    void testConstructeur() {
        assertAll("Vérification de l'initialisation du contrat",
            () -> assertEquals(ID_CONTRAT, contrat.getIdContrat()),
            () -> assertEquals(client, contrat.getClient()),
            () -> assertEquals(voiture, contrat.getVoiture()),
            () -> assertEquals(dateDebut, contrat.getDateDebut()),
            () -> assertEquals(dateFin, contrat.getDateFin()),
            () -> assertEquals(Contrat.StatutContrat.EN_ATTENTE, contrat.getStatut()),
            () -> assertFalse(contrat.isEstSigne()),
            () -> assertTrue(contrat.getOptions().isEmpty())
        );
    }

    @Test
    @DisplayName("Le calcul du montant total doit être correct")
    void testCalculMontantTotal() {
        // Le montant total devrait être : prix par jour * nombre de jours
        double montantAttendu = PRIX_VOITURE_PAR_JOUR * 3; // 3 jours de location
        assertEquals(montantAttendu, contrat.getMontantTotal(), 0.01);

        // Ajout d'une option (5€ par option)
        contrat.ajouterOption("GPS");
        assertEquals(montantAttendu + 5.0, contrat.getMontantTotal(), 0.01);
    }

    @Test
    @DisplayName("La signature du contrat doit mettre à jour le statut")
    void testSignature() {
        contrat.signer();
        assertAll("Vérification après signature",
            () -> assertTrue(contrat.isEstSigne()),
            () -> assertEquals(Contrat.StatutContrat.EN_COURS, contrat.getStatut())
        );
    }

    @Test
    @DisplayName("La terminaison du contrat doit mettre à jour le statut")
    void testTerminaison() {
        contrat.terminer();
        assertEquals(Contrat.StatutContrat.TERMINE, contrat.getStatut());
    }

    @Test
    @DisplayName("L'annulation du contrat doit mettre à jour le statut")
    void testAnnulation() {
        contrat.annuler();
        assertEquals(Contrat.StatutContrat.ANNULE, contrat.getStatut());
    }

    @Test
    @DisplayName("La gestion des options doit fonctionner correctement")
    void testGestionOptions() {
        // Ajout d'options
        contrat.ajouterOption("GPS");
        contrat.ajouterOption("Siège bébé");
        
        List<String> optionsAttendues = Arrays.asList("GPS", "Siège bébé");
        assertEquals(optionsAttendues, contrat.getOptions());

        // Vérification du montant (prix location + 2 options à 5€)
        double montantAttendu = (PRIX_VOITURE_PAR_JOUR * 3) + (2 * 5.0);
        assertEquals(montantAttendu, contrat.getMontantTotal(), 0.01);

        // Retrait d'une option
        contrat.retirerOption("GPS");
        assertEquals(Arrays.asList("Siège bébé"), contrat.getOptions());
        
        // Vérification du nouveau montant
        montantAttendu = (PRIX_VOITURE_PAR_JOUR * 3) + (1 * 5.0);
        assertEquals(montantAttendu, contrat.getMontantTotal(), 0.01);
    }

    @Test
    @DisplayName("Les setters doivent mettre à jour les valeurs correctement")
    void testSetters() {
        LocalDate nouvelleDate = LocalDate.now().plusDays(1);
        contrat.setDateDebut(nouvelleDate);
        assertEquals(nouvelleDate, contrat.getDateDebut());

        Car nouvelleVoiture = new Car();
        nouvelleVoiture.setIdCar(2);
        nouvelleVoiture.setPriceday(75.0f);
        contrat.setVoiture(nouvelleVoiture);
        assertEquals(nouvelleVoiture, contrat.getVoiture());

        contrat.setCaution(1000.0);
        assertEquals(1000.0, contrat.getCaution(), 0.01);

        contrat.setTypeAssurance("All Risk");
        assertEquals("All Risk", contrat.getTypeAssurance());
    }

    @Test
    @DisplayName("toString doit retourner une chaîne contenant toutes les informations importantes")
    void testToString() {
        String representation = contrat.toString();
        assertAll("Vérification du contenu de toString",
            () -> assertTrue(representation.contains(ID_CONTRAT)),
            () -> assertTrue(representation.contains(dateDebut.toString())),
            () -> assertTrue(representation.contains(dateFin.toString())),
            () -> assertTrue(representation.contains(String.valueOf(contrat.getMontantTotal()))),
            () -> assertTrue(representation.contains(Contrat.StatutContrat.EN_ATTENTE.getLibelle()))
        );
    }

    @Test
    @DisplayName("Les libellés des statuts doivent être corrects")
    void testStatutLibelles() {
        assertAll("Vérification des libellés de statut",
            () -> assertEquals("En attente", Contrat.StatutContrat.EN_ATTENTE.getLibelle()),
            () -> assertEquals("En cours", Contrat.StatutContrat.EN_COURS.getLibelle()),
            () -> assertEquals("Terminé", Contrat.StatutContrat.TERMINE.getLibelle()),
            () -> assertEquals("Annulé", Contrat.StatutContrat.ANNULE.getLibelle())
        );
    }
    
    @Test
    @DisplayName("La méthode getId() doit retourner l'identifiant du contrat")
    void testGetId() {
        assertEquals(ID_CONTRAT, contrat.getId());
    }
    
    @Test
    @DisplayName("Les méthodes héritées de AbstractEntity doivent fonctionner correctement")
    void testAbstractEntityMethods() {
        assertFalse(contrat.isDeleted());
        contrat.setDeleted(true);
        assertTrue(contrat.isDeleted());
    }
}
