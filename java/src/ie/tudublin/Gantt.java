package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class Gantt extends PApplet
{	
	// ArrayList declaration
	ArrayList<Task> tasks = new ArrayList<Task>();
	
	public void settings()
	{
		size(800, 600);
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
		// Task names variables
		float taskX = width * 0.05f;
		float taskY = height * 0.15f;
		int taskSpace = 40;

		for(Task task : tasks)
		{
			textAlign(LEFT, CENTER);
			fill(0, 0, 255);
			text(task.getTask(), taskX, taskY);
			taskY += taskSpace;
		}


		// Vetrtical lines and numbers
		float textX = taskX * 3.5f; 
		float textY = height * 0.08f;
		int textSpace = 21;

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


		// Rectangles
		int rectH = 35;
		float colour = 0;
		float cOffset = 255 / 9;

		// reset necessary variables
		taskY = height * 0.15f;
		textX = taskX * 3.5f; 


		for(Task task : tasks)
		{
			fill(colour, 255, 255);
			noStroke();
			rect(map(task.getStart(), 1, 30, textX, textX + (29 * textSpace)),
				 taskY - (rectH / 2),
				 (task.getEnd() - task.getStart()) * textSpace,
				 rectH, 5);

			taskY += taskSpace;
			colour += cOffset;
		}
	}
	
	public void mousePressed()
	{
		println("Mouse pressed");	
	}

	public void mouseDragged()
	{
		println("Mouse dragged");
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
