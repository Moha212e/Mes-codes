#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <sys/wait.h>
#include <signal.h>
#include <string.h>
#include <unistd.h>
#include "protocole.h" // contient la cle et la structure d'un message
#include "FichierClient.h" // contient les fonctions pour le fichier client
#include <cerrno>


int idQ,idShm,idSem;
int fdPipe[2];
pid_t lancementPub, procAccesBd;
TAB_CONNEXIONS *tab;
char buffer[20];

void handlerSIGINT(int sig);
void handlerSIGCHLD(int sig);

void afficheTab();
void utilisationTableConnexions(MESSAGE *pM, MESSAGE* pReponse);
pid_t creerProcessusFils(int nbArg, const char* arg0, const char* arg1, const char* arg2, const char* arg3);
void reponseLOGIN(MESSAGE reponse, int typeClient, int typeRequete, const char* rep);
void login(MESSAGE* message, MESSAGE* reponse);
void envoiRequete(MESSAGE* pReponse);
void envoiSignal(int pid, int typeSignal);
int main()
{
 

  // Armement des signaux
  // TO DO
  fprintf(stderr,"Armement du signal SIGINT\n");
  struct sigaction A;
  A.sa_handler = handlerSIGINT;
  A.sa_flags = 0;
  sigemptyset(&A.sa_mask);
  sigaction(SIGINT,&A,NULL);

  fprintf(stderr,"Armement du signal SIGCHLD\n");
  struct sigaction B;
  B.sa_handler = handlerSIGCHLD;
  B.sa_flags = 0;
  sigemptyset(&B.sa_mask);
  sigaction(SIGCHLD,&B,NULL);
  // Creation des ressources
  fprintf(stderr, "(SERVEUR) création de la mémoire partagée\n");
  if ((idShm = shmget(CLE, 52, IPC_CREAT | IPC_EXCL | 0777)) ==-1)
  {
  perror("(SERVEUR)Erreur de création de la mémoire partagée\n");
  exit(1);
  }
 
  // Creation de la file de message
  fprintf(stderr,"(SERVEUR %d) Creation de la file de messages\n",getpid());
  if ((idQ = msgget(CLE,IPC_CREAT | IPC_EXCL | 0600)) == -1)  // CLE definie dans protocole.h
  {
    perror("(SERVEUR) Erreur de msgget");
    exit(1);
  }

  // TO BE CONTINUED

  // Creation du pipe
  // TO DO
  if(pipe(fdPipe) == -1)
  {
    perror("(SERVEUR) échec de création du pipe\n");
    exit(1);
  }
  printf("Creation pipe OK.\n");
  printf("fdPipe[0] = %d\n",fdPipe[0]);
  printf("fdPipe[1] = %d\n",fdPipe[1]);
  // Initialisation du tableau de connexions
  tab = (TAB_CONNEXIONS*) malloc(sizeof(TAB_CONNEXIONS)); 

  for (int i=0 ; i<6 ; i++)
  {
    tab->connexions[i].pidFenetre = 0;
    strcpy(tab->connexions[i].nom,"");
    tab->connexions[i].pidCaddie = 0;
  }
  tab->pidServeur = getpid();
  tab->pidPublicite = 0;

  afficheTab();

  //Creation du processus Publicite (étape 2)
  // TO DO
  lancementPub = creerProcessusFils(1,"./Publicite", "Publicite",NULL,NULL);
  tab->pidPublicite = lancementPub;
  // Creation du processus AccesBD (étape 4)
  // TO DO
  sprintf(buffer,"%d", fdPipe[0]);
  procAccesBd = creerProcessusFils(2,"./AccesBD", "AccesBD", buffer ,NULL);
  tab->pidAccesBD = procAccesBd;
  MESSAGE m;
  MESSAGE reponse;

  while(1)
  {
    fprintf(stderr,"(SERVEUR %d) Attente d'une requete...\n",getpid());
    if (msgrcv(idQ,&m,sizeof(MESSAGE)-sizeof(long),1,0) == -1)
    {
      if (errno == EINTR){
            continue;  // Réessaye si l'appel a été interrompu
      }
      perror("(SERVEUR) Erreur de msgrcv");
      msgctl(idQ,IPC_RMID,NULL);
      shmctl(idShm, IPC_RMID, NULL);
      exit(1);
    }

    switch(m.requete)
    {
      case CONNECT :  // TO DO
                      fprintf(stderr,"(SERVEUR %d) Requete CONNECT reçue de %d\n",getpid(),m.expediteur);

                      utilisationTableConnexions(&m, &reponse);
                     
                      break;

      case DECONNECT : // TO DO
                      fprintf(stderr,"(SERVEUR %d) Requete DECONNECT reçue de %d\n",getpid(),m.expediteur);
                      
                      utilisationTableConnexions(&m, &reponse);

                      break;
      case LOGIN :    // TO DO
                      fprintf(stderr,"(SERVEUR %d) Requete LOGIN reçue de %d : --%d--%s--%s--\n",getpid(),m.expediteur,m.data1,m.data2,m.data3);
                      //gestion du login
                      login(&m, &reponse);

                      //envoi de la reponse au client
                      envoiRequete(&reponse);

                      //envoi du signal SIGUSR1 pour faire afficher le message dans data4
                      envoiSignal(reponse.type, SIGUSR1);
                      break; 

      case LOGOUT :   // TO DO
                      fprintf(stderr,"(SERVEUR %d) Requete LOGOUT reçue de %d\n",getpid(),m.expediteur);
                  
                      utilisationTableConnexions(&m, &reponse);
                      //envoi de la reponse au Caddie si il y a un client
                      

                      break;

      case UPDATE_PUB :  // TO DO
                      fprintf(stderr,"(SERVEUR %d) Requete UPDATE_PUB Recue de %d\n", getpid(),m.expediteur);
                      
                      utilisationTableConnexions(&m, &reponse);

                      break;

      case CONSULT :  // TO DO
                      fprintf(stderr,"(SERVEUR %d) Requete CONSULT reçue de %d\n",getpid(),m.expediteur);
                      utilisationTableConnexions(&m, &reponse);
                      // //envoi de la requete de consultation au bon caddie
                      envoiRequete(&reponse);

                      break;

      case ACHAT :    // TO DO
                      fprintf(stderr,"(SERVEUR %d) Requete ACHAT reçue de %d\n",getpid(),m.expediteur);
                      utilisationTableConnexions(&m, &reponse);
                      // //envoi de la requete d'achat au bon caddie
                      envoiRequete(&reponse);

                      break;

      case CADDIE :   // TO DO
                      fprintf(stderr,"(SERVEUR %d) Requete CADDIE reçue de %d\n",getpid(),m.expediteur);
                      utilisationTableConnexions(&m, &reponse);
                      envoiRequete(&m);


                      break;

      case CANCEL :   // TO DO
                      fprintf(stderr,"(SERVEUR %d) Requete CANCEL reçue de %d\n",getpid(),m.expediteur);
                      break;

      case CANCEL_ALL : // TO DO
                      fprintf(stderr,"(SERVEUR %d) Requete CANCEL_ALL reçue de %d\n",getpid(),m.expediteur);
                      break;

      case PAYER : // TO DO
                      fprintf(stderr,"(SERVEUR %d) Requete PAYER reçue de %d\n",getpid(),m.expediteur);
                      break;

      case NEW_PUB :  // TO DO
                      fprintf(stderr,"(SERVEUR %d) Requete NEW_PUB reçue de %d\n",getpid(),m.expediteur);
                      break;
    }
    afficheTab();
  }
}

