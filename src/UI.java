/*
   File reading/writing application

   Reads in file treasures.txt comprising lines with items of data on each:
   text and integers, separated by tab characters. The data is read in
   and stored by an instance of class DataStore.

   The data is displayed in the GUI, and the user has options to filter through it, add items, see images of items, and print his selection.
*/


import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;


public class UI extends JFrame
{
    // Set up the application
    public static void main(String[] args) {
        UI window = new UI();
        window.setVisible(true);
    } // main

    // Constructor
    public UI() {
        setLAndF();
        setTitle("museum manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // For main close box click event
        dataSetup();
        initComponents();
    }

    public void setLAndF(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            // handle exception
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            // handle exception
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            // handle exception
            e.printStackTrace();
        }
    }

    public void dataSetup(){
        theData.readData();
    }

    private DataStore theData = new DataStore();         // Create a new DataStore object to hold all the data

    private void floorFilterComboBoxActionPerformed(ActionEvent e) {
        //update filter
        theData.filter(floorFilterComboBox, roomFilterComboBox, filterResultsComboBox);
    }

    private void roomFilterComboBoxActionPerformed(ActionEvent e) {
        //update filter
        theData.filter(floorFilterComboBox, roomFilterComboBox, filterResultsComboBox);
    }

    private void filterResultsComboBoxActionPerformed(ActionEvent e) {
        //update image
        artifactImageDisplayLabel.setIcon(new ImageIcon(theData.lookupImage((String) filterResultsComboBox.getSelectedItem())));
    }

    /*
    private void filterResultsListValueChanged(ListSelectionEvent e) {
        //update output
        artifactImageDisplayLabel.setIcon(new ImageIcon(theData.lookupImage((String) filterResultsComboBox.getSelectedItem())));
    }
    */

    private void printSelectionButtonActionPerformed(ActionEvent e) {
        //print selected floor, rooms on said floor, and the image of the selected item
        theData.printFilterSelection(floorFilterComboBox, roomFilterComboBox, filterResultsComboBox, artifactImageDisplayLabel);
    }

    /*
    private void deleteArtifactButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
    }
    */

    File selectedFile;

    private void addArtifactImageButtonActionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
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

    /*
    private void editArtifactButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
    }
    */

    private void addNewArtifactButtonActionPerformed(ActionEvent e) {
        //add new artifact to treasures dataset
        theData.addNewArtifact(IDTextField.getText(), NameTextField.getText(), selectedFile, FloorTextField.getText(), RoomTextField.getText());

        theData.fillIntChoice(floorFilterComboBox, theData.artifactFloorNumbers);

        theData.fillStringChoice(roomFilterComboBox, theData.artifactRooms);

        theData.filter(floorFilterComboBox, roomFilterComboBox, filterResultsComboBox);
    }

