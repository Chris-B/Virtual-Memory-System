public class BitMap {

    int bitMap[] = new int[32];

    int getBitMapPos(int frame) { return (int)Math.floor((float)frame/(float)32); }

    int getBitPos(int frame) { return frame % 32; }

    boolean isOccupied(int frame) {//0-1023
        int bitMapPos = getBitMapPos(frame);
        int integer = bitMap[bitMapPos];
        int bitPos = getBitPos(frame);
        return ((integer >> bitPos) & 0x1) == 1;
    }

    void setFrame(int frame, boolean occupied) {
        int bitMapPos = getBitMapPos(frame);
        int integer = bitMap[bitMapPos];
        int bitPos = getBitPos(frame);
        int occupiedBit = occupied == true ? 1 : 0;
        int orBit = occupiedBit << bitPos;
        bitMap[bitMapPos] = integer | orBit;
    }

    void setFrame(int startFrame, int frameCount, boolean occupied) {
        for (int i = 0; i < frameCount; i++)
            setFrame(startFrame+i, occupied);
    }

    void setAddress(int address, int frameCount, boolean occupied) {
        int startFrame = FrameUtils.getFrame(address);
        for (int i = 0; i < frameCount; i++)
            setFrame(startFrame + i, occupied);
    }

    public int findOpenFrames(int count) {
        int startFrame = -1;
        int currLen = 0;
        for(int i = 0; i < 1024; i++) {
            if(currLen == count)
                break;
            if (currLen == 0)
                startFrame = i;
            if(!isOccupied(i))
                currLen++;
            else
                currLen = 0;
        }
        return startFrame;
    }

}
