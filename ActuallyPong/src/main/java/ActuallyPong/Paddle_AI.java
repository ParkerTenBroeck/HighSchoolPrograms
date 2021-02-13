/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActuallyPong;

import DLibX.DConsole;
import java.awt.Color;
import java.util.List;

/**
 *
 * @author Parker TenBroeck
 */
public class Paddle_AI {

    List<Ball> ball;
    Paddle paddle;
    Collision collision;
    DConsole dc;
    int team;
    boolean debug;
    double moveSpeed = 1;
    boolean canUseSlap;
    boolean canUseDash;
    boolean canUseFreeze;

    public Paddle_AI(Paddle paddle, List<Ball> ball, Collision collision,
            DConsole dc, int difficulty, int powerUps, int team) {

        if (difficulty == 4) {
            this.moveSpeed = 0xfffffff;
        } else {
            this.moveSpeed = difficulty;
        }

        this.canUseSlap = isBitOn(1, powerUps);
        this.canUseDash = isBitOn(2, powerUps);
        this.canUseFreeze = isBitOn(3, powerUps);

        this.paddle = paddle;
        this.ball = ball;
        this.collision = collision;
        this.team = team;
        this.dc = dc;

    }

    public void think(boolean debug) {
        this.debug = debug;

        int targetBall;
        double targetYPos;

        if (team == 0) {  //left side

            targetBall = closestBall();
            if (targetBall == -1) {
                paddle.moveAI(0, 0, false, false, false, false);
                return;
            }
            if (targetBall >= 0) {
                dc.setPaint(Color.WHITE);
                targetYPos = getBallYFromX(ball.get(targetBall), this.paddle.xPosAI() + (this.paddle.sizeX() / 2) + (ball.get(targetBall).size / 2));
                dc.setPaint(new Color(RGBcycle.rgbR, RGBcycle.rgbG, RGBcycle.rgbB));
            } else {
                targetYPos = 300;
            }

            paddle.moveAI(0, yMoveDistance(targetYPos + 15, totalMoveSpeed()),
                    shouldSlap(targetYPos, targetBall), false,
                    shouldDash(targetYPos + 15, paddle.xPos() + (paddle.sizeX() / 2), targetBall), shouldFreeze(10));

        } else if (team == 1) {  //right side

            targetBall = closestBall();
            if (targetBall == -1) {
                paddle.moveAI(0, 0, false, false, false, false);
                return;
            } else if (targetBall >= 0) {
                dc.setPaint(Color.WHITE);
                targetYPos = getBallYFromX(ball.get(targetBall), this.paddle.xPosAI() - (this.paddle.sizeX() / 2) - (ball.get(targetBall).size / 2));
                dc.setPaint(new Color(RGBcycle.rgbR, RGBcycle.rgbG, RGBcycle.rgbB));
            } else {
                targetYPos = 300;
            }
            paddle.moveAI(0, yMoveDistance(targetYPos - 15, totalMoveSpeed()),
                    false, shouldSlap(targetYPos, targetBall),
                    shouldDash(targetYPos - 15, paddle.xPos() - (paddle.sizeX() / 2), targetBall), shouldFreeze(10));
        }

    }

    private int closestBall() {
        double temp;
        double closestValue = 9999;
        int closestI = -1;
        if (team == 0) {
            for (int i = 0; i < ball.size(); i++) {
                temp = getDistanceToCenterRightSide(ball.get(i));
                if (temp < closestValue) {
                    closestValue = temp;
                    closestI = i;
                }
            }
        } else if (team == 1) {
            for (int i = 0; i < ball.size(); i++) {

                temp = getDistanceToCenterLeftSide(ball.get(i));
                if (temp < closestValue) {
                    closestValue = temp;
                    closestI = i;
                }
            }
        } else {

        }
        return closestI;
    }

    private double getDistanceToCenterRightSide(Ball ball) { //gets the distance from the ball to the left side of the paddle

        double paddleHitX = (this.paddle.xPosAI() + (this.paddle.sizeX() / 2));

        if (ball.xPos >= paddleHitX && ball.xSpeed() < 0) {

            //value baced on distance from paddle and X speed towards the paddle
            double tempTargetYPos = getYPosFromStartingPos(ball.xPos, ball.yPos, ball.xV, ball.yV, paddleHitX, ball.size);
            return (Math.abs(paddleHitX - ball.xPos) / Math.abs(ball.xSpeed()))
                    + ((Math.abs(tempTargetYPos - ball.yPos) / Math.abs(ball.ySpeed())) / this.moveSpeed);

        } else {
            return 9999;
        }
    }

