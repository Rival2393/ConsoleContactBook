package processing_classes;

import data_classes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dima on 31.08.2015.
 */
public class TemporaryStorage
{
    /*Indices*/
    private static final int FRIENDS = 0;
    private static final int COLLEAGUES = 1;
    private static final int MATES = 2;
    private static final int SERVICES = 3;
    private static final int OTHERS = 4;
    /*Storage*/
    private static ArrayList<HashMap<Integer, Contact>> storage = new ArrayList<HashMap<Integer, Contact>>();
    private static HashMap<Integer, Contact> friends = new HashMap<Integer, Contact>();
    private static HashMap<Integer, Contact> colleagues = new HashMap<Integer, Contact>();
    private static HashMap<Integer, Contact> mates = new HashMap<Integer, Contact>();
    private static HashMap<Integer, Contact> services = new HashMap<Integer, Contact>();
    private static HashMap<Integer, Contact> others = new HashMap<Integer, Contact>();

    static {
        storage.add(friends);
        storage.add(colleagues);
        storage.add(mates);
        storage.add(services);
        storage.add(others);
    }
    /*Methods*/

    public static void add(Contact contact)
    {
        if(contact instanceof Friend) storage.get(FRIENDS).put(contact.hashCode(), contact);
        else if (contact instanceof Colleague) storage.get(COLLEAGUES).put(contact.hashCode(), contact);
        else if (contact instanceof Mate) storage.get(MATES).put(contact.hashCode(), contact);
        else if (contact instanceof Service) storage.get(SERVICES).put(contact.hashCode(), contact);
        else if (contact instanceof Other) storage.get(OTHERS).put(contact.hashCode(), contact);

        else return;
    }

    public static void showFriends()
    {
        for(Map.Entry<Integer, Contact> pair : storage.get(FRIENDS).entrySet())
        {
            System.out.println(""+pair.getKey()+' '+pair.getValue());
        }
    }
    public static void showColleagues()
    {
        for(Map.Entry<Integer, Contact> pair : storage.get(COLLEAGUES).entrySet())
        {
            System.out.println(""+pair.getKey()+' '+pair.getValue());
        }
    }
    public static void showMates()
    {
        for(Map.Entry<Integer, Contact> pair : storage.get(MATES).entrySet())
        {
            System.out.println(""+pair.getKey()+' '+pair.getValue());
        }
    }
    public static void showServices()
    {
        for(Map.Entry<Integer, Contact> pair : storage.get(SERVICES).entrySet())
        {
            System.out.println(""+pair.getKey()+' '+pair.getValue());
        }
    }
    public static void showOthers()
    {
        for(Map.Entry<Integer, Contact> pair : storage.get(OTHERS).entrySet())
        {
            System.out.println(""+pair.getKey()+' '+pair.getValue());
        }
    }

    public static ArrayList<HashMap<Integer, Contact>> getStorage() {
        return storage;
    }
}
