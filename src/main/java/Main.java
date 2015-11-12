import java.util.Scanner;

/**
 * Created by nim_13512065 on 11/8/15.
 */
public class Main {
    public static int EXIT_CODE;
    public static void main(String[] args) {
        Options options = null;
        do {
            System.out.println("Selamat datang di program java-cassandra");
            System.out.println("1. mendaftar user baru");
            System.out.println("2. follow a friend");
            System.out.println("3. menambah tweet");
            System.out.println("4. menampilkan tweet per user");
            System.out.println("5. menampilkan timeline per user");
            System.out.println("6. exit");
            Scanner input = new Scanner(System.in);
            int pil = input.nextInt();
        } while (options!=Options.EXIT_CODE);
    }

    enum Options {
        CREATE_USER(1),
        FOLLOW(2),
        ADD_TWEET(3),
        DISPLAY_TWEET(4),
        DISPLAY_TIMELINE(5),
        EXIT_CODE(6);


        private final int code;

        Options(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

    }
}
