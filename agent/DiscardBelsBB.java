package agent;

import jason.asSemantics.Agent;
import jason.asSyntax.Literal;
import jason.bb.DefaultBeliefBase;

import java.util.HashSet;
import java.util.Set;

public class DiscardBelsBB extends DefaultBeliefBase {
    //static private Logger logger = Logger.getLogger(UniqueBelsBB.class.getName());

    Set<String> discartedBels = new HashSet<String>();

    public void init(Agent ag, String[] args) {
        for (int i=0; i<args.length; i++) {
            discartedBels.add(args[i]);
        }
    }

    @Override
    public boolean add(Literal bel) {
        if (!discartedBels.contains(bel.getFunctor())) {
            return super.add(bel);
        } else {
            return false;
        }
    }
}
