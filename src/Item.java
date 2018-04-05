import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * This is the Items Class, used to handle specific items.
 */

/**
 * @author Chi-Jen Lee
 */
public abstract class Item extends Entity {
	private final static int PICK_UP_RANGE = 50;
	private boolean obtained = false;

	/**
	 * This method returns whether an items has been obtained by the player.
	 * @return True, if the item has been obtained.
	 */
	public boolean isObtained() {
		return obtained;
	}

	/**
	 * This method sets the state of whether an item has been obtained by the player.
	 * @param obtained
	 */
	public void setObtained(boolean obtained) {
		this.obtained = obtained;
	}

	/**
	 * This is the constructor for the item class.
	 * @param x The x coordinate of the item.
	 * @param y The y coordinate of the item.
	 * @param name The name of the item.
	 * @param image_path The path to the image of the item.
	 * @throws SlickException
	 */
	public Item(double x, double y, String name, String image_path) 
			throws SlickException {
		super(x, y, name, image_path);
	}
	
	
	public void render(Graphics g) {
		super.getImg().drawCentered((int) super.getX(), (int) super.getY());
	}
	
	/**
	 * This method checks if the player is within the pick up range of the item.
	 * If so, it updates the obtained attribute of the item to true.
	 * @param player This is the specified player used in the method.
	 */
	public void update(Player player) {
		if (super.inPlayerRange(player, PICK_UP_RANGE)) {
			obtained = true;
		}
	}
}
