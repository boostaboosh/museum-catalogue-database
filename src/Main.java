/*
   File reading/writing demo application

   Reads in file data.txt comprising lines with two items of data on each:
   some text, and an integer, separated by a tab character. The data is read in
   and stored by an instance of class DataStore.

   The data is displayed: A choice list (JComboBox) is displayed with all the texts,
   and a JTextField shows the number associated with the currently selected text.

   When the Double button is clicked, the stored integer associated with the currently
   selected text is doubled in the array where it is located, and the screen display is updated.

   When the Store button is clicked, all the data is written back to the file data.txt.
   The data is NOT written to the file automatically when the program exits!

*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main
        extends JFrame
        implements ActionListener     // For the JButton handling and item selection in the JComboBox
{

    // Set up the application
    public static void main(String[] args) {

        Main window = new Main();
        //window.setSize(300,200);       // Width, height of window
        //window.pack();
        window.setSize(950,500);
        window.setLocation(0,0);   // Where on the screen
        window.setVisible(true);

    } // main

    // Constructor
    public Main() {

        setTitle("checkpoint 6");
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // For main close box click event
        init();                                    // Set up the GUI and data

    } // FileDemo

    private DataStore theData = new DataStore();         // Create a new DataStore object to hold all the data

    //adding tabbed panels
    private JTabbedPane tabbedPane = new JTabbedPane(); //this will contain the switchable panels
    private JPanel
            filterAndPrintPanel, addNewArtifactPanel, editArtifactsPanel, imagePanel;

    //filterAndPrintPanel GUI stuff
    private JButton printFilterButton = new JButton("Print Selection");
    private JComboBox floorSelection = new JComboBox();
    private JComboBox roomSelection = new JComboBox();
    private JComboBox artifactSelection = new JComboBox();
    private JLabel artifactImage = new JLabel();

    //editTreasuresPanel GUI stuff
    private JLabel IDLabel = new JLabel("ID"),
        artifactNameLabel = new JLabel("artifact name"),
        artifactImageFileLabel = new JLabel("artifact image file"),
        artifactFloorNumberLabel = new JLabel("artifact floor number"),
        artifactRoomLetterLabel = new JLabel("artifact room letter");

    /*
    private JComboBox floorSelection = new JComboBox(),
        roomSelection = new JComboBox(),
        artifactSelection = new JComboBox(),
        roomSelection = new JComboBox(),
        artifactSelection = new JComboBox();
    */

    private JTextField artifactIDField = new JTextField(10),
        artifactNameField = new JTextField(20),
        //artifactImageFileField = new JTextField(25),
        artifactFloorNumberField = new JTextField(10),
        artifactRoomLetterField = new JTextField(10);

    private JButton addNewArtifactButton = new JButton("add new artifact to the treasure set"),
        addNewArtifactImageFileButton = new JButton("add new artifact image file");
    File selectedFile;

    //old edit stuff
    private JButton doubleArtifactIDButton = new JButton("Double Artifact ID"), // The buttons for the GUI
            storeNewArtifactIDButton = new JButton("Store Artifact ID"),
            editArtifactNameButton = new JButton("Edit Artifact Name");

    private JComboBox<String> artifactChoiceList = new JComboBox<String>();   // For displaying a selectable text list
    private JTextField displayArtifactIDField = new JTextField(10), // For displaying the number associated with the selected text item
            artifactRenameField = new JTextField(10); //description field


    //imagePanel GUI stuff
    private JLabel imageLabel = new JLabel(); //label for my image inside init()

    // Read the data file into the object theData, and set up the GUI
    public void init() {

        // Instruct the DataStore object theData to read in the data file
        theData.readData();

        //Set up the GUI
        Container contentPane = getContentPane();
        //contentPane.setLayout(new FlowLayout());

        //creating the filterPanel
        filterAndPrintPanel = new JPanel();
        filterAndPrintPanel.setBackground(Color.green);

        filterAndPrintPanel.add(printFilterButton);
        printFilterButton.addActionListener(this);

        theData.fillIntChoice(floorSelection, theData.artifactFloorNumbers);
        filterAndPrintPanel.add(floorSelection);
        floorSelection.addActionListener(this);

        theData.fillStringChoice(roomSelection, theData.artifactRooms);
        filterAndPrintPanel.add(roomSelection);
        roomSelection.addActionListener(this);

        theData.filter(floorSelection, roomSelection, artifactSelection);
        filterAndPrintPanel.add(artifactSelection);
        artifactSelection.addActionListener(this);

        filterAndPrintPanel.add(artifactImage);
        artifactImage.setIcon(new ImageIcon(theData.lookupImage((String) artifactSelection.getSelectedItem())));

        //creating editTreasures panel
        addNewArtifactPanel = new JPanel();
        addNewArtifactPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //row 1
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 40;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        addNewArtifactPanel.add(IDLabel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 40;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        addNewArtifactPanel.add(artifactNameLabel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 40;
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        addNewArtifactPanel.add(artifactImageFileLabel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 40;
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        addNewArtifactPanel.add(artifactFloorNumberLabel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 40;
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        addNewArtifactPanel.add(artifactRoomLetterLabel, constraints);

        //row 2
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 40;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        addNewArtifactPanel.add(artifactIDField, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 40;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        addNewArtifactPanel.add(artifactNameField, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 40;
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        addNewArtifactPanel.add(addNewArtifactImageFileButton, constraints);
        addNewArtifactImageFileButton.addActionListener(this);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 40;
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        addNewArtifactPanel.add(artifactFloorNumberField, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 40;
        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        addNewArtifactPanel.add(artifactRoomLetterField, constraints);

        //row 3
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 40;
        constraints.gridx =0;
        constraints.gridy = 2;
        constraints.gridwidth = 5;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        addNewArtifactPanel.add(addNewArtifactButton, constraints);
        addNewArtifactButton.addActionListener(this);

        /*
        editTreasuresPanel.setBackground(Color.red);

        editTreasuresPanel.add(artifactRenameField); //adding the description field

        theData.fillStringChoice(artifactChoiceList, theData.artifactNames); //Tell theData to fill up the drop-down JComboBox textChoice with the text items,
        editTreasuresPanel.add(artifactChoiceList); //add textChoice to the display,
        artifactChoiceList.addActionListener(this); //and set it to notify actionPerformed when an item is selected

        editTreasuresPanel.add(editArtifactNameButton); //adding edit button
        editArtifactNameButton.addActionListener(this);

        editTreasuresPanel.add(displayArtifactIDField); //adding the number associated with the selected text item field
        displayArtifactIDField.setEditable(false);

        editTreasuresPanel.add(doubleArtifactIDButton); //adding the double button
        doubleArtifactIDButton.addActionListener(this);

        editTreasuresPanel.add(storeNewArtifactIDButton); //adding store button
        storeNewArtifactIDButton.addActionListener(this);

        // make sure that initial display in numberField
        // is consistent with the initially selected text item
        updateNumberField();
        */

        /*
        //creating the imagePanel
        imagePanel = new JPanel();
        imagePanel.setBackground(Color.red);

        //giving image label initial image value
        imageLabel.setIcon(new ImageIcon(theData.lookupImage((String) artifactChoiceList.getSelectedItem())));
        //adding image to pane
        imagePanel.add(imageLabel);
        */


        //adding the panels to JTabbedPane
        tabbedPane.add("filter and print artifacts", filterAndPrintPanel);
        tabbedPane.add("add new artifact", addNewArtifactPanel);
        //tabbedPane.add("image panel", imagePanel);
        contentPane.add(tabbedPane);

    } // init

    // Handle button presses and item selection events
    public void actionPerformed(ActionEvent e) {
        /*
        if (e.getSource() == doubleArtifactIDButton) {
            // Need to double the currently selected integer
            // First find out Which text item is selected
            String chosen = (String) artifactChoiceList.getSelectedItem();
            // Now tell theData to find chosen in its data store and to double the number associated with it
            theData.doubleNumber(chosen);
            // Finally update the number field display
            updateNumberField();
        }
        if (e.getSource() == storeNewArtifactIDButton)
            // Tell theData to write its contents back to the file
            theData.writeData();
        if (e.getSource() == editArtifactNameButton) {
            // Replaces the selection of the combo box with the new description
            artifactChoiceList.insertItemAt(artifactRenameField.getText(), artifactChoiceList.getSelectedIndex());
            artifactChoiceList.removeItemAt(artifactChoiceList.getSelectedIndex());
            artifactChoiceList.setSelectedIndex(artifactChoiceList.getSelectedIndex());
            theData.setDescription(artifactChoiceList.getSelectedIndex(), artifactRenameField.getText());
            // Empties text field and updates number field
            artifactRenameField.setText("");
            updateNumberField();
        }
        if (e.getSource() == artifactChoiceList) {
            // Handle an event from the combo box list: it might be an item
            // selection, or deselection or some other change to the combo box.
            // Note: Deselection occurs when changing changing a selection, or
            // removing the currently selected item - including when all items
            // are removed from the list. In this situation there will not be a
            // selected item so we must check and avoid processing when there is
            // no selected item.
            // What needs to be done is for the number field to be brought up to date
            // with the new currently selected text, or set to blank if nothing is selected.
            // The check for "nothing selected" can be done here, or in updateNumberField
            // (in this example code it is done in both).
            if (artifactChoiceList.getSelectedIndex() == -1) {   // -1 indicates "nothing selected"
                // No item is currently selected
                displayArtifactIDField.setText("");
            }
            else
            {
                //updating the number field
                updateNumberField();

                //updating the imageLabel to display the image
                imageLabel.setIcon(new ImageIcon(theData.lookupImage((String) artifactChoiceList.getSelectedItem())));
            }
        }
        */

        //filter and print artifacts stuff
        if (e.getSource() == floorSelection)
        {
            //update filter
            theData.filter(floorSelection, roomSelection, artifactSelection);
        }
        if (e.getSource() == roomSelection)
        {
            //update filter
            theData.filter(floorSelection, roomSelection, artifactSelection);
        }
        if (e.getSource() == artifactSelection)
        {
            //update output
            artifactImage.setIcon(new ImageIcon(theData.lookupImage((String) artifactSelection.getSelectedItem())));
        }
        if (e.getSource() == printFilterButton)
        {
            //print selected floor, rooms on said floor, and the image of the selected item
            theData.printFilterSelection(floorSelection, roomSelection, artifactSelection, artifactImage);
        }

        //edit treasures stuff
        if (e.getSource() == addNewArtifactImageFileButton)
        {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose this file: " + chooser.getSelectedFile().getName());

                selectedFile = chooser.getSelectedFile();
            }

            /*
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & GIF Images", "jpg", "gif");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(parent);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getName());
            }
            */
        }

        if (e.getSource() == addNewArtifactButton)
        {
            //add new artifact to treasures dataset
            theData.addNewArtifact(artifactIDField.getText(), artifactNameField.getText(), selectedFile, artifactFloorNumberField.getText(), artifactRoomLetterField.getText());

            theData.fillIntChoice(floorSelection, theData.artifactFloorNumbers);

            theData.fillStringChoice(roomSelection, theData.artifactRooms);

            theData.filter(floorSelection, roomSelection, artifactSelection);
        }

    } // actionPerformed

    // Display the correct integer from the data store in numberField
    // - the one associated with the currently selected text,
    // or set to blank if nothing is selected
    private void updateNumberField() {

        // First find out which text item is selected
        String chosen = (String) artifactChoiceList.getSelectedItem();   // Will be null if nothing selected
        if (chosen == null) {
            displayArtifactIDField.setText("");
        }
        else {
            // Then ask theData to look up the chosen text in its data store, and to return the associated number
            int theNumber = theData.lookupNumber(chosen);
            // Finally display the number in the number field
            displayArtifactIDField.setText(Integer.toString(theNumber));
        }

    } // updateNumberField

} // class FileDemo