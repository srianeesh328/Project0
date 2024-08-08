package org.example.DAOs;
import org.example.models.Item;
import org.example.models.User;
import java.util.*;

public interface ItemDAOInterface {

    Item getItemById(int id);

    public List<Item> getItemByUserId(int userid);

    public List<Item> getAllItems();

    String updateItemDescription(int itemId, String description);

    Item insertItem(Item a);

    public Integer deleteItem(Integer item_id);

}
