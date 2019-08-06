package com.codecool.snake.entities.enemies;

import com.codecool.snake.Display;
import com.codecool.snake.Game;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.entities.snakes.SnakeHead;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

import javafx.geometry.Point2D;



public class SimpleEnemy extends Enemy implements Animatable, Interactable {

    private Point2D heading;
    private static Random rnd = new Random();

    public SimpleEnemy() {
        super(10);

        setImage(Globals.getInstance().getImage("SimpleEnemy"));
//        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
//        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
//        setX(250);
//        setY(250);
        double possibleX = rnd.nextDouble() * Globals.WINDOW_WIDTH;
        double possibleY = rnd.nextDouble() * Globals.WINDOW_HEIGHT;
        if(isSnakeThereWhenSpanning(possibleX, possibleY)){
            setX(possibleX);
            setY(possibleY);

        }
//        System.out.println(getX());
//        System.out.println((getY()));

        double direction = rnd.nextDouble() * 360;
        setRotate(direction);

        int speed = 1;
        heading = Utils.directionToVector(direction, speed);
    }

    private boolean isSnakeThereWhenSpanning(double possibleX, double possibleY) {

        boolean canSpawn = true;
        Game game = Globals.getInstance().game;
        Snake snake = game.getSnake();
        ListIterator bodyParts =  snake.getBody().getList().listIterator();

//        if snake.getHead().getPosition()
        if (snake.getHead().intersects(this.getBoundsInParent())) {
            canSpawn = false;
        } else {
            while (bodyParts.hasNext()) {
                GameEntity bodyPart = (GameEntity)bodyParts.next();
                if (bodyPart.intersects(this.getBoundsInParent())) {
                    canSpawn = false;
                    break;
                }
            }
        }
        System.out.println(canSpawn);
        return canSpawn;

    }


    @Override
    public void step() {
        if (isOutOfBounds()) {
            destroy();
        }
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(GameEntity entity) {
        if(entity instanceof SnakeHead){
            System.out.println(getMessage());

            destroy();
        }
    }

    @Override
    public String getMessage() {
        return (getDamage() + " damage");
    }
}
