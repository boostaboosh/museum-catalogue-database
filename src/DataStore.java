/*
   DataStore class supporting the main FileDemo program

   Each instance of this class looks after a collection of text/number pairs in two arrays:
   The string array "texts" contains text items from element 0 up and including the element
   indicated by the variable "top".
   The int array "numbers" is similar.
   Each specific element in "texts" has a corresponding int in "numbers" at the same
   array index position.

   Initially the collection is empty. "top" is initially -1 to indicate this.

   Methods available are:

   o readData: The data storage arrays are in effect emptied by setting top to -1.
      Then the contents of file data.txt are read in. Each line in the
      file consists of some text, then a tab, then a number.
      Each line of text is split up into its text and number components,
      and the text/number pair is added to the arrays (by calls of addEntry).

   o writeData: The entire current contents of the data store is written
      out to file data.txt, overwriting its previous contents.
      The output format precisely mirrors the original data format in
      data.txt, so that the data can subsequently be read back in by readData.

   o addEntry: When called, adds a given text/number pair to the data store
      by increasing "top" and inserting the new data into the elements
      indicated by "top".

   o lookupNumber: Given a text, seeks it in the texts array and returns the
      corresponding number from the numbers array.

   o doubleNumber: Given a text, seeks it in the texts array and doubles the
     number in the corresponding element of the numbers array.

   o fillChoice: Fills up a given JComboBox widget with the contents of the texts array.

*/

import javax.naming.PartialResultException;
import javax.swing.*;
import java.awt.*;
import java.io.*;

