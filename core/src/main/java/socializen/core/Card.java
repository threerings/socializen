//
// SocializeN - auth and social services for use in PlayN-based games
// Copyright (c) 2011, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/socializen/blob/master/etc/LICENSE

package socializen.core;

/**
 * Provides basic information for a particular social network user.
 */
public class Card
{
    /** The user's unique id. */
    public final String userId;

    /** The user's name. */
    public final String name;

    /** The user's photo. */
    public final String photoURL;

    public Card (String userId, String name, String photoURL) {
        this.userId = userId;
        this.name = name;
        this.photoURL = photoURL;
    }
}
