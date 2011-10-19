//
// SocializeN - auth and social services for use in PlayN-based games
// Copyright (c) 2011, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/socializen/blob/master/etc/LICENSE

package socializen.android;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

import playn.core.PlayN;
import playn.core.util.Callback;

import socializen.core.Card;
import socializen.core.SocializeN;

/**
 * Provides an implementation of {@link Facebook} for the Android backend.
 */
public class FacebookAndroid implements socializen.core.Facebook
{
    public FacebookAndroid (Activity activity, String appId) {
        _activity = activity;
        _fb = new Facebook(appId);
    }

    public void authorizeCallback (int requestCode, int resultCode, Intent data) {
        _fb.authorizeCallback(requestCode, resultCode, data);
    }

    @Override public void establishSession (final Callback<Void> callback) {
        // check whether we have a valid existing session
        try {
            String token = PlayN.storage().getItem(ACCESS_TOKEN_KEY);
            String expires = PlayN.storage().getItem(ACCESS_EXPIRES_KEY);
            if (token != null && expires != null) {
                _fb.setAccessToken(token);
                _fb.setAccessExpires(Long.parseLong(expires));
            }
            // if the session is valid, we're done
            if (_fb.isSessionValid()) {
                SocializeN.queueAction(new Runnable() {
                    public void run () {
                        callback.onSuccess(null);
                    }
                });
                return;
            }
        } catch (Exception e) {
            PlayN.log().warn("Failed to check for existing session", e);
        }

        // no valid existing session, so establish a new one!
        String[] perms = new String[] { /* nada */ };
        _fb.authorize(_activity, perms, new Facebook.DialogListener() {
            @Override public void onComplete (Bundle values) {
                PlayN.storage().setItem(ACCESS_TOKEN_KEY, _fb.getAccessToken());
                PlayN.storage().setItem(ACCESS_EXPIRES_KEY, String.valueOf(_fb.getAccessExpires()));
                callback.onSuccess(null);
            }
            @Override public void onFacebookError (FacebookError error) {
                callback.onFailure(error);
            }
            @Override public void onError (DialogError e) {
                callback.onFailure(e);
            }
            @Override public void onCancel() {
                callback.onFailure(new Exception(USER_CANCELED_AUTH));
            }
        });
    }

    @Override public void logout (Callback<Void> callback) {
        new AsyncFacebookRunner(_fb).logout(_activity, new CBLA<Void>(callback) {
            public void onComplete (String response, Object state) {
                callback.onSuccess(null);
            }
        }, null);
    }

    @Override public void getMyCard (Callback<Card> callback) {
        // TODO
    }

    @Override public void getMyFriends (Callback<List<Card>> callback) {
        // TODO
    }

    protected static abstract class CBLA<T> implements AsyncFacebookRunner.RequestListener {
        /** The callback to which we automatically delegate failures. */
        public final Callback<T> callback;

        public CBLA (Callback<T> callback) {
            this.callback = callback;
        }

        @Override public void onIOException (IOException e, Object state) {
            callback.onFailure(e);
        }
        @Override public void onFileNotFoundException (FileNotFoundException e, Object state) {
            callback.onFailure(e);
        }
        @Override public void onMalformedURLException (MalformedURLException e, Object state) {
            callback.onFailure(e);
        }
        @Override public void onFacebookError (FacebookError e, Object state) {
            callback.onFailure(e);
        }
    }

    protected final Activity _activity;
    protected final Facebook _fb;

    protected static final String ACCESS_TOKEN_KEY = "socializen.facebook.access_token";
    protected static final String ACCESS_EXPIRES_KEY = "socializen.facebook.access_expires";
}
