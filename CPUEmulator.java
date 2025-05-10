import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CPUEmulator {
    private int PC = 0;
    private int AC = 0;
    private int FLAG = 0;
    private int baseAddress;

    private final Memory memory = new Memory();
    private final Cache cache = new Cache(memory);

    public void loadConfig(String configFile) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(configFile));
        baseAddress = Integer.parseInt(lines.get(0).trim().replace("0x", ""), 16);
        PC = Integer.parseInt(lines.get(1).trim().replace("0x", ""), 16);
        System.out.printf("✅ Config loaded: baseAddress = 0x%04X, PC = 0x%04X%n", baseAddress, PC);
    }

    public void loadProgram(String programFile) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(programFile));
        int addr = baseAddress;
        for (String line : lines) {
            int instruction = Integer.parseInt(line, 2);
            memory.writeByte(addr, (byte) ((instruction >> 8) & 0xFF)); // high byte
            memory.writeByte(addr + 1, (byte) (instruction & 0xFF));     // low byte
            addr += 2;
        }
    }

    public void run() {
        System.out.printf("📍 PC starts at: 0x%04X | Base address: 0x%04X%n", PC, baseAddress);
        System.out.println("🟢 Program execution started...");

        while (true) {
            int instruction = fetch();
            int opcode = (instruction >> 12) & 0xF;
            int operand = instruction & 0xFFF;
            System.out.printf("PC:0x%04X OPC:0x%X OPD:0x%03X AC:%d FLAG:%d%n", PC, opcode, operand, AC, FLAG);

            boolean jumped = false;
            int effectiveAddr;

            switch (opcode) {
                case 0x0: // START
                    break;
                case 0x1: // LOAD immediate
                    AC = operand;
                    break;
                case 0x2: // LOADM: Relative addressing using baseAddress
                    effectiveAddr = baseAddress + 2 * operand;
                    AC = cache.read(effectiveAddr);
                    break;
                case 0x3: // STORE: Relative addressing using baseAddress
                    effectiveAddr = baseAddress + 2 * operand;
                    cache.write(effectiveAddr, AC);
                    break;
                case 0x4: // CMPM: Relative addressing using baseAddress
                    effectiveAddr = baseAddress + 2 * operand;
                    FLAG = Integer.compare(AC, cache.read(effectiveAddr));
                    break;
                case 0x5: // CJMP: Absolute addressing
                    if (FLAG > 0) {
                        PC = baseAddress + 2 * operand;
                        jumped = true;
                    }
                    break;
                case 0x6: // JMP: Absolute addressing
                    PC = baseAddress + 2 * operand;
                    jumped = true;
                    break;
                case 0x7: // ADD immediate
                    AC += operand;
                    break;
                case 0x8: // ADDM: Relative addressing using baseAddress
                    effectiveAddr = baseAddress + 2 * operand;
                    AC += cache.read(effectiveAddr);
                    break;
                case 0x9: // SUB immediate
                    AC -= operand;
                    break;
                case 0xA: // SUBM: Relative addressing using baseAddress
                    effectiveAddr = baseAddress + 2 * operand;
                    AC -= cache.read(effectiveAddr);
                    break;
                case 0xB: // MUL immediate
                    AC *= operand;
                    break;
                case 0xC: // MULM: Relative addressing using baseAddress
                    effectiveAddr = baseAddress + 2 * operand;
                    AC *= cache.read(effectiveAddr);
                    break;
                case 0xD: // DISP
                    System.out.printf("AC = %d%n", AC);
                    break;
                case 0xE: // HALT
                    System.out.println("HALT");
                    return;
                default:
                    throw new IllegalArgumentException("Invalid opcode: " + opcode);
            }

            if (!jumped) {
                PC += 2;
            }
        }
    }

    private int fetch() {
        // Instruction fetch through cache
        return cache.read(PC);
    }

    public int getAC() {
        return AC;
    }

    public double getCacheHitRatio() {
        return cache.getHitRatio();
    }
}