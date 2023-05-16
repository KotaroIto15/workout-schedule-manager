package ui;

import model.Protein;
import model.Schedule;

import javax.swing.*;
import java.awt.*;

// Panel represents the protein input areas and the information about the protein
public class ProteinInputAreasUI extends JPanel {

    private InputAreaUI flavor;
    private InputAreaUI amount;
    private JButton addButton;
    private JLabel flavorInfo;
    private JLabel amountInfo;

    // EFFECTS: creates a panel with input areas used for adding a new protein on the top,
    //          and the information about hte protein beneath it.
    public ProteinInputAreasUI(Action a, Schedule sc) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        flavor = new InputAreaUI("name");
        amount = new InputAreaUI("remaining amount");
        addButton = new JButton(a);

        add(flavor);
        add(amount);
        add(addButton);

        flavorInfo = getProteinFlavorInfo(sc);
        flavorInfo.setPreferredSize(new Dimension(50, 90));
        flavorInfo.setVerticalAlignment(JLabel.BOTTOM);

        amountInfo = getProteinAmountInfo(sc);
        amountInfo.setVerticalAlignment(JLabel.BOTTOM);

        add(flavorInfo);
        add(amountInfo);

    }

    public String getFlavor() {
        return flavor.getInput();
    }

    public int getRemainingAmount() {
        return Integer.parseInt(amount.getInput());
    }

    // EFFECTS: creates a JLabel with the flavor of the protein that the given schedule has, and return it.
    public JLabel getProteinFlavorInfo(Schedule sc) {
        Protein protein = sc.getProtein();
        String info = "Flavor: " + protein.getFlavor();

        return new JLabel(info);
    }

    // EFFECTS: creates a JLabel with the remaining amount of the protein that the given schedule has, and return it.
    public JLabel getProteinAmountInfo(Schedule sc) {
        Protein protein = sc.getProtein();
        String info = "Remaining Amount: " + protein.getRemainingAmount() + " g";

        return new JLabel(info);
    }

}
