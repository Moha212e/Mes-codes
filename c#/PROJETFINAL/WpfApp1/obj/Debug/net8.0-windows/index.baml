   M S B A M L   `   `   `  ����  WpfApp1    WpfApp1.MainWindow   5   	   W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	     � Title$  Voyage Planner��5   	   $	��500��6    $	��800��6-   .��5      � .+�5   
   �� $��Images/Designer.jpeg��6   $
��Fill=�6.    K� ZIndex$ -1��6=   � 5   
   .+�5      �� $��	Voyagevoo��6   $
��Left=�6(   $	��Top=�6C   $��Segoe UI)�5      $��12(�6*   $
��Bold"�68   $��	26,10,0,0q�6J   5      �� 5   
    �� CornerRadius$ 10��6   
������� J� Opacity$	 0.9Q�66   $��135,20,28,357q�6D    �� Padding$ 10q�6[   ._�5      � .+�5      �� .+�5      �� $��16(�6    $
��Bold"�6.    �� TextAlignment$ Center=�6@   $��0,5,0,8q�6W   Réservation5      � 5      $��50��6   .+�5      �� $��	30,5,0,15q�6$   $
��Left=�67   �� 5       � Language$ fr-be�6"   $
�Où?��63   5      �� 5      -   5         ��Name$ txtLieu��5   "   $��	30,22,0,5q�63   $	��180��6F   $��22��6R   $
��Left=�6^   �� 5      $��
250,5,0,15q�6$   $
��Left=�68   �� 5      $ fr-be�6"   $
�Prix��63   5      �� 5       -   5        $ txtPrix��5    "   $��
250,22,0,5q�63   $��80��6G   $��22��6R   $
��Left=�6^   �� 5"      $��
400,5,0,15q�6$   $
��Left=�68   �� 5#      $ fr-be�6"   $
�Date��63   5$      (  "System.Windows.Controls.DatePicker  5%      -   5          Name$ 
datePicker��5%   %   $��
400,22,0,6q�69   $	��159��6M   $��22��6Y   $
��Left=�6e   5&      5'      5(      5)      �� 5,   
   $ 10��6   
�������$	 0.9Q�66   $��135,132,28,15q�6D   $ 10q�6[   ._�5-      �� $��0,0,0,40q�6   .+�5.      �� $��18(�6   $
��Bold"�6*   $ Center=�6<   $��0,5,0,10q�6S   Suggestions de Voyage50      �� 52      $	��234��6   $
��Auto=�6,   $��Disabled=�6Q   .��53      �� 	 �� Orientation$	 
Horizontal=�6!   .+�55      �� $ 10��6!   ���#F5F5F5$ 10q�6H   $��5q�6U   ._�56      �� .+�57   "   �� $����C:\Users\Utilisateur\OneDrive - Enseignement de la Province de Liège\Documents\Bachelier 2 - informatique de gestion\Q2\Mes-codes\c#\PROJETFINAL\Images/amsterdam.jpg��6(   $	��160��6�   $	��110��6�   �� 58   "   ���#E0E0E0$ 5q�6>   $ 5��6J   $��0,5,0,5q�6[   ._�59   &   �� $��12(�60   $
��Wrap=�6>   $ Center=�6R   #!Explorez les canaux pittoresques.5;   '   5<   #   �� 5=   "   $��14(�6,   $
��Bold"�6:   $ Center=�6L   Prix : 350€5?   #   �� 5@   "   $��12(�6,   $ Center=�6:   
�������Date : 15/07/20255B   #   5C      5D      �� 5G      $ 10��6!   ���#F5F5F5$ 10q�6H   $��5q�6U   ._�5H      �� .+�5I   "   �� $����C:\Users\Utilisateur\OneDrive - Enseignement de la Province de Liège\Documents\Bachelier 2 - informatique de gestion\Q2\Mes-codes\c#\PROJETFINAL\Images/skopje.jpg��6(   $	��160��6�   $	��110��6�   �� 5J   "   ���#E0E0E0$ 5q�6>   $ 5��6J   $��0,5,0,5q�6[   ._�5K   &   �� $��12(�60   $
��Wrap=�6>   $ Center=�6R   )'Découvrez une ville riche en histoire.5M   '   5N   #   �� 5O   "   $��14(�6,   $
��Bold"�6:   $ Center=�6L   Prix : 250€5Q   #   �� 5R   "   $��12(�6,   $ Center=�6:   
�������Date : 22/06/20255T   #   5U      5V      �� 5Y      $ 10��6!   ���#F5F5F5$ 10q�6H   $��5q�6U   ._�5Z      �� .+�5[   "   �� $����C:\Users\Utilisateur\OneDrive - Enseignement de la Province de Liège\Documents\Bachelier 2 - informatique de gestion\Q2\Mes-codes\c#\PROJETFINAL\Images/maroc.jpg��6(   $	��160��6�   $	��110��6�   �� 5\   "   ���#E0E0E0$ 5q�6>   $ 5��6J   $��0,5,0,5q�6[   ._�5]   &   �� $��12(�60   $
��Wrap=�6>   $ Center=�6R   Entre désert et montagnes.5_   '   5`   #   �� 5a   "   $��14(�6,   $
��Bold"�6:   $ Center=�6L   Prix : 450€5c   #   �� 5d   "   $��12(�6,   $ Center=�6:   
�������Date : 10/08/20255f   #   5g      5h      �� 5k      $ 10��6!   ���#F5F5F5$ 10q�6H   $��5q�6U   ._�5l      �� .+�5m   "   �� $����C:\Users\Utilisateur\OneDrive - Enseignement de la Province de Liège\Documents\Bachelier 2 - informatique de gestion\Q2\Mes-codes\c#\PROJETFINAL\Images/thailande.jpg��6(   $	��160��6�   $	��110��6�   �� 5n   "   ���#E0E0E0$ 5q�6>   $ 5��6J   $��0,5,0,5q�6[   ._�5o   &   �� $��12(�60   $
��Wrap=�6>   $ Center=�6R    Plages et temples magnifiques.5q   '   5r   #   �� 5s   "   $��14(�6,   $
��Bold"�6:   $ Center=�6L   Prix : 700€5u   #   �� 5v   "   $��12(�6,   $ Center=�6:   
�������Date : 05/09/20255x   #   5y      5z      5{      5|      5}      5~      �� 5�   
   -   5        $��Suivant��5�      $	��100��6#   $��30��6/   $��12(�6;   $
��Bold"�6I   $��Right=�5�      $��Bottom=�6-   ���#007ACC
�������$��0q�5�   9   
 � Cursor$

 Hand��5�      $��	0,0,46,24q�65   5�      5�      