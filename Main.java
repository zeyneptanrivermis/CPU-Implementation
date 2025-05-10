
public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: java Main <programFile> <configFile>");
            return;
        }
        CPUEmulator cpu = new CPUEmulator();
        cpu.loadConfig(args[1]);
        cpu.loadProgram(args[0]);
        cpu.run();
        // private alanlar yerine getter’ları kullanıyoruz:
        System.out.printf("Value in AC: %d%n", cpu.getAC());
        System.out.printf("Cache hit ratio: %.2f%%%n", cpu.getCacheHitRatio());
    }
}
