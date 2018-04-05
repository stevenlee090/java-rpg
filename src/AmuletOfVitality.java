import org.newdawn.slick.SlickException;

public class AmuletOfVitality extends Item {
	/**
	 * The constructor for the Amulet of Vitality item.
	 * @param x The x coordinate of the item.
	 * @param y	The y coordinate of the item.
	 * @param name The name of the item.
	 * @param image_path The path to the image of the item.
	 * @throws SlickException
	 */
	public AmuletOfVitality(double x, double y, String name, String image_path) 
			throws SlickException {
		super(x, y, name, image_path);
	}
}
