
/**
 * This is the Entity Class, which serves as the parent class for various
 * different types of subclasses, such as NPCs and player.
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Chi-Jen Lee
 *
 */
public abstract class Entity {
	/** The x coordinate of the entity. */
	private double x;
	
	/** The y coordinate of the entity. */
	private double y;
	
	/** The name of the entity. */
	private String name;
	
	/** The image of the entity. */
	private Image img;

	/**
	 * Constructor for the abstract entity class
	 * @param x The x coordinate of the starting position of the entity.
	 * @param y The y coordinate of the starting position of the entity.
	 * @param name The name of the entity.
	 * @param image_path The path to the image of the entity.
	 * @throws SlickException
	 */
	public Entity(double x, double y, String name, String image_path) throws SlickException {
		this.x = x;
		this.y = y;
		this.name = name;
		img = new Image(image_path);
	}

	/**
	 * This method returns the entity's x coordinate.
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * This method sets the entity's x coordinate.
	 * @param x The x coordinate to be assigned to the entity.
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * This method returns the entity's y coordinate.
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * This method sets the entity's x coordinate.
	 * @param y The y coordinate to be assigned to the entity.
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * This method returns the entity's name.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method sets the name of the entity.
	 * @param name The name to be assigned to the entity.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method returns the image of the entity.
	 * @return
	 */
	public Image getImg() {
		return img;
	}

	/**
	 * This method sets the image of the entity.
	 * @param img The image to be assigned to the entity.
	 */
	public void setImg(Image img) {
		this.img = img;
	}
	
	/** This method is responsible for the rendering of the entity. */
	public abstract void render(Graphics g);

	/**
	 * This method checks if an entity is within a specific range to the player.
	 * @param player The player object which is considered.
	 * @param range The specific range to the player.
	 * @return Returns true if the player is within the specific range.
	 */
	public boolean inPlayerRange(Player player, int range) {
		if (Math.sqrt(Math.pow((getX() - player.getX()), 2) + Math.pow((getY() - player.getY()), 2)) <= range) {
			return true;
		} else {
			return false;
		}
	}

}
