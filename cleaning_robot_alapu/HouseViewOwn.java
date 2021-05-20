import jason.environment.grid.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


/** class that implements the View of Domestic Car application */
public class HouseViewOwn extends GridWorldView {

    HouseModelOwn hmodel;

    public HouseViewOwn(HouseModelOwn model) {
        super(model, "Restaurant Delivery System", 800);
        hmodel = model;
        defaultFont = new Font("Arial", Font.BOLD, 12); // change default font
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
			case HouseModelOwn.MYDEPOTMOTOR:
			/*if (lMotor.equals(hmodel.lMydepotmotor)) {
					super.drawAgent(g, x, y, Color.green, -2);
				}*/
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, "MotorDepot ("+hmodel.availableJuices+")");
            break;
		case HouseModelOwn.MYDEPOTBIC:
			/*if (lBicycle.equals(hmodel.lMydepotbic)) {
					super.drawAgent(g, x, y, Color.green, -3);
				}*/
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, "BicycleDepot");
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
		switch (id) {
			case 0:
				Location lCar = hmodel.getAgPos(0);
				if (!lCar.equals(hmodel.lRestaurant) && !lCar.equals(hmodel.lMydepot)) {
					c = Color.yellow;
					if (hmodel.carryingFood) c = Color.red;
					super.drawAgent(g, x, y, c, -1);
					g.setColor(Color.black);
					super.drawString(g, x, y, defaultFont, "Car");
			}
			case 1:
				Location lMotor = hmodel.getAgPos(1);
				c = Color.green;								
				super.drawAgent(g, x+1, y+1, c, -1);
				g.setColor(Color.black);
				super.drawString(g, x+1, y+1, defaultFont, "Motor");
				/*if (!lMotor.equals(hmodel.lRestaurant) && !lMotor.equals(hmodel.lMydepotmotor)) {
						c = Color.green;
						if (hmodel.carryingJuice) c = Color.red;
						super.drawAgent(g, x, y, c, -1);
						g.setColor(Color.black);
						super.drawString(g, x, y, defaultFont, "Motor");
				}*/
			case 2:
				c = Color.magenta;
				super.drawAgent(g, 8, 4, c, -1);
				g.setColor(Color.black);
				super.drawString(g, 8, 4, defaultFont, "Bicycle");
		}
        
		/*else if (!lMotor.equals(hmodel.lRestaurant) && !lMotor.equals(hmodel.lMydepotmotor) && id==1) {
            c = Color.green;
            if (hmodel.carryingJuice) c = Color.red;
            super.drawAgent(g, x, y, c, -2);
            g.setColor(Color.black);
            super.drawString(g, x, y, defaultFont, "Motor");
        }*/
    }
}
