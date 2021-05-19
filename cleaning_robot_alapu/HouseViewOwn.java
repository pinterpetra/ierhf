import jason.environment.grid.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


/** class that implements the View of Domestic Car application */
public class HouseViewOwn extends GridWorldView {

    HouseModelOwn hmodel;

    public HouseViewOwn(HouseModelOwn model) {
        super(model, "Restaurant Delivery System", 700);
        hmodel = model;
        defaultFont = new Font("Arial", Font.BOLD, 16); // change default font
        setVisible(true);
        repaint();
    }

    /** draw application objects */
    @Override
    public void draw(Graphics g, int x, int y, int object) {
        Location lCar = hmodel.getAgPos(0);
		Location lMotor = hmodel.getAgPos(1);
		
        super.drawAgent(g, x, y, Color.lightGray, -1);
        switch (object) {
        case HouseModelOwn.MYDEPOT:
            if (lCar.equals(hmodel.lMydepot)) {
                super.drawAgent(g, x, y, Color.orange, -1);
            }
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, "CarDepot ("+hmodel.availableFoods+")");
            break;
			
		//motornak is k�ne depot, itt pl kell �rni ahhoz, nem csin�l semmit
		case HouseModelOwn.MYDEPOTMOTOR:
			if (lCar.equals(hmodel.lMydepotmotor)) {
					super.drawAgent(g, x, y, Color.green, -1);
				}
            g.setColor(Color.green);
            drawString(g, x, y, defaultFont, "Mydepotmotor");
            break;
		case HouseModelOwn.MYDEPOTBIC:
			if (lCar.equals(hmodel.lMydepotbic)) {
					super.drawAgent(g, x, y, Color.green, -1);
				}
            g.setColor(Color.green);
            drawString(g, x, y, defaultFont, "Mydepotbic");
            break;
			
        case HouseModelOwn.RESTAURANT:
            if (lCar.equals(hmodel.lRestaurant)) {
                super.drawAgent(g, x, y, Color.orange, -1);
            }
            String o = "Restaurant";
            if (hmodel.foodConsumptionCount > 0) {
                o +=  " ("+hmodel.foodConsumptionCount+")";
            }
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, o);
            break;
        }
        repaint();
    }

    @Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        Location lCar = hmodel.getAgPos(0);
        if (!lCar.equals(hmodel.lRestaurant) && !lCar.equals(hmodel.lMydepot)) {
            c = Color.yellow;
            if (hmodel.carryingFood) c = Color.red;
            super.drawAgent(g, x, y, c, -1);
            g.setColor(Color.black);
            super.drawString(g, x, y, defaultFont, "Car");
        }
    }
}
