package org.example.model.entity;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Contrat
 */
public class ContratTest {

    @Test
    public void testContratDefaultConstructor() {
        // Test du constructeur par défaut
        Contrat contrat = new Contrat();
        assertNotNull(contrat);
        assertNotNull(contrat.getOptions());
        assertEquals(StatutContrat.EN_ATTENTE, contrat.getStatutContrat());
        assertEquals(0.0, contrat.getPrixAssurance(), 0.001);
        assertEquals(0.0, contrat.getPrixTotal(), 0.001);
    }
    
    @Test
    public void testContratConstructorWithParams() {
        // Test du constructeur avec paramètres
        String idContrat = "C1";
        double caution = 500.0;
        String typeAssurance = "Tous risques";
        boolean estSigne = true;
        StatutContrat statutContrat = StatutContrat.SIGNE;
        
        Contrat contrat = new Contrat(idContrat, caution, typeAssurance, estSigne, statutContrat);
        
        assertEquals(idContrat, contrat.getIdContrat());
        assertEquals(caution, contrat.getCaution(), 0.001);
        assertEquals(typeAssurance, contrat.getTypeAssurance());
        assertEquals(estSigne, contrat.isEstSigne());
        assertEquals(statutContrat, contrat.getStatutContrat());
        assertTrue(contrat.getPrixAssurance() > 0); // Le prix d'assurance devrait être calculé
    }
    
    @Test
    public void testContratConstructorWithoutId() {
        // Test du constructeur sans ID
        double caution = 600.0;
        String typeAssurance = "Tiers";
        boolean estSigne = false;
        StatutContrat statutContrat = StatutContrat.EN_ATTENTE;
        
        Contrat contrat = new Contrat(caution, typeAssurance, estSigne, statutContrat);
        
        assertNull(contrat.getIdContrat()); // L'ID n'est pas défini
        assertEquals(caution, contrat.getCaution(), 0.001);
        assertEquals(typeAssurance, contrat.getTypeAssurance());
        assertEquals(estSigne, contrat.isEstSigne());
        assertEquals(statutContrat, contrat.getStatutContrat());
        assertTrue(contrat.getPrixAssurance() > 0); // Le prix d'assurance devrait être calculé
    }
    
    @Test
    public void testGettersAndSetters() {
        Contrat contrat = new Contrat();
        
        // Test des setters
        String idContrat = "C2";
        double caution = 700.0;
        String typeAssurance = "Tous risques";
        boolean estSigne = true;
        StatutContrat statutContrat = StatutContrat.SIGNE;
        double prixAssurance = 150.0;
        double prixTotal = 450.0;
        
        contrat.setIdContrat(idContrat);
        contrat.setCaution(caution);
        contrat.setTypeAssurance(typeAssurance);
        contrat.setEstSigne(estSigne);
        contrat.setStatutContrat(statutContrat);
        contrat.setPrixAssurance(prixAssurance);
        contrat.setPrixTotal(prixTotal);
        
        // Test des getters
        assertEquals(idContrat, contrat.getIdContrat());
        assertEquals(caution, contrat.getCaution(), 0.001);
        assertEquals(typeAssurance, contrat.getTypeAssurance());
        assertEquals(estSigne, contrat.isEstSigne());
        assertEquals(statutContrat, contrat.getStatutContrat());
        assertEquals(prixAssurance, contrat.getPrixAssurance(), 0.001);
        assertEquals(prixTotal, contrat.getPrixTotal(), 0.001);
    }
    
