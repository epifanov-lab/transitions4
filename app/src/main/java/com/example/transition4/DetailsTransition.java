package com.example.transition4;

import androidx.transition.ChangeBounds;
import androidx.transition.ChangeTransform;
import androidx.transition.TransitionSet;


public class DetailsTransition extends TransitionSet {

    private final int duration;

    public DetailsTransition(int duration) {
        this.duration = duration;
        init();
    }

    private void init() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds())
                                         .addTransition(new ChangeTransform())
                                         //addTransition(new ChangeImageTransform())
                                         .setDuration(duration)
        ;
    }
}
