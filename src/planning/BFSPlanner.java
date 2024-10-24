package planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import modelling.Variable;

public class BFSPlanner implements Planner {
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;

    private int nodeCount = 0;
    private boolean isNodeCountActive = false;

    public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<Map<Variable, Object>, Map<Variable, Object>>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<Map<Variable, Object>, Action>();
        List<Map<Variable, Object>> closed = new ArrayList<Map<Variable, Object>>();
        closed.add(this.initialState);
        Queue<Map<Variable, Object>> open = new LinkedList<Map<Variable, Object>>();
        open.add(this.initialState);
        father.put(this.initialState, null);

        if (isNodeCountActive)
            nodeCount = 1;

        if (goal.isSatisfiedBy(this.initialState))
            return new ArrayList<Action>();
        while (!open.isEmpty()) {
            Map<Variable, Object> instantiation = open.poll();
            closed.add(instantiation);
            for (Action action : this.actions) {
                if (action.isApplicable(instantiation)) {
                    Map<Variable, Object> newState = action.successor(instantiation);
                    if (!closed.contains(newState) && !open.contains(newState)) {
                        father.put(newState, instantiation);
                        plan.put(newState, action);
                        if (isNodeCountActive)
                            nodeCount++;
                        if (this.goal.isSatisfiedBy(newState))
                            return this.getBfsPlan(father, plan, newState);
                        open.add(newState);
                    }
                }
            }
        }
        return null;
    }

    private List<Action> getBfsPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
        Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> goal) {
        Queue<Action> bfsPlan = new LinkedList<Action>();
        while (plan.get(goal) != null) {
            bfsPlan.add(plan.get(goal));
            goal = father.get(goal);
        }

        List<Action> reversedBfsPlan = new ArrayList<Action>();
        while (!bfsPlan.isEmpty())
            reversedBfsPlan.add(bfsPlan.poll());
        return reversedBfsPlan;
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
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public void activateNodeCount(boolean active) {
        isNodeCountActive = active;
    }
}
