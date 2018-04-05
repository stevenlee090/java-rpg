import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class Monster extends Unit {

	/**
	 * Constructor for the monster class.
	 * @param x The x coordinate of the starting position of the monster.
	 * @param y The y coordinate of the starting position of the monster.
	 * @param name The name of the monster.
	 * @param image_path The path to the monster's image.
	 * @throws SlickException
	 */
	public Monster(double x, double y, String name, String image_path) throws SlickException {
		super(x, y, name, image_path);
	}
	
	/**
	 * Renders the monster at its current x and y coordinates.
	 */
	public void render(Graphics g) {
		super.getImg().drawCentered((int) super.getX(), (int) super.getY());
	}

}
