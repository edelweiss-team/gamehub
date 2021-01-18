package model.personalization;

import com.opencsv.CSVWriter;
import controller.HomeServlet;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.dao.ConPool;
import org.jetbrains.annotations.NotNull;

/**
 * This class's purpose is to write, on a buffer csv file, new user samples for the machine
 * learning model used to segment a classify the users.
 */
public class DatasetSampleDAO {
    public static final @NotNull String BUFFER =
            HomeServlet.EXECUTION_PATH + "/WEB-INF/personalization/buffer.csv";

    /**
     * This method buffers the user sample corresponding to the given username.
     * It is synchronized because no more than one user at time may access to the buffer file.
     *
     * @param username the username corresponding to the user sample to buffer.
     *                 It has to be non-null.
     */
    public synchronized void doBufferByUsername(@NotNull String username) {
        try {
            Connection cn = ConPool.getConnection();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(BUFFER)); //creiamo il CSVWriter
            PreparedStatement ps = cn.prepareStatement(
                    "SELECT * from game_hub_db.datasetsample where Username=?;"
            );
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            csvWriter.writeAll(rs, true); //scriviamo sul csv i nuovi dati
            csvWriter.flush();
            csvWriter.close();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
