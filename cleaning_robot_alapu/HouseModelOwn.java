import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

/** class that implements the Model of Domestic Robot application */
public class HouseModelOwn extends GridWorldModel {

    // constants for the grid objects
    public static final int MYDEPOT = 16;
	public static final int MYDEPOTMOTOR = 24;
    public static final int RESTAURANT  = 32;

    // the grid size
    public static final int GSize =10;

    boolean mydepotOpen   = false; // whether the mydepot is open
	//boolean mydepotmotorOpen   = false;
	
    boolean carryingFood = false; // whether the car is carrying food
	//boolean carryingJuice = false;
	
    int foodConsumptionCount        = 0; // how many sip the restaurant did
	//int juiceConsumptionCount        = 0;
	
    int availableFoods  = 6; // how many foods are available
	//int availableJuices  = 6;

    Location lMydepot = new Location(0,0);
	Location lMydepotmotor = new Location(0,GSize-1);
    Location lRestaurant  = new Location(GSize-1,GSize-1);

    public HouseModelOwn() {
        // create a 7x7 grid with one mobile agent
        super(GSize, GSize, 1);

        // initial location of car (column 3, line 3)
        // ag code 0 means the car
        setAgPos(0, GSize/2, GSize/2);
		//setAgPos(1, GSize/4, GSize/4); //ez lenne az 1 miatt a motor?

        // initial location of mydepot and restaurant
        add(MYDEPOT, lMydepot);
		add(MYDEPOTMOTOR, lMydepotmotor);
        add(RESTAURANT, lRestaurant);
    }

    boolean openMydepot() {
        if (!mydepotOpen) {
            mydepotOpen = true;
            return true;
        } else {
            return false;
        }
    }

	/*boolean openMydepotmotor() {
        if (!mydepotmotorOpen) {
            mydepotmotorOpen = true;
            return true;
        } else {
            return false;
        }
    }*/

	
    boolean closeMydepot() {
        if (mydepotOpen) {
            mydepotOpen = false;
            return true;
        } else {
            return false;
        }
    }
	
	/*boolean closeMydepotmotor() {
        if (mydepotmotorOpen) {
            mydepotmotorOpen = false;
            return true;
        } else {
            return false;
        }
    }*/

    boolean moveTowards(Location dest) {
        Location r1 = getAgPos(0);
        if (r1.x < dest.x)        r1.x++;
        else if (r1.x > dest.x)   r1.x--;
        if (r1.y < dest.y)        r1.y++;
        else if (r1.y > dest.y)   r1.y--;
        setAgPos(0, r1); // move the car in the grid

        // repaint the mydepot and restaurant locations
        if (view != null) {
            view.update(lMydepot.x,lMydepot.y);
            view.update(lRestaurant.x,lRestaurant.y);
        }
        return true;
    }
	
	//ugyanúgy, csak a motor mozgásának
	/*boolean moveTowards(Location dest) {
        Location r2 = getAgPos(0);
        if (r2.x < dest.x)        r1.x++;
        else if (r2.x > dest.x)   r1.x--;
        if (r2.y < dest.y)        r1.y++;
        else if (r2.y > dest.y)   r1.y--;
        setAgPos(1, r2); // move the motor?? in the grid

        // repaint the mydepotmotor and restaurant locations
        if (view != null) {
            view.update(lMydepotmotor.x,lMydepotmotor.y);
            view.update(lRestaurant.x,lRestaurant.y);
        }
        return true;
    }*/

    boolean getFood() {
        if (mydepotOpen && availableFoods > 0 && !carryingFood) {
            availableFoods--;
            carryingFood = true;
            if (view != null)
                view.update(lMydepot.x,lMydepot.y);
            return true;
        } else {
            return false;
        }
    }
	
	/*boolean getJuice() {
        if (mydepotmotorOpen && availableJuices > 0 && !carryingJuice) {
            availableJuices--;
            carryingJuice = true;
            if (view != null)
                view.update(lMydepotmotor.x,lMydepotmotor.y);
            return true;
        } else {
            return false;
        }
    }*/

    boolean addFood(int n) {
        availableFoods += n;
        if (view != null)
            view.update(lMydepot.x,lMydepot.y);
        return true;
    }
	
	/*boolean addJuice(int n) {
        availableJuices += n;
        if (view != null)
            view.update(lMydepotmotor.x,lMydepotmotor.y);
        return true;
    }*/

    boolean handInFood() {
        if (carryingFood) {
            foodConsumptionCount = 10;
            carryingFood = false;
            if (view != null)
                view.update(lRestaurant.x,lRestaurant.y);
            return true;
        } else {
            return false;
        }
    }
	
	/*boolean handInJuice() {
        if (carryingJuice) {
            juiceConsumptionCount = 15;
            carryingJuice = false;
            if (view != null)
                view.update(lRestaurant.x,lRestaurant.y);
            return true;
        } else {
            return false;
        }
    }*/

    boolean sipFood() {
        if (foodConsumptionCount > 0) {
            foodConsumptionCount--;
            if (view != null)
                view.update(lRestaurant.x,lRestaurant.y);
            return true;
        } else {
            return false;
        }
    }
	
	/*boolean sipJuice() {
        if (juiceConsumptionCount > 0) {
            juiceConsumptionCount--;
            if (view != null)
                view.update(lRestaurant.x,lRestaurant.y);
            return true;
        } else {
            return false;
        }
    }*/
}
