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
import java.awt.event.*;

public class Main
        extends JFrame
        implements ActionListener     // For the JButton handling and item selection in the JComboBox
{

    // Set up the application
    public static void main(String[] args) {

        Main window = new Main();
        //window.setSize(300,200);       // Width, height of window
        //window.pack();
        window.setSize(1000,1000);
        window.setLocation(0,00);   // Where on the screen
        window.setVisible(true);

    } // main

    // Constructor
    public Main() {

        setTitle("checkpoint 3");
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // For main close box click event
        init();                                    // Set up the GUI and data

    } // FileDemo

    private DataStore theData = new DataStore();         // Create a new DataStore object to hold all the data

    //adding tabbed panels
    private JTabbedPane switchable = new JTabbedPane(); //this will contain the switchable panels
    private JPanel
        editTreasuresPanel, imagePanel;

    private JButton doubleButton = new JButton("Double"), // The buttons for the GUI
            storeButton  = new JButton("Store"),
            editButton = new JButton("Edit");

    private JComboBox<String> textChoice = new JComboBox<String>();   // For displaying a selectable text list
    private JTextField numberField = new JTextField(10), // For displaying the number associated with the selected text item
            descField = new JTextField(10); //description field

    private JLabel imageLabel = new JLabel(); //label for my image inside init()

    // Read the data file into the object theData, and set up the GUI
    public void init() {

        // Instruct the DataStore object theData to read in the data file
        theData.readData();

        //Set up the GUI
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        //creating editTreasures panel
        editTreasuresPanel = new JPanel();
        editTreasuresPanel.setBackground(Color.red);

        editTreasuresPanel.add(doubleButton); //adding the double button
        doubleButton.addActionListener(this);

        editTreasuresPanel.add(storeButton); //adding store button
        storeButton.addActionListener(this);

        editTreasuresPanel.add(editButton); //adding edit button
        editButton.addActionListener(this);

        theData.fillChoice(textChoice); //Tell theData to fill up the drop-down JComboBox textChoice with the text items,
        editTreasuresPanel.add(textChoice); //add textChoice to the display,
        textChoice.addActionListener(this); //and set it to notify actionPerformed when an item is selected

        editTreasuresPanel.add(numberField); //adding the number associated with the selected text item field
        numberField.setEditable(false);

        editTreasuresPanel.add(descField); //adding the description field

        // make sure that initial display in numberField
        // is consistent with the initially selected text item
        updateNumberField();

        /*
        // The buttons notify actionPerformed when clicked
        contentPane.add(doubleButton);
        doubleButton.addActionListener(this);
        contentPane.add(storeButton);
        storeButton.addActionListener(this);

        // Tell theData to fill up the drop-down JComboBox textChoice with the text items,
        // add textChoice to the display,
        // and set it to notify actionPerformed when an item is selected
        theData.fillChoice(textChoice);
        contentPane.add(textChoice);
        textChoice.addActionListener(this);

        // Display numberField, and disable user editing
        contentPane.add(numberField);
        numberField.setEditable(false);

        contentPane.add(editButton);
        editButton.addActionListener(this);

        contentPane.add(descField);
        */

        //creating the imagePanel
        imagePanel = new JPanel();
        editTreasuresPanel.setBackground(Color.green);

        //giving image label initial image value
        imageLabel.setIcon(new ImageIcon(theData.lookupImage((String)textChoice.getSelectedItem())));
        //adding image to pane
        imagePanel.add(imageLabel);


        //adding the panels to JTabbedPane
        switchable.add("edit treasures", editTreasuresPanel);
        switchable.add("image panel", imagePanel);
        contentPane.add(switchable);

    } // init

    // Handle button presses and item selection events
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == doubleButton) {
            // Need to double the currently selected integer
            // First find out Which text item is selected
            String chosen = (String)textChoice.getSelectedItem();
            // Now tell theData to find chosen in its data store and to double the number associated with it
            theData.doubleNumber(chosen);
            // Finally update the number field display
            updateNumberField();
        }
        if (e.getSource() == storeButton)
            // Tell theData to write its contents back to the file
            theData.writeData();
        if (e.getSource() == editButton) {
            // Replaces the selection of the combo box with the new description
            textChoice.insertItemAt(descField.getText(), textChoice.getSelectedIndex());
            textChoice.removeItemAt(textChoice.getSelectedIndex());
            textChoice.setSelectedIndex(textChoice.getSelectedIndex());
            theData.setDescription(textChoice.getSelectedIndex(), descField.getText());
            // Empties text field and updates number field
            descField.setText("");
            updateNumberField();
        }
        if (e.getSource() == textChoice) {
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
            if (textChoice.getSelectedIndex() == -1) {   // -1 indicates "nothing selected"
                // No item is currently selected
                numberField.setText("");
            }
            else
            {
                //updating the number field
                updateNumberField();

                //updating the imageLabel to display the image
                imageLabel.setIcon(new ImageIcon(theData.lookupImage((String)textChoice.getSelectedItem())));
            }
        }

    } // actionPerformed

    // Display the correct integer from the data store in numberField
    // - the one associated with the currently selected text,
    // or set to blank if nothing is selected
    private void updateNumberField() {

        // First find out which text item is selected
        String chosen = (String)textChoice.getSelectedItem();   // Will be null if nothing selected
        if (chosen == null) {
            numberField.setText("");
        }
        else {
            // Then ask theData to look up the chosen text in its data store, and to return the associated number
            int theNumber = theData.lookupNumber(chosen);
            // Finally display the number in the number field
            numberField.setText(Integer.toString(theNumber));
        }

    } // updateNumberField

} // class FileDemo