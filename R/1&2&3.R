#############################################
# TP1 - MANIPULATION DES DONNÉES SOUS R
#############################################

### A. Récupération et mise en forme des données (ozone)

ozone <- read.table(file.choose())  # Chargement du fichier de données ozone
View(ozone)                         # Affiche le tableau comme une feuille Excel

nrow(ozone)        # Nombre d'observations (lignes)
ncol(ozone)        # Nombre de variables (colonnes)

rownames(ozone)    # Affiche les noms des lignes (souvent automatiques)
colnames(ozone)    # Affiche les noms des colonnes (noms des variables)

str(ozone)         # Structure : type de chaque variable (numérique, facteur...)

# Trois façons d'accéder à la 3e colonne (T12 ici)
colonne3_1 <- ozone[, 3]       # Par position
colonne3_2 <- ozone$T12        # Par nom via $
colonne3_3 <- ozone[["T12"]]   # Par nom via [[ ]]

rm(colonne3_1, colonne3_2, colonne3_3)  # Suppression des objets temporaires

### B. Manipulations des données

# Renommer la colonne "pluie" en "Pluies"
names(ozone)[names(ozone) == "pluie"] <- "Pluies"

# Filtrer les lignes où maxO3 >= 85
ozone_85 <- ozone[ozone$maxO3 >= 85, ]
View(ozone_85)

# Filtrer les lignes avec temps "sec"
ozone_sec <- ozone[tolower(ozone$Pluies) == "sec", ]
View(ozone_sec)

# Temps sec ET maxO3 ≥ 95
ozone_sec_95 <- ozone[tolower(ozone$Pluies) == "sec" & ozone$maxO3 >= 95, ]
View(ozone_sec_95)

# Recode pluie : "sec" devient "Oui", le reste devient "Non"
ozone$pluie <- ifelse(tolower(ozone$Pluies) == "sec", "Oui", "Non")

# Extraire les colonnes numériques
ozone_quanti <- ozone[sapply(ozone, is.numeric)]

# Extraire les colonnes non numériques (qualitatives)
ozone_qualit <- ozone[, !sapply(ozone, is.numeric)]

# Supprimer les lignes où vent = "Nord"
ozone_sans_nord <- ozone[ozone$vent != "Nord", ]

# Comptage des directions du vent
table(ozone$vent)

# Comptage croisé : direction du vent × pluie
table(ozone$vent, ozone$pluie)

#############################################
# TP2 - ÉCHANTILLONNAGE ET STATISTIQUE DESCRIPTIVE
#############################################

# Tirage d’un échantillon aléatoire de 50 individus depuis un fichier
grossebouffe <- read.csv(file.choose())
sample_grossebouffe <- grossebouffe[sample(nrow(grossebouffe), 50), ]
View(sample_grossebouffe)

# Analyse descriptive de 3 variables
nb_visites <- sample_grossebouffe$numvisit
nb_problemes <- sample_grossebouffe$badh
age <- sample_grossebouffe$age

# Effectifs
n_visites <- length(nb_visites)
n_problemes <- length(nb_problemes)
n_age <- length(age)

# Moyennes
moy_visites <- mean(nb_visites)
moy_problemes <- mean(nb_problemes)
moy_age <- mean(age)

# Écarts-types manuels
ecart_visites_man <- sqrt(sum((nb_visites - moy_visites)^2) / (n_visites - 1))
ecart_problemes_man <- sqrt(sum((nb_problemes - moy_problemes)^2) / (n_problemes - 1))
ecart_age_man <- sqrt(sum((age - moy_age)^2) / (n_age - 1))

# Écarts-types automatiques
ecart_visites <- sd(nb_visites)
ecart_problemes <- sd(nb_problemes)
ecart_age <- sd(age)

# Médianes
med_visites <- median(nb_visites)
med_problemes <- median(nb_problemes)
med_age <- median(age)

# Résumé dans un tableau
tableau_sante <- data.frame(
  Variable = c("Nombre de visites", "Problèmes de santé", "Âge"),
  Effectif = c(n_visites, n_problemes, n_age),
  Moyenne = c(moy_visites, moy_problemes, moy_age),
  Ecart_Type_Manuel = c(ecart_visites_man, ecart_problemes_man, ecart_age_man),
  Ecart_Type_sd = c(ecart_visites, ecart_problemes, ecart_age),
  Mediane = c(med_visites, med_problemes, med_age)
)

View(tableau_sante)

