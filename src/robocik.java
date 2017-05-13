/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Grzegorz
 */
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.awt.geom.Point2D;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.ArrayList;

public class robocik extends Robot {

    boolean peek; // Don't turn if there's a robot there
    double moveAmount; // How much to move
    Random rand = new Random();
    Point target;
    double angle;
    double dist;

    public void run() {

        int borderRange = getSentryBorderSize() - 20;

// ruch robota
        while (true) {
            if (target == null) {
                if(rand.nextDouble()>0.9){
                    turnRight(rand.nextDouble()*180-90);
                }
                if ((getY() > getBattleFieldHeight() - borderRange && (getHeading() < 90 || getHeading() > 270)) || (getY() < borderRange && (getHeading() > 90 && getHeading() < 270))) {
                    if (rand.nextBoolean()) {
                        turnRight(rand.nextInt(90) + 90);
                    } else {
                        turnLeft(rand.nextInt(90) + 90);
                    }
                    ahead(10);
                } else if ((getX() < borderRange && (getHeading() >= 180 && getHeading() <= 360)) || (getX() > getBattleFieldWidth() - borderRange && (getHeading() >= 0 && getHeading() <= 180))) {
                    if (rand.nextBoolean()) {
                        turnRight(rand.nextInt(90) + 90);
                    } else {
                        turnLeft(rand.nextInt(90) + 90);
                    }
                    ahead(10);
                } else {
                    ahead(10);
                }
            }
            else {
                angle=-Math.toDegrees(Math.atan2(target.y - getY(), target.x - getX()));
                angle+=90;
                if(angle<0)
                angle+=360;
                angle-=getHeading();
                if(angle>180)
                    angle-=360;
                else if(angle<-180)
                    angle+=360;
                
                dist=Math.sqrt((target.x-getX())*(target.x-getX()) + (target.y-getY())*(target.y-getY()));
                if(Math.abs(angle)>1) turnRight(angle);

                if(dist>10 && Math.abs(angle)<=1)
                ahead(10);
                else if (Math.abs(angle)<1)
                    target=null;
                
                
                
            }
        }

    }

    public void onScannedRobot(ScannedRobotEvent e) { // wyniki z radaru

    }

    public void onMouseClicked(MouseEvent e) {
        target = new Point(e.getX(), e.getY());

    }

    public void onPaint(Graphics2D g) {

        g.setColor(Color.RED);
        g.drawString("Współrzędne:", 20, 400);
        g.drawString("X: " + (int) getX() + "    Y: " + (int) getY(), 20, 380);
        if(target!=null)
        g.drawString("X: " + (int) target.getX() + "    Y: " + (int) target.getY(), 20, 360);
        g.drawString("angle: " +  angle +"head: "+getHeading() + "dist: "+dist , 20, 340);

    }
}
