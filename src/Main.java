import java.io.IOException;

public class Main {

    static String usbPath = "";
    static String input1 = "input1.txt";
    static String input2 = "input2.txt";

    public static void main(String[] args) throws IOException {

        Manager manager = new Manager(false);//no tlb
        manager.initializePM(usbPath+input1);
        manager.translateVirtuals(usbPath+input2, usbPath+"482544261.txt");

        Manager managerTLB = new Manager(true);//with tlb
        managerTLB.initializePM(usbPath+input1);
        managerTLB.translateVirtuals(usbPath+input2, usbPath+"482544262.txt");

    }

}