    @Test
    public void testOptionsManagement() {
        Contrat contrat = new Contrat();
        
        // Test d'ajout d'options
        contrat.ajouterOption("GPS");
        contrat.ajouterOption("Siège bébé");
        contrat.ajouterOption("Chaînes neige");
        
        List<String> options = contrat.getOptions();
        assertEquals(3, options.size());
        assertTrue(options.contains("GPS"));
        assertTrue(options.contains("Siège bébé"));
        assertTrue(options.contains("Chaînes neige"));
        
        // Test de suppression d'option
        boolean removed = contrat.supprimerOption("Siège bébé");
        assertTrue(removed);
        
        options = contrat.getOptions();
        assertEquals(2, options.size());
        assertFalse(options.contains("Siège bébé"));
        
        // Test de suppression d'une option inexistante
        removed = contrat.supprimerOption("Option inexistante");
        assertFalse(removed);
        
        // Test de définition complète des options
        List<String> newOptions = new ArrayList<>();
        newOptions.add("Assurance conducteur");
        newOptions.add("Assistance 24/7");
        
        contrat.setOptions(newOptions);
        
        options = contrat.getOptions();
        assertEquals(2, options.size());
        assertTrue(options.contains("Assurance conducteur"));
        assertTrue(options.contains("Assistance 24/7"));
    }
    
    @Test
    public void testSignatureChangesStatut() {
        Contrat contrat = new Contrat();
        contrat.setStatutContrat(StatutContrat.EN_ATTENTE);
        
        // Vérifier que la signature change le statut
        contrat.setEstSigne(true);
        assertTrue(contrat.isEstSigne());
        assertEquals(StatutContrat.SIGNE, contrat.getStatutContrat());
        
        // Vérifier que la désignature ne change pas le statut automatiquement
        contrat.setEstSigne(false);
        assertFalse(contrat.isEstSigne());
        // Le statut reste SIGNE car il n'y a pas de logique pour revenir en arrière
        assertEquals(StatutContrat.SIGNE, contrat.getStatutContrat());
    }
    
    @Test
    public void testReservationAssociation() {
        Contrat contrat = new Contrat();
        Reservation reservation = new Reservation();
        reservation.setIdReservation(1);
        
        contrat.setReservation(reservation);
        
        assertEquals(reservation, contrat.getReservation());
        assertEquals(reservation.getIdReservation(), contrat.getReservationId());
        
        // Test avec null
        contrat.setReservation(null);
        assertNull(contrat.getReservation());
        assertEquals(0, contrat.getReservationId());
    }
    
    @Test
    public void testClientAndCarInfoStorage() {
        Contrat contrat = new Contrat();
        
        String clientId = "1";
        String clientName = "Dupont";
        String clientSurname = "Jean";
        String carId = "AB-123-CD";
        String carBrand = "Renault";
        String carModel = "Clio";
        
        contrat.setClientId(clientId);
        contrat.setClientName(clientName);
        contrat.setClientSurname(clientSurname);
        contrat.setCarId(carId);
        contrat.setCarBrand(carBrand);
        contrat.setCarModel(carModel);
        
        assertEquals(clientId, contrat.getClientId());
        assertEquals(clientName, contrat.getClientName());
        assertEquals(clientSurname, contrat.getClientSurname());
        assertEquals(carId, contrat.getCarId());
        assertEquals(carBrand, contrat.getCarBrand());
        assertEquals(carModel, contrat.getCarModel());
    }
    
    @Test
    public void testPrixAssuranceCalculation() {
        // Vérifier que le prix d'assurance est calculé correctement selon le type
        Contrat contratTousRisques = new Contrat();
        contratTousRisques.setTypeAssurance("Tous risques");
        
        Contrat contratTiers = new Contrat();
        contratTiers.setTypeAssurance("Tiers");
        
        Contrat contratIntermédiaire = new Contrat();
        contratIntermédiaire.setTypeAssurance("Intermédiaire");
        
        // Le prix d'assurance "Tous risques" devrait être plus élevé que "Tiers"
        assertTrue(contratTousRisques.getPrixAssurance() > contratTiers.getPrixAssurance());
        
        // Le prix d'assurance "Intermédiaire" devrait être entre les deux
        assertTrue(contratIntermédiaire.getPrixAssurance() > contratTiers.getPrixAssurance());
        assertTrue(contratIntermédiaire.getPrixAssurance() < contratTousRisques.getPrixAssurance());
    }
}
