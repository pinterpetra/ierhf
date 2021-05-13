package env;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/** factory for some scenarios */
public class WorldFactory  {


    // to test scan
    public static WorldModel world2() throws Exception {
        WorldModel model = new WorldModel(30, 30, 6);
        model.setDepot(5, 7);
        model.setAgPos(0, 1, 0);
        model.setAgPos(1, 1, 2);
        model.setAgPos(2, 1, 3);
        model.setAgPos(3, 1, 4);
        model.setAgPos(4, 1, 5);
        model.setAgPos(5, 1, 6);
        model.setInitialNbGolds(model.countObjects(WorldModel.GOLD));
        return model;
    }

    private static int[] getArray(Element e, String id) {
        NodeList l = e.getElementsByTagName("array");
        for (int i=0; i<l.getLength(); i++) {
            Element n = (Element)l.item(i);
            if (id.equals(n.getAttribute("meta:name"))) {
                int[] r = new int[Integer.parseInt(n.getAttribute("meta:length"))];
                for (int ai=0; ai<r.length; ai++) {
                    r[ai] = Integer.parseInt(n.getAttribute("item"+ai));
                }
                return r;
            }
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
        //new WorldView("Test - Fence", worldFromContest2007("Semiramis"), 1000);
    }
}
