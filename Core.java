import java.util.Random;

public class Core
{
	//Variables
	public static boolean drawU;
	public static int ticks;

	public static Simulator simulator;
	public static boolean updateRequired;
		
	public static Random random;

	//Methods
	public static void checkOptions()
	{
		if((Config.width - Config.rightShift) % Config.gridSize != 0)
		{
			System.out.println("Please choose a grid size that divides evenly into the screen width.");
			System.exit(1);
		}
		if((Config.height - Config.downShift)% Config.gridSize != 0)
		{
			System.out.println("Please choose a grid size that divides evenly into the screen height.");
			System.exit(1);
		}
	}

	public static float[][] getDrawingGrid()
	{
		if(drawU)
		{
			return simulator.u;
		}
		else
		{
			return simulator.v;
		}
	}
	
	public static float getDrawingMax()
	{
		if(drawU)
		{
			return simulator.maxU;
		}
		else
		{
			return simulator.maxV;
		}
	}
		
	public static float getDrawingMin()
	{
		if(drawU)
		{
			return simulator.minU;
		}
		else
		{
			return simulator.minV;
		}
	}

	public static void init()
	{
		checkOptions();
		Log.init(Config.logLevel);
		random = new Random();	

		drawU = true;
		simulator = new Simulator();
		ticks = Config.ticks;
		updateRequired = Config.startOnLoad;

		simulator.setFAndK(Config.defaultFAndK);
	}
	
	public static void uninit()
	{
		Log.uninit();
	}

	public static void update()
	{
		if(updateRequired)
		{
			simulator.simulate(ticks);
			//updateRequired = false;
		}
	}
}
