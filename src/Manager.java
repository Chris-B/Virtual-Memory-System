import java.io.*;

public class Manager {

    BitMap bitMap = new BitMap();
    boolean tlb;

    public Manager (boolean tlb) {
        this.tlb = tlb;
        PhysicalMemory.init();
        if(tlb)
            TLB.init();
        bitMap.setFrame(0, 1, true);
    }

    public void initializePM(String file) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String STEntries = reader.readLine();
        String PTEntries = reader.readLine();
        reader.close();

        String STSplit[] = STEntries.split(" ");
        String PTSplit[] = PTEntries.split(" ");

        for(int i = 0; i < STSplit.length; i+=2) {

            int segment = Integer.parseInt(STSplit[i]);
            int address = Integer.parseInt(STSplit[i+1]);

            SegmentTable.setEntry(segment, address);

            if(address != -1)
                bitMap.setAddress(address, 2, true);
        }
        for(int i = 0; i < PTSplit.length; i+=3) {

            int page = Integer.parseInt(PTSplit[i]);
            int segment = Integer.parseInt(PTSplit[i+1]);
            int address = Integer.parseInt(PTSplit[i+2]);

            int PTAddress = SegmentTable.getEntry(segment);

            PageTable.setEntry(PTAddress, page, address);

            if(address != -1)
                bitMap.setAddress(address, 1, true);
        }
    }

    public void translateVirtuals(String input, String output) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String VAEntries = reader.readLine();
        reader.close();

        String VASplit[] = VAEntries.split(" ");

        BufferedWriter writer = new BufferedWriter(new FileWriter(output));

        for(int i = 0; i < VASplit.length; i+=2) {

            int access = Integer.parseInt(VASplit[i]);
            int address = Integer.parseInt(VASplit[i + 1]);

            VirtualAddress vA = new VirtualAddress(address);


            int sp = vA.getSegmentPage();
            int offset = vA.getOffset();

            if(tlb) {
                int tlbPA = TLB.lookup(sp);
                if (tlbPA == -1)
                    writer.write("m ");
                else {
                    writer.write("h ");
                    writer.write(tlbPA + offset + " ");
                    continue;
                }
            }

            int segment = vA.getSegmentNumber();
            int sEntry = SegmentTable.getEntry(segment);
            if (sEntry == -1) {
                writer.write("pf ");
                continue;
            }
            if (sEntry == 0) {
                if (access == 1)
                    sEntry = createPT(segment);
                else {
                    writer.write("err ");
                    continue;
                }
            }

            int page = vA.getPageNumber();
            int pEntry = PageTable.getEntry(sEntry, page);
            if (pEntry == -1) {
                writer.write("pf ");
                continue;
            }
            if (pEntry == 0) {
                if (access == 1)
                    pEntry = createPage(sEntry, page);
                else {
                    writer.write("err ");
                    continue;
                }
            }

            if(tlb)
                TLB.updateOnMiss(sp, pEntry);

            int PA = pEntry + offset;
            writer.write(PA+" ");
        }
        writer.close();
    }

    int createPT(int segment) {
        int startFrame = bitMap.findOpenFrames(2);
        bitMap.setFrame(startFrame, 2, true);
        int address = FrameUtils.getAddress(startFrame);
        SegmentTable.setEntry(segment, address);
        return address;
    }

    int createPage(int PTAddress, int page) {
        int startFrame = bitMap.findOpenFrames(1);
        bitMap.setFrame(startFrame, 1, true);
        int address = FrameUtils.getAddress(startFrame);
        PageTable.setEntry(PTAddress, page, address);
        return address;
    }

}
