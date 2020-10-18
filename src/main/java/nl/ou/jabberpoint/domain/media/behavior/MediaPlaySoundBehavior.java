package nl.ou.jabberpoint.domain.media.behavior;

import nl.ou.jabberpoint.ui.JabberPointNotification;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class MediaPlaySoundBehavior implements ExecutableMedia {

    private final File audioFile;
    private Clip audioClip;

    public MediaPlaySoundBehavior(File audioFile) {
        this.audioFile = audioFile;
    }

    @Override
    public void start() {
        openMedia();
        audioClip.start();
    }

    @Override
    public boolean isPlaying() {
        return audioClip.isActive();
    }

    @Override
    public void stop() {
        audioClip.stop();
        audioClip = null;
    }

    private void openMedia() {
        try {
            audioClip = AudioSystem.getClip();

            audioClip.open(AudioSystem.getAudioInputStream(audioFile));
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            JabberPointNotification.mediaNotPlayableNotification();
        }
    }
}