void handlerSIGINT(int sig)
{
  fprintf(stderr,"\nSuppression de la file de message (%d)\n", idQ);

  kill(tab->pidPublicite, SIGINT);
  if (msgctl(idQ,IPC_RMID,NULL) ==-1)
  {
   perror("(SERVEUR)Erreur de msgctl(2), File de message non supprimé\n");
   exit(1);
  }
  
  fprintf(stderr, "Supression de la mémoire partagée\n");

  if (shmctl(idShm, IPC_RMID, NULL) == -1)
  {
    perror("(SERVEUR)Erreur de suppresion de la mémoire partagée\n");
    exit(1);
  }

  // Fermeture du pipe
  if (close(fdPipe[0]) ==-1)
  {
    perror("Erreur fermeture sortie du pipe");
    exit(1);
  } 

  if (close(fdPipe[1]) ==-1)
  {
    perror("Erreur fermeture entree du pipe");
    exit(1);
  }
  
}

void handlerSIGCHLD(int sig)
{
  int id, status;
  id = wait(&status);
  printf("\n(PERE) Suppression du fils %d de la table des processus\n",id);
  if (WIFEXITED(status))
  printf("(PERE) Le fils %d s’est termine par un exit(%d)\n",id,WEXITSTATUS(status));
  for(int i = 0; i < 6; i++)
  {
    if(tab->connexions[i].pidCaddie == id)
    {
      tab->connexions[i].pidCaddie = 0;

      i = 10;
    }       
   
  }

}

