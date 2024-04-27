package model;

/**
 * @author: Haochen Gong
 * @description: plant树的管理方法类
 **/
public class PlantTreeManager implements TreeManager<Plant>{

    private final RBTree<Plant> plantRBTree;

    public PlantTreeManager(RBTree<Plant> plantRBTree) {
        this.plantRBTree = plantRBTree;
    }

    @Override
    public void insert(int id, Plant plant) {
        plantRBTree.insert(id,plant);
    }

    @Override
    public void delete(int id) {
        plantRBTree.delete(id);
    }

    public RBTreeNode<Plant> searchByPlantID(int id) {
        return plantRBTree.search(id);
    }

}
