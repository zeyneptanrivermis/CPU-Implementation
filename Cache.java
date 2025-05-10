public class Cache {
    private static final int BLOCK_COUNT = 8;
    private final int[] tags = new int[BLOCK_COUNT];
    private final int[] data = new int[BLOCK_COUNT];
    private final boolean[] valid = new boolean[BLOCK_COUNT];

    private final Memory memory;
    private int hits = 0;
    private int accesses = 0;

    public Cache(Memory memory) {
        this.memory = memory;
    }

    public int read(int address) {
        accesses++;
        int blockNumber = address / 2;
        int blockIndex = blockNumber % BLOCK_COUNT;
        int tag = blockNumber / BLOCK_COUNT;

        if (valid[blockIndex] && tags[blockIndex] == tag) {
            hits++;
            return data[blockIndex];
        }

        int high = Byte.toUnsignedInt(memory.readByte(address));
        int low = Byte.toUnsignedInt(memory.readByte(address + 1));
        int word = (high << 8) | low;

        tags[blockIndex] = tag;
        data[blockIndex] = word;
        valid[blockIndex] = true;
        return word;
    }

    public void write(int address, int value) {
        // Write-through policy
        memory.writeByte(address, (byte) ((value >> 8) & 0xFF)); // high byte
        memory.writeByte(address + 1, (byte) (value & 0xFF));      // low byte

        int blockNumber = address / 2;
        int blockIndex = blockNumber % BLOCK_COUNT;
        int tag = blockNumber / BLOCK_COUNT;

        tags[blockIndex] = tag;
        data[blockIndex] = value;
        valid[blockIndex] = true;
    }

    public double getHitRatio() {
        return accesses == 0 ? 0.0 : (hits * 100.0) / accesses;
    }
}