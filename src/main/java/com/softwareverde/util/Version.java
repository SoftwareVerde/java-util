package com.softwareverde.util;

import java.util.List;

public class Version implements Comparable<Version> {
    public static Version parse(final String versionString) {
        final List<String> parts = StringUtil.pregMatch("^[v]?([0-9]*)\\.?([0-9]*)\\.?([0-9]*)-?(.*)$", versionString);
        if (parts.isEmpty()) { return null; }

        final String lowerCaseVersionString = versionString.toLowerCase();
        final boolean hasPrefix = lowerCaseVersionString.startsWith("v");

        final Integer major = Util.parseInt(parts.get(0));
        final Integer minor = (parts.size() > 1 ? Util.parseInt(parts.get(1)) : null);
        final Integer patch = (parts.size() > 2 ? Util.parseInt(parts.get(2)) : null);
        final String extra = (parts.size() > 3 ? parts.get(3) : null);

        return new Version(major, minor, patch, extra, hasPrefix);
    }

    protected final Boolean _hasPrefix;
    public final Integer major;
    public final Integer minor;
    public final Integer patch;
    public final String extra;

    public Version(final Integer major) {
        this(major, null, null, null, false);
    }

    public Version(final Integer major, final Integer minor) {
        this(major, minor, null, null, false);
    }

    public Version(final Integer major, final Integer minor, final Integer patch) {
        this(major, minor, patch, null, false);
    }

    public Version(final Integer major, final Integer minor, final Integer patch, final String extra) {
        this(major, minor, patch, extra, false);
    }

    public Version(final Integer major, final Integer minor, final Integer patch, final String extra, final Boolean hasPrefix) {
        this._hasPrefix = hasPrefix;
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.extra = (Util.isBlank(extra) ? null : extra);
    }

    @Override
    public boolean equals(final Object object) {
        if (! (object instanceof Version)) { return false; }

        final Version version = (Version) object;
        if (! Util.areEqual(this.major, version.major)) { return false; }
        if (! Util.areEqual(this.minor, version.minor)) { return false; }
        if (! Util.areEqual(this.patch, version.patch)) { return false; }
        if (! Util.areEqual(this.extra, version.extra)) { return false; }

        return true;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        if (this._hasPrefix) {
            stringBuilder.append('v');
        }

        stringBuilder.append(this.major);

        if (this.minor != null) {
            stringBuilder.append(".");
            stringBuilder.append(this.minor);

            if (this.patch != null) {
                stringBuilder.append(".");
                stringBuilder.append(this.patch);

                if (this.extra != null) {
                    stringBuilder.append("-");
                    stringBuilder.append(this.extra);
                }
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(final Version version) {
        final int majorCompare = Util.coalesce(this.major).compareTo(Util.coalesce(version.major));
        if (majorCompare != 0) { return majorCompare; }

        final int minorCompare = Util.coalesce(this.minor).compareTo(Util.coalesce(version.minor));
        if (minorCompare != 0) { return minorCompare; }

        final int patchCompare = Util.coalesce(this.patch).compareTo(Util.coalesce(version.patch));
        if (patchCompare != 0) { return patchCompare; }

        return Util.coalesce(this.extra).compareTo(Util.coalesce(version.extra));
    }
}
