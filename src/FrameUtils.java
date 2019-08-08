public class FrameUtils {

    public static int getFrame(int address) {
        return (int)Math.floor((float)address/(float)512);
    }

    public static int getAddress(int frame) {
        return frame * 512;
    }

}
