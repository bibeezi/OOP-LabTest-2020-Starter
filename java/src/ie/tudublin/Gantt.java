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
	float taskX;
	float taskY;
	int taskSpace;

	float textX; 
	float textY;
	int textSpace;

	int rectH;
	float colour;
	float cOffset;

	float hitTop;

	
	public void settings()
	{
		size(800, 600);

		taskX = width * 0.05f;
		taskY = height * 0.15f;
		taskSpace = 40;

		textX = taskX * 3.5f; 
		textY = height * 0.08f;
		textSpace = 21;

		rectH = 35;
		colour = 0;
		cOffset = 255 / 9;
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
			textAlign(LEFT, CENTER);
			fill(0, 0, 255);
			text(task.getTask(), taskX, taskY);
			taskY += taskSpace;
		}

		// Vertical lines and numbers
		for(int count = 1; count < 31; count++)
		{
			if(count % 2 == 0)
			{
				stroke(255);
			}
			else
			{
				stroke(100);
			}

			line(textX, textY, textX, height - textY);
			
			textAlign(CENTER, CENTER);
			text(count, textX, textY - textSpace);

			textX += textSpace;
		}

		// reset necessary variables
		taskY = height * 0.15f;
		textX = taskX * 3.5f; 

		// Rectangles
		for(Task task : tasks)
		{
			fill(colour, 255, 255);
			noStroke();
			rect(map(task.getStart(), 1, 30, textX, textX + (29 * textSpace)),
				 taskY - (rectH / 2),
				 (task.getEnd() - task.getStart()) * textSpace,
				 rectH,
				 5);

			taskY += taskSpace;
			colour += cOffset;
		}

		taskY = height * 0.15f;
		colour = 0;
	}
	
	public void mousePressed()
	{
		float hitbox = 20;
		
		hitTop = height * 0.15f;

		for(int i = 0; i < tasks.size(); i++)
        {
			Task task = tasks.get(i);

			float left = map(task.getStart(), 1, 30, textX, textX + (29 * textSpace));
			float right = map(task.getEnd(), 1, 30, textX, textX + (29 * textSpace));

            if(mouseX > left && mouseX < left + hitbox && 
               mouseY > hitTop - (rectH / 2) && mouseY < hitTop + (rectH / 2))
            {
                println("Mouse Pressed L");
			}
			else if(mouseX > right - hitbox && mouseX < right && 
			mouseY > hitTop - (rectH / 2) && mouseY < hitTop + (rectH / 2))
			{
				println("Mouse Pressed R");
			}
			
			hitTop += taskSpace;
        }
	}

	public void mouseDragged()
	{
		println("Mouse Dragged");
	}

	
	
	public void setup() 
	{
		loadTasks();
		printTasks();

		background(0);

		colorMode(HSB);

	}
	
	public void draw()
	{
		displayTasks();
	}
}
