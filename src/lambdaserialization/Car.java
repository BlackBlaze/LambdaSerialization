package lambdaserialization;

import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author Miguel
 */
public class Car implements Serializable{

    private long id;
    private String model;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Car other = (Car) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", model=" + model + '}';
    }

     private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException {
       
    }
}
