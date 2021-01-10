package model.dao;

import model.personalization.DatasetSampleDAO;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class DatasetSampleDAOTest {

    @NotNull DatasetSampleDAO dsd = new DatasetSampleDAO();

    @Test
    void doBufferAllOk() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(DatasetSampleDAO.BUFFER);
        writer.print("");
        writer.close();
        dsd.doBufferAll(0, 1);
        assertTrue(true);
    }

    @Test
    void doBufferAllNotOk() {
        assertThrows(RuntimeException.class, () -> dsd.doBufferAll(-1, -100));
    }
}