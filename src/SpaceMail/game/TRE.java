/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceMail.game;


import SpaceMail.GameConstants;
import SpaceMail.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class TRE extends JPanel implements Runnable {

    //private Image bgImg = Resource.getResourceImage("space-bg");
    private BufferedImage world;
    static long tick = 0;
    final private Launcher lf;
    static ArrayList<GameObject> gameObjects;
    static ArrayList<Barrier> barriers;
    static ArrayList<Meteor> meteors;
    static ArrayList<BarrierCannon> barrierCannons;
    private PlayerSpaceShip pss;

    public TRE(Launcher lf){
        this.lf = lf;
    }

    public static void add_meteor(Meteor m)
    {
        meteors.add(m);
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
               this.tick++;
               this.pss.update(); // update ship
               this.repaint();   // redraw game

               for (Barrier element : barriers) {
                   if ((element != null) && element.getHitBox().intersects(this.pss.getHitBox())) {
                       if (this.pss.isUpPressed()) {
                           this.pss.setY(this.pss.getY() - this.pss.getVy());
                           this.pss.setX(this.pss.getX() - this.pss.getVx());
                       } else if (this.pss.isDownPressed()) {
                           this.pss.setY(this.pss.getY() + this.pss.getVy());
                           this.pss.setX(this.pss.getX() + this.pss.getVx());
                       }
                   }
               }

               for (GameObject gameObject : gameObjects) {
                   if (gameObject instanceof Planet && gameObject.getHitBox().intersects(this.pss.getHitBox()) && this.tick % 50 == 0) {
                       //System.out.println("PSC landed.");
                       this.pss.setHas_landed(true);
                       //System.out.println("pss.isHas_Landed = " + pss.isHas_landed());
                       this.pss.setGas(this.pss.getGas() + 5);
                   }
               }

               for (GameObject gameObject : gameObjects) {
                   if (gameObject instanceof Planet && !(pss.getHitBox().intersects(gameObject.getHitBox())) && this.tick % 300 == 0) {
                       this.pss.setHas_landed(false);
                       //System.out.println("pss.isHas_Landed = " + pss.isHas_landed());
                   }
               }


               for (GameObject gameObject : gameObjects) {
                   if ((gameObject instanceof Planet) && !(this.pss.getHitBox().intersects(gameObject.getHitBox())) && !pss.isHas_landed()) {
                       if (this.tick % 100 == 0) {
                           //System.out.println("PSC gas = " + this.pss.getGas());
                           this.pss.setGas(this.pss.getGas() - 1);
                       }
                   }
               }

               for (BarrierCannon element : barrierCannons)
               {
                   element.update();
               }

               for(int i = 0; i < meteors.size(); i++)
               {
                   if(meteors.get(i).getHitBox().intersects(barriers.get(i).getHitBox()))
                   {
                        //System.out.println("Meteor collided with barrier.");
                        /*meteors.get(i).setUpPressed(false);
                        meteors.get(i).setDownPressed(false);
                        meteors.get(i).setDraw_img(false);*/
                       if (meteors.get(i).isUpPressed()) {
                           meteors.get(i).setY(meteors.get(i).getY() - meteors.get(i).getVy());
                           meteors.get(i).setX(meteors.get(i).getX() - meteors.get(i).getVx());
                           meteors.get(i).setDraw_img(false);
                           meteors.get(i).setDownPressed(false);
                           meteors.get(i).setUpPressed(false);
                       } else if (meteors.get(i).isDownPressed()) {
                           meteors.get(i).setY(meteors.get(i).getY() + meteors.get(i).getVy());
                           meteors.get(i).setX(meteors.get(i).getX() + meteors.get(i).getVx());
                           meteors.get(i).setDraw_img(false);
                           meteors.get(i).setUpPressed(false);
                           meteors.get(i).setDownPressed(false);
                       }
                   }
                   if(meteors.get(i).getHitBox().intersects(pss.getHitBox()) && !pss.isHas_landed())
                   {
                       //System.out.println("Meteor hit pss");
                       //System.out.println("pss.isHas_Landed = " + pss.isHas_landed());
                       pss.setGas(pss.getGas() - 25);
                       meteors.get(i).setUpPressed(false);
                       meteors.get(i).setDownPressed(false);
                       meteors.get(i).setDraw_img(false);
                       if (this.pss.isUpPressed()) {
                           this.pss.setY(this.pss.getY() - this.pss.getVy());
                           this.pss.setX(this.pss.getX() - this.pss.getVx());
                       } else if (this.pss.isDownPressed()) {
                           this.pss.setY(this.pss.getY() + this.pss.getVy());
                           this.pss.setX(this.pss.getX() + this.pss.getVx());
                       }
                   }
               }



               Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                if(tick > 2500){
                    // reset to make sure tick never hits end game w/o pss running out of gas first
                    tick = 0;
                    // when pss is out of gas, set tick to 0 to actually end game
                }
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        //meteors = new ArrayList<>();
        this.pss.setX(96);
        this.pss.setY(488);
        this.pss.setGas(100);
        tick = 0;
    }

    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                                       GameConstants.WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        gameObjects = new ArrayList<>();
        barriers = new ArrayList<>();
        meteors = new ArrayList<>();
        barrierCannons = new ArrayList<>();

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            InputStreamReader isr = new InputStreamReader((Objects.requireNonNull(TRE.class.getClassLoader().getResourceAsStream("maps/map1"))));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            /* outer grabs a row from the file and inner loop is going to go through each column in the row we just read */
            for(int curRow = 0; curRow < numRows; curRow++)
            {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for(int curCol = 0; curCol < numCols; curCol++) // -21
                {
                    switch(mapInfo[curCol])
                    {
                        // background
                        case "0":
                            gameObjects.add(new Space(curCol * 1133, curRow * 850, Resource.getResourceImage("space-bg")));
                            break;
                        // moons
                        case "1":
                            gameObjects.add(new Planet(curCol * 64, curRow * 64, Resource.getResourceImage("moons")));
                            break;
                        // barrier walls
                        case "2":
                            barriers.add(new Barrier(curCol * 30, curRow * 30, Resource.getResourceImage("barrier")));
                            break;
                        // barrier cannons located on top
                        case "3":
                            barrierCannons.add(new BarrierCannon(curCol * 30, curRow * 30, Resource.getResourceImage("barrier"), 90));
                            break;
                            // barrier canons located on bottom
                        case "4":
                            barrierCannons.add(new BarrierCannon(curCol * 30, curRow * 30, Resource.getResourceImage("barrier"), -90));
                            break;
                            // randomly generated planets
                        case "5":
                            Random rand = new Random();
                            int randomNum = rand.nextInt((2 - 1) + 1) + 1;
                            switch(randomNum)
                            {
                                case 1:
                                    // planet
                                    gameObjects.add(new Planet(curCol * 64, curRow * 64, Resource.getResourceImage("moons")));
                                    break;
                                case 2:
                                    // no planet

                                    break;
                            }
                    }

                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        this.setBackground(Color.BLACK);
        pss = new PlayerSpaceShip(96, 488, 0,0,0,100, Resource.getResourceImage("ship"));
        SpaceShipControl psc_control = new SpaceShipControl(pss, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_E);
        this.lf.getJf().addKeyListener(psc_control);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.fillRect(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        gameObjects.forEach(gameObjects -> gameObjects.drawImage(buffer));
        barriers.forEach(barrier -> barrier.drawImage(buffer));
        barrierCannons.forEach(barrierCannons -> barrierCannons.drawImage(buffer));
        this.pss.drawImage(buffer);
        g2.drawImage(world, 0,0, null);
        g2.setColor(Color.RED);
        g2.drawString("Remaining Gas: " + this.pss.getGas(), GameConstants.SCREEN_WIDTH / 10 - 50, GameConstants.SCREEN_HEIGHT / 2 + 300);
        g2.drawRect(GameConstants.SCREEN_WIDTH / 10 - 50, GameConstants.SCREEN_HEIGHT / 2 + 300, 100, 10);
        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.pss.getGas() && i < 100; i++) {
            g2.drawRect(GameConstants.SCREEN_WIDTH / 10 - 50 + i, GameConstants.SCREEN_HEIGHT / 2 + 300, 1, 10);
        }
        if(pss.getGas() >= 0 && pss.getGas() <= 50)
        {
            g2.setColor(Color.RED);
            g2.setFont(new Font("TimesRoman", Font.BOLD, 30));
            if(tick % 10 == 0)
                g2.drawString("REFUEL SOON", GameConstants.SCREEN_WIDTH/2-125, GameConstants.SCREEN_HEIGHT/2);
        }
        if(pss.getGas() <= 0)
        {
            this.lf.setFrame("end");
        }

        // for testing purposes
        /*g2.setColor(Color.LIGHT_GRAY);
        g2.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g2.drawString("Time Left: " + (2500-tick), GameConstants.SCREEN_WIDTH/2-150, GameConstants.SCREEN_HEIGHT/2-350);*/
        }
}


