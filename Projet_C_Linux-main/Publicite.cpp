#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <signal.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <signal.h>
#include "protocole.h" // contient la cle et la structure d'un message

int idQ, idShm;
char *pShm;
void handlerSIGUSR1(int sig);
int fd;

int main()
{
  MESSAGE requete;
  requete.type = 1;
  requete.expediteur = getpid();
  requete.requete = UPDATE_PUB;
  // Armement des signaux
  // TO DO
  struct sigaction A;
  A.sa_handler = handlerSIGUSR1;
  sigemptyset(&A.sa_mask);
  A.sa_flags = 0;
   
  if (sigaction(SIGUSR1,&A,NULL) ==-1)
  {
    perror("Erreur de sigaction");
  }

  
  // Masquage des signaux
  sigset_t mask;
  sigfillset(&mask);
  sigdelset(&mask,SIGUSR1);
  sigprocmask(SIG_SETMASK,&mask,NULL);

  // Recuperation de l'identifiant de la file de messages
  fprintf(stderr,"(PUBLICITE %d) Recuperation de l'id de la file de messages\n",getpid());
  if ((idQ = msgget(CLE,0)) == -1)
  {
    perror("(PUBLICITE) Erreur de msgget");
    exit(1);
  }

  // Recuperation de l'identifiant de la mémoire partagée
  if((idShm = shmget(CLE,0,0)) == -1)
    {
      perror("Erreur de shmget");
      exit(1);
    }
    printf("idShm = %d\n",idShm);
  // Attachement à la mémoire partagée
  if ((pShm = (char*)shmat(idShm,NULL,0)) == (char*)-1)
  {
    perror("Erreur de shmat");
    exit(1);
  }
  printf("pShm = %ld\n",pShm);
  // pShm = (char*)malloc(52); // a supprimer et remplacer par ce qu'il faut

  // Mise en place de la publicité en mémoire partagée
  char pub[51];
  strcpy(pub,"Bienvenue sur le site du Maraicher en ligne !");

  for (int i=0 ; i<=50 ; i++) pShm[i] = ' ';
  pShm[51] = '\0';
  int indDebut = 25 - strlen(pub)/2;
  for (int i=0 ; i<strlen(pub) ; i++) pShm[indDebut + i] = pub[i];

  while(1)
  {
    // Envoi d'une requete UPDATE_PUB au serveur
    if (msgsnd(idQ, &requete, sizeof(MESSAGE) - sizeof(long), 0) == -1) 
    {
      perror("(Logout)(PUBLICITE)Erreur lors de l'envoie de la requete UPDATE_PUB\n");
      exit(1);
    }

    sleep(1); 

    // Decallage vers la gauche
    char premierChar = pShm[0]; // Sauvegarde du premier caractère
    for (int i = 0; i < 50; i++) {
        pShm[i] = pShm[i + 1]; // Décalage des caractères
    }
    pShm[50] = premierChar;
    pShm[51] = '\0';

  }
}

void handlerSIGUSR1(int sig)
{
  fprintf(stderr,"(PUBLICITE %d) Nouvelle publicite !\n",getpid());

  // Lecture message NEW_PUB

  // Mise en place de la publicité en mémoire partagée
}
