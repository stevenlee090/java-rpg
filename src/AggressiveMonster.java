import org.newdawn.slick.SlickException;

public abstract class AggressiveMonster extends Monster {
	private final static int CHASE_RANGE = 150;
	private final static int ATTACK_RANGE = 50;
	private final static double SPEED = 0.25;
	
	/**
	 * Constructor for aggressive monster class
	 * @param x The x coordinate of the monster's starting position.
	 * @param y The y coordinate of the monster's starting position.
	 * @param name The name of the monster.
	 * @param image_path The path to the monster's image.
	 * @throws SlickException
	 */
	public AggressiveMonster(double x, double y, String name, String image_path) throws SlickException {
		super(x, y, name, image_path);
	}
	
	/** 
	 * This method updates the Aggressive Monsters so
	 * they will chase and attack the player according to
	 * the specified algorithm.
	 */
	@Override
	public void update(int delta, World world) {
		/* commence attack if the player is within the attack range */
		if (super.inPlayerRange(world.getPlayer(), ATTACK_RANGE)) {
			super.setTimeToNextAttack(getTimeToNextAttack() - delta );
			if (super.getTimeToNextAttack() <= 0) {
				world.getPlayer().setCurrentHP(world.getPlayer().getCurrentHP() - super.generateDamage());
				super.setTimeToNextAttack( super.getCooldown() );
			}
		/* commence chasing sequence if the player is within chasing range */
		} else if (super.inPlayerRange(world.getPlayer(), CHASE_RANGE)) {
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
			double new_x = super.getX() - dX;
			double new_y = super.getY() - dY;
			
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

}
