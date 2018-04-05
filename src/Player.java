/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * 
 * Author: Matt Giuca <mgiuca>
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** The character which the user plays as. */
public class Player extends Unit
{
    private Image panel = null;
    private Image amulet = new Image(RPG.ASSETS_PATH + "/items/amulet.png");
    private Image sword = new Image(RPG.ASSETS_PATH + "/items/sword.png");
    private Image tome = new Image(RPG.ASSETS_PATH + "/items/tome.png");
    private Image elixir = new Image(RPG.ASSETS_PATH + "/items/elixir.png");

    /** The speed of the player in pixels per millisecond. */
    private static final double SPEED = 0.25;			// TODO original value = 0.25, currently changed for debugging purposes
    private static final double START_HP = 100;
    private static final double START_DAMAGE = 26;		// original value = 26
    private static final double START_COOLDOWN = 600;
    private static final double AMULET_HP_UPGRADE = 80;

    /**
     * The player's inventory which is initialized to zeroes.
     */
    private int inventory[] = {0, 0, 0, 0};

    /**
     * This method returns the obtained status for a specific item
     * within the inventory.
     * @param index This is the index of the inventory.
     * 0 = amulet,
     * 1 = sword,
     * 2 = tome,
     * 3 = elixir.
     * @return The status of whether the item has been obtained.
     * 1 = obtained,
     * 0 = not obtained.
     */
    public int getInventory(int index) {
		return inventory[index];
	}

    /** This method configures the inventory of the player
     * @param index The index to modify.
     * @param value The value to assign to that index.
     * If value = 1, then true (player has that slot).
     * If value = 0, then false (player does not have that slot).
     */
	public void setInventory(int index, int value) {
		this.inventory[index] = value;
	}

	/**
	 * This method is responsible for updating the player's
	 * cooldown time, and teleporting the player back to
	 * original starting location if HP is reduced to 0;
	 */
	public void update(int delta, World world) {
    	// Calculate cool down time
    	if (RPG.isAttack() && super.getTimeToNextAttack() <= 0) {
    		super.setTimeToNextAttack(super.getCooldown());
    		super.setCanAttack(false);
    	}
    	if (super.isCanAttack() == false) {
    		super.setTimeToNextAttack(super.getTimeToNextAttack() - delta);
    	}
    	if (super.getTimeToNextAttack() <= 0) {
    		super.setCanAttack(true);
    	}

    	// Teleport player if the HP goes to 0 or below
    	if (super.getCurrentHP() <= 0) {
    		super.setX(world.getPlayerStartX());
    		super.setY(world.getPlayerStartY());
    		/* check if the player has the amulet, if so set the current
    		 * HP to a higher value */
    		if (inventory[0] == 1) {
    			super.setCurrentHP(START_HP + AMULET_HP_UPGRADE);
    		} else {
    			super.setCurrentHP(START_HP);
    		}
    	}
    }

    /** Creates a new Player.
     * @param x 			The Player's starting x location in pixels.
     * @param y 			The Player's starting y location in pixels.
     * @param name 			The name of the player.
     * @param image_path 	Path of player's image file.
     * @param panel_path	Path of status panel file.
     */
    public Player(double x, double y, String name, String image_path, String panel_path) throws SlickException {
		super(x, y, name, image_path);
		super.setImageFlip();
		super.setFace_left(false);
		super.setWidth(super.getImg().getWidth()/2);
		super.setHeight(super.getImg().getHeight()/2);
		panel = new Image(panel_path);

		super.setMaxHP(START_HP);
		super.setCurrentHP(START_HP);
		super.setMaxDamage(START_DAMAGE);
		super.setCooldown(START_COOLDOWN);
	}


    /** Move the player in a given direction.
     * Prevents the player from moving outside the map space, and also updates
     * the direction the player is facing.
     * @param world The world the player is on (to check blocking).
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void move(World world, double dir_x, double dir_y, double delta)
    {
        // Update player facing based on X direction
        if (dir_x > 0)
            super.setFace_left(false);
        else if (dir_x < 0)
            super.setFace_left(true);

        // Move the player by dir_x, dir_y, as a multiple of delta * speed
        double new_x = super.getX() + dir_x * delta * SPEED;
        double new_y = super.getY() + dir_y * delta * SPEED;

//	    super.setX(new_x);
//	    super.setY(new_y); // TODO restore terrain blocking after debugging is finished

        /* terrain blocking */

        // Move in x first
        double x_sign = Math.signum(dir_x);
        if(!world.terrainBlocks(new_x + x_sign * super.getWidth() / 2, super.getY() + super.getHeight() / 2)
                && !world.terrainBlocks(new_x + x_sign * super.getWidth() / 2, super.getY() - super.getHeight() / 2)) {
            super.setX(new_x);
        }

        // Then move in y
        double y_sign = Math.signum(dir_y);
        if(!world.terrainBlocks(super.getX() + super.getWidth() / 2, new_y + y_sign * super.getHeight() / 2)
                && !world.terrainBlocks(super.getX() - super.getWidth() / 2, new_y + y_sign * super.getHeight() / 2)){
            super.setY(new_y);
        }


    }

    /** Draw the player to the screen at the correct place.
     * @param g The current Graphics context.
     * @param cam_x Camera x position in pixels.
     * @param cam_y Camera y position in pixels.
     */
    public void render(Graphics g)
    {
        Image which_img;
        which_img = super.isFace_left() ? super.getImg_flipped() : super.getImg();
        which_img.drawCentered((int) super.getX(), (int) super.getY());
    }

    /** Renders the player's status panel.
     * @param g The current Slick graphics context.
     */
    public void renderPanel(Graphics g)
    {
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp

        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle
        int inv_x, inv_y;           // Coordinates to draw inventory item

        float health_percent;       // Player's health, as a percentage

        // Panel background image
        panel.draw(0, RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT);

        // Display the player's health
		text_x = 15;
		text_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 25;
        g.setColor(LABEL);
        g.drawString("Health:", text_x, text_y);
        text = (int)super.getCurrentHP() + "/" + (int)super.getMaxHP();

        bar_x = 90;
        bar_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 20;
        bar_width = 90;
        bar_height = 30;
        health_percent = (float) super.getPercentHP();
        hp_bar_width = (int) (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's damage and cooldown
        text_x = 200;
        g.setColor(LABEL);
        g.drawString("Damage:", text_x, text_y);
        text_x += 80;
        text = Integer.toString((int) super.getMaxDamage());
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
        text_x += 40;
        g.setColor(LABEL);
        g.drawString("Rate:", text_x, text_y);
        text_x += 55;
        text = Integer.toString((int) super.getCooldown());
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's inventory
        g.setColor(LABEL);
        g.drawString("Items:", 420, text_y);
        bar_x = 490;
        bar_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 10;
        bar_width = 288;
        bar_height = bar_height + 20;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);

        inv_x = 490;
        inv_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT
            + ((RPG.PANEL_HEIGHT - 72) / 2);

        for (int i = 0; i < 4 ; i++)
        {
            // Render the item to (inv_x, inv_y)
        	if (inventory[i] == 1) {
        		if (i == 0) {
        			g.drawImage(amulet, inv_x, inv_y);
        		} else if (i == 1) {
        			g.drawImage(sword, inv_x, inv_y);
        		} else if (i == 2) {
        			g.drawImage(tome, inv_x, inv_y);
        		} else if (i == 3) {
        			g.drawImage(elixir, inv_x, inv_y);
        		}
        	}
            inv_x += 72;
        }
    }

}
