import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class NPC extends Unit {
	/**
	 * The range of interaction in pixels.
	 */
	private final int INTERACT_RANGE = 50;
	
	/**
	 * This method returns the interact range of NPCs.
	 * @return The interaction range.
	 */
	public int returnInteractRange() {
		return INTERACT_RANGE;
	}

	/**
	 * The constructor for NPC class.
	 * @param x The x coordinate of the NPC.
	 * @param y The y coordinate of the NPC.
	 * @param name The name of the NPC.
	 * @param image_path The path to the image of the NPC.
	 * @throws SlickException
	 */
	public NPC(double x, double y, String name, String image_path) throws SlickException {
		super(x, y, name, image_path);
	}
	
	@Override
	public void render(Graphics g) {
		super.getImg().drawCentered((int) super.getX(), (int) super.getY());
	}
	
	
	public void update(int delta, World world) {
		
	}
	
	/**
	 * This method handles the interaction between the NPC and the player.
	 * @param delta Time passed since the previous frame in milliseconds.
	 * @param world The world object.
	 */
	public abstract void interact (int delta, World world);
	
	/**
	 * This method is responsible for handling the dialogue of the NPC.
	 * @param world The world object.
	 * @param g The graphics object.
	 */
	public abstract void renderDialogue(World world, Graphics g);

}
