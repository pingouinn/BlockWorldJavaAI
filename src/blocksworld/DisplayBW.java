package blocksworld;

import java.util.Map;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Point;

import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWIntegerGUI;

import modelling.Variable;

public class DisplayBW {
    private BWData data;
    private String title;
    private int nbState;
    private Point location;

    public DisplayBW(String title, BWData data, int x, int y) {
        this.title = title;
        this.data = data;
        this.nbState = 0;
        this.location = new Point(x, y);
    }

    public BWState<Integer> buildState(Map<Variable, Object> state, int blocksAmount) {
        Variable[] on = this.data.getOnArray();
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(blocksAmount);
        for (int b = 0; b < blocksAmount; b++) {
            Variable onB = on[b];
            int under = (int) state.get(onB);
            if (under >= 0)
                builder.setOn(b, under);
        }
        return builder.getState();
    }

    public void display(Map<Variable, Object> state) {
        int blocksAmount = this.data.getBlocksAmount();
        BWIntegerGUI gui = new BWIntegerGUI(blocksAmount);
        JFrame frame = new JFrame(this.title + " - State " + this.nbState++);
        frame.add(gui.getComponent(buildState(state, blocksAmount)));
        frame.pack();
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(700, 700));
        frame.setLocation(this.location);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}