class DataStore
    extends JFrame{

    private final int MAX = 50;                          // Used as limit on arrays

    //adding arrays for data storage
    public int[] artifactIDs = new int[MAX];
    public String[] artifactNames = new String[MAX];
    public String[] artifactImageFileNames = new String[MAX];
    public int[] artifactFloorNumbers = new int[MAX];
    public String[] artifactRooms = new String[MAX];
    private int numberOfEntries = 0;                                // Entries indexed 0..top of the arrays are occupied

    // No constructor needed here: all the initialization is in the declarations above

    // Read data from data.txt, split up each line and store the data in the arrays.
    // If any ill formatted data, or other file problem is encountered,
    // the reading is abandoned, but storing all correctly read data up to that point.
    public void readData() {

        numberOfEntries = 0;   // Empty current data storage by adjusting the top index.
        try {
            BufferedReader input = new BufferedReader(new FileReader("treasures.txt"));
            String dataLine;  // To receive each line from the file

            /*
            //adding a counter so i can add the image filenames to their array
            //int counter = 0;
            actually I don't need this because i use the addEntry method
            */

            while ((dataLine = input.readLine()) != null) {  // Get next line from file

                // Find the position of the *first* tab for splitting the line
                int tabPos1 = dataLine.indexOf('\t');

                int tabPos2 = dataLine.indexOf('\t',tabPos1+1);

                int tabPos3 = dataLine.indexOf('\t',tabPos2+1);

                int tabPos4 = dataLine.indexOf('\t', tabPos3+1);

                // Check, in case no tab was found (indexOf returns -1)
                if (tabPos1 < 0) {
                    System.out.println("No tab in data line");
                    break;                                  // Bad data: read no more!
                }

                // Split the line: extract the parts of the string up to the tab,
                // and from after the tab to the end of the string
                String artifactIDPart = dataLine.substring(0, tabPos1);
                String artifactNamePart = dataLine.substring(tabPos1+1, tabPos2);
                //adding the image part
                String imagePart = dataLine.substring(tabPos2+1, tabPos3);
                //adding the floor part
                String floorPart = dataLine.substring(tabPos3+1, tabPos4);
                //adding the room part
                String roomPart = dataLine.substring(tabPos4+1);

                // Convert artifactIDPart to a proper int for storing
                int artifactID = 0;   // To hold the converted number
                try {
                    artifactID = Integer.parseInt(artifactIDPart);      // Convert
                }
                catch (NumberFormatException ex) {
                    System.out.println("Bad data in number");
                    break;                                 // Bad data: read no further!
                }

                //convert floorPart to a proper int for storing
                int floor = 0;
                try
                {
                    floor = Integer.parseInt(floorPart);
                }
                catch (NumberFormatException ex)
                {
                    System.out.println("Bad data in number");
                    break;                                 // Bad data: read no further!
                }

                // We now have the text and number parts,
                // so store the data obtained as next entry in the arrays
                addEntry(artifactID, artifactNamePart, imagePart, floor, roomPart);

                //we also have to add the image file names to their array, and the
                //artifactImageFileNames[counter] = imagePart;
                //counter++;

            }

            input.close();                               // File finished, arrays full or bad data, so close file
        }
        catch (IOException ex) {
            System.out.println("File reading error");    // File handling error: read no further
        }

    } // readData

    // Write the array contents out to data.txt, in a compatible format for readData
    public void writeData() {

        try {
            BufferedWriter output = new BufferedWriter(new FileWriter("treasures.txt")); //treasures.txt instead of data text file so that new entries show up in filter panel. Scary! don't make mistakes.

            // Process each stored text/number pair from 0 to top
            for (int i = 0; i < numberOfEntries; i++) {

                // Build a correctly structured string as an output line:
                // the two corresponding array items are separated by a tab
                String theArtifactID = Integer.toString(artifactIDs[i]);
                String theArtifactFloor = Integer.toString(artifactFloorNumbers[i]);
                String outputLine = theArtifactID + "\t" + artifactNames[i]+ "\t" + artifactImageFileNames[i] + "\t" + theArtifactFloor + "\t" + artifactRooms[i];
                // And output the line to the file, followed by a new line
                output.write(outputLine);
                output.newLine();

            }

            output.close();
        }
        catch (IOException ex) {
            System.out.println("File writing error");    // File management error: write no more
        }

    } // writeData

    // Add one more text/number entry to the arrays, if there is space.
    // If there is no space left, the new data is simply ignored, with no error report
    public void addEntry(int artifactID, String artifactName, String artifactImageFileName, int artifactFloor, String artifactRoom) {
        for (int i = 0; i < numberOfEntries; i++){
            if (artifactID == artifactIDs[i]){
                JOptionPane.showMessageDialog(null, "error: ID "+artifactID+" is already taken by artifact "+artifactNames[i]+". You cannot have more than 1 entry with the same artifact ID");
                return;
            }
        }

        if (numberOfEntries >= MAX) return;     // If array is full don't add data

        artifactIDs[numberOfEntries] = artifactID;       // There is space, so put the data into that space
        artifactNames[numberOfEntries] = artifactName;
        artifactImageFileNames[numberOfEntries] = artifactImageFileName;
        artifactFloorNumbers[numberOfEntries] = artifactFloor;
        artifactRooms[numberOfEntries] = artifactRoom;

        // and add new items
        numberOfEntries++;                        // Adjust pointer to next free space
    } // addEntry

    // Search for the given text in the texts array,
    // and return the corresponding number, or 0 if not found.
    // (0 is not a clever way to deal with "not found" but it will do for now!)
    public int lookupNumber(String text) {

        // Scan all the entries
        for (int i = 0; i < numberOfEntries; i++)

            if (text.equals(artifactNames[i]))   // Check next text
                // Found the required entry! Return the corresponding number
                return artifactIDs[i];

        // Execution will only arrive here if didn't find the required entry
        return 0;

    } // lookupNumber

    // Search for the given text in the texts array,
    // and double the corresponding number, or no action if not found
    public void doubleNumber(String text) {

        // Scan all the entries
        for (int i = 0; i < numberOfEntries; i++)

            if (text.equals(artifactNames[i])) {   // Check next text
                // Found it, so double the number
                artifactIDs[i] = 2* artifactIDs[i];
                // And leave the loop and method immediately because the work is done!
                return;
            }

    } // doubleNumbers

    // Fill up the given choiceList with the contents of the texts array
    public void fillStringChoice(JComboBox choiceList, String[] array)
    {
        // Empty the current entries in the choiceList
        choiceList.removeAllItems();

        choiceList.addItem(array[0]);
        for(int i = 1; i < numberOfEntries;i++)
        {
            int occurrences = 0;
            boolean uniqueBool = true;
            for (int j = 0; j < i; j++) {
                if (array[i].equals(array[j]))
                {
                    occurrences++;
                    if (occurrences==1)
                    {
                        uniqueBool = false;
                        break;
                    }
                }
            }
            if (uniqueBool)
            {
                choiceList.addItem(array[i]);
            }
        }

        // Finally if there is at least one entry, select the first one
        if (numberOfEntries > 0)
            choiceList.setSelectedIndex(0);

    } // fillStringChoice

    public void fillIntChoice(JComboBox choiceList, int[] array)
    {
        choiceList.removeAllItems();

        for (int i = 0; i < numberOfEntries; i++)
        {
            boolean valueInChoiceList = false;

            for (int j = 0; j < i; j++)
            {
                if (array[i] == array[j])
                {
                    valueInChoiceList = true;
                    break;
                }
            }
            if (!valueInChoiceList)
            {
                choiceList.addItem(array[i]);
                System.out.println("value added:"+array[i]);
            }
        }

        // selects the first item in the combobox list to be the selected item when we run the program
        if (numberOfEntries > 0) {
            choiceList.setSelectedIndex(0);
        }
    }

    // Set the appropriate text array position with the new description
    public void setDescription(int i, String desc){
        artifactNames[i] = desc;
    }


    // Search for the given text in the texts array,
    // and return the corresponding file name, or an empty string if not found.
    public String lookupImage(String artifactName) {
    if (artifactName != null) {
    // Scan all the entries
    for (int i = 0; i < numberOfEntries; i++)

        if (artifactName.equals(artifactNames[i]))   // Check next item
            // Found the required entry! Return the corresponding file name
            return artifactImageFileNames[i];
    }
        // Execution will only arrive here if didn't find the required entry
        return ("");

    } // lookupImage

    public void filter (JComboBox floorParameter, JComboBox roomParameter, JComboBox resultsList)
    {
        if ( (floorParameter.getSelectedItem() != null) && (roomParameter.getSelectedItem() != null)) {

            resultsList.removeAllItems();

            int floor = (int) floorParameter.getSelectedItem();
            String room = (String) roomParameter.getSelectedItem();

            String[] artifactResults = new String[MAX];
            int resultsCounter = 0;

            for (int i = 0; i < numberOfEntries; i++) {
                if (artifactFloorNumbers[i] == floor && artifactRooms[i].equals(room)) {
                    artifactResults[resultsCounter] = artifactNames[i];
                    resultsCounter++;
                }
            }

            if (resultsCounter == 0) {
                resultsList.addItem("no results");
            } else {
                stringArrayInJComboBox(artifactResults, resultsCounter, resultsList);
            }
        }
    }

    public void stringArrayInJComboBox (String[] stringArray, int arrayCounter, JComboBox choiceList)
    {
        for (int i = 0; i<arrayCounter; i++)
        {
            choiceList.addItem(stringArray[i]);
        }

        // selects the first item in the combobox list to be the selected item when we run the program
        if (arrayCounter > 0) {
            choiceList.setSelectedIndex(0);
        }
    }

    public void printFilterSelection (JComboBox floorSelection, JComboBox roomSelection, JComboBox artifactSelection, JLabel imageSelection)
    {
        int floorNumber = (int) floorSelection.getSelectedItem();
        String roomLetter = (String) roomSelection.getSelectedItem();
        String artifactName = (String) artifactSelection.getSelectedItem();
        Icon artifactImage = imageSelection.getIcon();

        //print stuff
        //print available and selected floors, rooms, and artifacts.

        PrintJob printFilterSelectionJob = getToolkit().getPrintJob(this,"Print Filter Selection",null); // This causes the print dialog box to pop up
        if (printFilterSelectionJob == null) {                                     // Was the print cancelled?
            return;                                          // If it was, just do nothing
        }
        Graphics g = printFilterSelectionJob.getGraphics();                        // Grab a page to draw ("print") on
        g.translate(50,50);                                  // To move the printing away from top left corner of page

        //Starting x and y of "paint brush"
        int brushX = 10;
        int brushY = 20;

        String[] availableSubjectType = {"floors", "rooms", "artifacts"};
        JComboBox[] availableSubjects = {floorSelection, roomSelection, artifactSelection};

        String[] selectedSubjectType = {"floor", "room", "artifact"};
        String[] selectedSubject = {String.valueOf(floorNumber), roomLetter, artifactName};

        for (int i=0; i<(selectedSubject.length); i++)
        {
            //available floors/rooms/artifacts
            g.setFont(new Font("SansSerif", Font.PLAIN, 14));
            g.drawString("The " + availableSubjectType[i] + " available were: " + JComboBoxToStringListWithCommas(availableSubjects[i]), brushX, brushY);
            brushY += 30;

            //selected floors/rooms/artifacts
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            g.drawString("The " + selectedSubjectType[i] + " selected was: " + selectedSubject[i], brushX, brushY);
            brushY += 50;
        }

        g.dispose();                                         // This actually prints the page
        printFilterSelectionJob.end();
    }

    public String JComboBoxToStringListWithCommas(JComboBox choiceList)
    {
        //return String containing the items in choiceList separated by commas
        int numberOfItems = choiceList.getItemCount();

        String listWithCommas = "";

        for (int i = 0; i < numberOfItems; i++)
        {
            listWithCommas += String.valueOf(choiceList.getItemAt(i));

            if (i < (numberOfItems-1))
            {
                listWithCommas += ", ";
            }
            else
            {
                listWithCommas += ".";
            }
        }

        return listWithCommas;
    }

    public void addNewArtifact(String IDField, String artifactNameField, File artifactImageFile, String artifactFloorNumberField, String artifactRoomLetterField)
    {
        int newID;
        String newArtifactName;
        File newArtifactImageFile;
        int newArtifactFloorNumber;
        String newArtifactRoomLetter;

        try{
            newID = Integer.parseInt(IDField);
        }
        catch(NumberFormatException exception)
        {
            JOptionPane.showMessageDialog(null, "error: ID must be a whole number!");
            return;
        }

        try{
            newArtifactFloorNumber = Integer.parseInt(artifactFloorNumberField);
        }
        catch(NumberFormatException exception)
        {
            JOptionPane.showMessageDialog(null, "error: artifact must be a whole number!");
            return;
        }

        newArtifactName = artifactNameField;
        newArtifactImageFile = artifactImageFile;
        newArtifactRoomLetter = artifactRoomLetterField;

        //adding new artifact data to arrays
        addEntry(newID, newArtifactName, newArtifactImageFile.getName(), newArtifactFloorNumber, newArtifactRoomLetter);
        //adding to file
        writeData();
    }

} // class DataStore

