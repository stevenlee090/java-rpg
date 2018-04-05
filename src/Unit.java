import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * This is the Unit Class, the main parent class for player,
 * villagers, and monsters.
 */

/**
 * @author Chi-Jen Lee
 */
public abstract class Unit extends Entity {
	private static final int ATTACK_RANGE = 50;
	
	private double maxHP;
	private double maxDamage;
	private double cooldown;
	private double timeToNextAttack = 0;
	private double width, height;
	private double currentHP;
	
	private boolean face_left;	
	private boolean canAttack = true;
	
	private Image img_flipped;
	
	/* The methods below are the getters and setters for
	 * the attributes defined above. */
	public boolean isCanAttack() {
		return canAttack;
	}

	public void setCanAttack(boolean canAttack) {
		this.canAttack = canAttack;
	}
	
	public double getTimeToNextAttack() {
		return timeToNextAttack;
	}

	public void setTimeToNextAttack(double timeToNextAttack) {
		this.timeToNextAttack = timeToNextAttack;
	}
	
	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setImageFlip() {
		this.img_flipped = super.getImg().getFlippedCopy(true, false);
	}

	public double getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(double maxHP) {
		this.maxHP = maxHP;
	}

	public double getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(double maxDamage) {
		this.maxDamage = maxDamage;
	}

	public double getCooldown() {
		return cooldown;
	}

	public void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}

	public double getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(double currentHP) {
		this.currentHP = currentHP;
	}

	public Image getImg_flipped() {
		return img_flipped;
	}
	
	public boolean isFace_left() {
		return face_left;
	}
	
	public void setFace_left(boolean face_left) {
		this.face_left = face_left;
	}
	
	/**
	 * Constructor for the Unit class
	 * @param x The x coordinate of the starting position.
	 * @param y The y coordinate of the starting position.
	 * @param name The name of the unit.
	 * @param image_path The path to the image of the unit.
	 * @throws SlickException
	 */
	public Unit(double x, double y, String name, String image_path) 
			throws SlickException {
		super(x, y, name, image_path);
	}

	/**
	 * This method returns the current HP in percentage.
	 * @return Current HP in percentage.
	 */
	public double getPercentHP() {
		return currentHP / maxHP;
	}
	
	/**
	 * This method randomly generates damage between 0 to maxDamage value.
	 * @return The randomly generated value.
	 */
	public double generateDamage() {
		return Math.random() * getMaxDamage();
	}
	
	/**
	 * This method returns the width of the image.
	 * @return The width of the image.
	 */
	public int getImageWidth() {
		return super.getImg().getWidth();
	}

	/**
	 * This method returns the height of the image.
	 * @return The height of the image.
	 */
	public int getImageHeight() {
		return super.getImg().getHeight();
	}	

	
	/** This method updates the monsters in combat
	 * @param unit This is the unit attacking the monsters. That is, the player.
	 */
	public void updateMonster(int delta, World world, Unit unit) {
		if ((super.inPlayerRange(world.getPlayer(), ATTACK_RANGE) == true && RPG.isAttack()) && unit.canAttack) {
			setCurrentHP(currentHP - unit.generateDamage());
		}
	}
	
	/**
	 * This method is responsible for rendering the health bar.
	 * @param g The graphics object.
	 */
	public void renderHealthBar(Graphics g) {
		// Panel colours
		Color VALUE = new Color(1.0f, 1.0f, 1.0f); // White
		Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f); // Black, transp
		Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f); // Red, transp

		// Variables for layout
		String text; // Text to display
		int text_x, text_y; // Coordinates to draw text
		int bar_x, bar_y; // Coordinates to draw rectangles
		int bar_width, bar_height; // Size of rectangle to draw
		int hp_bar_width; // Size of red (HP) rectangle

		float health_percent; // Player's health, as a percentage

		// Display the unit's name and health bar

		text = super.getName();

		bar_width = g.getFont().getWidth(text) + 10;
		bar_height = 18;

		bar_x = (int) super.getX() - bar_width / 2;
		bar_y = (int) super.getY() - 45;

		text_x = (int) super.getX() - bar_width / 2;
		text_y = (int) super.getY() - 45;

		health_percent = (float) getPercentHP();
		hp_bar_width = (int) (bar_width * health_percent);
		text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;

		g.setColor(BAR_BG);
		g.fillRect(bar_x, bar_y, bar_width, bar_height);
		g.setColor(BAR);
		g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
		g.setColor(VALUE);
		g.drawString(text, text_x, text_y);
	}
	
	/**
	 * This is the update class responsible for updating the unit.
	 * @param delta Time passed since the previous frame in milliseconds.
	 * @param world The world object.
	 */
	public abstract void update(int delta, World world);

}
