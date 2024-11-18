package planning;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import modelling.Variable;

public class Demo {
    
    public static void main(String[] args) {

        // Ceci est un exemple simple pour comparer les algorithmes de recherche
        // On part de (0,0) sur une grille de 50x50 (le domaine) et on cherche à satisfaire deux goals intermédiaires et un goal final (aller à (0,20), puis (20,0) pour finir à (0,0))
        // Les goals sont dépendants de la réalisation du précédent goal

        int maxPosValue = 50;

        Map<Variable, Object> initialState = new HashMap<>();
        Set<Object> domainPos = new HashSet<>();
        for (int i = 0; i <= maxPosValue; i++) {
            domainPos.add(i);
        }

        Variable posX = new Variable("posX", domainPos);
        Variable posY = new Variable("posY", domainPos);
        Variable midGoal = new Variable("midGoal", Set.of(0, 1, 2));

        initialState.put(posX, 0);
        initialState.put(posY, 0);
        initialState.put(midGoal, 0);
        Set<Action> actions = new HashSet<Action>();
        for (int i = 0; i <= maxPosValue; i++) {
            Map<Variable, Object> preconditionsX = new HashMap<>();
            preconditionsX.put(posX, i);
            Map<Variable, Object> effectsXPlus = new HashMap<>();
            effectsXPlus.put(posX, i + 1);
            actions.add(new BasicAction(preconditionsX, effectsXPlus, 2));
            Map<Variable, Object> effectsXMinus = new HashMap<>();
            effectsXMinus.put(posX, i - 1);
            actions.add(new BasicAction(preconditionsX, effectsXMinus, 2));
            
            Map<Variable, Object> preconditionsY = new HashMap<>();
            preconditionsY.put(posY, i);
            Map<Variable, Object> effectsYPlus = new HashMap<>();
            effectsYPlus.put(posY, i + 1);
            actions.add(new BasicAction(preconditionsY, effectsYPlus, 2));
            Map<Variable, Object> effectsYMinus = new HashMap<>();
            effectsYMinus.put(posY, i - 1);
            actions.add(new BasicAction(preconditionsY, effectsYMinus, 2));
        }


        Map<Variable, Object> preconditions = new HashMap<>();
        preconditions.put(posX, 0);
        preconditions.put(posY, 20);
        preconditions.put(midGoal, 0);
        Map<Variable, Object> effects = new HashMap<>();
        effects.put(midGoal, 1);
        actions.add(new BasicAction(preconditions, effects, 1));

        Map<Variable, Object> preconditions2 = new HashMap<>();
        preconditions2.put(posX, 20);
        preconditions2.put(posY, 0);
        preconditions2.put(midGoal, 1);
        Map<Variable, Object> effects2 = new HashMap<>();
        effects2.put(midGoal, 2);
        actions.add(new BasicAction(preconditions2, effects2, 1));

        Map<Variable, Object> goalState = new HashMap<>();
        goalState.put(posX, 0);
        goalState.put(posY, 0);
        goalState.put(midGoal, 2);
        Goal goal = new BasicGoal(goalState);

        Set<Planner> planners = new HashSet<Planner>();

        planners.add(new DFSPlanner(initialState, actions, goal));
        planners.add(new BFSPlanner(initialState, actions, goal));
        planners.add(new DijkstraPlanner(initialState, actions, goal));
        planners.add(new AStarPlanner(initialState, actions, goal, new Heuristic() {
            @Override
            public float estimate(Map<Variable, Object> state) {//get from decompiled code in AStarPlannerTest
                return goal.isSatisfiedBy(state) ? 0 : 1;
            }
        }));
        
        System.out.println("\nComparaison des algorithmes de recherche : ");
        for (Planner planner : planners) {
            planner.activateNodeCount(true);
            planner.plan();
            System.out.println(planner.getClass().getSimpleName() + "\n\t-Nodes count : " + planner.getNodeCount());
            System.out.println();
        }

        System.out.println("Le classement des algorithmes selon le nombre de noeuds explorés est le suivant : Djikstra > BFS > AStar > DFS ");
        System.out.println("Dijkstra a parcouru le plus de noeuds, car il explore selon le coût de chaque noeud et les coûts sont égaux. Il est similaire à BFS dans le parcours donc ils retournent sensiblement le même nombre de noeuds.");
        System.out.println("A* a parcouru presque le même nombre de noeuds que BFS, car il utilise une heuristique mediocre pour guider l'exploration. Il devient donc un UCS qui possède quasiment les mêmes propriétés que Dijkstra.");


    }
}
