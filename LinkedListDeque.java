package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;

    private class TNode {
        private T value;
        private TNode next;
        private TNode prev;

        private TNode(TNode p, T item, TNode n) {
            prev = p;
            value = item;
            next = n;
        }
    }

    private TNode sentinel;

    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        TNode n = new TNode(sentinel, item, sentinel.next);
        sentinel.next.prev = n;
        sentinel.next = n;
        size++;
    }

    @Override
    public void addLast(T item) {
        TNode n = new TNode(sentinel.prev, item, sentinel);
        sentinel.prev.next = n;
        sentinel.prev = n;
        size++;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void printDeque() {
        TNode current = sentinel.next;
        while (current != sentinel) {
            System.out.print(current.value + " ");
            current = current.next;
        }
        System.out.print('\n');
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        TNode n = sentinel.next.next;
        TNode first = sentinel.next;
        sentinel.next = n;
        n.prev = sentinel;
        size--;
        return first.value;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        TNode n = sentinel.prev.prev;
        TNode last = sentinel.prev;
        sentinel.prev = n;
        n.next = sentinel;
        size--;
        return last.value;
    }

    @Override
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        TNode current = sentinel;
        while (index >= 0) {
            current = current.next;
            index--;
        }
        return current.value;
    }

    public T getRecursive(int index) {
        if (index > size - 1) {
            return null;
        }
        return getRecursive(index, sentinel.next);
    }

    private T getRecursive(int index, TNode node) {
        if (index == 0) {
            return node.value;
        }
        return getRecursive(index - 1, node.next);
    }

    public Iterator<T> iterator() {
        return new LLDequeIterator();
    }

    private class LLDequeIterator implements Iterator<T> {
        private TNode pos = sentinel.next;

        public boolean hasNext() {
            return !isEmpty() && pos != sentinel;
        }

        public T next() {
            if (!hasNext()) {
                return null;
            }
            T returnItem = pos.value;
            pos = pos.next;
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
