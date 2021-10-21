import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class UserProfile implements Serializable {
    private static final long serialVersionUID = 26292552485L;

    private String login;
    private String email;
    private transient String password;

    public UserProfile(String login, String email, String password) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    private void writeObject(ObjectOutputStream oos) throws Exception {
        oos.defaultWriteObject();
        String encryptPassword = encrypt(password);
        oos.writeObject(encryptPassword);
    }

    private String encrypt(String password) {
        StringBuilder sb = new StringBuilder(password);
        for (int i = 0; i < password.length(); i++) {
            sb.setCharAt(i, (char) (sb.charAt(i) + 1));
        }
        return sb.toString();
    }

    private void readObject(ObjectInputStream ois) throws Exception {
        ois.defaultReadObject();
        this.password = decrypt((String) ois.readObject());
    }

    private String decrypt(String readObject) {
        StringBuilder sb = new StringBuilder(readObject);
        for (int i = 0; i < readObject.length(); i++) {
            sb.setCharAt(i, (char) (sb.charAt(i) - 1));
        }
        return sb.toString();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}