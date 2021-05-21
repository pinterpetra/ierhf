import jason.environment.grid.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class View extends GridWorldView {

    Model hmodel;

    public View(Model model) {
        super(model, "Restaurant Delivery System", 800);
        hmodel = model;
        defaultFont = new Font("Arial", Font.BOLD, 12);
        setVisible(true);
        repaint();
    }

    @Override
    public void draw(Graphics g, int x, int y, int object) {
        Location lCar = hmodel.getAgPos(0);
		Location lMotor = hmodel.getAgPos(1);
		Location lBicycle = hmodel.getAgPos(2);
		
        super.drawAgent(g, x, y, Color.lightGray, -1);
		switch (object) {
        case Model.MYDEPOT:
            if (lCar.equals(hmodel.lMydepot)) {
                super.drawAgent(g, x, y, Color.orange, -1);
            }
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, "CarD. ("+hmodel.availableFoods+")");
            break;
			
		case Model.MYDEPOTMOTOR:
				if (lMotor.equals(hmodel.lMydepotmotor)) {
					super.drawAgent(g, x, y, Color.orange, -1);
				}
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, "MotorD. ("+hmodel.availableJuices+")");
            break;
			
		case Model.MYDEPOTBIC:
			if (lBicycle.equals(hmodel.lMydepotbic)) {
					super.drawAgent(g, x, y, Color.orange, -1);
				}
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, "BicycleD. ("+hmodel.availableCookies+")");
            break;
			
        case Model.RESTAURANT:
            if (lCar.equals(hmodel.lRestaurant)) {
                super.drawAgent(g, x, y, Color.orange, -1);
            }
			else if (lMotor.equals(hmodel.lRestaurant)) {
                super.drawAgent(g, x, y, Color.orange, -1);
            }
			else if (lBicycle.equals(hmodel.lRestaurant)) {
                super.drawAgent(g, x, y, Color.orange, -1);
            }
			String srestaurant = "Restaurant";
            String sfood = "food:"+hmodel.foodConsumptionCount;
			String sjuice ="juice:"+hmodel.juiceConsumptionCount;
			String scookie ="cookie:"+hmodel.cookieConsumptionCount;

			g.setColor(Color.black);
			drawString(g, x, y, defaultFont, srestaurant);
            drawString(g, x-1, y-1, defaultFont, sfood);
			drawString(g, x-1, y, defaultFont, sjuice);
			drawString(g, x, y-1, defaultFont, scookie);
			
            break;
        }
        repaint();
    }

    @Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
		if(id == 0) {
                Location lCar = hmodel.getAgPos(0);
                if (!lCar.equals(hmodel.lRestaurant) && !lCar.equals(hmodel.lMydepot)) {
                    c = Color.yellow;
                    if (hmodel.carryingFood) c = Color.red;
                    super.drawAgent(g, x, y, c, id);
                    g.setColor(Color.black);
                    super.drawString(g, x, y, defaultFont, "Car");
				}
			}
        else if(id == 1) {
                Location lMotor = hmodel.getAgPos(1);
                if (!lMotor.equals(hmodel.lRestaurant) && !lMotor.equals(hmodel.lMydepotmotor)) {
                        c = Color.green;
                        if (hmodel.carryingJuice) c = Color.red;
                        super.drawAgent(g, x, y, c, id);
                        g.setColor(Color.black);
                        super.drawString(g, x, y, defaultFont, "Motor");
                }
            }
		else if(id == 2) {
                Location lBicycle = hmodel.getAgPos(2);
				if (!lBicycle.equals(hmodel.lRestaurant) && !lBicycle.equals(hmodel.lMydepotbic)) {
						c = Color.magenta;
						if (hmodel.carryingCookie) c = Color.red;
						super.drawAgent(g, x, y, c, -1);
						g.setColor(Color.black);
						super.drawString(g, x, y, defaultFont, "Bicycle");
				}
           }
	}
}
