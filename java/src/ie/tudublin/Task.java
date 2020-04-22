package ie.tudublin;

import processing.data.TableRow;

// Encapsulating class
public class Task {
    // Private fields
    private String task;
    private int start;
    private int end;

    // Public accessor methods
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    // Constructor that takes initial values
    //  and assigns them to the fields
    public Task(String task, int start, int end)
    {
        this.task = task;
        this.start = start;
        this.end = end;
    }

    // Constructor that takes a TableRow as a parameter
    public Task(TableRow tr)
    {
        this(tr.getString("task"), tr.getInt("start"), tr.getInt("end"));
    }

    // toString() method
    @Override
    public String toString() {
        return "Task [end=" + end + ", start=" + start + ", task=" + task + "]";
    }
}