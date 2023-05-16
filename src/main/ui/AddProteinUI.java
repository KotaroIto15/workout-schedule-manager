package ui;

import model.Schedule;

import javax.swing.*;
import java.awt.*;

// Panel that contains the text fields to add protein, and information about the protein.
public class AddProteinUI extends JPanel {

    private ProteinInputAreasUI inputAreas;

    // EFFECTS: create a panel by creating an input areas
    public AddProteinUI(Action a, Schedule sc) {
        super();
        setPreferredSize(new Dimension(50, 50));
        setLayout(new FlowLayout());
        inputAreas = new ProteinInputAreasUI(a, sc);

        add(inputAreas);
    }

    public ProteinInputAreasUI getInputAreas() {
        return inputAreas;
    }

}
