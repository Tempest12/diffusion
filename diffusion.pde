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

		case 'd':
		case 'D':
			Core.simulator.react = !Core.simulator.react;
			break;

		case 'p':
		case 'P':
			Core.simulator.varyFAndK = !Core.simulator.varyFAndK;
			break;
		
		case '1':
			Core.simulator.setFAndK(1);
			break;
		
		case '2':
			Core.simulator.setFAndK(2);
			break;

		case '3':
			Core.simulator.setFAndK(3);
			break;
	
		case '4':
			Core.simulator.setFAndK(4);
			break;
	}
}

void mouseClicked()
{
	if(mouseX - rightShift >= 0 && mouseY - downShift >= 0)
	{
		println("U: " + Core.simulator.u[(mouseX - rightShift) / Config.gridSize][(mouseY - downShift) / Config.gridSize]);
		println("V: " + Core.simulator.v[(mouseX - rightShift) / Config.gridSize][(mouseY - downShift) / Config.gridSize]);
		
		if(Core.simulator.varyFAndK)
		{
			println("K: " + (((mouseX - rightShift)/ Config.gridSize) * (0.04 / Core.simulator.u.length) + 0.03f));
			println("F: " + ((mouseY - downShift)/ Config.gridSize) * (0.08 / Core.simulator.u.length));	
		}
		println("");
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