# Loi normale - probabilité avec pnorm() / quantiles avec qnorm()
pnorm(344, mean = 340, sd = 6)                              # a) P(X < 344)
pnorm(339, mean = 340, sd = 6)                              # b) P(X < 339)
1 - pnorm(343, mean = 340, sd = 6)                          # c) P(X > 343)
pnorm(346, mean = 340, sd = 6) - pnorm(334, mean = 340, sd = 6)  # d) P(334 < X < 346)
pnorm(342, mean = 340, sd = 6) - pnorm(338, mean = 340, sd = 6)  # e) |X - 340| < 2
qnorm(0.75, mean = 340, sd = 6)                             # f) seuil supérieur 75%
qnorm(0.20, mean = 340, sd = 6)                             # g) seuil inférieur 20%
pnorm(330, mean = 340, sd = 6) * 1000                       # h) combien < 330 sur 1000
qnorm(0.1, mean = 340, sd = 6)                              # i) réglage pour 10% < 340

#############################################
# TP3 - GRAPHIQUES EN R
#############################################

graphique <- read.table(file.choose())  # Charger les données ozone

# Histogramme en densité
hist(graphique$maxO3, probability = TRUE, col = "skyblue",
     main = "Histogramme (densité)", xlab = "maxO3")
lines(density(graphique$maxO3, na.rm = TRUE), col = "red", lwd = 2)

# Histogramme amélioré avec classes personnalisées
breaks <- c(seq(0, 180, by = 20), max(graphique$maxO3, na.rm = TRUE))
hist(graphique$maxO3, breaks = breaks, probability = TRUE, col = "lightgreen",
     main = "Histogramme amélioré", xlab = "maxO3")

# Diagramme en bâtons
barplot(table(graphique$maxO3), col = "skyblue",
        main = "Diagramme en bâtons", xlab = "maxO3", ylab = "Effectifs", las = 2)

# Polygone des effectifs
hist_data <- hist(graphique$maxO3, plot = FALSE)
plot(hist_data$mids, hist_data$counts, type = "o", col = "blue", xlab = "maxO3", ylab = "Effectifs",
     main = "Polygone des effectifs")

# Superposition histogramme + polygone
hist(graphique$maxO3, col = "lightgray", main = "Histogramme + Polygone", xlab = "maxO3")
lines(hist_data$mids, hist_data$counts, type = "o", col = "blue", lwd = 2)

# Effectifs cumulés
cum_count <- cumsum(hist_data$counts)
plot(hist_data$mids, cum_count, type = "s", col = "darkgreen",
     xlab = "maxO3", ylab = "Effectifs cumulés", main = "Effectifs cumulés")

# Nuage de points : ozone vs T12
plot(graphique$T12, graphique$maxO3, xlab = "Température à midi", ylab = "maxO3",
     main = "Nuage de points", pch = 19, col = "darkblue")

# Boîte à moustaches selon direction du vent
boxplot(maxO3 ~ vent, data = graphique, col = "lightblue",
        main = "Ozone par direction du vent")

# Boîte à moustaches selon vent + pluie
boxplot(maxO3 ~ vent + pluie, data = graphique, col = c("lightblue", "lightpink"),
        main = "Ozone selon vent et pluie", las = 2)

# Répartition pluie/sec par direction du vent
table_pluie_vent <- table(graphique$vent, graphique$pluie)
barplot(table_pluie_vent, beside = TRUE, col = c("skyblue", "salmon"), legend = TRUE,
        main = "Répartition pluie/sec par vent")

# Moyennes d’ozone par direction du vent
moyennes_ozone <- tapply(graphique$maxO3, graphique$vent, mean, na.rm = TRUE)
barplot(moyennes_ozone, main = "Ozone moyen par vent", col = "red")

# Nuages de points pluie / sec
plot(graphique$T12[graphique$pluie == "Pluie"], graphique$maxO3[graphique$pluie == "Pluie"],
     col = "blue", pch = 19, xlab = "T12", ylab = "maxO3", main = "Ozone selon pluie")
points(graphique$T12[graphique$pluie == "Sec"], graphique$maxO3[graphique$pluie == "Sec"],
       col = "red", pch = 17)
legend("topleft", legend = c("Pluie", "Sec"), col = c("blue", "red"), pch = c(19, 17))

# Nombre de relevés par vent
barplot(table(graphique$vent), col = "lightgray", main = "Relevés par vent")

# Relevés croisés vent/pluie
barplot(table_pluie_vent, beside = TRUE, col = c("lightgreen", "tomato"), legend = TRUE,
        main = "Vent et pluie", ylab = "Effectifs")

