int rightShift;
int downShift;
int size;

void draw()
{
	Core.update();

	//Draw Grid
	float[][] drawingGrid = Core.getDrawingGrid();
	float max = Core.getDrawingMax();
	float min = Core.getDrawingMin();

        float diff = max - min;

	for(int x = 0; x < drawingGrid.length; x++)
	{
		for(int y = 0; y < drawingGrid[0].length; y++)
		{
			fill((drawingGrid[x][y] - min) / diff);
			rect(rightShift + x * size, downShift + y * size, size, size);
		}
	}
}

void keyPressed()
{
	switch(key)
	{
		case 'u':
		case 'U':
			Core.drawU = true;
			break;

		case 'v':
		case 'V':
			Core.drawU = false;
			break;

		case ' ':
			Core.updateRequired = !Core.updateRequired;
			break;

		case 'i':
		case 'I':
			Core.simulator.initializeGrid();
			break;
	}
}

void setup()
{
	size(Config.width, Config.height);
	
	colorMode(RGB, 1.0f);
	noStroke();

	Core.init();

	size = Config.gridSize;
	rightShift = Config.rightShift;
	downShift = Config.downShift;
}
