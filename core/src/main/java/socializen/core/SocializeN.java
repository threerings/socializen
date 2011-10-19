//
// SocializeN - auth and social services for use in PlayN-based games
// Copyright (c) 2011, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/socializen/blob/master/etc/LICENSE

package socializen.core;

import java.util.ArrayList;
import java.util.List;

import playn.core.PlayN;

/**
 * Provides access to the various social services.
 */
public class SocializeN
{
    /** Provides Facebook auth and social services.
     * @return null unless a Facebook backend has been registered. */
    public static Facebook facebook () {
        return _facebook;
    }

    /** Provides Google auth and social services.
     * @return null unless a Google backend has been registered. */
    public static Google google () {
        return _google;
    }

    /** Provides iOS Game Center auth and social services.
     * @return null unless a Game Center backend has been registered. */
    public static GameCenter gameCenter () {
        return _gameCenter;
    }

    /**
     * Registers the Facebook backend. This will generally not be called directly, rather the
     * library user should call, for example, {@code FacebookBackend.register()}.
     */
    public static void setFacebook (Facebook facebook) {
        if (_facebook != null) {
            throw new IllegalStateException("Facebook backend already registered.");
        }
        _facebook = facebook;
    }

    /**
     * Registers the Google backend. This will generally not be called directly, rather the library
     * user should call, for example, {@code GoogleBackend.register()}.
     */
    public static void setGoogle (Google google) {
        if (_google != null) {
            throw new IllegalStateException("Google backend already registered.");
        }
        _google = google;
    }

    /**
     * Registers the Game Center backend. This will generally not be called directly, rather the
     * library user should call, for example, {@code GameCenterBackend.register()}.
     */
    public static void setGameCenter (GameCenter gameCenter) {
        if (_gameCenter != null) {
            throw new IllegalStateException("Game Center backend already registered.");
        }
        _gameCenter = gameCenter;
    }

    /**
     * This method must be called in your {@link Game#update} method to process any pending
     * deferred events enqueued by the social network backends.
     */
    public static void processActions () {
        List<Runnable> actions;
        synchronized (_actions) {
            if (_actions.isEmpty()) return;
            actions = new ArrayList<Runnable>(_actions);
            _actions.clear();
        }
        for (Runnable action : actions) {
            try {
                action.run();
            } catch (Throwable t) {
                PlayN.log().warn("SocializeN action failure", t);
            }
        }
    }

    /**
     * Enqueues an action for processing on the next call to {@link #processActions}.
     */
    public static void queueAction (Runnable action) {
        synchronized (_actions) {
            _actions.add(action);
        }
    }

    protected static Facebook _facebook;
    protected static Google _google;
    protected static GameCenter _gameCenter;
    protected static List<Runnable> _actions = new ArrayList<Runnable>();
}
