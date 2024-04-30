package model;

/**
 * @author: Haochen Gong
 * @description: 管理树的方法类的泛型接口
 * @using method: 1) 用已有的tree（activity的attribute中已经定义，已有方法初始化数据并构建树）构建tree的manger:
 *                      xxxTreeManager treemanager = new xxxTreeManager(xxxTree);
 *                2) 通过manager的search方法搜索到符合条件的节点列表：
 *                      ArrayList<RBTreeNode<xxx>> nodes = xxxTreeManager.search(xxxTreeManager.xxxInfoType.info, 具体的搜索信息);
 *                3) 对获取到的arraylist进行操作，获取到所需要的节点。
 *                4) 对节点执行getValue()，获取到相应的数据实例。
 *                eg: 根据植物id获取某种植物的信息：
 *                    PlantTreeManager plantTreeManager = new PlantTreeManager(plantTree);
 *                    ArrayList<RBTreeNode<Plant>> plants = plantTreeManager.search(PlantTreeManager.PlantInfoType.ID,77116);
 *                    RBTreeNode<Plant> node = plants.get(0);
 *                    Plant plant = node.getValue();
 **/
public interface TreeManager<T> {
    void insert(int key, T value);
    void delete(int key);
}
