public class PageTable {

    public static int getEntry(int tableAddress, int entry) {
        return PhysicalMemory.getValue(tableAddress+entry);
    }

    public static void setEntry(int tableAddress, int entry, int value) {
        PhysicalMemory.setValue(tableAddress+entry, value);
    }

}