package Stubs;
import DomainLayer.Product;
import DomainLayer.Roles.StoreManger;
import DomainLayer.Roles.StoreOwner;
import DomainLayer.System;
import DomainLayer.User.Subscriber;
import DomainLayer.Store.Store;

public class Esimulation {
    public static void Init(){
        
        Subscriber tamer = new Subscriber("tamer","tamer");
        Subscriber ahmad = new Subscriber("ahmad","ahmad");
        Subscriber mahmoud = new Subscriber("mahmoud","mahmoud");
        Subscriber noor = new Subscriber("noor","noor");
        Subscriber yakir = new Subscriber("yakir","yakir");
        Subscriber adam = new Subscriber("adam","adam");
        Subscriber user = new Subscriber("user","user");

        System.getSystem().getUser_list().add(tamer);
        System.getSystem().getUser_list().add(ahmad);
        System.getSystem().getUser_list().add(mahmoud);
        System.getSystem().getUser_list().add(yakir);
        System.getSystem().getUser_list().add(noor);
        System.getSystem().getUser_list().add(adam);
        System.getSystem().getUser_list().add(user);


        System.getSystem().getStore_list().add(new Store("Japanika"));
        System.getSystem().getStore_list().add(new Store("BicycleStore"));
        System.getSystem().getStore_list().add(new Store("Toyota"));
        System.getSystem().getStore_list().add(new Store("ShuferSal"));
        System.getSystem().getStore_list().add(new Store("Black"));
        System.getSystem().getStore_list().add(new Store("BBB"));
        System.getSystem().getStore_list().add(new Store("Agadir"));
        System.getSystem().getStore_list().add(new Store("BBB"));


        Store Japanika = System.getSystem().get_store("Japanika");
        Store BicycleStore = System.getSystem().get_store("BicycleStore");
        Store Toyota = System.getSystem().get_store("Toyota");
        Store ShuferSal = System.getSystem().get_store("ShuferSal");
        Store Black = System.getSystem().get_store("Black");
        Store BBB = System.getSystem().get_store("BBB");
        Store Agadir = System.getSystem().get_store("Agadir");


        Product makaw = new Product("Makaw",50,10,Japanika);
        Product crispy = new Product("Crispy",28,9,Japanika);
        Product sushi = new Product("sushi",33,13,Japanika);
        Product kombinatsya = new Product("kombinatsya",99,13,Japanika);
        Product bisli = new Product("bisli",16,122,ShuferSal);
        Product milk = new Product("milk",23,54,ShuferSal);
        Product yolo = new Product("yolo",33,89,ShuferSal);
        Product cola = new Product("cola",33,89,ShuferSal);
        Product Camry = new Product("Camry",30000,20,Toyota);
        Product Yaris = new Product("Yaris",20000,19,Toyota);
        Product Prius = new Product("Prius",44000,15,Toyota);
        Product Corolla= new Product("Corolla",50000,15,Toyota);
        Product Hybrid = new Product("Hybrid",2500,15,BicycleStore);
        Product Mountain = new Product("Mountain",5400,15,BicycleStore);
        Product Street = new Product("Street",1000,15,BicycleStore);
        Product race = new Product("race",7000,15,BicycleStore);
        Product g150 = new Product("150g",24,123,Black);
        Product g200 = new Product("200g",28,111,Black);
        Product g300 = new Product("300g",35,123,Black);
        Product g500 = new Product("500g",35,123,Black);
        Product B = new Product("B",44,322,BBB);
        Product BB = new Product("BB",44,322,BBB);
        Product BBB1 = new Product("BBB",44,322,BBB);
        Product BBBB = new Product("BBBB",44,322,BBB);
        Product Chicken= new Product("Chicken",44,322,Agadir);
        Product Diana= new Product("Diana",44,322,Agadir);
        Product Jounior = new Product("Jounior",44,322,Agadir);
        Product maxi = new Product("maxi",44,322,Agadir);

        Japanika.getProduct_list().add(makaw);
        Japanika.getProduct_list().add(crispy);
        Japanika.getProduct_list().add(sushi);
        Japanika.getProduct_list().add(kombinatsya);
        ShuferSal.getProduct_list().add(bisli);
        ShuferSal.getProduct_list().add(milk);
        ShuferSal.getProduct_list().add(yolo);
        ShuferSal.getProduct_list().add(cola);
        BicycleStore.getProduct_list().add(Hybrid);
        BicycleStore.getProduct_list().add(Mountain);
        BicycleStore.getProduct_list().add(Street);
        BicycleStore.getProduct_list().add(race);
        Toyota.getProduct_list().add(Camry);
        Toyota.getProduct_list().add(Corolla);
        Toyota.getProduct_list().add(Yaris);
        Toyota.getProduct_list().add(Prius);
        Black.getProduct_list().add(g150);
        Black.getProduct_list().add(g200);
        Black.getProduct_list().add(g300);
        Black.getProduct_list().add(g500);
        Black.getProduct_list().add(BB);
        Black.getProduct_list().add(B);
        Black.getProduct_list().add(BBB1);
        Black.getProduct_list().add(BBBB);
        Black.getProduct_list().add(maxi);
        Black.getProduct_list().add(Diana);
        Black.getProduct_list().add(Jounior);
        Black.getProduct_list().add(Chicken);


        Japanika.getRoles().add(new StoreOwner(tamer,Japanika));
        BicycleStore.getRoles().add(new StoreOwner(ahmad,BicycleStore));
        Toyota.getRoles().add(new StoreOwner(noor,Toyota));
        BicycleStore.getRoles().add(new StoreOwner(mahmoud,BicycleStore));
        Black.getRoles().add(new StoreOwner(yakir,Black));
        BBB.getRoles().add(new StoreOwner(adam,BBB));
        Agadir.getRoles().add(new StoreOwner(tamer,Agadir));

        Japanika.getRoles().add(new StoreManger(ahmad,Japanika));
        Toyota.getRoles().add(new StoreManger(noor,Toyota));


    }
}
