import org.newdawn.slick.SlickException;

public class Skeleton extends AggressiveMonster {
    private static final double START_HP = 100;
    private static final double START_DAMAGE = 16;
    private static final double START_COOLDOWN = 500;
    
    /**
     * The constructor for the Skeleton class.
     * @param x The x coordinate of the starting position of the skeleton.
     * @param y The y coordinate of the starting position of the skeleton.
     * @param name The name of the monster.
     * @param image_path The path to the image of the skeleton.
     * @throws SlickException
     */
	public Skeleton(double x, double y, String name, String image_path) throws SlickException {
		super(x, y, name, image_path);
		super.setMaxHP(START_HP);
		super.setCurrentHP(START_HP);
		super.setMaxDamage(START_DAMAGE);
		super.setCooldown(START_COOLDOWN);
	}

}
