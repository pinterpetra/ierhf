import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import java.util.logging.Logger;

public class HouseEnvOwn extends Environment {

    // common literals
    public static final Literal literalCARopensItsDepot  = Literal.parseLiteral("open(mydepot)");
    public static final Literal literalCARclosesItsDepot = Literal.parseLiteral("close(mydepot)");
    public static final Literal literalGetFOOD  = Literal.parseLiteral("get(food)");
    public static final Literal literalHandInFOOD  = Literal.parseLiteral("hand_in(food)");
    public static final Literal literalSipFOOD  = Literal.parseLiteral("sip(food)");
    public static final Literal literalRestaurantHasFOOD = Literal.parseLiteral("has(restaurant,food)");
	
	public static final Literal literalMOTORopensItsDepot  = Literal.parseLiteral("open(mydepotmotor)");
    public static final Literal literalMOTORclosesItsDepot = Literal.parseLiteral("close(mydepotmotor)");
    public static final Literal literalGetJUICE  = Literal.parseLiteral("get(juice)");
    public static final Literal literalHandInJUICE  = Literal.parseLiteral("hand_in(juice)");
    public static final Literal literalSipJUICE  = Literal.parseLiteral("sip(juice)");
    public static final Literal literalRestaurantHasJUICE = Literal.parseLiteral("has(restaurant,juice)");
	
	public static final Literal literalBICYCLEopensItsDepot  = Literal.parseLiteral("open(mydepotbic)");
    public static final Literal literalBICYCLEclosesItsDepot = Literal.parseLiteral("close(mydepotbic)");
    public static final Literal literalGetCOOKIE = Literal.parseLiteral("get(cookie)");
    public static final Literal literalHandInCOOKIE  = Literal.parseLiteral("hand_in(cookie)");
    public static final Literal literalSipCOOKIE  = Literal.parseLiteral("sip(cookie)");
    public static final Literal literalRestaurantHasCOOKIE = Literal.parseLiteral("has(restaurant,cookie)");

	
    public static final Literal literalCARisAtItsDepot = Literal.parseLiteral("at(car,mydepot)");
    public static final Literal literalCARisAtRestaurant = Literal.parseLiteral("at(car,restaurant)");
	
	public static final Literal literalMOTORisAtItsDepot = Literal.parseLiteral("at(motor,mydepotmotor)");
    public static final Literal literalMOTORisAtRestaurant = Literal.parseLiteral("at(motor,restaurant)");
	
	public static final Literal literalBICYCLEisAtItsDepot = Literal.parseLiteral("at(bicycle,mydepotbic)");
    public static final Literal literalBICYCLEisAtRestaurant = Literal.parseLiteral("at(bicycle,restaurant)");

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
		clearPercepts("bicycle");
        clearPercepts("restaurant");

        // get the car location
        Location lCar = model.getAgPos(0);
		Location lMotor = model.getAgPos(1);
		Location lBicycle = model.getAgPos(2);

        // add agent location to its percepts
        if (lCar.equals(model.lMydepot)) {
            addPercept("car", literalCARisAtItsDepot);
        }
        if (lCar.equals(model.lRestaurant)) {
            addPercept("car", literalCARisAtRestaurant);
        }
		if (lMotor.equals(model.lMydepotmotor)) {
            addPercept("motor", literalMOTORisAtItsDepot);
        }
        if (lMotor.equals(model.lRestaurant)) {
            addPercept("motor", literalMOTORisAtRestaurant);
        }
		if (lMotor.equals(model.lMydepotmotor)) {
            addPercept("bicycle", literalBICYCLEisAtItsDepot);
        }
        if (lBicycle.equals(model.lRestaurant)) {
            addPercept("bicycle", literalBICYCLEisAtRestaurant);
        }