    private void initComponents() {
        //Component initialization
        filterArtifactsLabel = new JLabel();
        selectedArtifactImageLabel = new JLabel();
        artifactImageDisplayLabel = new JLabel();
        floorFilterLabel = new JLabel();
        floorFilterComboBox = new JComboBox();
        roomFilterLabel = new JLabel();
        roomFilterComboBox = new JComboBox();
        filterResultsLabel = new JLabel();
        filterResultsComboBox = new JComboBox();
        /*
        scrollPane1 = new JScrollPane();
        filterResultsList = new JList();
        //JList used a model view controller MDC - when we want to add data to our JList, we need to add the data to the MODEL of our JList
        DefaultListModel filterResultsListModel = new DefaultListModel();
        filterResultsList.setModel(filterResultsListModel);
        */
        printSelectionButton = new JButton();
        //deleteArtifactButton = new JButton();
        editArtifactsLabel = new JLabel();
        IDLabel = new JLabel();
        IDTextField = new JTextField();
        nameLabel = new JLabel();
        NameTextField = new JTextField();
        imageFileLabel = new JLabel();
        addArtifactImageButton = new JButton();
        floorLabel = new JLabel();
        FloorTextField = new JTextField();
        roomLabel = new JLabel();
        RoomTextField = new JTextField();
        //editArtifactButton = new JButton();
        addNewArtifactButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                        "[163,fill]" +
                        "[583,fill]",
                // rows
                "[]" +
                        "[]" +
                        "[]" +
                        //"[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]"));

        //---- filterArtifactsLabel ----
        filterArtifactsLabel.setText("FILTER ARTIFACTS");
        filterArtifactsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        filterArtifactsLabel.setFont(filterArtifactsLabel.getFont().deriveFont(filterArtifactsLabel.getFont().getStyle() | Font.BOLD, filterArtifactsLabel.getFont().getSize() + 28f));
        contentPane.add(filterArtifactsLabel, "cell 0 0 2 1");

        //---- selectedArtifactImageLabel ----
        selectedArtifactImageLabel.setText("selected artifact image:");
        selectedArtifactImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        selectedArtifactImageLabel.setFont(selectedArtifactImageLabel.getFont().deriveFont(selectedArtifactImageLabel.getFont().getSize() + 4f));
        contentPane.add(selectedArtifactImageLabel, "cell 2 0");

        //---- artifactImageLabel ----
        //artifactImageDisplayLabel.addPropertyChangeListener(e -> artifactImageLabelPropertyChange(e));
        contentPane.add(artifactImageDisplayLabel, "cell 2 1 1 11");

        //---- floorFilterLabel ----
        floorFilterLabel.setText("floor");
        floorFilterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        floorFilterLabel.setFont(floorFilterLabel.getFont().deriveFont(floorFilterLabel.getFont().getSize() + 8f));
        contentPane.add(floorFilterLabel, "cell 0 1");

        //---- floorFilterComboBox ----
        floorFilterComboBox.setFont(floorFilterComboBox.getFont().deriveFont(floorFilterComboBox.getFont().getSize() + 8f));
        floorFilterComboBox.addActionListener(e -> floorFilterComboBoxActionPerformed(e));
        theData.fillIntChoice(floorFilterComboBox, theData.artifactFloorNumbers);
        contentPane.add(floorFilterComboBox, "cell 1 1");

        //---- roomFilterLabel ----
        roomFilterLabel.setText("room");
        roomFilterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roomFilterLabel.setFont(roomFilterLabel.getFont().deriveFont(roomFilterLabel.getFont().getSize() + 8f));
        contentPane.add(roomFilterLabel, "cell 0 2");

        //---- roomFilterComboBox ----
        roomFilterComboBox.setFont(roomFilterComboBox.getFont().deriveFont(roomFilterComboBox.getFont().getSize() + 8f));
        roomFilterComboBox.addActionListener(e -> roomFilterComboBoxActionPerformed(e));
        theData.fillStringChoice(roomFilterComboBox, theData.artifactRooms);
        contentPane.add(roomFilterComboBox, "cell 1 2");

        //---- filterResultsLabel ----
        filterResultsLabel.setText("results:");
        filterResultsLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 28));
        filterResultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(filterResultsLabel, "cell 0 3");

        //---- filterResultsComboBox ----
        filterResultsComboBox.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 28));
        filterResultsComboBox.addActionListener(e -> filterResultsComboBoxActionPerformed(e));
        contentPane.add(filterResultsComboBox, "cell 1 3");

        /*
        //======== scrollPane1 ========
        {

            //---- filterResultsList ----
            filterResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            filterResultsList.setFont(filterResultsList.getFont().deriveFont(filterResultsList.getFont().getSize() + 8f));
            filterResultsList.addListSelectionListener(e -> filterResultsListValueChanged(e));
            theData.filter(floorFilterComboBox, roomFilterComboBox, filterResultsList);
            scrollPane1.setViewportView(filterResultsList);
        }
        contentPane.add(scrollPane1, "cell 1 4");
        */

        //---- printSelectionButton ----
        printSelectionButton.setText("PRINT SELECTION");
        printSelectionButton.setFont(printSelectionButton.getFont().deriveFont(printSelectionButton.getFont().getSize() + 8f));
        printSelectionButton.addActionListener(e -> printSelectionButtonActionPerformed(e));
        contentPane.add(printSelectionButton, "cell 0 4");

        /*
        //---- deleteArtifactButton ----
        deleteArtifactButton.setText("DELETE ARTIFACT");
        deleteArtifactButton.setFont(deleteArtifactButton.getFont().deriveFont(deleteArtifactButton.getFont().getStyle() | Font.BOLD, deleteArtifactButton.getFont().getSize() + 12f));
        deleteArtifactButton.addActionListener(e -> deleteArtifactButtonActionPerformed(e));
        contentPane.add(deleteArtifactButton, "cell 1 4");
        */

        //---- editArtifactsLabel ----
        editArtifactsLabel.setText("EDIT ARTIFACTS");
        editArtifactsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        editArtifactsLabel.setFont(editArtifactsLabel.getFont().deriveFont(editArtifactsLabel.getFont().getStyle() | Font.BOLD, editArtifactsLabel.getFont().getSize() + 28f));
        contentPane.add(editArtifactsLabel, "cell 0 5 2 1");

        //---- IDLabel ----
        IDLabel.setText("ID");
        IDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        IDLabel.setFont(IDLabel.getFont().deriveFont(IDLabel.getFont().getSize() + 10f));
        contentPane.add(IDLabel, "cell 0 6");

        //---- IDTextField ----
        IDTextField.setFont(IDTextField.getFont().deriveFont(IDTextField.getFont().getSize() + 8f));
        contentPane.add(IDTextField, "cell 1 6");

        //---- nameLabel ----
        nameLabel.setText("name");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(nameLabel.getFont().deriveFont(nameLabel.getFont().getSize() + 10f));
        contentPane.add(nameLabel, "cell 0 7");

        //---- NameTextField ----
        NameTextField.setFont(NameTextField.getFont().deriveFont(NameTextField.getFont().getSize() + 8f));
        contentPane.add(NameTextField, "cell 1 7");

        //---- imageFileLabel ----
        imageFileLabel.setText("image");
        imageFileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageFileLabel.setFont(imageFileLabel.getFont().deriveFont(imageFileLabel.getFont().getSize() + 10f));
        contentPane.add(imageFileLabel, "cell 0 8");

        //---- ArtifactImageButton ----
        addArtifactImageButton.setText("add image");
        addArtifactImageButton.setFont(addArtifactImageButton.getFont().deriveFont(addArtifactImageButton.getFont().getSize() + 8f));
        addArtifactImageButton.addActionListener(e -> addArtifactImageButtonActionPerformed(e));
        contentPane.add(addArtifactImageButton, "cell 1 8");

        //---- floorLabel ----
        floorLabel.setText("floor");
        floorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        floorLabel.setFont(floorLabel.getFont().deriveFont(floorLabel.getFont().getSize() + 10f));
        contentPane.add(floorLabel, "cell 0 9");

        //---- FloorTextField ----
        FloorTextField.setFont(FloorTextField.getFont().deriveFont(FloorTextField.getFont().getSize() + 8f));
        contentPane.add(FloorTextField, "cell 1 9");

        //---- roomLabel ----
        roomLabel.setText("room");
        roomLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roomLabel.setFont(roomLabel.getFont().deriveFont(roomLabel.getFont().getSize() + 10f));
        contentPane.add(roomLabel, "cell 0 10");

        //---- RoomTextField ----
        RoomTextField.setFont(RoomTextField.getFont().deriveFont(RoomTextField.getFont().getSize() + 8f));
        contentPane.add(RoomTextField, "cell 1 10");

        /*
        //---- editArtifactButton ----
        editArtifactButton.setText("EDIT SELECTED ARTIFACT");
        editArtifactButton.setFont(editArtifactButton.getFont().deriveFont(editArtifactButton.getFont().getStyle() | Font.BOLD, editArtifactButton.getFont().getSize() + 12f));
        editArtifactButton.addActionListener(e -> editArtifactButtonActionPerformed(e));
        contentPane.add(editArtifactButton, "cell 0 11 2 1");
        */

        //---- addNewArtifactButton ----
        addNewArtifactButton.setText("ADD NEW ARTIFACT");
        addNewArtifactButton.setFont(addNewArtifactButton.getFont().deriveFont(addNewArtifactButton.getFont().getStyle() | Font.BOLD, addNewArtifactButton.getFont().getSize() + 12f));
        addNewArtifactButton.addActionListener(e -> addNewArtifactButtonActionPerformed(e));
        contentPane.add(addNewArtifactButton, "cell 0 11 2 1");

        //making sure the program starts with a visible image for the default filter selection
        artifactImageDisplayLabel.setIcon(new ImageIcon(theData.lookupImage((String) filterResultsComboBox.getSelectedItem())));

        pack();
        setLocationRelativeTo(getOwner());
        //End of component initialization
    }

    //Variables declaration
    private JLabel filterArtifactsLabel;
    private JLabel selectedArtifactImageLabel;
    private JLabel artifactImageDisplayLabel;
    private JLabel floorFilterLabel;
    private JComboBox floorFilterComboBox;
    private JLabel roomFilterLabel;
    private JComboBox roomFilterComboBox;
    private JLabel filterResultsLabel;
    private JComboBox filterResultsComboBox;
    //private JScrollPane scrollPane1;
    //private JList filterResultsList;
    private JButton printSelectionButton;
    //private JButton deleteArtifactButton;
    private JLabel editArtifactsLabel;
    private JLabel IDLabel;
    private JTextField IDTextField;
    private JLabel nameLabel;
    private JTextField NameTextField;
    private JLabel imageFileLabel;
    private JButton addArtifactImageButton;
    private JLabel floorLabel;
    private JTextField FloorTextField;
    private JLabel roomLabel;
    private JTextField RoomTextField;
    private JButton editArtifactButton;
    private JButton addNewArtifactButton;
    //End of variables declaration
}