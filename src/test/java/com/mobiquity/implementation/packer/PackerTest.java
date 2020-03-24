package com.mobiquity.implementation.packer;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PackerTest {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Test
    public void testSuccess() {
        String expectedResult = "4\n" +
                "-\n" +
                "2,7\n" +
                "8,9";
        Stream<String> content = Stream.of("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)","8 : (1,15.3,€34)",
                "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)",
                "56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)");
        String result = Packer.pack(content);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testEmptyContentSuccess() {
        String expectedResult = "[-]";
        Stream<String> content = Stream.of("");
        String result = Packer.pack(content);
        assertEquals(expectedResult, result);
    }
}
