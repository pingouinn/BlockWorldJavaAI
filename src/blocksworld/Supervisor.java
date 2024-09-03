package blocksworld;
import java.util.Map;
import java.util.List;

public class Supervisor {

    private Integer blocksAmount;
    private Integer stackAmount;
    private Map<Integer, List<Integer>> blocksState;

    public Supervisor(Integer blocksAmount, Integer stackAmount) {
        this.blocksAmount = blocksAmount;
        this.stackAmount = stackAmount;
    }

    public Integer getBlocksAmount() {
        return blocksAmount;
    }

    public Integer getstackAmount() {
        return stackAmount;
    }

    //Int,List<Int> type is a placeholder for the stack pointer and blocks pointer list related to it
    public void setBlocksState(Map<Integer, List<Integer>> blocks) {
        this.blocksState = blocks;
    }

    public Map<Integer, List<Integer>> getBlocksState() {
        return this.blocksState;
    }
}