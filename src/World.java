/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Sample Solution
 * Author: Matt Giuca <mgiuca>
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
	// Single units' starting position
    private static final int PLAYER_START_X = 756, PLAYER_START_Y = 684;
	private static final int ELVIRA_X = 738, ELVIRA_Y = 549;
	private static final int PRINCE_X = 467, PRINCE_Y = 679;
	private static final int GARTH_X = 756, GARTH_Y = 870;

	// Items' starting position
	private static final int AMULET_X = 965, AMULET_Y = 3563;
	private static final int SWORD_X = 546, SWORD_Y = 6707;
	private static final int TOME_X = 4791, TOME_Y = 1253;
	private static final int ELIXIR_X = 1976, ELIXIR_Y = 402;

	// Items' names
	private final static String AMULET_NAME = "Amulet of Vitality";
	private final static String SWORD_NAME = "Sword of Strength";
	private final static String TOME_NAME = "Tomb of Agility";
	private final static String ELIXIR_NAME = "Elixir Of Life";

    private Player player = null;
    private TiledMap map = null;
    private Camera camera = null;
    private Elvira elvira = null;
    private PrinceAldric princeAldric = null;
    private Garth garth = null;

    private ArrayList<Bandit> bandits = new ArrayList<Bandit>();
    private ArrayList<Skeleton> skeletons = new ArrayList<Skeleton>();
    private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
    private ArrayList<GiantBat> giantBats = new ArrayList<GiantBat>();
    private ArrayList<Draelic> draelic = new ArrayList<Draelic>();
    private ArrayList<Item> items = new ArrayList<Item>();

    /**
     * @return The x coordinate of the player's starting position.
     */
    public int getPlayerStartX() {
    	return PLAYER_START_X;
    }

    /**
     *
     * @return The y coordinate of the player's starting position.
     */
    public int getPlayerStartY() {
    	return PLAYER_START_Y;
    }

    /** Return the player object */
    public Player getPlayer() {
    	return player;
    }

    /** Map width, in pixels. */
    private int getMapWidth()
    {
        return map.getWidth() * getTileWidth();
    }

    /** Map height, in pixels. */
    private int getMapHeight()
    {
        return map.getHeight() * getTileHeight();
    }

    /** Tile width, in pixels. */
    private int getTileWidth()
    {
        return map.getTileWidth();
    }

    /** Tile height, in pixels. */
    private int getTileHeight()
    {
        return map.getTileHeight();
    }

    /** Create a new World object.
     * @throws FileNotFoundException */
    public World()
    throws SlickException, FileNotFoundException
    {
        map = new TiledMap(RPG.ASSETS_PATH + "/map.tmx", RPG.ASSETS_PATH);
        player = new Player(PLAYER_START_X, PLAYER_START_Y, "Player",RPG.ASSETS_PATH + "/units/player.png",
        		RPG.ASSETS_PATH + "/panel.png");
        camera = new Camera(player, RPG.SCREEN_WIDTH, RPG.SCREEN_HEIGHT);

        elvira = new Elvira(ELVIRA_X, ELVIRA_Y, "Elvira",
        		RPG.ASSETS_PATH + "/units/shaman.png");
        garth = new Garth(GARTH_X, GARTH_Y, "Garth",
        		RPG.ASSETS_PATH + "/units/peasant.png");
        princeAldric = new PrinceAldric(PRINCE_X, PRINCE_Y, "Prince Aldric",
        		RPG.ASSETS_PATH + "/units/prince.png");

        loadMonster();
        loadItem();

    }

    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void update(int dir_x, int dir_y, int delta)
    throws SlickException
    {
        player.move(this, dir_x, dir_y, delta);
        updateMonster(delta); // TODO remove comment after development, update monster first
        updateItem();
        player.update(delta, this);
        camera.update();

        // update game based on villager interaction
        elvira.interact(delta, this);
        princeAldric.interact(delta, this);
        garth.interact(delta, this);

        removeMonster();
        removeItem();

    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
        // Render the relevant section of the map
        int x = -(camera.getMinX() % getTileWidth());
        int y = -(camera.getMinY() % getTileHeight());
        int sx = camera.getMinX() / getTileWidth();
        int sy = camera.getMinY()/ getTileHeight();
        int w = (camera.getMaxX() / getTileWidth()) - (camera.getMinX() / getTileWidth()) + 1;
        int h = (camera.getMaxY() / getTileHeight()) - (camera.getMinY() / getTileHeight()) + 1;
        map.render(x, y, sx, sy, w, h);

        // Translate the Graphics object
        g.translate(-camera.getMinX(), -camera.getMinY());

        // Render the villagers
        renderSingleUnit(g);

        // Render the player
        player.render(g);

        // Render the monsters
        renderMonster(g);

        // Render the items
        renderItem(g);

        // Render the dialogue
        elvira.renderDialogue(this, g);
        princeAldric.renderDialogue(this, g);
        garth.renderDialogue(this, g);

        g.translate(camera.getMinX(), camera.getMinY());
        // Render the status panel
        player.renderPanel(g);

    }

    /** Determines whether a particular map coordinate blocks movement.
     * @param x Map x coordinate (in pixels).
     * @param y Map y coordinate (in pixels).
     * @return true if the coordinate blocks movement.
     */
    public boolean terrainBlocks(double x, double y)
    {
        // Check we are within the bounds of the map
        if (x < 0 || y < 0 || x > getMapWidth() || y > getMapHeight()) {
            return true;
        }

        // Check the tile properties
        int tile_x = (int) x / getTileWidth();
        int tile_y = (int) y / getTileHeight();
        int tileid = map.getTileId(tile_x, tile_y, 0);
        String block = map.getTileProperty(tileid, "block", "0");
        return !block.equals("0");
    }

    /**
     * This method is responsible for loading the monsters' starting position
     * coordinates from the provided data file.
     * @throws FileNotFoundException
     * @throws SlickException
     */
	public void loadMonster()
			throws FileNotFoundException, SlickException {

		Scanner read = new Scanner (new File(RPG.ASSETS_PATH + "/units.dat"));

		/* Define the delimiters for the data file used. */
		read.useDelimiter(",|\n");
		String name, x_string, y_string;
		int x = 0, y = 0;

		String zombieName = new String("Zombie");
		String banditName = new String("Bandit");
		String skeletonName = new String("Skeleton");
		String giantBatName = new String("GiantBat");
		String draelicName = new String("Draelic");

		/* Temporary storage for objects. */
		Zombie tempZombie;
		Bandit tempBandit;
		Skeleton tempSkeleton;
		GiantBat tempGiantBat;
		Draelic tempDraelic;

		while (read.hasNext()) {
			name = read.next();
			x_string = read.next();
			y_string = read.next();

			try {
				x = Integer.valueOf(x_string.trim());
			} catch (NumberFormatException e) {
				System.out.println("x not a number");
			}

			try {
				y = Integer.parseInt(y_string.trim());
			} catch (NumberFormatException e) {
				System.out.println("y not a number");
			}

			/* Check the name and create new objects accordingly,
			 * then add them into the pre-defined array list. */
			if (name.equals(zombieName)) {
				tempZombie = new Zombie(x, y, name,
						RPG.ASSETS_PATH + "/units/zombie.png");
				zombies.add(tempZombie);
			} else if (name.equals(banditName)) {
				tempBandit = new Bandit(x, y, name,
						RPG.ASSETS_PATH + "/units/bandit.png");
				bandits.add(tempBandit);
			} else if (name.equals(skeletonName)) {
				tempSkeleton = new Skeleton(x, y, name,
						RPG.ASSETS_PATH + "/units/skeleton.png");
				skeletons.add(tempSkeleton);
			} else if (name.equals(giantBatName)) {
				tempGiantBat = new GiantBat(x, y, name,
						RPG.ASSETS_PATH + "/units/dreadbat.png");
				giantBats.add(tempGiantBat);
			} else if (name.equals(draelicName)) {
				tempDraelic = new Draelic(x, y, name,
						RPG.ASSETS_PATH + "/units/necromancer.png");
				draelic.add(tempDraelic);
			}

		}
		read.close();
	}

	/**
	 * This method is responsible for loading the items.
	 * @throws SlickException
	 */
	public void loadItem() throws SlickException {
		items.add(new AmuletOfVitality(AMULET_X, AMULET_Y,
				AMULET_NAME, RPG.ASSETS_PATH + "/items/amulet.png"));
		items.add(new SwordOfStrength(SWORD_X, SWORD_Y,
				SWORD_NAME, RPG.ASSETS_PATH + "/items/sword.png"));
		items.add(new TomeOfAgility(TOME_X, TOME_Y,
				TOME_NAME, RPG.ASSETS_PATH + "/items/tome.png"));
		items.add(new ElixirOfLife(ELIXIR_X, ELIXIR_Y,
				ELIXIR_NAME, RPG.ASSETS_PATH + "/items/elixir.png"));
	}

	/**
	 * This method loops through the item array list and
	 * renders each of the items.
	 * @param g
	 */
	public void renderItem(Graphics g) {
		for (Item single_item : items) {
			single_item.render(g);
		}
	}

	/**
	 * This method updates the items within the items array list.
	 */
	public void updateItem() {
		for (Item single_item : items) {
			single_item.update(player);
		}
	}

	/**
	 * This method removes the item from the items array list if
	 * the item has been obtained.
	 */
	public void removeItem() {
		Iterator<Item> iter = items.iterator();

		/* Use a while loop to check if an item has been obtained,
		 * then proceed to delete by using an iterator. */
	    while (iter.hasNext()) {
	    	Item item = iter.next();
	    	if (item.isObtained() == true) {
	    		if (item.getName() == AMULET_NAME) {
	    			player.setInventory(0, 1);
	    			player.setMaxHP(player.getMaxHP() + 80);
	    			player.setCurrentHP(player.getMaxHP());
	    		} else if (item.getName() == SWORD_NAME) {
	    			player.setInventory(1, 1);
	    			player.setMaxDamage(player.getMaxDamage() + 30);
	    		} else if (item.getName() == TOME_NAME) {
	    			player.setInventory(2, 1);
	    			player.setCooldown(player.getCooldown() - 300);
	    			System.out.println("cooldown reduced" + player.getCooldown());
	    		} else if (item.getName() == ELIXIR_NAME) {
	    			player.setInventory(3, 1);
	    		}
	    		System.out.println(item.getName());
	    		iter.remove();
	    	}
	    }
	}

	/**
	 * This method is responsible to rendering all the monsters.
	 * @param g The graphics object.
	 */
	public void renderMonster(Graphics g) {

		for (Zombie zombie_unit : zombies) {
			zombie_unit.render(g);
			zombie_unit.renderHealthBar(g);
		}

		for (Bandit bandit_unit : bandits) {
			bandit_unit.render(g);
			bandit_unit.renderHealthBar(g);
		}

		for (Skeleton skeleton_unit : skeletons) {
			skeleton_unit.render(g);
			skeleton_unit.renderHealthBar(g);
		}

		for (GiantBat giantBat_unit : giantBats) {
			giantBat_unit.render(g);
			giantBat_unit.renderHealthBar(g);
		}

		for (Draelic draelic_unit : draelic) {
			draelic_unit.render(g);
			draelic_unit.renderHealthBar(g);
		}
	}

	/**
	 * This method renders all the single units.
	 * @param g The graphics object.
	 */
	public void renderSingleUnit(Graphics g) {
        elvira.render(g);
        elvira.renderHealthBar(g);

        garth.render(g);
        garth.renderHealthBar(g);

        princeAldric.render(g);
        princeAldric.renderHealthBar(g);
	}

	/**
	 * This method is responsible for updating all the monsters.
	 * @param delta
	 */
	public void updateMonster(int delta) {

		for (Zombie zombie_unit : zombies) {
			zombie_unit.updateMonster(delta, this, player);
			zombie_unit.update(delta, this);
		}

		for (Bandit bandit_unit : bandits) {
			bandit_unit.updateMonster(delta, this, player);
			bandit_unit.update(delta, this);
		}

		for (Skeleton skeleton_unit : skeletons) {
			skeleton_unit.updateMonster(delta, this, player);
			skeleton_unit.update(delta, this);
		}


		for (GiantBat giantBat_unit : giantBats) {
			giantBat_unit.updateMonster(delta, this, player);
			giantBat_unit.update(delta, this);
		}

		for (Draelic draelic_unit: draelic) {
			draelic_unit.updateMonster(delta, this, player);
			draelic_unit.update(delta, this);
		}

	}

	/**
	 * This method is responsible for removing the monsters
	 * from their respective array list after their HP reaches
	 * 0.
	 */
	public void removeMonster() {
	    Iterator<GiantBat> iter1 = giantBats.iterator();
	    Iterator<Zombie> iter2 = zombies.iterator();
	    Iterator<Bandit> iter3 = bandits.iterator();
	    Iterator<Skeleton> iter4 = skeletons.iterator();
	    Iterator<Draelic> iter5 = draelic.iterator();

	    while (iter1.hasNext()) {
	    	GiantBat gb = iter1.next();
	    	if (gb.getCurrentHP() <= 0) {
	    		iter1.remove();
	    	}
	    }

	    while (iter2.hasNext()) {
	    	Zombie z = iter2.next();
	    	if (z.getCurrentHP() <= 0) {
	    		iter2.remove();
	    	}
	    }

	    while (iter3.hasNext()) {
	    	Bandit b = iter3.next();
	    	if (b.getCurrentHP() <= 0) {
	    		iter3.remove();
	    	}
	    }

	    while (iter4.hasNext()) {
	    	Skeleton s = iter4.next();
	    	if (s.getCurrentHP() <= 0) {
	    		iter4.remove();
	    	}
	    }

	    while (iter5.hasNext()) {
	    	Draelic d = iter5.next();
	    	if (d.getCurrentHP() <= 0) {
	    		iter5.remove();
	    	}
	    }
	}



}
