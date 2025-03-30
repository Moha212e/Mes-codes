#exercice 1 
ozone <- read.table("C:\\etude-ozone-bretagne-2011.txt", header=TRUE, sep="")
summary(ozone)
str(ozone)

#exercice 2 
nrow(ozone)  # Nombre de lignes

ncol(ozone)  # Nombre de colonnes

#exercice 3
rownames(ozone) #nom des lignes
colnames(ozone)  # Noms des colonnes

#exercice 4 
str(ozone) #il affiche le nom de la colonne, ses donn??es, et le type de donn??es 

#exercice 5

#extraction en tant que vecteur
colonne3var1 <- ozone[[3]]
colonne3var1

#extraction par nom en tant que vecteur
colonne3var2 <- ozone$T12
colonne3var2

#extraction en tant que vecteur
colonne3var3 <- ozone[,3]

rm(colonne3var1,colonne3var2,colonne3var2)

#MANIPULATIONS DES DONNEES

#exercice 1
colnames(ozone)[which(colnames(ozone) == "pluie")] <- "Pluies"
colnames(ozone)  # Noms des colonnes

#exercice 2
ozone_85 <- ozone[ozone$maxO3 >= 85, ]
str(ozone_85)
rm(ozone_85)

#exercice 3
ozone_sec <- ozone[ozone$Pluies == "Sec", ]
str(ozone_sec)
rm(ozone_sec)

#exercice 4
ozone_secetozone <- ozone[ozone$Pluies == "Sec" & ozone$max03v>=95, ]
str(ozone_secetozone)
rm(ozone_secetozone)

#exercice 5
levels(ozone$Pluies)<- c("Oui","Non")
table(ozone$Pluies)

ozone[["Pluies"]]

#exercice 6
ozone.quanti <- ozone[, sapply(ozone, is.numeric)]
str(ozone.quanti)
ozone.quanti
rm(ozone.quanti)

#exercice 7
ozone.quali <- ozone[, sapply(ozone, is.factor)]
str(ozone.quali)
rm(ozone.quali)

#exercice 8
ozonessNord <- ozone[ozone$vent != "Nord",]
rm(ozonessNord)

#exercice 9
table(ozone$vent)

#exercice 10
table(ozone$vent, ozone$Pluies)




