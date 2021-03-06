import org.newdawn.slick.SlickException;

public class TomeOfAgility extends Item {
	/**
	 * This is the constructor for the TomeOfAgility class.
	 * @param x The x coordinate of the item.
	 * @param y The y coordinate of the item.
	 * @param name The name of the item.
	 * @param image_path The path to the image of the tome.
	 * @throws SlickException
	 */
	public TomeOfAgility(double x, double y, String name, String image_path) 
			throws SlickException {
		super(x, y, name, image_path);
	}

}
