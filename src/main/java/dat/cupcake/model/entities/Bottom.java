package dat.cupcake.model.entities;

import java.util.Objects;

public class Bottom {
    private int id;
    private int price;
    private String bottomName;
    
    public Bottom(int id, int price, String bottomName) {
        this.id = id;
        this.price = price;
        this.bottomName = bottomName;
    }
    
    public Bottom(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public int getPrice() {
        return price;
    }
    
    public String getName() {
        return bottomName;
    }
    
    @Override
    public String toString() {
        return "Bottom{" +
                "id=" + id +
                ", price=" + price +
                ", bottomName='" + bottomName + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bottom bottom = (Bottom) o;
        return id == bottom.id && price == bottom.price && bottomName.equals(bottom.bottomName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, price, bottomName);
    }
}
