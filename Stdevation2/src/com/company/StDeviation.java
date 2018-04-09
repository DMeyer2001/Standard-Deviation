package com.company;

/**
 * Created by Robert Hubert on 21/03/2018.
 */


// ---------*---------*---------*---------*
// The use of static imports is something that should be used carefully.
// I have only ever used this technique for the project wide constants.
//
//import com.sun.java.util.jar.pack.Instruction;
//import com.sun.org.apache.bcel.internal.generic.SWITCH;
import static com.company.ProjConstants.*;

public class StDeviation {

    /* ---------*---------*---------*---------*---------*---------*---------*---------*
    // Class Level Variables
    // ---------*---------*---------*---------*---------*---------*---------*--------- */

    // As discussed in class we are using a class populated with Project Constants to
    // ensure help manage the project data. This ensures that each class can access the
    // constant values, but in the event that a change is needed/required that this will
    // only need to be made in one location, or file.
    //
    // Notice: If I had not done the "static import com.company.ProjConstants.*;" then
    //         the use of the constant would have been written as:
    //
    //         private int[] Data = new int[ProjConstants.MAXDATA];
    //
    private int[] Data = new int[MAXDATA];

    // sets all values to invalid to allow some checking
    private int sdItems = INVALID;
    private int sdMinRange = INVALID_RANGE;
    private int sdMaxRange = INVALID_RANGE;
    private int calcMethod = INVALID_CALC_METHOD;
    private double sdAve = INVALID;
    private double sdVar = INVALID;
    private double sdDev = INVALID;

    // ******************************************************************************
    // ******************************************************************************

    // THERE ARE SEVERAL METHODS THAT WILL HAVE TO CHANGE IN THIS CLASS TO ALLOW THE
    // CALCULATION OF STANDARD DEVIATION USING THE FREQUENCY TABLE METHOD AS WELL.
    // PLEASE CONSIDER THE CHANGES THAT YOU WILL HAVE TO MAKE TO:
    //   - addNewDataItem (precondition -  calculation method is set)
    //   - calcAverage    (precondition -  data added, & calculation method is set)
    //   - calcVariance   (precondition -  average calculated, data added, & calculation method is set)


    public void setCalcMethod(int how2calculate) {
        switch (how2calculate) {
            case DISCRETE: {
                calcMethod = how2calculate;
                System.out.println("Calc method: DISCRETE");
                break;
            }
            case FRQTABLE: {
                calcMethod = how2calculate;
                System.out.println("Calc method: FRQTABLE");
                break;
            }
            // Grouped Method not part of current assignment so it is allowed
            // to drop through to the default case (no break statement)
            case GROUPED:
            default: {
                calcMethod = INVALID_CALC_METHOD;
                System.out.println("ERROR: Standard Deviation Calculation Method either UNIMPLEMENTED, or UNKNOWN");
                break;
            }
        }
    }


    // You need to write the code to set the class variable here
    // you should use a switch/case statement that allows you to
    // check if "how2calculate" is DISCRETE, FRQTABLE, GROUPED, if
    // "how2calculate" is not one of these then it must be set to
    // INVALID_CALC_METHOD
    public void addNewDataItem(int dataItem) {

        if ((sdItems == INVALID)) {
            sdItems = 0;
        }

        switch (getCalcMethod()) {

            case DISCRETE: {

                Data[sdItems] = dataItem;
                sdItems++;
                break;
            }

            case FRQTABLE: {

                if ((getMin() != INVALID_RANGE) && (getMax() != INVALID_RANGE)) {

                    if ((dataItem < getMin()) || (dataItem > getMax())) {

                        System.out.printf("ERROR: RANGE VIOLATION - Data Value ( %5.0 ), User Values: Minimum ( %5.0f ), Maxium ( %5.0f )",
                                dataItem, (double) getMin(), (double) getMax());

                    } else if ((dataItem < MINDATA) || (dataItem > MAXDATA)) {

                        System.out.printf("ERROR: RANGE VIOLATION - Data Value ( %5.0 ), System Values: DATAMIN ( %5.0f ), DATAMAX ( %5.0f )",
                                dataItem, (double) MINDATA, (double) MAXDATA);

                    } else {

                        Data[dataItem] = Data[dataItem] + 1;
                        sdItems++;

                    }
                } else {
                    System.out.printf("ERROR: RANGE VIOLATION - Range values not set");
                }
                break;
            }

            case GROUPED: {
            }

            default: {
                sdItems = INVALID;
                calcMethod = INVALID_CALC_METHOD;
                System.out.println("ERROR: Standard Deviation Calculation Method either UNIMPLEMENTED, or UNKNOWN");
                break;
            }
        }

    }


