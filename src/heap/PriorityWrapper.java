package heap;

public class PriorityWrapper<T> implements Comparable<PriorityWrapper<T>> {

  private T value;
  private int priority;

  public PriorityWrapper(T value, int priority) {
    this.value = value;
    this.priority = priority;
  }

  @Override
  public int compareTo(PriorityWrapper<T> sPriorityWrapper) {
    return new Integer(priority).compareTo(sPriorityWrapper.priority);
  }

  public T getValue() {
    return value;
  }
}
