package model.dao;

import model.bean.*;

import java.util.ArrayList;

public class DatabaseCleaner {
    public static void main(String[] args) {
        User u1 = new User("username1","password", "Name", "Surname",
                "Address", "City", "Country", "2020-11-16", "Mail1",
                'M', "1111111111");
        User u2 = new User("username2","password", "Name", "Surname",
                "Address", "City", "Country", "2020-11-16", "Mail2",
                'M', "1111111111");
        User u3 = new User("username3","password", "Name", "Surname",
                "Address", "City", "Country", "2020-11-16", "Mail3",
                'M', "1111111111");
        User u4 = new User("username4","password", "Name", "Surname",
                "Address", "City", "Country", "2020-11-16", "Mail4",
                'M', "1111111111");

        UserDAO ud = new UserDAO();

        ud.doDeleteFromUsername(u1.getUsername());
        ud.doDeleteFromUsername(u2.getUsername());
        ud.doDeleteFromUsername(u3.getUsername());
        ud.doDeleteFromUsername(u4.getUsername());

        Tag t1 = new Tag("Sparatutto");
        Tag t2 = new Tag("Azione");
        Tag t3 = new Tag("Avventura");
        Tag t4 = new Tag("Rompicapo");

        TagDAO td = new TagDAO();

        td.doDelete(t1.getName());
        td.doDelete(t2.getName());
        td.doDelete(t3.getName());
        td.doDelete(t4.getName());

        Category c1 = new Category("game", "DescrizioneCategory1",
                "immagine1");
        Category c2 = new Category("dlc", "DescrizioneCategory2",
                "immagine2");
        Category c3 = new Category("video", "DescrizioneCategory3",
                "immagine3");
        Category c4 = new Category("categoria", "DescrizioneCategory4",
                "immagine4");

        CategoryDAO cd = new CategoryDAO();

        cd.doDeleteByName(c1.getName());
        cd.doDeleteByName(c2.getName());
        cd.doDeleteByName(c3.getName());
        cd.doDeleteByName(c4.getName());


        DigitalProduct d1 = new DigitalProduct(1, "GiocoDigitale1", 12.3,
                "DescrizioneDigitale1", "immagine1", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "1999-12-12",
                10, "SoftwareHouse", "publisher");
        DigitalProduct d2 = new DigitalProduct(2, "GiocoDigitale2", 12.3,
                "DescrizioneDigitale2", "immagine2", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "1999-12-12",
                10, "SoftwareHouse", "publisher");
        DigitalProduct d3 = new DigitalProduct(3, "GiocoDigitale3", 12.3,
                "DescrizioneDigitale3", "immagine3", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "1999-12-12",
                10, "SoftwareHouse", "publisher");
        DigitalProduct d4 = new DigitalProduct(4, "GiocoDigitale4", 12.3,
                "DescrizioneDigitale4", "immagine4", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "1999-12-12",
                10, "SoftwareHouse", "publisher");


        DigitalProductDAO dp = new DigitalProductDAO();
        //  -- NON FUNZIONA -- PERCHE' L'ID
        // DEL PRODOTTO E' DIVERSO DA QUELLO SALVATO
        // NEL DB
        dp.doDelete(d1.getId());
        dp.doDelete(d2.getId());
        dp.doDelete(d3.getId());
        dp.doDelete(d4.getId());

        PhysicalProduct p1 = new PhysicalProduct(1,"GiocoFisico1", 12.1,
                "DescrizioneFisico1", "imamgine1", new ArrayList<>(),
                new ArrayList<>(), 1, "12x12x12", 12.1);
        PhysicalProduct p2 = new PhysicalProduct(2,"GiocoFisico2", 12.1,
                "DescrizioneFisico2", "imamgine2", new ArrayList<>(),
                new ArrayList<>(), 1, "12x12x12", 12.1);
        PhysicalProduct p3 = new PhysicalProduct(3,"GiocoFisico3", 12.1,
                "DescrizioneFisico3", "imamgine3", new ArrayList<>(),
                new ArrayList<>(), 1, "12x12x12", 12.1);
        PhysicalProduct p4 = new PhysicalProduct(4,"GiocoFisico4", 12.1,
                "DescrizioneFisico4", "imamgine4", new ArrayList<>(),
                new ArrayList<>(), 1, "12x12x12", 12.1);


        PhysicalProductDAO pd = new PhysicalProductDAO();

        //  -- NON FUNZIONA -- PERCHE' L'ID
        // DEL PRODOTTO E' DIVERSO DA QUELLO SALVATO
        // NEL DB
        pd.doDelete(p1.getId());
        pd.doDelete(p2.getId());
        pd.doDelete(p3.getId());
        pd.doDelete(p4.getId());


        Order o1 = new Order(1, u1, null, "2000-10-10");
        Order o2 = new Order(2, u1, null, "2020-2-2");


        OrderDAO od = new OrderDAO();

        od.doDelete(o1.getId());
        od.doDelete(o2.getId());

        Operator o = new Operator(u2, "2020-11-12", "Ciao");

        OperatorDAO opd = new OperatorDAO();

        opd.doDeleteByUsername(o.getUsername());
    }

}
