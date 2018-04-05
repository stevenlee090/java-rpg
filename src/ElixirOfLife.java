import org.newdawn.slick.SlickException;

public class ElixirOfLife extends Item {
	/**
	 * This is the constructor for the Elixir of Life item.
	 * @param x The x coordinate of the item's location.
	 * @param y The y coordinate of the item's location.
	 * @param name The name of the item.
	 * @param image_path The path to the item's image.
	 * @throws SlickException
	 */
	public ElixirOfLife(double x, double y, String name, String image_path) 
			throws SlickException {
		super(x, y, name, image_path);
	}
}
