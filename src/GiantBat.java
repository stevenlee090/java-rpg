import org.newdawn.slick.SlickException;

public class GiantBat extends PassiveMonster {
	/* variables used for initializing the Giant Bat */
    private static final double START_HP = 40;
    private static final double START_DAMAGE = 0;
    private static final double START_COOLDOWN = 0;
    
    /**
     * This is the constructor for the Giant Bat monster.
     * @param x The x coordinate of the starting position of the monster.
     * @param y The y coordinate of the starting position of the monster.
     * @param name The name of the monster.
     * @param image_path The path to the image of the monster.
     * @throws SlickException
     */
	public GiantBat(double x, double y, String name, String image_path) throws SlickException {
		super(x, y, name, image_path);
		super.setMaxHP(START_HP);
		super.setCurrentHP(START_HP);
		super.setMaxDamage(START_DAMAGE);
		super.setCooldown(START_COOLDOWN);
	}
}