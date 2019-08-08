public class PhysicalMemory {

    static int memory[];

    public static void init() {
        memory = new int[524288];
    }

    public static void setValue(int address, int value) {
        memory[address] = value;
    }

    public static int getValue(int address) {
        return memory[address];
    }

}
