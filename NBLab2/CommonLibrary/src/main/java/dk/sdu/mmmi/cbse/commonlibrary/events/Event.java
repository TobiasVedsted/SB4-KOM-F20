package dk.sdu.mmmi.cbse.commonlibrary.events;

import dk.sdu.mmmi.cbse.commonlibrary.data.Entity;
import java.io.Serializable;

/**
 *
 * @author Mads
 */
public class Event implements Serializable{
    private final Entity source;

    public Event(Entity source) {
        this.source = source;
    }

    public Entity getSource() {
        return source;
    }
}
