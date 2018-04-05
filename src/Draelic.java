import org.newdawn.slick.SlickException;

public class Draelic extends AggressiveMonster {
    private static final double START_HP = 140;
    private static final double START_DAMAGE = 30;
    private static final double START_COOLDOWN = 400;
    
    /**
     * The constructor for the Draelic concrete class.
     * @param x The x coordinate of the starting position.
     * @param y The y coordinate of the starting position.
     * @param name The name of the monster.
     * @param image_path The path to the image of the monster.
     * @throws SlickException
     */
	public Draelic(double x, double y, String name, String image_path) throws SlickException {
		super(x, y, name, image_path);
		super.setMaxHP(START_HP);
		super.setCurrentHP(START_HP);
		super.setMaxDamage(START_DAMAGE);
		super.setCooldown(START_COOLDOWN);
	}

}
