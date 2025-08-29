package com.pgno248.model;

import java.util.NoSuchElementException;

public class Queue<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;  // Array to store queue elements
    private int front;          // Index of the front element
    private int rear;           // Index where next element would be inserted
    private int size;           // Current number of elements
    private int capacity;       // Total capacity of the array
    
    public Queue() {
        this(DEFAULT_CAPACITY);
    }
    
    public Queue(int initialCapacity) {
        capacity = initialCapacity;
        elements = new Object[capacity];
        front = 0;
        rear = 0;
        size = 0;
    }
      // Add an item to the end of the queue
    public void enqueue(T item) {
        // Resize if the array is full
        if (size == capacity) {
            resize(capacity * 2);
        }
        
        elements[rear] = item;
        rear = (rear + 1) % capacity;  // Circular array implementation
        size++;
    }
      // Remove and return the item from the front of the queue
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        
        T item = (T) elements[front];
        elements[front] = null;  // Help garbage collection
        front = (front + 1) % capacity;  // Circular array implementation
        size--;
        
        // Shrink the array if it's too large
        if (size > 0 && size == capacity / 4) {
            resize(capacity / 2);
        }
        
        return item;
    }
      // Return the front item without removing it
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return (T) elements[front];
    }
      // Check if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }
      // Return the number of items in the queue
    public int size() {
        return size;
    }
      // Resize the underlying array to the specified capacity
    private void resize(int newCapacity) {
        Object[] newElements = new Object[newCapacity];
        
        // Copy elements from the old array to the new array
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(front + i) % capacity];
        }
        
        elements = newElements;
        front = 0;
        rear = size;
        capacity = newCapacity;
    }
}
