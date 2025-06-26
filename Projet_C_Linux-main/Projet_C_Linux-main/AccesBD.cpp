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
MYSQL* connexion;


MESSAGE m, reponse;
ARTICLE aEnvoyer;
char buffer[20];
ARTICLE requeteSqlArticle(int id);
int requeteAchatArticle(int id, int quantite);


int main(int argc,char* argv[])
{

  // Masquage de SIGINT
  sigset_t mask;
  sigaddset(&mask,SIGINT);
  sigprocmask(SIG_SETMASK,&mask,NULL);

  // Recuperation de l'identifiant de la file de messages
  fprintf(stderr,"(ACCESBD %d) Recuperation de l'id de la file de messages\n",getpid());
  if ((idQ = msgget(CLE,0)) == -1)
  {
    perror("(ACCESBD) Erreur de msgget");
    exit(1);
  }

  // Récupération descripteur lecture du pipe
  int fdRpipe = atoi(argv[1]);

  // Connexion à la base de donnée
  // TO DO
  connexion = mysql_init(NULL);
  if (mysql_real_connect(connexion,"localhost","Student","PassStudent1_","PourStudent",0,0,0) == NULL)
  {
    fprintf(stderr,"(SERVEUR) Erreur de connexion à la base de données...\n");
    exit(1);  
  }

  // MESSAGE m;

  while(1)
  {
    // Lecture d'une requete sur le pipe
    // TO DO
    int lecture = read(fdRpipe, &m, sizeof(MESSAGE));

    if (lecture == 0) 
    {
        fprintf(stderr, "(ACCESBD) Fin de la communication par le pipe\n");
        mysql_close(connexion);
        fprintf(stderr, "(ACCESBD) Fermeture de la BD\n");
        exit(1);
    }

    else if (lecture == -1) {
        perror("(ACCESBD) Erreur de lecture dans le pipe");
        exit(1);
    }

    switch(m.requete)
    {
      int tmp;
      case CONSULT :  // TO DO
                      fprintf(stderr,"(ACCESBD %d) Requete CONSULT reçue de %d\n",getpid(),m.expediteur);
                      // Acces BD
                      aEnvoyer = requeteSqlArticle(m.data1);
                      // Preparation de la reponse

                      reponse.type = m.expediteur;
                      reponse.expediteur = getpid();
                      reponse.requete = CONSULT;
                      if (aEnvoyer.stock == -1)
                      {
                        reponse.data1 = aEnvoyer.stock;
                      }

                      else
                      {
                        reponse.data1 = aEnvoyer.id; 
                        strcpy(reponse.data2, aEnvoyer.intitule);
                        strcpy(reponse.data4, aEnvoyer.image);
                        sprintf(buffer,"%d",aEnvoyer.stock);
                        strcpy(reponse.data3, buffer);
                        reponse.data5 = aEnvoyer.prix;
                      }
                     
                      // Envoi de la reponse au bon caddie
                      if (msgsnd(idQ, &reponse, sizeof(MESSAGE) - sizeof(long), 0) == -1)                    
                      {
                        perror("(ACCESBD)Erreur envoi reponse\n");
                      }
                     

                      break;

      case ACHAT :    // TO DO
                      fprintf(stderr,"(ACCESBD %d) Requete ACHAT reçue de %d\n",getpid(),m.expediteur);
                      // Acces BD
                      tmp = requeteAchatArticle(m.data1,(atoi(m.data2)));
                      // Finalisation et envoi de la reponse
                      reponse.type = m.expediteur;
                      reponse.expediteur = getpid();
                      reponse.requete = ACHAT;
                      reponse.data1 = m.data1;
                      strcpy(reponse.data2, aEnvoyer.intitule);
                      sprintf(buffer, "%d", tmp);
                      strcpy(reponse.data3, buffer);
                      strcpy(reponse.data4, aEnvoyer.image);
                      reponse.data5 = aEnvoyer.prix;

                      // Envoi de la reponse au bon caddie
                      if (msgsnd(idQ, &reponse, sizeof(MESSAGE) - sizeof(long), 0) == -1)                    
                      {
                        perror("(ACCESBD)Erreur envoi reponse\n");
                      }

                      break;

      case CANCEL :   // TO DO
                      fprintf(stderr,"(ACCESBD %d) Requete CANCEL reçue de %d\n",getpid(),m.expediteur);
                      // Acces BD

                      // Mise à jour du stock en BD
                      break;

    }
  }
}

ARTICLE requeteSqlArticle(int id)
{
  ARTICLE tmp;
  char requete[256];


// Construire la requête SQL
    sprintf(requete, "SELECT id, intitule, prix, stock, image FROM UNIX_FINAL WHERE id = %d LIMIT 1;", id);

    // Exécuter la requête
    if (mysql_query(connexion, requete) != 0)
    {
        fprintf(stderr, "Erreur de mysql_query: %s\n", mysql_error(connexion));
        exit(1);
    }
    printf("Requête SELECT réussie.\n");

    // Récupérer le résultat
    MYSQL_RES *ResultSet;
    if ((ResultSet = mysql_store_result(connexion)) == NULL)
    {
        fprintf(stderr, "Erreur de mysql_store_result: %s\n", mysql_error(connexion));
        exit(1);
    }

    // Récupérer la première ligne du résultat
    MYSQL_ROW ligne;
    if ((ligne = mysql_fetch_row(ResultSet)) == NULL)
    {
        fprintf(stderr, "Aucune donnée trouvée dans la table.\n");
        mysql_free_result(ResultSet);
        tmp.stock = -1;
        return tmp;
    }

    // Convertir et copier les données dans la structure ARTICLE
    tmp.id = atoi(ligne[0]);
    strncpy(tmp.intitule, ligne[1], sizeof(tmp.intitule) - 1);
    tmp.intitule[sizeof(tmp.intitule) - 1] = '\0'; // Sécurise la chaîne
    tmp.prix = atof(ligne[2]);
    tmp.stock = atoi(ligne[3]);
    strncpy(tmp.image, ligne[4], sizeof(tmp.image) - 1);
    tmp.image[sizeof(tmp.image) - 1] = '\0'; // Sécurise la chaîne

    // Libérer le ResultSet
    mysql_free_result(ResultSet);

    return tmp;
}

int requeteAchatArticle(int id, int quantite)
{   
    char requete[256];
    ARTICLE tmp = requeteSqlArticle(id);
    int articlesDecrementes = 0; // Variable pour stocker le nombre d'articles décrémentés
                                 // Sert de verification de quantité Mise a jour  

    // Si le stock est suffisant pour la quantité demandée
    if (tmp.stock >= quantite)
    {
        // Mettre à jour le stock dans la base de données
        int nouveauStock = tmp.stock - quantite;
        sprintf(requete, "UPDATE UNIX_FINAL SET stock = %d WHERE id = %d;", nouveauStock, id);

        if (mysql_query(connexion, requete) != 0)
        {
            fprintf(stderr, "Erreur de mise à jour du stock: %s\n", mysql_error(connexion));
            return 0; // Erreur de mise à jour
        }

        printf("Stock de l'article %d mis à jour.\n", id);
        articlesDecrementes = quantite; // Le nombre d'articles décrémentés est la quantité demandée
    }

    // Retourner le nombre d'articles décrémentés ou 0 si aucun stock n'a été modifié
    return articlesDecrementes;
}
