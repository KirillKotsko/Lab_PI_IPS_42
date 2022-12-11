package ua.kotsko.project.Examinator.utils;

import java.util.ArrayList;

import java.util.List;

public class ListShuffleUtil<T>{

    public void shuffle(List<T> shuffleList) {
        List<T> tmp = new ArrayList<>(shuffleList);
        Integer[] randSequence = RandomSequence.create(shuffleList.size());
        for (int i = 0; i < shuffleList.size(); i++)
            shuffleList.set(i, tmp.get(randSequence[i]));
    }

}
