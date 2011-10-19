SocializeN
----------

This library provides cross-platform bindings to popular social networking APIs
for use by [PlayN] games. Much like PlayN, a game is written against a generic
API, and implementations are provided for each supported PlayN platform (Java,
GWT, Android, etc.).

Because the process of configuring an app on a single platform for a single
social network is complicated, we cannot hope to explain, in a simple manner,
how one goes about configuring their app to work across multiple social
networks and multiple platforms. If you are familiar with a given social
network's APIs and configuration process, you will likely have no problem
profiting from this library.

If some ambitious community member wishes to provide documentation on how to
get things working on one or more combinations of social network and platform,
we'll gladly incorporate it, though it will likely rapidly fall out of date as
these social networks evolve their APIs and configuration process at break-neck
speeds.

Building
--------

The library is built using [SBT] or [Maven].

Invoke `sbt publish-local` to build and install the library to your local Ivy
repository (i.e. `~/.ivy2/local`).

Invoke `mvn install` to build and install the library to your local Maven
repository (i.e. `~/.m2/repository`).

Distribution
------------

SocializeN is released under the New BSD License. The most recent version of
the library is available at http://github.com/threerings/socializen

Contact
-------

Questions, comments, and other communications can be handled via the [Three
Rings Libraries](http://groups.google.com/group/ooo-libs) Google Group.

[PlayN]: http://code.google.com/p/playn
[API documentation]: http://threerings.github.com/socializen/apidocs/overview-summary.html
[SBT]: http://github.com/harrah/xsbt/wiki/Setup
[Maven]: http://maven.apache.org/
