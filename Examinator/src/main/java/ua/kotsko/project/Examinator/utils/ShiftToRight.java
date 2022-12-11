package ua.kotsko.project.Examinator.utils;

public class ShiftToRight<T> {

    public void shift(T[] a, int n) {
        if (n > a.length - 1)
            n = a.length - 1;
        T tmp = a[n];
        System.arraycopy(a, 0, a, 1, n);
        a[0] = tmp;
    }

}
