import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class LinearHazard implements Hazard
{
	private static Random rand;
	
	static
	{
		rand = new Random();
	}
	
	Direction direction;
	Point position;
	
	public LinearHazard()
	{
		direction = Direction.fromIndex(rand.nextInt(4));
		int x, y;
		switch(direction)
		{
		case Top:
		case Bottom:
			x = HackRCIIMain.getAbsX(rand.nextInt(10));
			y = HackRCIIMain.getAbsY(1 + direction.y * -3);
			break;
		case Left:
		case Right:
			x = HackRCIIMain.getAbsX(5 + direction.x * -7);
			y = HackRCIIMain.getAbsY(rand.nextInt(3));
			break;
		default:
			throw new RuntimeException("Send help, it broke");
		}
		position = new Point(x, y);
	}
	
	protected abstract BufferedImage img();
	protected abstract int speed();

	@Override
	public boolean hitsLlama(Point llama) 
	{
		return llama.distanceSq(position) <= 16 * 16;
	}

	@Override
	public void step(HackRCIIMain main) 
	{
		position.x += direction.x * speed();
		position.y += direction.y * speed();
	}

	@Override
	public void draw(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.rotate(direction.getAngle() - Math.PI, position.x + img().getWidth() / 2, position.y + img().getHeight() / 2);
		g.drawImage(img(), position.x, position.y, null);
		g2.rotate(-(direction.getAngle() - Math.PI), position.x + img().getWidth() / 2, position.y + img().getHeight() / 2);

	}
	
	@Override
	public int damageDone()
	{
		return 1;
	}
	
	@Override
	public boolean shouldDie()
	{
		return false;
	}
}
