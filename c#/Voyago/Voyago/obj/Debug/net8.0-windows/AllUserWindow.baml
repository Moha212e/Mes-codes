   M S B A M L   `   `   `  ����
  Voyago    Voyago.AllUserWindow   5   	   W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	     � Title$  	All Users��5   	    � WindowStartupLocation$ CenterScreen=�6   $	��889��5   	   $
��1067��6    � WindowState$ 	Maximized=�6#    � WindowStyle$
 None=�6;   
������  � AllowsTransparency$
 True��5   "   .��5      � ��5
   
   �� 5       �� ImageSource$y sC:\Users\pro\Documents\c#\code\C\c#\Voyago\Voyago\BackGround\beautiful-shot-silhouette-plane-flying-sky-sunrise.jpg��6    j� Stretch$ UniformToFill=�6�   .+�5   
   ��  J� Opacity$	 0.5Q�6   ���	#AAFFFFFF J� Effect 5      -  'System.Windows.Media.Effects.BlurEffect  5      	   Radius$	 15Q�6   5      ]� 5   
   $
��True��6   $��
0,84,0,-84q�6)   .+�5      �� 
����������#E5E7EB$��1q�6>   
 �� CornerRadius$
 10��6R   $	��600��5      $
��1000��6"   $��Center=�6/   $��Center=�6L   ._�5      � $��20q�6   .+�5      &   System.Windows.Controls.DataGrid  -   5          Name$ UsersDataGrid��5         AutoGenerateColumns$ False��66   $��Stretch=�6R   $��Stretch=�6p   
����������#E5E7EB$��1q�5   H      RowBackground �#F3F4F6   AlternatingRowBackground
 �����   HeadersVisibility$ Column=�5         GridLinesVisibility$	 All=�6:      SelectionUnit$ FullRow=�6T      SelectionMode$ Single=�6l      CanUserAddRows$ False��5         Columns 5!      0  *System.Windows.Controls.DataGridTextColumn  5"      ,  &System.Windows.Controls.DataGridColumn   Header$ Email��61      Width5  /System.Windows.Controls.DataGridLengthConverter$ * 6Z      
IsReadOnly$
 True��6d   1  +System.Windows.Controls.DataGridBoundColumn   Binding 6@   �� *Email+6d     5#      $ 
First Name��61   $ * 6c   $
 True��6m    6E   �� *	FirstName+6m     5$      $ 	Last Name��61   $ * 6a   $
 True��6k    6D   �� *
LastName+6k   4  .System.Windows.Controls.DataGridCheckBoxColumn  5%      $ Is Admin��65   $ * 6c    6G   �� *	IsAdmin+6c      	CellStyle 5)      �� 5*       �� 
TargetType*  $System.Windows.Controls.DataGridCell"  6$   .�5+   "   ��  �� Property � �� �� Value$	 8,4q�6<   �� 5,   "    � �� �#E5E7EB6@   �� 5-   "    � ��$ 0,0,1,1q�6D   �� 5.   "    � �� �#2E3A596?   �� 5/   "    � ��$ 14(�6=   �� 50   "    � ��$
 Left=�6H   �� 51   "    � ��$ Center=�6F   �� 52   "    � ��$ 40��6;    �� Triggers 53   "   P� 55   &    P� Property �@J�IsMouseOver P� Value$
 True��6E   . �56   *   ��  � �� �#E0E7FF6G   57   '   P� 58   &    �@ 
IsSelected$
 True��6D   . �59   *   ��  � �� �#4F46E56G   �� 5:   *    � ��
 �����6G   5;   '   5=          ColumnHeaderStyle  5@      �� 5A      =	  7System.Windows.Controls.Primitives.DataGridColumnHeader" 	 6$   .�5B   "   ��  � �� 5C   &   �� 5D   *   ! �� 
StartPoint$	! 0,0&�6>   " �� EndPoint$	" 1,0&�6O   .��5E   .   � # � Color$# #4F46E5��6;   $ � Offset$$ 0Q�6K   � 5F   .   $# #6366F1��6;   $$ 1Q�6K   5G   +   5I   #   �� 5J   "    � ��
 �����6?   �� 5K   "    � ��$
 Bold"�6?   �� 5L   "    � ��$ Center=�6O   �� 5M   "    � ��$
 10,5q�6<   �� 5N   "    � �� �#4F46E56@   �� 5O   "    � ��$ 0,0,0,2q�6D   �� 5P   "    � ��$ 14(�6=    5Q   "   P� 5R   &    �@J�IsMouseOver$
 True��6E   . �5S   *   ��  � �� 5T   .   �� 5U   2   $	! 0,0&�6F   $	" 1,0&�6W   .��5V   6   � $# #6366F1��6C   $$ 0Q�6S   � 5W   6   $# #818CF8��6C   $$ 1Q�6S   5X   3   5Z   +   5[   '   5]      5_      5`      5a      5b      �� 5e   
   $��Center=�6   $��60��61   $	��Top=�6=   ���	#FF5E81F0$
��2000��6f   �� 5f   
   $
��Left=�6   $��	30,10,0,0q�6/   $
��Wrap=�6B   $��Voyago��6V   $	��Top=�6d   $��57��6|   $	��204��6�   $��48(�6�   
�������$��Times New Roman)�6�   m� 5g   
   -   5        $��Accueil��5g      $	��Top=�6?   $��0,0,0,20q�6W   
�������$��Right=�6}   m� 5h   
   -   5        $��
Deconnexio��5h      $	��Top=�69   $��0,0,98,0q�6Q   
�������$��Right=�6w   �� 5k   
   -   5        $#��Enregistrer les modifications��5k      $��Center=�69   $��Bottom=�6V   $��0,0,0,20q�6q   $	��200��6�   $��40��6�   ���	#FF5E81F0
�������$
��Bold"�6�   $��14(�6�   ��5l      �� 5m      $" 0.5,1&�6&   $! 0.5,0&�67   .��5n      � $# Black��6#   � 5o      $# White��6#   $$ 1Q�61   5p      5r      5s      5t      