import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * // -------------------------------------------------------------------------
/**
 *  This is a program that prompts the user for a single parameter, the path
 *  to the file. It then reads line by line from the file and scores the
 *  poker hands with a straight, straight flush, royal flush, flush, four of a
 *  kind, full house, three of a kind, two pair, one pair, or high card.
 *
 *  @author Jack Cobb
 *  @version Nov 1, 2013
 */
public class PokerHand
{

    // ----------------------------------------------------------
    /**
     * The main method that runs the program and prompts the user for
     * the path to the file. Then calls the interpretHand method to
     * score the hands.
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
                System.out.println(interpretHand(nextLine));
            }

            fileScanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method is going to take the string of cards and separate the
     * cards into an array of 5 separate cards
     * @param line is the line of cards in text for that determines a hand
     * @return is an array of the "cards"
     */
    public static String interpretHand(String line)
    {
        int diamonds = 0;                         //Keeps track of how many of
        int spades = 0;                           //each suit to determine flushes
        int hearts = 0;
        int clubs = 0;
        String[] cards = line.split(" ");
        String handScore = "";

        for (int ii = 0; ii < 5; ii++)
        {
            if (cards[ii].charAt(1) == 'D')
            {
                diamonds = diamonds + 1;
            }
            else if (cards[ii].charAt(1) == 'C')
            {
                clubs = clubs + 1;
            }
            else if (cards[ii].charAt(1) == 'H')
            {
                hearts = hearts + 1;
            }
            else if (cards[ii].charAt(1) == 'S')
            {
                spades = spades + 1;
            }

            //checks for 10, if 10, shift right a character for suit
            else if (Character.getNumericValue(cards[ii].charAt(0)) == 1)
            {
                if (cards[ii].charAt(2) == 'D')
                {
                    diamonds = diamonds + 1;
                }
                else if (cards[ii].charAt(2) == 'C')
                {
                    clubs = clubs + 1;
                }
                else if (cards[ii].charAt(2) == 'H')
                {
                    hearts = hearts + 1;
                }
                else if (cards[ii].charAt(2) == 'S')
                {
                    spades = spades + 1;
                }
                else
                {
                    System.out.println("Invalid card suit specified.");
                }

            }
            else
            {
                System.out.println("Invalid card suit specified.");
            }
        }

        //Sorts the cards in a low to high array
        int[] inOrder = inSequence(cards);

        //Determines the correct score of the hand

        if (straight(inOrder))    //Royal FLush, Straight, Straight Flush
        {
            if ((spades | hearts | clubs | diamonds) == 5)
            {
                if (inOrder[0] == 10)            //Royal Flush
                {
                    handScore = "Royal Flush";
                }
                else                             //Straight Flush
                {
                    handScore = "Straight Flush";
                }

            }
            else                                  //Straight
            {
                handScore = "Straight";
            }
        }

        else if ((spades | hearts | clubs | diamonds) == 5)        //Flush
        {
            handScore = "Flush";
        }

        else if (findDuplicate(inOrder) == 4)        //Four of a kind
        {
            handScore = "Four of a Kind";

        }

        else if (findDuplicate(inOrder) == 3)         //Three of a kind or Full house
        {
            if (countUniqueCards(inOrder) == 2)       //Full House
            {
                handScore = "Full House";
            }
            else if(countUniqueCards(inOrder) == 3)
            {
                handScore = "Three of a Kind";         //Three of a kind
            }
        }

        else if (findDuplicate(inOrder) == 2)      //One pair or two pair
        {
            if (countUniqueCards(inOrder) == 3)    //Two pair
            {
                handScore = "Two Pair";
            }
            else                                    //One Pair
            {
                handScore = "One Pair";
            }

        }

        else                                        //High Card
        {
            handScore = "High Card";
        }

        return handScore;
    }

    /**
     * This puts numeric values on the cards in the hand then sorts them
     * from low to high.
     * @param hand is your current hand
     * @return is the sorted array of cards
     */
    public static int[] inSequence(String[] hand)
    {
        int[] values = new int[5];               //new array of only ints

        for (int jj = 0; jj < 5; jj++)          //puts numerical value of card into the array
        {                                       //including face cards
            if (hand[jj].charAt(0) == 'A')
            {
                values[jj] = 14;
            }
            else if (hand[jj].charAt(0) == 'K')
            {
                values[jj] = 13;
            }
            else if (hand[jj].charAt(0) == 'Q')
            {
                values[jj] = 12;
            }
            else if (hand[jj].charAt(0) == 'J')
            {
                values[jj] = 11;
            }
            else if (Character.getNumericValue(hand[jj].charAt(0)) == 1)
            {
                values[jj] = 10;
            }
            else
            {
                values[jj] = Character.getNumericValue(hand[jj].charAt(0));
            }

        }

        Arrays.sort(values);          //Sorts the array from low to high

        return values;
    }

    /**
     * Checks if the hand is a straight
     * @param hand is the sorted array
     * @return is whether it is a straight
     */
    public static boolean straight(int[] hand)
    {
        boolean straight = false;
        for (int i = 0; i < hand.length - 2; i++ )  //goes through the array
        {                                           //and determines if it is a straight
            int temp = hand[i];

            if((hand[i + 1] == (temp + 1)) && (hand[i + 2] == (temp + 2)))
            {
                straight = true;
            }
        }

        return straight;
    }


    /**
     * This method finds if there are more than one of the same card.
     * It finds pairs, three of a kind, and four of a kind.
     * @param hand is the hand of cards
     * @return is the number of repeating cards in the hand
     */
    public static int findDuplicate(int[] hand)
    {
        int maxMatch;                           // max is the highest number of duplicate cards
        int x = 1;
        int y = 1;                             //X and Y are counters for matches
        boolean matchFound = false;
        for (int ii = 0; ii < 4; ii++)
        {
            if (hand[ii] == hand[ii + 1] && !matchFound)          //checks if its the same if
            {                                                   // no other match already
                x = x + 1;
            }
            else if (hand[ii] == hand[ii + 1] && matchFound)     //checks its its the same number
            {                                                   // when another match has already been found
                y = y + 1;
            }
            else if (hand[ii] != hand[ii + 1] && ii > 0)        //checks if another match has already been found
            {
                matchFound = true;
            }
        }
        if (x > y)
        {
            maxMatch = x;
        }
        else if (x == 2 && y == 2)                  // Decides which match number is bigger
        {                                           // and returns that value
            maxMatch = 2;
        }
        else
        {
            maxMatch = y;
        }
        return maxMatch;

    }

    /**
     * This method returns the total number of different cards in the hand
     * @param hand is the sorted card values
     * @return is the number of cards
     */
    public static int countUniqueCards(int[] hand)
    {
        int count = 1;                              //count of unique cards in hand
        for (int c = 0; c < 4; c++)
        {
            if (hand[c] != hand[c + 1])            //checks against next card
            {
                count = count + 1;                 //if true, then its a different card
            }                                      //add one to count of unique cards

        }

        return count;
    }
}

