   M S B A M L   `   `   `  ����B  >voyagoo, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null!clr-namespace:voyagoovoyagoo  5   	       voyagoo.MainWindow   5   	   W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   'localclr-namespace:voyagoo     5   	     � Title$  
MainWindow��5   	    � WindowState$ 	Maximized=�6     � SizeToContent$ Manual=�5	   	    � WindowStartupLocation$ CenterScreen=�6    .��5
      � .+�5   
   �� $��/Images/Designer.jpeg��6   $
��Fill=�5       K� ZIndex$ -1��5      (� 5   
    (� Stretch$ Uniform=�6   .��5      � $	��800��6   $	��450��6   .+�5      � $��Stretch=�6   $��Stretch=�65   $��50q�6Q   .+�5      ��  �� CornerRadius$ 20��6   
�������$��Stretch=�5      $��Stretch=�6;    �� Padding$ 20q�5      $	��300��6*   $	��200��69   ._�5      �  � ColumnDefinitions 5      �� 5   "   $��2* �63   �� 5   "   $��3* �63   .+�5      �� $ 60��6%   $��Center=�67   $
}�True��6T   ._�5    "   �� $��/Images/logo.jpeg��6(   $��Center=�5!   (   $��Uniform=�5"   (   5#      �� 5%      $��1��6)   $��Center=�69   $��20q�6T   .+�5&   "   �� $��	Connexion��6,   $��22(�6=   $
��Bold"�6K   
���   �$��Center=�6p   $��Segoe UI)�6�   �� 5'   "   $��Email��6-   $��14(�6:   $��SemiBold"�6H   
�������$��5,10,0,0q�6p   $��Segoe UI)�6�   $��Center=�6�   �� 5(   "   -   5        	 ��Name$	 EmailTextBox��5(   *   $��30��6@   $��Stretch=�6L   $��14(�6j   $��Segoe UI)�6x   �� 5)   "   $��Mot de passe��6-   $��14(�6A   $��SemiBold"�6O   
�������$��5,10,0,0q�6w   $��Segoe UI)�6�   $��Center=�6�   G� 5*   "   -   5        
 G�Name$
 Password��5*   .   $��30��6@   $��Stretch=�6L   $��14(�6j   $��Segoe UI)�6x   �� 5+   "   -   5        $��Se connecter��5+   )   $��35��6@   
������
�������$��14(�6w   $
��Bold"�6�   $��Segoe UI)�6�   $	��354��6�   $��Center=�6�   $��Center=�6�   $��0,30,0,0q�6  �� 5,   "   -   5        $��Pas de compte ?��5,   )   $��30��6C   
������ 
������$��12(�6�   $��Medium"�6�   $��0q�6�    � Cursor$
 Hand��6�   $��Center=�5-   )   $��Segoe UI)�6F   6\   �� 5.   "   -   5         ��Name$ ErrorMessage��5.   ,   $�� ��6B   $��14(�6J   $��SemiBold"�6X   
���  ��$��Center=�6   $��0,10,0,0q�6�   $��Segoe UI)�6�   5/      50      51      52      53      54      55      56      