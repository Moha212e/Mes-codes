#include <stdio.h>      // Inclusion de la bibliothèque standard d'entrée/sortie
#include <stdlib.h>     // Inclusion de la bibliothèque standard
#include <unistd.h>     // Inclusion de la bibliothèque pour les appels système POSIX
#include <pthread.h>    // Inclusion de la bibliothèque pour la gestion des threads
#include <signal.h>     // Inclusion de la bibliothèque pour la gestion des signaux
#include <time.h>       // Inclusion de la bibliothèque pour la gestion du temps
#include "GrilleSDL.h"  // Inclusion de l'en-tête pour la gestion de la grille SDL
#include "Ressources.h" // Inclusion de l'en-tête pour les ressources
#include <X11/Xlib.h>

// Dimensions de la grille de jeu
#define NB_LIGNE 21   // Nombre de lignes de la grille
#define NB_COLONNE 17 // Nombre de colonnes de la grille

// Macros utilisées dans le tableau tab
#define VIDE 0        // Case vide
#define MUR 1         // Mur
#define PACMAN 2      // Pac-Man
#define PACGOM 3      // Pac-Gom
#define SUPERPACGOM 4 // Super Pac-Gom
#define BONUS 5       // Bonus
#define FANTOME 6     // Fantôme

// Autres macros
#define LENTREE 15 // Ligne d'entrée de Pac-Man
#define CENTREE 8  // Colonne d'entrée de Pac-Man

// Structure représentant un fantôme
typedef struct
{
  int L;       // Ligne du fantôme
  int C;       // Colonne du fantôme
  int couleur; // Couleur du fantôme
  int cache;   // État de la case avant l'arrivée du fantôme
} S_FANTOME;

// Structure représentant une case de la grille
typedef struct
{
  int presence;  // Présence d'un élément dans la case
  pthread_t tid; // Identifiant du thread associé
} S_CASE;

// Tableau représentant la grille de jeu
S_CASE tab[NB_LIGNE][NB_COLONNE];

