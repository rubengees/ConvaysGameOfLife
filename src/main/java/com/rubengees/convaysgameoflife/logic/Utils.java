package com.rubengees.convaysgameoflife.logic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.Objects;

/**
 * Class, holding various helper methods.
 *
 * @author Ruben Gees
 */
public class Utils {

    /**
     * Clones a matrix of cells for immutability purposes.
     *
     * @param src The matrix to clone.
     * @return A clone of the given matrix.
     */
    @NotNull
    public static Cell[][] cloneMatrix(@NotNull Cell[][] src) {
        Objects.requireNonNull(src);

        Cell[][] result = new Cell[src.length][src[0].length];

        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src[0].length; j++) {
                result[i][j] = src[i][j].clone();
            }
        }

        return result;
    }

    /**
     * Returns the URL for the resource with the given name.
     *
     * @param clazz The class to load the resource from.
     * @param location  The location of the resource.
     * @return The URL of the resource. May be <code>null</code> if the resource is not found.
     */
    @Nullable
    public static URL getResource(@NotNull Class<?> clazz, @NotNull String location) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(location);

        return clazz.getClassLoader().getResource(location);
    }

}
