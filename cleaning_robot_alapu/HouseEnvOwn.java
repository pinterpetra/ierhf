import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import java.util.logging.Logger;

public class HouseEnvOwn extends Environment {

    // common literals
    public static final Literal of  = Literal.parseLiteral("open(mydepot)");
    public static final Literal clf = Literal.parseLiteral("close(mydepot)");
    public static final Literal gb  = Literal.parseLiteral("get(food)");
    public static final Literal hb  = Literal.parseLiteral("hand_in(food)");
    public static final Literal sb  = Literal.parseLiteral("sip(food)");
    public static final Literal hob = Literal.parseLiteral("has(restaurant,food)");
	
	/*public static final Literal ofm  = Literal.parseLiteral("open(mydepotmotor)");
    public static final Literal clfm = Literal.parseLiteral("close(mydepotmotor)");
    public static final Literal gbm  = Literal.parseLiteral("get(juice)");
    public static final Literal hbm  = Literal.parseLiteral("hand_in(juice)");
    public static final Literal sbm  = Literal.parseLiteral("sip(juice)");
    public static final Literal hobm = Literal.parseLiteral("has(restaurant,juice)");*/

    public static final Literal af = Literal.parseLiteral("at(car,mydepot)");
    public static final Literal ao = Literal.parseLiteral("at(car,restaurant)");
	
	/*public static final Literal afm = Literal.parseLiteral("at(motor,mydepotmotor)");
    public static final Literal aom = Literal.parseLiteral("at(motor,restaurant)");*/

    static Logger logger = Logger.getLogger(HouseEnvOwn.class.getName());

    HouseModelOwn model; // the model of the grid

    @Override
    public void init(String[] args) {
        model = new HouseModelOwn();

        if (args.length == 1 && args[0].equals("gui")) {
            HouseViewOwn view  = new HouseViewOwn(model);
            model.setView(view);
        }

        updatePercepts();
    }

    /** creates the agents percepts based on the HouseModelOwn */
    void updatePercepts() {
        // clear the percepts of the agents
        clearPercepts("car");
		clearPercepts("motor");
        clearPercepts("restaurant");

        // get the car location
        Location lCar = model.getAgPos(0);
		//Location lMotor = model.getAgPos(1);

        // add agent location to its percepts
        if (lCar.equals(model.lMydepot)) {
            addPercept("car", af);
        }
        if (lCar.equals(model.lRestaurant)) {
            addPercept("car", ao);
        }
		/*if (lMotor.equals(model.lMydepotmotor)) {
            addPercept("motor", afm);
        }
        if (lMotor.equals(model.lRestaurant)) {
            addPercept("motor", aom);
        }*/

        // add food "status" the percepts
        if (model.mydepotOpen) {
            addPercept("car", Literal.parseLiteral("stock(food,"+model.availableFoods+")"));
        }
        if (model.foodConsumptionCount > 0) {
            addPercept("car", hob);
            addPercept("restaurant", hob);
        }
		/*if (model.mydepotmotorOpen) {
            addPercept("motor", Literal.parseLiteral("stock(juice,"+model.availableJuices+")"));
        }
        if (model.juiceConsumptionCount > 0) {
            addPercept("motor", hobm);
            addPercept("restaurant", hobm);
        }*/
    }


    @Override
    public boolean executeAction(String ag, Structure action) {
        System.out.println("["+ag+"] doing: "+action);
        boolean result = false;
        if (action.equals(of)) { // of = open(mydepot)
            result = model.openMydepot();

        } else if (action.equals(clf)) { // clf = close(mydepot)
            result = model.closeMydepot();

        } else if (action.getFunctor().equals("move_towards")) {
            String l = action.getTerm(0).toString();
            Location dest = null;
            if (l.equals("mydepot")) {
                dest = model.lMydepot;
            } else if (l.equals("restaurant")) {
                dest = model.lRestaurant;
            }

            try {
                result = model.moveTowards(dest);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (action.equals(gb)) {
            result = model.getFood();

        } else if (action.equals(hb)) {
            result = model.handInFood();

        } else if (action.equals(sb)) {
            result = model.sipFood();

        } else if (action.getFunctor().equals("deliver")) {
            // wait 4 seconds to finish "deliver"
            try {
                Thread.sleep(4000);
                result = model.addFood( (int)((NumberTerm)action.getTerm(1)).solve());
            } catch (Exception e) {
                logger.info("Failed to execute action deliver!"+e);
            }

        } else {
            logger.info("Failed to execute action "+action);
        }

        if (result) {
            updatePercepts();
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
        }
        return result;
    }
}
