/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActuallyPong;

/**
 *
 * @author Parker TenBroeck
 */
public class Collision {

    public Collision() {

    }

    public boolean ballToPaddle(Ball ball, double rectX, double rectY, int rectXSize, int rectYSize, double slapSpeed) {

        int temp = 0;

        if (slapSpeed != 0) {

            if (slapSpeed > 0) { //slaps to the left 

                if (inRangeOfNegative(ball.xPos + (ball.size / 2), rectX - (rectXSize / 2) + Math.abs(slapSpeed * 3), Math.abs(slapSpeed * 3) + ball.totalSpeed) && inRangeOf(ball.yPos, rectY  + ball.totalSpeed, (rectYSize / 2) + (ball.size / 2) + ball.totalSpeed)) {
                    ball.slapSpeed = Math.abs(slapSpeed) + ball.slapSpeed;
                    ball.xPos = rectX - (rectXSize / 2) - (ball.slapSpeed * 3);
                    
                }

            } else if (slapSpeed < 0) {  //slaps to the right 

                if (inRangeOfPositive(ball.xPos - (ball.size / 2), rectX + (rectXSize / 2) - Math.abs(slapSpeed * 3), Math.abs(slapSpeed * 3) + ball.totalSpeed) && inRangeOf(ball.yPos, rectY  + ball.totalSpeed, (rectYSize / 2) + (ball.size / 2) + ball.totalSpeed)) {
                    ball.slapSpeed = Math.abs(slapSpeed) + ball.slapSpeed;
                    ball.xPos = rectX + (rectXSize / 2) + (ball.slapSpeed * 3);
                    
                }

                //  if(inRangeOf())
            }
        }

        if (inRangeOf(ball.xPos + (ball.size / 2), rectX - (rectXSize / 2), Math.abs(ball.xSpeed()) + 6) && inRangeOf(ball.yPos, rectY, (rectYSize / 2) + ball.size)) {
            ball.yV = (ball.yPos - rectY) / (rectYSize / 2);
            ball.xV = Math.abs(ball.xV) * -1;
          //  System.out.println("hit");
            temp++;
        }
        if (inRangeOf(ball.xPos - (ball.size / 2), rectX + (rectXSize / 2), Math.abs(ball.xSpeed()) + 6) && inRangeOf(ball.yPos, rectY, (rectYSize / 2) + ball.size)) {
            ball.yV = (ball.yPos - rectY) / (rectYSize / 2);
            ball.xV = Math.abs(ball.xV) * 1;
           // System.out.println("hit");
            temp++;
        }
        if (inRangeOf(ball.yPos + (ball.size / 2), rectY - (rectYSize / 2), ball.totalSpeed) && inRangeOf(ball.xPos, rectX, (rectXSize / 2) + ball.size)) {
            ball.yV = Math.abs(ball.yV) * -1;
            temp++;
        }
        if (inRangeOf(ball.yPos - (ball.size / 2), rectY + (rectYSize / 2), ball.totalSpeed) && inRangeOf(ball.xPos, rectX, (rectXSize / 2) + ball.size)) {
            ball.yV = Math.abs(ball.yV) * 1;
            temp++;
        }
        return temp > 0;
    }

    public boolean ballToWall(Ball ball, int maxX, int minX, int maxY, int minY) {

        int temp = 0;

        if (ball.xPos + (ball.size / 2) >= maxX) {
            ball.xV = Math.abs(ball.xV) * -1;
            temp++;
        }
        if (ball.xPos - (ball.size / 2) <= minX) {
            ball.xV = Math.abs(ball.xV) * 1;
            temp++;
        }
        if (ball.yPos + (ball.size / 2) >= maxY) {
            ball.yV = Math.abs(ball.yV) * -1;
            temp++;
        }
        if (ball.yPos - (ball.size / 2) <= minY) {
            ball.yV = Math.abs(ball.yV) * 1;
            temp++;
        }
        return temp > 0;
    }

    public boolean isCircleInCircle(double x1, double y1, int size1, double x2, double y2, int size2) {  //detects if a circle is inside another circle
        double r1 = size1 / 2;
        double r2 = size2 / 2;

        double C1C2 = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));

        return C1C2 < (r1 + r2);
    }

    public boolean inRangeOf(double value1, double value2, double range) { //returns a one if value 1 is in the range of value 2 defined by the range in both directions 
        return ((value1 <= value2 + range) && (value1 >= value2 - range));

    }

    public boolean inRangeOfPositive(double value1, double value2, double range) { //returns a one if value 1 is in the range of value 2 defined by the range only in the positive diraction
        return ((value1 <= value2 + range) && (value1 >= value2));

    }

    public boolean inRangeOfNegative(double value1, double value2, double range) { //returns a one if value 1 is in the range of value 2 defined by the range only in the negative direction
        return ((value1 <= value2) && (value1 >= value2 - range));

    }
}
