package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int front;
    private int rear;
    private double usageRatio;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        front = -1;
        rear = -1;
        usageRatio = 0.0;
    }

    @Override
    public int size() {
        return size;
    }

    private int nextIndex(int index) {
        index++;
        if (index == items.length) {
            index = 0;
        }
        return index;
    }

    private int prevIndex(int index) {
        index--;
        if (index == -1) {
            index = items.length - 1;
        }
        return index;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int j = 0;
        for (int i = front; i != rear; i = nextIndex(i)) {
            a[j] = items[i];
            j++;
        }
        a[j] = items[rear];
        items = a;
        front = 0;
        rear = j;
        usageRatio = (double) size / items.length;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (isEmpty()) {
            front = 0;
            rear = 0;
        } else {
            front = prevIndex(front);
        }
        items[front] = item;
        size++;
        usageRatio = (double) size / items.length;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (isEmpty()) {
            front = 0;
            rear = 0;
        } else {
            rear = nextIndex(rear);
        }
        items[rear] = item;
        size++;
        usageRatio = (double) size / items.length;
    }

    @Override
    public void printDeque() {
        for (int i = front; i != nextIndex(rear); i = nextIndex(i)) {
            System.out.print(items[i] + " ");
        }
        System.out.println('\n');
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T first = items[front];
        front = nextIndex(front);
        size--;
        usageRatio = (double) size / items.length;
        if (items.length >= 16 && usageRatio < 0.25) {
            resize(items.length / 2);
        }
        return first;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last = items[rear];
        rear = prevIndex(rear);
        size--;
        usageRatio = (double) size / items.length;
        if (items.length >= 16 && usageRatio < 0.25) {
            resize(items.length / 2);
        }
        return last;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int i = (front + index) % items.length;
        return items[i];
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos = front;
        private int count = size;

        public boolean hasNext() {
            return !isEmpty() && count > 0;
        }

        public T next() {
            if (!hasNext()) {
                return null;
            }
            T returnItem = items[pos];
            pos = nextIndex(pos);
            count--;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof Deque<?>)) {
            return false;
        }
        Deque o = (Deque) other;
        if (o.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < this.size; i++) {
            if (!(get(i).equals(o.get(i)))) {
                return false;
            }
        }
        return true;
    }

}
