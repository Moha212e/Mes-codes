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
#include <mysql.h>
#include "protocole.h" // contient la cle et la structure d'un message

int idQ;

// ARTICLE aEnvoyer;
// char bufferCnvert[100];
ARTICLE articles[10];
int nbArticles = 0;

int fdWpipe;
int pidClient;


void handlerSIGALRM(int sig);
void wPipe(MESSAGE* pM);
void envoiArticleClient(MESSAGE* pM);
void stockeArticle(MESSAGE* pM);
void envoiArticleVecteur(MESSAGE* pM);
int main(int argc,char* argv[])
{
  // Masquage de SIGINT
  sigset_t mask;
  sigaddset(&mask,SIGINT);
  sigprocmask(SIG_SETMASK,&mask,NULL);

  // Armement des signaux
  // TO DO

  // struct sigaction A;
  // A.sa_handler = handlerSIGALRM;
  // sigemptyset(&A.sa_mask);
  // A.sa_flags = 0;
   
  // if (sigaction(handlerSIGALRM,&A,NULL) ==-1)
  // {
  //   perror("Erreur de sigaction");
  // }

  // Recuperation de l'identifiant de la file de messages
  fprintf(stderr,"(CADDIE %d) Recuperation de l'id de la file de messages\n",getpid());
  if ((idQ = msgget(CLE,0)) == -1)
  {
    perror("(CADDIE) Erreur de msgget");
    exit(1);
  }

  MESSAGE m;
  
  char requete[200];
  char newUser[20];
  MYSQL_RES  *resultat;
  MYSQL_ROW  Tuple;

  // Récupération descripteur écriture du pipe
  fdWpipe = atoi(argv[1]);

  while(1)
  {
    if (msgrcv(idQ,&m,sizeof(MESSAGE)-sizeof(long),getpid(),0) == -1)
    {
      perror("(CADDIE) Erreur de msgrcv");
      exit(1);
    }

    switch(m.requete)
    {
      case LOGIN :    // TO DO
                      fprintf(stderr,"(CADDIE %d) Requete LOGIN reçue de %d\n",getpid(),m.expediteur);
                      pidClient = m.expediteur;
                      break;

      case LOGOUT :   // TO DO
                      fprintf(stderr,"(CADDIE %d) Requete LOGOUT reçue de %d\n",getpid(),m.expediteur);
                      // mysql_close(connexion);
                      exit(1);
                      break;

      case CONSULT :  // TO DO
                      fprintf(stderr,"(CADDIE %d) Requete CONSULT reçue de %d\n",getpid(),m.expediteur);

                      //envoi du message CONSULT ,recu du client en passant par le serveur, a AccesBD
                      wPipe(&m);
                      
                      //envoi de l'article au client
                      envoiArticleClient(&m);        
                      break;

      case ACHAT :    // TO DO
                      fprintf(stderr,"(CADDIE %d) Requete ACHAT reçue de %d\n",getpid(),m.expediteur);

                      // on transfert la requete à AccesBD
                      wPipe(&m);
                      // stocke l'article dans le vecteur et incrémenté nbArticle
                      stockeArticle(&m);
                      // on attend la réponse venant de AccesBD
                      envoiArticleClient(&m);
                      // Envoi de la reponse au client


                      break;

      case CADDIE :   // TO DO
                      fprintf(stderr,"(CADDIE %d) Requete CADDIE reçue de %d\n",getpid(),m.expediteur);

                      envoiArticleVecteur(&m);

                      break;

      case CANCEL :   // TO DO
                      fprintf(stderr,"(CADDIE %d) Requete CANCEL reçue de %d\n",getpid(),m.expediteur);

                      // on transmet la requete à AccesBD

                      // Suppression de l'aricle du panier
                      break;

      case CANCEL_ALL : // TO DO
                      fprintf(stderr,"(CADDIE %d) Requete CANCEL_ALL reçue de %d\n",getpid(),m.expediteur);

                      // On envoie a AccesBD autant de requeres CANCEL qu'il y a d'articles dans le panier

                      // On vide le panier
                      break;

      case PAYER :    // TO DO
                      fprintf(stderr,"(CADDIE %d) Requete PAYER reçue de %d\n",getpid(),m.expediteur);

                      // On vide le panier
                      break;
    }
  }
}

