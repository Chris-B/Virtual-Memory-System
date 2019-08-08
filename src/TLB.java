public class TLB {

    static TLBEntry[] buffer;

    public static void init() {
        buffer = new TLBEntry[4];
        for(int i = 0; i < buffer.length; i++)
            buffer[i] = new TLBEntry();
    }

    public static int lookup(int sp) {
        for (int i = 0; i < buffer.length; i++) {
            TLBEntry entry = buffer[i];
            if (entry.SP == sp) {
                updateOnHit(i);
                return entry.PA;
            }
        }
        return -1;
    }

    static void updateOnHit(int lru) {
        TLBEntry[] temp = new TLBEntry[4];
        int count = 0;
        for (int i = 0; i < buffer.length; i++) {
            if(i == lru) {
                temp[3] = buffer[i];
                continue;
            }
            temp[count++] = buffer[i];
        }
        buffer = temp;
    }

    public static void updateOnMiss(int sp, int pa) {
        TLBEntry[] temp = new TLBEntry[4];
        TLBEntry newEntry = new TLBEntry();
        newEntry.SP = sp;
        newEntry.PA = pa;
        temp[3] = newEntry;
        for (int i = 1; i < buffer.length; i++)
            temp[i-1] = buffer[i];
        buffer = temp;
    }

}

class TLBEntry {
    public TLBEntry() {
        this.SP = -1;
        this.PA = -1;
    }
    int SP;
    int PA;
}
