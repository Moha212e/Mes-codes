#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include <string.h>
#include <signal.h>
#include "protocole.h"

int idQ;
int fdWpipe;
int pidClient; // Garder le pid du client associé

// Structure pour le panier interne du Caddie
ARTICLE articles[10];
int nbArticles = 0;

void wPipe(MESSAGE* pM);
void handlerSIGALRM(int sig);

int main(int argc,char* argv[])
{
  // Armement du signal SIGALRM
  struct sigaction A;
  A.sa_handler = handlerSIGALRM;
  sigemptyset(&A.sa_mask);
  A.sa_flags = 0;
  if (sigaction(SIGALRM, &A, NULL) == -1)
  {
      perror("Erreur de sigaction pour SIGALRM");
      exit(1);
  }

  // Masquage de SIGINT
  sigset_t mask;
  sigaddset(&mask,SIGINT);
  sigprocmask(SIG_SETMASK,&mask,NULL);

  // Recuperation de l'identifiant de la file de messages
  if ((idQ = msgget(CLE,0)) == -1)
  {
    perror("(CADDIE) Erreur de msgget");
    exit(1);
  }
  
  // Récupération descripteur écriture du pipe
  if (argc != 2)
  {
    fprintf(stderr, "(CADDIE) Mauvais nombre d'arguments.\n");
    exit(1);
  }
  fdWpipe = atoi(argv[1]);

  MESSAGE m;
  
  while(1)
  {
    // Attente d'une requete
    if (msgrcv(idQ, &m, sizeof(MESSAGE)-sizeof(long), getpid(), 0) == -1)
    {
      perror("(CADDIE) Erreur de msgrcv");
      exit(1);
    }

    alarm(10); // On relance l'alarme à chaque requête reçue

    pidClient = m.expediteur;
    m.expediteur = getpid();

    switch(m.requete)
    {
      case LOGIN:
        alarm(10); // Démarrage de l'alarme
        break;

      case LOGOUT:
        alarm(0); // On désactive l'alarme
        handlerSIGALRM(0); // On appelle le handler pour nettoyer le panier
        exit(0);
        break;

      case CONSULT:
        wPipe(&m);
        if (msgrcv(idQ, &m, sizeof(MESSAGE)-sizeof(long), getpid(), 0) == -1) { break; }
        if (m.data1 != -1)
        {
          m.type = pidClient;
          if (msgsnd(idQ, &m, sizeof(MESSAGE)-sizeof(long), 0) != -1)
            kill(m.type, SIGUSR1);
        }
        break;

      case ACHAT:
      {
        wPipe(&m);
        if (msgrcv(idQ, &m, sizeof(MESSAGE)-sizeof(long), getpid(), 0) == -1) { break; }
        
        int quantiteAchetee = atoi(m.data3);
        if (quantiteAchetee > 0 && nbArticles < 10)
        {
          articles[nbArticles].id = m.data1;
          strcpy(articles[nbArticles].intitule, m.data2);
          articles[nbArticles].prix = m.data5;
          articles[nbArticles].stock = quantiteAchetee;
          nbArticles++;
        }
        
        m.type = pidClient;
        if (msgsnd(idQ, &m, sizeof(MESSAGE)-sizeof(long), 0) != -1)
            kill(m.type, SIGUSR1);
        break;
      }
      case CANCEL:
      {
        int indice = m.data1;
        if (indice >= 0 && indice < nbArticles)
        {
            MESSAGE toAccesBD;
            toAccesBD.requete = CANCEL;
            toAccesBD.data1 = articles[indice].id;
            sprintf(toAccesBD.data2, "%d", articles[indice].stock);
            wPipe(&toAccesBD);

            for (int i = indice; i < nbArticles - 1; i++) { articles[i] = articles[i + 1]; }
            nbArticles--;
        }
        break;
      }
       case CANCEL_ALL:
       {
           for (int i = 0; i < nbArticles; i++)
           {
               MESSAGE toAccesBD;
               toAccesBD.requete = CANCEL;
               toAccesBD.data1 = articles[i].id;
               sprintf(toAccesBD.data2, "%d", articles[i].stock);
               wPipe(&toAccesBD);
           }
           nbArticles = 0;
           break;
       }
       case PAYER:
         alarm(0); // On désactive l'alarme
         nbArticles = 0;
         break;
        case CADDIE:
        {
          for (int i = 0; i < nbArticles; i++)
          {
            MESSAGE reponse;
            reponse.type = pidClient;
            reponse.expediteur = getpid();
            reponse.requete = CADDIE;
            strcpy(reponse.data2, articles[i].intitule);
            sprintf(reponse.data3, "%d", articles[i].stock);
            reponse.data5 = articles[i].prix;

            if (msgsnd(idQ, &reponse, sizeof(MESSAGE)-sizeof(long), 0) != -1)
              kill(reponse.type, SIGUSR1);
          }
          break;
        }
    }
  }
}

void wPipe(MESSAGE* pM)
{
  if (write(fdWpipe, pM, sizeof(MESSAGE)) == -1)
  {
    perror("Erreur de write dans le pipe");
  }
}

void handlerSIGALRM(int sig)
{
    fprintf(stderr,"(CADDIE %d) Time Out ou Logout ! Nettoyage du panier.\n",getpid());
    
    // Remise à jour de la BD
    for (int i = 0; i < nbArticles; i++)
    {
        MESSAGE toAccesBD;
        toAccesBD.requete = CANCEL;
        toAccesBD.data1 = articles[i].id;
        sprintf(toAccesBD.data2, "%d", articles[i].stock);
        wPipe(&toAccesBD);
    }
    nbArticles = 0;

    if (sig == SIGALRM) // Si c'est un vrai time-out, on prévient le client
    {
        // Envoi d'un message TIME_OUT au client s'il existe toujours
        if (kill(pidClient, 0) == 0) // Vérifie si le processus client existe
        {
            MESSAGE timeOutMsg;
            timeOutMsg.type = pidClient;
            timeOutMsg.requete = TIME_OUT;
            msgsnd(idQ, &timeOutMsg, sizeof(MESSAGE) - sizeof(long), 0);
            kill(pidClient, SIGUSR1);
        }
        exit(0); // Le Caddie se termine
    }
}
    