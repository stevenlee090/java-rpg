import org.newdawn.slick.SlickException;

public class Bandit extends AggressiveMonster {
    private static final double START_HP = 40;
    private static final double START_DAMAGE = 8;
    private static final double START_COOLDOWN = 200;
    
    /**
     * The constructor for the Bandit concrete class.
     * @param x The x coordinate of the bandit's starting position.
     * @param y The y coordinate of the bandit's starting position.
     * @param name The name of the unit.
     * @param image_path The path to the image of bandit.
     * @throws SlickException
     */
	public Bandit(double x, double y, String name, String image_path) throws SlickException {
		super(x, y, name, image_path);
		super.setMaxHP(START_HP);
		super.setCurrentHP(START_HP);
		super.setMaxDamage(START_DAMAGE);
		super.setCooldown(START_COOLDOWN);
	}

}