    private double getDistanceToCenterLeftSide(Ball ball) { //gets the distance from the ball to the right side of the paddle

        double paddleHitX = (this.paddle.xPosAI() - (this.paddle.sizeX() / 2));

        if (ball.xPos <= paddleHitX && ball.xSpeed() > 0) {
            double tempTargetYPos = getYPosFromStartingPos(ball.xPos, ball.yPos, ball.xV, ball.yV, paddleHitX, ball.size);
            return (Math.abs(paddleHitX - ball.xPos) / Math.abs(ball.xSpeed()))
                    + ((Math.abs(tempTargetYPos - ball.yPos) / Math.abs(ball.ySpeed())) / this.moveSpeed); //value baced on distance from paddle and X speed towards the paddle

        } else {
            return 9999;
        }
    }

    public double getBallYFromX(Ball ball, double xPos) {

        return getYPosFromStartingPos(ball.xPos, ball.yPos, ball.xV, ball.yV, xPos, ball.size);
    }

    public double getYPosFromStartingPos(double initXPos, double initYPos, double initXV, double initYV, double endXPos, int ballSize) {

        double tempXPos = initXPos;    //current x pos
        double tempYPos = initYPos;  //current x pos
        double tempYPos2; //y position out from ball
        double tempXPos2 = 0; //y position out from ball
        double tempXV = initXV;
        double tempYV = initYV;

        while (Math.round(tempXPos2) != Math.round(endXPos)) {

            tempYPos2 = getEndYPos(tempXV, tempYV, tempXPos, tempYPos, endXPos);

            if (tempYPos2 <= 0 + (ballSize / 2)) {

                tempYPos2 = 0 + (ballSize / 2);
                tempXPos2 = (((tempYPos2 - tempYPos) / (tempYV / tempXV)) + tempXPos);
                tempYV = tempYV * -1;

            } else if (tempYPos2 >= 600 - (ballSize / 2)) {

                tempYPos2 = 600 - (ballSize / 2);
                tempXPos2 = (((tempYPos2 - tempYPos) / (tempYV / tempXV)) + tempXPos);
                tempYV = tempYV * -1;

            } else {

                tempXPos2 = endXPos;

            }
            if (debug == true) {
                dc.drawLine(tempXPos, tempYPos, tempXPos2, tempYPos2);
            }
            tempXPos = tempXPos2;
            tempYPos = tempYPos2;
        }

        return tempYPos;
    }

    public boolean shouldSlap(double targetYPos, int tagetBall) {
        // System.out.println(ball.get(tagetBall).xPos + " " + ((paddle.xPosAI() + (paddle.sizeX() / 2)) + 10));
        if (!this.canUseSlap) {
            return false;
        }

        if (team == 0) {
            if (collision.inRangeOfPositive((paddle.xPosAI() + (paddle.sizeX() / 2)) + 10 + ball.get(tagetBall).totalSpeed, ball.get(tagetBall).xPos, 15 + ball.get(tagetBall).totalSpeed)) {
                // System.out.println((paddle.xPosAI() + (paddle.sizeX() / 2)) + " " +  (ball.get(tagetBall).xPos));
                return false;
            }
        } else if (team == 1) {
            if (collision.inRangeOfNegative((paddle.xPosAI() - (paddle.sizeX() / 2)) - 10 - ball.get(tagetBall).totalSpeed, ball.get(tagetBall).xPos, 15 + ball.get(tagetBall).totalSpeed)) {
                return false;
            }
        }
        return collision.inRangeOf(paddle.yPos(), targetYPos, paddle.sizeY() / 2);

    }

    public boolean shouldDash(double targetYPos, double targetXPos, int targetBall) {
        if (canUseDash) {

            double tempBallToPaddleDistance = Math.abs(targetXPos - ball.get(targetBall).xPos) / ball.get(targetBall).totalSpeed;
            double tempPaddleToTargetDistance = Math.abs(targetYPos - paddle.yPos()) / totalMoveSpeed();

            if (tempBallToPaddleDistance <= tempPaddleToTargetDistance) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }

    public boolean shouldFreeze(int ballCountForFreeze) {
if(!canUseDash){
    return false;
}
        int ballInRangeCount = 0;
        for (int i = 0; i < ball.size(); i++) {

            if (collision.isCircleInCircle(ball.get(i).xPos, ball.get(i).yPos, ball.get(i).size,
                    paddle.xPos(), paddle.yPos(), 400)) {

                ballInRangeCount++;
            }
        }

        return ballInRangeCount >= ballCountForFreeze;
    }

    public double yMoveDistance(double targetYPos, double moveSpeed) {

        double tempDistance = targetYPos - paddle.yPos();

        if (Math.abs(tempDistance) > moveSpeed) {

            if (tempDistance > 0) {
                return moveSpeed;
            } else {
                return moveSpeed * -1;
            }

        } else if (Math.abs(tempDistance) < moveSpeed) {
            return tempDistance;
        } else {
            return 0;
        }

    }

    public double getEndYPos(double xV, double yV, double startX, double startY, double endX) {  //gets ending y position
        return ((yV / xV) * (endX - startX)) + startY;
    }

    public boolean isBitOn(int bit, int value) {
        return ((value >> (bit - 1) & 1) == 1);
    }

    public double totalMoveSpeed() {
        return this.moveSpeed * (paddle.dashSpeed + 1);
    }
}
