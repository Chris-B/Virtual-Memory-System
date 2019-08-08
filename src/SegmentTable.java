public class SegmentTable {

    public static int getEntry(int segment) {
        if (segment >= 512 || segment < 0)
            return -1;
        return PhysicalMemory.getValue(segment);
    }

    public static void setEntry(int segment, int value) {
        if (segment >= 512 || segment < 0)
            return;
        PhysicalMemory.setValue(segment, value);
    }

}
