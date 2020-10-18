package nl.ou.jabberpoint.domain.media.behavior;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public interface ExecutableMedia {
    void stop();

    void start();

    boolean isPlaying();
}
