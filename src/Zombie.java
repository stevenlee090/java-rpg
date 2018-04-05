import org.newdawn.slick.SlickException;


public class Zombie extends AggressiveMonster {
    private static final double START_HP = 60;
    private static final double START_DAMAGE = 10;
    private static final double START_COOLDOWN = 800;
    
    /**
     * Constructor for the Zombie class.
     * @param x The x coordinate of the zombie's starting position.
     * @param y The y coordinate of the zombie's starting position.
     * @param name The name of the zombie.
     * @param image_path The path to the image of zombie.
     * @throws SlickException
     */
	public Zombie(double x, double y, String name, String image_path) throws SlickException {
		super(x, y, name, image_path);
		super.setMaxHP(START_HP);
		super.setCurrentHP(START_HP);
		super.setMaxDamage(START_DAMAGE);
		super.setCooldown(START_COOLDOWN);
	}

}
