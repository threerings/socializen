//
// SocializeN - auth and social services for use in PlayN-based games
// Copyright (c) 2011, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/socializen/blob/master/etc/LICENSE

package socializen.core;

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

    protected static Facebook _facebook;
    protected static Google _google;
    protected static GameCenter _gameCenter;
}
