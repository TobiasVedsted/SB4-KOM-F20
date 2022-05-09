package dk.sdu.mmmi.cbse.commonlibrary.services;

import dk.sdu.mmmi.cbse.commonlibrary.data.GameData;
import dk.sdu.mmmi.cbse.commonlibrary.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
