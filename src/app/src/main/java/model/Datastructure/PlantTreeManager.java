package model.Datastructure;

import java.util.ArrayList;

/**
 * @author Haochen Gong
 * @uid u7776634
 * @description: PlantTree management methodology class
 **/
public class PlantTreeManager implements TreeManager<Plant>{

    private final RBTree<Plant> plantRBTree;

    public enum PlantInfoType {
        ID, COMMON_NAME, SLUG, SCIENTIFIC_NAME, GENUS, FAMILY;
    }

    private static PlantTreeManager instance;
    private PlantTreeManager(RBTree<Plant> plantRBTree) {
        if (instance != null) {
            throw new IllegalStateException("Instance already created");
        }
        this.plantRBTree = plantRBTree;
    }
    public static synchronized PlantTreeManager getInstance(RBTree<Plant> plantRBTree) {
        if (instance == null) {
            instance = new PlantTreeManager(plantRBTree);
        }
        return instance;
    }
    public static synchronized PlantTreeManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Instance not created. Call getInstance(RBTree<Plant>) first.");
        }
        return instance;
    }
    @Override
    public void insert(int id, Plant plant) {
        plantRBTree.insert(id,plant);
    }

    @Override
    public void delete(int id) {
        plantRBTree.delete(id);
    }

    // call this method to search
    public <T> ArrayList<Plant> search (PlantInfoType infoType, T info) {

        ArrayList<Plant> plants = new ArrayList<>();

        if (infoType == PlantInfoType.ID) {
            try {
                RBTreeNode<Plant> plant = plantRBTree.search(Integer.parseInt((String) info));
                if (plant != null) {
                    plants.add(plant.getValue());
                }
            } catch (NumberFormatException e) {
                return plants;
            }
        } else {
            search(plantRBTree.root, infoType, info, plants);
        }

        return plants;
    }

    // actual iteration method
    private  <T> void search(RBTreeNode<Plant> node, PlantInfoType infoType, T info, ArrayList<Plant> plants) {
        // If the current node is null, it means that the child of the leaf node has been reached, and it is returned directly
        if (node == null) {
            return;
        }

        // If the value of the current node is equal to the searched value, add to the result list
        switch (infoType) {
            case COMMON_NAME:
                if (node.getValue().getCommonName().toLowerCase().contains(String.valueOf(info).toLowerCase())) {
                    plants.add(node.getValue());
                }
                break;
            case SLUG:
                if (node.getValue().getSlug().toLowerCase().contains(String.valueOf(info).toLowerCase())) {
                    plants.add(node.getValue());
                }
                break;
            case SCIENTIFIC_NAME:
                if (node.getValue().getScientificName().toLowerCase().contains(String.valueOf(info).toLowerCase())) {
                    plants.add(node.getValue());
                }
                break;
            case GENUS:
                if (node.getValue().getGenus().toLowerCase().contains(String.valueOf(info).toLowerCase())) {
                    plants.add(node.getValue());
                }
                break;
            case FAMILY:
                if (node.getValue().getFamily().toLowerCase().contains(String.valueOf(info).toLowerCase())) {
                    plants.add(node.getValue());
                }
                break;
        }

        // Continue to search recursively in the left subtree
        search(node.getLeft(), infoType, info, plants);
        // Continue to search recursively in the right subtree
        search(node.getRight(), infoType, info, plants);
    }

    public PlantInfoType getTypeByString(String type) throws Exception {
        switch (type.toUpperCase()) {
            case "ID":
                return PlantInfoType.ID;
            case "COMMON_NAME":
                return PlantInfoType.COMMON_NAME;
            case "SLUG":
                return PlantInfoType.SLUG;
            case "SCIENTIFIC_NAME":
                return PlantInfoType.SCIENTIFIC_NAME;
            case "GENUS":
                return PlantInfoType.GENUS;
            case "FAMILY":
                return PlantInfoType.FAMILY;
            default:
                throw new Exception("Invalid Plant Type");
        }
    }
}