////////////////////////////////////////////
// Variables globales
////////////////////////////////////////////
pthread_t tidPacMan;                                         // Thread pour Pac-Man
pthread_t tidFantome[4];                                     // Threads pour les fantômes
pthread_t tidBonus;                                          // Thread pour les bonus
pthread_t tidEvent;                                          // Thread pour les événements
pthread_t tidPacGom;                                         // Thread pour les pac-goms
pthread_t tidScore;                                          // Thread pour le score
pthread_t tidCompteurFantomes;                               // Thread pour le compteur de fantômes
pthread_t tidVies;                                           // Thread pour les vies
pthread_t tidTimeOut;                                        // Thread pour le time-out
int L = LENTREE;                                             // Ligne initiale de Pac-Man
int C = CENTREE;                                             // Colonne initiale de Pac-Man
int dir = GAUCHE;                                            // Direction initiale de Pac-Man
int delai = 300;                                             // Délai initial en millisecondes
int nbPacGom = 0;                                            // Nombre de pac-goms restants
int score = 0;                                               // Score du joueur
int niveau = 1;                                              // Niveau actuel
bool MAJScore = false;                                       // Indicateur de mise à jour du score
int nbRouge = 0;                                             // Nombre de fantômes rouges
int nbVert = 0;                                              // Nombre de fantômes verts
int nbMauve = 0;                                             // Nombre de fantômes mauves9
int nbOrange = 0;                                            // Nombre de fantômes oranges
int mode = 1;                                                // 1 = Fantômes normaux, 2 = Fantômes comestibles
int temps_restant = 0;                                       // Temps restant pour le mode Fantômes comestibles
bool JeuFini = true;                                         // Indicateur de fin de jeu
pthread_mutex_t mutexVies = PTHREAD_MUTEX_INITIALIZER;       // Mutex pour les vies
pthread_mutex_t mutexNbFantomes = PTHREAD_MUTEX_INITIALIZER; // Mutex pour le nombre de fantômes
pthread_cond_t condNbFantomes = PTHREAD_COND_INITIALIZER;    // Condition pour le nombre de fantômes
pthread_mutex_t mutexTab = PTHREAD_MUTEX_INITIALIZER;        // Mutex pour le tableau
pthread_mutex_t mutexNbPacGom = PTHREAD_MUTEX_INITIALIZER;   // Mutex pour le nombre de pac-goms
pthread_mutex_t mutexScore = PTHREAD_MUTEX_INITIALIZER;      // Mutex pour le score
pthread_cond_t condNbPacGom = PTHREAD_COND_INITIALIZER;      // Condition pour le nombre de pac-goms
pthread_cond_t condScore = PTHREAD_COND_INITIALIZER;         // Condition pour le score
pthread_mutex_t mutexJeuFini = PTHREAD_MUTEX_INITIALIZER;    // Mutex pour la fin de jeu
pthread_mutex_t mutexMode = PTHREAD_MUTEX_INITIALIZER;       // Mutex pour le mode
pthread_key_t fantome_key;                                   // Clé pour les données spécifiques au thread
/////////////////////
// Fonctions à coder
/////////////////////
void *PacMan(void *arg);                                           // Fonction pour le thread Pac-Man
void *Event(void *arg);                                            // Fonction pour le thread des événements
void *PacGom(void *arg);                                           // Fonction pour le thread des pac-goms
void *Score(void *arg);                                            // Fonction pour le thread du score
void *Bonus(void *arg);                                            // Fonction pour le thread des bonus
void *Fantome(void *arg);                                          // Fonction pour le thread des fantômes
void *CompteurFantome(void *arg);                                  // Fonction pour le thread du compteur de fantômes
void *Vies(void *arg);                                             // Fonction pour le thread des vies
void DessineGrilleBase();                                          // Fonction pour dessiner la grille de base
void Attente(int milli);                                           // Fonction pour attendre un certain temps en millisecondes
void setTab(int l, int c, int presence = VIDE, pthread_t tid = 0); // Fonction pour mettre à jour le tableau tab
void handler_SIGINT(int sig);                                      // Gestionnaire pour le signal SIGINT
void handler_SIGHUP(int sig);                                      // Gestionnaire pour le signal SIGHUP
void handler_SIGUSR1(int sig);                                     // Gestionnaire pour le signal SIGUSR1
void handler_SIGUSR2(int sig);                                     // Gestionnaire pour le signal SIGUSR2
void handler_SIGALRM(int sig);                                     // Gestionnaire pour le signal SIGUSR2
void *threadTimeOut(void *arg);                                    // Déclaration anticipée de la fonction
///////////////////////////////////////////////////////////////////////////////////////////////////
int main(int argc, char *argv[])
{
  sigset_t mask;                      // Masque de signaux
  struct sigaction sigAct;            // Structure pour les actions de signaux
  sigAct.sa_handler = handler_SIGINT; // Gestionnaire pour SIGINT
  sigemptyset(&sigAct.sa_mask);       // Initialisation du masque de signaux
  sigAct.sa_flags = 0;                // Pas de flags
  sigaction(SIGINT, &sigAct, NULL);   // Définir l'action pour SIGINT

  sigAct.sa_handler = handler_SIGHUP; // Gestionnaire pour SIGHUP
  sigaction(SIGHUP, &sigAct, NULL);   // Définir l'action pour SIGHUP

  sigAct.sa_handler = handler_SIGUSR1; // Gestionnaire pour SIGUSR1
  sigaction(SIGUSR1, &sigAct, NULL);   // Définir l'action pour SIGUSR1

  sigAct.sa_handler = handler_SIGUSR2; // Gestionnaire pour SIGUSR2
  sigaction(SIGUSR2, &sigAct, NULL);   // Définir l'action pour SIGUSR2
  srand((unsigned)time(NULL));         // Initialiser le générateur de nombres aléatoires

  // Ouverture de la fenêtre graphique
  printf("(MAIN %p) Ouverture de la fenêtre graphique\n", pthread_self());
  fflush(stdout);
  if (OuvertureFenetreGraphique() < 0)
  {
    printf("Erreur de OuvrirGrilleSDL\n");
    fflush(stdout);
    exit(1);
  }

  DessineGrilleBase(); // Dessiner la grille de base

  // Création des threads
  pthread_create(&tidPacGom, NULL, PacGom, NULL);
  pthread_create(&tidEvent, NULL, Event, NULL);
  pthread_create(&tidScore, NULL, Score, NULL);
  pthread_create(&tidBonus, NULL, Bonus, NULL); // Ajout du thread Bonus
  pthread_create(&tidCompteurFantomes, NULL, CompteurFantome, NULL);
  pthread_create(&tidVies, NULL, Vies, NULL); // Lancer le thread Vies

  // Attente de la fin des threads
  pthread_join(tidEvent, NULL);
  pthread_join(tidPacGom, NULL);
  pthread_join(tidScore, NULL);
  pthread_join(tidBonus, NULL);
  pthread_join(tidCompteurFantomes, NULL);
  pthread_join(tidVies, NULL); // Attendre la fin du thread Vies

  printf("attendre 1500 millisecondes");
  Attente(1500); // Attendre 1,5 seconde

  // Fermeture de la fenêtre
  printf("(MAIN %p) Fermeture de la fenêtre graphique...", pthread_self());
  pthread_mutex_destroy(&mutexTab);
  pthread_mutex_destroy(&mutexNbPacGom);
  pthread_mutex_destroy(&mutexScore);
  pthread_cond_destroy(&condNbPacGom);
  pthread_cond_destroy(&condScore);
  pthread_key_delete(fantome_key);

  fflush(stdout);
  FermetureFenetreGraphique();
  printf("OK\n");
  fflush(stdout);

  exit(0);
}
//*********************************************************************************************
void Attente(int milli)
{
  struct timespec del;                    // Déclare une structure pour le délai
  del.tv_sec = milli / 1000;              // Calcule les secondes à partir des millisecondes
  del.tv_nsec = (milli % 1000) * 1000000; // Calcule les nanosecondes à partir des millisecondes restantes
  nanosleep(&del, NULL);                  // Attend le délai spécifié
}
//*********************************************************************************************
void setTab(int l, int c, int presence, pthread_t tid)
{
  tab[l][c].presence = presence; // Met à jour la présence dans la case spécifiée
  tab[l][c].tid = tid;           // Met à jour l'identifiant du thread dans la case spécifiée
}
//*********************************************************************************************
void DessineGrilleBase()
{
  int t[NB_LIGNE][NB_COLONNE] = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                 {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                                 {1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1},
                                 {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                                 {1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1},
                                 {1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1},
                                 {1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1},
                                 {1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1},
                                 {1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1},
                                 {0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                                 {1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1},
                                 {1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1},
                                 {1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1},
                                 {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                                 {1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1},
                                 {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
                                 {1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
                                 {1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1},
                                 {1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1},
                                 {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                                 {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}}; // Déclare et initialise la grille de base

  for (int l = 0; l < NB_LIGNE; l++)     // Parcourt les lignes de la grille
    for (int c = 0; c < NB_COLONNE; c++) // Parcourt les colonnes de la grille
    {
      if (t[l][c] == VIDE) // Si la case est vide
      {
        setTab(l, c);      // Met à jour la case pour indiquer qu'elle est vide
        EffaceCarre(l, c); // Efface le carré à cette position
      }
      if (t[l][c] == MUR) // Si la case est un mur
      {
        setTab(l, c, MUR); // Met à jour la case pour indiquer qu'elle contient un mur
        DessineMur(l, c);  // Dessine un mur à cette position
      }
    }
}
//*********************************************************************************************
void *threadTimeOut(void *arg)
{
  int temps = *(int *)arg;
  free(arg);

  // Définir le gestionnaire pour SIGALRM
  struct sigaction sa;
  sa.sa_handler = handler_SIGALRM;
  sigemptyset(&sa.sa_mask);
  sa.sa_flags = 0;
  sigaction(SIGALRM, &sa, NULL);

  // Définir l'alarme
  alarm(temps);

  // Attendre que l'alarme se déclenche
  pause();

  pthread_exit(NULL);
}
//*********************************************************************************************
void *PacMan(void *arg)
{
  struct sigaction sa;    // Déclare une structure pour les actions de signaux
  sigset_t mask, oldmask; // Déclare des masques de signaux
  printf("[DEBUG] Démarrage du thread PacMan\n");

  while (JeuFini) // Boucle infinie pour déplacer Pac-Man
  {
    printf("[DEBUG] Pac-Man en mouvement - Position: (%d, %d)\n", L, C);
    sigemptyset(&mask);
    sigaddset(&mask, SIGINT);
    sigaddset(&mask, SIGHUP);
    sigaddset(&mask, SIGUSR1);
    sigaddset(&mask, SIGUSR2);
    pthread_sigmask(SIG_BLOCK, &mask, &oldmask);

    pthread_mutex_lock(&mutexTab);
    int localDelai = delai;
    pthread_mutex_unlock(&mutexTab);
    Attente(localDelai);

    pthread_sigmask(SIG_SETMASK, &oldmask, NULL);

    pthread_mutex_lock(&mutexTab);
    int newL = L, newC = C;
    switch (dir)
    {
    case GAUCHE:
      if (tab[L][C - 1].presence != MUR)
        newC--;
      break;
    case DROITE:
      if (tab[L][C + 1].presence != MUR)
        newC++;
      break;
    case HAUT:
      if (tab[L - 1][C].presence != MUR)
        newL--;
      break;
    case BAS:
      if (tab[L + 1][C].presence != MUR)
        newL++;
      break;
    }

    EffaceCarre(L, C);
    setTab(L, C, VIDE);

    if (newL != L || newC != C)
    {
      L = newL;
      C = newC;
    }

    if (tab[L][C].presence == PACGOM)
    {
      pthread_mutex_lock(&mutexScore);
      score += 1;
      nbPacGom--;
      MAJScore = true;
      pthread_cond_signal(&condScore);
      pthread_mutex_unlock(&mutexScore);
      pthread_cond_signal(&condNbPacGom);
    }
    else if (tab[L][C].presence == SUPERPACGOM)
    {
      pthread_mutex_lock(&mutexScore);
      score += 5;
      nbPacGom--;
      MAJScore = true;
      pthread_cond_signal(&condScore);
      pthread_mutex_unlock(&mutexScore);
      pthread_cond_signal(&condNbPacGom);

      pthread_mutex_lock(&mutexMode);
      mode = 2;
      printf("[INFO] Mode Fantômes comestibles activé !\n");
      pthread_mutex_unlock(&mutexMode);

      int *duree = (int *)malloc(sizeof(int));
      *duree = 8 + rand() % 8;
      if (temps_restant > 0)
      {
        *duree += temps_restant;
        pthread_cancel(tidTimeOut);
      }
      temps_restant = *duree;
      pthread_create(&tidTimeOut, NULL, threadTimeOut, duree);
    }
    else if (tab[L][C].presence == BONUS)
    {
      pthread_mutex_lock(&mutexScore);
      score += 30;
      MAJScore = true;
      pthread_cond_signal(&condScore);
      pthread_mutex_unlock(&mutexScore);
    }

    else if (tab[L][C].presence == FANTOME && mode == 1)
    {
      printf("Pac-Man a été mangé par un fantôme !\n");
      setTab(L, C, VIDE, 0);
      EffaceCarre(L, C);
      pthread_mutex_unlock(&mutexTab);
      pthread_exit(NULL);
    }
    else if (tab[L][C].presence == FANTOME && mode == 2)
    {
      printf("Pac-Man a mangé un fantôme !\n");
      // Effacer le fantôme
      EffaceCarre(L, C);
      setTab(L, C, VIDE, 0);
      // Réduire le nombre de fantômes
      pthread_mutex_lock(&mutexNbFantomes);

      score += 50;
      MAJScore = true;
      pthread_cond_signal(&condScore);
      pthread_mutex_unlock(&mutexNbFantomes);
      pthread_cond_signal(&condNbFantomes);
    }
    DessinePacMan(L, C, dir);

    DessinePacMan(L, C, dir);
    setTab(L, C, PACMAN);

    pthread_mutex_unlock(&mutexTab);

    if (!JeuFini)
    {
      pthread_exit(NULL);
    }
  }
  return NULL;
}
//*********************************************************************************************
void *Event(void *arg)
{
  EVENT_GRILLE_SDL event; // Déclaration d'une variable pour les événements de la grille SDL
  int ok = 0;             // Variable de contrôle pour la boucle

  while (!ok) // Boucle principale pour gérer les événements
  {
    event = ReadEvent();     // Lire un événement de la grille SDL
    if (event.type == CROIX) // Si l'événement est de type CROIX (fermeture de la fenêtre)
    {
      ok = 1; // Mettre à jour la variable de contrôle pour sortir de la boucle
      JeuFini = false;
      pthread_exit(NULL); // Terminer le thread Event
    }
    if (event.type == CLAVIER) // Si l'événement est de type CLAVIER (appui sur une touche)
    {
      switch (event.touche) // Vérifier quelle touche a été pressée
      {
      case 'q': // Si la touche 'q' est pressée
        ok = 1; // Mettre à jour la variable de contrôle pour sortir de la boucle
        JeuFini = false;
        break;
      case KEY_RIGHT:                    // Si la touche flèche droite est pressée
        printf("Fleche droite !\n");     // Afficher un message
        pthread_kill(tidPacMan, SIGHUP); // Envoyer le signal SIGHUP au thread PacMan
        break;
      case KEY_LEFT:                     // Si la touche flèche gauche est pressée
        printf("Fleche gauche !\n");     // Afficher un message
        pthread_kill(tidPacMan, SIGINT); // Envoyer le signal SIGINT au thread PacMan
        break;
      case KEY_UP:                        // Si la touche flèche haut est pressée
        printf("Fleche haut !\n");        // Afficher un message
        pthread_kill(tidPacMan, SIGUSR1); // Envoyer le signal SIGUSR1 au thread PacMan
        break;
      case KEY_DOWN:                      // Si la touche flèche bas est pressée
        printf("Fleche bas !\n");         // Afficher un message
        pthread_kill(tidPacMan, SIGUSR2); // Envoyer le signal SIGUSR2 au thread PacMan
        break;
      }
    }
  }

  return NULL; // Retourner NULL à la fin de la fonction
}
//*********************************************************************************************
void handler_SIGINT(int sig)
{
  dir = GAUCHE; // Change la direction de Pac-Man vers la gauche
}
//*********************************************************************************************
void handler_SIGHUP(int sig)
{
  dir = DROITE; // Change la direction de Pac-Man vers la droite
}
//*********************************************************************************************
void handler_SIGUSR1(int sig)
{
  dir = HAUT; // Change la direction de Pac-Man vers le haut
}
//*********************************************************************************************
void handler_SIGUSR2(int sig)
{
  dir = BAS; // Change la direction de Pac-Man vers le bas
}
void handler_SIGALRM(int sig)
{
  pthread_mutex_lock(&mutexMode);
  mode = 1;
  temps_restant = 0;
  printf("[INFO] Mode normal réactivé (Fantômes normaux)\n");
  pthread_mutex_unlock(&mutexMode);
}
//*********************************************************************************************
void *PacGom(void *arg)
{
  DessineChiffre(14, 22, niveau); // Dessine le chiffre du niveau

  while (JeuFini) // Boucle infinie pour gérer les pac-goms
  {
    pthread_mutex_lock(&mutexTab); // Verrouille le mutex pour accéder au tableau
    nbPacGom = 0;                  // Réinitialise le nombre de pac-goms

    // Remplir la grille de pac-goms
    for (int l = 0; l < NB_LIGNE; l++) // Parcourt les lignes de la grille
    {
      for (int c = 0; c < NB_COLONNE; c++) // Parcourt les colonnes de la grille
      {
        if ((l == 15 && c == 8) || (l == 8 && c == 8) || (l == 9 && c == 8)) // Ignore certaines cases spécifiques
          continue;

        if (tab[l][c].presence == VIDE) // Vérifie si la case est vide
        {
          setTab(l, c, PACGOM); // Met à jour la case pour indiquer la présence d'un pac-gom
          DessinePacGom(l, c);  // Dessine un pac-gom à cette position
          nbPacGom++;           // Incrémente le nombre de pac-goms
        }
      }
    }

    // Placer les super pac-goms
    setTab(2, 1, SUPERPACGOM);   // Place un super pac-gom à la position (2, 1)
    DessineSuperPacGom(2, 1);    // Dessine un super pac-gom à cette position
    setTab(2, 15, SUPERPACGOM);  // Place un super pac-gom à la position (2, 15)
    DessineSuperPacGom(2, 15);   // Dessine un super pac-gom à cette position
    setTab(15, 1, SUPERPACGOM);  // Place un super pac-gom à la position (15, 1)
    DessineSuperPacGom(15, 1);   // Dessine un super pac-gom à cette position
    setTab(15, 15, SUPERPACGOM); // Place un super pac-gom à la position (15, 15)
    DessineSuperPacGom(15, 15);  // Dessine un super pac-gom à cette position
    nbPacGom += 2;               // Incrémente le nombre de pac-goms de 4

    pthread_mutex_unlock(&mutexTab); // Déverrouille le mutex

    // Attendre que tous les pac-goms soient mangés
    pthread_mutex_lock(&mutexNbPacGom); // Verrouille le mutex pour accéder au nombre de pac-goms
    while (nbPacGom != 0)               // Boucle tant qu'il reste des pac-goms
    {

      pthread_cond_wait(&condNbPacGom, &mutexNbPacGom); // Attend la condition de changement du nombre de pac-goms
      DessineChiffre(12, 22, nbPacGom / 100);           // Dessine le chiffre des centaines du nombre de pac-goms
      DessineChiffre(12, 23, (nbPacGom / 10) % 10);     // Dessine le chiffre des dizaines du nombre de pac-goms
      DessineChiffre(12, 24, nbPacGom % 10);            // Dessine le chiffre des unités du nombre de pac-goms
    }
    pthread_mutex_unlock(&mutexNbPacGom); // Déverrouille le mutex

    // Incrémenter le niveau et mettre à jour la fenêtre graphique
    niveau++;                       // Incrémente le niveau
    DessineChiffre(14, 22, niveau); // Dessine le chiffre du niveau

    // Doubler la vitesse de Pac-Man
    pthread_mutex_lock(&mutexTab);   // Verrouille le mutex pour accéder au tableau
    delai /= 2;                      // Divise le délai par 2 pour augmenter la vitesse de Pac-Man
    pthread_mutex_unlock(&mutexTab); // Déverrouille le mutex
  }

  return NULL; // Retourne NULL à la fin de la fonction
}
//*********************************************************************************************
void *Score(void *arg)
{
  while (JeuFini) // Boucle infinie pour gérer le score
  {
    pthread_mutex_lock(&mutexScore); // Verrouille le mutex pour accéder au score
    while (!MAJScore)                // Boucle tant que le score n'a pas besoin d'être mis à jour
    {
      pthread_cond_wait(&condScore, &mutexScore); // Attend la condition de mise à jour du score
    }

    // Afficher le score dans les cases (16,22), (16,23), (16,24) et (16,25)
    DessineChiffre(16, 22, score / 1000);       // Dessine le chiffre des milliers du score
    DessineChiffre(16, 23, (score / 100) % 10); // Dessine le chiffre des centaines du score
    DessineChiffre(16, 24, (score / 10) % 10);  // Dessine le chiffre des dizaines du score
    DessineChiffre(16, 25, score % 10);         // Dessine le chiffre des unités du score

    MAJScore = false;                  // Réinitialise l'indicateur de mise à jour du score
    pthread_mutex_unlock(&mutexScore); // Déverrouille le mutex
  }

  return NULL; // Retourne NULL à la fin de la fonction
}
//*********************************************************************************************
void *Bonus(void *arg)
{
  while (JeuFini) // Boucle infinie pour gérer les bonus
  {
    // Attente d'un laps de temps aléatoire entre 10 et 20 secondes
    int waitTime = 10000 + rand() % 10000; // Calcule un temps d'attente aléatoire entre 10 et 20 secondes
    Attente(waitTime);                     // Attend le temps calculé

    pthread_mutex_lock(&mutexTab); // Verrouille le mutex pour accéder au tableau

    // Recherche d'une case libre pour déposer le bonus
    int l, c; // Déclare les variables pour les coordonnées
    do
    {
      l = rand() % NB_LIGNE;   // Calcule une ligne aléatoire
      c = rand() % NB_COLONNE; // Calcule une colonne aléatoire
    } while (tab[l][c].presence != VIDE); // Répète tant que la case n'est pas vide

    // Dépose le bonus
    setTab(l, c, BONUS); // Met à jour la case pour indiquer la présence d'un bonus
    DessineBonus(l, c);  // Dessine un bonus à cette position

    pthread_mutex_unlock(&mutexTab); // Déverrouille le mutex

    // Attente de 10 secondes
    Attente(10000); // Attend 10 secondes

    pthread_mutex_lock(&mutexTab); // Verrouille le mutex pour accéder au tableau

    // Vérifie si le bonus est toujours présent
    if (tab[l][c].presence == BONUS) // Vérifie si la case contient toujours un bonus
    {
      // Retire le bonus
      setTab(l, c, VIDE); // Met à jour la case pour indiquer qu'elle est vide
      EffaceCarre(l, c);  // Efface le carré à cette position
    }

    pthread_mutex_unlock(&mutexTab); // Déverrouille le mutex
  }

  return NULL; // Retourne NULL à la fin de la fonction
}

//*********************************************************************************************
void *Fantome(void *arg)
{
  S_FANTOME *fantome = (S_FANTOME *)arg;
  if (!fantome)
  {
    fprintf(stderr, "Erreur : pointeur fantôme NULL\n");
    pthread_exit(NULL);
  }

  printf("[DEBUG] Démarrage du fantôme - Position: (%d, %d)\n", fantome->L, fantome->C);
  int dir = HAUT;

  pthread_mutex_lock(&mutexTab);
  fantome->cache = tab[fantome->L][fantome->C].presence;
  setTab(fantome->L, fantome->C, FANTOME, pthread_self());
  DessineFantome(fantome->L, fantome->C, fantome->couleur, dir);
  pthread_mutex_unlock(&mutexTab);

  while (JeuFini)
  {
    pthread_mutex_lock(&mutexMode);
    int currentMode = mode;
    pthread_mutex_unlock(&mutexMode);

    int newL = fantome->L, newC = fantome->C;
    int directions[4] = {HAUT, BAS, GAUCHE, DROITE};
    int validDirections[4];
    int count = 0;

    for (int i = 0; i < 4; i++)
    {
      int testL = fantome->L, testC = fantome->C;
      switch (directions[i])
      {
      case HAUT:
        testL--;
        break;
      case BAS:
        testL++;
        break;
      case GAUCHE:
        testC--;
        break;
      case DROITE:
        testC++;
        break;
      }
      if (testL >= 0 && testL < NB_LIGNE && testC >= 0 && testC < NB_COLONNE && tab[testL][testC].presence != MUR)
      {
        validDirections[count++] = directions[i];
      }
    }

    if (count > 0)
    {
      dir = validDirections[rand() % count];
    }

    switch (dir)
    {
    case HAUT:
      newL--;
      break;
    case BAS:
      newL++;
      break;
    case GAUCHE:
      newC--;
      break;
    case DROITE:
      newC++;
      break;
    }

    pthread_mutex_lock(&mutexTab);
    if (newL >= 0 && newL < NB_LIGNE && newC >= 0 && newC < NB_COLONNE && tab[newL][newC].presence != MUR)
    {
      if (tab[newL][newC].presence == PACMAN)
      {
        if (currentMode == 1)
        {
          printf("Pac-Man a été mangé par un fantôme !\n");

          pthread_mutex_lock(&mutexVies);
          pthread_cancel(tidPacMan); // Annule Pac-Man UNIQUEMENT si nécessaire
          pthread_mutex_unlock(&mutexVies);

          // Libération de la mutex avant de quitter proprement
          pthread_mutex_unlock(&mutexTab);

          // Quitter proprement le thread du fantôme
          pthread_exit(NULL);
        }
        else
        {
          printf("Pac-Man a mangé un fantôme !\n");

          // Effacer le fantôme immédiatement
          EffaceCarre(fantome->L, fantome->C);
          setTab(fantome->L, fantome->C, VIDE, 0);

          // Lancer un thread pour le faire réapparaître après 3 secondes
          pthread_t tidRespawn;
          S_FANTOME *newFantome = (S_FANTOME *)malloc(sizeof(S_FANTOME));
          if (!newFantome)
          {
            fprintf(stderr, "Erreur d'allocation mémoire pour le fantôme réapparaissant\n");
            pthread_mutex_unlock(&mutexTab);
            pthread_exit(NULL); // Quitter proprement en cas d'erreur d'allocation
          }

          newFantome->L = 9;                      // Position de réapparition
          newFantome->C = 8;                      // Position de réapparition
          newFantome->couleur = fantome->couleur; // Conserver la même couleur

          // Créer le thread pour réapparaître le fantôme
          if (pthread_create(&tidRespawn, NULL, Fantome, newFantome) != 0)
          {
            fprintf(stderr, "Erreur lors de la création du thread pour réapparaître le fantôme\n");
            free(newFantome); // Libérer la mémoire si la création échoue
            pthread_mutex_unlock(&mutexTab);
            pthread_exit(NULL); // Quitter proprement si échec
          }

          // Libérer la mémoire du fantôme mangé
          free(fantome);

          // Libération de la mutex avant de quitter
          pthread_mutex_unlock(&mutexTab);

          // Quitter proprement le thread du fantôme
          pthread_exit(NULL);
        }
      }

      setTab(fantome->L, fantome->C, fantome->cache, 0);
      EffaceCarre(fantome->L, fantome->C);
      fantome->cache = tab[newL][newC].presence;
      fantome->L = newL;
      fantome->C = newC;
      setTab(fantome->L, fantome->C, FANTOME, pthread_self());

      // Affichage correct selon le mode
      if (currentMode == 2)
      {
        DessineFantomeComestible(fantome->L, fantome->C);
      }
      else
      {
        DessineFantome(fantome->L, fantome->C, fantome->couleur, dir);
      }
    }
    pthread_mutex_unlock(&mutexTab);

    Attente((5 * delai) / 3);
  }

  free(fantome);
  return NULL;
}
//*********************************************************************************************
void *CompteurFantome(void *arg)
{
  while (JeuFini)
  {
    pthread_mutex_lock(&mutexNbFantomes);

    // Attente si le mode est 2 (empêche la création de nouveaux fantômes)
    while (mode == 2 || nbRouge == 2 && nbVert == 2 && nbMauve == 2 && nbOrange == 2)
    {
      pthread_cond_wait(&condNbFantomes, &mutexNbFantomes);
    }

    // Allocation mémoire pour le nouveau fantôme
    S_FANTOME *fantome = (S_FANTOME *)malloc(sizeof(S_FANTOME));
    if (!fantome)
    {
      fprintf(stderr, "Erreur d'allocation mémoire\n");
      pthread_mutex_unlock(&mutexNbFantomes);
      pthread_exit(NULL); // Si l'allocation échoue, on quitte le thread
    }

    // Initialisation des coordonnées et de la couleur du fantôme
    fantome->L = 9;
    fantome->C = 8;
    fantome->cache = VIDE;

    // Sélection de la couleur du fantôme et mise à jour des compteurs
    if (nbRouge < 2)
    {
      fantome->couleur = ROUGE;
      nbRouge++;
    }
    else if (nbVert < 2)
    {
      fantome->couleur = VERT;
      nbVert++;
    }
    else if (nbMauve < 2)
    {
      fantome->couleur = MAUVE;
      nbMauve++;
    }
    else if (nbOrange < 2)
    {
      fantome->couleur = ORANGE;
      nbOrange++;
    }

    // Affichage du nombre de fantômes après l'ajout du nouveau
    printf("Nombre de fantômes - ROUGE: %d, VERT: %d, MAUVE: %d, ORANGE: %d\n", nbRouge, nbVert, nbMauve, nbOrange);
    if (mode == 2)
    {
      pthread_mutex_lock(&mutexNbFantomes);
      // Mise à jour de l'état de la case dans le tableau `tab` en fonction des coordonnées du fantôme
      switch (fantome->couleur)
      {
      case ROUGE:
        nbRouge--;
        break;
      case VERT:
        nbVert--;
        break;
      case MAUVE:
        nbMauve--;
        break;
      case ORANGE:
        nbOrange--;
        break;
      }

      // Effacer le fantôme du tableau de jeu
      EffaceCarre(fantome->L, fantome->C);
      setTab(fantome->L, fantome->C, VIDE, 0); // Mise à jour de la case à VIDE
      printf("Après traitement - ROUGE: %d, VERT: %d, MAUVE: %d, ORANGE: %d\n", nbRouge, nbVert, nbMauve, nbOrange);
    }
    // Création du thread pour le fantôme
    pthread_t tid;
    if (pthread_create(&tid, NULL, Fantome, fantome) != 0)
    {
      fprintf(stderr, "Erreur lors de la création du thread fantôme\n");
      free(fantome); // Libérer la mémoire en cas d'échec
      pthread_mutex_unlock(&mutexNbFantomes);
      pthread_exit(NULL); // Quitter proprement en cas d'échec
    }

    // Simuler la suppression d'un fantôme dans le cas où le mode est 2

    // Affichage du nombre de fantômes après suppression ou ajout

    // Déverrouillage de la mutex avant de quitter
    pthread_mutex_unlock(&mutexNbFantomes);
  }

  return NULL;
}
//*********************************************************************************************
void *Vies(void *arg)
{
  int vies = 3;
  printf("[DEBUG] Début du thread Vies : %d vies restantes\n", vies);
  DessineChiffre(18, 22, vies);
  while (vies > 0)
  {
    if (mode == 1)
    {
      printf("[DEBUG] Nouvelle vie, vies restantes : %d\n", vies);

      pthread_create(&tidPacMan, NULL, PacMan, NULL);
      pthread_join(tidPacMan, NULL);

      pthread_mutex_lock(&mutexVies);
      vies--;
      DessineChiffre(18, 22, vies);
      printf("[DEBUG] Vie perdue, vies restantes : %d\n", vies);
      pthread_mutex_unlock(&mutexVies);

      pthread_mutex_lock(&mutexTab);
      EffaceCarre(L, C);
      setTab(L, C, VIDE);
      L = LENTREE;
      C = CENTREE;
      DessinePacMan(L, C, dir);
      setTab(L, C, PACMAN);
      pthread_mutex_unlock(&mutexTab);
    }
  }

  printf("[DEBUG] Plus de vies, fin du jeu - Annulation des threads...\n");
  DessineChiffre(18, 22, vies);

  JeuFini = false;
  printf("[DEBUG] Annulation des threads en cours...\n");

  pthread_cancel(tidPacGom);
  pthread_cancel(tidScore);
  pthread_cancel(tidBonus);
  pthread_cancel(tidCompteurFantomes);

  printf("[DEBUG] Tous les threads ont été arrêtés.\n");

  DessineGameOver(9, 4);
  pthread_exit(NULL);
}