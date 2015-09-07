package data_classes;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import processing_classes.TemporaryStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dima on 30.08.2015.
 */
public abstract class Contact
{
    protected String firstName;
    protected String secondName;
    protected String phoneNumber;
    //private String address;


    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Contact(String firstName, String secondName, String phoneNumber) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
    }

    public Contact() {
    }

    public static class DataInput
    {
        private static BufferedReader reader;
        private static InputStream inputStream = System.in;
        private static final String DEFAULT = "none";


        public static void input()
        {
            String fName;
            String sName;
            String pNumber;

            try
            {

                while(true)
                {
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    while (true)
                    {
                        System.out.println("Enter name (Obligatory field) : ");
                        fName = reader.readLine();
                        System.in.skip(System.in.available());
                        if (fName.length() > 20) {
                            System.out.println("Name is too long");
                            continue;
                        }
                        else if (fName.equals("")) {
                            System.out.println("Name shouldn't be empty");
                            continue;
                        } else break;

                    }
                    while (true) {
                        System.out.println("Enter second name (optional): ");
                        sName = reader.readLine();
                        System.in.skip(System.in.available());
                        if (sName.length() > 20) {
                            System.out.println("Second name is too long");
                            continue;
                        }
                        if (sName.equals("")) {
                            sName = DEFAULT;
                            break;
                        }
                        break;
                    }
                    while (true) {
                        System.out.println("Enter phone number (format 063õõõõõõõ): ");
                        pNumber = reader.readLine();
                        System.in.skip(System.in.available());
                        pNumber = checkPhoneNumber(pNumber);
                        if (pNumber.equals("")) {
                            System.out.println("Wrong phone number");
                            continue;
                        } else {
                            System.out.println(pNumber);
                            break;
                        }
                    }
                    while (true) {
                        System.out.println("Relate to group");
                        System.out.println("Enter 1: " + Types.FRIEND + ". 2: " + Types.COLLEAGUE + ". 3: "
                                + Types.MATE + " .4: " + Types.SERVICE + ". 5: " + Types.OTHER + ".");
                        try{
                            int answer = Integer.parseInt(reader.readLine());
                            System.in.skip(System.in.available());
                            switch (answer) {
                                case 1:
                                    System.out.println("Creating contact in group \"Friends\"");
                                    TemporaryStorage.add(new Friend(fName, sName, pNumber));
                                    break;
                                case 2:
                                    System.out.println("Creating contact in group \"Colleagues\"");
                                    TemporaryStorage.add(new Colleague(fName, sName, pNumber));
                                    break;
                                case 3:
                                    System.out.println("Creating contact in group \"Mates\"");
                                    TemporaryStorage.add(new Mate(fName, sName, pNumber));
                                    break;
                                case 4:
                                    System.out.println("Creating contact in group \"Services\"");
                                    TemporaryStorage.add(new Service(fName, sName, pNumber));
                                    break;
                                case 5:
                                    System.out.println("Creating contact in group \"Others\"");
                                    TemporaryStorage.add(new Other(fName, sName, pNumber));
                                    break;
                                default:
                                    continue;
                            }
                        }
                        catch (NumberFormatException e){continue;}

                        break;
                    }
                    System.out.println("Do you wish to add another one contact? Press Y or N.");
                    char symbol = Character.toUpperCase((char) reader.read());
                    System.in.skip(System.in.available());
                    if(symbol!='Y')
                    {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static String checkPhoneNumber(String pNumber)
        {
            Pattern pattern = Pattern.compile("^[0-9]{10}$");
            Matcher matcher = pattern.matcher(pNumber);
            if(!matcher.matches())
            {
                return "";
            }
            else return pNumber = "+380"+' '+'('+
                    pNumber.substring(1,3)+')'+' '+
                    pNumber.substring(3,6)+' '+
                    pNumber.substring(6,8)+' '+
                    pNumber.substring(8,10);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 5).append(this.firstName).append(this.phoneNumber).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Contact)) return false;
        if(this == obj) return true;

        Contact temp = (Contact) obj;
        return new EqualsBuilder().
                append(this.firstName, temp.firstName).
                append(this.secondName, temp.secondName).
                append(this.phoneNumber, temp.phoneNumber).isEquals();
    }

    @Override
    public String toString() {
        return "My "+this.getClass().getSimpleName()+" name: "+this.firstName+

                ". Second name: "+this.secondName+". Phone number: "+this.phoneNumber;
    }
}
