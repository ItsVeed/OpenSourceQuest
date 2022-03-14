import Quests.Quest;
import com.epicbot.api.shared.APIContext;
import data.Vars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Gui extends JFrame {
    APIContext ctx;

    private ArrayList<JCheckBox> quests;
    private JButton startButton = null;

    public Gui(APIContext ctx) {
        this.ctx = ctx;

        quests = getQuests();

        setTitle("Veeds Quester");

        JPanel normal = new JPanel();
        JPanel test = new JPanel();

        normal.setLayout(new BorderLayout());
        test.setLayout(new FlowLayout(FlowLayout.CENTER));

        for (JCheckBox i : quests) {
            test.add(i);
        }

        normal.add(test, BorderLayout.CENTER);
        normal.add(getStartButton(), BorderLayout.PAGE_END);

        add(normal);

        setSize(200, 400);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                ctx.script().stop("Gui was closed.");
                dispose();
            }
        });
    }

    private ArrayList<JCheckBox> getQuests() {
        ArrayList<JCheckBox> data = new ArrayList<JCheckBox>();
        for (Quest i : Vars.quests) {
            data.add(new JCheckBox(i.name));
        }
        return data;
    }

    private boolean start() {
        for (int i = 0; i < quests.size(); i++) {
            Vars.quests[i].setDoQuest(quests.get(i).isSelected());
        }
        Vars.start = true;
        return true;
    }

    private JButton getStartButton() {
        if (startButton == null) {
            startButton = new JButton("Start");

            ActionListener actionListener = e -> {
                if (start()) {
                    dispose();
                }
            };

            startButton.addActionListener(actionListener);
        }
        return startButton;
    }
}
