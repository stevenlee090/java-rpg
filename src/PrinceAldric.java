import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class PrinceAldric extends NPC {
    private static final double START_HP = 1;
    private static final double START_DAMAGE = 0;
    private static final double START_COOLDOWN = 0;
    private static final double DIALOGUE_TIME = 4000;
    
    private static final String NEED_ELIXIR = "Please seek out the Elixir of Life to cure the king.";
    private static final String FINISHED = "The elixir! My father is cured! Thank you!";
    
    private boolean gameFinished = false;
	private double nonFinishedTimer = 0;
	private double finishedTimer = 0;

	/**
	 * The constructor for the PrinceAldric class.
	 * @param x The x coordinate of the prince's location.
	 * @param y The y coordinate of the prince's location.
	 * @param name The name of the prince.
	 * @param image_path The path to the image of the prince.
	 * @throws SlickException
	 */
	public PrinceAldric(double x, double y, String name, String image_path) throws SlickException {
		super(x, y, name, image_path);
		super.setMaxHP(START_HP);
		super.setCurrentHP(START_HP);
		super.setMaxDamage(START_DAMAGE);
		super.setCooldown(START_COOLDOWN);
	}

	@Override
	public void interact(int delta, World world) {
		if (super.inPlayerRange(world.getPlayer(), super.returnInteractRange())
				&& RPG.isInteract() 
				&& world.getPlayer().getInventory(3) == 1) 
		{
			world.getPlayer().setInventory(3, 0);
			gameFinished = true;
		}
		
		if (super.inPlayerRange(world.getPlayer(), super.returnInteractRange())
				&& RPG.isInteract()
				&& gameFinished == false) {
			nonFinishedTimer = DIALOGUE_TIME;
		}
		
		if (super.inPlayerRange(world.getPlayer(), super.returnInteractRange())
				&& RPG.isInteract()
				&& gameFinished == true) {
			finishedTimer = DIALOGUE_TIME;
		}
		
		if (nonFinishedTimer > 0) {
			nonFinishedTimer = nonFinishedTimer - delta;
		}
		
		if (finishedTimer > 0) {
			finishedTimer = finishedTimer - delta;
		}
	}
	
	@Override
	public void renderDialogue(World world, Graphics g) {
		if (nonFinishedTimer > 0) {
			g.drawString(NEED_ELIXIR, (float)super.getX() - g.getFont().getWidth(NEED_ELIXIR)/2, (float)super.getY() - 80);
		}
		if (finishedTimer > 0) {
			g.drawString(FINISHED, (float)super.getX() - g.getFont().getWidth(FINISHED)/2, (float)super.getY() - 80);
		}
		
	}

}