    public int getCalcMethod() {
        return calcMethod;
    }


    public void setMin(int userMin) {

    }

    public int getMin() {
        return getMin();
    }

    public void setMax(int userMax) {
    }

    public int getMax() {
        return getMax();
    }


    // ******************************************************************************
    // ******************************************************************************

    // --------------------------------------------------
    // The following method (procedure) will take a new data item (a parameter)
    // and add it into the 1 Dimensional Array of data values to be used later.
    //
    //      Pre-Conditions:
    //          - none
    //


    // --------------------------------------------------
    // The following method (function) will return the total number of data
    // items currently stored

    //      Pre-Conditions:
    //          - none
    //
    public int getNumberOfDataItems() {
        return sdItems;
    }


    // --------------------------------------------------
// The following method (function) returns a double precision value which
// is the average of all of the data values
//
//      Pre-Conditions:
//          - at least one data has been added
//

    public double calcAverage() {
        double total = 0;

        switch (getCalcMethod()) {
            case DISCRETE: {
                if (sdItems != INVALID) {

                    // Add all data values together (recall sdItems is the number of data items in the
                    // Data storage array
                    //
                    for (int i = 0; i < sdItems; i++) {
                        total = total + Data[i];
                    }

                    // calculate the average, the "(double)" below instructs the computer to treat
                    // the integer value of "sdItems" as a real number
                    //
                    sdAve = total / (double) sdItems;
                } else {
                    sdAve = INVALID;
                }
                break;
            }
            // returns the calculated average
            case FRQTABLE: {
                sdAve = total;
                break;
            }

            case GROUPED: {
                break;
            }

            default: {
                sdItems = INVALID;
                sdAve = INVALID;
                calcMethod = INVALID_CALC_METHOD;
                System.out.println("ERROR: Standard Deviation Calculation Method either UNIMPLEMENTED, or UNKNOWN");
                break;
            }

        }
        return sdAve;
    }


    // --------------------------------------------------
// The following method (function) returns a double precision value which is the Variance of all
// of the data values. If there is no data, or if the average has not been calculated
// then it returns INVALID
//
//      Pre-Conditions:
//          - at least one data has been added
//          - the average must have been calculated
//
//

    public double calcVariance() {
        double total = 0;
        double difference = 0;
        double diffSquared = 0;

        switch (getCalcMethod()) {
            case DISCRETE: {

                // Checks that data entry, and average have been done
                //
                if ((sdItems != INVALID) || (sdAve != INVALID)) {

                    // Loop over all data and calculate variance
                    //

                    for (int i = 0; i < sdItems; i++) {
                        difference = (Data[i] - sdAve);
                        diffSquared = Math.pow(difference, 2);
                        total = total + diffSquared;

                        // The calculation above could have been done on a single line
                        // i.e. total += Math.pow( (Data[i] - sdAve), 2)
                        // but it is easier to understand if done on separate lines.
                    }

                    // to calculate the variance we need to divide by the number of data items,
                    // the "(double)" below instructs the computer to treat the integer value
                    // of "sdItems" as a real number
                    //
                    sdVar = total / (double) sdItems;

                } else {
                    // Pre-Conditions have not been met
                    sdVar = INVALID;
                }
            }

            case FRQTABLE: {

            }

            case GROUPED: {

            }

            default: {
            }

        }

        return sdVar;
    }

    // --------------------------------------------------
// The following method (function) returns a double precision value which is the Standard
// Deviation of all of the data values. If there is no data, no average, or if
// the variance has not been calculated then it returns INVALID
//
//      Pre-Conditions:
//          - at least one data has been added
//          - the average must have been calculated
//          - the variance must have been calculated
//
    public double calcStandardDeviation() {

        double calcStandardDeviation;

        // Checks that data entry, average, and variance have been done
        if ((sdItems != INVALID) || (sdAve != INVALID) || (sdVar != INVALID)) {
            sdDev = Math.sqrt(sdVar);
        } else {
            // Pre-Conditions have not been met
            sdDev = INVALID;
        }

        return sdDev;
    }
}

    /* private void SwitchFrameWork () {

        switch (getCalcMethod()) {
            case DISCRETE: {
                // what you need to do
                break;
            } //end of DISCRETE

                case FRQTABLE: {
                    // what you need to do
                    break;
                } //end of FRQTABLE

                case GROUPED: {
                    // what you need to do
                    break;
                } //end of GROUPED

                default: {
                    // what to do if calcmethod is not an expected value
                    break;
  */