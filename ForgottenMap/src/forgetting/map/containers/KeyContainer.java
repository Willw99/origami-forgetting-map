package forgetting.map.containers;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

/*
Container for the forgetting map key, the key was placed in a container to allow for additional information related to
the environment of the key; namely the time/date of creation and the number of times the keys been used.
 */
public class KeyContainer<Key> {
  private final LocalDateTime createdAt;
  private Integer uses;
  private final Key key;

  public KeyContainer(Key key) {
    this.key = key;
    uses = 0;
    createdAt = LocalDateTime.now(Clock.systemUTC());
  }

  /*
  This method is synchronised to ensure concurrent access of the map maintains an accurate uses count.
   */
  public synchronized void incrementUses() {
    this.uses++;
  }

  public Integer getUses() {
    return uses;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Key getKey() {
    return key;
  }

  /*
  Equating keyContainers is done using the key.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    KeyContainer<?> that = (KeyContainer<?>) o;
    return key.equals(that.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key);
  }

  @Override
  public String toString() {
    return "KeyContainer{" + "createdAt=" + createdAt + ", uses=" + uses + ", key=" + key + '}';
  }
}
