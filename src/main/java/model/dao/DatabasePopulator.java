package model.dao;

import model.bean.*;
import java.util.ArrayList;

public class DatabasePopulator {
    public static void main(String[] args) {

        User u1 = new User("username1","password", "Name", "Surname",
                "Address", "City", "Country", "2020-11-16", "Mail1",
                'M', "3313456754");
        User u2 = new User("username2","password", "Name", "Surname",
                "Address", "City", "Country", "2020-11-16", "Mail2",
                'M', "3313456754");
        User u3 = new User("username3","password", "Name", "Surname",
                "Address", "City", "Country", "2020-11-16", "Mail3",
                'M', "3313456754");
        User u4 = new User("username4","password", "Name", "Surname",
                "Address", "City", "Country", "2020-11-16", "Mail4",
                'M', "3313456754");

        UserDAO ud = new UserDAO();

        ud.doSave(u1);
        ud.doSave(u2);
        ud.doSave(u3);
        ud.doSave(u4);

        Tag t1 = new Tag("Sparatutto");
        Tag t2 = new Tag("Azione");
        Tag t3 = new Tag("Avventura");
        Tag t4 = new Tag("Rompicapo");

        TagDAO td = new TagDAO();
        td.doSave(t1);
        td.doSave(t2);
        td.doSave(t3);
        td.doSave(t4);


        Category c1 = new Category("game", "DescrizioneCategory1",
                "immagine1");
        Category c2 = new Category("dlc", "DescrizioneCategory2",
                "immagine2");
        Category c3 = new Category("video", "DescrizioneCategory3",
                "immagine3");
        Category c4 = new Category("categoria", "DescrizioneCategory4",
                "immagine4");

        CategoryDAO cd = new CategoryDAO();

        cd.doSave(c1);
        cd.doSave(c2);
        cd.doSave(c3);
        cd.doSave(c4);

        DigitalProduct d1 = new DigitalProduct(1, "GiocoDigitale1", 12.3,
                "DescrizioneDigitale1", "immagine1", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "1999-12-12", 10,
                "SoftwareHouse", "publisher");
        DigitalProduct d2 = new DigitalProduct(2, "GiocoDigitale2", 12.3,
                "DescrizioneDigitale2", "immagine2", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "1999-12-12", 10,
                "SoftwareHouse", "publisher");
        DigitalProduct d3 = new DigitalProduct(3, "GiocoDigitale3", 12.3,
                "DescrizioneDigitale3", "immagine3", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "1999-12-12", 10,
                "SoftwareHouse", "publisher");
        DigitalProduct d4 = new DigitalProduct(4, "GiocoDigitale4", 12.3,
                "DescrizioneDigitale4", "immagine4", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "1999-12-12", 10,
                "SoftwareHouse", "publisher");
        d1.addCategory(c1);
        d2.addCategory(c2);
        d3.addCategory(c3);
        d4.addCategory(c4);
        d1.addTag(t1);
        d2.addTag(t2);
        d3.addTag(t3);
        d4.addTag(t4);

        DigitalProductDAO dp = new DigitalProductDAO();

        dp.doSave(d1);
        dp.doSave(d2);
        dp.doSave(d3);
        dp.doSave(d4);

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
        p1.addCategory(c1);
        p2.addCategory(c2);
        p3.addCategory(c3);
        p4.addCategory(c4);

        PhysicalProductDAO pd = new PhysicalProductDAO();

        pd.doSave(p1);
        pd.doSave(p2);
        pd.doSave(p3);
        pd.doSave(p4);

        Order o1 = new Order(1, u1, null, "2000-10-10");
        Order o2 = new Order(2, u1, null, "2020-2-2");
        o1.addProduct(p1, 2);
        o1.addProduct(d1, 3);
        o2.addProduct(d2, 1);
        o2.addProduct(p3, 1);
        o2.addProduct(d4, 1);

        OrderDAO od = new OrderDAO();

        od.doSave(o1);
        od.doSave(o2);

        Operator o = new Operator(u2, "2020-11-12", "Ciao");

        OperatorDAO opd = new OperatorDAO();

        opd.doPromote(o);

        o1.setOperator(o);
        od.doUpdateOperator(o1.getId(), o.getUsername());

        Moderator m = new Moderator(u3, "2020-11-12");

        ModeratorDAO md = new ModeratorDAO();

        md.doSave(m);

        Admin a = new Admin(m, true);

        AdminDAO ad = new AdminDAO();

        ad.doSave(a);


    }
}

