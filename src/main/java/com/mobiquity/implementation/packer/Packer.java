package com.mobiquity.implementation.packer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Stream;

public class Packer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Packer.class);

    private Packer() {
    }

    public static String pack(Stream<String> content) {
        List<String> output = new ArrayList();
            content.forEach(line-> {
                List<String> itemsToIncludeInPackage = parseLine(line.strip());
                if(itemsToIncludeInPackage.isEmpty()){
                    output.add("-");
                } else{
                    output.add(String.join(",", itemsToIncludeInPackage));
                }
            });

        return String.join("\n", output);
    }

    // Parse each individual line of the input file and returns the list of built Items
    private static List<String> parseLine(String lineStringContent) {
        List<String> itemsToIncludeInPackage = new ArrayList<>();
        if(!lineStringContent.isEmpty()) {
            LOGGER.info("Processing file row: " + lineStringContent);
            List<Item> pojoItems = new ArrayList<>();

            String[] content = lineStringContent.split(":");
            int maxWeight = Integer.parseInt(content[0].trim());
            buildPojoItems(pojoItems, content[1].trim().replaceAll("\\s+", ""), maxWeight);
            Collections.sort(pojoItems);

            double partialWeight = 0;

            for (int i = 0; i < pojoItems.size(); i++) {
                Item item = pojoItems.get(i);
                if (i < pojoItems.size() - 1) {
                    if (item.getWeight() + partialWeight <= maxWeight) {
                        partialWeight += item.getWeight();
                        itemsToIncludeInPackage.add(item.getIndex());
                    }
                }
            }
        }
        return itemsToIncludeInPackage;
    }

    /** Build the list of pojos with the content from the file row.
     * These Items will then be processed to determine which will go into the package to send.*/
    private static void buildPojoItems(List<Item> pojoItems, String content, int maxWeight) {
        LOGGER.info("Building Item: " + content);
        String[] items = content.split("[()]");
        Arrays.stream(items).filter(value -> !value.isEmpty()).forEach(value -> {
            String[] properties = value.split(",");
            String index = properties[0];
            double itemWeight = Double.parseDouble(properties[1]);
            double itemCost = Double.parseDouble(properties[2].substring(1));
            // We only add the item if the weight is less or equals to the max weight that the package allows.
            if(itemWeight <= maxWeight){
                pojoItems.add(new Item(index,itemWeight,itemCost));
            }
        });
    }
}