void afficheTab()
{
  fprintf(stderr,"Pid Serveur   : %d\n",tab->pidServeur);
  fprintf(stderr,"Pid Publicite : %d\n",tab->pidPublicite);
  fprintf(stderr,"Pid AccesBD   : %d\n",tab->pidAccesBD);
  for (int i=0 ; i<6 ; i++)
    fprintf(stderr,"%6d -%20s- %6d\n",tab->connexions[i].pidFenetre,
                                                      tab->connexions[i].nom,
                                                      tab->connexions[i].pidCaddie);
  fprintf(stderr,"\n");
}
pid_t creerProcessusFils(int nbArg, const char* arg0, const char* arg1, const char* arg2, const char* arg3)
{
  pid_t id;
  id = fork();
  
  if(id == 0)
  {
    if (nbArg == 1)
    {
      if (execl(arg0,arg1,NULL) == -1)
      {
        perror("Erreur de execl()");
        exit(1);
      }
    }

    if (nbArg == 2)
    {
      if (execl(arg0,arg1,arg2,NULL) == -1)
      {
        perror("Erreur de execl()");
        exit(1);
      }
    }

    if (nbArg == 3)
    {
      if (execl(arg0,arg1,arg2,arg3,NULL) == -1)
      {
        perror("Erreur de execl()");
        exit(1);
      }
    }
  }

  return id;
}
void utilisationTableConnexions(MESSAGE *pM,MESSAGE* pReponse)
{
  for(int i = 0; i < 6; i++){
    switch(pM->requete)
    {
      case CONNECT: 
                  if(tab->connexions[i].pidFenetre == 0)
                  {
                    tab->connexions[i].pidFenetre = pM->expediteur;

                    i = 10;
                  }
                  break;
      case DECONNECT:
                  if(tab->connexions[i].pidFenetre == pM->expediteur)
                  {
                    tab->connexions[i].pidFenetre = 0;

                    i = 10;
                  }        
                  break;
      case LOGIN:  
                  if(tab->connexions[i].pidFenetre == pM->expediteur)
                  {
 
                    if(pReponse->data1 == 1)
                    {
                      strcpy(tab->connexions[i].nom,pM->data2);
                      pReponse->expediteur = pM->expediteur;
                      sprintf(buffer, "%d", fdPipe[1]);
                      tab->connexions[i].pidCaddie = creerProcessusFils(2,"./Caddie", "Caddie", buffer,NULL);
                      pReponse->type = tab->connexions[i].pidCaddie;
                      envoiRequete(pReponse);
                    }
 
                    pReponse->type = pM->expediteur;
 
                    i = 10;
                  }  
                  break;
      case LOGOUT:
                  if(tab->connexions[i].pidFenetre == pM->expediteur)
                  {
                    pReponse->requete = LOGOUT;
                    pReponse->type = tab->connexions[i].pidCaddie;
                    if(pReponse->type != 0)
                    envoiRequete(pReponse);
                    strcpy(tab->connexions[i].nom,"");
                    
                    i = 10;
                  }
                  break;
      case UPDATE_PUB:
                  if(tab->connexions[i].pidFenetre > 0)
                  {
                    kill(tab->connexions[i].pidFenetre, SIGUSR2);
                  }
                  break;
      case CONSULT:
                  if(tab->connexions[i].pidFenetre == pM->expediteur)
                  {
                    pReponse->requete = CONSULT;
                    pReponse->type = tab->connexions[i].pidCaddie;
                    pReponse->data1 = pM->data1;
                    pReponse->expediteur = pM->expediteur;
                    strcpy(pReponse->data2, pM->data2);
                    i = 10;
                  }     
                  break;
      case ACHAT:
                  if(tab->connexions[i].pidFenetre == pM->expediteur)
                  {
                    pReponse->requete = ACHAT;
                    pReponse->type = tab->connexions[i].pidCaddie;
                    pReponse->data1 = pM->data1;
                    strcpy(pReponse->data2, pM->data2);
                    
                    pReponse->expediteur = pM->expediteur;
                    i = 10;
                  }     
                  break;
      case CADDIE:
                  if(tab->connexions[i].pidFenetre == pM->expediteur)
                  {
                    pM->type = tab->connexions[i].pidCaddie;
                    i = 10;
                  }    
                  break;
    }
    
  }
}


