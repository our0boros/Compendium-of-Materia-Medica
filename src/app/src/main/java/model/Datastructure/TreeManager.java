package model.Datastructure;

/**
 * @author Haochen Gong
 * @uid u7776634
 * @description: A generic interface to the method class that manages the tree
 * @using method: 1) Build the tree's manger with an existing tree (already defined in activity's attribute, there are already methods to initialize the data and build the tree):
 *                   xxxTreeManager treeManager = new xxxTreeManager(xxxTree);
 *                2) Search through the manager's search method to find a list of instances that match the criteria:
 *                   ArrayList<xxx> instances = xxxTreeManager.search(xxxTreeManager.xxxInfoType.info, the specific search information);
 *                3) Operate on the fetched arraylist to get the desired instances.
 *                  eg: according to the plant id to get a certain plant information:
 *                      PlantTreeManager plantTreeManager = new PlantTreeManager(plantTree);
 *                      ArrayList<Plant> plants = plantTreeManager.search(PlantTreeManager.PlantInfoType.ID,77116);
 *                      Plant plant = plants.get(0);
 **/
public interface TreeManager<T> {
    void insert(int key, T value);
    void delete(int key);
}
