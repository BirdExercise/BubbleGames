package com.example.dreamjm.bubblegamewithclient;

import java.util.Random;

/**
 * Created by Dreamjm on 12/24/17.
 */

public enum DifferentBubble {
    LightBlue(R.drawable.bubble1),
    Pink(R.drawable.bubble2),
    Orange(R.drawable.bubble3);

    private final int resourceId;

    private DifferentBubble(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    private static final Random random = new Random();

    public static int getSize() {
        return DifferentBubble.values().length;
    }

    public static DifferentBubble randomBubble(int index) {
        return DifferentBubble.values()[index];
    }
}
