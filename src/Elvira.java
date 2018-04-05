import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Elvira extends NPC {
	/* Define the variables needed in for this class. */
    private static final double START_HP = 1;
    private static final double START_DAMAGE = 0;
    private static final double START_COOLDOWN = 0;
    private static final double DIALOGUE_TIME = 4000;
    
    private static final String NEED_HEAL = "You're looking much healthier now.";
    private static final String HEALED = "Return to me if you ever need healing.";
	
	private double healedTimer = 0;
	private double nonHealedTimer = 0;
	
	/**
	 * The constructor for the villager, Elvira.
	 * @param x The x coordinate of the starting position.
	 * @param y The y coordinate of the starting position.
	 * @param name The name of the villager.
	 * @param image_path The path to the image of the villager.
	 * @throws SlickException
	 */
	public Elvira(double x, double y, String name, String image_path) throws SlickException {
		super(x, y, name, image_path);
		super.setMaxHP(START_HP);
		super.setCurrentHP(START_HP);
		super.setMaxDamage(START_DAMAGE);
		super.setCooldown(START_COOLDOWN);
	}

	@Override
	public void interact(int delta, World world) {
		/* Check if the player is within the interaction range and needs to be healed. */
		if (super.inPlayerRange(world.getPlayer(), super.returnInteractRange())
				&& RPG.isInteract()
				&& world.getPlayer().getCurrentHP() != world.getPlayer().getMaxHP()) {
			
			world.getPlayer().setCurrentHP(world.getPlayer().getMaxHP());
			healedTimer = DIALOGUE_TIME;
		}
		
		/* Check if the player is within the interaction range and does not need healing. */
		if (super.inPlayerRange(world.getPlayer(), super.returnInteractRange())
				&& RPG.isInteract()
				&& world.getPlayer().getCurrentHP() == world.getPlayer().getMaxHP()
				&& healedTimer <= 0){
			nonHealedTimer = DIALOGUE_TIME;
		}
		
		/* reduce time for the timer if the healed timer is greater than 0 */
		if (healedTimer > 0) {
			healedTimer = healedTimer - delta;
		}
		
		/* reduce time for the timer if the "does not need healing" timer is greater than 0 */
		if (nonHealedTimer > 0) {
			nonHealedTimer = nonHealedTimer - delta;
		}
	}
	
	@Override
	public void render(Graphics g) {
		super.getImg().drawCentered((int) super.getX(), (int) super.getY());
	}
	
	/**
	 * This method renders the corresponding dialogues, depending on the heal state of the player.
	 * @param world The world object.
	 * @param g The graphics object.
	 */
	public void renderDialogue(World world, Graphics g) {
		if (healedTimer > 0) {
			g.drawString(NEED_HEAL, (float)super.getX() - g.getFont().getWidth(NEED_HEAL)/2, (float)super.getY() - 80);
		}
		if (nonHealedTimer > 0) {
			g.drawString(HEALED, (float)super.getX() - g.getFont().getWidth(NEED_HEAL)/2, (float)super.getY() - 80);
		}
	}

	

	
	
	
}
