   M S B A M L   `   `   `  ����  voyagoo     voyagoo.EditerUtilisateurs   5   	   W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	     � Title$  Gestion des Utilisateurs��5   	    � WindowState$ 	Maximized=�5   	   ���	#FFF0F0F0$��Segoe UI)�5   	    � WindowStartupLocation$ CenterScreen=�5   	   .��5
      � $��10q�6    � RowDefinitions 5   
   �� 5      $
��Auto �6   �� 5      $��* �6   �� 5      $
��Auto �6   .+�5   
   �� $��0��6   
������� �� CornerRadius$ 5��61    �� Padding$ 15q�5      $��5q�6   ���	#FFDDDDDD$��1q�6A   ._�5      �  � ColumnDefinitions 5      �� 5      $��* �6'   �� 5      $
��Auto �6'   .+�5      �� $��Gestion des Utilisateurs��6   $��20(�5      $��SemiBold"�6)   ���	#FF0078D7$��Center=�5   2   5      5      �� 5!   
   $��1��6   $��5q�6   $ 5��6)   $��1q�5"      ���	#FFDDDDDD
�������._�5#      &   System.Windows.Controls.DataGrid  -   5          Name$ UsersDataGrid��5#         AutoGenerateColumns$ False��5$      	   CanUserAddRows$	 False��5%      
   SelectionMode$
 Single=�5&         HeadersVisibility$ Column=�5'         RowHeaderWidth$ 0Q�5(         GridLinesVisibility$
 None=�5)      $��Stretch=�5*      $��Stretch=�5+         Columns 5-      4  .System.Windows.Controls.DataGridTemplateColumn  5/      ,  &System.Windows.Controls.DataGridColumn   Header$	 Nom��6-      Width5  /System.Windows.Controls.DataGridLengthConverter$
 1.5* 6:      DataGridColumnHeaderStyle   HeaderStyle# [  50   +      CellTemplate 51      �� 52      .R�53   "   ��  �� Padding$
 10,5q�6H   ��6,   �� *
_firstName+6H   54         CellEditingTemplate 56      �� 57      .R�58   "   ��   ModernTextBox#��[ 59   *   $��0q�6Q   ��58   *   �� *
_firstName+ �� UpdateSourceTrigger$ PropertyChanged=�59   Q   5:      5<        5?      $ Prénom��6-   $
 1.5* 6=   # [  5@   +    5A      �� 5B      .R�5C   "   �� $
 10,5q�6G   ��6,   �� *	_lastName+6G   5D       5F      �� 5G      .R�5H   "   �� #��[ 5I   *   $��0q�6Q   ��5H   *   �� *	_lastName+$ PropertyChanged=�5I   Q   5J      5L        5O      $ Email��6-   $ 2* 6<   # [  5P   +    5Q      �� 5R      .R�5S   "   �� $
 10,5q�6D   ��6,   �� *_email+6D   5T       5V      �� 5W      .R�5X   "   �� #��[ 5Y   *   $��0q�6Q   ��5X   *   �� *_email+$ PropertyChanged=�5Y   Q   5Z      5\        5_      $ Date de naissance��6-   $
 1.5* 6H   # [  5`   +    5a      �� 5b      .R�5c   "   �� $
 10,5q�6X   ��6,   �� *
_dateBirth+ �� StringFormat$ d��6X   5d       5f      �� 5g      .R�5h   "   (  "System.Windows.Controls.DatePicker    ModernDatePicker#��[ 5i   -      SelectedDate 5h   -   �� *
_dateBirth+$ PropertyChanged=�5i   -   5j      5l         RowStyle 5~      �� 5       �� 
TargetType)  #System.Windows.Controls.DataGridRow"  6   .�5�      ��  �� Property � �� �� Value
 �����67    �� Triggers 5�      P� 5�       P� Property �@J�IsMouseOver P� Value$
 True��6=   . �5�   "   ��  � �� �	#FFF5F5F56?   5�      P� 5�       �@ 
IsSelected$
 True��6<   . �5�   "   ��  � �� �	#FFE3F2FD6?   5�      P� 5�       �@ 	IsEditing$
 True��6;   . �5�   "   ��  � �� �	#FFF0FFF06?   5�      5�      5�      5�      �� 5�   
   $��2��6   
�������$ 0,0,5,5��61   $ 10q�5�      $��5,0,5,5q�6   $��0,1,1,1q�6/   ���	#FFDDDDDD._�5�      ��  �� Orientation$ 
Horizontal=�6   $��Right=�62   .+�5�      �� -   5        $��Enregistrer��5�      $	��120��5�      $��35��6$     PrimaryButton#��[ 5�      5�      �� 5�      -   5        $��Retour��5�      $	��100��5�      $��35��6$   $��10,0,0,0q�60     SecondaryButton#��[ 5�      5�      5�      5�      5�      5�      