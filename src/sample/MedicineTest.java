package sample;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MedicineTest {
    private Medicine medicine;

    @Before
    public void init() {
        medicine = new Medicine();
    }


    @Test
    public void setQuantity() {
        medicine.setQuantity(1);
        assertEquals(medicine.getQuantity(), 1);
    }


    @Test
    public void setName() {
        medicine.setName("Синупрет");
        assertEquals(medicine.getName(), "Синупрет");
    }
}