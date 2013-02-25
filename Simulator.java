public class Simulator
{
	public float[][] u;
	public float[][] v;

	public float[][] newU;
	public float[][] newV;

	public EdgeValue edgeValue;

	public float f;
	public float k;
	
	public boolean varyFAndK = false;
	public float timeStep;

	public float sizeSquared;
	
	public float minU;
	public float maxU;

	public float minV;
	public float maxV;


	public Simulator()
	{
		this.initializeGrid();	
	
		edgeValue = Config.defaultEdgeValue;
			
		varyFAndK = Config.varyFAndK;
		
		this.timeStep = Config.timeStep;

		this.sizeSquared = Config.gridSize * Config.gridSize;
	}

	private float getEdgeValue(int x, int y, float[][] array)
	{
		float temp = 0.0f;

		switch(edgeValue)
		{
			case TORIODIAL:
				if(x < 0)
				{	
					x = array.length - 1;
				}
				else if(x >= array.length)
				{
					x = 0;
				}
			
				if(y < 0)
				{
					y = array[0].length - 1;
				}
				else  if(y >= array.length)
				{
					y = 0;
				}
			
				temp = array[x][y];		
					
				break;

			case FIXED:
				break;
			
			case DERIVATIVE_FIXED:
				break;
		}

		return temp;
	}

	public boolean isEdgeSquare(int x, int y)
	{
		return (x < 0 || y < 0 || x >= u.length || y >= u[0].length);	
	}
	
	public void initializeGrid()
	{
		this.u = new float[Config.width / Config.gridSize][Config.height / Config.gridSize];
		this.v = new float[u.length][u[0].length];	

		this.newU = new float[u.length][u[0].length];
		this.newV = new float[v.length][v[0].length];

		for(int x = 0; x < u.length; x++)
		{
			for(int y = 0; y < u[0].length; y++)
			{
				this.u[x][y] = 1.0f;
				this.newU[x][y] = 1.0f;
			}
		}

		for(int index = 0; index < Config.numberOfRandomBlocks; index++)
		{
			spawnRandomBlock();
		}
	}

	public float lapacian(int x, int y, float[][] array)
	{
		float temp = 0.0f;
		
		if(isEdgeSquare(x - 1, y))
		{
			temp += getEdgeValue(x - 1, y, array);
		}
		else
		{
			temp += array[x - 1][y];
		}
		
		if(isEdgeSquare(x + 1, y))
		{
			temp += getEdgeValue(x + 1, y, array);
		}
		else
		{
			temp += array[x + 1][y];
		}
				
		if(isEdgeSquare(x, y - 1))
		{
			temp += getEdgeValue(x, y - 1, array);
		}
		else
		{
			temp += array[x][y - 1];
		}
		
		if(isEdgeSquare(x, y + 1))
		{
			temp += getEdgeValue(x, y + 1, array);
		}
		else
		{
			temp += array[x][y + 1];
		}

		temp -= 4 * array[x][y];
		
		return temp;
	}

	public void setFAndK(int number)
	{
		switch(number)
		{
			case 1:
				this.f = oneF;
				this.k = oneK;
				break;

			case 2:
				this.f = twoF;
				this.k = twoK;
				break;

			case 3:
				this.f = threeF;
				this.k = threeK;
				break;
			
			case 4:
				this.f = fourF;
				this.k = fourK;
				break;
		}
	}

	public void simulate(int ticks)
	{
		float[][] temp = null;

		float uvv = 0.0f;

		while(ticks > 0)
		{
			ticks--;	
		

			for(int x = 0; x < u.length; x++)
			{
				for(int y = 0; y < u[0].length; y++)
				{
					uvv = u[x][y] * v[x][y] * v[x][y];
					newU[x][y] = u[x][y] + timeStep * (f * (1 - u[x][y]) - uvv + ru * lapacian(x, y, u));
					newV[x][y] = v[x][y] + timeStep * (-(f + k) * v[x][y] + uvv + rv * lapacian(x, y, v));

				//	newU[x][y] = u[x][y] + ru * lapacian(x, y, u) * timeStep;
					//newV[x][y] = v[x][y] + rv * lapacian(x, y, v) * timeStep;
				}
			}


			temp = this.u;
			this.u = this.newU;
			this.newU = temp;

			temp = this.v;
			this.v = this.newV;
			this.newV = temp;
		}	
		
		minU = Float.MAX_VALUE;
		maxU = Float.MIN_VALUE;

		minV = Float.MAX_VALUE;
		maxV = Float.MIN_VALUE;

		for(int x = 0; x < u.length; x++)
		{
			for(int y = 0; y < u[0].length; y++)
			{
				if(u[x][y] > maxU)
				{
					maxU = u[x][y];
				}
				if(u[x][y] < minU)
				{
					minU = u[x][y];
				}

				if(u[x][y] > maxU)
				{
					maxU = u[x][y];
				}
				if(u[x][y] < minU)
				{
					minU = u[x][y];
				}
			}
		}
	}

	public void spawnRandomBlock()
	{
		int startX = Core.random.nextInt(u.length - 10);
		int startY = Core.random.nextInt(u[0].length  - 10);
		
		for(int x = startX; x < startX + 10; x++)
		{
			for(int y = startY; y < startY + 10; y++)
			{
				u[x][y] = 0.5f;
				v[x][y] = 0.25f;

				newU[x][y] = 0.5f;
				newV[x][y] = 0.25f;	
			}
		}
	}
	
	////////////////////////////////////////////////////////////////
	//// 		Static Constants								////
	////////////////////////////////////////////////////////////////
	
	public static final float ru = 0.082f;
	public static final float rv = 0.041f;

	public static final float oneF = 0.035f;
	public static final float oneK = 0.0625f;

	public static final float twoF = 0.035f;
	public static final float twoK = 0.06f;
	
	public static final float threeF = 0.0118f;
	public static final float threeK = 0.0475f;
	
	public static final float fourF = 0.0f;
	public static final float fourK = 0.0f;
}
