package planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import modelling.Variable;

public class DFSPlanner implements Planner {

    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;

    private int nodeCount = 0;
    private boolean isNodeCountActive = false;

    public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return initialState;
    }

    @Override
    public Set<Action> getActions() {
        return actions;
    }

    @Override
    public Goal getGoal() {
        return goal;
    }

    @Override
    public List<Action> plan() {
        Stack<Action> plan = new Stack<Action>();
        List<Map<Variable, Object>> closed = new ArrayList<Map<Variable, Object>>();// qui pue du cul
        closed.add(this.initialState);

        if (isNodeCountActive)
            nodeCount = 1;

        return plan(this.initialState, plan, closed);
    }
    
    private List<Action> plan(Map<Variable, Object> instantiation, Stack<Action> plan,List<Map<Variable, Object>> closed) {
        if (goal.isSatisfiedBy(instantiation)) {
            return plan;
        }
        for (Action action : this.actions) {
            if (action.isApplicable(instantiation)) {
                Map<Variable, Object> newState = action.successor(instantiation);
                if (!closed.contains(newState)) {
                    plan.add(action);
                    closed.add(newState);
                    if (isNodeCountActive)
                        nodeCount++;
                    List<Action> subPlan = plan(newState, plan, closed);
                    if (subPlan != null) {
                        return subPlan;
                    }
                    plan.pop();
                }
            }
        }
        return null;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public void activateNodeCount(boolean active) {
        isNodeCountActive = active;
    }
}
