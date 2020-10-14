package aqua.blatt1.common;

import java.io.Serializable;
import java.util.Random;

import aqua.blatt1.client.TankModel;

@SuppressWarnings("serial")
public final class FishModel implements Serializable {
	private final static int xSize = 100;
	private final static int ySize = 50;
	private final static Random rand = new Random();

	private final String id;
	private int x;
	private int y;
	private Direction direction;

	private boolean toggled;

	public FishModel(String id, int x, int y, Direction direction) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public String getId() {
		return id;
	}

	public String getTankId() {
		return id.substring(id.indexOf("@") + 1);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Direction getDirection() {
		return direction;
	}

	public void reverse() {
		direction = direction.reverse();
	}

	public static int getXSize() {
		return xSize;
	}

	public static int getYSize() {
		return ySize;
	}

	public void toggle() {
		toggled = !toggled;
	}

	public boolean isToggled() {
		return toggled;
	}

	public boolean hitsEdge() {
		return (direction == Direction.LEFT && x == 0)
				|| (direction == Direction.RIGHT && x == TankModel.WIDTH - xSize);
	}

	public boolean disappears() {
		return (direction == Direction.LEFT && x == -xSize)
				|| (direction == Direction.RIGHT && x == TankModel.WIDTH);
	}

	public void update() {
		x += direction.getVector();

		double discreteSin = Math.round(Math.sin(x / 30.0));
		discreteSin = rand.nextInt(10) < 8 ? 0 : discreteSin;
		y += discreteSin;
		y = y < 0 ? 0 : y > TankModel.HEIGHT - FishModel.getYSize() ? TankModel.HEIGHT
				- FishModel.getYSize() : y;
	}

	public void setToStart() {
		x = direction == Direction.LEFT ? TankModel.WIDTH : -xSize;
	}

	public boolean isDeparting() {
		return (direction == Direction.LEFT && x < 0)
				|| (direction == Direction.RIGHT && x > TankModel.WIDTH - xSize);
	}

}