////////////////////////////////////////////////////////////////////////
////////////////////////////*HANDLER*///////////////////////////////////
////////////////////////////////////////////////////////////////////////
void handlerSIGALRM(int sig)
{
  fprintf(stderr,"(CADDIE %d) Time Out !!!\n",getpid());

  // Annulation du caddie et mise à jour de la BD
  // On envoie a AccesBD autant de requetes CANCEL qu'il y a d'articles dans le panier

  // Envoi d'un Time Out au client (s'il existe toujours)
         
  exit(0);
}

////////////////////////////////////////////////////////////////////////
////////////////////////////*FONCTION*//////////////////////////////////
////////////////////////////////////////////////////////////////////////

void wPipe(MESSAGE* pM)
{
  pM->expediteur = getpid();

  if(write(fdWpipe, pM, sizeof(MESSAGE)) != sizeof(MESSAGE))                      
  {
    perror("(CONSULT)(CADDIE)Erreur lors de l'envoie de la requete CONSULT a ACCESBD\n");
    exit(1);
  }
  if (msgrcv(idQ,pM,sizeof(MESSAGE)-sizeof(long),getpid(),0) == -1)
  {
    perror("(CADDIE) Erreur de msgrcv");
    exit(1);
  }
}

void envoiArticleClient(MESSAGE* pM)
{

  if(pM->data1 != -1)
  {
   
    pM->type = pidClient;
    pM->expediteur = getpid();
    if (msgsnd(idQ, pM, sizeof(MESSAGE) - sizeof(long), 0) == -1) 
    {
      perror("(CADDIE)Erreur lors de l'envoie de la requete au client\n");
    }
    kill(pidClient, SIGUSR1);
  }
}

void stockeArticle(MESSAGE* pM)
{
  if(atoi(pM->data3) != 0)
  {
    fprintf(stderr,"(CADDIE) nbArticles: %d\n", nbArticles);

     if (nbArticles >= 10) {
        fprintf(stderr, "Erreur : le tableau d'articles est plein.\n");
        exit(1);
    }

    articles[nbArticles].id = pM->data1;
    strcpy(articles[nbArticles].intitule, pM->data2);
    articles[nbArticles].prix = pM->data5;
    articles[nbArticles].stock = atoi(pM->data3);
    strcpy(articles[nbArticles].image, pM->data4);
    printf("Article ajouté avec succès : ID = %d, Intitulé = %s\n", articles[nbArticles].id, articles[nbArticles].intitule);

    fprintf(stderr,"id :%d\n articles[i].intitule: %s\n articles[i].prix: %f\n articles[i].stock: %d\n articles[i].image: %s\n", articles[nbArticles].id,
        articles[nbArticles].intitule, articles[nbArticles].prix, articles[nbArticles].stock, articles[nbArticles].image);
    nbArticles++;

    
  }

  fprintf(stderr,"(CADDIE) nbArticles: %d\n", nbArticles);

  
 
}

void envoiArticleVecteur(MESSAGE* pM)
{
  
  for(int i = 0;  i < nbArticles; i++)
  {
    fprintf(stderr,"envoi du l'article numero %d du vecteur au client\n",  i);
    pM->type = pidClient;
    pM->expediteur = getpid();
    pM->data1 = articles[i].id ;
    strcpy(pM->data2 , articles[i].intitule);
    pM->data5  = articles[i].prix;
    sprintf(pM->data3, "%d", articles[i].stock);
    strcpy(pM->data4 , articles[i].image);

    if (msgsnd(idQ, pM, sizeof(MESSAGE) - sizeof(long), 0) == -1) 
    {
      perror("(selecteurArticle)(CADDIE)Erreur lors de l'envoie de la requete CADDIE au client\n");
    }

    kill(pidClient, SIGUSR1);
   

  }
  

}
    