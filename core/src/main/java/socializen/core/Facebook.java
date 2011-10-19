//
// SocializeN - auth and social services for use in PlayN-based games
// Copyright (c) 2011, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/socializen/blob/master/etc/LICENSE

package socializen.core;

import java.util.List;

import playn.core.util.Callback;

/**
 * Provides access to Facebook social and auth services. The general flow should be:
 * <pre>{@code
 * Facebook fb = ...;
 * fb.establishSession(new Callback<Void>() {
 *   public void onSuccess (Void result) {
 *     // it's now OK to call fb.getMyCard, etc.
 *   }
 * });
 * }</pre>
 */
public interface Facebook
{
    /** An error code indicating that no active session is established. */
    String NO_ACTIVE_SESSION = "no_active_session";

    /** An error code indicating that the user canceled the auth request. */
    String USER_CANCELED_AUTH = "user_canceled_auth";

    /**
     * Obtains an authorization token for this user and this app. If the user has already
     * authorized the app and a valid session token exists, the callback will be notified
     * immediately. Otherwise an authorization dialog will be presented (possibly including a
     * request for the user to logon to Facebook). If the user cancels the auth dialog, an {@link
     * Exception} will be delivered with {@link #USER_CANCELED_AUTH} as its message.
     */
    void establishSession (Callback<Void> callback);

    /**
     * Logs the user out of the current session. A subseqeuent call to {@link #establishSession}
     * will require them to logon to Facebook (but not to reauthorize this app for access, unless
     * they have also explicitly removed the app in Facebook).
     */
    void logout ();

    /**
     * Fetches the {@link Card} for the authenticated user. If a valid Facebook session does not
     * exist, an {@link Exception} will be delivered with {@link #NO_ACTIVE_SESSION} as its
     * message.
     */
    void getMyCard (Callback<Card> callback);

    /**
     * Fetches {@link Card}s for the authenticated user's friends. If a valid Facebook session does
     * not exist, an {@link Exception} will be delivered with {@link #NO_ACTIVE_SESSION} as its
     * message.
     */
    void getMyFriends (Callback<List<Card>> callback);
}
