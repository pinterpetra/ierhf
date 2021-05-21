import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

/** class that implements the Model of Domestic Robot application */
public class Model extends GridWorldModel {

    public static final int MYDEPOT = 16;
	public static final int MYDEPOTMOTOR = 64;
	public static final int MYDEPOTBIC = 128;
    public static final int RESTAURANT  = 32;

    public static final int GSize =14;

    boolean mydepotOpen   = false;
	boolean mydepotmotorOpen   = false;
	boolean mydepotbicOpen   = false;
	
    boolean carryingFood = false;
	boolean carryingJuice = false;
	boolean carryingCookie = false;
	
    int foodConsumptionCount        = 0;
	int juiceConsumptionCount        = 0;
	int cookieConsumptionCount        = 0;
	
    int availableFoods  = 6;
	int availableJuices  = 4;
	int availableCookies  = 8;

    Location lMydepot = new Location(0,0);
	Location lMydepotmotor = new Location(0,GSize-1);
	Location lMydepotbic = new Location(GSize-1, 3);
    Location lRestaurant  = new Location(GSize-1,GSize-1);

    public Model() {
        super(GSize, GSize, 5);
		//System.out.println(getNbOfAgs());

        setAgPos(0, 0, 1);
		setAgPos(1, 0, 9);
		setAgPos(2, 12, 3);
		

        add(MYDEPOT, lMydepot);
		add(MYDEPOTMOTOR, lMydepotmotor);
		add(MYDEPOTBIC, lMydepotbic);
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
	boolean openMydepotmotor() {
        if (!mydepotmotorOpen) {
            mydepotmotorOpen = true;
            return true;
        } else {
            return false;
        }
    }
	boolean openMydepotbic() {
        if (!mydepotbicOpen) {
            mydepotbicOpen = true;
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
	boolean closeMydepotmotor() {
        if (mydepotmotorOpen) {
            mydepotmotorOpen = false;
            return true;
        } else {
            return false;
        }
    }
	boolean closeMydepotbic() {
        if (mydepotbicOpen) {
            mydepotbicOpen = false;
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
        setAgPos(0, r1);

        if (view != null) {
            view.update(lMydepot.x,lMydepot.y);
            view.update(lRestaurant.x,lRestaurant.y);
        }
        return true;
    }
	boolean moveTowardsmotor(Location dest) {
        Location r2 = getAgPos(1);
        if (r2.x < dest.x)        r2.x++;
        else if (r2.x > dest.x)   r2.x--;
        if (r2.y < dest.y)        r2.y++;
        else if (r2.y > dest.y)   r2.y--;
        setAgPos(1, r2);

        if (view != null) {
            view.update(lMydepotmotor.x,lMydepotmotor.y);
            view.update(lRestaurant.x,lRestaurant.y);
        }
        return true;
    }
	boolean moveTowardsbic(Location dest) {
        Location r3 = getAgPos(2);
        if (r3.x < dest.x)        r3.x++;
        else if (r3.x > dest.x)   r3.x--;
        if (r3.y < dest.y)        r3.y++;
        else if (r3.y > dest.y)   r3.y--;
        setAgPos(2, r3);

        if (view != null) {
            view.update(lMydepotbic.x,lMydepotbic.y);
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
	boolean getJuice() {
        if (mydepotmotorOpen && availableJuices > 0 && !carryingJuice) {
            availableJuices--;
            carryingJuice = true;
            if (view != null)
                view.update(lMydepotmotor.x,lMydepotmotor.y);
            return true;
        } else {
            return false;
        }
    }
	boolean getCookie() {
        if (mydepotbicOpen && availableCookies > 0 && !carryingCookie) {
            availableCookies--;
            carryingCookie = true;
            if (view != null)
                view.update(lMydepotbic.x,lMydepotbic.y);
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
	boolean addJuice(int n) {
        availableJuices += n;
        if (view != null)
            view.update(lMydepotmotor.x,lMydepotmotor.y);
        return true;
    }
	boolean addCookie(int n) {
        availableCookies += n;
        if (view != null)
            view.update(lMydepotbic.x,lMydepotbic.y);
        return true;
    }

    boolean handInFood() {
        if (carryingFood) {
            foodConsumptionCount = 30;
            carryingFood = false;
            if (view != null)
                view.update(lRestaurant.x,lRestaurant.y);
            return true;
        } else {
            return false;
        }
    }
	boolean handInJuice() {
        if (carryingJuice) {
            juiceConsumptionCount = 24;
            carryingJuice = false;
            if (view != null)
                view.update(lRestaurant.x,lRestaurant.y);
            return true;
        } else {
            return false;
        }
    }
	boolean handInCookie() {
        if (carryingCookie) {
            cookieConsumptionCount = 8;
            carryingCookie = false;
            if (view != null)
                view.update(lRestaurant.x,lRestaurant.y);
            return true;
        } else {
            return false;
        }
    }

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
	boolean sipJuice() {
        if (juiceConsumptionCount > 0) {
            juiceConsumptionCount--;
            if (view != null)
                view.update(lRestaurant.x,lRestaurant.y);
            return true;
        } else {
            return false;
        }
    }
	boolean sipCookie() {
        if (cookieConsumptionCount > 0) {
            cookieConsumptionCount--;
            if (view != null)
                view.update(lRestaurant.x,lRestaurant.y);
            return true;
        } else {
            return false;
        }
    }
}
