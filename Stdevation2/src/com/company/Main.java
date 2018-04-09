package com.company;

/**
 * Created by Robert Hubert on 21/03/2018.
 */

// ---------*---------*---------*---------*
// The use of static imports is something that should be used carefully.
// I have only ever used this technique for the project wide constants.
//

import static com.company.ProjConstants.*;
import java.io.*;
import java.lang.invoke.MethodType;
import java.nio.channels.CancelledKeyException;
import java.util.*;

public class Main {

    // --------------------------------
    // Here is a private method (procedure) that will Display the results to the screen
    //
    private static void displayResults(int numItems, double ave, double var, double dev) {

        System.out.println("==========================================================================================");
        System.out.println("                       Data Distribution Info\n");
        System.out.printf("\tNumber of Items........ %10.0f\n", (double) numItems);
        System.out.printf("\tAverage:............... %10.4f\n", ave);
        System.out.printf("\tVariance:.............. %10.4f\n", var);
        System.out.printf("\tStandard Deviation:.... %10.4f\n", dev);
        System.out.println("\n                       Range Values\n");
        System.out.printf("\t68 %% of Data:\t %10.4f    <  %10.4f    <  %10.4f\n", (ave - dev), ave, (ave + dev));
        System.out.printf("\t95 %% of Data:\t %10.4f    <  %10.4f    <  %10.4f\n", (ave - (2 * dev)), ave, (ave + (2 * dev)));
        System.out.printf("\t99 %% of Data:\t %10.4f    <  %10.4f    <  %10.4f\n", (ave - (3 * dev)), ave, (ave + (3 * dev)));
        System.out.println("==========================================================================================\n");

    }

