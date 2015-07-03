import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 *  This program prompts the user for the file path. The program then calculates
 *  the decimal number for each Roman Numeral found.
 *
 *  @author Jack Cobb
 *  @version Nov 1, 2013
 */

public class RomanNumeralConverter
{

    // ----------------------------------------------------------
    /**
     * The main method that prompts the user for the path to the file, then
     * runs the method to convert to decimal
     * @param args
     */
    public static void main(String[] args)
    {
        System.out.println("Please input the target file path.");
        Scanner scan = new Scanner(System.in);
        String filePath = scan.next();
        Scanner fileScanner;
        try
        {
            String nextLine;
            fileScanner = new Scanner(new FileReader(filePath));
            while (fileScanner.hasNextLine())
            {
                nextLine = fileScanner.nextLine();
                System.out.println(numeralConversion(nextLine));
            }

            fileScanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }





    }

    /**
     * This is the method that converts the Roman numerals to decimal
     * @param numeral is a string of Roman numerals
     * @return is the decimal conversion of the Roman numerals
     */
    public static int numeralConversion(String numeral)
    {
        int total = 0;
        for (int ii = 0; ii < numeral.length(); ii++)    //Iterate across the Roman Numeral
        {

            //checks for 1000
            if (numeral.charAt(ii) == 'M')
            {
                total = total + 1000;
            }

            //checks for 500
            else if (numeral.charAt(ii) == 'D')
            {

                total = total + 500;
            }

            //checks for 100, 400, 900
            else if (numeral.charAt(ii) == 'C')
            {
                if (ii < numeral.length() - 1 &&
                    numeral.charAt(ii + 1) == 'D')      //If D following, then subtract
                {                                       // 100 to get 400
                    total = total + 400;
                    ii = ii + 1;
                }
                else if (ii < numeral.length() - 1 &&    //If M following, then subtract 100
                    numeral.charAt(ii + 1) == 'M')     // to get 900.
                {
                    total = total + 900;
                    ii = ii + 1;
                }
                else
                {
                    total = total + 100;
                }
            }

            //checks for 50
            else if (numeral.charAt(ii) == 'L')
            {
                total = total + 50;
            }

            //checks for 10, 40, 90
            else if (numeral.charAt(ii) == 'X')
            {
                if (ii < numeral.length() - 1 &&
                    numeral.charAt(ii + 1) == 'L')     //If followed by L, then subtract
                {                                       // 10 to get 40.
                    total = total + 40;
                    ii = ii + 1;
                }
                else if (ii < numeral.length() - 1 &&
                    numeral.charAt(ii + 1) == 'C')     //If followed by C, then subtract
                {                                       // 10 to get 90.
                    total = total + 90;
                    ii = ii + 1;
                }
                else
                {
                    total = total + 10;
                }

            }

            //Checks for 5
            else if (numeral.charAt(ii) == 'V')
            {
                total = total + 5;
            }

            // Checks for 1, 4, and 9
            else if (numeral.charAt(ii) == 'I')
            {
                if (ii < numeral.length() - 1 &&
                    numeral.charAt(ii + 1) == 'V')      //If followed by V, then subtract
                {                                       //1 to get 4.
                    total = total + 4;
                    ii = ii + 1;
                }
                else if (ii < numeral.length() - 1 &&
                    numeral.charAt(ii + 1) == 'X')      //If followed by X, then subtract
                {                                       //1 to get 9.
                    total = total + 9;
                    ii = ii + 1;
                }
                else
                {
                    total = total + 1;
                }
            }

            else                    //If any other Character is found, print that it is
            {                       //an invalid Roman Numeral, and cannot be added.
                System.out.println("Found an invalid Roman Numeral");
            }
        }

        return total;             //Return the total found from the next line in the file
    }

}
