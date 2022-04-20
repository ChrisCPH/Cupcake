package dat.cupcake.model.entities;

import java.util.Objects;

public class Topping {
    private int id;
    private int price;
    private String toppingName;
    
    public Topping(int id, int price, String toppingName) {
        this.id = id;
        this.price = price;
        this.toppingName = toppingName;
    }
    
    public Topping(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public int getPrice() {
        return price;
    }
    
    public String getName() {
        return toppingName;
    }
    
    @Override
    public String toString() {
        return "id=" + id +
                ", price=" + price +
                ", toppingName=" + toppingName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topping topping = (Topping) o;
        return id == topping.id && price == topping.price && toppingName.equals(topping.toppingName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, price, toppingName);
    }
}
