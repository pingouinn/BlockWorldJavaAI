package planning;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import modelling.Variable;

public class DijkstraPlanner implements Planner {
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;

    private int nodeCount = 0;
    private boolean isNodeCountActive = false;

    public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
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
        Map<Map<Variable, Object>, Double> distance = new HashMap<Map<Variable, Object>, Double>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<Map<Variable, Object>, Map<Variable, Object>>();

        Set<Map<Variable, Object>> goals = new HashSet<Map<Variable, Object>>();
        father.put(this.initialState, null);
        distance.put(this.initialState, 0.0);
        Set<Map<Variable, Object>> open = new HashSet<Map<Variable, Object>>();
        open.add(this.initialState);

        if (isNodeCountActive) 
            nodeCount = 1;


        while (!open.isEmpty()) {
            Map<Variable, Object> instantiation = open.stream()
                .min((x, xPrime) -> Double.compare(distance.get(x), distance.get(xPrime)))
                .get();
            open.remove(instantiation);
            if (goal.isSatisfiedBy(instantiation)) {
                goals.add(instantiation);
                break;// to avoid unnecessary iterations
            }
            for (Action action : this.actions) {
                if (action.isApplicable(instantiation)) {
                    Map<Variable, Object> newState = action.successor(instantiation);
                    if (!distance.containsKey(newState))
                        distance.put(newState, Double.POSITIVE_INFINITY);
                    if (distance.get(newState) > distance.get(instantiation) + action.getCost()) {
                        distance.put(newState, distance.get(instantiation) + action.getCost());
                        father.put(newState, instantiation);
                        plan.put(newState, action);
                        open.add(newState);
                        if (isNodeCountActive)
                            nodeCount++;
                    }
                }
            }
        }
        if (goals.isEmpty())
            return null;
        return this.getDijkstraPlan(father, plan, goals, distance);
    }

    private List<Action> getDijkstraPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Set<Map<Variable, Object>> goals, Map<Map<Variable, Object>, Double> distance) {
        Queue<Action> dijkstraPlan = new LinkedList<Action>();
        Map<Variable, Object> goalD = goals.stream()
            .min((x, xPrime) -> Double.compare(distance.get(x), distance.get(xPrime)))
            .get();

        while (father.get(goalD) != null) {
            dijkstraPlan.add(plan.get(goalD));
            goalD = father.get(goalD);
        }
        List<Action> reversedPlan = new ArrayList<Action>();
        while (!dijkstraPlan.isEmpty()) {
            reversedPlan.add(0, dijkstraPlan.poll());
        }
        return reversedPlan;
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
