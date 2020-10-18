package nl.ou.jabberpoint.domain.media.behavior;

import java.io.File;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class MediaPlayVideoBehavior implements ExecutableMedia {
    private final File videoFile;

    public MediaPlayVideoBehavior(File videoFile) {
        this.videoFile = videoFile;
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void start() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPlaying() {
        return false;
    }
}
