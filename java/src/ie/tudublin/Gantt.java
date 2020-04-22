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
		float border = width * 0.05f;
		float taskY = height * 0.2f;
		int count = 1;

		for(Task task : tasks)
		{
			textAlign(LEFT, CENTER);
			fill(255);
			text(task.getTask(), border, map(count, 1, tasks.size(), taskY, height - taskY));
			count++;
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
