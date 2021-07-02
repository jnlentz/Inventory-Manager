package inventoryapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;
    
    public Inventory(){
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }
    
    public void addPart(Part newPart) {
        this.allParts.add(newPart);
    }
    
    public Part lookupPart(int partId) {
        for (Part i : allParts) {
            if (i.getId() == partId) {
                return i;
            }
        }
        return null;
    }
    
    public ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> temp = FXCollections.observableArrayList();
        for (Part i : allParts) {
            if (i.getName().equalsIgnoreCase(partName)) {
                temp.add(i);
            }
        }
        return temp;
    }
    
    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    
    public void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }
        
    public void addProduct(Product newProduct) {
        this.allProducts.add(newProduct);
    }
    
    public Product lookupProduct(int productId) {
        return this.allProducts.get(productId - 1);
    }
    
    public ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> temp = FXCollections.observableArrayList();;
        for (Product i : allProducts) {
            if (i.getName().equalsIgnoreCase(productName)) {
                temp.add(i);
            }
        }
        return temp;
    }
    
    public void updateProduct(int index, Product selectedProduct) {
        this.allProducts.set(index, selectedProduct);
    }
    
    public void deleteProduct(Product selectedProduct) {
        this.allProducts.remove(selectedProduct);
    }
    
    public ObservableList<Part> getAllParts() {
        return this.allParts;
    }
    
    public ObservableList<Product> getAllProducts() {
        return this.allProducts;
    }
    
    public void printParts() {
        for (Part i : this.allParts) {
            System.out.println(i.getName());
            System.out.println(i.getPrice());
            System.out.println("------------------");
        }
    }
}
