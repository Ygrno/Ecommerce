package Stubs;
import DomainLayer.System;
import DomainLayer.User.Subscriber;

public class Esimulation {
    public static void Init(){
        System.getSystem().getUser_list().add(new Subscriber("tamer","tamer"));
        System.getSystem().getUser_list().add(new Subscriber("ahmad","ahmad"));
    }
}
