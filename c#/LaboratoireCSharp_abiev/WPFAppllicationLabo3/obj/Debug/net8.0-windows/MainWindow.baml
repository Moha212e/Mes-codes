   M S B A M L   `   `   `  ����  WPFAppllicationLabo3$    WPFApplicationLabo3.MainWindow   5   	   W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	     � Title$  Gestion des Carrés��5   	   $	��450��6$   $	��800��61   .��5      �  � RowDefinitions 5   
   �� 5      $
��Auto �6   �� 5      $��* �6   �� 5	      $
��Auto �6   .+�5   
   &   System.Windows.Controls.DataGrid  $��0��6      AutoGenerateColumns$
 True��6w   $��10q�6�   ��6    �� *ListeCarres+ �� SelectedItem 6D   �� *CurrentCarre+ �� Mode$ TwoWay=�6�   �� 5   
   $��1��6   $��10q�6"   .+�5      �� $��Longueur du côté:��6   �� 5      $��0,5,0,10q�6^   ��6   �� *CurrentCarre.Cote+ �� UpdateSourceTrigger$ PropertyChanged=�6^   �� 5       � Minimum$ 1Q�6    � Maximum$	 100Q�6!   $��0,5,0,10q�6`    � Value 6/   �� *CurrentCarre.Cote+$ TwoWay=�6`   5      �� 5   
   -   5        $��2��5      $��Ajouter Carré��6   $��10q�6Q   $
��Left=�6]   �� 5   
   $��2��6   $��50(�6C   $��Right=�6Q   $��10q�6m   ��6   �� *ListeCarres.Count+6m   5      5      