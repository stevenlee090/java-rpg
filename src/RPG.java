/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Matt Giuca <mgiuca>
 */

import java.io.FileNotFoundException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/** Main class for the Role-Playing Game engine.
 * Handles initialisation, input and rendering.
 */

public class RPG extends BasicGame
{
	/** Height of the status panel */
	public static final int PANEL_HEIGHT = 70;
    /** Location of the "assets" directory. */
    public static final String ASSETS_PATH = "assets";
    /** Screen width, in pixels. */
    public static final int SCREEN_WIDTH = 800;
    /** Screen height, in pixels. */
    public static final int SCREEN_HEIGHT = 600;
    /** Font Size */
    public static final int FONT_SIZE = 15;
    /** The world of our game */
    private World world;
    /** Field for the custom font */
    private Font font;

    /** Attack Status: if true, then player is attempting to attack */
    private static boolean attack = false;
    /** Interact Status: if true, then player is attempting to interact with NPC */
    private static boolean interact = false;


    /** Create a new RPG object. */
    public RPG()
    {
        super("Shadow Quest");
    }

    /** Initialise the game state.
     * @param gc The Slick game container object.
     */
    @Override
    public void init(GameContainer gc)
    throws SlickException
    {
        try {
			world = new World();					// TODO Need to take care of this try and catch issue
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        font = FontLoader.loadFont(ASSETS_PATH + "/DejaVuSans-Bold.ttf", 15);
    }

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
    throws SlickException
    {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();

        // Update the player's movement direction based on keyboard presses.
        int dir_x = 0;
        int dir_y = 0;
        if (input.isKeyDown(Input.KEY_DOWN))
            dir_y += 1;
        if (input.isKeyDown(Input.KEY_UP))
            dir_y -= 1;
        if (input.isKeyDown(Input.KEY_LEFT))
            dir_x -= 1;
        if (input.isKeyDown(Input.KEY_RIGHT))
            dir_x += 1;

        // Press ESC key to close the game
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
        	System.exit(0);
        }

        // Press A to attack
        if (input.isKeyDown(Input.KEY_A)) {
        	attack = true;
        } else {
        	attack = false;
        }

        // Press T to interact
        if (input.isKeyDown(Input.KEY_T)) {
        	interact = true;
        } else {
        	interact = false;
        }

        // Let World.update decide what to do with this data.
        world.update(dir_x, dir_y, delta);
    }

    /**
     * This method returns a boolean expression on
     * whether the player is attempting to attack a
     * monster.
     * @return True if the user is pressing "A".
     */
    public static boolean isAttack() {
    	return attack;
    }

    /**
     * This method returns a boolean expression on
     * whether the player is attempting to interact with
     * a villager.
     * @return True if the user is pressing "T".
     */
    public static boolean isInteract() {
    	return interact;
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g)
    throws SlickException
    {
        // Set the font to the custom font.
        g.setFont(font);
        // Let World.render handle the rendering.
        world.render(g);
    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
    throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new RPG());
        // setShowFPS(true), to show frames-per-second.
        app.setShowFPS(true);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.start();
    }
}
