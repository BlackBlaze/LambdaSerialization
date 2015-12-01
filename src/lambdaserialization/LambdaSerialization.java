package lambdaserialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel
 */
public class LambdaSerialization {

    User user = new User();

    public static void main(String[] args) {
        LambdaSerialization ls = new LambdaSerialization();
        ls.test();
    }

    public void test() {

        
        
//        User user = this.user;  //  TODO: Uncomment this line to resolve NotSerializableException
        
        user.setId(1);
        user.setName("testName");
        user.setNick("testNick");
        
        Car car = new Car();
        car.setId(1);
        car.setModel("Seat");
        
        user.setCar(car);
        

        try {
            /**
             * Serializing user
             */
            LambdaSerialization.serialize(user, "serialization.txt");

            /**
             * Deserializing user
             */
            User newUser = (User) LambdaSerialization.deserialize("serialization.txt");
            System.out.println(newUser.toString());

            //Lambda serialization
            LambdaSerialization.filter((User u) -> user.equals(u), new User());

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(LambdaSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static <T> void filter(SerializablePredicate<T> sp, T value) throws IOException, ClassNotFoundException {

        sp.getClass().isLocalClass();

        File tempFile = File.createTempFile("labmda", "set");

        try (ObjectOutput oo = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            oo.writeObject(sp);
        }

        try (ObjectInput oi = new ObjectInputStream(new FileInputStream(tempFile))) {
            SerializablePredicate<T> p = (SerializablePredicate<T>) oi.readObject();

            System.out.println(p.test(value));
        }

    }

    public static Object deserialize(String fileName) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        Object obj;
        try (ObjectInputStream ois = new ObjectInputStream(bis)) {
            obj = ois.readObject();
        }
        return obj;
    }

    public static void serialize(Object obj, String fileName)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
        }
    }
}
