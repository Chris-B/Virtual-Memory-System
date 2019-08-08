public class VirtualAddress {

    int address;

    public VirtualAddress(int v) {
        this.address = v;
    }

    public int getSegmentNumber() {
        return address >> 19;
    }

    public int getPageNumber() {
        return (address >> 9) & 0x3FF;
    }

    public int getOffset() { return address & 0x1FF; }

    public int getSegmentPage() { return address >> 9; }

}
