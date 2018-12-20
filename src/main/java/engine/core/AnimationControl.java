package engine.core;

import java.util.ArrayList;

public final class AnimationControl {

    private final ArrayList<Integer> frames;
    private long elapsedTime;
    private final long frameTime;
    private int step;

    public AnimationControl(String frames, long frameTime) {
        this.frameTime = frameTime;
        final var framesList = frames.split(",");
        this.frames = new ArrayList<>(framesList.length + 1);
        for (var frame : framesList)
            this.frames.add(Integer.parseInt(frame));
        this.frames.add(-1);
    }

    /**
     *
     * @param totalElapsedTime Total time elapsed in milliseconds
     * @return
     */
    public boolean update(final long totalElapsedTime) {
        // Update elapsed time
        elapsedTime += totalElapsedTime;
        // If the elapsed time is longer than the time frame, we have to change the frames
        if (elapsedTime > frameTime) {
            elapsedTime = 0;
            if (frames.get(++step) == -1) {
                step = 0;
                return true;
            }
        }
        return false;
    }

    public void restart() {
        elapsedTime = step = 0;
    }

    /**
     * @return The index of the current frame
     */
    public int getStep() {
        return step;
    }

    /**
     * @param step The frame index to be set
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     *
     * @return The index of the current frame to draw
     */
    public int getCurrentFrame() {
        return frames.get(step);
    }

}
