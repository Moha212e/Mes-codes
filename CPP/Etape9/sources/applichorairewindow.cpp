#include "applichorairewindow.h"
#include "ui_applichorairewindow.h"
#include "unistd.h"
#include "Timetable.h"
#include <QDesktopWidget>
#include <QMessageBox>
#include <QInputDialog>
#include <QFileDialog>
#include <iostream>
#include <sstream>
using namespace std;



ApplicHoraireWindow::ApplicHoraireWindow(QWidget *parent) : QMainWindow(parent), ui(new Ui::ApplicHoraireWindow)
{
    ui->setupUi(this);
    ::close(2);

    // Configuration de la table des professeurs
    ui->tableWidgetProfesseurs->setColumnCount(3);
    ui->tableWidgetProfesseurs->setRowCount(0);
    QStringList labelsTableProfesseurs;
    labelsTableProfesseurs << "id" << "Nom" << "Prenom";
    ui->tableWidgetProfesseurs->setHorizontalHeaderLabels(labelsTableProfesseurs);
    ui->tableWidgetProfesseurs->setSelectionMode(QAbstractItemView::SingleSelection);
    ui->tableWidgetProfesseurs->setSelectionBehavior(QAbstractItemView::SelectRows);
    ui->tableWidgetProfesseurs->horizontalHeader()->setVisible(true);
    ui->tableWidgetProfesseurs->horizontalHeader()->setDefaultSectionSize(100);
    ui->tableWidgetProfesseurs->horizontalHeader()->setStretchLastSection(true);
    ui->tableWidgetProfesseurs->verticalHeader()->setVisible(false);
    ui->tableWidgetProfesseurs->horizontalHeader()->setStyleSheet("background-color: lightyellow");

    // Configuration de la table des groupes
    ui->tableWidgetGroupes->setColumnCount(2);
    ui->tableWidgetGroupes->setRowCount(0);
    QStringList labelsTableGroupes;
    labelsTableGroupes << "id" << "Nom";
    ui->tableWidgetGroupes->setHorizontalHeaderLabels(labelsTableGroupes);
    ui->tableWidgetGroupes->setSelectionMode(QAbstractItemView::MultiSelection);
    ui->tableWidgetGroupes->setSelectionBehavior(QAbstractItemView::SelectRows);
    ui->tableWidgetGroupes->horizontalHeader()->setVisible(true);
    ui->tableWidgetGroupes->horizontalHeader()->setDefaultSectionSize(100);
    ui->tableWidgetGroupes->horizontalHeader()->setStretchLastSection(true);
    ui->tableWidgetGroupes->verticalHeader()->setVisible(false);
    ui->tableWidgetGroupes->horizontalHeader()->setStyleSheet("background-color: lightyellow");

    // Configuration de la table des locaux
    ui->tableWidgetLocaux->setColumnCount(2);
    ui->tableWidgetLocaux->setRowCount(0);
    QStringList labelsTableLocaux;
    labelsTableLocaux << "id" << "Nom";
    ui->tableWidgetLocaux->setHorizontalHeaderLabels(labelsTableLocaux);
    ui->tableWidgetLocaux->setSelectionMode(QAbstractItemView::SingleSelection);
    ui->tableWidgetLocaux->setSelectionBehavior(QAbstractItemView::SelectRows);
    ui->tableWidgetLocaux->horizontalHeader()->setVisible(true);
    ui->tableWidgetLocaux->horizontalHeader()->setDefaultSectionSize(100);
    ui->tableWidgetLocaux->horizontalHeader()->setStretchLastSection(true);
    ui->tableWidgetLocaux->verticalHeader()->setVisible(false);
    ui->tableWidgetLocaux->horizontalHeader()->setStyleSheet("background-color: lightyellow");

    // Configuration de la table des cours
    ui->tableWidgetCourss->setColumnCount(8);
    ui->tableWidgetCourss->setRowCount(0);
    QStringList labelsTableCourss;
    labelsTableCourss << "code" << "Jour" << "Heure" << "Duree" << "Local" << "Intitule" << "Professeur" << "Groupes";
    ui->tableWidgetCourss->setHorizontalHeaderLabels(labelsTableCourss);
    ui->tableWidgetCourss->setSelectionMode(QAbstractItemView::NoSelection);
    //ui->tableWidgetCourss->setSelectionBehavior(QAbstractItemView::SelectRows);
    ui->tableWidgetCourss->horizontalHeader()->setVisible(true);
    ui->tableWidgetCourss->horizontalHeader()->setDefaultSectionSize(100);
    ui->tableWidgetCourss->horizontalHeader()->setStretchLastSection(true);
    ui->tableWidgetCourss->verticalHeader()->setVisible(false);
    ui->tableWidgetCourss->horizontalHeader()->setStyleSheet("background-color: lightyellow");

    // Pour faire joli
    ui->pushButtonAjouterProfesseur->setStyleSheet("background-color: lightgreen");
    ui->pushButtonAjouterGroupe->setStyleSheet("background-color: lightgreen");
    ui->pushButtonAjouterLocal->setStyleSheet("background-color: lightgreen");
    ui->pushButtonSupprimerProfesseur->setStyleSheet("background-color: orange");
    ui->pushButtonSupprimerGroupe->setStyleSheet("background-color: orange");
    ui->pushButtonSupprimerLocal->setStyleSheet("background-color: orange");
    ui->pushButtonPlanifier->setStyleSheet("background-color: lightblue");
    ui->pushButtonSelectionner->setStyleSheet("background-color: lightblue");

    // On fixe la taille et la position de la fenetre
    setFixedSize(1070,647);
    move((QApplication::desktop()->width()-width())/2,(QApplication::desktop()->height()-height())/2);
    // -------------------------------------------------------------------
}

