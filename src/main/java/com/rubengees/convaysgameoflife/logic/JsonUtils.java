package com.rubengees.convaysgameoflife.logic;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URL;
import java.util.Objects;

/**
 * Class for serializing the board from and into json.
 *
 * @author Ruben Gees
 */
public class JsonUtils {

    /**
     * Builds a matrix from the specified resource.
     *
     * @param clazz    The Class to load from.
     * @param location The location of the resource.
     * @return The new matrix.
     * @throws IOException         If the file was not found or could not be read.
     * @throws JsonSyntaxException If the json file is not properly formatted.
     */
    @NotNull
    public static boolean[][] deserialize(@NotNull Class<?> clazz, @NotNull String location)
            throws IOException, JsonSyntaxException {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(location);

        return deserialize(Utils.getResource(clazz, location));
    }

    /**
     * Builds the matrix from the specified file.
     *
     * @param file The file.
     * @return The new matrix.
     * @throws IOException         If the file was not found or could not be read.
     * @throws JsonSyntaxException If the json file is not properly formatted.
     */
    @NotNull
    public static boolean[][] deserialize(@NotNull File file) throws IOException, JsonSyntaxException {
        Objects.requireNonNull(file);

        return deserialize(file.toURI().toURL());
    }

    /**
     * Serializes the given matrix to the given json file.
     *
     * @param aliveMatrix The matrix to serialize.
     * @param file        The file to save to.
     * @throws IOException If the file was not found or could not write to.
     */
    public static void serialize(@NotNull boolean[][] aliveMatrix, @NotNull File file) throws IOException {
        Objects.requireNonNull(aliveMatrix);
        Objects.requireNonNull(file);

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file))) {
            new Gson().toJson(aliveMatrix, writer);
        }
    }

    /**
     * Method for actually deserializing the file.
     *
     * @param url The url of the file.
     * @return The new matrix.
     * @throws IOException         If the File was not found or could not be read.
     * @throws JsonSyntaxException If the json file is not properly formatted.
     */
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
