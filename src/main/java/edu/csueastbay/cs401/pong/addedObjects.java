package edu.csueastbay.cs401.pong;

import javafx.scene.shape.Rectangle;




import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.Random;

    /**
     * Add random objects horizontally
     *
     * @see Collidable
     * @see Collision
     *
     *
     */

    public class addedObjects extends Rectangle implements Collidable {
        /**
         * This is Moving Object. Added distraction for the players
         *
         */
        public static final double STARTING_SPEED = 5.0;
        private final String id;
        private double speed = STARTING_SPEED;
        private double leftBound;
        private double rightBound;

        /**
         * Constructor
         * Initialize values and set random to the left or to the right
         */

        public addedObjects(String id, double x, double y, double width, double height, double leftBound, double rightBound){
            super(x, y, width, height);
            this.id = id;
            this.leftBound = leftBound;
            this.rightBound = rightBound;
            Random random = new Random();
            Boolean negativeDirection = random.nextBoolean();
            if(negativeDirection) speed = speed*-1;

        }

        /**
         * Override function
         * @return Collision
         */

        @Override
        public Collision getCollision(Shape shape) {
            return new Collision(
                    "Moving Object",
                    this.id,
                    this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                    this.getLayoutBounds().getMinY(),
                    this.getLayoutBounds().getMaxY(),
                    this.getLayoutBounds().getMinX(),
                    this.getLayoutBounds().getMaxX()
            );
        }

        /**
         * Getter for ID
         * @return ID
         */

        @Override
        public String getID() {
            return id;
        }
        /**
         * Getter for Type
         * @return type
         */
        @Override
        public String getType() {
            return "Moving Object";
        }

        /**
         * This will move the Moving Object
         * Move to the left or right
         * Move to the opposite direction when reach the bounds
         */

        void move()
        {
            setX(getX() + speed);
            if (getX() < leftBound)
            {
                setX(leftBound);
                speed = speed * -1;
            }
            double maxRight = rightBound - getWidth();
            if (getX() > maxRight)
            {
                setX(maxRight);
                speed = speed*-1;
            }
        }
        /**
         * Getter for speed
         * @return speed
         */
        public double getSpeed(){return speed;}

}
