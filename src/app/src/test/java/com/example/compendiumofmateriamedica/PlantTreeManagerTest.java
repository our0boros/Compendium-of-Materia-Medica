package com.example.compendiumofmateriamedica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.Datastructure.Plant;
import model.Datastructure.PlantTreeManager;
import model.Datastructure.RBTree;

public class PlantTreeManagerTest {

    private RBTree<Plant> plantTree;
    private PlantTreeManager plantTreeManager;

    @Before
    public void setUp() {
        plantTree = new RBTree<>();
        plantTreeManager = PlantTreeManager.getInstance(plantTree);

        Plant plant1 = new Plant(1, "Rose", "rose", "Rosa", "rose_image.png", "Rosa", "Rosaceae", "A beautiful flower");
        Plant plant2 = new Plant(2, "Lily", "lily", "Lilium", "lily_image.png", "Lilium", "Liliaceae", "A lovely flower");
        Plant plant3 = new Plant(3, "Sunflower", "sunflower", "Helianthus", "sunflower_image.png", "Helianthus", "Asteraceae", "A sunny flower");

        plantTreeManager.insert(plant1.getId(), plant1);
        plantTreeManager.insert(plant2.getId(), plant2);
        plantTreeManager.insert(plant3.getId(), plant3);
    }

    @Test
    public void testInsert() {
        Plant newPlant = new Plant(4, "Tulip", "tulip", "Tulipa", "tulip_image.png", "Tulipa", "Liliaceae", "A pretty flower");
        plantTreeManager.insert(newPlant.getId(), newPlant);

        ArrayList<Plant> result = plantTreeManager.search(PlantTreeManager.PlantInfoType.ID, String.valueOf(newPlant.getId()));
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(newPlant.getCommonName(), result.get(0).getCommonName());
    }

    @Test
    public void testDelete() {
        plantTreeManager.delete(1);
        ArrayList<Plant> result = plantTreeManager.search(PlantTreeManager.PlantInfoType.ID, "1");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchByCommonName() {
        ArrayList<Plant> plants = plantTreeManager.search(PlantTreeManager.PlantInfoType.COMMON_NAME, "Rose");
        assertEquals(1, plants.size());
        assertEquals("Rose", plants.get(0).getCommonName());
    }

    @Test
    public void testSearchBySlug() {
        ArrayList<Plant> plants = plantTreeManager.search(PlantTreeManager.PlantInfoType.SLUG, "sunflower");
        assertEquals(1, plants.size());
        assertEquals("Sunflower", plants.get(0).getCommonName());
    }

    @Test
    public void testSearchByScientificName() {
        ArrayList<Plant> plants = plantTreeManager.search(PlantTreeManager.PlantInfoType.SCIENTIFIC_NAME, "Lilium");
        assertEquals(1, plants.size());
        assertEquals("Lily", plants.get(0).getCommonName());
    }

    @Test
    public void testSearchByGenus() {
        ArrayList<Plant> plants = plantTreeManager.search(PlantTreeManager.PlantInfoType.GENUS, "Helianthus");
        assertEquals(1, plants.size());
        assertEquals("Sunflower", plants.get(0).getCommonName());
    }

    @Test
    public void testSearchByFamily() {
        ArrayList<Plant> plants = plantTreeManager.search(PlantTreeManager.PlantInfoType.FAMILY, "Liliaceae");
        assertEquals(2, plants.size());
        assertTrue(plants.stream().anyMatch(plant -> plant.getCommonName().equals("Lily")));
        assertTrue(plants.stream().anyMatch(plant -> plant.getCommonName().equals("Tulip")));
    }

    @Test
    public void testSingleton() {
        PlantTreeManager anotherInstance = PlantTreeManager.getInstance(plantTree);
        assertSame(plantTreeManager, anotherInstance);
    }

    @Test
    public void testGetTypeByString() throws Exception {
        assertEquals(PlantTreeManager.PlantInfoType.COMMON_NAME, plantTreeManager.getTypeByString("COMMON_NAME"));
        assertEquals(PlantTreeManager.PlantInfoType.SCIENTIFIC_NAME, plantTreeManager.getTypeByString("SCIENTIFIC_NAME"));
    }

    @Test(expected = Exception.class)
    public void testGetTypeByStringInvalid() throws Exception {
        plantTreeManager.getTypeByString("INVALID_TYPE");
    }


}
