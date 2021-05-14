package agent;

import jason.asSemantics.Agent;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import jason.bb.DefaultBeliefBase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UniqueBelsBB extends DefaultBeliefBase {
    //static private Logger logger = Logger.getLogger(UniqueBelsBB.class.getName());

    Map<String,Literal> uniqueBels = new HashMap<String,Literal>();
    Unifier             u = new Unifier();

    public void init(Agent ag, String[] args) {
        for (int i=0; i<args.length; i++) {
            Literal arg = Literal.parseLiteral(args[i]);
            uniqueBels.put(arg.getFunctor(), arg);
        }
    }

    @Override
    public boolean add(Literal bel) {
        Literal kb = uniqueBels.get(bel.getFunctor());
        if (kb != null && kb.getArity() == bel.getArity()) {

            // find the bel in BB and eventually remove it
            u.clear();
            Literal linbb = null;
            boolean remove = false;

            Iterator<Literal> relevant = getCandidateBeliefs(bel, null);
            if (relevant != null) {
                while (relevant.hasNext() && !remove) {
                    linbb = relevant.next();

                    boolean equals = true;
                    for (int i = 0; i<kb.getArity(); i++) {
                        Term kbt = kb.getTerm(i);
                        if (!kbt.isVar()) {
                            if (!u.unifies(bel.getTerm(i), linbb.getTerm(i))) {
                                equals = false;
                                break;
                            }
                        }
                    }
                    if (equals) {
                        remove = true;
                    }
                }
            }
            if (remove) {
                remove(linbb);
            }
        }
        return super.add(bel);
    }

}
