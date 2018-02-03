package hackmaster20.objects;

/**
 * Created by Owner on 1/29/2018.
 */

public class ResourceClass {
    private int health = 0; // 0 if not used
    private int cryptoCoin = 0;
    private int cpu = 0;
    private int botnet = 0;
    private int gpuMiner = 0;
    private int coreRate = 0;
    private int infectionRate = 0;

    public ResourceClass(int h, int crypt, int c, int bot, int gpu, int coreR, int infecR) {
        health = h;
        cryptoCoin = crypt;
        cpu = c;
        botnet = bot;
        gpuMiner = gpu;
        coreRate = coreR;
        infectionRate = infecR;
    }

    public int getHealth() { return health; }
    public int getCryptoCoin() { return cryptoCoin; }
    public int getCpu() { return cpu; }
    public int getBotnet() { return botnet; }
    public int getGpuMiner() { return gpuMiner; }
    public int getCoreRate() { return coreRate; }
    public int getInfectionRate() { return infectionRate; }

    public String toString() {
        String strung = "";

        if (health != 0)
            strung +="\nHealth:"+health;
        if (cryptoCoin != 0)
            strung +="\nHCoin:"+cryptoCoin;
        if (cpu != 0)
            strung +="\nCPU:"+cpu;
        if (botnet != 0)
            strung +="\nBotnet:"+botnet;
        if (gpuMiner != 0)
            strung +="\nMiner:"+gpuMiner;
        if (coreRate != 0)
            strung +="\nTeraflops:"+coreRate;
        if (infectionRate != 0)
            strung +="\nBotRate:"+infectionRate;

        return strung;
    }
}
