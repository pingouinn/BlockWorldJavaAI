package planning;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
import java.util.LinkedList;

import modelling.Variable;

public class AStarPlanner implements Planner {
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private Heuristic heuristic;

    private int nodeCount = 0;
    private boolean isNodeCountActive = false;

    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    @Override
    public Set<Action> getActions() {
        return this.actions;
    }

    @Override
    public Goal getGoal() {
        return this.goal;
    }

    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<Map<Variable, Object>, Action>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<Map<Variable, Object>, Map<Variable, Object>>();
        Map<Map<Variable, Object>, Double> distance = new HashMap<Map<Variable, Object>, Double>();
        Map<Map<Variable, Object>, Double> value = new HashMap<Map<Variable, Object>, Double>();

        Set<Map<Variable, Object>> open = new HashSet<Map<Variable, Object>>();
        open.add(this.initialState);
        father.put(this.initialState, null);
        distance.put(this.initialState, 0.0);
        value.put(this.initialState, Double.valueOf(this.heuristic.estimate(this.initialState)));
        
        if (isNodeCountActive)
            nodeCount = 1;

        while (!open.isEmpty()) {
            Map<Variable, Object> instantiation = open.stream()
                .min((x, xPrime) -> Double.compare(value.get(x), value.get(xPrime)))
                .get();
            if (goal.isSatisfiedBy(instantiation))
                return getBfsPlan(father, plan, instantiation);
            open.remove(instantiation);
            for (Action action : this.actions) {
                if (action.isApplicable(instantiation)) {
                    Map<Variable, Object> newState = action.successor(instantiation);
                    if (!distance.containsKey(newState))
                        distance.put(newState, Double.POSITIVE_INFINITY);
                    Double newDistance = distance.get(instantiation) + action.getCost();
                    if (distance.get(newState) > newDistance) {
                        distance.put(newState, newDistance);
                        value.put(newState, distance.get(newState) + Double.valueOf(this.heuristic.estimate(newState)));
                        father.put(newState, instantiation);
                        plan.put(newState, action);
                        open.add(newState);

                        if (isNodeCountActive)
                            nodeCount++;
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
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public void activateNodeCount(boolean active) {
        isNodeCountActive = active;
    }
}