package circular_queue;

class CircularQueue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;
    private final int capacity;

    // Constructor to initialize queue with a fixed capacity
    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    // Node class
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Enqueue: Add an element to the rear of the queue
    public boolean enqueue(T data) {
        if (isFull()) {
            System.out.println("Queue is full. Cannot enqueue " + data);
            return false;
        }
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
            newNode.next = front; // Circular link
        } else {
            rear.next = newNode;
            rear = newNode;
            rear.next = front; // Maintain circular link
        }
        size++;
        return true;
    }

    // Dequeue: Remove and return the element from the front of the queue
    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot dequeue.");
            return null;
        }
        T data = front.data;
        if (front == rear) { // Only one element in the queue
            front = null;
            rear = null;
        } else {
            front = front.next;
            rear.next = front; // Maintain circular link
        }
        size--;
        return data;
    }

    // Peek: Get the front element without removing it
    public T peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Nothing to peek.");
            return null;
        }
        return front.data;
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Check if the queue is full
    public boolean isFull() {
        return size == capacity;
    }

    // Get the size of the queue
    public int getSize() {
        return size;
    }

    // Print the elements of the queue
    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        Node<T> current = front;
        System.out.print("Queue elements: ");
        do {
            System.out.print(current.data + " ");
            current = current.next;
        } while (current != front);
        System.out.println();
    }
}
