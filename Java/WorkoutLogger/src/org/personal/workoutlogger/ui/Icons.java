package org.personal.workoutlogger.ui;

import javax.swing.*;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class Icons {
    private static final Map<String, ImageIcon> iconCache = new HashMap<>();
    private static final String ICON_PATH = "/resources/icons/";

    // Standard icons used throughout the application
    public static final String ADD = "add.png";
    public static final String EDIT = "edit.png";
    public static final String DELETE = "delete.png";
    public static final String REFRESH = "refresh.png";
    public static final String CLOSE = "close.png";
    public static final String VIEW = "view.png";
    public static final String SAVE = "save.png";
    public static final String LOGIN = "login.png";
    public static final String LOGOUT = "logout.png";
    public static final String USER = "user.png";
    public static final String WORKOUT = "workout.png";
    public static final String METRICS = "metrics.png";

    public static ImageIcon getIcon(String name) {
        return getIcon(name, -1);
    }

    public static ImageIcon getIcon(String name, int size) {
        String key = name + (size > 0 ? "_" + size : "");

        if (iconCache.containsKey(key)) {
            return iconCache.get(key);
        }

        // First try to load from resources
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(Icons.class.getResource(ICON_PATH + name));
            if (size > 0) {
                Image img = icon.getImage();
                Image resized = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                icon = new ImageIcon(resized);
            }
        } catch (Exception e) {
            // If icon not found, create a default colored square as fallback
            icon = new ImageIcon(new byte[0]); // Empty icon instead of null
        }

        iconCache.put(key, icon);
        return icon;
    }

    public static void clearCache() {
        iconCache.clear();
    }
}
