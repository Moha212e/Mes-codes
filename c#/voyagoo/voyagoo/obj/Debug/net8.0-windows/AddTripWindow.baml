   M S B A M L   `   `   `  ����  voyagoo    Voyago.AddTripWindow   5   	   W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	     � Title$  Ajouter un voyage��5   	    � WindowStartupLocation$ CenterScreen=�6#   $	��889��5   	   $
��1067��6    � WindowStyle$
 None=�6$   .��5      � ��5	   
   �� 5
       �� ImageSource$Q KC:\Users\pro\Documents\C\Mes-codes\c#\voyagoo\voyagoo\ImagesFond\images.jpg��6    j� Stretch$ UniformToFill=�6s   .+�5   
   ��  J� Opacity$	 0.5Q�6   ���	#AAFFFFFF J� Effect 5      -  'System.Windows.Media.Effects.BlurEffect  5         Radius$ 15Q�6   5      ]� 5   
   $
��True��6   $��
0,84,0,-84q�6)   .+�5      � $	��Top=�6   .+�5      �� 
�������
���   �$��1q�6@    �� CornerRadius$ 10��6T   $	��580��6f   $��170,66,197,0q�6s   $��Center=�6�   $��Center=�6�   $	��800��6�   ._�5      �� $��Center=�6!   $��Center=�6<   .+�5      �� $��Ajouter un voyage��6$   $��24(�6=   $
��Bold"�6K   $��Center=�6]   $��	0,30,0,20q�6z   �� 5      	 �� Orientation$	 
Horizontal=�6%   $��Center=�6>   
 J� RenderTransformOrigin$
 0.505,0.649&�6[   $��Center=�6   .+�5      �� $��10q�6)   .+�5   "   �� $��Nom du voyage��6(   $
��Left=�6@   $��14(�6[   �� 5    "   -   5         ��Name$ NameBox��5    *   $	��180��6;   $��35��6G   
�������$��1q�6f   5!      �� 5#      $��10q�6)   .+�5$   "   �� $��Description��6(   $
��Left=�6>   $��14(�6Y   �� 5%   "   -   5        $ DescriptionBox��5%   *   $	��180��6B   $��35��6N   
�������$��1q�6m   5&      5'      �� 5*      $
��Prix��6    $��Center=�6/   �� 5+      -   5        $ PriceBox��5+   "   $	��350��64   $��37��6@   
�������$��1q�6_   $��Center=�6s   $��Center=�6�   �� 5.      $
��Date��6    $��Center=�6/   (  "System.Windows.Controls.DatePicker  5/      -   5          Name$ 
DatePicker��5/   %   $	��350��69   $��37��6E   
�������$��1q�6d   $��Center=�6x   $��Center=�6�   �� 51      $#��glissez deposez une image ici��6    $��Center=�6H   �� 52      -   5         ��Name$ ImageDropArea��52   !   
�������$��2q�6K    �� Padding$ 10q�6_   $��10q�6l   $��Center=�6x   $��Center=�6�   $	��350��6�   $��60��6�   ._�53      �� $#��glissez deposez une image ici��6(   $��Center=�6M   $��Center=�6j   54      �� 56      -   5        $	��350��56   !   $��52��6-   ���	#FF5E81F0$��Ajouter��6P   $��Center=�6b   $��0,20,0,0q�6   ��57      �� 58   "    �� 
TargetType" ��62   .R�59   &   �� ���	#FF5E81F0$ 10��6D   $ 10q�6V   ._�5:   *   �� $��Center=�6;   $��Center=�6X   5;   '   5<   #   5>      5?      5@      5A      5B      �� 5D   
   $��Center=�6   $��60��61   $	��Top=�6=   ���	#FF5E81F0$
��2000��6f    � Cursor$  ��6s   �� 5E   
   $
��Left=�6   $��1,-5,0,0q�6/   $
��Wrap=�6A   $��Voyago��6U   $	��Top=�6c   $��57��6{   $	��204��6�   $��48(�6�   
�������$��Times New Roman)�6�   m� 5G   
   -   5        $��Accueil��5G      $	��Top=�6?   $��0,0,0,20q�6W   
�������$��Right=�6|   m� 5H   
   -   5        $��Deconnexion��5H      $	��Top=�6:   $��0,0,98,0q�6R   
�������$��Right=�6w   5I      5J      