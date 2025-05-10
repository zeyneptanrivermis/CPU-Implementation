
public class Memory {
    private final byte[] mem = new byte[65536];

    public byte readByte(int address) {
        return mem[address];
    }

    public void writeByte(int address, byte value) {
        mem[address] = value;
    }
}
