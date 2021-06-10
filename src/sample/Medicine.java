package sample;

public class Medicine {

    private String add_medicine;
    private int quantity;

    public Medicine(String add_medicine, int quantity) {
        this.add_medicine = add_medicine;
        this.quantity = quantity;
    }

    public String getAdd_medicine() {
        return add_medicine;
    }

    public void setAdd_medicine(String add_medicine) {
        this.add_medicine = add_medicine;
    }



    public int getQuantity() {
        return quantity;
    }

    public int setQuantity(int quantity) {
        return quantity;
    }
}
