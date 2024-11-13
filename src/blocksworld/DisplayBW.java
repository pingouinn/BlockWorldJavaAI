package blocksworld;

import java.util.Map;
import javax.swing.JFrame;
import java.awt.Dimension;

import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWComponent;
import bwui.BWIntegerGUI;

import modelling.Variable;

public class DisplayBW {
    private BWData data;
    private String title;
    private int nbState;
    private JFrame frame;
    private BWComponent<Integer> comp;

    public DisplayBW(String title, BWData data, int x, int y, Map<Variable, Object> state) {
        this.title = title;
        this.data = data;
        this.nbState = 0;
        this.frame = new JFrame(title + " - State " + this.nbState);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(x, y);
        frame.setSize(new Dimension(650, 400));

        int blocksAmount = this.data.getBlocksAmount();
        BWIntegerGUI gui = new BWIntegerGUI(blocksAmount);
        this.comp = gui.getComponent(buildState(state, blocksAmount));
        this.frame.add(comp);
        this.frame.setVisible(true);
    }

    public BWState<Integer> buildState(Map<Variable, Object> state, int blocksAmount) {
        Variable[] on = this.data.getOnArray();
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(blocksAmount);
        for (int b = 0; b < blocksAmount; b++) {
            int under = (int) state.get(on[b]);
            if (under >= 0)
                builder.setOn(b, under);
        }
        return builder.getState();
    }

    public void next(Map<Variable, Object> state) {
        this.frame.setTitle(title + " - State " + ++this.nbState);
        this.comp.setState(buildState(state, this.data.getBlocksAmount()));
    }

}