    public static void main(String[] args) {

        int howManyDataItems = INVALID;
        double theAverage = INVALID;
        double theVariance = INVALID;
        double theStDeviation = INVALID;

        // --------------------------------
        // Create the Object to perform standard deviation calculations
        StDeviation CalcSD = new StDeviation();

        // ******************************************************************************
        // ******************************************************************************
        // READ THE USER CHOICES FROM THE COMMAND LINE
        //
        // THE USER WILL BE ALLOWED TO SELECT DISCRETE VARIABLES, OR DISCRETE VARIABLES
        // IN  A FREQUENCY TABLE. (see changes made to the ProjConstants class)
        //
        //   IF THE USER SELECTS DISCRETE VARIABLES IN A FREQUENCY TABLE THEN THEY WILL NEED TO BE PROMPTED
        //   FOR A MINIMUM, AND A MAXIMUM BOUNDARY FOR THE RANGE OF VALUES (MINIMUM CANNOT BE LESS THAN 0,
        //   AND MAXIMUM CANNOT BE GREATER THAN 1999). THERE IS NO LIMIT TO THE NUMBER OF DATA ITEMS IN THE
        //   FILE SINCE WE ARE COUNTING THE NUMBER OF OCCURRENCES (FREQUENCY) OF A VALUE USING THIS METHOD.
        //
        // THIS IS WHERE:
        // 1) THE new "set" METHOD SHOULD TO BE USED FROM THE "StDeviation" TO IDENTIFY HOW THE MEAN,
        //    AND VARIANCE ARE TO BE CALCULATED, AND
        // 2) THE new "set" METHOD SHOULD TO BE USED FROM THE "StDeviation" TO IDENTIFY THE MINIMUM AND
        //    MAXIMUMS FOR THE RANGE OF VALUES TO BE USED.
        //
        //
        // ******************************************************************************
        // ******************************************************************************

        // --------------------------------
        // create a variable userFileName of type scanner to process input from "System.in"
        //



        {
            String userInputFileName;
            String methodType;

            System.out.println("\nPlease type in the INPUT file name\n");

            // --------------------------------
            // Here we are using another class defined in Java, the Scanner class, to
            // allow us to read the input entered by the person running/using our program.
            // Note: We use the keyword "new" in front of the class name "Scanner" to create
            // an "object" from a class. Additionally, we tell our program to specifically
            // create an object that will read information from "System.in". In this case
            // we have called our Scanner object "s"
            //

            Scanner scanSystemIn = new Scanner(System.in, "UTF-8");

            // Use the Scanner "userFileName" to get the "next" input from "System.in"
            userInputFileName = scanSystemIn.next();

            System.out.println("\ninput Calc method\n");
            methodType = scanSystemIn.next();
            CalcSD.setCalcMethod(Integer.parseInt(methodType));
            // ---------------------------------------------
            // This is where we "try" to process the file

            try {

                // --------------------------------
                // create file, and scanner objects
                // - file object is called tempfilenums.txt and is in your project directory
                //   that is the same folder as the iml file
                //

                File userFile = new File(userInputFileName);
                Scanner scanUserFile = new Scanner(userFile, "UTF-8");

                // ******************************************************************************
                // ******************************************************************************
                // THE WAY THE DATA IS READ FROM THE FILE WILL HAVE TO BE CHANGED TO READ THE VALUES INSIDE A
                // PRE-TESTED LOOP. RECALL THE USER CAN SELECT DISCRETE VARIABLES, OR DISCRETE VARIABLES
                // IN  A FREQUENCY TABLE. IF THE USER SELECTED:
                //  1) DISCRETE VARIABLES THEN ONLY "MAXDATA" ITEMS CAN BE READ INTO THE ARRAY
                //  2) DISCRETE VARIABLES IN A FREQUENCY TABLE THEN THEY WERE PROMPTED FOR A MINIMUM, AND A
                //     MAXIMUM BOUNDARY FOR THE RANGE OF VALUES (MINIMUM CANNOT BE LESS THAN 0, AND MAXIMUM
                //     CANNOT BE GREATER THAN 1999). THERE IS NO LIMIT TO THE NUMBER OF DATA ITEMS IN THE FILE
                //     USING THE FREQUENCY TABLE METHOD SINCE WE ARE COUNTING THE NUMBER OF OCCURRENCES
                //     (FREQUENCY) OF A VALUE USING THIS METHOD.
                // ******************************************************************************
                // ******************************************************************************

                // ---------------------------------------------
                // Reads in values from the file in a for loop
                //

                for (int i = 0; i < MAXDATA; i++) {

                    // ---------------------------------------------
                    // The scanner checks if there is another integer and prints it
                    // if there is
                    //

                    if (scanUserFile.hasNext()) {

                        // gets the next integer from the file and adds it into the Standard Deviation Object
                        CalcSD.addNewDataItem(scanUserFile.nextInt());

                    } else {
                        // ---------------------------------------------
                        // The scanner detected no other integers
                        // - closes the scanner for the file
                        // - breaks out of the for loop
                        //
                        System.out.print("\n\nDataFileFILE has been completely READ\n\n");
                        scanUserFile.close();

                        // A break statement allows us to exit the loop before we have reach the end
                        break;
                    }
                }

                // ******************************************************************************
                // ******************************************************************************
                // RECALL THAT SINCE THE USER NOW CAN SELECT 2 DIFFERENT DATA PRESENTATIONS THE
                // WAY WE CALCULATE THE AVERAGE, AND THE VARIANCE CHANGES SLIGHTLY. SINCE WE ARE
                // USING A NEW "set" METHOD IN THE "StDeviation" CLASS THERE IS NO NEED TO
                // MODIFY THIS CODE SEGMENT THAT CALCULATES THE STANDARD DEVIATION (SEE ABOVE)
                // ******************************************************************************
                // ******************************************************************************

                // ---------------------------------------------
                // The file has been processed so we can do the calculations now.
                // Important Note: If the calculation fails the value returned is "INVALID"
                //
                howManyDataItems = CalcSD.getNumberOfDataItems();
                theAverage = CalcSD.calcAverage();
                theVariance = CalcSD.calcVariance();
                theStDeviation = CalcSD.calcStandardDeviation();

                // ---------------------------------------------
                // Display the information to the screen
                // Some checking is performed to ensure accuracy.
                //

                if (howManyDataItems != INVALID || howManyDataItems == 0) {
                    if (theAverage != INVALID) {
                        if (theVariance != INVALID) {
                            if (theStDeviation != INVALID) {

                                // ---------------------------------------------
                                // Use the private method to display the results
                                //

                                displayResults(howManyDataItems, theAverage, theVariance, theStDeviation);

                            } else {
                                System.out.println("ERROR Standard Deviation: NO VARIANCE Calculated");
                            }
                        } else {
                            System.out.println("ERROR Variance: NO AVERAGE Calculated");
                        }
                    } else {
                        System.out.println("ERROR Average: NO DATA Read from the file");
                    }
                } else {
                    System.out.println("ERROR Data Read: NO DATA stored check file");
                }

            } // end of try

            // ---------------------------------------------
            // Catch Statements from the try above
            //
            // If the file cannot be found then an exception (error) is generated (thrown) that we have to
            // deal with (catch).
            //
            catch (FileNotFoundException e) {
                System.err.format("File Not Found Exception: %s%n", e);
                e.printStackTrace();
            }

        } // end of main method
    } // end of class
}