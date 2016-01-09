package com.rubengees.convaysgameoflife.logic;

import com.google.gson.JsonSyntaxException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

/**
 * Tests for the {@link JsonUtils}.
 *
 * @author Ruben Gees
 */
public class JsonUtilsTest {

    boolean[][] aliveMatrix;

    @Before
    public void setUp() throws Exception {
        aliveMatrix = new boolean[2][2];

        aliveMatrix[0][0] = false;
        aliveMatrix[0][1] = false;
        aliveMatrix[1][0] = true;
        aliveMatrix[1][1] = false;
    }

    @Test
    public void testDeserialize() throws Exception {
        File file = File.createTempFile("import", ".json");

        file.deleteOnExit();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("[[false,false],[true,false]]");
        }

        boolean[][] result = JsonUtils.deserialize(file);

        Assert.assertTrue(Arrays.deepEquals(aliveMatrix, result));
    }

    @Test(expected = JsonSyntaxException.class)
    public void testDeserializeCorrupt() throws Exception {
        File file = File.createTempFile("import", ".json");

        file.deleteOnExit();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("false,false],[true,false]]");
        }

        JsonUtils.deserialize(file);
    }

    @Test
    public void testSerialize() throws Exception {
        File file = File.createTempFile("export", ".json");
        file.deleteOnExit();

        JsonUtils.serialize(aliveMatrix, file);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String content = reader.readLine();

            Assert.assertEquals("[[false,false],[true,false]]", content);
        }
    }
}