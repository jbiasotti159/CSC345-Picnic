

import java.awt.*;        // import statements to make necessary classes available
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class PicnicScene extends JPanel {

    public static void main(String[] args) {
        JFrame window;
        window = new JFrame("Picnic Scene");
        PicnicScene panel = new PicnicScene();
        window.setContentPane(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setResizable(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Timer animationTimer;
        final long startTime = System.currentTimeMillis();
        animationTimer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //this is for the sun movement
                double scale = 1;
                double delta = 0.01;
                if (scale < 0.01) {
                    delta = -delta;
                } else if (scale > 0.99) {
                    delta = -delta;
                }
                scale += delta;
                panel.frameNumber++;
                panel.repaint();
            }
        });
        // Center window on screen.
        window.setLocation((screen.width - window.getWidth())/2, (screen.height - window.getHeight())/2);
        window.setVisible(true); // Open the window, making it visible on the screen.
        animationTimer.start();  // Start the timer going.
    }

    private float pixelSize;
    private int frameNumber = 0;
    private int scale = 0;

    public PicnicScene() {
        setPreferredSize(new Dimension(1000,1000) );
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        //double dx = ((frameNumber+150)%600)*0.05;  // The mod helps it "wrap" around

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(new Color(51,204,255));
        g2.fillRect(0,0,getWidth(),getHeight());
        g2.setPaint(new Color(70, 255, 8));
        g2.fill(new Rectangle2D.Double(0, 400, getWidth(), 500));
        g2.setPaint(Color.blue);
        g2.fill(new QuadCurve2D.Double(200,400,500,700,800,400));
        AffineTransform save = g2.getTransform();
        //applyWindowToViewportTransformation(g2, -5, 10, -1, 14, true);
       // drawScene(g2);

        //Drawing the sun
        /*g2.setPaint(new Color(203, 178, 5));
        //g2.scale(scale, scale);
        g2.fill(new Ellipse2D.Double(600, 13, 100, 100));
        g2.setTransform(save);
        g2.setPaint(new Color(203, 178, 5,200));
        //g2.scale(scale, scale);
        g2.fill(new Ellipse2D.Double(600-12, 3, 125, 125));
        g2.setTransform(save);
        g2.setPaint(new Color(203, 178, 5,150));
        //g2.scale(scale, scale);
        g2.fill(new Ellipse2D.Double(600-25, -7, 150, 150));
        g2.setTransform(save);*/
        drawSun(g2);
        double dx = ((frameNumber+10)%1500)*0.05;  // The mod helps it "wrap" around



        //AffineTransform save = g2.getTransform();



        //draw clouds
        g2.setPaint(new Color(240, 240, 240, 200));
        //double dx = ((frameNumber+150)%600)*0.05;  // The mod helps it "wrap" around
        g2.translate(500-dx, 150);  // Move it up and over with the framenumber used for animation...
        drawCloud(g2);
        g2.setTransform(save);
        g2.translate(0-dx, 50);  // Move it up and over with the framenumber used for animation...
        drawCloud(g2);
        g2.setTransform(save);
        g2.translate(-500-dx, 100);  // Move it up and over with the framenumber used for animation...
        drawCloud(g2);
        g2.setTransform(save);

        //draw birds
        g2.scale(0.4,0.4);
        g2.translate(800+dx,0);
        drawBird(g2);
        g2.translate(300+dx,400);
        drawBird(g2);
        g2.setTransform(save);


        //draw trees
        drawTree(g2);
        g2.translate(600, 0);
        drawTree(g2);
        g2.setTransform(save);

        //draw sunflowers
        g2.translate(getWidth()/2+350, getHeight()/2+210);
        g2.scale(7, 7);
        g2.rotate(Math.toRadians(180));
        double[] heights = { 4.0, 6.0, 3.0, 4.5, 5.5 };
        int[] petals = {10, 15, 8, 7, 12};
        drawSunflowerGarden(g2, heights, petals);
        g2.setTransform(save);
        g2.translate(getWidth()/2-250, getHeight()/2+210);
        g2.scale(7, 7);
        g2.rotate(Math.toRadians(180));
        drawSunflowerGarden(g2, heights, petals);
        g2.setTransform(save);

        //draw blanket & basket
        g2.rotate(50);
        g2.translate(-100,0);
        drawBlanket(g2);
        g2.setTransform(save);

    }


    private void drawSeesaw(Graphics2D g2)
    {
        AffineTransform cs = g2.getTransform();
        g2.setColor(Color.black);
        int width = getWidth();
        int height = getHeight();


    }
    private void drawSunflowerGarden(Graphics2D g2, double[] height, int[] petals) {
        AffineTransform cs = g2.getTransform();  // Save C.S. state
        assert(height.length == petals.length);
        for (int i = 0; i < height.length; i++) {
            drawSunflower(g2, height[i], petals[i]);
            g2.translate(4, 0);
        }
        g2.setTransform(cs);  // And restore it...
    }
    private void drawSunflower(Graphics2D g2, double height, int numPetals) {
        AffineTransform cs = g2.getTransform();  // Save C.S. state
        g2.setColor(new Color(0, 150, 0));  // Dark green
        g2.fill(new Rectangle2D.Double(-0.25, 0, 0.5, height));   // Draw stalk
        {
            // Draw one leaf
            AffineTransform cs2 = g2.getTransform();  // Save C.S. state
            g2.translate(0, height/4.0);
            g2.rotate(Math.PI/6);
            g2.fill(new Ellipse2D.Double(0, -0.1, 1, 0.2));
            g2.setTransform(cs2);
        }
        {
            // Draw second leaf
            AffineTransform cs2 = g2.getTransform();  // Save C.S. state
            g2.translate(0, height/2.0);
            g2.rotate(-Math.PI/8);
            g2.scale(-1, 1);  // Reflect it about y-axis
            g2.fill(new Ellipse2D.Double(0, -0.08, 1.2, 0.16));
            g2.setTransform(cs2);
        }

        g2.translate(0, height); // Move to top of stalk

        // Draw the petals (first layer)
        AffineTransform cs2 = g2.getTransform();  // Save C.S. state
        g2.setColor(new Color(240, 240, 0));
        double rotation = 2*Math.PI/numPetals;
        for (int i = 0; i < numPetals; i++) {
            g2.fill(new Ellipse2D.Double(0, -0.25, 3, 0.5));
            g2.rotate(rotation);  // Next petal is slightly rotated
        }
        g2.rotate(rotation/2);  // Slight adjustment for next layer
        g2.setColor(new Color(255, 255, 0, 200));
        for (int i = 0; i < numPetals; i++) {
            g2.fill(new Ellipse2D.Double(0, -0.25, 2.5, 0.5));
            g2.rotate(rotation);  // Next petal is slightly rotated
        }
        g2.setTransform(cs2);

        // Draw the flower center
        g2.setColor(new Color(160,82,45));  // "Sienna"
        g2.fill(new Ellipse2D.Double(-0.75, -0.75, 1.5, 1.5));   // lower left X, lower left Y, width, height

        g2.setTransform(cs);       // Restore previous C.S. state
    }

    private void drawSun(Graphics2D g2){
        // A "sun"
        //double size = 1.5;
        int maxFrameNum = 20;
        //AnimationTimer timer = new AnimationTimer();
        //timer.start();
        double dx = ((frameNumber+10)%50)*0.05;

        AffineTransform cs = g2.getTransform();
        int sunHeight = 10;
        Ellipse2D sun1 = new Ellipse2D.Double(600, sunHeight+10, 100, 100);
        g2.fill(sun1);
        g2.scale(scale, scale);
        g2.setTransform(cs);
        g2.setPaint(new Color(203, 178, 5,200));
        Ellipse2D ring1 = new Ellipse2D.Double(600-12, sunHeight-1, 125-dx, 125-dx);
        g2.fill(ring1);
        g2.scale(scale, scale);
        g2.setTransform(cs);
        g2.setPaint(new Color(203, 178, 5,150));
        //g2.setPaint(Color.black);
        Ellipse2D ring2 = new Ellipse2D.Double(600-25, sunHeight-12, 150+dx, 150+dx);
        g2.fill(ring2);
        g2.scale(scale, scale);
        g2.setTransform(cs);
        g2.setPaint(new Color(203, 178, 5,100));
        Ellipse2D ring3 = new Ellipse2D.Double(600-38, sunHeight-23, 175-dx, 175-dx);
        g2.fill(ring3);
        g2.scale(scale, scale);
        g2.setTransform(cs);
        g2.setPaint(new Color(203, 178, 5,50));
        double size = 10;
        //boolean isGrowing = true;
        Ellipse2D ring4 = new Ellipse2D.Double();
        ring4.setFrame((600 - 51), (sunHeight - 34), 200 + dx, 200+dx);
        g2.fill(ring4);
        g2.scale(scale, scale);
        g2.setTransform(cs);
        //isGrowing = false;
        Ellipse2D ring5 = new Ellipse2D.Double();
        ring5.setFrame((600 - 64), (sunHeight - 45), 225 - dx, 225-dx);
        g2.fill(ring5);
        g2.scale(scale, scale);
        g2.setTransform(cs);






        // lower left X, lower left Y, width, height

    }


    private void drawBlanket(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        g2.setPaint(Color.white);
        g2.fill(new RoundRectangle2D.Double(400,750,210,175,1,1));
        g2.setPaint(new Color(255, 30, 30));
        g2.fill(new Rectangle2D.Double(400,750,10,175));
        g2.fill(new Rectangle2D.Double(425,750,10,175));
        g2.fill(new Rectangle2D.Double(450,750,10,175));
        g2.fill(new Rectangle2D.Double(475,750,10,175));
        g2.fill(new Rectangle2D.Double(500,750,10,175));
        g2.fill(new Rectangle2D.Double(525,750,10,175));
        g2.fill(new Rectangle2D.Double(550,750,10,175));
        g2.fill(new Rectangle2D.Double(575,750,10,175));
        g2.fill(new Rectangle2D.Double(600,750,10,175));
        g2.setPaint(new Color(255, 30, 30, 120));
        g2.fill(new Rectangle2D.Double(400,750,200,10));
        g2.fill(new Rectangle2D.Double(400,775,200,10));
        g2.fill(new Rectangle2D.Double(400,800,200,10));
        g2.fill(new Rectangle2D.Double(400,825,200,10));
        g2.fill(new Rectangle2D.Double(400,850,200,10));
        g2.fill(new Rectangle2D.Double(400,875,200,10));
        g2.fill(new Rectangle2D.Double(400,900,200,10));
        g2.fill(new Rectangle2D.Double(400,920,200,10));
        g2.setPaint(new Color(87, 35, 0));
        g2.rotate(-50);
        g2.fill(new RoundRectangle2D.Double(770,630,100,70,20,20));
        g2.setStroke(new BasicStroke(8));
        g2.draw(new QuadCurve2D.Double(780,630,830,570,860,630));
        g2.setTransform(cs);
    }
    private void drawBird(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        double dx = ((frameNumber+50)%600)*0.05;
        //double dx = ((frameNumber+10)%50)*0.05;

        g2.setPaint(new Color(14, 0, 2));
        g2.setStroke(new BasicStroke(6));
        g2.draw(new QuadCurve2D.Double(100,400,150,350+dx,200,400));
        g2.draw(new QuadCurve2D.Double(200,400,250,350+dx,300,400));
        g2.setTransform(cs);
    }
    private void drawCloud(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();  // Save C.S. state
        Path2D cloud = new Path2D.Double();
        int sunHeight = 10;
        int width = getWidth();
        int height = getHeight();
        cloud.moveTo(64, 112);                   // These coordinates were used because that was the frame
        cloud.curveTo(32, 144, 0, 48, 64, 64);   // of reference when I drew them in a separate drawing program!
        cloud.curveTo(32, 16, 112, 16, 112, 64);
        cloud.curveTo(144, 16, 192, 16, 192, 80);
        cloud.curveTo(240, 16, 304, 160, 192, 144);
        cloud.curveTo(240, 192, 64, 192, 112, 128);
        cloud.curveTo(96, 160, 48, 144, 64, 112);
        cloud.closePath();
        g2.scale(0.9, 0.9);
        g2.translate(width/2, sunHeight-5);
        g2.fill(cloud);
        g2.setTransform(cs);
    }



    protected void drawTree(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        int width = getWidth();
        int height = getHeight();
        g2.setColor(new Color(139, 69, 19));
        g2.fillRect(((width / 2) - 30)-300, (height / 2)-90, 65, height / 3);
        g2.setColor(new Color(26, 139, 51));
        int radius = 70;
        g2.fillOval(((width / 2) - radius)-300, ((height / 2) - (radius * 2))-90, radius * 2, radius * 2);
        g2.fillOval(((width / 2) - radius)-300, ((height / 2) - radius)-90, radius * 2, radius * 2);
        g2.fillOval(((width / 2) - (radius * 2)-300), ((height / 2) - radius)-90, radius * 2, radius * 2);
        g2.fillOval((width / 2)-300, ((height / 2) - radius)-90, radius * 2, radius * 2);
        g2.setTransform(cs);
    }

    private void applyWindowToViewportTransformation(Graphics2D g2,
                                                     double left, double right, double bottom, double top,
                                                     boolean preserveAspect) {
        int width = getWidth();   // The width of this drawing area, in pixels.
        int height = getHeight(); // The height of this drawing area, in pixels.
        if (preserveAspect) {
            // Adjust the limits to match the aspect ratio of the drawing area.
            double displayAspect = Math.abs((double)height / width);
            double requestedAspect = Math.abs(( bottom-top ) / ( right-left ));
            if (displayAspect > requestedAspect) {
                // Expand the viewport vertically.
                double excess = (bottom-top) * (displayAspect/requestedAspect - 1);
                bottom += excess/2;
                top -= excess/2;
            }
            else if (displayAspect < requestedAspect) {
                // Expand the viewport vertically.
                double excess = (right-left) * (requestedAspect/displayAspect - 1);
                right += excess/2;
                left -= excess/2;
            }
        }
        g2.scale( width / (right-left), height / (bottom-top) );
        g2.translate( -left, -top );
        double pixelWidth = Math.abs(( right - left ) / width);
        double pixelHeight = Math.abs(( bottom - top ) / height);
        pixelSize = (float)Math.max(pixelWidth,pixelHeight);
    }
}