ApplicHoraireWindow::~ApplicHoraireWindow() {
    delete ui;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// Fonctions utiles Table des professeurs /////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::addTupleTableProfessors(const string& tuple) {
    // Parsing du tuple recu
    istringstream ss(tuple);
    string token;

    string id, lastName, firstName;
    getline(ss,id,';');
    getline(ss,lastName,';');
    getline(ss,firstName,';');

    // Ajout dans la table
    int nbLignes = ui->tableWidgetProfesseurs->rowCount();
    nbLignes++;
    ui->tableWidgetProfesseurs->setRowCount(nbLignes);

    QTableWidgetItem *item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setTextAlignment(Qt::AlignCenter);
    item->setText(QString::fromStdString(id));
    ui->tableWidgetProfesseurs->setItem(nbLignes-1,0,item);

    item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setText(QString::fromStdString(lastName));
    ui->tableWidgetProfesseurs->setItem(nbLignes-1,1,item);

    item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setText(QString::fromStdString(firstName));
    ui->tableWidgetProfesseurs->setItem(nbLignes-1,2,item);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::clearTableProfessors() {
    ui->tableWidgetProfesseurs->setRowCount(0);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
int ApplicHoraireWindow::getIndexProfessorSelection() const {
    QModelIndexList liste = ui->tableWidgetProfesseurs->selectionModel()->selectedRows();
    if (liste.size() == 0) return -1;
    QModelIndex index = liste.at(0);
    int indice = index.row();
    return indice;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// Fonctions utiles Table des groupes /////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::addTupleTableGroups(const string& tuple) {
    // Parsing du tuple recu
    istringstream ss(tuple);
    string token;

    string id, name;
    getline(ss,id,';');
    getline(ss,name,';');

    // Ajout possible
    int nbLignes = ui->tableWidgetGroupes->rowCount();
    nbLignes++;
    ui->tableWidgetGroupes->setRowCount(nbLignes);

    QTableWidgetItem *item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setTextAlignment(Qt::AlignCenter);
    item->setText(QString::fromStdString(id));
    ui->tableWidgetGroupes->setItem(nbLignes-1,0,item);

    item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setText(QString::fromStdString(name));
    ui->tableWidgetGroupes->setItem(nbLignes-1,1,item);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::clearTableGroups() {
    ui->tableWidgetGroupes->setRowCount(0);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
list<int> ApplicHoraireWindow::getIndexesGroupsSelection() const {
    QModelIndexList liste = ui->tableWidgetGroupes->selectionModel()->selectedRows();
    list<int> result;
    for (int i=0 ; i<liste.size() ; i++) result.push_back(liste.at(i).row());
    return result;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// Fonctions utiles Table des locaux //////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::addTupleTableClassrooms(const string& tuple) {
    // Parsing du tuple recu
    istringstream ss(tuple);
    string token;

    string id, name;
    getline(ss,id,';');
    getline(ss,name,';');

    // Ajout possible
    int nbLignes = ui->tableWidgetLocaux->rowCount();
    nbLignes++;
    ui->tableWidgetLocaux->setRowCount(nbLignes);

    QTableWidgetItem *item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setTextAlignment(Qt::AlignCenter);
    item->setText(QString::fromStdString(id));
    ui->tableWidgetLocaux->setItem(nbLignes-1,0,item);

    item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setText(QString::fromStdString(name));
    ui->tableWidgetLocaux->setItem(nbLignes-1,1,item);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::clearTableClassrooms() {
    ui->tableWidgetLocaux->setRowCount(0);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
int ApplicHoraireWindow::getIndexClassroomSelection() const {
    QModelIndexList liste = ui->tableWidgetLocaux->selectionModel()->selectedRows();
    if (liste.size() == 0) return -1;
    QModelIndex index = liste.at(0);
    int indice = index.row();
    return indice;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// Fonctions utiles Table des professeurs /////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::addTupleTableCourses(const string& tuple) {
    // Parsing du tuple recu
    istringstream ss(tuple);
    string token;

    string code, day, hour, duration, classroom, title, professor, groups;
    getline(ss,code,';');
    getline(ss,day,';');
    getline(ss,hour,';');
    getline(ss,duration,';');
    getline(ss,classroom,';');
    getline(ss,title,';');
    getline(ss,professor,';');
    getline(ss,groups,';');

    // Ajout possible
    int nbLignes = ui->tableWidgetCourss->rowCount();
    nbLignes++;
    ui->tableWidgetCourss->setRowCount(nbLignes);

    QTableWidgetItem *item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setTextAlignment(Qt::AlignCenter);
    item->setText(QString::fromStdString(code));
    ui->tableWidgetCourss->setItem(nbLignes-1,0,item);

    item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setText(QString::fromStdString(day));
    ui->tableWidgetCourss->setItem(nbLignes-1,1,item);

    item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setText(QString::fromStdString(hour));
    ui->tableWidgetCourss->setItem(nbLignes-1,2,item);

    item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setText(QString::fromStdString(duration));
    ui->tableWidgetCourss->setItem(nbLignes-1,3,item);

    item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setText(QString::fromStdString(classroom));
    ui->tableWidgetCourss->setItem(nbLignes-1,4,item);

    item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setText(QString::fromStdString(title));
    ui->tableWidgetCourss->setItem(nbLignes-1,5,item);

    item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setText(QString::fromStdString(professor));
    ui->tableWidgetCourss->setItem(nbLignes-1,6,item);

    item = new QTableWidgetItem;
    item->setFlags(Qt::ItemIsSelectable|Qt::ItemIsEnabled);
    item->setText(QString::fromStdString(groups));
    ui->tableWidgetCourss->setItem(nbLignes-1,7,item);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::clearTableCourses() {
    ui->tableWidgetCourss->setRowCount(0);
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// Fonctions permettant d'afficher des boites de dialogue /////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::dialogMessage(const string& title,const string& message) {
   QMessageBox::information(this,QString::fromStdString(title),QString::fromStdString(message));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::dialogError(const string& title,const string& message) {
   QMessageBox::critical(this,QString::fromStdString(title),QString::fromStdString(message));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
string ApplicHoraireWindow::dialogInputText(const string& title,const string& question) {
    return QInputDialog::getText(this,QString::fromStdString(title),QString::fromStdString(question)).toStdString();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
int ApplicHoraireWindow::dialogInputInt(const string& title,const string& question) {
    return QInputDialog::getInt(this,QString::fromStdString(title),QString::fromStdString(question));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
string ApplicHoraireWindow::dialogInputFileForLoad(const string& question)
{
  QString fileName = QFileDialog::getOpenFileName(this,QString::fromStdString(question), "", "Tous les fichiers (*.*)");
  return fileName.toStdString();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
string ApplicHoraireWindow::dialogInputFileForSave(const string& question)
{
  QString fileName = QFileDialog::getSaveFileName(this,QString::fromStdString(question), "", "Tous les fichiers (*.*)");
  return fileName.toStdString();
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// Fonctions utiles aux widgets de la fenetre /////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
string ApplicHoraireWindow::getProfessorLastName() const {
    return ui->lineEditNomProfesseur->text().toStdString();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::setProfessorLastName(const string& lastName) {
    ui->lineEditNomProfesseur->setText(QString::fromStdString(lastName));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
string ApplicHoraireWindow::getProfessorFirstName() const {
    return ui->lineEditPrenomProfesseur->text().toStdString();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::setProfessorFirstName(const string& firstName) {
    ui->lineEditPrenomProfesseur->setText(QString::fromStdString(firstName));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
string ApplicHoraireWindow::getGroupName() const {
    return ui->lineEditNumeroGroupe->text().toStdString();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::clearGroupName() {
    ui->lineEditNumeroGroupe->setText("");
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
string ApplicHoraireWindow::getClassroomName() const {
    return ui->lineEditNomLocal->text().toStdString();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::setClassroomName(const string& name) {
    ui->lineEditNomLocal->setText(QString::fromStdString(name));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
string ApplicHoraireWindow::getDaySelection() const {
    return ui->comboBoxJour->currentText().toStdString();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
int ApplicHoraireWindow::getHourStart() const {
    if (ui->lineEditHeuresDebut->text().toStdString() == "") return -1;
    return stoi(ui->lineEditHeuresDebut->text().toStdString());
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::clearHourStart() {
    ui->lineEditHeuresDebut->setText("");
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
int ApplicHoraireWindow::getMinuteStart() const {
    if (ui->lineEditMinutesDebut->text().toStdString() == "") return -1;
    return stoi(ui->lineEditMinutesDebut->text().toStdString());
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::clearMinuteStart() {
    ui->lineEditMinutesDebut->setText("");
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
int ApplicHoraireWindow::getDuration() const {
    if (ui->lineEditDuree->text().toStdString() == "") return -1;
    return stoi(ui->lineEditDuree->text().toStdString());
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::clearDuration() {
    ui->lineEditDuree->setText("");
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
string ApplicHoraireWindow::getTitle() const {
    return ui->lineEditIntitule->text().toStdString();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::setTitle(const string& title) {
    ui->lineEditIntitule->setText(QString::fromStdString(title));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
bool ApplicHoraireWindow::isDayChecked() const {
    return ui->checkBoxJour->isChecked();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::setDayChecked(bool b) {
    ui->checkBoxJour->setChecked(b);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
bool ApplicHoraireWindow::isProfessorChecked() const {
    return ui->checkBoxProfesseur->isChecked();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::setProfessorChecked(bool b) {
    ui->checkBoxProfesseur->setChecked(b);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
bool ApplicHoraireWindow::isGroupChecked() const {
    return ui->checkBoxGroupe->isChecked();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::setGroupChecked(bool b) {
    ui->checkBoxGroupe->setChecked(b);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
bool ApplicHoraireWindow::isClassroomChecked() const {
    return ui->checkBoxLocal->isChecked();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::setClassroomChecked(bool b) {
    ui->checkBoxLocal->setChecked(b);
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// Fonctions clics sur les boutons ////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_pushButtonAjouterProfesseur_clicked() {
    // cout << "Clic sur bouton Ajouter Professeur" << endl;
    if (getProfessorLastName().empty() || getProfessorFirstName().empty())
    {
        dialogError("ERREUR !", "Champ vide");
    }
 
    else
    {
        Timetable::getInstance().addProfessor(getProfessorLastName(), getProfessorFirstName());
        clearTableProfessors();
 
        auto tmp = Timetable::getInstance().getProfessors();
 
        for (auto it = tmp.cbegin(); it != tmp.cend(); ++it)
         {
            addTupleTableProfessors(it->tuple());
         }
 
         ui->lineEditPrenomProfesseur->clear();
         ui->lineEditNomProfesseur->clear();
    }
}   


/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_pushButtonAjouterGroupe_clicked() {
    cout << "Clic sur bouton Ajouter Groupe" << endl;
    // // TO DO (Etape 9)

    if(getGroupName().empty())
    {
        dialogError("Erreur", "Aucun groupe a ajouter saisi !");
    }
    else
    {
        Timetable::getInstance().addGroup(getGroupName());
        clearTableGroups();
        auto tmp = Timetable::getInstance().getGroups();
        for(auto it = tmp.cbegin(); it != tmp.cend(); it++)
        {
            addTupleTableGroups(it->tuple());
        }

        clearGroupName();
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_pushButtonAjouterLocal_clicked() {
    cout << "Clic sur bouton Ajouter Local" << endl;
    // TO DO (Etape 9)
    if (getClassroomName().empty())
    {
        dialogError("ERREUR !", "Champ vide");
    }
 
    else
    {
        Timetable::getInstance().addClassroom(getClassroomName(), dialogInputInt("Nombre de siége", "Indiquer le nombres de siéges de la classe"));
        clearTableClassrooms();
 
        auto tmp = Timetable::getInstance().getClassrooms();
 
        for (auto it = tmp.cbegin(); it != tmp.cend(); ++it)
         {
            addTupleTableClassrooms(it->tuple());
         }
 
         ui->lineEditNomLocal->clear();
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_pushButtonSupprimerProfesseur_clicked() {
    cout << "Clic sur bouton Supprimer Professeur" << endl;
    // TO DO (Etape 9)
    if((getIndexProfessorSelection()) == -1)
    {
        dialogError("ERREUR", "Veuillez selectionner un professeur");
    }
 
    else
    {
        Timetable::getInstance().deleteProfessorByIndex(getIndexProfessorSelection());
        clearTableProfessors();
 
        auto tmp = Timetable::getInstance().getProfessors();
        for (auto it = tmp.cbegin(); it != tmp.cend(); ++it)
         {
            addTupleTableProfessors(it->tuple());
         } 
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_pushButtonSupprimerGroupe_clicked() {
    cout << "Clic sur bouton Supprimer Groupe" << endl;
    // TO DO (Etape 9)
    list<int> indexesGroup = getIndexesGroupsSelection();
    
    if (indexesGroup.empty()) {
        dialogError("Erreur", "Aucun groupe à supprimer sélectionné !");
        return;
    }
    for (list<int>::const_iterator it = indexesGroup.cbegin(); it != indexesGroup.cend(); it++) {
        
        Timetable::getInstance().deleteGroupByIndex(*it);
    }
    
    clearTableGroups();
    auto tmp = Timetable::getInstance().getGroups();
    for(auto it = tmp.cbegin(); it != tmp.cend(); it++)
    {
        addTupleTableGroups(it->tuple());
    }
    clearGroupName();

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_pushButtonSupprimerLocal_clicked() {
    cout << "Clic sur bouton Supprimer Local" << endl;
    // TO DO (Etape 9)
    if((getIndexClassroomSelection()) == -1)
    {
        dialogError("ERREUR", "Veuillez selectionner une classe");
    }
 
    else
    {
        Timetable::getInstance().deleteClassroomByIndex(getIndexClassroomSelection());
        clearTableClassrooms();
 
        auto tmp = Timetable::getInstance().getClassrooms();
        for (auto it = tmp.cbegin(); it != tmp.cend(); ++it)
         {
            addTupleTableClassrooms(it->tuple());
         } 
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_pushButtonPlanifier_clicked()
{
    
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_pushButtonSelectionner_clicked()
{
    cout << "Clic sur bouton Selectionner" << endl;
    // TO DO (Etape 13)

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// Fonctions clics sur les items de menu //////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionQuitter_triggered()
{
    cout << "Clic sur Menu Fichier --> Item Quitter" << endl;
    QApplication::quit();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionOuvrir_triggered()
{

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionNouveau_triggered()
{
    cout << "Clic sur Menu Fichier --> Item Nouveau" << endl;
    
    auto tmp1 = Timetable::getInstance().getProfessors();

    for (auto it = tmp1.cbegin(); it != tmp1.cend(); ++it)
    {
        Timetable::getInstance().deleteProfessorById(it->getId());
        
    } 
    clearTableProfessors();
    

    auto tmp2 = Timetable::getInstance().getClassrooms();

    for (auto it = tmp2.cbegin(); it != tmp2.cend(); ++it)
    {
        Timetable::getInstance().deleteClassroomById(it->getId());
        
    } 
    clearTableClassrooms();
    

    auto tmp3 = Timetable::getInstance().getGroups();

    for (auto it = tmp3.cbegin(); it != tmp3.cend(); ++it)
    {
        Timetable::getInstance().deleteGroupById(it->getId());
        
    } 
    clearTableGroups();
    


    

    Schedulable::currentId = 1;

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionEnregistrer_triggered()
{
    cout << "Clic sur Menu Fichier --> Item Enregistrer" << endl;
    // TO DO (Etape 10)
 
}   

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionSupprimerProfesseur_triggered() {
    cout << "Clic sur Menu Supprimer --> Item Professeur" << endl;
    // TO DO (Etape 9)
    Timetable::getInstance().deleteProfessorById(dialogInputInt("Supprimer un professeur", "Saissisez l'id du professeur a supprimer"));
    clearTableProfessors();
 
    auto tmp = Timetable::getInstance().getProfessors();
    for (auto it = tmp.cbegin(); it != tmp.cend(); ++it)
    {
        addTupleTableProfessors(it->tuple());
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionSupprimerGroupe_triggered() {
    cout << "Clic sur Menu Supprimer --> Item Groupe" << endl;
    // TO DO (Etape 9)
    int id = dialogInputInt("supprimer un Groupe", "veuillez saisi l'Id du groupe a supprimer");
    Timetable::getInstance().deleteGroupById(id);
    clearTableGroups();
    auto tmp = Timetable::getInstance().getGroups();
    for(auto it = tmp.cbegin(); it != tmp.cend(); it++)
    {
        addTupleTableGroups(it->tuple());
    }


}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionSupprimerLocal_triggered() {
    cout << "Clic sur Menu Supprimer --> Item Local" << endl;
    // TO DO (Etape 9)
    Timetable::getInstance().deleteClassroomById(dialogInputInt("Supprimer un local", "Saissisez l'id du local a supprimer"));
    clearTableClassrooms();
 
    auto tmp = Timetable::getInstance().getClassrooms();
    for (auto it = tmp.cbegin(); it != tmp.cend(); ++it)
    {
        addTupleTableClassrooms(it->tuple());
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionSupprimerCours_triggered()
{
    cout << "Clic sur Menu Supprimer --> Item Cours" << endl;
    // TO DO (Etape 11)


}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionImporterProfesseurs_triggered()
{
    cout << "Clic sur Menu Importer --> Item Professeurs" << endl;
    // TO DO (Etape 12)

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionImporterGroupes_triggered()
{
    cout << "Clic sur Menu Importer --> Item Groupes" << endl;
    // TO DO (Etape 12)

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionImporterLocaux_triggered()
{
    cout << "Clic sur Menu Importer --> Item Locaux" << endl;
    // TO DO (Etape 12)

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionExporterProfesseur_triggered()
{
    cout << "Clic sur Menu Exporter horaire --> Item Professeur" << endl;
    // TO DO (Etape 12)

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionExporterGroupe_triggered()
{
    cout << "Clic sur Menu Exporter horaire --> Item Groupe" << endl;
    // TO DO (Etape 12)

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
void ApplicHoraireWindow::on_actionExporterLocal_triggered()
{
    cout << "Clic sur Menu Exporter horaire --> Item Local" << endl;
    // TO DO (Etape 12)

}

