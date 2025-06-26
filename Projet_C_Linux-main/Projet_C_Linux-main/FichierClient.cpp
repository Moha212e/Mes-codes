#include "FichierClient.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <fcntl.h>
#include <string.h>

int estPresent(const char* nom)
{
  int fd = open(FICHIER_CLIENT, O_RDONLY);
  if(fd == -1){
    printf("le fichier n'existe pas a l'ouverture\n");
    return -1; //le fichier n'existe pas a l'ouverture
  }

  CLIENT user;
  int position = 1; // La position commence à 1.
  while (read(fd, &user, sizeof(CLIENT)))
  {
    if (strcmp(user.nom, nom) == 0){
        close(fd);
        printf("Utilisateur trouvé, retourne la position (1, 2, 3, ...).\n");
        return position; // Utilisateur trouvé, retourne la position (1, 2, 3, ...).

    }
    position++;
  }

  close(fd);
  return 0; // Utilisateur non trouvé.
  
}

////////////////////////////////////////////////////////////////////////////////////
int hash(const char* motDePasse)
{
  // calcul le hash du mot de passe = (somme des codes ASCII) % 97
  
  int hash = 0;
  int codeASCII;
  for(int i = 0; i < strlen(motDePasse); i++)
  {
   codeASCII = (int)motDePasse[i];
   hash += (i + 1) * codeASCII;
  }
  return hash % 97;
}

////////////////////////////////////////////////////////////////////////////////////
void ajouteClient(const char* nom, const char* motDePasse)
{
  int fd = open(FICHIER_CLIENT, O_WRONLY|O_APPEND|O_CREAT);
  // crée le fichier su celui-ci n'existe pas
  if(fd == -1)
  {
    printf("le fichier n'existe pas a l'ouverture\n");
    //return -1; //le fichier n'existe pas a l'ouverture
    exit(1);
  }
  CLIENT user;
  strcpy(user.nom, nom);
  user.hash = hash(motDePasse);
  // ajoute un nouvel utilisateur à la fin du fichier
  write(fd, &user, sizeof(CLIENT));
  close(fd);
  
}

////////////////////////////////////////////////////////////////////////////////////
int verifieMotDePasse(int pos, const char* motDePasse)
{
  CLIENT user;
  int fd = open(FICHIER_CLIENT, O_RDONLY);
  if(fd == -1)
  {
    printf("le fichier n'existe pas a l'ouverture\n");
    return -1; //le fichier n'existe pas a l'ouverture
  }
  lseek(fd,(pos-1)*sizeof(CLIENT),SEEK_SET);
  read(fd,&user, sizeof(CLIENT));
  if(user.hash == hash(motDePasse))// retourne 1 si le mot de passe est correct
  {
    close(fd);
    return 1;
  }
  else return 0;
}

////////////////////////////////////////////////////////////////////////////////////
int listeUtilisateurs(CLIENT *vecteur) // le vecteur doit etre suffisamment grand
{
  int fd = open(FICHIER_CLIENT, O_RDONLY);
  if(fd == -1){
    printf("le fichier n'existe pas a l'ouverture\n");
    return -1; //le fichier n'existe pas a l'ouverture
  }
  int nbUser;
  CLIENT user;
  for(nbUser = 0; read(fd, vecteur, sizeof(CLIENT)); nbUser++, vecteur++);
  return nbUser;// retourne le nombre d'utilisateurs présents dans le fichier

}
