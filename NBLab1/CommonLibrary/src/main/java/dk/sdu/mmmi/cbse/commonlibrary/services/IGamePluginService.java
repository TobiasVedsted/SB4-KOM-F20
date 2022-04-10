package dk.sdu.mmmi.cbse.commonlibrary.services;

import dk.sdu.mmmi.cbse.commonlibrary.data.GameData;
import dk.sdu.mmmi.cbse.commonlibrary.data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
