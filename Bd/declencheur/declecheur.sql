/* Ajouter, dans la table Patients, les contraintes applicatives suivantes : 
• Le sexe doit toujours être connu et doit être égal à 'F' ou 'M' 
• L'état civil doit toujours être connu et doit être égal à 'C', 'M', 'D' ou 'V' 
• Le groupe sanguin doit être égal à 'A', 'B', 'O' ou 'AB', la valeur par défaut est 'A' 
• Les deux derniers caractères du compte bancaire sont le reste de la division des dix 
premiers caractères par 97 
Construire un jeu de commandes permettant de tester ces contraintes. 
Afficher toutes les contraintes spécifiées sur la table Patients.
*/
ALTER TABLE patients ADD CONSTRAINT choixSexe CHECK (sexe IN ('F', 'M'));
ALTER TABLE patients ADD CONSTRAINT choixCivil CHECK (etatcivil IN ('C', 'M', 'D', 'V'));
ALTER TABLE patients MODIFY grpsanguin DEFAULT 'A'; -- Ajout de la valeur par défaut
ALTER TABLE patients ADD CONSTRAINT choixSang CHECK (grpsanguin IN ('A', 'B', 'O', 'AB'));
ALTER TABLE patients ADD CONSTRAINT choixIban CHECK (
    LENGTH(cptbancaire) = 12 AND 
    TO_NUMBER(SUBSTR(cptbancaire, 1, 10)) MOD 97 = TO_NUMBER(SUBSTR(cptbancaire, -2))
);
