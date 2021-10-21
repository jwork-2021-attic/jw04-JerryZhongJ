package com.anish.calabashbros;

public class QuickSorter<T extends Comparable<T>> implements Sorter<T> {

    private T[] a;
    @Override
    public void load(T[] a) {
        this.a = a;
    }

    private void swap(int i, int j) {
        T temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        plan += "" + a[i] + "<->" + a[j] + "\n";
    }

    private String plan = "";

    @Override
    public void sort() {
        // TODO Auto-generated method stub
        sort(0, a.length);
    }
    
    private void sort(int st, int en) {
        if (st + 1 >= en)
            return;
        int left_ptr = st, right_ptr = en - 1;
        T mid = a[(st + en) / 2];
        while (left_ptr <= right_ptr) {
            while (a[left_ptr].compareTo(mid) < 0)
                left_ptr++;
            while (a[right_ptr].compareTo(mid) > 0)
                right_ptr--;
            if (left_ptr <= right_ptr) {
                swap(left_ptr, right_ptr);
                left_ptr++;
                right_ptr--;
            }
        }
        sort(st, right_ptr + 1);
        sort(left_ptr, en);
    }

    @Override
    public String getPlan() {
        return this.plan;
    }

}