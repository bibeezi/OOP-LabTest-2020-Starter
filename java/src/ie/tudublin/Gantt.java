package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class Gantt extends PApplet
{	
	// ArrayList declaration
	ArrayList<Task> tasks = new ArrayList<Task>();

	// displayTask method variables
	float taskX, taskY;
	int taskSpace;

	float textX, textY, textXEnd;
	int textSpace;

	int rectH;
	float rectOffset;

	float colour;
	float colOffset;

	float hitboxTop;
	float hitbox;

	int selectedL;
	int selectedR;
	
	public void settings()
	{
		size(800, 600);
		
		taskX = width * 0.05f;
		taskY = height * 0.15f;
		taskSpace = 40;

		textX = taskX * 3.5f; 
		textY = height * 0.08f;
		textSpace = 21;

		textXEnd = textX + (29 * textSpace);

		rectH = 35;
		rectOffset = rectH / 2;

		colour = 0;
		colOffset = 255 / 9;

		hitbox = 10;
		hitboxTop = 0;

		selectedL = -1;
		selectedR = -1;
	}

	// Populate the ArrayList 'tasks'
	public void loadTasks()
	{
		Table t = loadTable("tasks.csv", "header");

		for(TableRow row : t.rows())
		{
			Task task = new Task(row);
			tasks.add(task);
		}
	}

	// Prints the elements of the ArrayList 'tasks' on the console
	public void printTasks()
	{
		for(Task task : tasks)
		{
			println(task);
		}
	}

	// display tasks as in the video
	public void displayTasks()
	{
		// Task names
		for(Task task : tasks)
		{
			fill(0, 0, 255);

			textAlign(LEFT, CENTER);
			text(task.getTask(), taskX, taskY);

			taskY += taskSpace;
		}

		// Vertical lines and numbers
		for(int count = 1; count < 31; count++)
		{
			// Alternates white or grey lines
			colour = (count % 2 == 0) ? 255 : 100;

			stroke(colour);
			line(textX, textY, textX, height - textY);
			
			textAlign(CENTER, CENTER);
			text(count, textX, textY - textSpace);

			textX += textSpace;
		}

		// reset necessary variables
		taskY = height * 0.15f;
		textX = taskX * 3.5f; 
		colour = 0;

		// Rectangles
		for(Task task : tasks)
		{
			fill(colour, 255, 255);
			noStroke();

			rect(map(task.getStart(), 1, 30, textX, textXEnd),
				 taskY - rectOffset,
				 (task.getEnd() - task.getStart()) * textSpace,
				 rectH,
				 5);

			taskY += taskSpace;
			colour += colOffset;
		}

		// reset necessary variables
		taskY = height * 0.15f;
		colour = 0;
	}
	
	public void mousePressed()
	{	
		// Reset selected box
		selectedL = -1;
		selectedR = -1;

		hitboxTop = height * 0.15f;

		for(int i = 0; i < tasks.size(); i++)
        {
			Task task = tasks.get(i);

			float left = map(task.getStart(), 1, 30, textX, textXEnd);
			float right = map(task.getEnd(), 1, 30, textX, textXEnd);

            if(mouseX > left && mouseX < left + hitbox && 
               mouseY > hitboxTop - rectOffset && mouseY < hitboxTop + rectOffset)
            {
				//println("Mouse Pressed L");
				selectedL = i;
			}
			else if(mouseX > right - hitbox && mouseX < right && 
			mouseY > hitboxTop - rectOffset && mouseY < hitboxTop + rectOffset)
			{
				//println("Mouse Pressed R");
				selectedR = i;
			}
			
			hitboxTop += taskSpace;
        }
	}

	public void mouseDragged()
	{
		int size;

		// Selected left of the box
		if(selectedL != -1)
		{
			Task task = tasks.get(selectedL);

			float left = map(task.getStart(), 1, 30, textX, textXEnd);

			// Moving to the left
			if(mouseX < left && mouseX > textX)
			{
				size = task.getStart() - 1;
				task.setStart(size);
			}
			// Moving to the right
			else if(task.getEnd() - task.getStart() != 1 && mouseX > textX && mouseX > left + textSpace)
			{
				size = task.getStart() + 1;
				task.setStart(size);
			}
		}
		// Selected right of the box
		else if(selectedR != -1)
		{
			Task task = tasks.get(selectedR);

			float right = map(task.getEnd(), 1, 30, textX, textXEnd);

			// Moving to the left
			if(mouseX > right && mouseX < textXEnd)
			{
				size = task.getEnd() + 1;
				task.setEnd(size);
			}
			// Moving to the right
			else if(task.getEnd() - task.getStart() != 1 && mouseX < right - textSpace && mouseX < textXEnd)
			{
				size = task.getEnd() - 1;
				task.setEnd(size);
			}
		}
	}

	
	
	public void setup() 
	{
		loadTasks();
		printTasks();

		colorMode(HSB);
	}
	
	public void draw()
	{
		background(0);

		displayTasks();
	}
}
