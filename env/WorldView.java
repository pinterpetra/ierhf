package env;

import jason.environment.grid.GridWorldView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class WorldView extends GridWorldView {

    private static final long serialVersionUID = 1L;

    Environment env = null;

    JLabel     jCycle;
    JLabel     jGoldsC;

    JLabel     jlMouseLoc;
    JComboBox  scenarios;
    JSlider    jSpeed;


    public void destroy() {
        setVisible(false);
    }

    public WorldView(WorldModel model, int windowSize) {
        this("Mining World", model, windowSize);
    }

    public WorldView(String title, WorldModel model) {
        this(title,model,800);
    }

    public WorldView(String title, WorldModel model, int windowSize) {
        super(model, title, windowSize);
        setVisible(true);
        repaint();
    }

    @Override
    public void initComponents(int width) {
        super.initComponents(width);
        scenarios = new JComboBox();
        for (int i=1; i<=13; i++) {
            scenarios.addItem(i);
        }
        JPanel args = new JPanel();
        args.setLayout(new BoxLayout(args, BoxLayout.Y_AXIS));

        JPanel sp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sp.setBorder(BorderFactory.createEtchedBorder());
        sp.add(new JLabel("Scenario:"));
        sp.add(scenarios);

        jSpeed = new JSlider();
        jSpeed.setMinimum(0);
        jSpeed.setMaximum(400);
        jSpeed.setValue(50);
        jSpeed.setPaintTicks(true);
        jSpeed.setPaintLabels(true);
        jSpeed.setMajorTickSpacing(100);
        jSpeed.setMinorTickSpacing(20);
        jSpeed.setInverted(true);
        Hashtable<Integer,Component> labelTable = new Hashtable<Integer,Component>();
        labelTable.put( 0, new JLabel("max") );
        labelTable.put( 200, new JLabel("speed") );
        labelTable.put( 400, new JLabel("min") );
        jSpeed.setLabelTable( labelTable );
        JPanel p = new JPanel(new FlowLayout());
        p.setBorder(BorderFactory.createEtchedBorder());
        p.add(jSpeed);

        args.add(sp);
        args.add(p);

        JPanel msg = new JPanel();
        msg.setLayout(new BoxLayout(msg, BoxLayout.Y_AXIS));
        msg.setBorder(BorderFactory.createEtchedBorder());

        p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(new JLabel("Click on the cells to add new pieces of gold."));
        p.add(new JLabel("  (mouse at:"));
        jlMouseLoc = new JLabel("0,0)");
        p.add(jlMouseLoc);
        msg.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(new JLabel("Cycle:"));
        jCycle = new JLabel("0");
        p.add(jCycle);
        p.add(new JLabel("        Collected golds (red x blue / total):"));
        jGoldsC = new JLabel("0");
        p.add(jGoldsC);
        msg.add(p);

        JPanel s = new JPanel(new BorderLayout());
        s.add(BorderLayout.WEST, args);
        s.add(BorderLayout.CENTER, msg);
        getContentPane().add(BorderLayout.SOUTH, s);

        // Events handling
        jSpeed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (env != null) {
                    env.setSleep((int)jSpeed.getValue());
                }
            }
        });

        scenarios.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ievt) {
                int w = ((Integer)scenarios.getSelectedItem()).intValue();
                if (env != null && env.getSimId() != w) {
                    env.startNewWorld(w);
                }
            }
        });

        getCanvas().addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / cellSizeW;
                int lin = e.getY() / cellSizeH;
                if (col >= 0 && lin >= 0 && col < getModel().getWidth() && lin < getModel().getHeight()) {
                    WorldModel wm = (WorldModel)model;
                    wm.add(WorldModel.GOLD, col, lin);
                    wm.setInitialNbGolds(wm.getInitialNbGolds()+1);
                    update(col, lin);
                }
            }
            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
        });

        getCanvas().addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) { }
            public void mouseMoved(MouseEvent e) {
                int col = e.getX() / cellSizeW;
                int lin = e.getY() / cellSizeH;
                if (col >= 0 && lin >= 0 && col < getModel().getWidth() && lin < getModel().getHeight()) {
                    jlMouseLoc.setText(col+","+lin+")");
                }
            }
        });
    }

    public void setEnv(Environment env) {
        this.env = env;
        scenarios.setSelectedIndex(env.getSimId()-1);
    }

    public void setCycle(int c) {
        if (jCycle != null) {
            WorldModel wm = (WorldModel)model;

            String steps = "";
            if (wm.getMaxSteps() > 0) {
                steps = "/" + wm.getMaxSteps();
            }
            jCycle.setText(c+steps);

            jGoldsC.setText(wm.getGoldsInDepotRed() + " x " + wm.getGoldsInDepotBlue() + "/" + wm.getInitialNbGolds());
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int object) {
        switch (object) {
        case WorldModel.DEPOT:
            drawDepot(g, x, y);
            break;
        }
    }

    @Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        int golds = ((WorldModel)model).getGoldsWithAg(id);
        int nbAgByTeam = ((WorldModel)model).getAgsByTeam();
        if (id == 0 || id == 1) {
            // red team
            int gw = (WorldModel.AG_CAPACITY - golds) + 1;
            g.setColor(Color.red);
            g.fillOval(x * cellSizeW + gw, y * cellSizeH + gw, cellSizeW - gw*2, cellSizeH - gw*2);
            if (id >= 0) {
                g.setColor(Color.black);
                drawString(g, x, y, defaultFont, String.valueOf(id+1));
            }
        }
        else if (id == 2 || id == 3) {
            // green team
            int gw = (WorldModel.AG_CAPACITY - golds) + 1;
            g.setColor(Color.green);
            g.fillOval(x * cellSizeW + gw, y * cellSizeH + gw, cellSizeW - gw*2, cellSizeH - gw*2);
            if (id >= 0) {
                g.setColor(Color.black);
                drawString(g, x, y, defaultFont, String.valueOf(id+1));
            }
        }
        else {
            // blue team
            int gw = (WorldModel.AG_CAPACITY - golds) + 1;
            g.setColor(Color.blue);
            g.fillOval(x * cellSizeW + gw, y * cellSizeH + gw, cellSizeW - gw*2, cellSizeH - gw*2);
            if (id >= 0) {
                g.setColor(Color.white);
                drawString(g, x, y, defaultFont, String.valueOf(id-(nbAgByTeam-1)));
            }
        }
        if (golds > 0) {
            g.setColor(Color.darkGray);
            g.fillRect(x*cellSizeW+3, (y+1)*cellSizeH-3, (cellSizeW-6)/(WorldModel.AG_CAPACITY+1-golds), 2);
        }
    }

    //ez az etterem!!!
    public void drawDepot(Graphics g, int x, int y) {
        g.setColor(Color.gray);
        g.fillRect(x * cellSizeW, y * cellSizeH, cellSizeW, cellSizeH);
        g.setColor(Color.white);
        g.drawRect(x * cellSizeW + 2, y * cellSizeH + 2, cellSizeW - 4, cellSizeH - 4);
        g.drawLine(x * cellSizeW + 2, y * cellSizeH + 2, (x + 1) * cellSizeW - 2, (y + 1) * cellSizeH - 2);
        g.drawLine(x * cellSizeW + 2, (y + 1) * cellSizeH - 2, (x + 1) * cellSizeW - 2, y * cellSizeH + 2);
    }

    public static void main(String[] args) throws Exception {
        //new WorldView("Test - Fence", worldFromContest2007("Semiramis"), 1000);
    }

}
