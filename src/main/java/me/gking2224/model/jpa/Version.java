package me.gking2224.model.jpa;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.springframework.core.convert.converter.Converter;

@Embeddable
public class Version {
    
    public static enum IncrementType {
        MAJOR, MINOR;
    }
    Integer majorVersion;
    Integer minorVersion;
    private String location;
    private boolean enabled;
    
    public Integer getMajorVersion() {
        return majorVersion;
    }
    
    public void setMajorVersion(Integer majorVersion) {
        this.majorVersion = majorVersion;
    }
    
    public Integer getMinorVersion() {
        return minorVersion;
    }
    
    public void setMinorVersion(Integer minorVersion) {
        this.minorVersion = minorVersion;
    }
    
    public Version() {
        
    }
    
    public Version(Integer majorVersion, Integer minorVersion) {
        super();
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }
    
    @Override
    public String toString() {
        return "Version [majorVersion=" + majorVersion + ", minorVersion=" + minorVersion + "]";
    }
    
    public boolean equalsString(String str) {
        return asVersionString().equals(str);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((majorVersion == null) ? 0 : majorVersion.hashCode());
        result = prime * result + ((minorVersion == null) ? 0 : minorVersion.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Version other = (Version) obj;
        if (majorVersion == null) {
            if (other.majorVersion != null)
                return false;
        } else if (!majorVersion.equals(other.majorVersion))
            return false;
        if (minorVersion == null) {
            if (other.minorVersion != null)
                return false;
        } else if (!minorVersion.equals(other.minorVersion))
            return false;
        return true;
    }
    
    public String asVersionString() {
        return new StringBuilder().append(majorVersion).append(".").
                append(minorVersion).toString();
    }
    
    public static Version fromString(String str) {
        String regex = null;
        if (str.indexOf('.') != -1) {
            regex = "\\.";
        }
        else if (str.indexOf('_') != -1) {
            regex = "_";
        }
        else {
            return new Version(Integer.parseInt(str), 0);
        }
        String[] parts = str.split(regex);
        Integer majorVersion = Integer.parseInt(parts[0]);
        Integer minorVersion = Integer.parseInt(parts[1]);
        return new Version(majorVersion, minorVersion);
    }
    
    public static class StringToVersion implements Converter<String, Version> {

        @Override
        public Version convert(String source) {
            return Version.fromString(source);
        }
    }
    
    public static class VersionToString implements Converter<Version, String> {

        @Override
        public String convert(Version source) {
            return source.asVersionString();
        }
    }

    @Transient
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Transient
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
}