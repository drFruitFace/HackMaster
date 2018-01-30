package hackmaster20.business;

import hackmaster20.objects.ResourceClass;

/**
 * Created by Owner on 1/29/2018.
 */

public class ResourceManager {
    private ResourceClass player;
    private ResourceClass enemy;

    public ResourceManager(ResourceClass p, ResourceClass e) {
        player = p;
        enemy = e;
    }

    public ResourceClass getPlaterR() { return player; }
    public ResourceClass getEnemyR() { return enemy; }

    public String toString() {
        String strung = "ResourceManager String\n";

        if (player != null)
            strung += "player:" + player.toString();
        if (enemy != null)
            strung += "\nenemy:" + enemy.toString();

        return strung;
    }
}