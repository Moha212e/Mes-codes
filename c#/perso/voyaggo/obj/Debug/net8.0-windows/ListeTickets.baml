   M S B A M L   `   `   `  ����  voyagoo    voyagoo.ListeTickets   5   	   W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	     � Title$  Liste des Tickets��5   	   $	��400��6#   $	��700��60   ���#F4F7FC � WindowStartupLocation$ CenterScreen=�5      .��5      � .+�5   
   �� $��Liste des Tickets��6   $��18(�6-   $
��Bold"�6;   ���#007ACC$��Center=�5	   )   $��0,20,0,0q�6F   &   System.Windows.Controls.DataGrid  5   
   -   5        $��TicketsDataGrid��5      $��20,60,20,100q�6*      AutoGenerateColumns$ False��6@      CanUserAddRows$ False��5         CanUserDeleteRows$
 True��6*      
IsReadOnly$ False��6C      SelectionMode$ Single=�5         Columns 5      0  *System.Windows.Controls.DataGridTextColumn  5      ,  &System.Windows.Controls.DataGridColumn   Header$ Voyage��6%   	   Width5  /System.Windows.Controls.DataGridLengthConverter$		 200 6P   
   
IsReadOnly$

 True��6\   1  +System.Windows.Controls.DataGridBoundColumn   Binding 65   �� *IdTrip+6\     5      $
 Date��6%   $		 150 6L   $

 True��6X    63   �� *Date+6X     5      $
 Prix��6%   $		 120 6L   $

 True��6X    63   �� *Prix+6X   4  .System.Windows.Controls.DataGridTemplateColumn  5      $ 	Voyageurs��6)   $		 200 6<      CellTemplate 5      �� 5      .R�5      �� ��6(   �� *Travelers.Count+ �� StringFormat$ {0} voyageurs��5      5        5      $ 
Operations��6)   $		 200 6=    5      �� 5      .R�5      ��  �� Orientation$ 
Horizontal=�6)   5       5!      5#      5%      �� 5(   
   -   5        $��Retour��5(      $	��100��6"   $��30��6.   $
��Left=�6:   $��Bottom=�5)      $��	20,0,0,20q�6,   6?   5*      5+      