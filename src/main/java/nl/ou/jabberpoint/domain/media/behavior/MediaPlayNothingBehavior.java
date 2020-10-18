package nl.ou.jabberpoint.domain.media.behavior;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class MediaPlayNothingBehavior implements ExecutableMedia {

    public MediaPlayNothingBehavior() {

    }

    @Override
    public void start() {
        // nothing to do here
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void stop() {
        // nothing to do here
    }
}
