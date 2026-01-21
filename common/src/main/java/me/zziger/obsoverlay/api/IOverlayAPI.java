package me.zziger.obsoverlay.api;

import me.zziger.obsoverlay.component.IOverlayComponent;

public interface IOverlayAPI {
    /**
     * Should be called before drawing a specific component
     * This will hide component only if it is enabled in settings
     * @param component Component that is being drawn
     */
    default void beginDraw(IOverlayComponent component) {}

    /**
     * Should be called after drawing a specific component, if you used beginDraw(IOverlayComponent component)
     * @param component Component that was drawn
     */
    default void endDraw(IOverlayComponent component) {}

    /**
     * Should be called before rendering elements, that you want hidden from stream
     * This method does not check settings
     */
    default void beginDraw() {}

    /**
     * Should be called after rendering elements, if you used beginDraw()
     * This method does not check settings
     */
    default void endDraw() {}
}
