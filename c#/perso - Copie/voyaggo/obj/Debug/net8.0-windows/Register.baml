   M S B A M L   `   `   `  ����B  >voyagoo, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null!clr-namespace:voyaggovoyaggo  5   	       voyagoo.Register   5   	   W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   'localclr-namespace:voyaggo     5   	     � Title$  Register��5   	    � WindowState$ 	Maximized=�6    � SizeToContent$ Manual=�5	   	    � WindowStartupLocation$ CenterScreen=�6    .��5
      � .+�5   
   �� $T��NC:\Users\pasch\Documents\Mes-codes\c#\PROJETFINAL\WpfApp1\Images\Designer.jpeg��6   $
��Fill=�6h    K� ZIndex$ -1��6w   (� 5   
    (� Stretch$ Uniform=�6   .��5      � $	��800��6   $	��450��6   .+�5      � $��Stretch=�6   $��Stretch=�65   $��50q�6Q   .+�5      ��  �� CornerRadius$ 20��6   
������� �� Padding$ 20q�5      $	��300��6*   $	��200��69   $��0,-42,0,-38q�6I   ._�5      �  � ColumnDefinitions 5      �� 5   "   $��2* �63   �� 5   "   $��3* �63   .+�5      �� $ 60��6%   $��Center=�67   $
}�True��6T   ._�5   "   �� $P��JC:\Users\pasch\Documents\Mes-codes\c#\PROJETFINAL\WpfApp1\Images\logo.jpeg��6(   $��Center=�6|   $��Uniform=�6�   5      �� 5       $��1��6)   $��Center=�69   $��20q�6T   .+�5!   "   �� $��Inscription��6,   $��22(�6?   $
��Bold"�6M   
���   �$��Center=�6r   $��Segoe UI)�6�   �� 5$   "   -   5        	 ��Name$	 ErrorMessage��5$   ,   $�� ��6B   $��14(�6J   $��SemiBold"�6X   
���  ��$��Center=�6   $��0,10,0,0q�6�   $��Segoe UI)�6�   � 5&   "    5'   &   �� 5(   *   $��* �6;   �� 5)   *   $��* �6;   .+�5+   &   �� $��0��61   $��0,0,5,0q�6A   .+�5,   *   �� $��Prenom��64   $��14(�6B   $��SemiBold"�6P   
�������$��5,10,0,0q�6x   $��Segoe UI)�6�   �� 5-   *   -   5        
 ��Name$
 Prenom��5-   2   $��30��6B   $��Stretch=�6N   $��14(�6l   $��Segoe UI)�6z   5.   '   �� 5/   &   $��1��61   $��5,0,0,0q�6A   .+�50   *   �� $	��Nom��64   $��14(�6?   $��SemiBold"�6M   
�������$��5,10,0,0q�6u   $��Segoe UI)�6�   �� 51   *   -   5        $	
 Nom��51   2   $��30��6?   $��Stretch=�6K   $��14(�6i   $��Segoe UI)�6w   52   '   53   #   �� 55   "   $��Email��6,   $��14(�69   $��SemiBold"�6G   
�������$��5,10,0,0q�6o   $��Segoe UI)�6�   �� 56   "   -   5        $
 Email��56   *   $��30��69   $��Stretch=�6E   $��14(�6c   $��Segoe UI)�6q   � 58   "    59   &   �� 5:   *   $��* �6;   �� 5;   *   $��* �6;   .+�5=   &   �� $��0��61   $��0,0,5,0q�6A   .+�5>   *   �� $��Mot de passe��64   $��14(�6H   $��SemiBold"�6V   
�������$��5,10,0,0q�6~   $��Segoe UI)�6�   G� 5?   *   -   5         G�Name$ Password��5?   6   $��30��6H   $��Stretch=�6T   $��14(�6r   $��Segoe UI)�6�   5@   '   �� 5A   &   $��1��61   $��5,0,0,0q�6A   .+�5B   *   �� $��Confirmation��64   $��14(�6H   $��SemiBold"�6V   
�������$��5,10,0,0q�6~   $��Segoe UI)�6�   G� 5C   *   -   5        $ 
PasswordCo��5C   6   $��30��6J   $��Stretch=�6V   $��14(�6t   $��Segoe UI)�6�   5D   '   5E   #   �� 5G   "   $��Date de naissance��6,   $��14(�6E   $��SemiBold"�6S   
�������$��5,10,0,0q�6{   $��Segoe UI)�6�   (  "System.Windows.Controls.DatePicker  5H   "   -   5          Name$ DateNaissance��5H   -   $��Stretch=�6D   $��14(�6b   $��Segoe UI)�6p   �� 5J   "   -   5        $��
S'inscrire��5J   )   $��35��6>   
������
�������$��14(�6u   $
��Bold"�6�   $��	10,10,0,0q�6�   $��Stretch=�5K   )   $��Segoe UI)�6G   6]   �� 5L   "   -	   5        $��Deja un compte ?��5L   )   $��30��6D   
������ 
������$��12(�6�   $��Medium"�6�   $��0q�6�    � Cursor$
 Hand��6�   $��Center=�5M   )   $��Segoe UI)�6F   6\   5N      5O      5P      5Q      5R      5S      5T      5U      