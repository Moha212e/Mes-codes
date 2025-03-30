#exercice1
santer <- read.csv("C:/bonne_sante.csv", header = TRUE)
summary(santer)
str(santer)

#exercice echantillonage 

#Isoler la colonne numvisit (nombre de visites chez le médecin).
numvisit <- santer$numvisit
rm(numvisit)

#Construire un échantillon de 50 éléments à partir 
#des données complètes en utilisant la fonction R sample().

sample_numvisit <- sample(numvisit, size = 50)
summary(sample_numvisit)
rm(sample_numvisit)

#exercice statistique descriptive

#calcule avec sd
EcartTypeAuto <- sd(sample_numvisit)
EcartTypeAuto

#Calcule manuelle 

# Calcule de l'effectif (effectif)
TotEchantillon <- length(sample_numvisit) 
TotEchantillon


# Calcule de la moyenne 
str(sample_numvisit)
MoyenneEchantillons <- sum(sample_numvisit)/TotEchantillon
MoyenneEchantillons 


# Calcule de la variance 
VarianceEchantillons <- sum((sample_numvisit - MoyenneEchantillons )^2) / TotEchantillon
VarianceEchantillons


# Calcule de l écart-type
EcartTypeManu <- sqrt(VarianceEchantillons)
EcartTypeManu

#Calcule de la médiane
MedianeEchantillons =  median(sample_numvisit)
MedianeEchantillons


#mettre les données dans un tableau
tableau <- c(effectif = TotEchantillon, Moyenne = MoyenneEchantillons, EcartType = EcartTypeManu, Mediane = MedianeEchantillons)

tableau

#exercice loi normale 

GrosseBouffeµ <- 340 #Moyenne
GrosseBouffeσ <- 6   # Ecart Type

#a) Quelle est la probabilité 
#qu'une boîte choisie au hasard ait un poids inférieur à 344 g?

ValeurRechercher <- 344
probabilité1 <- pnorm(ValeurRechercher,GrosseBouffeµ,GrosseBouffeσ)
probabilité1

#b) Quelle est la probabilité qu'une boîte 
#choisie au hasard ait un poids inférieur à 339 g?  

ValeurRechercher <- 339
probabilité2 <- pnorm(ValeurRechercher,GrosseBouffeµ,GrosseBouffeσ)
probabilité2

#c) Quelle est la probabilité qu'une boîte choisie 
#au hasard ait un poids supérieur à 343 g? 

ValeurRechercher <- 343
probabilité3 <- 1 - pnorm(ValeurRechercher,GrosseBouffeµ,GrosseBouffeσ)
probabilité3

#d) Quelle est la probabilité qu'une boîte choisie 
#au hasard ait un poids entre 334 et 346 g ? 

ValeurRechercherA <- 334
ValeurRechercherB <- 346

probabilitéAB <- pnorm(ValeurRechercherB,GrosseBouffeµ,GrosseBouffeσ)-pnorm(ValeurRechercherA,GrosseBouffeµ,GrosseBouffeσ)
probabilitéAB

#e) Quelle est la probabilité qu'une boîte choisie au hasard 
#ait un poids qui diffère de la moyenne par moins de 2 grammes ? 

ValeurRechercherA <- GrosseBouffeµ - 2
ValeurRechercherB <- GrosseBouffeµ + 2

# Mettre la plus grande valeur en 1
probabilite_e <- pnorm(ValeurRechercherB,GrosseBouffeµ,GrosseBouffeσ) - pnorm(ValeurRechercherA,GrosseBouffeµ,GrosseBouffeσ) 
probabilite_e

#f) Quelle est la limite supérieure du poids de 75% des boîtes ? 
quartile2 <- qnorm(0.75, mean = GrosseBouffeµ,sd = GrosseBouffeσ)
quartile2
#g) Quelle est la limite inférieure du poids de 80% des boîtes ? 

quartile1 <- qnorm(1-0.80, mean = GrosseBouffeµ,sd = GrosseBouffeσ)
quartile1
#h) Sur une production de 1000 boîtes, combien auront un poids inférieur à 330 g ? 
ValeurRecherche_h <- 330
NombreBoiteProduite <- 1000

PoucentageInf <- pnorm(ValeurRecherche_h,GrosseBouffeµ,GrosseBouffeσ)
PoucentageInf

NombreBoiteInf <- NombreBoiteProduite * PoucentageInf 
NombreBoiteInf

#i) A quelle valeur doit être fixée le niveau de remplissage 
#pour assurer que seulement une boîte sur 100 aura un poids inférieur à 340 g ? 
# Définition des paramètres
GrosseBouffeµ <- 340    # Moyenne du poids
GrosseBouffeσ <- 6      # Écart-type

# Calcul du poids correspondant à 1 % des boîtes ayant un poids inférieur à ce niveau
valeur_remplissage <- qnorm(0.01, mean = GrosseBouffeµ, sd = GrosseBouffeσ)

# Affichage du résultat
valeur_remplissage
