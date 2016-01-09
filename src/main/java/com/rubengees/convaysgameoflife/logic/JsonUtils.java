package com.rubengees.convaysgameoflife.logic;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URL;
import java.util.Objects;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class JsonUtils {

    @NotNull
    public static boolean[][] deserialize(@NotNull Class<?> clazz, @NotNull String location) throws IOException {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(location);

        return deserialize(Utils.getResource(clazz, location));
    }

    @NotNull
    public static boolean[][] deserialize(@NotNull File file) throws IOException {
        Objects.requireNonNull(file);

        return deserialize(file.toURI().toURL());
    }

    public static void serialize(@NotNull boolean[][] aliveMatrix, @NotNull File file) throws IOException {
        Objects.requireNonNull(aliveMatrix);
        Objects.requireNonNull(file);

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file))) {
            new Gson().toJson(aliveMatrix, writer);
        }
    }

    @NotNull
    private static boolean[][] deserialize(@Nullable URL url) throws IOException, JsonSyntaxException {
        if (url == null) {
            throw new IOException();
        }

        try (InputStreamReader reader = new InputStreamReader(url.openStream())) {
            return new Gson().fromJson(reader, boolean[][].class);
        }
    }

}