        // add food "status" the percepts
        if (model.mydepotOpen) {
            addPercept("car", Literal.parseLiteral("stock(food,"+model.availableFoods+")"));
        }
        if (model.foodConsumptionCount > 0) {
            addPercept("car", literalRestaurantHasFOOD);
            addPercept("restaurant", literalRestaurantHasFOOD);
        }
		if (model.mydepotmotorOpen) {
            addPercept("motor", Literal.parseLiteral("stock(juice,"+model.availableJuices+")"));
        }
        if (model.juiceConsumptionCount > 0) {
            addPercept("motor", literalRestaurantHasJUICE);
            addPercept("restaurant", literalRestaurantHasJUICE);
        }
		if (model.mydepotbicOpen) {
            addPercept("bicycle", Literal.parseLiteral("stock(cookie,"+model.availableCookies+")"));
        }
        if (model.cookieConsumptionCount > 0) {
            addPercept("bicycle", literalRestaurantHasCOOKIE);
            addPercept("restaurant", literalRestaurantHasCOOKIE);
        }
    }


    @Override
    public boolean executeAction(String ag, Structure action) {
        //System.out.println("["+ag+"] doing: "+action);
		
        boolean result = false;
        if (action.equals(literalCARopensItsDepot)) { // literalCARopensItsDepot = open(mydepot)
            result = model.openMydepot();
        }
		else if (action.equals(literalCARclosesItsDepot)) { // literalCARclosesItsDepot = close(mydepot)
            result = model.closeMydepot();
        }
		else if (action.equals(literalMOTORopensItsDepot)) {
            result = model.openMydepotmotor();
        }
		else if (action.equals(literalMOTORclosesItsDepot)) {
            result = model.closeMydepotmotor();
        }
		else if (action.equals(literalBICYCLEopensItsDepot)) {
            result = model.openMydepotbic();
        }
		else if (action.equals(literalBICYCLEclosesItsDepot)) {
            result = model.closeMydepotbic();
        }
		
		else if (action.getFunctor().equals("move_towards")) { //ezzel mozog a car
            String l = action.getTerm(0).toString();
            Location dest = null;
            if (l.equals("mydepot")) {
                dest = model.lMydepot;
            }
			else if (l.equals("restaurant")) {
                dest = model.lRestaurant;
            }

            try {
                result = model.moveTowards(dest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		else if (action.getFunctor().equals("move_towards_motor")) { //ezzel mozogna a motor
            String l = action.getTerm(0).toString(); //mi az a getTerm?
            Location dest = null;
            if (l.equals("mydepotmotor")) {
                dest = model.lMydepotmotor;
            }
			else if (l.equals("restaurant")) {
                dest = model.lRestaurant;
            }

            try {
                result = model.moveTowardsmotor(dest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		else if (action.getFunctor().equals("move_towards_bic")) { //ezzel mozogna a bickili
            String l = action.getTerm(0).toString(); //mi az a getTerm?
            Location dest = null;
            if (l.equals("mydepotbic")) {
                dest = model.lMydepotbic;
            }
			else if (l.equals("restaurant")) {
                dest = model.lRestaurant;
            }

            try {
                result = model.moveTowardsbic(dest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		else if (action.equals(literalGetFOOD)) {
            result = model.getFood();

        }
		else if (action.equals(literalGetJUICE)) {
            result = model.getJuice();

        }
		else if (action.equals(literalGetCOOKIE)) {
            result = model.getCookie();

        }
		
		else if (action.equals(literalHandInFOOD)) {
            result = model.handInFood();
        }
		else if (action.equals(literalHandInJUICE)) {
            result = model.handInJuice();
        }
		else if (action.equals(literalHandInCOOKIE)) {
            result = model.handInCookie();
        }
		
		else if (action.equals(literalSipFOOD)) {
            result = model.sipFood();
        } 
		else if (action.equals(literalSipJUICE)) {
            result = model.sipJuice();
        }
		else if (action.equals(literalSipCOOKIE)) {
            result = model.sipCookie();
        }
		
		else if (action.getFunctor().equals("deliver")) { //ez a deliver a supermarket belief systemjében van, depó utántöltéséért felel, lehet kell bel?le külön minden típusú kajára
            // wait 4 seconds to finish "deliver"
            try {
                Thread.sleep(4000);
                result = model.addFood( (int)((NumberTerm)action.getTerm(1)).solve());
				result = model.addJuice( (int)((NumberTerm)action.getTerm(1)).solve()); //addJuice-nak egy fura, de m?köd? ág
				result = model.addCookie( (int)((NumberTerm)action.getTerm(1)).solve());
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
