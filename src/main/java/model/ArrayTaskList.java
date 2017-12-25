package model;


import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayTaskList extends TaskList{

    private Task[] tasks;
    private int numberOfTasks;
    private int currentCapacity;
    private class ArrayTaskListIterator implements Iterator<Task> {
        private int cursor;
        private int lastCalled = -1;
        @Override
        public boolean hasNext() {
            return cursor < numberOfTasks;
        }

        @Override
        public Task next() {
            if (!hasNext()){
                throw new NoSuchElementException("No next element");
            }
            lastCalled = cursor;
            return getTask(cursor++);
        }

        @Override
        public void remove() {
            if (lastCalled == -1){
                throw new IllegalStateException();
            }
            ArrayTaskList.this.remove(getTask(lastCalled));
            cursor = lastCalled;
            lastCalled = -1;
        }
    }
    public ArrayTaskList(){
        currentCapacity = 10;
        this.tasks = new Task[currentCapacity];
    }

    @Override
    public Iterator<Task> iterator() {
        return new ArrayTaskListIterator();
    }

    @Override
    public void add(Task task){
        if (task.equals(null)) throw new NullPointerException("Task shouldn't be null");
        if (numberOfTasks == currentCapacity-1){
            currentCapacity = currentCapacity * 2;
            Task[] withAddedTask = new Task[currentCapacity];
            System.arraycopy(tasks,0,withAddedTask,0,tasks.length);
            this.tasks = withAddedTask;
        }
        this.tasks[numberOfTasks] = task;
        this.numberOfTasks++;
    }
    @Override
    public boolean remove(Task task){
        int indexOfTaskToDelete = -1;
        for(int i = 0; i < tasks.length; i++){
            if (task.equals(tasks[i])){
                indexOfTaskToDelete = i;
                break;
            }
        }
        if (indexOfTaskToDelete >= 0){
            this.numberOfTasks--;
            System.arraycopy(tasks, indexOfTaskToDelete+1,tasks,indexOfTaskToDelete,
                    numberOfTasks-indexOfTaskToDelete+1);
            return true;
        }
        return false;
    }
    @Override
    public int size(){
        return this.numberOfTasks;
    }
    @Override
    public Task getTask(int index){
        if (index < 0 || index > size()-1) throw new IndexOutOfBoundsException("Index not found");
        return tasks[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayTaskList that = (ArrayTaskList) o;

        if (numberOfTasks != that.numberOfTasks) return false;
        int i = 0;
        for (Task a : this){
            if (!a.equals(((ArrayTaskList) o).getTask(i))){
                return false;
            }
            i++;
        }
        return true;
        //return Arrays.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(tasks);
        result = 31 * result + numberOfTasks;
        result = 31 * result + currentCapacity;
        return result;
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "tasks=" + Arrays.toString(tasks) +
                ", numberOfTasks=" + numberOfTasks +
                ", currentCapacity=" + currentCapacity +
                '}';
    }
    @Override
    protected ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList tasks = new ArrayTaskList();
        for (int i = 0; i < this.tasks.length; i++){
            tasks.add(this.getTask(i));
        }
        return tasks;

    }
}