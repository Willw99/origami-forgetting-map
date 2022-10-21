package forgetting.map;

import forgetting.map.containers.KeyContainer;
import forgetting.map.containers.ObjectContainer;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class ForgettingMap<Key, Object> {

  private final List<KeyContainer<Key>> sortedKeys;
  private final Map<Key, ObjectContainer<Key, Object>> forgettingMap;
  private final Integer maxSize;

  public ForgettingMap(Integer numOfAssociations) {
    this.sortedKeys = new ArrayList<>();
    this.forgettingMap = new HashMap<>();
    this.maxSize = numOfAssociations;
  }

  private boolean containsSortedKey(Key key){
      for (var sortedKey : sortedKeys) {
          if (sortedKey.getKey().equals(key)){
              return true;
          }
      }
      return false;
  }

  private synchronized void reSynchroniseMap() {
      /*
      In the event of disparity between the data structures, the structures are re-synced together.
      This is a basic implementation and works on the assumption disparity would only occur on incomplete removal of a key.

      Re-syncing the data structure is a slow process based on this implementation but should only occur in event of failure
      so impact is minimal to performance.
       */
      if (sortedKeys.size() > forgettingMap.keySet().size()) {
       sortedKeys.forEach(keyContainer -> {
            if(!forgettingMap.containsKey(keyContainer.getKey())){
                sortedKeys.remove(keyContainer);
            }
        });
    }else if(sortedKeys.size() < forgettingMap.keySet().size()){
        forgettingMap.keySet().forEach(key -> {
            if(!containsSortedKey(key)){
                forgettingMap.remove(key);
            }
        });
    }
      /*
      Sorts the keys by uses, in the event of identical uses the date of creation is used so the oldest record is removed
      if uses are identical.
       */
    sortedKeys.sort(
            (o1, o2) -> o2.getUses().equals(o1.getUses())
                    ? o1.getCreatedAt().compareTo(o2.getCreatedAt())
                    : o1.getUses().compareTo(o2.getUses()));
  }

  /*
  Adding occurs through two flows:
   - the first is if there's space in the forgetting map a record is simply added.
   - the second is overflow mechanism for when the map is full, the least used record is removed in favour of the new
     record.
   */
  public synchronized void add(Key key, Object object) {
    if (forgettingMap.keySet().size() > maxSize) {
      var keyToRemove = sortedKeys.remove(0).getKey();
      forgettingMap.remove(keyToRemove);
    }
    var keyContainer = new KeyContainer<>(key);
    sortedKeys.add(keyContainer);
    forgettingMap.put(keyContainer.getKey(), new ObjectContainer<>(object, keyContainer));
    reSynchroniseMap();
  }

  /*
  Find method gets the object from the map and increments the uses within the key container.
   */
  public Object find(Key key) {
    if (forgettingMap.containsKey(key)) {
      var objectContainer = forgettingMap.get(key);
      objectContainer.getKeyReference().incrementUses();
      reSynchroniseMap();
      return objectContainer.getObject();
    } else {
      return null;
    }
  }

  @Override
  public String toString() {
    var SB = new StringBuilder();
      synchronized (this) {
          sortedKeys.forEach(
                  keyContainer -> {
                      SB.append(
                              String.format(
                                      "KEY=%s\nOBJECT=%s\n", keyContainer.toString(), forgettingMap.get(keyContainer.getKey()).toString()));
                  });
      };
    return SB.toString();
  }
}
