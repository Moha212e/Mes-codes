   M S B A M L   `   `   `  ����  voyagoo    voyagoo.ListeTickets   5   	   W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	     � Title$  Liste des Voyageurs��5   	   $	��600��6%   $	��900��62   ���#F4F7FC � WindowStartupLocation$ CenterScreen=�5      .��5      � .+�5   
   �� $��Liste des Voyageurs��6   $��18(�6/   $
��Bold"�6=   ���#007ACC$��Center=�5	   )   $��0,20,0,0q�6F   &   System.Windows.Controls.DataGrid  5   
   -   5        $��TicketsDataGrid��5      $��20,60,20,100q�6*      AutoGenerateColumns$ False��6@      CanUserAddRows$ False��5         CanUserDeleteRows$ False��6*      
IsReadOnly$
 True��6D      SelectionMode$ Single=�5         SelectionUnit$ FullRow=�6*      Columns 5      0  *System.Windows.Controls.DataGridTextColumn  5      ,  &System.Windows.Controls.DataGridColumn	   Header$	 ID Voyageur��6%   
   Width5  /System.Windows.Controls.DataGridLengthConverter$
 80 6Y   1  +System.Windows.Controls.DataGridBoundColumn   Binding 6:   �� *
idTraveler+6Y     5      $	 	ID Voyage��6%   $
 80 6S    68   �� *idTrip+6S     5      $	 Prénom��6%   $	
 120 6S    65   �� *	firstName+6S     5      $		 Nom��6%   $	
 120 6O    62   �� *
lastName+6O     5      $	 Date de naissance��6%   $	
 120 6p    6@   �� *dateOfBirth+ �� StringFormat$ d��6p     5      $	 Nationalité��6%   $	
 100 6Z    6:   �� *nationality+6Z     5      $	 N° Passeport��6%   $	
 100 6^    6;   �� *passportNumber+6^     5      $	 Destination��6%   $	
 100 6\    6:   �� *destinination+6\     5      $	 Bagage (kg)��6%   $
 80 6U    6:   �� *bagage+6U   5      �� 5   
   -   5        $��Retour��5      $	��100��6"   $��30��6.   $
��Left=�6:   $��Bottom=�5      $��	20,0,0,20q�6,   6?   5      5       