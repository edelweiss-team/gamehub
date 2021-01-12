package model.dao;

import java.util.ArrayList;
import model.bean.*;


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
        User u5 = new User("roberto","password", "Roberto", "Esposito",
                "Via Roma", "Napoli", "IT", "2020-11-16", "roberto@gmail.com",
                'M', "3313456754");
        User u6 = new User("andrea99_","password", "Andrea", "Terlizzi",
                "Via Roma", "Caserta", "IT", "2020-11-16", "andrea@gmail.com",
                'M', "3313456754");
        User u7 = new User("domenicoo","password", "Domenico", "Dalessandro",
                "Via Roma", "Napoli", "IT", "2020-11-16", "domenico@gmail.com",
                'M', "3313456754");
        User u8 = new User("francescoo","password", "Francesco", "Rastelli",
                "Via Roma", "Napoli", "IT", "2020-11-16", "francescor@gmail.com",
                'M', "3313456754");
        User u9 = new User("francescom", "password", "Francesco", "Mattina",
                "Via Roma", "Napoli", "IT", "2020-11-16", "francescom@gmail.com",
                'M', "3313456754");

        UserDAO ud = new UserDAO();

        ud.doSave(u1);
        ud.doSave(u2);
        ud.doSave(u3);
        ud.doSave(u4);
        ud.doSave(u5);
        ud.doSave(u6);
        ud.doSave(u7);
        ud.doSave(u8);
        ud.doSave(u9);


        Tag t1 = new Tag("Sparatutto");
        Tag t2 = new Tag("Azione");
        Tag t3 = new Tag("Avventura");
        Tag t4 = new Tag("Rompicapo");
        Tag t5 = new Tag("Casual");
        Tag t6 = new Tag("Design & Illustrations");
        Tag t7 = new Tag("Early Access");
        Tag t8 = new Tag("Joystick");
        Tag t9 = new Tag("EcoFriendly");
        Tag t10 = new Tag("Yellow");
        Tag t11 = new Tag("Red");
        Tag t12 = new Tag("Blue");
        Tag t13 = new Tag("Sport");
        Tag t14 = new Tag("Accounting");
        Tag t15 = new Tag("Animation & Modeling");
        Tag t16 = new Tag("Audio Production");

        TagDAO td = new TagDAO();

        td.doSave(t1);
        td.doSave(t2);
        td.doSave(t3);
        td.doSave(t4);
        td.doSave(t5);
        td.doSave(t6);
        td.doSave(t7);
        td.doSave(t8);
        td.doSave(t9);
        td.doSave(t10);
        td.doSave(t11);
        td.doSave(t12);
        td.doSave(t13);
        td.doSave(t14);
        td.doSave(t15);
        td.doSave(t16);


        Category c1 = new Category("game", "DescrizioneCategory1",
                "immagine1");
        Category c2 = new Category("dlc", "DescrizioneCategory2",
                "immagine2");
        Category c3 = new Category("video", "DescrizioneCategory3",
                "immagine3");
        Category c4 = new Category("categoria", "DescrizioneCategory4",
                "immagine4");
        Category c5 = new Category("Action Figure", "description", "");
        Category c6 = new Category("Cuscini", "description", "");
        Category c7 = new Category("Console", "description", "");
        Category c8 = new Category("Videogiochi", "description", "");
        Category c9 = new Category("Accessori Console", "description", "");
        Category c10 = new Category("Sparatutto", "description", "");
        Category c11 = new Category("Action", "description", "");
        Category c12 = new Category("Rompicapo", "description", "");
        Category c13 = new Category("Avventura", "description", "");
        Category c14 = new Category("Sport", "description", "");

        CategoryDAO cd = new CategoryDAO();

        cd.doSave(c1);
        cd.doSave(c2);
        cd.doSave(c3);
        cd.doSave(c4);
        cd.doSave(c5);
        cd.doSave(c6);
        cd.doSave(c7);
        cd.doSave(c8);
        cd.doSave(c9);
        cd.doSave(c10);
        cd.doSave(c11);
        cd.doSave(c12);
        cd.doSave(c13);
        cd.doSave(c14);


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
        DigitalProduct d7 = new DigitalProduct(1, "Call of Duty", 49.99,
                "Description Call of Duty", "immagine1", new ArrayList<>(),
                new ArrayList<>(), 1, "ps4", "2020-12-12", 18,
                "Activision", "Activision");
        DigitalProduct d8 = new DigitalProduct(1, "Fifa21", 70.00,
                "Description Fifa21", "immagine1", new ArrayList<>(),
                new ArrayList<>(), 1, "xbox", "2021-07-30", 3,
                "EA Sports", "EA Sports");
        DigitalProduct d9 = new DigitalProduct(1, "Rocket League", 10.50,
                "Descrizione Rocket League", "immagine1", new ArrayList<>(),
                new ArrayList<>(), 1, "pc", "2020-12-12", 3,
                "AB Software Production", "AB Software Production");
        DigitalProduct d10 = new DigitalProduct(1, "League of Legends PRO", 45.55,
                "Descrizione League of Legends", "immagine1", new ArrayList<>(),
                new ArrayList<>(), 1, "pc", "2008-11-09", 13,
                "Riot Games", "Riot Games");
        DigitalProduct d5 = new DigitalProduct(1, "Watch Dogs 2", 55.4,
                "Descrizione Watch Dogs 2", "immagine1", new ArrayList<>(),
                new ArrayList<>(), 1, "xbox", "2019-12-12", 18,
                "Xbox Ultimate Production", "Xbox Ultimate Production");
        DigitalProduct d6 = new DigitalProduct(1, "Sleeping Dogs", 21.1,
                "Descrizione Sleeping Dogs", "immagine1", new ArrayList<>(),
                new ArrayList<>(), 1, "ps5", "2021-12-12", 18,
                "AB Software Production", "AB Software Production");


        d1.addCategory(c1);
        d2.addCategory(c2);
        d3.addCategory(c3);
        d4.addCategory(c4);
        d7.addCategory(c1);
        d8.addCategory(c2);
        d8.addCategory(c3);
        d9.addCategory(c3);
        d10.addCategory(c4);
        d10.addCategory(c5);
        d5.addCategory(c5);
        d6.addCategory(c6);


        d1.addTag(t1);
        d2.addTag(t2);
        d3.addTag(t3);
        d4.addTag(t4);
        d1.addTag(t13);
        d1.addTag(t15);
        d1.addTag(t16);
        d1.addTag(t6);
        d2.addTag(t14);
        d2.addTag(t15);
        d2.addTag(t8);
        d2.addTag(t12);
        d3.addTag(t15);
        d3.addTag(t6);
        d4.addTag(t16);
        d5.addTag(t6);
        d5.addTag(t7);
        d5.addTag(t8);
        d5.addTag(t9);
        d6.addTag(t6);
        d6.addTag(t13);
        d6.addTag(t14);
        d6.addTag(t15);


        DigitalProductDAO dp = new DigitalProductDAO();

        dp.doSave(d1);
        dp.doSave(d2);
        dp.doSave(d3);
        dp.doSave(d4);
        dp.doSave(d5);
        dp.doSave(d6);
        dp.doSave(d7);
        dp.doSave(d8);
        dp.doSave(d9);
        dp.doSave(d10);



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
        PhysicalProduct p5 = new PhysicalProduct(1,"Nintendo", 42.1,
                "Descrizione Nintendo", "imamgine1", new ArrayList<>(),
                new ArrayList<>(), 1, "12x12x12", 13);
        PhysicalProduct p6 = new PhysicalProduct(1,"Pippo Baudo", 58,
                "Descrizione Pippo Baudo", "imamgine1", new ArrayList<>(),
                new ArrayList<>(), 1, "180x12x14", 56);
        PhysicalProduct p7 = new PhysicalProduct(1,"Action Figure Draven", 12.1,
                "Descrizione Draven", "imamgine1", new ArrayList<>(),
                new ArrayList<>(), 1, "12x12x12", 21.2);
        PhysicalProduct p8 = new PhysicalProduct(1,"Playstation 5", 120.10,
                "Descrizione Playstation 5", "imamgine1", new ArrayList<>(),
                new ArrayList<>(), 1, "90x45x90", 45.4);
        PhysicalProduct p9 = new PhysicalProduct(1,"Xbox Pro", 300,
                "Descrizione Xbox Pro", "imamgine1", new ArrayList<>(),
                new ArrayList<>(), 1, "90x12x19", 50);;
        PhysicalProduct p10 = new PhysicalProduct(1,"Wii U", 90,
                "Descrizione Wii", "imamgine1", new ArrayList<>(),
                new ArrayList<>(), 1, "56x29x44", 56.1);


        p1.addCategory(c1);
        p2.addCategory(c2);
        p3.addCategory(c3);
        p4.addCategory(c4);

        p1.addTag(t7);
        p1.addTag(t13);
        p1.addTag(t5);
        p2.addTag(t8);
        p2.addTag(t14);
        p2.addTag(t14);
        p3.addTag(t10);
        p3.addTag(t12);
        p4.addTag(t13);
        p5.addTag(t6);
        p4.addTag(t8);
        p5.addTag(t13);
        p6.addTag(t12);

        PhysicalProductDAO pd = new PhysicalProductDAO();

        pd.doSave(p1);
        pd.doSave(p2);
        pd.doSave(p3);
        pd.doSave(p4);
        pd.doSave(p5);
        pd.doSave(p6);
        pd.doSave(p7);
        pd.doSave(p8);
        pd.doSave(p9);
        pd.doSave(p10);



        Order o1 = new Order(1, u1, null, "2000-10-10");
        Order o2 = new Order(2, u1, null, "2020-2-2");
        Order o6 = new Order(6, u5, null, "2020-12-20");
        Order o7 = new Order(7, u6, null, "2020-12-20");
        Order o3 = new Order(8, u7, null, "2020-12-20");
        Order o4 = new Order(9, u8, null, "2020-12-20");
        Order o5 = new Order(10, u9, null, "2020-12-20");


        o1.addProduct(p1, 2);
        o1.addProduct(d1, 3);
        o2.addProduct(d2, 1);
        o2.addProduct(p3, 1);
        o2.addProduct(d4, 1);
        o6.addProduct(d8, 1);
        o7.addProduct(p8, 1);
        o6.addProduct(d9, 4);
        o7.addProduct(p4, 2);
        o7.addProduct(p2, 1);
        o7.addProduct(p6, 1);
        o7.addProduct(d2, 1);
        o3.addProduct(p6, 1);
        o3.addProduct(d1, 1);
        o3.addProduct(d5, 1);
        o4.addProduct(d2, 1);
        o5.addProduct(d6, 3);


        OrderDAO od = new OrderDAO();

        od.doSave(o1);
        od.doSave(o2);
        od.doSave(o6);
        od.doSave(o7);
        od.doSave(o3);
        od.doSave(o4);
        od.doSave(o5);


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



