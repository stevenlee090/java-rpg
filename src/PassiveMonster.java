import org.newdawn.slick.SlickException;

public abstract class PassiveMonster extends Monster {
	/* Define the variables needed for the passive monster class */
	private final static double SPEED = 0.2;
	private final static double ESCAPE_TIME = 5000;
	private final static double WANDER_LIMIT = 3000;
	private final static int ATTACK_RANGE = 50;
	private double timeSinceAttack = ESCAPE_TIME;
	private double wanderTime = 0;
	
	private int currentDirection = randomWithRange(UP, NO_DIRECTION);
	private final static int UP = 1;
	private final static int DOWN = 2;
	private final static int LEFT = 3;
	private final static int RIGHT = 4;
	private final static int UP_RIGHT = 5;
	private final static int DOWN_RIGHT = 6;
	private final static int DOWN_LEFT = 7;
	private final static int UP_LEFT = 8;
	private final static int NO_DIRECTION = 9;
	
	/**
	 * This is the constructor for the passive monster class.
	 * @param x The x coordinate of the starting position.
	 * @param y The y coordinate of the starting position.
	 * @param name The name of the passive monster.
	 * @param image_path The path to the image of the passive monster.
	 * @throws SlickException
	 */
	public PassiveMonster(double x, double y, String name, String image_path) 
			throws SlickException {
		super(x, y, name, image_path);
		super.setWidth(super.getImg().getWidth()/2);
		super.setHeight(super.getImg().getHeight()/2);
	}

	/**
	 * This method is responsible for updating the passive monster, which
	 * mainly involves the two main types of movement behavior.
	 */
	public void update(int delta, World world) {
		/* run away from player for 5 seconds if it has been attacked */
		if (timeSinceAttack < 5000) {
			timeSinceAttack = timeSinceAttack + delta;
			
			double distX, distY, distTotal, dX, dY;
			/* Let distX and distY be the x and y distance from 
			 * the monster to the player, in pixels.*/
			distX = super.getX() - world.getPlayer().getX();
			distY = super.getY() - world.getPlayer().getY();
			
			/* Calculate the distance to move according to algorithm 1 */
			distTotal = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
			dX = distX / distTotal * SPEED * delta;
			dY = distY / distTotal * SPEED * delta;
			
			/* set the new x and y */
			setPosition(super.getX() + dX, super.getY() + dY, world);
			
		} else {	/* wander around the map as specified */
			
			if (wanderTime < WANDER_LIMIT) {
				wanderTime = wanderTime + delta; 
			} else if (wanderTime >= WANDER_LIMIT) {
				wanderTime = 0;
				wanderTime = wanderTime + delta; 
				currentDirection  = randomWithRange(UP, NO_DIRECTION);
			}
			wanderMovement(delta, world);
			
		}
		
		/* check if the passive monster is under attack */
		if (super.inPlayerRange(world.getPlayer(), ATTACK_RANGE) && RPG.isAttack()) {
			underAttack();
		}
	}
	
	/** This method initialize the timeSinceAttack attribute to 0. */
	public void underAttack() {
		timeSinceAttack = 0;
	}
	
	/**
	 * This method takes care of how the passive monster wanders around the world
	 * @param delta The time passed since the previous frame.
	 * @param world The world object.
	 */
	public void wanderMovement(int delta, World world) {
		double new_x = super.getX();
		double new_y = super.getY();
		
		if (currentDirection == UP) {
			new_x = super.getX();
			new_y = super.getY() - SPEED * delta;
		} else if (currentDirection == DOWN) {
			new_x = super.getX();
			new_y = super.getY() + SPEED * delta;
		} else if (currentDirection == LEFT) {
			new_x = super.getX() - SPEED * delta;
			new_y = super.getY();
		} else if (currentDirection == RIGHT) {
			new_x = super.getX() + SPEED * delta;
			new_y = super.getY();
		} else if (currentDirection == UP_RIGHT) {
			new_x = super.getX() + SPEED * Math.cos(Math.PI/4) * delta;
			new_y = super.getY() - SPEED * Math.cos(Math.PI/4) * delta;
		} else if (currentDirection == DOWN_RIGHT) {
			new_x = super.getX() + SPEED * Math.cos(Math.PI/4) * delta;
			new_y = super.getY() + SPEED * Math.cos(Math.PI/4) * delta;
		} else if (currentDirection == DOWN_LEFT) {
			new_x = super.getX() - SPEED * Math.cos(Math.PI/4) * delta;
			new_y = super.getY() + SPEED * Math.cos(Math.PI/4) * delta;
		} else if (currentDirection == UP_LEFT) {
			new_x = super.getX() - SPEED * Math.cos(Math.PI/4) * delta;
			new_y = super.getY() - SPEED * Math.cos(Math.PI/4) * delta;
		} else if (currentDirection == NO_DIRECTION) {
			// do nothing
		}
		
		setPosition(new_x, new_y, world);

	}
	
	/** randomly generate an integer, inclusive of the range,
	 * that is, generating a number in the domain [min, max]. */
	
	/**
	 * This method randomly generates an integer, inclusive of
	 * the minimum and maximum.
	 * @param min The minimum integer.
	 * @param max The maximum integer.
	 * @return The randomly generated integer.
	 */
	private int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int)(Math.random() * range) + min;
	}
	
	/**
	 * This method sets the new position of the passive monster
	 * and checks for any blocked terrain before assigning the value.
	 * @param new_x The new x coordinate of the passive monster.
	 * @param new_y The new y coordinate of the passive monster.
	 * @param world The world object.
	 */
	private void setPosition(double new_x, double new_y, World world) {
		/* First, move in x */
        double x_sign = Math.signum(new_x - super.getX());
        if(!world.terrainBlocks(new_x + x_sign * super.getWidth() / 2, super.getY() + super.getHeight() / 2) 
                && !world.terrainBlocks(new_x + x_sign * super.getWidth() / 2, super.getY() - super.getHeight() / 2)) {
            super.setX(new_x);
        }
        
        /* Then move in y */
        double y_sign = Math.signum(new_y - super.getY());
        if(!world.terrainBlocks(super.getX() + super.getWidth() / 2, new_y + y_sign * super.getHeight() / 2) 
                && !world.terrainBlocks(super.getX() - super.getWidth() / 2, new_y + y_sign * super.getHeight() / 2)){
            super.setY(new_y);
        }
	}
	
}
