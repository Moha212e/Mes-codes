using System;
using System.Windows;
using System.Windows.Controls;

namespace CalculatriceWPF
{
    public partial class MainWindow : Window
    {
        private string currentInput = "";
        private double result = 0;
        private char lastOperation;

        public MainWindow()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            if (sender is Button button)
            {
                string content = button.Content.ToString();

                if (double.TryParse(content, out _))
                {
                    currentInput += content;
                    Display.Text = currentInput;
                }
                else
                {
                    switch (content)
                    {
                        case "C":
                            currentInput = "";
                            result = 0;
                            Display.Text = "0";
                            break;

                        case "CE":
                            currentInput = "";
                            Display.Text = "0";
                            break;

                        case "+":
                        case "-":
                        case "*":
                        case "/":
                            Calculate();
                            lastOperation = content[0];
                            currentInput = "";
                            break;

                        case "=":
                            Calculate();
                            lastOperation = '\0';
                            Display.Text = result.ToString();
                            currentInput = "";
                            break;
                    }
                }
            }
        }

        private void Calculate()
        {
            if (double.TryParse(currentInput, out double number))
            {
                switch (lastOperation)
                {
                    case '+':
                        result += number;
                        break;
                    case '-':
                        result -= number;
                        break;
                    case '*':
                        result *= number;
                        break;
                    case '/':
                        if (number != 0)
                            result /= number;
                        else
                            Display.Text = "Erreur";
                        return;
                    default:
                        result = number;
                        break;
                }
                Display.Text = result.ToString();
            }
        }
    }
} 