import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Garth extends NPC {
    private static final double START_HP = 1;
    private static final double START_DAMAGE = 0;
    private static final double START_COOLDOWN = 0;
    private static final double DIALOGUE_TIME = 4000;
    
    private double timer = 0;
    private int option;
    
    private String dialogue[] = {
		"Find the Amulet of Vitality, across the river to the west.",
		"Find the Sword of Strength - cross the bridge to the east, then head south.",
		"Find the Tome of Agility, in the Land of Shadows.",
		"You have found all the treasure I know of."
    };
    
    /**
     * The constructor for Garth, the villager.
     * @param x The x coordinate of the Garth's position.
     * @param y The y coordinate of the Garth's position.
     * @param name The name of the villager.
     * @param image_path The path to Garth's image.
     * @throws SlickException
     */
	public Garth(double x, double y, String name, String image_path) 
			throws SlickException {
		super(x, y, name, image_path);
		super.setMaxHP(START_HP);
		super.setCurrentHP(START_HP);
		super.setMaxDamage(START_DAMAGE);
		super.setCooldown(START_COOLDOWN);
	}

	@Override
	public void interact(int delta, World world) {
		if (super.inPlayerRange(world.getPlayer(), super.returnInteractRange())
				&& RPG.isInteract()) {
			option = 3;
			for (int i = 0; i < 3; i++) {
				if (world.getPlayer().getInventory(i) == 0) {
					option = i;
					break;
				}
			}
			timer = DIALOGUE_TIME;
		}
		if (timer > 0) {
			timer = timer - delta;
		}
	}

	@Override
	public void renderDialogue(World world, Graphics g) {
		if (timer > 0) {
			g.drawString(dialogue[option], (float)super.getX() - 
					g.getFont().getWidth(dialogue[option])/2, (float)super.getY() - 80);
		}
	}
	

}