#############################################
# TP4 - LOI NORMALE & INTERVALLES DE CONFIANCE
#############################################

# 1.1 - Intervalle de confiance théorique
echantillon <- c(31.16, 33.36, 33.82, 33.26, 31.20, 34.71, 33.22, 29.60, 34.53, 30.25,
                 30.44, 32.04, 32.50, 24.95, 36.84, 29.59, 33.17, 30.09, 33.16, 28.59,
                 31.06, 31.91, 32.37, 28.42, 38.99, 32.09, 32.03, 30.18, 29.73, 36.12,
                 35.26, 32.41, 33.87, 31.98, 32.37, 28.19, 34.67, 28.56, 32.51, 35.40,
                 35.25, 32.07, 27.50, 34.34, 29.37, 32.66, 36.68, 34.97, 35.20, 34.90)

mu <- 33
sigma <- 3
n <- length(echantillon)
z <- qnorm(0.975)
borneInf <- mu - z * sigma / sqrt(n)
borneSup <- mu + z * sigma / sqrt(n)
cat("IC à 95% :", round(borneInf, 2), "-", round(borneSup, 2), "\n")

# 1.2 - Boulons
boulons <- data.frame(
  Diamètre = c(15.71, 15.94, 16.02, 15.94, 15.87, 16.92, 16.01, 15.94, 15.93, 15.95,
               16.02, 16.11, 15.97, 16.14, 15.89, 16.10, 15.97, 15.95, 16.04, 16.07),
  Longueur = c(50.11, 50.01, 50.63, 50.22, 50.06, 49.97, 50.12, 50.12, NA, 50.07,
               50.10, 49.87, 49.94, 49.95, 50.09, NA, 49.92, 49.99, 50.00, 49.97)
)
diametre <- na.omit(boulons$Diamètre)
longueur <- na.omit(boulons$Longueur)
n_d <- length(diametre)
n_l <- length(longueur)
z <- qnorm(0.975)
ic_diam <- mean(diametre) + c(-1, 1) * z * sd(diametre) / sqrt(n_d)
ic_long <- mean(longueur) + c(-1, 1) * z * sd(longueur) / sqrt(n_l)

# 2.1 - IC à 90%
moyenne <- 43; ecart_type <- 14; n <- 80
z <- qnorm(0.95)
marge <- z * ecart_type / sqrt(n)
cat("IC 90% :", round(moyenne - marge, 2), "-", round(moyenne + marge, 2), "\n")

# 2.2 - IC à 95% petit échantillon : Student !
moyenne <- 29; ecart <- 13; n <- 18
t <- qt(0.975, df = n - 1)
marge <- t * ecart / sqrt(n)
cat("IC 95% :", round(moyenne - marge, 2), "-", round(moyenne + marge, 2), "\n")

# 2.3 - IC pour plusieurs variables
ozone <- read.table(file.choose(), header = TRUE)
variables <- c("maxO3", "T9", "T12", "T15", "Ne9", "Ne12", "Ne15", "Vx9", "Vx12", "Vx15")
for (var in variables) {
  tab <- ozone[[var]]
  n <- length(tab)
  m <- mean(tab)
  s <- sd(tab)
  t <- qt(0.975, df = n - 1)
  marge <- t * s / sqrt(n)
  IC <- c(m - marge, m + marge)
  cat(sprintf("IC 95%% pour %-5s : [%.2f ; %.2f], Moyenne = %.2f, t = %.3f, n = %d\n",
              var, IC[1], IC[2], m, t, n))
}

#############################################
# TP5 -  Distribution d'échantillonnage et tests de conformité
#############################################
#1.1
#############################################
# Charger les données depuis un fichier CSV (choix manuel du fichier)
Ormeaux <- read.csv(file.choose(), header = TRUE)

# Vérification du résumé des données
summary(Ormeaux)

# Conversion de la variable 'Sex' en facteur (sans espace entre le nom et le $)
Ormeaux$Sex <- as.factor(Ormeaux$Sex)

# Nouveau résumé après conversion de 'Sex'
summary(Ormeaux)

# ⚠️ Éviter 'attach' car il peut causer des conflits d'environnement
# Utiliser directement avec $ ou with()

# Visualisation globale : produit une matrice de scatterplots pour les variables numériques
plot(Ormeaux)
# la fonction boxplot je l'ai moi meme elle est pas demande juste pour voir le visuel 
boxplot(Length ~ Sex, data = Ormeaux, col = c("lightblue", "pink", "lightgreen"),
        main = "Longueur par sexe", xlab = "Sexe", ylab = "Longueur")


