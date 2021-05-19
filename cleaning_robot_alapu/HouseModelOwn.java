import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

/** class that implements the Model of Domestic Robot application */
public class HouseModelOwn extends GridWorldModel {

    // constants for the grid objects
    public static final int MYDEPOT = 16;
    public static final int RESTAURANT  = 32;

    // the grid size
    public static final int GSize = 7;

    boolean mydepotOpen   = false; // whether the mydepot is open
    boolean carryingFood = false; // whether the car is carrying food
    int sipCount        = 0; // how many sip the restaurant did
    int availableFoods  = 2; // how many foods are available

    Location lMydepot = new Location(0,0);
    Location lRestaurant  = new Location(GSize-1,GSize-1);

    public HouseModelOwn() {
        // create a 7x7 grid with one mobile agent
        super(GSize, GSize, 1);

        // initial location of car (column 3, line 3)
        // ag code 0 means the car
        setAgPos(0, GSize/2, GSize/2);

        // initial location of mydepot and restaurant
        add(MYDEPOT, lMydepot);
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

    boolean closeMydepot() {
        if (mydepotOpen) {
            mydepotOpen = false;
            return true;
        } else {
            return false;
        }
    }

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

    boolean addFood(int n) {
        availableFoods += n;
        if (view != null)
            view.update(lMydepot.x,lMydepot.y);
        return true;
    }

    boolean handInFood() {
        if (carryingFood) {
            sipCount = 10;
            carryingFood = false;
            if (view != null)
                view.update(lRestaurant.x,lRestaurant.y);
            return true;
        } else {
            return false;
        }
    }

    boolean sipFood() {
        if (sipCount > 0) {
            sipCount--;
            if (view != null)
                view.update(lRestaurant.x,lRestaurant.y);
            return true;
        } else {
            return false;
        }
    }
}
