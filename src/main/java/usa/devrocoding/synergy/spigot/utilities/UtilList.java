package usa.devrocoding.synergy.spigot.utilities;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import java.util.List;

public class UtilList {

  public static <T> LinkedList<T> reverseLinkedList(LinkedList<T> list) {
    LinkedList<T> revLinkedList = Lists.newLinkedList();
    for (int i = list.size() - 1; i >= 0; i--) {

      // Append the elements in reverse order
      revLinkedList.add(list.get(i));
    }
    // Return the reversed arraylist
    return revLinkedList;
  }

}