//initialise le type de reponse a renvoyer qu client pour le LOGIN
void reponseLOGIN(MESSAGE* pReponse, int typeClient, int typeRequete, const char* rep)
{
  pReponse->requete = typeRequete;
  pReponse->data1 = typeClient;
  strcpy(pReponse->data4, rep);
}

//fonction login va gerer la demande de LOGIN du client et faire les vérifications
void login(MESSAGE *pM, MESSAGE *pReponse)
{
  if(pM->data1 == 1){
    if(estPresent(pM->data2))
    {
      reponseLOGIN(pReponse, 0, LOGIN, "---Client deja existant !---");
      utilisationTableConnexions(pM, pReponse);
    }
    else
    { 
      ajouteClient(pM->data2, pM->data3);
      reponseLOGIN(pReponse, 1, LOGIN, "---Nouvel client cree: bienvenue !---");
      utilisationTableConnexions(pM, pReponse);

    }
  }
  else
  {
    int position = estPresent(pM->data2);
    if(position)
    {
      if(verifieMotDePasse(position, pM->data3))
      {
        reponseLOGIN(pReponse, 1, LOGIN, "---Re-bonjour cher client !---");
        utilisationTableConnexions(pM, pReponse);

      }
      else
      {
        reponseLOGIN(pReponse, 0, LOGIN, "---Mot de passe incorrect---");
        utilisationTableConnexions(pM, pReponse);

      }
        
    }
    else
    {
      reponseLOGIN(pReponse, 0, LOGIN, "---Utilisateur inconnu---");
      utilisationTableConnexions(pM, pReponse);

    }
      
  }
  
  
}

//fonction qui gere l'envoi de toute les requetes
void envoiRequete(MESSAGE* pReponse)
{
  
  if (msgsnd(idQ, pReponse, sizeof(MESSAGE) - sizeof(long), 0) == -1) 
  {
    perror("(SERVEUR)Erreur envoi reponse\n");
    exit(1);
  }
}

//fonction qui gere l'envoi d'un signal a un processus
void envoiSignal(int pid, int typeSignal)
{
  if(kill(pid, typeSignal) == -1) 
  {
    perror("erreur KILL");
    exit(1);
  }
}