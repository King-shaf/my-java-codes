/*
(Sort three integers) Write a program that prompts the user to enter three integers
and display the integers in non-decreasing order.
*/
import java.util.Scanner;

public class orderInt {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		// Prompt the user to enter three integers
		System.out.println("program that prompts the user to enter three integers and display the integers in non-decreasing order ");
		System.out.print("Enter first integers: ");
		int number1 = input.nextInt();
		System.out.print("Enter second integers: ");
		int number2 = input.nextInt();
		System.out.print("Enter last integers: ");
		int number3 = input.nextInt();

		// Sort numbers
		int temp;
		if (number2 < number1 || number3 < number1)
		{
			if (number2 < number1)
			{
				temp = number1;
				number1 = number2;
				number2 = temp; 
			}
			if (number3 < number1)
			{
				temp = number1;
				number1 = number3;
				number3 = temp;
			}
		}
		if (number3 < number2)
		{
			temp = number2;
			number2 = number3;
			number3 = temp;
		}

		// Display numbers in accending order
		System.out.println("ordered= "+number1 + ", " + number2 + ", " + number3);
	}
}