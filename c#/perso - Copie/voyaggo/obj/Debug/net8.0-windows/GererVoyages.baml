   M S B A M L   `   `   `  ����  voyagoo    voyagoo.GererVoyages   5   	   W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	     � Title$  Gérer les Voyages��5   	    � WindowState$ 	Maximized=�5   	   ���	#FFF0F0F0$��Segoe UI)�5   	   .��5	      � $��5q�6    � RowDefinitions 5
   
   �� 5      $
��Auto �6   �� 5      $��* �6   �� 5      $
��Auto �6   .+�5   
   �� $��0��6   
������� �� CornerRadius$ 5��61    �� Padding$ 10q�6B   $��5q�6O   $��1q�5      ���	#FFDDDDDD._�5      �  � ColumnDefinitions 5      �� 5      $��* �6'   �� 5      $
��Auto �6'   �� 5      $
��Auto �6'   .+�5      �� -   5         ��Name$ txtRecherche��5      $��0��60   $��35��6@      ModernTextBox#��[  5      
 � Tag$ Rechercher...��6A   5      �� 5      -   5        $��1��5      $	��120��6)   $��35��65   $��10,0,0,0q�6A     PrimaryButton#��[ 5      $��
Rechercher��5       �� 5"      -   5        $��2��5"      $	��150��6)   $��35��65   $��10,0,0,0q�6A   #��[ 5#      $��+ Nouveau Voyage��5%      5&      5'      &   System.Windows.Controls.DataGrid  5*   
   -   5          Name$ VoyagesDataGrid��5*      $��1��6,   $��5q�69   	   AutoGenerateColumns$	 False��5+      
   CanUserAddRows$
 False��6.      SelectionMode$ Single=�5,         HeadersVisibility$ Column=�6)      RowHeaderWidth$ 0Q�5-         GridLinesVisibility$
 None=�6%   $��Stretch=�5.      $��Stretch=�60      CanUserDeleteRows$ False��5/         CanUserReorderColumns$ False��6,      Columns 51      4  .System.Windows.Controls.DataGridTemplateColumn  53      ,  &System.Windows.Controls.DataGridColumn   Width5  /System.Windows.Controls.DataGridLengthConverter$
 1.5* 6)      Header$ Destination��66     DataGridColumnHeaderStyle   HeaderStyle# [ 54   (      CellTemplate 55      �� 56      .R�57      ��  �� Padding$ 5q�6F   ��6(   �� *_destination+6F   58         CellEditingTemplate 5:      �� 5;      .R�5<      �� #��[  5=   &   $��0q�6M   ��5<   &   �� *_destination+ �� UpdateSourceTrigger$ PropertyChanged=�5=   M   5>      5@        5C      $ 2* 6)   $ Description��64   # [ 5D   (    5E      �� 5F      .R�5G      �� $ 5q�6F   $
��Wrap=�6R   ��6(   �� *_description+6R   5H       5J      �� 5K      .R�5L      ��  �� AcceptsReturn$
 True��5M   &    �� TextWrapping$
 Wrap=�6;   #��[  5N   &   $��0q�6M   ��5L   &   �� *_description+$ PropertyChanged=�5N   M   5O      5Q        5T      $ 1* 6)   $
 Date��64   # [ 5U   (    5V      �� 5W      .R�5X      �� $ 5q�6O   ��6(   �� *_date+ �� StringFormat$ d��6O   5Y       5[      �� 5\      .R�5]      (  "System.Windows.Controls.DatePicker    ModernDatePicker#��[ 5^   )      SelectedDate 5]   )   �� *_date+$ PropertyChanged=�5^   )   5_      5a        5d      $ 1* 6)   $
 Prix��64   # [ 5e   (    5f      �� 5g      .R�5h      �� $ 5q�6P   ��6(   �� *_price+$ C��6P   5i       5k      �� 5l      .R�5m      �� #��[  5n   &   $��0q�6M   ��5m   &   �� *_price+$ PropertyChanged=�.��5n   a    J� InputBindings 5o   "   �� 5p   &   
 �� Key$ Enter��61    �� Command 6=   �� *UpdatePriceCommand+.��5q   9   5r      5s      5u        5x      $
 1.5* 6)   $ Image��66   # [ 5y   (    5z      �� 5{      .R�5|      � $��Stretch=�6#    5}   "   �� 5~   &   $��* �67   �� 5   &   $
��Auto �67   .+�5�   "   �� $��0��6(   $��50��68   $��Uniform=�5�   (   $
��Left=�6:   ��5�   D   �� *_image+5�   :   �� 5�   "   -   5        $��1��5�   )   $��Changer��5�   A     SecondaryButton#��[ 5�   )   $��10,0,0,0q�5�   )     � DataContext  5�   )   �� 5�   )   5�      5�      5�        5�      $ 1* 6)   $ Actions��64   # [ 5�   (    5�      �� 5�      .R�5�      �� $��Center=�5�   '   $��Center=�6D   ! d� 	IsChecked! 5�   '   �� *
_isDeleted+$ PropertyChanged=�5�   D   5�      5�      "   RowStyle" 5�      �� 5�      # �� 
TargetType)  #System.Windows.Controls.DataGridRow"#  6   .�5�      �� $ �� Property$ � ��% �� Value
% �����63   & �� Triggers& 5�      P� 5�      ' P� Property' �@J�IsMouseOver( P� Value$
( True��69   . �5�      �� $ � ��% �	#FFF5F5F56;   5�      P� 5�      ' �@ 
IsSelected$
( True��68   . �5�      �� $ � ��% �	#FFE3F2FD6;   5�      P� 5�      ' �@ 	IsEditing$
( True��67   . �5�      �� $ � ��% �	#FFF0FFF06;   5�      5�      5�      �� 5�   
   $��2��6   
�������$ 0,0,5,5��61   $ 10q�5�      $��5,0,5,5q�6   $��0,1,1,1q�6/   ���	#FFDDDDDD._�5�      �� ) �� Orientation$) 
Horizontal=�6   $��Right=�62   .+�5�      �� -   5        $	��120��5�      $��35��6%   #��[ 61   $��Enregistrer tout��5�   3   �� 5�      -   5        $	��100��5�      $��35��6%   $��10,0,0,0q�61   #��[ 5�      $��Retour��5�      5�      5�      5�      5�      