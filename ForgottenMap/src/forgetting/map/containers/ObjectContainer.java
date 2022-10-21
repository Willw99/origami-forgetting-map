package forgetting.map.containers;

import java.util.Objects;

/*
Object container wraps the object with a reference to the key, this was to allow for efficient uses increment without the
need to maintain another data structure of keys or search the map incrementally to find the key to increment uses.
 */
public class ObjectContainer<Key, Object> {

  private final Object object;
  private final KeyContainer<Key> keyReference;

  public ObjectContainer(Object object, KeyContainer<Key> keyReference) {
    this.object = object;
    this.keyReference = keyReference;
  }

  public Object getObject() {
    return object;
  }

  public KeyContainer<Key> getKeyReference() {
    return keyReference;
  }

  /*
  Equating objectContainers is done using the object.
 */
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ObjectContainer<?, ?> that = (ObjectContainer<?, ?>) o;
    return object.equals(that.object);
  }

  @Override
  public int hashCode() {
    return Objects.hash(object);
  }

  @Override
  public String toString() {
    return "ObjectContainer{" +
            "object=" + object +
            '}';
  }
}
